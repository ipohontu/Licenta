package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pojos.Approach;
import pojos.DesignatedPoint;
import pojos.GmlIdentifier;
import pojos.Leg;
import pojos.Measurement;
import pojos.Navaid;
import pojos.Pos;
import pojos.Reference;
import pojos.StandardInstrumentArrival;
import pojos.StandardInstrumentDeparture;
import enums.MeasurementToMeterEnum;
import enums.MeasurementToMeterperSecondEnum;

public class Utils {

	public static Double convertToM(Measurement measure) {
		MeasurementToMeterEnum ms = MeasurementToMeterEnum.getByUOM(measure
				.getUom());
		return measure.getValue() * ms.getValue();
	}

	public static Double convertToMPS(Measurement measure) {
		MeasurementToMeterperSecondEnum mps = MeasurementToMeterperSecondEnum
				.getByUOM(measure.getUom());
		return measure.getValue() * mps.getValue();
	}

	public static List<StandardInstrumentDeparture> parseSIDFromList(
			NodeList nodeList, Element rootElement) throws Exception {
		List<StandardInstrumentDeparture> sidList = new ArrayList<StandardInstrumentDeparture>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			StandardInstrumentDeparture sid = new StandardInstrumentDeparture();
			Element elFromList = (Element) nodeList.item(i);

			if (getNodeFromElementByName(elFromList, "gml:identifier") != null) {
				Node identifier = getNodeFromElementByName(elFromList,
						"gml:identifier");
				sid.setIdentifier(new GmlIdentifier(getAtributeValueByName(
						identifier, "codeSpace"), String.valueOf(identifier
						.getTextContent())));
			}
			NodeList ndList1 = elFromList
					.getElementsByTagName("aixm-5.1:transitionLeg");
			List<Integer> seqNo = new ArrayList<Integer>();
			List<Reference> theSegment = new ArrayList<Reference>();

			for (int j = 0; j < ndList1.getLength(); j++) {
				Element el1 = (Element) ndList1.item(j);

				seqNo.add(
						j,
						new Integer(getNodeFromElementByName(el1,
								"aixm-5.1:seqNumberARINC").getTextContent()));

				theSegment.add(
						j,
						new Reference(
								getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xlink:href"), getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xmlns:xlink")));
			}
			sidList.add(sid);
			sid.setSeqNumberARINCList(seqNo);
			sid.setTheSegmentLegList(theSegment);

			System.out.println("SID :");
			if (sid.getIdentifier() != null) {
				System.out.println(sid.getIdentifier().getValue());
			} else
				System.out.println("nu e id");
			for (int j = 0; j < ndList1.getLength(); j++) {
				System.out.println(sid.getSeqNumberARINCList().get(j));
				System.out.println(sid.getTheSegmentLegList().get(j).getHref());
			}
			System.out.println(" ");

		}
		return sidList;
	}

	public static List<DesignatedPoint> parseDPFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<DesignatedPoint> pointsList = new ArrayList<DesignatedPoint>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			DesignatedPoint point = new DesignatedPoint();
			Element elFromList = (Element) nodeList.item(i);

			if (getNodeFromElementByName(elFromList, "gml:identifier") != null) {
				Node identifier = getNodeFromElementByName(elFromList,
						"gml:identifier");
				point.setIdentifier(new GmlIdentifier(getAtributeValueByName(
						identifier, "codeSpace"), String.valueOf(identifier
						.getTextContent())));
			}

			if (getAtributeValueByName(elFromList, "gml:id") != null) {
				point.setId(getAtributeValueByName(elFromList, "gml:id"));
			}

			if (getNodeFromElementByName(elFromList, "aixm-5.1:designator") != null) {
				Node designator = getNodeFromElementByName(elFromList,
						"aixm-5.1:designator");
				point.setDesignator(String.valueOf(designator.getTextContent()));
			}

			Pos w = new Pos();
			if (getNodeFromElementByName(elFromList, "gml:pos") != null) {
				Node position = getNodeFromElementByName(elFromList, "gml:pos");
				String no = String.valueOf(position.getTextContent());
				w.setLat(Double.parseDouble(no.substring(0, no.indexOf(" "))));
				w.setLng(Double.parseDouble(no.split(" ")[1]));
				point.setPosition(w);
			}

			pointsList.add(point);
			System.out.println("DP :");
			if (point.getIdentifier() != null) {
				System.out.println(point.getIdentifier().getValue());
			} else
				System.out.println("nu e id");
			System.out.println(" latitudnea " + "  "
					+ point.getPosition().getLat() + " longitudinea "
					+ point.getPosition().getLng());
		}
		return pointsList;
	}

	public static List<Navaid> parseNAVAIDFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Navaid> pointsList = new ArrayList<Navaid>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Navaid point = new Navaid();
			Element elFromList = (Element) nodeList.item(i);

			if (getNodeFromElementByName(elFromList, "gml:identifier") != null) {
				Node identifier = getNodeFromElementByName(elFromList,
						"gml:identifier");
				point.setIdentifier(new GmlIdentifier(getAtributeValueByName(
						identifier, "codeSpace"), String.valueOf(identifier
						.getTextContent())));
			}

			if (getAtributeValueByName(elFromList, "gml:id") != null) {
				point.setId(getAtributeValueByName(elFromList, "gml:id"));
			}

			if (getNodeFromElementByName(elFromList, "aixm-5.1:designator") != null) {
				Node designator = getNodeFromElementByName(elFromList,
						"aixm-5.1:designator");
				point.setDesignator(String.valueOf(designator.getTextContent()));
			}

			Pos w = new Pos();
			if (getNodeFromElementByName(elFromList, "gml:pos") != null) {
				Node position = getNodeFromElementByName(elFromList, "gml:pos");
				String no = String.valueOf(position.getTextContent());
				w.setLat(Double.parseDouble(no.substring(0, no.indexOf(" "))));
				w.setLng(Double.parseDouble(no.substring(no.indexOf(" "))));
				point.setPosition(w);
				System.out.println(" latitudnea " + "  "
						+ point.getPosition().getLat() + " longitudinea "
						+ point.getPosition().getLng());
			}

			pointsList.add(point);
			System.out.println("NAVAID :");
			if (point.getIdentifier() != null) {
				System.out.println(point.getIdentifier().getValue());
			} else
				System.out.println("nu e id");

		}
		return pointsList;
	}

	public static List<StandardInstrumentArrival> parseSTARFromList(
			NodeList nodeList, Element rootElement) throws Exception {
		List<StandardInstrumentArrival> starList = new ArrayList<StandardInstrumentArrival>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			StandardInstrumentArrival star = new StandardInstrumentArrival();
			Element elFromList = (Element) nodeList.item(i);

			if (getNodeFromElementByName(elFromList, "gml:identifier") != null) {
				Node identifier = getNodeFromElementByName(elFromList,
						"gml:identifier");
				star.setIdentifier(new GmlIdentifier(getAtributeValueByName(
						identifier, "codeSpace"), String.valueOf(identifier
						.getTextContent())));
			}
			NodeList ndList1 = elFromList
					.getElementsByTagName("aixm-5.1:transitionLeg");
			List<Integer> seqNo = new ArrayList<Integer>();
			List<Reference> theSegment = new ArrayList<Reference>();

			for (int j = 0; j < ndList1.getLength(); j++) {
				Element el1 = (Element) ndList1.item(j);

				seqNo.add(
						j,
						new Integer(getNodeFromElementByName(el1,
								"aixm-5.1:seqNumberARINC").getTextContent()));

				theSegment.add(
						j,
						new Reference(
								getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xlink:href"), getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xmlns:xlink")));
			}
			starList.add(star);
			star.setSeqNumberARINCList(seqNo);
			star.setTheSegmentLegList(theSegment);

			System.out.println("STAR :");
			if (star.getIdentifier() != null) {
				System.out.println(star.getIdentifier().getValue());
			} else
				System.out.println("nu e id");
			for (int j = 0; j < ndList1.getLength(); j++) {
				System.out.println(star.getSeqNumberARINCList().get(j));
				System.out
						.println(star.getTheSegmentLegList().get(j).getHref());
			}
			System.out.println(" ");

		}
		return starList;
	}

	public static List<Approach> parseIAPFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Approach> iapList = new ArrayList<Approach>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Approach iap = new Approach();
			Element elFromList = (Element) nodeList.item(i);

			if (getNodeFromElementByName(elFromList, "gml:identifier") != null) {
				Node identifier = getNodeFromElementByName(elFromList,
						"gml:identifier");
				iap.setIdentifier(new GmlIdentifier(getAtributeValueByName(
						identifier, "codeSpace"), String.valueOf(identifier
						.getTextContent())));
			}
			NodeList ndList1 = elFromList
					.getElementsByTagName("aixm-5.1:transitionLeg");
			List<Integer> seqNo = new ArrayList<Integer>();
			List<Reference> theSegment = new ArrayList<Reference>();
			List<String> type = new ArrayList<String>();

			for (int j = 0; j < ndList1.getLength(); j++) {
				Element el1 = (Element) ndList1.item(j);
				seqNo.add(
						j,
						new Integer(getNodeFromElementByName(el1,
								"aixm-5.1:seqNumberARINC").getTextContent()));

				theSegment.add(
						j,
						new Reference(
								getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xlink:href"), getAtributeValueByName(
										getNodeFromElementByName(el1,
												"aixm-5.1:theSegmentLeg"),
										"xmlns:xlink")));
			}
			iapList.add(iap);
			iap.setTypeList(type);
			iap.setSeqNumberARINCList(seqNo);
			iap.setTheSegmentLegList(theSegment);

			System.out.println("IAP :");
			if (iap.getIdentifier() != null) {
				System.out.println(iap.getIdentifier().getValue());
			} else
				System.out.println("nu e id");
			for (int j = 0; j < ndList1.getLength(); j++) {

				System.out.println(iap.getSeqNumberARINCList().get(j));
				System.out.println(iap.getTheSegmentLegList().get(j).getHref());
			}
			System.out.println(" ");

		}
		return iapList;
	}

	public static List<Leg> parseArrivalLegFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Leg> legList = new ArrayList<Leg>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Leg leg = new Leg();
			Element elFromList = (Element) nodeList.item(i);
			parseCommonLegAtrs(leg, elFromList);

			if (getNodeFromElementByName(elFromList, "aixm-5.1:startPoint") != null) {

				Node startPoint = getNodeFromElementByName(elFromList,
						"aixm-5.1:startPoint");

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:theAngleIndication") != null) {
					leg.setTheAngleIndicationStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xmlns:xlink")));
				} else {
					leg.setTheAngleIndicationStart(new Reference("0", "0"));
				}

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {
					leg.setFacilityDistanceStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xmlns:xlink")));
				} else {
					leg.setFacilityDistanceStart(new Reference("0", "0"));
				}
				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {

					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xlink:href"),
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xmlns:xlink")));
				} else {
					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							"0", "0"));
				}

			}

			System.out.println("Arrival Leg");
			System.out.println("id: " + leg.getId() + " identif:"
					+ leg.getIdentifier().getValue());
			System.out.println(leg.getLegPath());
			System.out.println(leg.getLegTypeARINC());
			System.out.println(leg.getCourse());
			System.out.println(leg.getCourseType());
			System.out.println(leg.getTurnDirection());
			if (leg.getSpeedLimit() != null) {
				System.out.println(leg.getSpeedLimit().getUom() + " "
						+ leg.getSpeedLimit().getValue());
			}

			System.out.println(leg.getSpeedReference());
			System.out.println(leg.getBankAngle());
			if (leg.getLength() != null) {
				System.out.println(leg.getLength().getUom() + " "
						+ leg.getLength().getValue());
			}

			if (leg.getUpperLimitAltitude() != null) {
				System.out.println(leg.getUpperLimitAltitude().getUom() + " "
						+ leg.getUpperLimitAltitude().getValue());
			}
			System.out.println(leg.getUpperLimitReference());
			if (leg.getLowerLimitAltitude() != null) {
				System.out.println(leg.getLowerLimitAltitude().getUom() + " "
						+ leg.getLowerLimitAltitude().getValue());
			}
			System.out.println(leg.getLowerLimitReference());
			System.out.println(leg.getAltitudeInterpretation());
			System.out.println(leg.getVerticalAngle());

			System.out.println(leg.getAircraftLandingCategory());

			if (leg.getFacilityDistanceStart() != null) {
				System.out
						.println(leg.getFacilityDistanceStart().getHref() + " "
								+ leg.getFacilityDistanceStart().getXlink()
								+ " ");
			}

			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getTheAngleIndicationStart().getHref()
						+ " " + leg.getTheAngleIndicationStart().getXlink()
						+ " ");
			}
			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getPointChoice_fixDesignatedPointStart()
						.getHref()
						+ " "
						+ leg.getPointChoice_fixDesignatedPointStart()
								.getXlink());
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out.println(leg.getFacilityDistanceEnd().getHref() + " "
						+ leg.getFacilityDistanceEnd().getXlink() + " ");
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getTheAngleIndicationEnd().getHref() + " "
								+ leg.getTheAngleIndicationEnd().getXlink()
								+ " ");
			}
			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getPointChoice_fixDesignatedPointEnd()
								.getHref()
								+ " "
								+ leg.getPointChoice_fixDesignatedPointEnd()
										.getXlink());
			}
			System.out.println(" ");

			legList.add(leg);
		}

		return legList;
	}

	public static List<Leg> parseIntermediateLegFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Leg> legList = new ArrayList<Leg>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Leg leg = new Leg();
			Element elFromList = (Element) nodeList.item(i);
			parseCommonLegAtrs(leg, elFromList);

			if (getNodeFromElementByName(elFromList, "aixm-5.1:startPoint") != null) {

				Node startPoint = getNodeFromElementByName(elFromList,
						"aixm-5.1:startPoint");

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:theAngleIndication") != null) {
					leg.setTheAngleIndicationStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xmlns:xlink")));
				} else {
					leg.setTheAngleIndicationStart(new Reference("0", "0"));
				}

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {
					leg.setFacilityDistanceStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xmlns:xlink")));
				} else {
					leg.setFacilityDistanceStart(new Reference("0", "0"));
				}
				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {

					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xlink:href"),
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xmlns:xlink")));
				} else {
					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							"0", "0"));
				}

			}

			System.out.println("Intermediate Leg");
			System.out.println("id: " + leg.getId() + " identif:"
					+ leg.getIdentifier().getValue());
			System.out.println(leg.getLegPath());
			System.out.println(leg.getLegTypeARINC());
			System.out.println(leg.getCourse());
			System.out.println(leg.getCourseType());
			System.out.println(leg.getTurnDirection());
			if (leg.getSpeedLimit() != null) {
				System.out.println(leg.getSpeedLimit().getUom() + " "
						+ leg.getSpeedLimit().getValue());
			}

			System.out.println(leg.getSpeedReference());
			System.out.println(leg.getBankAngle());
			if (leg.getLength() != null) {
				System.out.println(leg.getLength().getUom() + " "
						+ leg.getLength().getValue());
			}

			if (leg.getUpperLimitAltitude() != null) {
				System.out.println(leg.getUpperLimitAltitude().getUom() + " "
						+ leg.getUpperLimitAltitude().getValue());
			}
			System.out.println(leg.getUpperLimitReference());
			if (leg.getLowerLimitAltitude() != null) {
				System.out.println(leg.getLowerLimitAltitude().getUom() + " "
						+ leg.getLowerLimitAltitude().getValue());
			}
			System.out.println(leg.getLowerLimitReference());
			System.out.println(leg.getAltitudeInterpretation());
			System.out.println(leg.getVerticalAngle());

			System.out.println(leg.getAircraftLandingCategory());

			if (leg.getFacilityDistanceStart() != null) {
				System.out
						.println(leg.getFacilityDistanceStart().getHref() + " "
								+ leg.getFacilityDistanceStart().getXlink()
								+ " ");
			}

			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getTheAngleIndicationStart().getHref()
						+ " " + leg.getTheAngleIndicationStart().getXlink()
						+ " ");
			}
			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getPointChoice_fixDesignatedPointStart()
						.getHref()
						+ " "
						+ leg.getPointChoice_fixDesignatedPointStart()
								.getXlink());
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out.println(leg.getFacilityDistanceEnd().getHref() + " "
						+ leg.getFacilityDistanceEnd().getXlink() + " ");
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getTheAngleIndicationEnd().getHref() + " "
								+ leg.getTheAngleIndicationEnd().getXlink()
								+ " ");
			}
			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getPointChoice_fixDesignatedPointEnd()
								.getHref()
								+ " "
								+ leg.getPointChoice_fixDesignatedPointEnd()
										.getXlink());
			}
			System.out.println(" ");

			legList.add(leg);
		}

		return legList;
	}

	public static List<Leg> parseInitialLegFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Leg> legList = new ArrayList<Leg>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Leg leg = new Leg();
			Element elFromList = (Element) nodeList.item(i);
			parseCommonLegAtrs(leg, elFromList);

			// ce e in plus fata de common
			if (getNodeFromElementByName(elFromList, "aixm-5.1:startPoint") != null) {

				Node startPoint = getNodeFromElementByName(elFromList,
						"aixm-5.1:startPoint");

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:theAngleIndication") != null) {
					leg.setTheAngleIndicationStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xmlns:xlink")));
				} else {
					leg.setTheAngleIndicationStart(new Reference("0", "0"));
				}

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {
					leg.setFacilityDistanceStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xmlns:xlink")));
				} else {
					leg.setFacilityDistanceStart(new Reference("0", "0"));
				}
				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {

					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xlink:href"),
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xmlns:xlink")));
				} else {
					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							"0", "0"));
				}

			}

			System.out.println("Initial Leg");
			System.out.println("id: " + leg.getId() + " identif:"
					+ leg.getIdentifier().getValue());
			System.out.println(leg.getLegPath());
			System.out.println(leg.getLegTypeARINC());
			System.out.println(leg.getCourse());
			System.out.println(leg.getCourseType());
			System.out.println(leg.getTurnDirection());
			if (leg.getSpeedLimit() != null) {
				System.out.println(leg.getSpeedLimit().getUom() + " "
						+ leg.getSpeedLimit().getValue());
			}

			System.out.println(leg.getSpeedReference());
			System.out.println(leg.getBankAngle());
			if (leg.getLength() != null) {
				System.out.println(leg.getLength().getUom() + " "
						+ leg.getLength().getValue());
			}

			if (leg.getUpperLimitAltitude() != null) {
				System.out.println(leg.getUpperLimitAltitude().getUom() + " "
						+ leg.getUpperLimitAltitude().getValue());
			}
			System.out.println(leg.getUpperLimitReference());
			if (leg.getLowerLimitAltitude() != null) {
				System.out.println(leg.getLowerLimitAltitude().getUom() + " "
						+ leg.getLowerLimitAltitude().getValue());
			}
			System.out.println(leg.getLowerLimitReference());
			System.out.println(leg.getAltitudeInterpretation());
			System.out.println(leg.getVerticalAngle());

			System.out.println(leg.getAircraftLandingCategory());

			if (leg.getFacilityDistanceStart() != null) {
				System.out
						.println(leg.getFacilityDistanceStart().getHref() + " "
								+ leg.getFacilityDistanceStart().getXlink()
								+ " ");
			}

			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getTheAngleIndicationStart().getHref()
						+ " " + leg.getTheAngleIndicationStart().getXlink()
						+ " ");
			}
			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getPointChoice_fixDesignatedPointStart()
						.getHref()
						+ " "
						+ leg.getPointChoice_fixDesignatedPointStart()
								.getXlink());
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out.println(leg.getFacilityDistanceEnd().getHref() + " "
						+ leg.getFacilityDistanceEnd().getXlink() + " ");
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getTheAngleIndicationEnd().getHref() + " "
								+ leg.getTheAngleIndicationEnd().getXlink()
								+ " ");
			}
			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getPointChoice_fixDesignatedPointEnd()
								.getHref()
								+ " "
								+ leg.getPointChoice_fixDesignatedPointEnd()
										.getXlink());
			}
			System.out.println(" ");
			legList.add(leg);
		}

		return legList;
	}

	public static List<Leg> parseFinalLegFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Leg> legList = new ArrayList<Leg>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Leg leg = new Leg();
			Element elFromList = (Element) nodeList.item(i);
			parseCommonLegAtrs(leg, elFromList);

			if (getNodeFromElementByName(elFromList, "aixm-5.1:startPoint") != null) {

				Node startPoint = getNodeFromElementByName(elFromList,
						"aixm-5.1:startPoint");

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:theAngleIndication") != null) {
					leg.setTheAngleIndicationStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:theAngleIndication"),
									"xmlns:xlink")));
				} else {
					leg.setTheAngleIndicationStart(new Reference("0", "0"));
				}

				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {
					leg.setFacilityDistanceStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xlink:href"), getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:facilityDistance"),
									"xmlns:xlink")));
				} else {
					leg.setFacilityDistanceStart(new Reference("0", "0"));
				}
				if (getNodeFromElementByName((Element) startPoint,
						"aixm-5.1:facilityDistance") != null) {

					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xlink:href"),
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) startPoint,
											"aixm-5.1:pointChoice_fixDesignatedPoint"),
									"xmlns:xlink")));
				} else {
					leg.setPointChoice_fixDesignatedPointStart(new Reference(
							"0", "0"));
				}

			}

			System.out.println("Final Leg");
			System.out.println("id: " + leg.getId() + " identif:"
					+ leg.getIdentifier().getValue());
			System.out.println(leg.getLegPath());
			System.out.println(leg.getLegTypeARINC());
			System.out.println(leg.getCourse());
			System.out.println(leg.getCourseType());
			System.out.println(leg.getTurnDirection());
			if (leg.getSpeedLimit() != null) {
				System.out.println(leg.getSpeedLimit().getUom() + " "
						+ leg.getSpeedLimit().getValue());
			}

			System.out.println(leg.getSpeedReference());
			System.out.println(leg.getBankAngle());
			if (leg.getLength() != null) {
				System.out.println(leg.getLength().getUom() + " "
						+ leg.getLength().getValue());
			}

			if (leg.getUpperLimitAltitude() != null) {
				System.out.println(leg.getUpperLimitAltitude().getUom() + " "
						+ leg.getUpperLimitAltitude().getValue());
			}
			System.out.println(leg.getUpperLimitReference());
			if (leg.getLowerLimitAltitude() != null) {
				System.out.println(leg.getLowerLimitAltitude().getUom() + " "
						+ leg.getLowerLimitAltitude().getValue());
			}
			System.out.println(leg.getLowerLimitReference());
			System.out.println(leg.getAltitudeInterpretation());
			System.out.println(leg.getVerticalAngle());

			System.out.println(leg.getAircraftLandingCategory());

			if (leg.getFacilityDistanceStart() != null) {
				System.out
						.println(leg.getFacilityDistanceStart().getHref() + " "
								+ leg.getFacilityDistanceStart().getXlink()
								+ " ");
			}

			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getTheAngleIndicationStart().getHref()
						+ " " + leg.getTheAngleIndicationStart().getXlink()
						+ " ");
			}
			if (leg.getFacilityDistanceStart() != null) {
				System.out.println(leg.getPointChoice_fixDesignatedPointStart()
						.getHref()
						+ " "
						+ leg.getPointChoice_fixDesignatedPointStart()
								.getXlink());
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out.println(leg.getFacilityDistanceEnd().getHref() + " "
						+ leg.getFacilityDistanceEnd().getXlink() + " ");
			}

			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getTheAngleIndicationEnd().getHref() + " "
								+ leg.getTheAngleIndicationEnd().getXlink()
								+ " ");
			}
			if (leg.getFacilityDistanceEnd() != null) {
				System.out
						.println(leg.getPointChoice_fixDesignatedPointEnd()
								.getHref()
								+ " "
								+ leg.getPointChoice_fixDesignatedPointEnd()
										.getXlink());
			}
			System.out.println(" ");
			legList.add(leg);
		}

		return legList;
	}

	public static Leg getLegFromListWithID(List<Leg> legList, String id) {
		for (Leg leg : legList) {
			if (id.equals(leg.getIdentifier().getValue()))
				return leg;
		}
		return null;
	}

	public static void parseCommonLegAtrs(Leg leg, Element elFromList)
			throws Exception {
		String id = getAtributeValueByName(elFromList, "gml:id");
		if (id != null) {
			leg.setId(getAtributeValueByName(elFromList, "gml:id"));
		}

		Node identifier = getNodeFromElementByName(elFromList, "gml:identifier");
		if (identifier != null) {
			leg.setIdentifier(new GmlIdentifier(getAtributeValueByName(
					identifier, "codeSpace"), String.valueOf(identifier
					.getTextContent())));
		}

		String endCondition = getChildTextFromElement(elFromList,
				"aixm-5.1:endConditionDesignator");
		if (endCondition != null) {
			leg.setEndConditionDesignator(getChildTextFromElement(elFromList,
					"aixm-5.1:endConditionDesignator"));
		}

		String legPath = getChildTextFromElement(elFromList, "aixm-5.1:legPath");
		if (legPath != null) {
			leg.setLegPath(getChildTextFromElement(elFromList,
					"aixm-5.1:legPath"));
		}

		String legTypeARINC = getChildTextFromElement(elFromList,
				"aixm-5.1:legTypeARINC");
		if (legTypeARINC != null) {
			leg.setLegTypeARINC(getChildTextFromElement(elFromList,
					"aixm-5.1:legTypeARINC"));
		}

		String course = getChildTextFromElement(elFromList, "aixm-5.1:course");
		if (course != null) {
			leg.setCourse(Double.valueOf(course));
		}

		String courseType = getChildTextFromElement(elFromList,
				"aixm-5.1:courseType");
		if (courseType != null) {
			leg.setCourseType(String.valueOf(courseType));
		}

		String turnDirection = getChildTextFromElement(elFromList,
				"aixm-5.1:turnDirection");
		if (turnDirection != null) {
			leg.setTurnDirection(getChildTextFromElement(elFromList,
					"aixm-5.1:turnDirection"));
		}

		Node speedLimit = getNodeFromElementByName(elFromList,
				"aixm-5.1:speedLimit");
		leg.setSpeedLimit(new Measurement(getAtributeValueByName(speedLimit,
				"uom"), Double.valueOf(speedLimit.getTextContent())));

		String speedReference = getChildTextFromElement(elFromList,
				"aixm-5.1:speedReference");
		if (speedReference != null) {
			leg.setSpeedReference(String.valueOf(speedReference));
		}

		String bankAngle = getChildTextFromElement(elFromList,
				"aixm-5.1:bankAngle");
		if (bankAngle != null) {
			leg.setBankAngle(Integer.valueOf(bankAngle));
		}

		Node lengthNode = getNodeFromElementByName(elFromList,
				"aixm-5.1:length");
		if (lengthNode != null) {
			leg.setLength(new Measurement(getAtributeValueByName(lengthNode,
					"uom"), Double.valueOf(lengthNode.getTextContent())));
		}

		Node upAltitude = getNodeFromElementByName(elFromList,
				"aixm-5.1:upperLimitAltitude");
		if (upAltitude != null) {
			leg.setUpperLimitAltitude(new Measurement(getAtributeValueByName(
					upAltitude, "uom"), Double.valueOf(upAltitude
					.getTextContent())));
		}

		String upLimitRef = getChildTextFromElement(elFromList,
				"aixm-5.1:upperLimitReference");
		if (upLimitRef != null) {
			leg.setUpperLimitReference(String.valueOf(upLimitRef));
		}

		Node lowAltitude = getNodeFromElementByName(elFromList,
				"aixm-5.1:lowerLimitAltitude");
		if (lowAltitude != null) {
			leg.setLowerLimitAltitude(new Measurement(getAtributeValueByName(
					lowAltitude, "uom"), Double.valueOf(lowAltitude
					.getTextContent())));
		}

		String lowLimitRef = getChildTextFromElement(elFromList,
				"aixm-5.1:lowerLimitReference");
		if (lowLimitRef != null) {
			leg.setLowerLimitReference(String.valueOf(lowLimitRef));
		}

		String Alt_interp = getChildTextFromElement(elFromList,
				"aixm-5.1:altitudeInterpretation");
		if (Alt_interp != null) {
			leg.setAltitudeInterpretation(getChildTextFromElement(elFromList,
					"aixm-5.1:altitudeInterpretation"));
		}

		String v_angle = getChildTextFromElement(elFromList,
				"aixm-5.1:verticalAngle");
		if (v_angle != null) {
			leg.setVerticalAngle(Double.valueOf(v_angle));
		}

		Node aircraftCategory = getNodeFromElementByName(elFromList,
				"aixm-5.1:aircraftCategory");
		if (aircraftCategory != null) {
			Node AircraftCharacteristic = getNodeFromElementByName(
					(Element) aircraftCategory,
					"aixm-5.1:AircraftCharacteristic");
			leg.setAircraftLandingCategory(getNodeFromElementByName(
					(Element) AircraftCharacteristic,
					"aixm-5.1:aircraftLandingCategory").getTextContent());
		}
		if (getNodeFromElementByName(elFromList, "aixm-5.1:endPoint") != null
				&& leg.getLegTypeARINC().equals("DF")) {

			Node endPoint = getNodeFromElementByName(elFromList,
					"aixm-5.1:endPoint");

			if (getNodeFromElementByName((Element) endPoint,
					"aixm-5.1:theAngleIndication") != null) {
				leg.setTheAngleIndicationEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:theAngleIndication"),
								"xlink:href"), getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:theAngleIndication"),
								"xmlns:xlink")));
			} else {
				leg.setTheAngleIndicationEnd(new Reference("0", "0"));
			}

			if (getNodeFromElementByName((Element) endPoint,
					"aixm-5.1:facilityDistance") != null) {
				leg.setFacilityDistanceEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:facilityDistance"),
								"xlink:href"), getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:facilityDistance"),
								"xmlns:xlink")));
			} else {
				leg.setFacilityDistanceEnd(new Reference("0", "0"));
			}
			if (endPoint != null) {
				leg.setPointChoice_fixDesignatedPointEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:pointChoice_fixDesignatedPoint"),
								"xlink:href"),
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:pointChoice_fixDesignatedPoint"),
								"xmlns:xlink")));
			} else {
				leg.setPointChoice_fixDesignatedPointEnd(new Reference("0", "0"));
			}

		}

		if (getNodeFromElementByName(elFromList, "aixm-5.1:endPoint") != null
				&& leg.getLegTypeARINC().equals("VD")) {

			Node endPoint = getNodeFromElementByName(elFromList,
					"aixm-5.1:endPoint");

			if (getNodeFromElementByName((Element) endPoint,
					"aixm-5.1:theAngleIndication") != null) {
				leg.setTheAngleIndicationEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:theAngleIndication"),
								"xlink:href"), getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:theAngleIndication"),
								"xmlns:xlink")));
			} else {
				leg.setTheAngleIndicationEnd(new Reference("0", "0"));
			}

			if (getNodeFromElementByName((Element) endPoint,
					"aixm-5.1:facilityDistance") != null) {
				leg.setFacilityDistanceEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:facilityDistance"),
								"xlink:href"), getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:facilityDistance"),
								"xmlns:xlink")));
			} else {
				leg.setFacilityDistanceEnd(new Reference("0", "0"));
			}
			if (getNodeFromElementByName((Element) endPoint,
					"aixm-5.1:facilityDistance") != null) {
				leg.setPointChoice_fixDesignatedPointEnd(new Reference(
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:pointChoice_fixDesignatedPoint"),
								"xlink:href"),
						getAtributeValueByName(
								getNodeFromElementByName((Element) endPoint,
										"aixm-5.1:pointChoice_fixDesignatedPoint"),
								"xmlns:xlink")));
			} else {
				if (getNodeFromElementByName((Element) endPoint,
						"aixm-5.1:pointChoice_navaidSystem") != null) {
					leg.setPointChoice_navaidSystem(new Reference(
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) endPoint,
											"aixm-5.1:pointChoice_navaidSystem"),
									"xlink:href"),
							getAtributeValueByName(
									getNodeFromElementByName(
											(Element) endPoint,
											"aixm-5.1:pointChoice_navaidSystem"),
									"xmlns:xlink")));
				} else {
					leg.setPointChoice_navaidSystem(new Reference("0", "0"));
				}
				leg.setPointChoice_fixDesignatedPointEnd(new Reference("0", "0"));
			}

		}

	}

	public static String getAtributeValueByName(Node nd, String atrName) {
		NamedNodeMap map = nd.getAttributes();
		return map.getNamedItem(atrName).getTextContent();
	}

	public static void printAllAtributes(Element el) {
		NamedNodeMap map = el.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			System.out.println(map.item(i).getTextContent());
		}
	}

	public static List<Leg> parseDepartureLegFromList(NodeList nodeList,
			Element rootElement) throws Exception {
		List<Leg> legList = new ArrayList<Leg>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Leg leg = new Leg();
			Element elFromList = (Element) nodeList.item(i);
			// printAllAtributes(elFromList);
			parseCommonLegAtrs(leg, elFromList);
			legList.add(leg);
			System.out.println("Departure Leg");
			System.out.println("id: " + leg.getId() + " identif:"
					+ leg.getIdentifier().getValue());
			System.out.println(leg.getLegPath());
			System.out.println(leg.getLegTypeARINC());
			System.out.println(leg.getCourse());
			System.out.println(leg.getCourseType());
			System.out.println(leg.getTurnDirection());
			if (leg.getSpeedLimit() != null) {
				System.out.println(leg.getSpeedLimit().getUom() + " "
						+ leg.getSpeedLimit().getValue());
			}

			System.out.println(leg.getSpeedReference());
			System.out.println(leg.getBankAngle());
			if (leg.getLength() != null) {
				System.out.println(leg.getLength().getUom() + " "
						+ leg.getLength().getValue());
			}

			if (leg.getUpperLimitAltitude() != null) {
				System.out.println(leg.getUpperLimitAltitude().getUom() + " "
						+ leg.getUpperLimitAltitude().getValue());
			}
			System.out.println(leg.getUpperLimitReference());
			if (leg.getLowerLimitAltitude() != null) {
				System.out.println(leg.getLowerLimitAltitude().getUom() + " "
						+ leg.getLowerLimitAltitude().getValue());
			}
			System.out.println(leg.getLowerLimitReference());
			System.out.println(leg.getAltitudeInterpretation());
			System.out.println(leg.getVerticalAngle());

			System.out.println(leg.getAircraftLandingCategory());
			System.out.println(" ");
		}
		return legList;
	}

	public static String getChildTextFromElement(Element el, String childTag)
			throws Exception {
		NodeList ndList = el.getElementsByTagName(childTag);
		if (ndList.getLength() > 1) {
			throw new Exception("More than 1 value with tag: " + childTag
					+ " in element: " + el.getTagName());
		} else if (ndList.getLength() == 0) {
			return null;
		}
		return ndList.item(0).getTextContent();
	}

	public static boolean validateXmlFile(File f) {
		return f.getPath().endsWith(".xml");
	}

	public static Node getNodeFromElementByName(Element el, String name) {
		try {
			NodeList ndList = el.getElementsByTagName(name);
			for (int i = 0; i < ndList.getLength(); i++) {
				Element nodeEl = (Element) ndList.item(i);
				if (nodeEl.getTagName().equals(name)) {
					return ndList.item(i);
				}
			}
		} catch (Exception e) {
			System.out.println();
		}
		return null;
	}

	public static Element getElementWithIdFromRoot(Element rootEl, String id) {
		NodeList nList = rootEl.getElementsByTagName("gml:identifier");
		for (int i = 0; i < nList.getLength(); i++) {
			if (id.equals(nList.item(i).getTextContent())) {
				return (Element) nList.item(i).getParentNode();
			}
		}
		return null;
	}

	public static String getHrefIdFromElement(Element el) {
		String hrefId = getAtributeValueByName(el, "xlink:href");
		return hrefId.substring(9, hrefId.length());
	}

}
