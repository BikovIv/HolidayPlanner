package holidayplanner.HolidayPlanner;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ClientAgent extends Agent{

	private ClientAgentGUI gui;
	private String searchedTown = null;
	private ArrayList<AID> sellers;
	
	@Override
	protected void setup() {
		
		gui = new ClientAgentGUI(this);
		
		System.out.println("Started ClientAgent(" + this.toString() + ") and ClientAgentGUI(" + gui.getTitle() + ")");
		
		addBehaviour(new TickerBehaviour(this, 2000) {
			
			@Override
			protected void onTick() {
				
				if(searchedTown != null) {
					System.out.println("ClientAgent: Searching for holidays with town: " + 
							searchedTown);
					
					DFAgentDescription dfd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					//ServiceDescription sdAllTowns = new ServiceDescription();
					
					sd.setType("holidayPlanner");
					//sdAllTowns.setType("allTowns");
					
					dfd.addServices(sd);
					//dfd.addServices(sdAllTowns);
					
					try {
						
						DFAgentDescription[] descrptions = 
								DFService.search(myAgent, dfd);
						
						sellers = new ArrayList<>();
						
						for(int i = 0; i < descrptions.length; i++) {
							sellers.add(descrptions[i].getName());
						}
						
					} catch (FIPAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					
					if(sellers.size() > 0) {
						
						if(searchedTown != "***all_towns***") {
							myAgent.addBehaviour(new HolidayBehaviour());
						}else {
							myAgent.addBehaviour(new AskForTownsBehaviour());
						}
							
					}else {
						System.out.println("ClientAgent: Not found holidays with town: " + searchedTown);
					}
						
				}
				
			}
		});
		
	}

	public String getsearchedTown() {
		return searchedTown;
	}

	public void setsearchedTown(String searchedTown) {
		this.searchedTown = searchedTown;
	}
	
	
	private class HolidayBehaviour extends Behaviour{

		int step = 0;
		MessageTemplate mt;
		int repliesCount = 0;
		
		Holiday[] holidayArray;
		
		@Override
		public void action() {
			switch(step) {
			case 0:
				
				System.out.println("ClientAgent: Start to asking for holiday with town: " + searchedTown);
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				
				for(int i=0; i < sellers.size(); i++) {
					cfp.addReceiver(sellers.get(i));
				}
				
				cfp.setContent(searchedTown);
				cfp.setConversationId("holiday stuff");
				cfp.setReplyWith("cfp" 
						+ System.currentTimeMillis());
				
				mt = MessageTemplate.and(
						MessageTemplate.MatchConversationId("holiday stuff"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith())
						);
				
				send(cfp);
				
				step++;
				
				break;
				
			case 1:
				
				ACLMessage reply = receive(mt);
				
				if(reply != null) {
					
					if(reply.getPerformative() == ACLMessage.PROPOSE) {
						ObjectMapper mapper = new ObjectMapper();
						
						try {
							holidayArray = mapper.readValue(
									reply.getContent(), Holiday[].class);
						
							for(int i = 0; i < holidayArray.length; i++) {
								System.out.println(holidayArray[i]);
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					repliesCount++;
					
					System.out.println("ClientAgent: New response");
					if(repliesCount >= sellers.size()) {
						System.out.println("ClientAgent: End");
						step++;
					}
					
				}
				
				break;
				
			}
			
		}

		@Override
		public boolean done() {
			if(step == 2) {		
								
				if(holidayArray == null || holidayArray.length == 0)
				{
					System.out.println("ClientAgent: There is no resuslts!");
				}
				
				searchedTown = null;
				
				removeBehaviour(this);
				
				return true;
			}
			
			return false;
		}
		
	}
	
	private class AskForTownsBehaviour extends Behaviour{

		int step = 0;
		MessageTemplate mt;
		int repliesCount = 0;
		
		Town[] townArray;
		
		@Override
		public void action() {
			switch(step) {
			case 0:
				
				System.out.println("ClientAgent: Start to ask for all towns: ");
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				
				for(int i=0; i < sellers.size(); i++) {
					cfp.addReceiver(sellers.get(i));
				}
				
				cfp.setContent(searchedTown);
				cfp.setConversationId("allTowns");
				cfp.setReplyWith("cfp" 
						+ System.currentTimeMillis());
				
				mt = MessageTemplate.and(
						MessageTemplate.MatchConversationId("allTowns"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith())
						);
				
				send(cfp);
				
				step++;
				
				break;
				
			case 1:
				
				ACLMessage reply = receive(mt);
				
				if(reply != null) {
					
					if(reply.getPerformative() == ACLMessage.PROPOSE) {
						ObjectMapper mapper = new ObjectMapper();
						
						try {
							townArray = mapper.readValue(
									reply.getContent(), Town[].class);
						
							for(int i = 0; i < townArray.length; i++) {
								System.out.println(townArray[i]);
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					repliesCount++;
					
					System.out.println("ClientAgent: New response");
					if(repliesCount >= sellers.size()) {
						System.out.println("ClientAgent: End");
						step++;
					}
					
				}
				
				break;
				
			}
			
		}

		@Override
		public boolean done() {
			if(step == 2) {		
								
				if(townArray == null || townArray.length == 0)
				{
					System.out.println("ClientAgent: There is no resuslts!");
				}
				
				searchedTown = null;
				
				removeBehaviour(this);
				
				return true;
			}
			
			return false;
		}
		
	}
}
