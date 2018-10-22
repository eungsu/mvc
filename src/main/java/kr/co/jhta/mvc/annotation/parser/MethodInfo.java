package kr.co.jhta.mvc.annotation.parser;

import java.lang.reflect.Method;

public class MethodInfo {

	private String path;
	private String className;
	private Method method;
	
	public MethodInfo() {}

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
		return "MethodInfo [path=" + path + ", className=" + className + ", method=" + method.getName() + "]";
	}

	
}
