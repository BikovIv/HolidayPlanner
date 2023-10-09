package holidayplanner.HolidayPlanner.userActivity;

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

import holidayplanner.HolidayPlanner.activity.ActivityEntity;
import holidayplanner.HolidayPlanner.user.User;
//import holidayplanner.HolidayPlanner.user.UserEntity;

@Entity
//@Table(name = "userActivity")
public class UserActivityEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/*@OneToOne(optional = false, mappedBy="")
	@JoinColumn(name = "activity_type_id", referencedColumnName = "id")
	//@Column(name="activity_type_id",  nullable = false)
	private ActivityEntity activityEntity;*/
	
	 // On Customer class:

   /* @OneToOne(optional=false)
    @JoinColumn(
    	name="CUSTREC_ID", unique=true, nullable=false, updatable=false)
    public CustomerRecord getCustomerRecord() { return customerRecord; }
*/
    // On CustomerRecord class:

   /* @OneToOne(optional=false, mappedBy="customerRecord")
    public Customer getCustomer() { return customer; }*/

	
	/*@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
	@Column(name="user_id", nullable = false)
	private int userId;*/
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(length = 100)
	private String description;
	
	@Column(length = 500, nullable = false)
	private String imagePath;
	
	//@ManyToOne
	//@JoinColumn(name = "activity", referencedColumnName = "activities")
	 @JsonProperty("province_id") //to get activity table data in json :@
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
	@JsonIgnore
	private ActivityEntity activity;
	
	/*@ManyToMany
	@JoinTable(name = "account_role",
		joinColumns = @JoinColumn(name="account_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;*/
	
	// @JsonProperty("province_id") //to get activity table data in json :@
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
	 
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	//private List<CommentEntity> comments;

	public UserActivityEntity() {	}
	
	/*public UserActivityEntity(String title, String description) {
		this.title = title;
		this.description = description;
	}*/
	
	public UserActivityEntity(String title, String description, String imagePath, ActivityEntity activity, User user) {
		this.title = title;
		this.description = description;
		this.imagePath = imagePath;
		this.activity = activity;
		this.user = user;
	}
	
	public UserActivityEntity(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setDescription(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public ActivityEntity getActivity() {
		return activity;
	}

	public void setActivity(ActivityEntity activity) {
		this.activity = activity;
	}	
	
}
