package holidayplanner.HolidayPlanner.userTrip;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import holidayplanner.HolidayPlanner.activity.ActivityEntity;
import holidayplanner.HolidayPlanner.user.User;


@Entity
@Table(name = "booked_trips")
public class UserTripEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 100, nullable = true)
	private String tripIRI;
	
	//@Column(nullable = true)
	//private boolean shared;
	
	//@Column(nullable = true)
	//private int rated; 
	
	@Column(nullable = true)
	private String image;
		
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getTripIRI() {
		return tripIRI;
	}

	public void setTripIRI(String tripIRI) {
		this.tripIRI = tripIRI;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ActivityEntity getActivity() {
		return activity;
	}

	public void setActivity(ActivityEntity activity) {
		this.activity = activity;
	}

	
	/* to get joined data in json*/
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	/* to get joined data in json*/
    private User user;
	
	@Column(length = 100, nullable = true)
	private String name;
	
	@Column(length = 100, nullable = true)
	private String town;
	@Column(nullable = true)
	private int place_to_stay_id;
	@Column(length = 100, nullable = true)
	private String trip_type;
	@Column(length = 100, nullable = true)
	private String trip_destination;
	@Column(length = 100, nullable = true)
	private String trip_season;
	@Column(length = 100, nullable = true)
	private String trip_transportation;
	@Column(length = 2000, nullable = true)
	private String trip_desctiption;
	
	@JsonProperty("province_id") //to get activity table data in json :@
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
	@JsonIgnore
	private ActivityEntity activity;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getPlace_to_stay_id() {
		return place_to_stay_id;
	}

	public void setPlace_to_stay_id(int place_to_stay_id) {
		this.place_to_stay_id = place_to_stay_id;
	}

	public String getTrip_type() {
		return trip_type;
	}

	public void setTrip_type(String trip_type) {
		this.trip_type = trip_type;
	}

	public String getTrip_destination() {
		return trip_destination;
	}

	public void setTrip_destination(String trip_destination) {
		this.trip_destination = trip_destination;
	}

	public String getTrip_season() {
		return trip_season;
	}

	public void setTrip_season(String trip_season) {
		this.trip_season = trip_season;
	}

	public String getTrip_transportation() {
		return trip_transportation;
	}

	public void setTrip_transportation(String trip_transportation) {
		this.trip_transportation = trip_transportation;
	}

	public String getTrip_desctiption() {
		return trip_desctiption;
	}

	public void setTrip_desctiption(String trip_desctiption) {
		this.trip_desctiption = trip_desctiption;
	}

	/*//THIS WORKS
	@JsonProperty("province_id") //to get activity table data in json :@
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
	@JsonIgnore
	private ActivityEntity activity;*/
	
	/*@ManyToMany
	@JoinTable(name = "account_role",
		joinColumns = @JoinColumn(name="account_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;*/
	 
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	//private List<CommentEntity> comments;

	public UserTripEntity() {	}
	
	public UserTripEntity(String trip_id, int user_id, String name, String town, int place_to_stay_id, String trip_type,
							String trip_destination, String trip_season, String trip_transportation, String trip_desctiption) {
		//this.trip_id = trip_id;
		//this.user_id = user_id;
		this.name = name;
		this.town = town;
		this.place_to_stay_id = place_to_stay_id;
		this.trip_type = trip_type;
		this.trip_destination = trip_destination;
		this.trip_season = trip_season;
		this.trip_transportation = trip_transportation;
		this.trip_desctiption = trip_desctiption;
	}
}