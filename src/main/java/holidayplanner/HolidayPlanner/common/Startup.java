package holidayplanner.HolidayPlanner.common;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import holidayplanner.HolidayPlanner.HolidayPlannerOntology;
import holidayplanner.HolidayPlanner.activity.ActivityEntity;
import holidayplanner.HolidayPlanner.activity.ActivityService;
import holidayplanner.HolidayPlanner.user.RoleEntity;
import holidayplanner.HolidayPlanner.user.RoleService;
import holidayplanner.HolidayPlanner.user.UserService;
import holidayplanner.HolidayPlanner.userActivity.UserActivityService;
import holidayplanner.HolidayPlanner.userTrip.UserTripService;

@Component
public class Startup {
	
	//private UserService userService;
	private RoleService roleService;
	private ActivityService activityService;
	private UserActivityService userActivityService;
	private UserTripService userTripService;
	private HolidayPlannerOntology holidayPlannerOntology =  new HolidayPlannerOntology();
	
	@Autowired
	public Startup(UserService userService, RoleService roleService, ActivityService activityService, UserActivityService userActivityService,
					UserTripService userTripService) {
		//this.userService = userService;
		this.roleService = roleService;
		this.activityService = activityService;
		this.userActivityService = userActivityService;
		this.userTripService = userTripService;
	}	
	
	public void initRoles() {
		//roleService.addRole(new RoleEntity("ROLE_USER"));
		//roleService.addRole(new RoleEntity("ROLE_ADMIN")); 
	}
	
	public void registerDev() {
		String email ="dev1@dev1.dev1"; 
		String username="dev1"; 
		String password="dev1"; 
		String repeatPassword="dev1";
		//Set<RoleEntity> roles = new HashSet(roleService.getAll()/*.stream().map((role) -> role.getCode()).collect(Collectors.toList())*/);
		
		//userService.registerUser(username, password, repeatPassword, email, roles);
	}
	
	public void initActivities() {
		activityService.addActivity("Staycation", "Do you feel that you need a break but don’t have enough coins to pay for a vacation? "
				+ "Staycation has got you. It is a type of vacation where you stay indoors and keep off from any engagement. You sit on the couch and "
				+ "watch your favorite movies and series. It is an ideal vacation for everyone. Whether you are solo, a couple, family, or friends, "
				+ "you can enjoy a staycation. It helps you to relieve yourself from work burnout and refresh again.");
		activityService.addActivity("Sports Vacation","If you love sports such as football, volleyball, golf or rugby, you can travel to watch local or "
				+ "international matches. You can attend the World Cup Football League or other tournaments. Besides, you can sign up for a local "
				+ "competition and enjoy a sports vacation. A sports vacation helps you enjoy your sporting hobby as you relax. Some of the best "
				+ "international sports vacations include Miami, Belize, and Ridgedale in Missouri. It is an exciting moment to grow your talent."); 
		activityService.addActivity("Beach Vacation","It is important to set some days in your life and go on a beach trip. It brings relaxation to your "
				+ "body and mind. Besides, you enjoy sunbathing with the white sand as you swim along the ocean waters."); 
		activityService.addActivity("Cruise Vacation","It is one of the different types of trips on holiday. A cruise vacation happens in a cruise ship "
				+ "which is a floating holiday resort. Most of the cruise ships have restaurants, swimming pools, casinos, Jacuzzi, among other facilities."
				+ " Therefore, there are many fun activities you can enjoy during a cruise vacation."); 
		activityService.addActivity("Camping vacation","Do you love camping in the forest? A camping vacation can be the best. You can combine this type of "
				+ "vacation with a road trip vacation. Enjoy the road adventures during the day and bonfire under the open sky at night."); 
		activityService.addActivity("Road Trip Vacation","Do you love to adventure? Then, a road trip vacation is your perfect match. You can enjoy this"
				+ " type of trip everywhere in the world. Road trips are flexible- you don’t need a timetable or a schedule to get a ride across the world. "); 
		activityService.addActivity("Sightseeing Type of Vacation","You can plan a vacation to experience the nature of places such as a museum, "
				+ "national park, mountain, or a market. On a sightseeing vacation, you enjoy the thrilling beauty of a place. It can be a bit expensive, "
				+ "but with early planning, you can manage it."); 
	}
	 
	@EventListener 
    public void onApplicationEvent(ContextRefreshedEvent event) {
		/*initRoles();
		registerDev();*/
		initActivities();
		//System.out.println("getBy(7, 7)	");
		//System.out.println(userActivityService.getBy("7", 7)	);
		
		System.out.println("userService.registerUser() ************************************************************* *****");
		//System.out.println(holidayPlannerOntology.
		//		getTripByIRI("http://www.semanticweb.org/ivan/ontologies/2023/4/holiday-planner-ontology#PamporovoHoliday01").getTown());
		//System.out.println(userService.registerUser());
		//System.out.println(userService.testMethoda(""));
		//System.out.println(userTripService.getAll().toString());
		//holidayPlannerOntology.getTripByIRI(null)
		//holidayPlannerOntology.getTripByIRI(String tripIRI));
		
		/*userTripService.addUserTrip("Test name", 1, "test town", "trip desript", 
				"trip destination", "trip season", "trip transport", "String trip_type", 
				1, 1, "String tripiri", null);*/
		
    }
}
