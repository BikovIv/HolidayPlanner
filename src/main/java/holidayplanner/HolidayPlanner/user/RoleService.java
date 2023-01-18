package holidayplanner.HolidayPlanner.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	private RoleRepository roleRepository;
	private WebSecurityConfig webSecurityConfig;
	
	@Autowired
	public RoleService(RoleRepository roleRepository, WebSecurityConfig webSecurityConfig) {
		this.roleRepository = roleRepository;
		this.webSecurityConfig = webSecurityConfig;	//??	
	}
	
	public RoleEntity addRole(String code, String description) {
		
		if(code.isBlank() || description.isBlank()) {
			return null;
		}
		
		RoleEntity role = new RoleEntity(code, description);
		
		return roleRepository.saveAndFlush(role);
	}
	
	public void addRole(RoleEntity role) {
		
		if(role == null) {
			return;
		}
		
		//RoleEntity role = new RoleEntity(code, description);
		
		roleRepository.saveAndFlush(role);
	}
	
	public ResponseEntity<Boolean> deleteRole(int id, int user_role_id) {
		
		if(id == 0 || user_role_id == 0) {
			return null;
		}
		
		//TO DO: check by user_role_id if he has permissions to delete roles
		
		Optional<RoleEntity> optionalRole = roleRepository.findById(id);
		
		if(optionalRole.isPresent()) {
			
			RoleEntity role = optionalRole.get();
			
			roleRepository.delete(role);
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);	
	}
	
	public List<RoleEntity> getAll(){
		return roleRepository.findAll();
	}
}
