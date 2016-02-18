package pojos;

//import java.util.List;

public class DesignatedPoint {
	
	private String id;
	
	private GmlIdentifier identifier;
	
	private String designator;
	
	private Pos position;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GmlIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(GmlIdentifier identifier) {
		this.identifier = identifier;
	}

	public String getDesignator() {
		return designator;
	}

	public void setDesignator(String designator) {
		this.designator = designator;
	}

	public Pos getPosition() {
		return position;
	}

	public void setPosition(Pos position) {
		this.position = position;
	}
	


}
