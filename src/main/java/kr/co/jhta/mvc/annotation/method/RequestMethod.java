package kr.co.jhta.mvc.annotation.method;

public enum RequestMethod {
	GET("GET"), POST("POST");
	
	private String method;
	
	RequestMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}
	
}
