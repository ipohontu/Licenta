package pojos;

import java.util.List;

public class LegWrapper {
	
	private List<Leg> legs;
	
	private String type;

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
