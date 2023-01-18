package holidayplanner.HolidayPlanner.userActivity;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import holidayplanner.HolidayPlanner.user.UserEntity;

@RestController
public class UserActivityController {
	
	private UserActivityService userActivityService;
	
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
	
	@GetMapping(path = "/userActivity/getById")
	public UserActivityEntity getUserActivityById(@RequestParam(value = "id")int id){
		
		return userActivityService.getById(id);	
	}
	
	@GetMapping(path = "/userActivity/getByActivityId")
	public  List<UserActivityEntity> getUserActivityByActivityId(@RequestParam(value = "activity_id")int activity_id){
		
		return userActivityService.getByActivityId(activity_id);	
	}
	
	@GetMapping(path = "/userActivity/getByTitle")
	public  List<UserActivityEntity> getByTitle(@RequestParam(value = "title") String title){
		
		return userActivityService.getByTitle(title);	
	}
	
	
}
