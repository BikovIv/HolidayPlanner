package holidayplanner.HolidayPlanner;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.semanticweb.owlapi.model.IRI;

public class Holiday implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String iri;
	
	public String getIri() {
		return iri;
	}

	public void setIri(String iri) {
		this.iri = iri;
	}

	private String id;
	private String name;
	private String town;
	private String placeToStay;
	private String type;
	private String destination;
	private String season;
	private String transportation;
	private String description;
	
	public Holiday() { //��������� �� � �� �� ���� �� ������� ������ � json 
		
	}
	
	public Holiday(String iri, String name, String town, String placeToStay, String type, String destination, String season, String transportation) {
		this.iri = iri;
		this.name = name;
		this.town = town;
		this.placeToStay = placeToStay;
		this.type = type;
		this.destination = destination;
		this.season = season;
		this.transportation = transportation;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPlaceToStay() {
		return placeToStay;
	}

	public void setPlaceToStay(String placeToStay) {
		this.placeToStay = placeToStay;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//@Override
	//public String toString() {
	//	return "Holiday [id=" + id + ", town=" + town + "]";
	//}	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Holiday other = (Holiday) o;
        return id == other.id && Objects.equals(id, other.id);
    }
}

