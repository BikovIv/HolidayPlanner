package holidayplanner.HolidayPlanner.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import holidayplanner.HolidayPlanner.user.RoleEntity;

@RestController
public class ActivityController {
	
	private ActivityService activityService;
	
	@Autowired
	public ActivityController(ActivityService userService) {
		this.activityService = userService;
	}	
	
	@PostMapping(path = "/activity/add")
	public ActivityEntity register( @RequestParam(value = "title") String title, 
								@RequestParam(value = "description") String description/*, 
								@RequestParam(value = "password") String password, 
								@RequestParam(value = "repeatPassword") String repeatPassword*/) {
		
		//Set<ActivityEntity> roles = new HashSet<ActivityEntity>();
		
		return activityService.addActivity(
				title, description/*, repeatPassword, email*/);	
		
	}
	
	@GetMapping(path = "/activity/all")
	public List<ActivityEntity> getAllRoles(){
		
		return activityService.getAll();	
	}
	
	/*@PostMapping(path = "/login")
	public String login(  @RequestParam(value = "username") String username, 
						  @RequestParam(value = "password") String password, 
						   HttpSession session) {
		
		UserEntity user = activityService.login(username, password, session);
		
		if(user != null) {
			return "home.html";
		}else {
			return "error.html";
		}
		
	}
	
	@GetMapping(path = "/loginnn")
	public Collection<SimpleGrantedAuthority> login() { //for test
		
			return activityService.getRoles();//"home.html";
	}
	
	@GetMapping(path = "/whoAmI")
	public ResponseEntity<Integer> loggedUserId(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			return new ResponseEntity<Integer>(user.getId(), HttpStatus.OK);			
		}else {
			return new ResponseEntity<Integer>(-1, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping(path = "/userTest")
	public ResponseEntity<Boolean> kickMeOut(){
		Authentication au = 
				SecurityContextHolder.getContext().getAuthentication();
		
		if( au != null && au.isAuthenticated() &&				
			!(au instanceof AnonymousAuthenticationToken)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
		}else {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);		
		}		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(path = "/all")
	public List<UserEntity> getAllUsers(){
		
		return activityService.getAll();	
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			session.invalidate();
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.I_AM_A_TEAPOT);
		
	}*/
	

}
