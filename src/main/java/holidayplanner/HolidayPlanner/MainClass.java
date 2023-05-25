package holidayplanner.HolidayPlanner;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class MainClass {
	
	public static void main(String[] args) {
		
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1099");
		profile.setParameter(Profile.GUI, "true");
		
		AgentContainer mainContainer =  rt.createMainContainer(profile);
		
		try {
			
			AgentController ag = mainContainer.createNewAgent("buyer", "holidayplanner.HolidayPlanner.HolidayPlannerAgent", null);
			
			//AgentController ag2 = mainContainer.createNewAgent("pizzaria", "uni.fmi.masters.PizzariaAgent", null);
			
			ag.start();
			//ag2.start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
