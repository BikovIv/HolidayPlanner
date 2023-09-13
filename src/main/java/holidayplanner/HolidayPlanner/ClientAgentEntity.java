package holidayplanner.HolidayPlanner;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class ClientAgentEntity {

	//HolidayPlannerAgent myHolidayPlannerAgent = new HolidayPlannerAgent();
	ClientAgent myClientAgent = new ClientAgent();
	
	public List<String> allTripTypes;

	public ClientAgentEntity() {
		
    }
	
	public List<Holiday> getHolidayByUserParameters(String tripType, String placeToStayType, String tripTownType, double placeToStayPrice){

		/*try {
			
			allTripTypes = myHolidayPlannerAgent.getAllTripTypes();
		
		} catch (OWLOntologyStorageException e2) {
			e2.printStackTrace();
		}*/
		
		return getHolidayByUserParameters(tripType, placeToStayType, tripTownType, placeToStayPrice);
	}
}
