package holidayplanner.HolidayPlanner;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@Embeddable
public class HolidayPlannerAgentEntity {
	
	HolidayPlannerAgent myHolidayPlannerAgent = new HolidayPlannerAgent();
	
	public List<String> allTripTypes;

	public HolidayPlannerAgentEntity() {
		
    }
	
	public List<String> getAllTripTypes(String tripType){ 

		try {
			
			allTripTypes = myHolidayPlannerAgent.getAllTripTypes(tripType);
		
		} catch (OWLOntologyStorageException e2) {
			e2.printStackTrace();
		}
		
		return allTripTypes;
	}
	
	public List<Holiday> getHolidayByUserParameters(String tripType, String tripSubType, String a, String tripTownType, double placeToStayPrice){

		try {
			System.out.println("myHolidayPlannerAgent.getHolidayByUserParameters, tripType: " + tripType + 
																					"tripSubType: " + tripSubType);

			return myHolidayPlannerAgent.getHolidayByUserParameters(tripType, tripSubType, a, tripTownType, placeToStayPrice);
		
		} catch (/*OWLOntologyStorage*/Exception e2) {
			e2.printStackTrace();
		}
		
		return null;
	}
	
	public Holiday getHolidayByIRI(String tripIRI){

		try {

			return myHolidayPlannerAgent.getHolidayByIRI(tripIRI);
		
		} catch (/*OWLOntologyStorage*/Exception e2) {
			e2.printStackTrace();
		}
		
		return null;
	}
}
