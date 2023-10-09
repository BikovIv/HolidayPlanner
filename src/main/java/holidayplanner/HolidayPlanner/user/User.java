package holidayplanner.HolidayPlanner.user;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import holidayplanner.HolidayPlanner.userTrip.UserTripEntity;

@Entity
@Table(name = "users")
public class User {
	
	    @Id
		@Column(name = "id", nullable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    
	    @Column(name = "username", nullable = false)
	    private String username;
	    
	    @Column(name = "pass", nullable = false)
	    private String pass;
	    
	    @Column(name = "email", nullable = false)
	    private String email;
	    
	    @ManyToMany
		@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
		private Set<RoleEntity> roles;
	    
	    @JsonSerialize()
	    @OneToMany(mappedBy = "user")
	    private List<UserTripEntity> trips;
	    
	    //@Autowired
	    public User() {
	    }
	    
	    @Autowired
	    public User(String username, String pass, String emal) {
	        this.username = username;
	        this.pass = pass;
	        this.email = emal;
	    }
	    
	    public int getId() {
	        return id;
	    }
	    
	    public void setId(int id) {
	        this.id = id;
	    }
	    
	    public String getUsername() {
	        return username;
	    }
	    
	    public void setUsername(String username) {
	        this.username = username;
	    }
	    
	    public String getPass() {
	        return pass;
	    }
	    
	    public void setPass(String pass) {
	        this.pass = pass;
	    }
	    
	    public String getEmail() {
	        return email;
	    }
	    
	    public void setEmail(String email) {
	        this.email = email;
	    }
	    
	    public Set<RoleEntity> getRoles() {
			return roles;
		}

		public void setTrips(Set<UserTripEntity> trips) {
			this.trips = (List<UserTripEntity>) trips;
		}
		
		public Set<UserTripEntity> getTrips() {
			return (Set<UserTripEntity>) trips;
		}

		public void setRoles(Set<RoleEntity> roles) {
			this.roles = roles;
		}
	    
	    @Override
	    public String toString() {
	        return "User { " +
	        		" UserID = '" + id + '\'' +
	                " Username = '" + username + '\'' +
	                ", Email = " + email +
	                '}';
	    }
	

}
