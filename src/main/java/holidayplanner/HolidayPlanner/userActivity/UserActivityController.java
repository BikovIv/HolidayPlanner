package holidayplanner.HolidayPlanner.userActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.Registration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import holidayplanner.HolidayPlanner.Holiday;
import holidayplanner.HolidayPlanner.HolidayPlannerAgent;
import holidayplanner.HolidayPlanner.HolidayPlannerAgentEntity;
import holidayplanner.HolidayPlanner.user.UserEntity;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserActivityController {
	
	private UserActivityService userActivityService;
	private HolidayPlannerAgentEntity myHolidayPlannerAgentEntity = new HolidayPlannerAgentEntity();
	
	@Autowired
	public UserActivityController(UserActivityService userActivityService) {
		this.userActivityService = userActivityService;
	}	
	
	@PostMapping(path = "/userActivity/add")
	@ResponseBody
	public /*UserActivityEntity*/ ResponseEntity<String> add( @RequestParam(value = "title") String title, 
																@RequestParam(value = "description") String description, 
																//@RequestParam(value = "imagePath") String imagePath, 
																@RequestParam(value = "activity_id") int activity_id, 
																@RequestParam(value = "file") MultipartFile file,
																@RequestParam(value = "user_activity_id") int user_activity_id,
																HttpSession session) {
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		userActivityService.addUserActivity(title, description/*, imagePath*/, activity_id, file, user_activity_id, user);	
		return new ResponseEntity<String>("{\"msg\":\"success\"}", HttpStatus.OK);
		//return userActivityService.addUserActivity(title, description, activity_id/*, repeatPassword, email*/);
	}
	
	@DeleteMapping(path = "/userActivity/delete")
	//@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteUserActivity(@RequestParam(value = "id")int id, HttpSession session){
				
		return userActivityService.deleteUserActivity(id);
	}
	
	@GetMapping(path = "/userActivity/all")
	public List<UserActivityEntity> getAllUserActivities(){
		
		return userActivityService.getAll();	
	}
	
	//OK - inicializira dropdown-a i palni vtoriq
	@PostMapping(path = "/userActivity/getAllActivityTypesFromOntology")
	public List<String> getAllActivityTypesFromOntology(@RequestBody MyRequest requestBody){
		
		String tripType = requestBody.getTripType();
	    
		//String responseMessage = "Selected value is: " + tripType;
		//System.out.println(responseMessage); 
		//System.out.println("/userActivity/getAllActivityTypesFromOntology !!!!!!!!!!!!!!!!!!!!!@!@!@!@@@!@!@!@!@" + " trip_type: " + tripType);
		
		return myHolidayPlannerAgentEntity.getAllTripTypes(tripType);

	}
		//OK RABOTI!!!!!!!!!!!!!!!!
		@PostMapping(value = "/userActivity/getHolidayByUserParameters")					
		public List<Holiday> getHolidayByUserParameters( HttpServletRequest request, HttpServletResponse response) {
			
			 String tripType;
	         String tripSubType;
			
			try {
	            // Read the request body
	            StringBuilder requestBody = new StringBuilder();
	            BufferedReader reader = request.getReader();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                requestBody.append(line);
	            }

	            // Parse the JSON data from the request body
	            ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
	            // Here, you can use a Map to directly access JSON properties
	            Map<String, Object> jsonData = objectMapper.readValue(requestBody.toString(), Map.class);

	            // Now you can access the parameters directly from the jsonData map
	             tripType = (String) jsonData.get("tripType");
	             tripSubType = (String) jsonData.get("tripSubType");

	            System.out.println("tripType: " + tripType);
	            System.out.println("tripSubType: " + tripSubType);
	            
	            System.out.println("/userActivity/getHolidayByUserParameters **************************************************************");
	            
	            return myHolidayPlannerAgentEntity.getHolidayByUserParameters(tripType, tripSubType ,"placeToStayType", "tripTownType", 0);

	            // Rest of your controller logic...
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle any exceptions here...
	        }
			
			return null;

		}
		
		//OK RABOTI!!!!!!!!!!!!!!!!
	@PostMapping(value = "/userActivity/getHolidayByIRI")					
	public Holiday getHolidayByIRI( HttpServletRequest request, HttpServletResponse response) {
		
		 String tripIRI;
		// String tripType;
       //  String tripSubType;
		
		try {
            // Read the request body
            StringBuilder requestBody = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            // Parse the JSON data from the request body
            ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
            // Here, you can use a Map to directly access JSON properties
            Map<String, Object> jsonData = objectMapper.readValue(requestBody.toString(), Map.class);

            // Now you can access the parameters directly from the jsonData map
             //tripType = (String) jsonData.get("tripType");
             //tripSubType = (String) jsonData.get("tripSubType");
             tripIRI = (String) jsonData.get("tripIRI");

            System.out.println("tripIRI: " + tripIRI);
           // System.out.println("tripSubType: " + tripSubType);
            
            System.out.println("/userActivity/getHolidayByIRI **************************************************************");
            
            return myHolidayPlannerAgentEntity.getHolidayByIRI(tripIRI);

            // Rest of your controller logic...
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here...
        }
		
		return null;

	}

	
	
	@GetMapping(path = "/userActivity/getById")
	public UserActivityEntity getUserActivityById(@RequestParam(value = "id")int id){
		
		return userActivityService.getById(id);	
	}
	
	@GetMapping(path = "/userActivity/getByActivityId")
	public  List<UserActivityEntity> getUserActivityByActivityId(@RequestParam(value = "activity_id")int activity_id){
		System.out.println("/userActivity/getByActivityId");
		return userActivityService.getBy(activity_id);	
	}
	
	/*@GetMapping(path = "/userActivity/getByTitle")
	public  List<UserActivityEntity> getByTitle(@RequestParam(value = "title") String title){
		System.out.println("/userActivity/getByTitle");
		return userActivityService.getBy(title);	
	}*/
	
	/*@RequestMapping(value = "/userActivity/getBy", params = {"title", "activity_id"})
	public  List<UserActivityEntity> getBy(@RequestParam(value = "title") String title, 
											@RequestParam(value = "activity_id")int activity_id){
		System.out.println("/userActivity/getBy");
		System.out.println(title);
		System.out.println(activity_id);
		System.out.println(userActivityService.getBy(title, activity_id));
		return userActivityService.getBy(title, activity_id);	
	}*/
	
	public static class MyRequest {
	    private String tripType;

	    // Getter and setter methods
	    public String getTripType() {
	        return tripType;
	    }

	    public void setTripType(String tripType) {
	        this.tripType = tripType;
	    }
	}
}
