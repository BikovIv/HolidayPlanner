package holidayplanner.HolidayPlanner;

import jade.core.Agent;

public class HolidayPlannerAgent extends Agent{
	
	HolidayPlannerAgentGUI gui;
	
	@Override
	protected void setup() {
		gui = new HolidayPlannerAgentGUI(this);
		
		System.out.println("Hello World...");
	}
}
