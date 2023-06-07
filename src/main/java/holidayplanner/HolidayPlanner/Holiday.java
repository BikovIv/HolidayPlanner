package holidayplanner.HolidayPlanner;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.semanticweb.owlapi.model.IRI;

public class Holiday implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private IRI iri;
	public IRI getIri() {
		return iri;
	}

	public void setIri(IRI iri) {
		this.iri = iri;
	}

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

