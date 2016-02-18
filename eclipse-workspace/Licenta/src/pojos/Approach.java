package pojos;

import java.util.List;

public class Approach {
	private GmlIdentifier identifier;

	List<String> typeList;

	List<Integer> seqNumberARINCList;

	List<Reference> theSegmentLegList;

	public GmlIdentifier getIdentifier() {
		return identifier;
	}

	public List<String> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
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
	public List<Integer> getSeqNumberARINCList() {
		return seqNumberARINCList;
	}

	public void setSeqNumberARINCList(List<Integer> seqNumberARINCList) {
		this.seqNumberARINCList = seqNumberARINCList;
	}


}
