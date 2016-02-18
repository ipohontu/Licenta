package pojos;

public class GmlIdentifier {

	private String codeSpace;
	
	private String value;
	
	public GmlIdentifier(String codeSpace, String value) {
		this.setCodeSpace(codeSpace);
		this.setValue(value);
	}

	public String getCodeSpace() {
		return codeSpace;
	}

	public void setCodeSpace(String codeSpace) {
		this.codeSpace = codeSpace;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
