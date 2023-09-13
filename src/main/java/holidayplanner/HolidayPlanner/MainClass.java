package holidayplanner.HolidayPlanner;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainClass {   /* TUK TRQBWA DA VZEMA SAZDAVANETO NA AGENTI I DA GO SLOJA PRI LOGIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1*/
	
	public static void main(String[] args) {
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1099");
		//profile.setParameter(Profile.GUI, "true");
		
		AgentContainer mainContainer =  rt.createMainContainer(profile); //Start jade container gui
		
		try {
			//add agents to container gui
			AgentController ag1 = mainContainer.createNewAgent("holidayAdministratorAgent", "holidayplanner.HolidayPlanner.HolidayPlannerAgent", null);
			AgentController ag2 = mainContainer.createNewAgent("clientAgent", "holidayplanner.HolidayPlanner.ClientAgent", null);

			ag2.start();
			ag1.start();
			
			System.out.println("Agents started!");
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
