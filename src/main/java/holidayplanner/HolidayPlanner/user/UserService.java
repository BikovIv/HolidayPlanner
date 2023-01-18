package holidayplanner.HolidayPlanner.user;

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
public class UserService {

	private UserRepository userRepository;
	private WebSecurityConfig webSecurityConfig;
	private RoleService roleService;
	
	@Autowired
	public UserService(UserRepository userRepository, WebSecurityConfig webSecurityConfig, RoleService roleService) {
		this.userRepository = userRepository;
		this.webSecurityConfig = webSecurityConfig;
		this.roleService = roleService;
	}
	
	public UserEntity registerUser(String username, String password, String repeatPassword, String email) {
		
		if(username.isBlank()  || 
			email.isBlank() 	||
			password.isBlank()  || 
			!password.equals(repeatPassword)) {
			return null;
		}
		
		UserEntity user = new UserEntity(username, PasswordServices.hashMe(password), email);
		
		return userRepository.saveAndFlush(user);	
	}
	
	/*public  UserEntity registerUser(String username, String password, String repeatPassword, String email, boolean isDeveloper) {
		
		if(		username.isBlank()  || 
				email.isBlank() 	||
				password.isBlank()  || 
				!password.equals(repeatPassword)) {
			return null;
		}
		
		UserEntity user = new UserEntity(username, PasswordServices.hashMe(password), email, isDeveloper);
		
		return userRepository.saveAndFlush(user);	
	}*/
	
	public  UserEntity registerUser(String username, String password, String repeatPassword, String email, Set<RoleEntity> roles) {
			
		if(username.isBlank()  || 
			email.isBlank() 	||
			password.isBlank()  || 
			!password.equals(repeatPassword)) {
			return null;
		}
		
		/*if(roles.isEmpty()) {	
			RoleEntity r1 = roleService.f
			
			roles.add(r1);
		}*/
			
		UserEntity user = new UserEntity(username, PasswordServices.hashMe(password), email, roles);
		
		return userRepository.saveAndFlush(user);	
	}
	
	public UserEntity login(String username, String password, HttpSession session) {
		
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
	}
	
	public List<UserEntity> getAll(){
		return userRepository.findAll();
	}	
	
	public Collection<SimpleGrantedAuthority>  getRoles(){
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	return authorities;
	}
}
