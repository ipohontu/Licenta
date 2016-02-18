package pojos;

public class Measurement {
	
	private String uom;
	
	private Double value;
	
	public Measurement(String uom, Double value) {
		this.uom = uom;
		this.value = value;
	}
	
	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
}
