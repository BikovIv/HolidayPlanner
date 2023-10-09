package holidayplanner.HolidayPlanner.userTrip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;

import holidayplanner.HolidayPlanner.Holiday;
import holidayplanner.HolidayPlanner.HolidayPlannerAgent;
import holidayplanner.HolidayPlanner.HolidayPlannerAgentEntity;
//import holidayplanner.HolidayPlanner.user.UserEntity;
import holidayplanner.HolidayPlanner.user.User;
import jade.wrapper.AgentController;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserTripController {
	
	private UserTripService userTripService;
	private HolidayPlannerAgentEntity myHolidayPlannerAgentEntity = new HolidayPlannerAgentEntity();
	
	@Autowired
	public UserTripController(UserTripService userTripService) {
		this.userTripService = userTripService;
	}	
	
	@PostMapping(path = "/userTrip/add")
	@ResponseBody
	public /*UserActivityEntity*/ /*ResponseEntity<String>*/
	RedirectView add( //@RequestParam(value = "title") String title, 
			@RequestParam(value = "name") String name,
			@RequestParam(value = "place_to_stay_id") int place_to_stay_id,
			@RequestParam(value = "town") String town,
			@RequestParam(value = "trip_desctiption") String trip_desctiption,
			@RequestParam(value = "trip_destination") String trip_destination,
			@RequestParam(value = "trip_season") String trip_season,
			@RequestParam(value = "trip_transportation") String trip_transportation,
			@RequestParam(value = "trip_type") String trip_type,
			@RequestParam(value = "user_id") int user_id,
			@RequestParam(value = "activity_id") int activity_id,
			@RequestParam(value = "tripiri") String tripiri,
			@RequestParam(value = "image") String image, 
																//@RequestParam(value = "description") String description, 
																//@RequestParam(value = "imagePath") String imagePath, 
																//@RequestParam(value = "activity_id") int activity_id, 
																//@RequestParam(value = "file") MultipartFile file,
																//@RequestParam(value = "user_activity_id") int user_activity_id,
																HttpSession session) {
		
		User user = (User)session.getAttribute("loggedUser");
		user_id = user.getId();
		
		System.out.println("session.getAttribute(\"userClientAgent\")" + session.getAttribute("userClientAgent"));
		//AgentController agent = new 
		//System.out.println("hereeeeeeeeeeeeeeeeee");
		
		userTripService.addUserTrip(name, place_to_stay_id, town, trip_desctiption,trip_destination, trip_season, trip_transportation,
									trip_type, user_id,  activity_id, tripiri, image);
		
		return  new RedirectView("/myTrips.html"); //new ResponseEntity<String>("{\"msg\":\"success\"}", HttpStatus.OK);
		//return userActivityService.addUserActivity(title, description, activity_id/*, repeatPassword, email*/);
	}
	
	@PostMapping(path = "/userTrip/edit")
	@ResponseBody
	public /*UserActivityEntity*/ /*ResponseEntity<String>*/
	RedirectView edit( @RequestParam(value = "user_trip_id") int user_trip_id, 
			@RequestParam(value = "name") String name,
			@RequestParam(value = "place_to_stay_id") int place_to_stay_id,
			@RequestParam(value = "town") String town,
			@RequestParam(value = "trip_desctiption") String trip_desctiption,
			@RequestParam(value = "trip_destination") String trip_destination,
			@RequestParam(value = "trip_season") String trip_season,
			@RequestParam(value = "trip_transportation") String trip_transportation,
			@RequestParam(value = "trip_type") String trip_type,
			@RequestParam(value = "user_id") int user_id,
			@RequestParam(value = "activity_id") int activity_id,
			@RequestParam(value = "tripiri") String tripiri,
			@RequestParam(value = "file") MultipartFile file,
			HttpSession session) {
		
		System.out.println(file);
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		
		String imageName = file.getOriginalFilename();
		
		
		User user = (User)session.getAttribute("loggedUser");
		user_id = user.getId();
		System.out.println("hereeeeeeeeeeeeeeeeee" + user_trip_id);
		
		userTripService.editUserTrip(user_trip_id, name, place_to_stay_id, town, trip_desctiption,trip_destination, trip_season, trip_transportation,
									trip_type, user_id,  activity_id,  tripiri, imageName /*MultipartFile file*/);
		
		return  new RedirectView("/myTrips.html"); //new ResponseEntity<String>("{\"msg\":\"success\"}", HttpStatus.OK);
		//return userActivityService.addUserActivity(title, description, activity_id/*, repeatPassword, email*/);
	}
	
	@DeleteMapping(path = "/userTrip/delete")
	//@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteUserActivity(@RequestParam(value = "id")int id, HttpSession session){
				
		return null;//userTripService.deleteUserActivity(id);
	}
	
	@GetMapping(path = "/userTrip/all")
	public List<UserTripEntity> getAllUserActivities(){
		
		return userTripService.getAll();	
	}
	
	//OK - inicializira dropdown-a i palni vtoriq
	@PostMapping(path = "/userTrip/getAllActivityTypesFromOntology")
	public List<String> getAllActivityTypesFromOntology(@RequestBody MyRequest requestBody){
		
		String tripType = requestBody.getTripType();
	    
		//String responseMessage = "Selected value is: " + tripType;
		//System.out.println(responseMessage); 
		//System.out.println("/userActivity/getAllActivityTypesFromOntology !!!!!!!!!!!!!!!!!!!!!@!@!@!@@@!@!@!@!@" + " trip_type: " + tripType);
		
		return myHolidayPlannerAgentEntity.getAllTripTypes(tripType);

	}
		//OK RABOTI!!!!!!!!!!!!!!!!
		@PostMapping(value = "/userTrip/getHolidayByUserParameters")					
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
	@PostMapping(value = "/userTrip/getHolidayByIRI")					
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
	
	@PostMapping(value = "/userTrip/getUserTripsByUserId")					
	public List<UserTripEntity> getUserTripsByUserId(  HttpSession session) {
		System.out.println("/userTrip/getUserTripsByUserId -  session");
		System.out.println(session.getAttribute("loggedUser"));
		 //String tripIRI;
		// String tripType;
       //  String tripSubType;
		User user = (User)session.getAttribute("loggedUser");
		 int user_id = user.getId();
		 
		 System.out.println("/userTrip/getUserTripsByUserId -  user.getId()");
			System.out.println(user.getId()); 
		try {
           /* // Read the request body
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
            */
            System.out.println("/userActivity/getHolidayByIDDDDDDDDDDDDDDD **************************************************************");
            
            return userTripService.findByUserId(user_id);

            // Rest of your controller logic...
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here...
        }
		
		return null;

	}
	
	@PostMapping(value = "/userTrip/getUserTripById")					
	public UserTripEntity getUserTripById(@RequestBody Map<String, Integer> requestBody) {
		
		try {
            System.out.println("/userActivity/getHolidayByID **************************************************************");
            int trip_id = requestBody.get("trip_id");
            return userTripService.findById(trip_id);     
        } catch (Exception e) {
            e.printStackTrace();
        }	
		return null;
	}

	
	/*
	@GetMapping(path = "/userActivity/getById")
	public UserActivityEntity getUserActivityById(@RequestParam(value = "id")int id){
		
		return userActivityService.getById(id);	
	}
	
	@GetMapping(path = "/userActivity/getByActivityId")
	public  List<UserActivityEntity> getUserActivityByActivityId(@RequestParam(value = "activity_id")int activity_id){
		System.out.println("/userActivity/getByActivityId");
		return userActivityService.getBy(activity_id);	
	}*/
	
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
