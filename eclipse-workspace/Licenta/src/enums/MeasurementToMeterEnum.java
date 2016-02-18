package enums;

public enum MeasurementToMeterEnum {

	NMtoMEnum("NM",new Double(1852)),
	FTtoMEnum("FT",new Double(0.3048)),
	CMtoMEnum("CM",new Double(0.01)),
	KMtoMEnum("KM",new Double(1000)),
	MItoMEnum("MI",new Double(1609.344)),
	MtoMEnum("M",new Double(1));
	
	private String uom;
	private Double value;
	/*
	private List<MeasurementEnum> allValues = */
	
	private MeasurementToMeterEnum(String uom, Double value) {
		this.uom = uom;
		this.value = value;
	}
	
	public static MeasurementToMeterEnum getByUOM(String uom){
		for(int i = 0;i<MeasurementToMeterEnum.values().length;i++) {
			if(MeasurementToMeterEnum.values()[i].getUom().equals(uom)) {
				return MeasurementToMeterEnum.values()[i];
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
