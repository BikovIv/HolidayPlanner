package holidayplanner.HolidayPlanner.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "role")
public class RoleEntity implements Serializable {

	@Id
	@Column(name ="role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="code", unique = true, nullable = false)
	private String code;
	
	@Column(name ="description")
	private String description;
	
	public RoleEntity() {}
	
	public RoleEntity(String code) {
		this.code = code;
	}
	
	public RoleEntity(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
