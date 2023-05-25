package holidayplanner.HolidayPlanner.activity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import holidayplanner.HolidayPlanner.common.PasswordServices;
import holidayplanner.HolidayPlanner.user.UserEntity;
import holidayplanner.HolidayPlanner.user.UserRepository;
import holidayplanner.HolidayPlanner.user.WebSecurityConfig;

@Service
public class ActivityService {

	private ActivityRepository activtyRepository;
	//private WebSecurityConfig webSecurityConfig;
	//private RoleService roleService;
	
	@Autowired
	public ActivityService(ActivityRepository activtyRepository/*, WebSecurityConfig webSecurityConfig, RoleService roleService*/) {
		this.activtyRepository = activtyRepository;
		//this.webSecurityConfig = webSecurityConfig;
		/*this.roleService = roleService;*/
	}
	
	public ActivityEntity addActivity(String title, String description) {
		
		if(title.isBlank()){
			return null;
		}
		
		ActivityEntity activity = new ActivityEntity(title, description);
		
		return activtyRepository.saveAndFlush(activity);	
	}
	
	/*public UserEntity login(String username, String password, HttpSession session) {
		
		UserEntity user = userRepository.findUserByUsernameAndPassword(username, PasswordServices.hashMe(password));
		
		if(user != null ) {
			
			session.setAttribute("loggedUser", user);
			
			UserDetails userDetails = webSecurityConfig.userDetailService().loadUserByUsername(username);
			
			if(userDetails != null) {
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),
																				userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				//ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
				
				session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());				
			}	
		}
		
		return user;
	}*/
	
	public ActivityEntity getById(int id){
		return activtyRepository.findById(id);
	}	
	
	public List<ActivityEntity> getAll(){
		return activtyRepository.findAll();
	}	
	
	/*public Collection<SimpleGrantedAuthority>  getRoles(){
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	return authorities;
	}*/
}
