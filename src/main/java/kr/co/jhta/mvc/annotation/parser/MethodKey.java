package kr.co.jhta.mvc.annotation.parser;

import kr.co.jhta.mvc.annotation.method.RequestMethod;

public class MethodKey {

	private RequestMethod requestMethod;
	private String path;
	
	public MethodKey(RequestMethod requestMethod, String path) {
		this.requestMethod = requestMethod;
		this.path = path;
	}
	
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}
	
	public String getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodKey other = (MethodKey) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (requestMethod != other.requestMethod)
			return false;
		return true;
	}
	
	
}
