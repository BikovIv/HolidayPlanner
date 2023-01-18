package holidayplanner.HolidayPlanner.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
		
		UserEntity findUserByUsernameAndPassword
						(String username, String password);
		
		UserEntity findByUsername(String username);

	}