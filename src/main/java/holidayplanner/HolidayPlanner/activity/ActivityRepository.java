package holidayplanner.HolidayPlanner.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import holidayplanner.HolidayPlanner.userActivity.UserActivityEntity;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer>{
		
		ActivityEntity findById(int id);
	
		/*ActivityEntity findUserByUsernameAndPassword
						(String username, String password);
		
		ActivityEntity findByUsername(String username);
*/
	}