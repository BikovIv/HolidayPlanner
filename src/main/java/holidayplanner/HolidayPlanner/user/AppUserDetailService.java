package holidayplanner.HolidayPlanner.user;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*import holidayplanner.HolidayPlanner.user.RoleEntity;
import holidayplanner.HolidayPlanner.user.UserEntity;
import holidayplanner.HolidayPlanner.user.UserPrincipal;
import holidayplanner.HolidayPlanner.user.UserRepository;*/

@Service
public class AppUserDetailService implements UserDetailsService{

private UserRepository userRepository;
	
	@Autowired
	public AppUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(
					username + " was slacking....");
		}
		
		Set<RoleEntity> roles = user.getRoles();
		
		return new UserPrincipal(user, roles);
	}
	
}
