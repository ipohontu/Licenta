package enums;

public enum MeasurementToMeterperSecondEnum {

	FT_MINtoMPSEnum("FT_MIN",new Double(0.005079)),
	FT_SECtoMPSEnum("FT_SEC",new Double(0.30479)),
	KPHtoMPSEnum("KM_H",new Double(0.2777777)),
	KTtoMPSEnum("KT",new Double(0.514444)),
	M_MINtoMPSEnum("M_MIN",new Double(0.016666)),
	M_SECtoMPSEnum("M_SEC",new Double(1)),
	MACHtoMPSEnum("MACH",new Double(340)),
	MPHtoMPSEnum("MPH",new Double(0.44704));
	
	private String uom;
	private Double value;
	
	private MeasurementToMeterperSecondEnum(String uom, Double value) {
		this.uom=uom;
		this.value=value;
	}
	
	public static MeasurementToMeterperSecondEnum getByUOM(String uom){
		for(int i = 0;i<MeasurementToMeterperSecondEnum.values().length;i++) {
			if(MeasurementToMeterperSecondEnum.values()[i].getUom().equals(uom)) {
				return MeasurementToMeterperSecondEnum.values()[i];
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
