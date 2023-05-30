package holidayplanner.HolidayPlanner;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HolidayPlannerAgent extends Agent{
	
	private HolidayPlannerOntology holidayPlannerOntology =  new HolidayPlannerOntology();;
	
	HolidayPlannerAgentGUI gui;
	
	@Override
	protected void setup() {
		
		//System.out.println("1 + " + this);
		
		gui = new HolidayPlannerAgentGUI(this);
		
		System.out.println("Started HolidayPlannerAgent(" + this.toString() + ")");
		//holidayPlannerOntology = new HolidayPlannerOntology();
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		
		ServiceDescription sd = new ServiceDescription();
		sd.setType("holidayPlanner");
		sd.setName("happy holiday");
		
		dfd.addServices(sd);
		
		//ServiceDescription sdAllTowns = new ServiceDescription();
		//sdAllTowns.setType("allTowns");
		//sdAllTowns.setName("all towns");
		
		//dfd.addServices(sdAllTowns);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//holidayPlannerOntology.addTown("PlainTown", "TestPlaintown");
		
		
		/*for(String s : holidayPlannerOntology.getAllTowns()) {
			System.out.println(s);
		};
		
		for(String s : holidayPlannerOntology.getAllTownTypes()) {
			System.out.println(s);
		};
		*/
		addBehaviour(new HolidayReqestBehaviour());
		//addBehaviour(new TownsReqestBehaviour());
	}
	
	private class HolidayReqestBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.
					MatchPerformative(ACLMessage.CFP);
			
			ACLMessage msg = receive(mt);
			
			if(msg != null /*&& msg.getConversationId() == "holidayPlanner"*/) {
				
				System.out.println(msg);
				String town = msg.getContent();
				
				System.out.println("HolidayPlannerAgent: Somebody search for holiday with town:  " + town);
				
				ACLMessage reply = msg.createReply();
				
				ArrayList<Holiday> result = 
						holidayPlannerOntology.getHolidayByTown(town);
				
				if(result.size() > 0) {
					
					System.out.println("HolidayPlannerAgent: I have it");
					ObjectMapper mapper = new ObjectMapper();
					
					reply.setPerformative(ACLMessage.PROPOSE);
					
					try {
						reply.setContent(
								mapper.writeValueAsString(result));
					
						reply.setLanguage("JSON");
						
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}					
				}else {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("HolidayPlannerAgent: Not found!");
					System.out.println("HolidayPlannerAgent: No such holiday!");
				}
				
				send(reply);				
			}
			
			
		}		
	}
	
	private class TownsReqestBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.
					MatchPerformative(ACLMessage.CFP);
			
			ACLMessage msg = receive(mt);
			
			if(msg != null && msg.getConversationId() == "allTowns") {
					
				//String town = msg.getContent();
				System.out.println("HolidayPlannerAgent: List all towns");
				
				ACLMessage reply = msg.createReply();
				
				ArrayList<Town> result = null;
						
				//holidayPlannerOntology.getAllTowns();
				
				/*if(result.size() > 0) {
					
					System.out.println("HolidayPlannerAgent: I have it");
					ObjectMapper mapper = new ObjectMapper();
					
					reply.setPerformative(ACLMessage.PROPOSE);
					
					try {
						reply.setContent(
								mapper.writeValueAsString(result));
					
						reply.setLanguage("JSON");
						
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}					
				}else {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("HolidayPlannerAgent: Not found!");
					System.out.println("HolidayPlannerAgent: No such holiday TownsReqestBehaviour!");
				}*/
				
				send(reply);				
			}		
		}		
	}
	
	public void addTrip(String tripType, String tripName, String town, String townType) throws OWLOntologyStorageException {
		System.out.println("addTrip - " + tripType + tripName + town + townType);
		
		holidayPlannerOntology.addTrip(tripType, tripName, town, townType);
	}
	
	public List<String> getAllTripTypes() throws OWLOntologyStorageException {
		return holidayPlannerOntology.getAllTripTypes();
	}
	
	public List<String> getAllTownTypes() throws OWLOntologyStorageException {
		return holidayPlannerOntology.getAllTownTypes();
	}
	
	
}
