package enums;

public enum SpeedEnumDep {
	CAT_A("A",new Double(110)),
	CAT_B("B",new Double(143)),
	CAT_C("C",new Double(176)),
	CAT_D("D",new Double(204)),
	CAT_E("E",new Double(253));
	
	private String uom;
	private Double value;
	
	private SpeedEnumDep(String uom, Double value) {
		this.setUom(uom);
		this.setValue(value);
	}

	public static SpeedEnumDep getByUOM(String uom){
		for(int i = 0;i<SpeedEnumDep.values().length;i++) {
			if(SpeedEnumDep.values()[i].getUom().equals(uom)) {
				return SpeedEnumDep.values()[i];
			}
		}
		return null;
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
