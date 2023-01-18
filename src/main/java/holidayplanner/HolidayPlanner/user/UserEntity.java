package holidayplanner.HolidayPlanner.user;

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
import javax.persistence.Table;

//import holidayplanner.HolidayPlanner.comment.CommentEntity;
import holidayplanner.HolidayPlanner.user.RoleEntity;
import holidayplanner.HolidayPlanner.userActivity.UserActivityEntity;

@Entity
@Table(name = "user")
public class UserEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = true, length = 50)
	private String firstName;
	
	@Column(nullable = true, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 75, unique = true)
	private String username;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false, length = 150)
	private String password;
	
	@Column(name = "avatar_location", length = 200)
	private String avatarLocation;
	
	/*@Column(name = "developer", nullable = false)
	private boolean isDeveloper = false;*/
	
	@ManyToMany
	@JoinTable(name = "account_role",
		joinColumns = @JoinColumn(name="account_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserActivityEntity> userActivities;
	
	public UserEntity() {	}
	
	public UserEntity(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	/*public UserEntity(String username, String password, String email, boolean isDeveloper) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isDeveloper = isDeveloper;
	}*/
	
	public UserEntity(String username, String password, String email, Set<RoleEntity> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarLocation() {
		return avatarLocation;
	}

	public void setAvatarLocation(String avatar) {
		this.avatarLocation = avatar;
	}
	
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
	
	/*public boolean getIsDeveloper() {
		return isDeveloper;
	}

	public void setIsDeveloper(boolean isDeveloper) {
		this.isDeveloper = isDeveloper;
	}*/
	
	/*public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}	*/
}
