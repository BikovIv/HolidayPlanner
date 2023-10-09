package holidayplanner.HolidayPlanner.userTrip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import holidayplanner.HolidayPlanner.activity.ActivityEntity;

@Repository
public interface UserTripRepository extends JpaRepository<UserTripEntity, Integer>{
		
	UserTripEntity findById(int id);
	List<UserTripEntity> findByUserId(int user_id);
	List<UserTripEntity> findAll();

	/*@Query("SELECT ua "//ua.id, ua.description, ua.title, a.id, a.description, a.title "
			+ "FROM UserActivityEntity ua inner join ActivityEntity a on ua.activity = a.id " 
			+ "	where ua.title like %:title% and ua.activity =:activity")
			//+ "	where (:title = 'null' or ua.title like %:title%) and (:activity = 'null' or ua.activity =:activity")
	 List<UserTripEntity> findByTitleAndActivityId(String title, ActivityEntity activity);  */
		 
}