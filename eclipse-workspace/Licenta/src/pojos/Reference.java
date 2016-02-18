package pojos;

public class Reference {
	
	private String href;
	
	private String xlink;

	public Reference(String href, String xlink) {
		this.href=href;
		this.xlink=xlink;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getXlink() {
		return xlink;
	}

	public void setXlink(String xlink) {
		this.xlink = xlink;
	}
	

}
