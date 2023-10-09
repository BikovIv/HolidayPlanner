package holidayplanner.HolidayPlanner;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class ClientAgentEntity {

	public ClientAgent myClientAgent = new ClientAgent();
	
	public List<String> allTripTypes;

	public ClientAgentEntity() {
		
    }
	
	public List<Holiday> getHolidayByUserParameters(String tripType, String placeToStayType, String tripTownType, double placeToStayPrice){
		
		return getHolidayByUserParameters(tripType, placeToStayType, tripTownType, placeToStayPrice);
	}
}
