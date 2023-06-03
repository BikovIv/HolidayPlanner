package holidayplanner.HolidayPlanner;

import java.io.Serializable;
import java.util.List;

public class Holiday implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String town;
	//private Integer price;
	
	public Holiday() { //��������� �� � �� �� ���� �� ������� ������ � json 
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	//@Override
	//public String toString() {
	//	return "Holiday [id=" + id + ", town=" + town + "]";
	//}	
}

