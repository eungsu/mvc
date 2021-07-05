package kr.co.jhta.mvc.annotation.parser;

import java.lang.reflect.Method;

import kr.co.jhta.mvc.annotation.method.RequestMethod;

public class MethodDetail {

	private RequestMethod requestMethod;
	private String path;
	private String className;
	private Method method;
	
	public MethodDetail() {}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}
	
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "[path=" + path + ", requestMethod=" + requestMethod +", className=" + className + ", method=" + method.getName() + "]";
	}

	
}
