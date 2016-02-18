package pojos;

public class PointRef {
	private Reference theAngleIndication;
	private Reference facilityDistance;

	public PointRef(Reference theAngleIndication , Reference facilityDistance) {
		this.facilityDistance = facilityDistance;
		this.theAngleIndication= theAngleIndication;
	}

	public Reference getFacilityDistance() {
		return facilityDistance;
	}

	public void setFacilityDistance(Reference facilityDistance) {
		this.facilityDistance = facilityDistance;
	}

	public Reference getTheAngleIndication() {
		return theAngleIndication;
	}

	public void setTheAngleIndication(Reference theAngleIndication) {
		this.theAngleIndication = theAngleIndication;
	}

}
