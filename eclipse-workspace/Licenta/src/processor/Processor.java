package processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pojos.Approach;
import pojos.DesignatedPoint;
import pojos.Leg;
import pojos.Navaid;
import pojos.Pos;
import pojos.Reference;
import pojos.StandardInstrumentArrival;
import pojos.StandardInstrumentDeparture;
import utils.Constants;
import utils.Utils;

public class Processor {

	private File xmlInput;
	private File xmlOutput;

	private List<Leg> arrivalLegs;
	private List<Leg> departureLegs;
	private List<Leg> initialLegs;
	private List<Leg> intermediateLegs;
	private List<Leg> finalLegs;
	private List<DesignatedPoint> designatedPoints;
	private List<Navaid> navaidPoints;
	private List<StandardInstrumentDeparture> sidList;
	private List<StandardInstrumentArrival> starList;
	private List<Approach> iapList;
	private List<Integer> seqNo;
	private List<Reference> theSegment;
	private static Double deltaT = new Double(0.01);

	Integer time = 0;
	Double lat0 = 56.90645; // START point
	Double lng0 = 23.9683555555556; // START point
	Document doc;

	Double lat1 = 56.857694867342; // TURN point
	Double lng1 = 23.9602874093629; // TURN point

	Double g = 9.81;

	Integer gmlId = 10000;

	public Processor(File inputXml, File outputXml) {
		this.xmlInput = inputXml;
		this.xmlOutput = outputXml;
	}

	public void loadXml() throws Exception {
		System.out.println("loading xml.....");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		this.doc = dBuilder.parse(xmlInput);
		Element rootElement = (Element) doc.getFirstChild();
		navaidPoints = Utils.parseNAVAIDFromList(
				rootElement.getElementsByTagName("aixm-5.1:Navaid"),
				rootElement);
		designatedPoints = Utils.parseDPFromList(
				rootElement.getElementsByTagName("aixm-5.1:DesignatedPoint"),
				rootElement);
		iapList = Utils.parseIAPFromList(rootElement
				.getElementsByTagName("aixm-5.1:InstrumentApproachProcedure"),
				rootElement);
		starList = Utils.parseSTARFromList(rootElement
				.getElementsByTagName("aixm-5.1:StandardInstrumentArrival"),
				rootElement);

		sidList = Utils.parseSIDFromList(rootElement
				.getElementsByTagName("aixm-5.1:StandardInstrumentDeparture"),
				rootElement);
		System.out.println("sid size" + sidList.size());
		System.out.println(" ");
		arrivalLegs = Utils.parseArrivalLegFromList(
				rootElement.getElementsByTagName("aixm-5.1:ArrivalLeg"),
				rootElement);
		departureLegs = Utils.parseDepartureLegFromList(
				rootElement.getElementsByTagName("aixm-5.1:DepartureLeg"),
				rootElement);

		System.out.println("departure size" + departureLegs.size());
		System.out.println(" ");
		initialLegs = Utils.parseInitialLegFromList(
				rootElement.getElementsByTagName("aixm-5.1:InitialLeg"),
				rootElement);

		intermediateLegs = Utils.parseIntermediateLegFromList(
				rootElement.getElementsByTagName("aixm-5.1:IntermediateLeg"),
				rootElement);

		finalLegs = Utils.parseFinalLegFromList(
				rootElement.getElementsByTagName("aixm-5.1:FinalLeg"),
				rootElement);

		System.out.println(rootElement.getTagName());

	}

	public void doMath() {
		Leg previousLeg = null;

		for (StandardInstrumentDeparture sid : sidList) {
			for (Reference segmentLeg : sid.getTheSegmentLegList()) {
				String hrefId = segmentLeg.getHref();
				Integer i = 0;

				for (Leg departureLeg : departureLegs) {
					if (hrefId.substring(9, hrefId.length()).equals(
							departureLeg.getIdentifier().getValue())) {
						i++;
						if (departureLeg.getLegTypeARINC().equals(
								Constants.LEG_TYPE_VD)) {
							calculateForVD(departureLeg);
							previousLeg = departureLeg;

						}
						if (departureLeg.getLegTypeARINC().equals(
								Constants.LEG_TYPE_DF)) {
							if (previousLeg != null) {
								calculateForDF(departureLeg, previousLeg);
							}

						}
					}
				}
			}
		}

		for (StandardInstrumentArrival star : starList) {
			for (Reference segmentLeg : star.getTheSegmentLegList()) {
				String hrefId = segmentLeg.getHref();
				Integer i = 0;

				for (Leg arrivalLeg : arrivalLegs) {
					if (hrefId.substring(9, hrefId.length()).equals(
							arrivalLeg.getIdentifier().getValue())) {
						System.out.println("s-a gasit " + hrefId
								+ " de la leg-ul " + arrivalLeg.getId());
						i++;
						/*
						 * if (arrivalLeg.getLegTypeARINC().equals(
						 * Constants.LEG_TYPE_CF)) { calculateForCF(arrivalLeg);
						 * previousLeg = departureLeg;
						 * /*System.out.println("previousLeg este acum " +
						 * previousLeg.getId() + " " +
						 * previousLeg.getLegTypeARINC() + " " +
						 * previousLeg.getCourseType());
						 * 
						 * } if (arrivalLeg.getLegTypeARINC().equals(
						 * Constants.LEG_TYPE_CF)) { if (previousLeg != null) {
						 * calculateForDF(departureLeg, previousLeg);
						 * //System.out.println("E de tip DF"); }
						 * 
						 * }
						 */
					}
				}
			}
		}

	}

	public void writeXml() throws Exception {

		System.out.println("writing xml.....");

		Element rootElement = (Element) doc.getFirstChild();

		for (Leg l : departureLegs) {
			putLegInXml(rootElement, l, "aixm-5.1:DepartureLeg");
		}
		printXmlFile();
	}

	private void printXmlFile() throws Exception {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		// result.setOutputStream(new FileOutputStream(xmlOutput));
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		if (xmlOutput.exists()) {
			xmlOutput.delete();
		}
		OutputStream fOut = new FileOutputStream(xmlOutput);
		byte[] bytes = xmlString.getBytes();
		fOut.write(bytes);
		fOut.flush();
		fOut.close();
	}

	private void putLegInXml(Element root, Leg l, String xmlTag)
			throws Exception {
		NodeList ndList = root.getElementsByTagName(xmlTag);
		Document document = doc;
		for (int i = 0; i < ndList.getLength(); i++) {
			Node nd = ndList.item(i);
			if (l.getId().equals(Utils.getAtributeValueByName(nd, "gml:id"))) {
				Node timeSlice = Utils.getNodeFromElementByName((Element) nd,
						"aixm-5.1:DepartureLegTimeSlice");
				// nodeToCreate.
				Node trajectory = document.createElement("aixm-5.1:trajectory");
				Element curve = document.createElement("aixm-5.1:Curve");
				curve.setAttribute("gml:id", l.getId() + this.gmlId);
				// srsName="urn:ogc:def:crs:EPSG:4326
				curve.setAttribute("srsName", "urn:ogc:def:crs:EPSG:4326");
				Element segments = document.createElement("gml:segments");
				Element lineStringSegment = document
						.createElement("gml:LineStringSegment");
				Node posList = document.createElement("gml:posList");
				posList.setTextContent(generateStringPosList(l));
				lineStringSegment.appendChild(posList);
				segments.appendChild(lineStringSegment);
				curve.appendChild(segments);
				trajectory.appendChild(curve);

				NodeList ndList2 = timeSlice.getChildNodes();
				// Node refChild = null;
				for (int j = 0; j < ndList2.getLength(); j++) {
					Node traj = ndList2.item(j);
					if (traj.getNodeName().contains("trajectory")) {
						timeSlice.removeChild(traj);
					}
				}
				// this.doc.importNode(trajectory, true);
				// timeSlice.appendChild(trajectory);
				timeSlice.insertBefore(trajectory, Utils
						.getNodeFromElementByName((Element) timeSlice,
								"aixm-5.1:aircraftCategory"));
				// timeSlice.appendChild(trajectory);
			}
		}
	}

	private String generateStringPosList(Leg l) {
		StringBuffer buff = new StringBuffer("");
		if (l.getPosList() != null) {
			for (Pos pos : l.getPosList()) {
				buff.append(pos.getLat() + " " + pos.getLng() + " ");
			}
		}
		return buff.toString();
	}

	private void calculateForVD(Leg leg) {
		List<Pos> posList = new ArrayList<Pos>();
		Pos pos = new Pos();
		Double h = Constants.INALTIME_RWY;
		Double course = new Double(leg.getCourse());
		Double spd_kts = new Double(
				Utils.convertToMPS(leg.getSpeedLimit()) * 1.943844);
		Double tas_kts = new Double(spd_kts * 171233
				* Math.pow((303 - 0.00198 * h), 0.5)
				/ Math.pow((288 - 0.00198 * h), 2.628));
		Double tas_mps = new Double(tas_kts * 0.51444444444);
		pos.setLat(lat0);
		pos.setLng(lng0);
		posList.add(pos);
		Integer i = 0;
		Double lat1 = new Double(lat0 + deltaT * tas_mps
				* Math.cos(Math.toRadians(course)) / Constants.EARTH_RADIUS
				* 180 / Math.PI);
		Double lng1 = new Double(lng0 + deltaT * tas_mps
				* Math.sin(Math.toRadians(course))
				/ Math.cos(Math.toRadians(lat1)) / Constants.EARTH_RADIUS * 180
				/ Math.PI);
		Double delta_lng = null;
		Double delta_lat = null;
		delta_lng = new Double(Math.toRadians(lng1 - lng0));
		delta_lat = new Double(Math.toRadians(lat1 - lng0));

		Double a = Math.sin(delta_lat / 2) * Math.sin(delta_lat / 2)
				+ Math.cos(lat0) * Math.cos(lat1) * Math.sin(delta_lng / 2)
				* Math.sin(delta_lng / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double d = Constants.EARTH_RADIUS * c;

		h = new Double(h + deltaT
				* Math.sin(Math.toRadians(leg.getVerticalAngle())) * tas_mps);
		pos = new Pos();
		pos.setLat(lat1);
		pos.setLng(lng1);
		posList.add(pos);
		i++;
		Double lat2 = null;
		Double lng2 = null;
		for (DesignatedPoint point : designatedPoints) {
			String hrefId = leg.getPointChoice_fixDesignatedPointEnd()
					.getHref();
			if (hrefId.substring(9, hrefId.length()).equals(
					point.getIdentifier().getValue())) {
				lat2 = point.getPosition().getLat();
				lng2 = point.getPosition().getLng();
			}
		}

		pos = new Pos();
		pos.setLat(lat2);
		pos.setLng(lng2);
		posList.add(pos);
		leg.setPosList(posList);
	}

	private void calculateForDF(Leg leg, Leg previousLeg) {
		List<Pos> posList = new ArrayList<Pos>();
		List<Pos> posList_prev = previousLeg.getPosList();

		Pos pos = new Pos();
		Double length = Utils.convertToM(leg.getLength());
		Double h = previousLeg.getLowerLimitAltitude().getValue();
		Double course = new Double(previousLeg.getCourse());
		Double y = null;
		Double x = null;
		Double lat = null;
		Double lng = null;
		Double lng2 = null;
		Double lat2 = null;
		Double lng1 = null;
		Double lat1 = null;
		Double az = null;
		Double azM = null;
		Double delta_lng = null;
		Double delta_lat = null;
		Double c = null;
		Double d = null;
		Double spd_mps = new Double(Utils.convertToMPS(leg.getSpeedLimit()));
		Double spd_kts = new Double(
				Utils.convertToMPS(leg.getSpeedLimit()) * 1.943844);
		Double tas_kts = new Double(spd_kts * 171233
				* Math.pow((303 - 0.00198 * h), 0.5)
				/ Math.pow((288 - 0.00198 * h), 2.628));
		Double tas_mps = new Double(tas_kts * 0.51444444444);
		Double Radius_turn = Math.pow(tas_mps, 2)
				/ Math.tan(Math.toRadians(leg.getBankAngle())) / g;
		Double omega = new Double(tas_mps / Radius_turn * 180 / Math.PI);
		Double psi_d = null;
		Double psi = course;
		Double vang = leg.getVerticalAngle();
		Integer n = new Integer(posList_prev.size());
		lng1 = posList_prev.get(n - 1).getLng();
		lat1 = posList_prev.get(n - 1).getLat();
		h = new Double(h + deltaT * Math.sin(Math.toRadians(vang)) * tas_mps);

		lng2 = null;
		lat2 = null;
		for (DesignatedPoint point : designatedPoints) {
			String hrefId = leg.getPointChoice_fixDesignatedPointEnd()
					.getHref();
			if (hrefId.substring(9, hrefId.length()).equals(
					point.getIdentifier().getValue())) {
				lat2 = point.getPosition().getLat();
				lng2 = point.getPosition().getLng();
			}
		}

		y = Math.sin(Math.toRadians(lng2 - lng1));
		x = Math.cos(Math.toRadians(lat1)) * Math.tan(Math.toRadians(lat2))
				- Math.sin(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lng2 - lng1));
		azM = Math.toDegrees(Math.atan2(y, x));
		if (azM < 0) {
			azM = new Double(azM);
		}
		System.out.println("legul" + leg.getId());
		System.out.println("Acesta este cursul calculat :" + azM);
		System.out.println("Asta este cursul precedent :" + psi);
		lat = new Double(lat1);
		lng = new Double(lng1);
		Integer i = 0;
		pos.setLat(lat1);
		pos.setLng(lng1);
		posList.add(pos);
		az = new Double(azM);
		if (az < 0) {
			az = new Double(360 + az);
		}
		psi_d = new Double(Math.signum(az - course) * omega);
		while (Math.abs(psi - az) >= 0.9) {
			psi = new Double(psi + deltaT * psi_d);
			lat = new Double(posList.get(i).getLat() + deltaT * tas_mps
					* Math.cos(Math.toRadians(psi)) / Constants.EARTH_RADIUS
					* 180 / Math.PI);
			lng = new Double(lng + deltaT * tas_mps
					* Math.sin(Math.toRadians(psi))
					/ Math.cos(Math.toRadians(posList.get(i).getLat()))
					/ Constants.EARTH_RADIUS * 180 / Math.PI);

			y = new Double(Math.sin(Math.toRadians(lng2 - lng)));
			x = new Double(Math.cos(Math.toRadians(lat))
					* Math.tan(Math.toRadians(lat2))
					- Math.sin(Math.toRadians(lat))
					* Math.cos(Math.toRadians(lng2 - lng)));
			az = new Double(Math.toDegrees(Math.atan2(y, x)));
			if (az < 0) {
				az = new Double(az + 360);
			}
			delta_lng = new Double(Math.toRadians(lng - lng1));
			delta_lat = new Double(Math.toRadians(lat - lat1));
			Double a = Math.sin(delta_lat / 2) * Math.sin(delta_lat / 2)
					+ Math.cos(lat1) * Math.cos(lat) * Math.sin(delta_lng / 2)
					* Math.sin(delta_lng / 2);
			c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			d = Constants.EARTH_RADIUS * c;
			h = new Double(h + deltaT
					* Math.sin(Math.toRadians(leg.getVerticalAngle()))
					* tas_mps);
			pos = new Pos();
			pos.setLat(lat);
			pos.setLng(lng);
			posList.add(pos);
			i++;

		}
		pos = new Pos();
		pos.setLat(lat2);
		pos.setLng(lng2);
		posList.add(pos);
		i++;

		System.out.println("distanta calculata  " + d);
		System.out.println("distanta reala  " + length);
		leg.setPosList(posList);
	}

}
