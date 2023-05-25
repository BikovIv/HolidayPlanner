package holidayplanner.HolidayPlanner.userActivity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import holidayplanner.HolidayPlanner.activity.ActivityEntity;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivityEntity, Integer>{
		
		List<UserActivityEntity> findByActivityId(int activity_id);
		
		List<UserActivityEntity> findByTitle(String title);

	@Query("SELECT ua "//ua.id, ua.description, ua.title, a.id, a.description, a.title "
			+ "FROM UserActivityEntity ua inner join ActivityEntity a on ua.activity = a.id " 
			+ "	where ua.title like %:title% and ua.activity =:activity")
			//+ "	where (:title = 'null' or ua.title like %:title%) and (:activity = 'null' or ua.activity =:activity")
	 List<UserActivityEntity> findByTitleAndActivityId(String title, ActivityEntity activity);  
		 
}