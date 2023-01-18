package holidayplanner.HolidayPlanner.userActivity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivityEntity, Integer>{
		
		List<UserActivityEntity> findByActivityId(int activity_id);
		
		List<UserActivityEntity> findByTitle(String title);

	/*
	@Query("SELECT ua.id, ua.description, ua.title, a.id, a.description, a.title "
			+ "FROM UserActivityEntity ua inner join ActivityEntity a on ua.activity = a.id "
			+ "	where ua.title like ':title'")// and a.id =:activity_id")
	//SELECT pg FROM Book bk join bk.pages pg WHERE bk.bookId = :bookId a
	 List<UserActivityEntity> findByTitleAndActivityId(String title);
		*/
}