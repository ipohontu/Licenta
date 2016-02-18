package pojos;

public class TerminalPoint {
	
	private PointRef facilityMakeup;
	
	private Reference pointChoice_fixDesignatedPoint;

	
	public TerminalPoint(PointRef facilityMakeup, Reference pointChoice_fixDesignatedPoint) {
		this.facilityMakeup=facilityMakeup;
		this.pointChoice_fixDesignatedPoint=pointChoice_fixDesignatedPoint;
		
	}

	public PointRef getFacilityMakeup() {
		return facilityMakeup;
	}

	public void setFacilityMakeup(PointRef facilityMakeup) {
		this.facilityMakeup = facilityMakeup;
	}

	public Reference getPointChoice_fixDesignatedPoint() {
		return pointChoice_fixDesignatedPoint;
	}

	public void setPointChoice_fixDesignatedPoint(
			Reference pointChoice_fixDesignatedPoint) {
		this.pointChoice_fixDesignatedPoint = pointChoice_fixDesignatedPoint;
	}


}
