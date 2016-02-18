package pojos;

import java.util.List;

public class StandardInstrumentArrival {

	private GmlIdentifier identifier;

	List<Integer> seqNumberARINCList;

	public List<Integer> getSeqNumberARINCList() {
		return seqNumberARINCList;
	}

	public void setSeqNumberARINCList(List<Integer> seqNumberARINCList) {
		this.seqNumberARINCList = seqNumberARINCList;
	}

	List<Reference> theSegmentLegList;

	public GmlIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(GmlIdentifier identifier) {
		this.identifier = identifier;
	}

	public List<Reference> getTheSegmentLegList() {
		return theSegmentLegList;
	}

	public void setTheSegmentLegList(List<Reference> theSegmentLegList) {
		this.theSegmentLegList = theSegmentLegList;
	}

}
