package kr.co.jhta.mvc.annotation.parser;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kr.co.jhta.mvc.annotation.RequestMapping;
import kr.co.jhta.mvc.annotation.method.RequestMethod;

public class RequestMappingAnnotationProcessor {

	@SuppressWarnings("rawtypes")
	public static Map<MethodKey, MethodDetail> process(Set<Class<?>> clazz) {
		Map<MethodKey, MethodDetail> requestMappingMethodMap = new HashMap<MethodKey, MethodDetail>();
		
		for (Class clz : clazz) {
			String className = clz.getName();
			Set<Method> methodSet = getRequestMappingMethod(clz);
			for (Method method : methodSet) {
				RequestMethod requestMethod = getRequestMethod(method);
				String path = getPath(method);
				MethodKey methodKey = new MethodKey(requestMethod, path);
				
				if (requestMappingMethodMap.containsKey(methodKey)) {
					throw new RuntimeException("요청 URI ["+path+"]가 중복되었습니다.");
				}
				
				MethodDetail methodInfo = new MethodDetail();
				methodInfo.setRequestMethod(requestMethod);
				methodInfo.setClassName(className);
				methodInfo.setPath(path);
				methodInfo.setMethod(method);
				
				requestMappingMethodMap.put(methodKey, methodInfo);
			}
		}
		
		return requestMappingMethodMap;
	}
	
	@SuppressWarnings("rawtypes")
	private static Set<Method> getRequestMappingMethod(Class claz) {
		Set<Method> set = new HashSet<Method>();
		
		Method[] methods = claz.getDeclaredMethods();
		for (Method method : methods) {
			if (isRequestMappingMethod(method)) {
				set.add(method);
			}
		}
		return set;
	}
	
	private static boolean isRequestMappingMethod(Method method) {
		return method.isAnnotationPresent(RequestMapping.class);
	}
	
	private static RequestMethod getRequestMethod(Method method) {
		RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
		return requestMappingAnnotation.method();
	}
	
	private static String getPath(Method method) {
		RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
		return requestMappingAnnotation.path();
	}
	
}
