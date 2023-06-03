package holidayplanner.HolidayPlanner;

import java.io.Serializable;

public class PlaceToStayType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public PlaceToStayType() {	

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

	
	@Override
	public String toString() {
		return id.substring(id.indexOf('#') + 1).replace(">", "");
	}	
	
	/*private String getClassFriendlyName(String text) {
		
		String label = text.toString();
		label = label.substring(label.indexOf('#') + 1).replace(">", "");
		
		return label;		
	}*/

}