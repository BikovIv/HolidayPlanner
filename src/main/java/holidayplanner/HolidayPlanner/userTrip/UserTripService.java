package holidayplanner.HolidayPlanner.userTrip;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import holidayplanner.HolidayPlanner.HolidayPlannerAgent;
import holidayplanner.HolidayPlanner.activity.ActivityEntity;
import holidayplanner.HolidayPlanner.activity.ActivityRepository;
import holidayplanner.HolidayPlanner.file.FileService;
import holidayplanner.HolidayPlanner.user.UserEntity;

@Service
public class UserTripService {

	private UserTripRepository userTripRepository;
	//private ActivityRepository activtyRepository;
	//private FileService storageService;
	//private WebSecurityConfig webSecurityConfig;
	//private RoleService roleService;
	
	private HolidayPlannerAgent a = new HolidayPlannerAgent();
	
	@Autowired
	public UserTripService(UserTripRepository userTripRepository/*, WebSecurityConfig webSecurityConfig, RoleService roleService*/
								 ) {
		this.userTripRepository = userTripRepository;
		//this.activtyRepository = activtyRepository;
		//this.storageService = storageService;
		//this.webSecurityConfig = webSecurityConfig;
		/*this.roleService = roleService;*/
	}
	
	/*public UserTripEntity addUserActivity(String title, String description,  int activity_id, MultipartFile file, 
												int user_activity_id, UserEntity user) {
		System.out.println(user);
		String imageName;
		String imagePath = "";
		
		if(title.isBlank()){
			return null;
		}
		
		ActivityEntity activity = new ActivityEntity();
		activity = activtyRepository.getById(activity_id);
		
		UserActivityEntity userActivityInserted = new UserActivityEntity();	
		
		if(user_activity_id > 0) {
			userActivityInserted = this.getById(user_activity_id);
			
			userActivityInserted.setTitle(title);
			userActivityInserted.setDescription(description);
			userActivityInserted.setActivity(activity);
			
			imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
			storageService.save(file, imageName);
			
			imagePath = "../uploads" + "/" + imageName;

			if(imagePath.isBlank()) {
				imagePath += "../assets/img/logo.png";
			}
			
			userActivityInserted.setImagePath(imagePath);
			
			return userActivtyRepository.saveAndFlush(userActivityInserted);
		}
		
		UserActivityEntity userActivity = new UserActivityEntity(title, description, imagePath, activity, user);
	
		userActivityInserted = userActivtyRepository.saveAndFlush(userActivity);
		
		imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
		storageService.save(file, imageName);
		
		imagePath = "../uploads" + "/" + imageName;

		if(imagePath.isBlank()) {
			imagePath += "../assets/img/logo.png";
		}
		
		userActivity.setImagePath(imagePath);
		
		return userActivtyRepository.saveAndFlush(userActivity);	
	}
	
	
	public ResponseEntity<Boolean> deleteUserActivity(int id) {
		
		Optional<UserActivityEntity> optinalUserActivity = userActivtyRepository.findById(id);
		
		if(optinalUserActivity.isPresent()) {
			
			UserActivityEntity activity = optinalUserActivity.get();
			
			userActivtyRepository.delete(activity);
	
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}		
		
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	}
	

	public UserActivityEntity getById(int id){	
		
		Optional<UserActivityEntity> optinalUserActivity = userActivtyRepository.findById(id);
		
		if(optinalUserActivity.isPresent()) {
			
			UserActivityEntity activity = optinalUserActivity.get();
			
	
			return activity;
			
		}		
		
		return null;
	}	
	
	public List<UserActivityEntity> getBy(int activity_id){	
		
		List<UserActivityEntity> optinalUserActivity = userActivtyRepository.findByActivityId(activity_id);	
		
		return optinalUserActivity;
	}	
	
	public List<UserActivityEntity> getBy(String title){	
		
		List<UserActivityEntity> optinalUserActivity = userActivtyRepository.findByTitle(title);	
		
		return optinalUserActivity;
	}	
	
	public List<UserActivityEntity> getBy(String title, int activity_id){	
		
		ActivityEntity activity = new ActivityEntity();
		activity = activtyRepository.findById(activity_id);
		
		List<UserActivityEntity> optinalUserActivity = userActivtyRepository.findByTitleAndActivityId(title, activity);	
		
		return optinalUserActivity;
	}	*/
	
	public List<UserTripEntity> getAll(){	
		return userTripRepository.findAll();
	}	
}
