package holidayplanner.HolidayPlanner.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer>{
		
		/*ActivityEntity findUserByUsernameAndPassword
						(String username, String password);
		
		ActivityEntity findByUsername(String username);
*/
	}