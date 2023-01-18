package holidayplanner.HolidayPlanner.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import holidayplanner.HolidayPlanner.user.UserEntity;
/*import holidayplanner.HolidayPlanner.user.UserService;*/

@RestController
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}	
	
	@PostMapping(path = "/register")
	public UserEntity register( @RequestParam(value = "email") String email, 
								@RequestParam(value = "username") String username, 
								@RequestParam(value = "password") String password, 
								@RequestParam(value = "repeatPassword") String repeatPassword) {
		
		Set<RoleEntity> roles = new HashSet<RoleEntity>();
		
		return userService.registerUser(
				username, password, repeatPassword, email);	
		
	}
	
	@PostMapping(path = "/login")
	public String login(  @RequestParam(value = "username") String username, 
						  @RequestParam(value = "password") String password, 
						   HttpSession session) {
		
		UserEntity user = userService.login(username, password, session);
		
		if(user != null) {
			return "home.html";
		}else {
			return "error.html";
		}
		
	}
	
	@GetMapping(path = "/loginnn")
	public Collection<SimpleGrantedAuthority> login() { //for test
		
			return userService.getRoles();//"home.html";
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
		
		return userService.getAll();	
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			session.invalidate();
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.I_AM_A_TEAPOT);
		
	}
	

}
