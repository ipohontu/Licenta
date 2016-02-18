package pojos;

public class Navaid {
	
	private String id;
	
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

	private GmlIdentifier identifier;
	
	private String designator;
	
	private Pos position;


}
