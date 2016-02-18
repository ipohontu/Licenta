package pojos;

//import java.io.File;
import java.util.List;

public class Leg {
	
	private String id;
	
	private GmlIdentifier identifier;
	
	private String endConditionDesignator;
	
	private String legPath;
	
	private String legTypeARINC;
	
	private Double course;
	
	private String courseType;
	
	private String turnDirection;
	
	private Measurement speedLimit;
	
	private String speedReference;
	
	private Integer bankAngle;
	
	private Measurement length;
	
	private Measurement upperLimitAltitude;
	
	private String upperLimitReference;
	
	private Measurement lowerLimitAltitude;
	
	private String lowerLimitReference;
	
	private String altitudeInterpretation;
	
	private Double verticalAngle;
	
	private String aircraftLandingCategory;
	
	private Reference reference;
	
	private Integer sequenceNo;
	
	private Reference facilityDistanceStart;
	private Reference theAngleIndicationStart;
	private Reference pointChoice_fixDesignatedPointStart;
	
	private Reference facilityDistanceEnd;
	private Reference theAngleIndicationEnd;
	private Reference pointChoice_fixDesignatedPointEnd;
	private Reference pointChoice_navaidSystem;
	
	private List<Pos> posList;

	public String getLegPath() {
		return legPath;
	}

	public void setLegPath(String legPath) {
		this.legPath = legPath;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Measurement getSpeedLimit() {
		return speedLimit;
	}

	public List<Pos> getPosList() {
		return posList;
	}

	public void setPosList(List<Pos> posList) {
		this.posList = posList;
	}

	public void setSpeedLimit(Measurement speedLimit) {
		this.speedLimit = speedLimit;
	}

	public Measurement getLength() {
		return length;
	}

	public void setLength(Measurement length) {
		this.length = length;
	}


	public String getLegTypeARINC() {
		return legTypeARINC;
	}

	public void setLegTypeARINC(String legTypeARINC) {
		this.legTypeARINC = legTypeARINC;
	}

	public Double getCourse() {
		return course;
	}

	public void setCourse(Double course) {
		this.course = course;
	}
	
	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getTurnDirection() {
		return turnDirection;
	}

	public void setTurnDirection(String turnDirection) {
		this.turnDirection = turnDirection;
	}

	public String getSpeedReference() {
		return speedReference;
	}

	public void setSpeedReference(String speedReference) {
		this.speedReference = speedReference;
	}

	public Measurement getLowerLimitAltitude() {
		return lowerLimitAltitude;
	}

	public void setLowerLimitAltitude(Measurement lowerLimitAltitude) {
		this.lowerLimitAltitude = lowerLimitAltitude;
	}

	public String getLowerLimitReference() {
		return lowerLimitReference;
	}

	public void setLowerLimitReference(String lowerLimitReference) {
		this.lowerLimitReference = lowerLimitReference;
	}

	public String getAltitudeInterpretation() {
		return altitudeInterpretation;
	}

	public void setAltitudeInterpretation(String altitudeInterpretation) {
		this.altitudeInterpretation = altitudeInterpretation;
	}

	public Double getVerticalAngle() {
		return verticalAngle;
	}

	public void setVerticalAngle(Double verticalAngle) {
		this.verticalAngle = verticalAngle;
	}



	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	
	public String getEndConditionDesignator() {
		return endConditionDesignator;
	}

	public void setEndConditionDesignator(String endConditionDesignator) {
		this.endConditionDesignator = endConditionDesignator;
	}

	public Integer getBankAngle() {
		return bankAngle;
	}

	public void setBankAngle(Integer bankAngle) {
		this.bankAngle = bankAngle;
	}

	public String getUpperLimitReference() {
		return upperLimitReference;
	}

	public void setUpperLimitReference(String upperLimitReference) {
		this.upperLimitReference = upperLimitReference;
	}

	public GmlIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(GmlIdentifier identifier) {
		this.identifier = identifier;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Measurement getUpperLimitAltitude() {
		return upperLimitAltitude;
	}

	public void setUpperLimitAltitude(Measurement upperLimitAltitude) {
		this.upperLimitAltitude = upperLimitAltitude;
	}

	public String getAircraftLandingCategory() {
		return aircraftLandingCategory;
	}

	public void setAircraftLandingCategory(String aircraftLandingCategory) {
		this.aircraftLandingCategory = aircraftLandingCategory;
	}

	public Reference getFacilityDistanceStart() {
		return facilityDistanceStart;
	}

	public void setFacilityDistanceStart(Reference facilityDistanceStart) {
		this.facilityDistanceStart = facilityDistanceStart;
	}

	public Reference getTheAngleIndicationStart() {
		return theAngleIndicationStart;
	}

	public void setTheAngleIndicationStart(Reference theAngleIndicationStart) {
		this.theAngleIndicationStart = theAngleIndicationStart;
	}

	public Reference getPointChoice_fixDesignatedPointStart() {
		return pointChoice_fixDesignatedPointStart;
	}

	public void setPointChoice_fixDesignatedPointStart(
			Reference pointChoice_fixDesignatedPointStart) {
		this.pointChoice_fixDesignatedPointStart = pointChoice_fixDesignatedPointStart;
	}

	public Reference getFacilityDistanceEnd() {
		return facilityDistanceEnd;
	}

	public void setFacilityDistanceEnd(Reference facilityDistanceEnd) {
		this.facilityDistanceEnd = facilityDistanceEnd;
	}

	public Reference getTheAngleIndicationEnd() {
		return theAngleIndicationEnd;
	}

	public void setTheAngleIndicationEnd(Reference theAngleIndicationEnd) {
		this.theAngleIndicationEnd = theAngleIndicationEnd;
	}

	public Reference getPointChoice_fixDesignatedPointEnd() {
		return pointChoice_fixDesignatedPointEnd;
	}

	public void setPointChoice_fixDesignatedPointEnd(
			Reference pointChoice_fixDesignatedPointEnd) {
		this.pointChoice_fixDesignatedPointEnd = pointChoice_fixDesignatedPointEnd;
	}

	public Reference getPointChoice_navaidSystem() {
		return pointChoice_navaidSystem;
	}

	public void setPointChoice_navaidSystem(Reference pointChoice_navaidSystem) {
		this.pointChoice_navaidSystem = pointChoice_navaidSystem;
	}

	
}
