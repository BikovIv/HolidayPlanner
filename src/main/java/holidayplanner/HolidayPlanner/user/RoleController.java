package holidayplanner.HolidayPlanner.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
	
	private RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}	
	
	@PostMapping(path = "/role/add")
	public RoleEntity addRole( @RequestParam(value = "code") String code, 
								@RequestParam(value = "description") String description){
		
		return roleService.addRole(code, description);	
	}
	
	@GetMapping(path = "/role/all")
	public List<RoleEntity> getAllRoles(){
		
		return roleService.getAll();	
	}

}
