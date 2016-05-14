package youtubeDownloader;

public class Videos {
	private String url;
	private String label;
	private double start;
	private double end;
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public double getStart() {
		return start;
	}
	
	public void setStart(double start) {
		this.start = start;
	}
	
	public double getEnd() {
		return end;
	}
	
	public void setEnd(double end) {
		this.end = end;
	}	
}
