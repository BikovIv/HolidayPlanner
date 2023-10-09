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
import holidayplanner.HolidayPlanner.user.User;
import holidayplanner.HolidayPlanner.user.UserRepository;
import holidayplanner.HolidayPlanner.userActivity.UserActivityEntity;
//import holidayplanner.HolidayPlanner.user.UserEntity;

@Service
public class UserTripService {

	private UserTripRepository userTripRepository;
	private UserRepository userRepository;
	private ActivityRepository activtyRepository;
	//private FileService storageService;
	//private WebSecurityConfig webSecurityConfig;
	//private RoleService roleService;
	
	private HolidayPlannerAgent a = new HolidayPlannerAgent();
	
	@Autowired
	public UserTripService(UserTripRepository userTripRepository, UserRepository userRepository,
								ActivityRepository activtyRepository/*, WebSecurityConfig webSecurityConfig, RoleService roleService*/
								 ) {
		this.userTripRepository = userTripRepository;
		this.userRepository = userRepository;
		this.activtyRepository = activtyRepository;
		//this.storageService = storageService;
		//this.webSecurityConfig = webSecurityConfig;
		/*this.roleService = roleService;*/
	}
	
	//id	name	place_to_stay_id	town	trip_desctiption	trip_destination	trip_season	trip_transportation	trip_type	user_id	activity_id	tripiri
	public UserTripEntity addUserTrip(String name, int place_to_stay_id, String town, String trip_desctiption, 
											String trip_destination, String trip_season, String trip_transportation, String trip_type, 
											int user_id, int activity_id, String tripiri, String image/*, MultipartFile file*/) {
		//System.out.println(user);
		//String imageName;
		//String imagePath = "";
		
		if(name.isBlank() /*|| town.isBlank() || trip_type.isBlank() || tripiri.isBlank() || user_id == 0 || activity_id == 0*/){
			return null;
		}
		
		User user = new User();
		user = userRepository.findById(user_id);
		
		ActivityEntity activity = new ActivityEntity();
		activity = activtyRepository.getById(activity_id);
		
		UserTripEntity userTripInserted = new UserTripEntity();	
		
		//if(user_activity_id > 0) {
			//userActivityInserted = this.getById(user_activity_id);
			
		userTripInserted.setName(name);
		userTripInserted.setPlace_to_stay_id(place_to_stay_id);
		userTripInserted.setTown(town);
		userTripInserted.setTrip_desctiption(trip_desctiption);
		userTripInserted.setTrip_destination(trip_destination);
		userTripInserted.setTrip_season(trip_season);
		userTripInserted.setTrip_transportation(trip_transportation);
		userTripInserted.setTrip_type(trip_type);
		userTripInserted.setUser(user);
		userTripInserted.setActivity(activity);
		userTripInserted.setTripIRI(tripiri);
		userTripInserted.setImage(image);
		//userTripInserted.setUser(null);
		
			//imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
			//storageService.save(file, imageName);
			
			//imagePath = "../uploads" + "/" + imageName;

			//if(imagePath.isBlank()) {
			//	imagePath += "../assets/img/logo.png";
			//}
			
			//userActivityInserted.setImagePath(imagePath);
			System.out.println(userTripInserted);
			return userTripRepository.saveAndFlush(userTripInserted);
		//}
		
		/*UserActivityEntity userActivity = new UserActivityEntity(name, description, imagePath, activity, user);
	
		userActivityInserted = userActivtyRepository.saveAndFlush(userActivity);
		
		imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
		storageService.save(file, imageName);
		
		imagePath = "../uploads" + "/" + imageName;

		if(imagePath.isBlank()) {
			imagePath += "../assets/img/logo.png";
		}
		
		userActivity.setImagePath(imagePath);
		
		return userActivtyRepository.saveAndFlush(userActivity);	*/
	}
	
	public UserTripEntity editUserTrip(int user_trip_id, String name, int place_to_stay_id, String town, String trip_desctiption, 
			String trip_destination, String trip_season, String trip_transportation, String trip_type, 
			int user_id, int activity_id, String tripiri, String imageName /*MultipartFile file*/) {
		//System.out.println(user);
		//String imageName;
		//String imagePath = "";
		
		if(name.isBlank() /*|| town.isBlank() || trip_type.isBlank() || tripiri.isBlank() || user_id == 0 || activity_id == 0*/){
		return null;
		}
		
		User user = new User();
		user = userRepository.findById(user_id);
		
		ActivityEntity activity = new ActivityEntity();
		activity = activtyRepository.getById(activity_id);
		
		UserTripEntity userTripInserted = new UserTripEntity();	
		
		//if(user_activity_id > 0) {
		//userActivityInserted = this.getById(user_activity_id);
		userTripInserted.setId(user_trip_id);
		userTripInserted.setName(name);
		userTripInserted.setPlace_to_stay_id(place_to_stay_id);
		userTripInserted.setTown(town);
		userTripInserted.setTrip_desctiption(trip_desctiption);
		userTripInserted.setTrip_destination(trip_destination);
		userTripInserted.setTrip_season(trip_season);
		userTripInserted.setTrip_transportation(trip_transportation);
		userTripInserted.setTrip_type(trip_type);
		userTripInserted.setUser(user);
		userTripInserted.setActivity(activity);
		userTripInserted.setTripIRI(tripiri);
		userTripInserted.setImage(imageName);
		//userTripInserted.setUser(null);
		
		//imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
		//storageService.save(file, imageName);
		
		//imagePath = "../uploads" + "/" + imageName;
		
		//if(imagePath.isBlank()) {
		//	imagePath += "../assets/img/logo.png";
		//}
		
		//userActivityInserted.setImagePath(imagePath);
		System.out.println(userTripInserted);
		return userTripRepository.saveAndFlush(userTripInserted);
		//}
		
		/*UserActivityEntity userActivity = new UserActivityEntity(name, description, imagePath, activity, user);
		
		userActivityInserted = userActivtyRepository.saveAndFlush(userActivity);
		
		imageName = Integer.toString(userActivityInserted.getId()) + "_" + file.getOriginalFilename();
		storageService.save(file, imageName);
		
		imagePath = "../uploads" + "/" + imageName;
		
		if(imagePath.isBlank()) {
		imagePath += "../assets/img/logo.png";
		}
		
		userActivity.setImagePath(imagePath);
		
		return userActivtyRepository.saveAndFlush(userActivity);	*/
		}
	
	/*
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
	
	public UserTripEntity findById(int id){	
		return userTripRepository.findById(id);
	}	
	
	public List<UserTripEntity> findByUserId(int user_id){	
		return userTripRepository.findByUserId(user_id);
	}	
}
