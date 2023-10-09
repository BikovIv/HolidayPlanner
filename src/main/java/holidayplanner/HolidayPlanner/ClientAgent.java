package holidayplanner.HolidayPlanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
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
	//private String searchedTown = null;
	public String tripType = "HolidayTrip";
	public String placeToStayType = "Hotel";
	public String tripTownType = "PlainTown";
	public double placeToStayPrice = 0;
	public String placeToStayPriceString = "10";
	public ArrayList<AID> sellers;
	public Holiday[] holidayArray = new Holiday[]{};
	public String[] holidayNames = new String[]{};
	public TripType[] tripTypes = new TripType[]{};
	public String[] tripTypesNames = new String[]{};
	public PlaceToStayType[] placeToStayTypes = new PlaceToStayType[]{};
	public String[] placeToStayTypeNames = new String[]{};
	public TripTownType[] tripTownTypes = new TripTownType[]{};
	public String[] tripTownTypeNames = new String[]{};
	public List<String> allTripTypes;

	@Override
	protected void setup() {
		
		gui = new ClientAgentGUI(this);
		
		System.out.println("Started ClientAgent(" + this.getName().toString());// + ") and ClientAgentGUI( + gui.getTitle() + )");
		
		addBehaviour(new TickerBehaviour(this, 200) {
			
			DFAgentDescription dfd;// = new DFAgentDescription();
			ServiceDescription sd;// = new ServiceDescription();
			DFAgentDescription[] descrptions;// = DFService.search(myAgent, dfd);
			
			@Override
			protected void onTick() {

				if(tripTypesNames.length == 0 || placeToStayTypeNames.length == 0) {//get all data for dropdowns when start client
					
					System.out.println("Initilizing data");
					
					dfd = new DFAgentDescription();		
					sd = new ServiceDescription();			
					sd.setType("initializeData"); 
					dfd.addServices(sd);
					
					try {
						descrptions = DFService.search(myAgent, dfd);
						
						//or(DFAgentDescription d : descrptions) {
							//System.out.println("descrptions: " + d);	
						//}
						
						sellers = new ArrayList<>();
						
						for(int i = 0; i < descrptions.length; i++) {
							sellers.add(descrptions[i].getName());
							//System.out.println("descrptions: " + descrptions[i].getName());	
						}
						
					} catch (FIPAException e) {
						e.printStackTrace();
					}	
					
					//System.out.println("sellers.size(): " + sellers.size());
					if(sellers.size() > 0) {
						//System.out.println("myAgent.addBehaviour(new InitializeBehaviour());");
						myAgent.addBehaviour(new InitializeBehaviour());
			
					}else {
						System.out.println("Cant initialize data");
					}
				} //end initializing data
				
				if(tripType != null) {
					System.out.println("ClientAgent: Searching for holidays with town: " + 
							tripType);
					
					//DFAgentDescription dfd = new DFAgentDescription();
					dfd = new DFAgentDescription();
					//ServiceDescription sd = new ServiceDescription();
					sd = new ServiceDescription();
					//ServiceDescription sdAllTowns = new ServiceDescription();
					
					sd.setType("holidayPlanner");
					//sdAllTowns.setType("allTowns");
					
					dfd.addServices(sd);
					//dfd.addServices(sdAllTowns);
					
					try {
						
						//DFAgentDescription[] descrptions = DFService.search(myAgent, dfd);
						descrptions = DFService.search(myAgent, dfd);
						
						sellers = new ArrayList<>();
						
						for(int i = 0; i < descrptions.length; i++) {
							sellers.add(descrptions[i].getName());
						}
						
					} catch (FIPAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					
					if(sellers.size() > 0) {
						
						if(tripType != "***all_towns***") {
							myAgent.addBehaviour(new HolidayBehaviour());
						}else {
							//myAgent.addBehaviour(new AskForTownsBehaviour());
						}
							
					}else {
						System.out.println("ClientAgent: Not found holidays with tripType: " + tripType);
					}
						
				}
				
			}
		});
		
	}

	/*public String getsearchedTown() {
		return searchedTown;
	}

	public void setsearchedTown(String searchedTown) {
		this.searchedTown = searchedTown;
	}*/
	
	public String getsearchedTripType(String tripType)	 {
		return tripType;
	}

	public void setsearchedTripType(String tripType)	 {
		this.tripType = tripType;
	}
	
	public String getsearchedPlaceToStayType(String placeToStayType) {
		return placeToStayType;
	}

	public void setsearchedPlaceToStayType(String placeToStayType) {
		this.placeToStayType = placeToStayType;
	}
	
	public String getsearchedTripTownType(String tripTownType) {
		return tripTownType;
	}
	
	public void setsearchedTripTownType(String tripTownType) {
		this.tripTownType = tripTownType;
	}
	
	public String getsearchedPlaceToStayPrice(String placeToStayPrice) {
		return placeToStayPrice;
	}
	
	public void setsearchedPlaceToStayPrice(double placeToStayPrice) {
		this.placeToStayPrice = placeToStayPrice;
		this.placeToStayPriceString = String.valueOf(placeToStayPrice);
	}
	
	private class HolidayBehaviour extends Behaviour{

		int step = 0;
		MessageTemplate mt;
		int repliesCount = 0;
		
		
		//Holiday[] holidayArray;
		
		@Override
		public void action() {
			
			switch(step) {
			case 0:

				System.out.println("ClientAgent, HolidayBehaviour: Start to asking for holiday with town: " + tripType);
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				
				for(int i=0; i < sellers.size(); i++) {
					cfp.addReceiver(sellers.get(i));
				}
				//System.out.println("111111111111111111111111111111111");
				cfp.addUserDefinedParameter("tripType", tripType);
				//System.out.println("1111122222222");
				cfp.addUserDefinedParameter("placeToStayType", placeToStayType);
				//System.out.println("333333333333");
				cfp.addUserDefinedParameter("tripTownType", tripTownType);
				//System.out.println("4444444444444444");
				cfp.addUserDefinedParameter("placeToStayPrice",placeToStayPriceString);
				//System.out.println("55555555555555555");
				
				cfp.setContent(tripType);
				cfp.setConversationId("holiday_stuff");
				cfp.setReplyWith("cfp" 
						+ System.currentTimeMillis());
				//System.out.println("333333333333");
				mt = MessageTemplate.and(
						MessageTemplate.MatchConversationId("holiday_stuff"),
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
							
							holidayNames = new String[holidayArray.length];
							
							for(int i=0; i < holidayNames.length; i++) {
								holidayNames[i] = getClassFriendlyName(holidayArray[i]);
							}
							
							//gui.holidaysList.removeAll();
							//gui.holidaysList.setListData(holidayNames);
						
							//for(int i = 0; i < holidayArray.length; i++) {
							//	System.out.println(holidayArray[i]);
							//}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					repliesCount++;
					
					if(repliesCount >= sellers.size()) {
						//System.out.println("ClientAgent, HolidayBehaviour: End");
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

				tripType = null;
				
				removeBehaviour(this);
				
				return true;
			}
			
			return false;
		}
		
	}
	
	private class InitializeBehaviour extends Behaviour{

		int step = 0;
		MessageTemplate mt;
		int repliesCount = 0;
		
		@Override
		public void action() {
			
			switch(step) {
			case 0:

				System.out.println("ClientAgent, InitializeBehaviour: Start to asking for initial data");
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				
				for(int i=0; i < sellers.size(); i++) {
					cfp.addReceiver(sellers.get(i));
				}
				
				//cfp.setContent(searchedTown);
				cfp.setConversationId("initialize_stuff");
				cfp.setReplyWith("cfp" + System.currentTimeMillis());
				
				mt = MessageTemplate.and(
						MessageTemplate.MatchConversationId("initialize_stuff"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith())
						);
				
				send(cfp);
				
				step++;
				
				break;
				
			case 1:
				
				ACLMessage reply = receive(mt);
				
				if(reply != null) {
					
					//System.out.println("reply: " + reply.getUserDefinedParameter("AllTownTypesArray"));
					
					if(reply.getPerformative() == ACLMessage.PROPOSE) {
						ObjectMapper mapper = new ObjectMapper();
						
						try {
							//trip types begin
							tripTypes = mapper.readValue(
									reply.getUserDefinedParameter("AllTripTypesArray"), TripType[].class);
							
							tripTypesNames = new String[tripTypes.length];
							
							for(int i=0; i < tripTypesNames.length; i++) {
								tripTypesNames[i] = getClassFriendlyName(tripTypes[i].toString());
							}
							//System.out.println("tripTypesNames: " + tripTypesNames);
							for(String tripType : tripTypesNames) {
								//gui.tripTypeCB.addItem(tripType);
								//System.out.println("tripType: " + tripType);;
							}
							//trip Types End
							
							//placeToStayTypes begin
							placeToStayTypes = mapper.readValue(
									reply.getUserDefinedParameter("AllPlaceToStayTypesArray"), PlaceToStayType[].class);
							//System.out.println("placeToStayTypes: " + placeToStayTypes);
							placeToStayTypeNames = new String[placeToStayTypes.length];
							
							for(PlaceToStayType p : placeToStayTypes) {
								//System.out.println("placeToStayTypes: " + p + placeToStayTypes.length);			
							}
							
							for(int i=0; i < placeToStayTypeNames.length; i++) {
								placeToStayTypeNames[i] = getClassFriendlyName(placeToStayTypes[i].toString());
							}
							//System.out.println("placeToStayTypeNames: " + placeToStayTypeNames);
							for(String placeToStayType : placeToStayTypeNames) {
								//gui.placeToStayTypeCB.addItem(placeToStayType);
								//System.out.println("123123placeToStayType: " + placeToStayType);;
							}
							//placeToStayTypes end
							
							//tripTownTypeCB begin
							
							tripTownTypes = mapper.readValue(
									reply.getUserDefinedParameter("AllTownTypesArray"), TripTownType[].class);
							//System.out.println("placeToStayTypes: " + placeToStayTypes);
							tripTownTypeNames = new String[tripTownTypes.length];
							
							//for(PlaceToStayType p : tripTownTypes) {
							//	System.out.println("placeToStayTypes: " + p + tripTownTypes.length);			
							//}
							
							for(int i=0; i < tripTownTypeNames.length; i++) {
								tripTownTypeNames[i] = getClassFriendlyName(tripTownTypes[i].toString());
							}
							//System.out.println("placeToStayTypeNames: " + placeToStayTypeNames);
							for(String tripTownType : tripTownTypeNames) {
								//gui.tripTownTypeCB.addItem(tripTownType);
								//System.out.println("123123placeToStayType: " + placeToStayType);;
							}
							
							//tripTownTypeCB end
							
							//allTripTypes begin
							
							for(int i=0; i < tripTownTypeNames.length; i++) {
								tripTownTypeNames[i] = getClassFriendlyName(tripTownTypes[i].toString());
							}
							
							System.out.println("reply.getUserDefinedParameter(AllTripTypes)");
							System.out.println(reply.getUserDefinedParameter("AllTripTypes"));
							
							allTripTypes = mapper.readValue(
									reply.getUserDefinedParameter("AllTripTypes"), new TypeReference<List<String>>() {});
							System.out.println("allTripTypes");
							System.out.println(allTripTypes);
							
							//reply.getUserDefinedParameter("AllTripTypes");
							
							//allTripTypes end
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					repliesCount++;
					
					//System.out.println("ClientAgent: New response");
					if(repliesCount >= sellers.size()) {
						System.out.println("ClientAgent, InitializeBehaviour: End");
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

				tripType = null;
				
				removeBehaviour(this);
				
				return true;
			}
			
			return false;
		}
		
	}
	
	/*private class AskForTownsBehaviour extends Behaviour{

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
		
	}*/
	
	private String getClassFriendlyName(Holiday holiday) {
		
		String label = holiday.getId();
		
		//System.out.println(label);
		label = label.substring(label.indexOf('#') + 1).replace(">", "");
		
		return label;		
	}
	
	private String getClassFriendlyName(String text) {
		
		String label = text.toString();
		label = label.substring(label.indexOf('#') + 1).replace(">", "");
		
		return label;		
	}
}
