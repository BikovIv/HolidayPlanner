package holidayplanner.HolidayPlanner;

import java.io.Serializable;

public class Town implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String townType;
	
	public Town() {	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTownType() {
		return townType;
	}

	public void setTownType(String townType) {
		this.townType = townType;
	}
	
	@Override
	public String toString() {
		return "Town [id=" + id + ", name=" + name + ", type=" + townType +"]";
	}	

}
