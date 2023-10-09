package holidayplanner.HolidayPlanner;

import java.io.IOException;
import java.io.Serializable;
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
	
	public HolidayPlannerAgent() {}
	public List<String> allTripTypes;
	private HolidayPlannerOntology holidayPlannerOntology =  new HolidayPlannerOntology();
	
	@Override
	protected void setup() {
		
		System.out.println("Started HolidayPlannerAgent(" + this.getName() + ")");

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		
		Object services[] = {"holidayPlanner","initializeData"};
		
		  for(int i=0;i < services.length ;i++){

			  ServiceDescription sd  = new ServiceDescription();
			        sd.setType((String)services[i]);
			        sd.setName("happy holiday");
			        dfd.addServices(sd);
			  }
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		addBehaviour(new HolidayReqestBehaviour());
		addBehaviour(new InitializeReqestBehaviour());
	}
	
	private class HolidayReqestBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			
			ACLMessage msg = receive(mt);
			
			if(msg != null) {System.out.println(msg.getConversationId() + ": got message" + msg);}
			
			
			if(msg != null && msg.getConversationId() == "holiday_stuff") {
				
				String tripType = "HolidayTrip";//msg.getUserDefinedParameter("tripType");
				String placeToStayType = "Hotel";//msg.getUserDefinedParameter("placeToStayType");
				String tripTownType = "PlainTown";//msg.getUserDefinedParameter("tripTownType");
				double placeToStayPrice = 10;//Double.parseDouble(msg.getUserDefinedParameter("placeToStayPrice"));
				
				System.out.println("HolidayPlannerAgent: Somebody search for holiday with tripType:  " + tripType +
						", placeToStayType :" + placeToStayType + ", tripTownType :" + tripTownType + ", placeToStayPrice :" + placeToStayPrice);
				
				ACLMessage reply = msg.createReply();
				
				ArrayList<Holiday> result = new ArrayList<>();
				
				try {
					result.add(getHolidayByIRI("<http://www.semanticweb.org/ivan/ontologies/2023/4/holiday-planner-ontology#BanskoHolidayTrip01>"));
				} catch (OWLOntologyStorageException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//= holidayPlannerOntology.getHolidayByUserParameters(tripType, placeToStayType, tripTownType, "", placeToStayPrice);
				
				if(result.size() > 0) {
					
					System.out.println("HolidayPlannerAgent: I have it");
					ObjectMapper mapper = new ObjectMapper();
					
					reply.setPerformative(ACLMessage.PROPOSE);
					
					try {
						reply.setContent(mapper.writeValueAsString(result));
					
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
	
	private class InitializeReqestBehaviour extends CyclicBehaviour{

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.
					MatchPerformative(ACLMessage.CFP);
			
			ACLMessage msg = receive(mt);
			
			if(msg != null && msg.getConversationId() == "initialize_stuff") {
				
				//System.out.println(msg);
				//String town = msg.getContent();
				
				System.out.println("HolidayPlannerAgent: Somebody wants initial data");
				
				ACLMessage reply = msg.createReply();
				
				ArrayList<TripType> result = 
						holidayPlannerOntology.getAllTripTypesArray();
				ArrayList<PlaceToStayType> placeToStayTypes = 
						holidayPlannerOntology.getAllPlaceToStayTypesArray();
				ArrayList<TripTownType> townTypes = 
						holidayPlannerOntology.getAllTownTypesArray();
				
				allTripTypes = holidayPlannerOntology.getAllTripTypes("Trip");
				
				///for(PlaceToStayType p : placeToStayTypes) {
				//	System.out.println("p: " + p);			
				//}
				
				if((result.size() > 0 && placeToStayTypes.size() > 0) || allTripTypes.size() > 0) {
					
					System.out.println("HolidayPlannerAgent: Initial data collected");
					ObjectMapper mapper = new ObjectMapper();
					
					//reply.addUserDefinedParameter("AllTripTypesArray", mapper.writeValueAsString(result));
					//reply.addUserDefinedParameter("AllPlaceToStayTypesArray", placeToStayTypes.toString());
					
					//System.out.println("HolidayPlannerAgent: Initial data collected: " + reply);
					reply.setPerformative(ACLMessage.PROPOSE);
					System.out.println("HolidayPlannerAgent: Initial data collected: " + result + placeToStayTypes + townTypes);					
					
					try {
						reply.setContent(mapper.writeValueAsString("result"));
						
						reply.addUserDefinedParameter("AllTripTypesArray", mapper.writeValueAsString(result));
						reply.addUserDefinedParameter("AllPlaceToStayTypesArray", mapper.writeValueAsString(placeToStayTypes));
						reply.addUserDefinedParameter("AllTownTypesArray", mapper.writeValueAsString(townTypes));
						
						reply.addUserDefinedParameter("AllTripTypes", mapper.writeValueAsString(allTripTypes));
						
						reply.setLanguage("JSON");
						
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}				
				}else {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("HolidayPlannerAgent: Initial NOT data collected!");
					System.out.println("HolidayPlannerAgent: Initial NOT data collected!");
				}
				
				send(reply);				
			}
			
			
		}		
	}
	
	
	/*private class TownsReqestBehaviour extends CyclicBehaviour{

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
				
				*if(result.size() > 0) {
					
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
				}*
				
				send(reply);				
			}		
		}		
	}*/
	
	public void addTrip(String tripType, String tripName, String town, String townType, String placeToStayType, String placeToStayName, double costByNight) throws OWLOntologyStorageException {
		//System.out.println("addTrip - " + tripType + tripName + town + townType);
		
		holidayPlannerOntology.addTrip(tripType, tripName, town, townType, placeToStayType, placeToStayName, costByNight);
	}
	
	public void addPlaceToStay(String placeToStayType/*Hotel/Motel*/, String placeToStayName, double costByNight) throws OWLOntologyStorageException {
		holidayPlannerOntology.addPlaceToStay(placeToStayType, placeToStayName, costByNight);
	}
	
	public List<String> getAllTripTypes(String tripType) throws OWLOntologyStorageException {
		return holidayPlannerOntology.getAllTripTypes(tripType);
	}
	
	public List<String> getAllTownTypes() throws OWLOntologyStorageException {
		return holidayPlannerOntology.getAllTownTypes();
	}
	
	public List<Holiday> getHolidayByUserParameters(String tripType, String tripSubType, String placeToStayType, String tripTownType, double placeToStayPrice) throws OWLOntologyStorageException {
		//System.out.println("tripType: " + tripType);
		return (List<Holiday>) holidayPlannerOntology.getAllTripTypesFromOntology(tripType, tripSubType);//getHolidayByUserParameters(tripType, placeToStayType, tripTownType, placeToStayPrice);
	}
	
	public Holiday getHolidayByIRI(String tripIRI) throws OWLOntologyStorageException {
		//System.out.println("tripType: " + tripType);
		return holidayPlannerOntology.getTripByIRI(tripIRI);
	}
	
}
