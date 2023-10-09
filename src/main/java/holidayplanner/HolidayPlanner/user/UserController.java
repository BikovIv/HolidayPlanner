package holidayplanner.HolidayPlanner.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import holidayplanner.HolidayPlanner.HolidayPlannerAgent;

@RestController
public class UserController {
	
	private UserService userService;
	//HolidayPlannerAgent myHolidayPlannerAgent;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}	
	
	@PostMapping(path = "/register")
	public User register( @RequestParam(value = "email") String email, 
								@RequestParam(value = "username") String username, 
								@RequestParam(value = "password") String password, 
								@RequestParam(value = "repeatPassword") String repeatPassword) {
		
		Set<RoleEntity> roles = new HashSet<RoleEntity>();
		
		return userService.registerUser(username, password, repeatPassword, email);	
		
	}
	
	@PostMapping(path = "/login")
	public String login(  @RequestParam(value = "username") String username, 
						  @RequestParam(value = "password") String password, 
						   HttpSession session) {
		
		User/*Entity*/ user = userService.login(username, password, session);
		
		System.out.println("session.getAttribute(\"loggedUser\")" + session.getAttribute("loggedUser"));
		System.out.println("session.getAttribute(\"loggedUserId\")" + session.getAttribute("loggedUserId"));
		System.out.println("session.getAttribute(\"userClientAgent\")" + session.getAttribute("userClientAgent"));
		
		if(user != null) {
			return "home.html";
		}else {
			return "error.html";
		}
		
	}
	
	
	
	@GetMapping(path = "/getSessionParameters")
	public int getSessionParameters(HttpServletRequest request, HttpSession session) { 
		
		User user = (User)session.getAttribute("loggedUser");
		
		System.out.println("user" + user);
		//HttpSession session = request.getSession();
		//System.out.println("session" + session.);
		int user_id = (int) session.getAttribute("loggedUserId");
		
			return user_id;
	}
	
	@GetMapping(path = "/loginnn")
	public Collection<SimpleGrantedAuthority> login() { //for test
		
			return userService.getRoles();//"home.html";
	}
	
	@GetMapping(path = "/whoAmI")
	public ResponseEntity<Integer> loggedUserId(HttpSession session){
		
		User user = (User)session.getAttribute("loggedUser");
		
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
	public List<User> getAllUsers(){
		
		return userService.getAll();	
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		User user = (User)session.getAttribute("loggedUser");
		
		if(user != null) {
			session.invalidate();
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.I_AM_A_TEAPOT);
		
	}
	

}
