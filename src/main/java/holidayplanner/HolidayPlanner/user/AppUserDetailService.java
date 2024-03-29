package holidayplanner.HolidayPlanner.user;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService{

private UserRepository userRepository;

//@Autowired
//public AppUserDetailService() {
//}

	//@Autowired
	public AppUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//System.out.println("username: " + username);
		//System.out.println("userRepository: " + userRepository);
		//System.out.println("userRepository.findByUsername(): " + userRepository.findById(1));
		//UserEntity user = userRepository.findByUsername(username);
		User user = userRepository.findByUsername(username);

		if(user == null) {
			throw new UsernameNotFoundException(
					username + " was slacking....");
		}
		
		Set<RoleEntity> roles = user.getRoles();
		
		return new UserPrincipal(user, roles);
	}
}
