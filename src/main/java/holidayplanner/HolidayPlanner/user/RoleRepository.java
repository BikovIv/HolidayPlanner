package holidayplanner.HolidayPlanner.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{

	RoleEntity findRoleByCode(String code);

	RoleEntity findRoleByDescription(String desctiption);
	
}
