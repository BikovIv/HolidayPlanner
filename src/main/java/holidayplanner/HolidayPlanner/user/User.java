package holidayplanner.HolidayPlanner.user;
import java.util.Set;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

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
		@JoinTable(name = "account_role",
			joinColumns = @JoinColumn(name="account_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
		private Set<RoleEntity> roles;
	    
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

		public void setRoles(Set<RoleEntity> roles) {
			this.roles = roles;
		}
	    
	    @Override
	    public String toString() {
	        return "User { " +
	                " Username = '" + username + '\'' +
	                ", Email = " + email +
	                '}';
	    }
	

}
