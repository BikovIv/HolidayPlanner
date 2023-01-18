package holidayplanner.HolidayPlanner.activity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import holidayplanner.HolidayPlanner.comment.CommentEntity;
import holidayplanner.HolidayPlanner.user.RoleEntity;
import holidayplanner.HolidayPlanner.userActivity.UserActivityEntity;

@Entity
@Table(name = "activity")
public class ActivityEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//@Column(name="activity_type",  nullable = false)
	//private int activitType;
	
	/*@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
	@Column(name="user_id", nullable = false)
	private int userId;*/
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(length = 500)
	private String description;
	
	/*@ManyToMany
	@JoinTable(name = "account_role",
		joinColumns = @JoinColumn(name="account_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;*/
	
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	//private List<CommentEntity> comments;
	
	//@OneToMany //the activity table must have a foreign key column "id" that references the userActivity table's "activity" column.
	//@JoinColumn(name = "id", referencedColumnName = "activity") 
	@OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
	private List<UserActivityEntity> activities;
	
	public ActivityEntity() {	}
	
	public ActivityEntity(String title, String description) {
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

	public void setDescription(String description) {
		this.description = description;
	}
}
