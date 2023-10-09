package holidayplanner.HolidayPlanner;

import jade.core.Profile;
import jade.Boot;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainClass {   /* TUK TRQBWA DA VZEMA SAZDAVANETO NA AGENTI I DA GO SLOJA PRI LOGIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1*/
	
	public static Runtime rt = Runtime.instance();
	public static Profile profile = new ProfileImpl();
	public static AgentContainer mainContainer;//;=  rt.createMainContainer(profile); //Start jade container gui
	public AgentController agent = null;
	
	//private ClientAgentGUI gui = new ClientAgentGUI(this);
	
	public static void main(String[] args) {
		
		//Runtime rt = Runtime.instance();
		//Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1099");
		profile.setParameter(Profile.GUI, "true");
		
		mainContainer =  rt.createMainContainer(profile); //Start jade container gui
		
		try {
			//add agents to container gui
			AgentController ag1 = mainContainer.createNewAgent("holidayAdministratorAgent", "holidayplanner.HolidayPlanner.HolidayPlannerAgent", null);
			AgentController ag2 = mainContainer.createNewAgent("clientAgent", "holidayplanner.HolidayPlanner.ClientAgent", null);
			
			
			ag1.start();
			ag2.start();
			
			//String[] param = new String[ 3 ];
			//param[ 0 ] = "-gui";
			//param[ 1 ] = "holidayAdministratorAgent:holidayplanner.HolidayPlanner.HolidayPlannerAgent()";
			//param[ 2 ] = "holidayClientAgent:holidayplanner.HolidayPlanner.ClientAgent()";

			//Boot.main( param );
			
			//System.out.println("Agent administrator try to start! - " + ag1.getName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public AgentController startNewClientAgent(int user_id) {	
		//Runtime rt = Runtime.instance();
		//Profile profile = new ProfileImpl();
		//profile.setParameter(Profile.MAIN_HOST, "localhost");
		//profile.setParameter(Profile.MAIN_PORT, "1099");
		//profile.setParameter(Profile.GUI, "true");
		//AgentContainer mainContainer =  rt.createMainContainer(profile); //Start jade container gui
		
		try {
			//add agents to container gui
			agent = mainContainer.createNewAgent("clientAgent-" + user_id, "holidayplanner.HolidayPlanner.ClientAgent", null);

			agent.start();

			System.out.println("Agent client started from MainClass! - " + agent.getName().toString());
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return agent;
	}

}
