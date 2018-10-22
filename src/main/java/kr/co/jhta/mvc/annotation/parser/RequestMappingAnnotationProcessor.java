package kr.co.jhta.mvc.annotation.parser;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kr.co.jhta.mvc.annotation.RequestMapping;

public class RequestMappingAnnotationProcessor {

	public static Map<String, MethodInfo> process(Set<Class<?>> clazz) {
		Map<String, MethodInfo> requestMappingMethodMap = new HashMap<String, MethodInfo>();
		
		for (Class clz : clazz) {
			String className = clz.getName();
			Set<Method> methodSet = getRequestMappingMethod(clz);
			for (Method method : methodSet) {
				String path = getPath(method);
				if (requestMappingMethodMap.containsKey(path)) {
					throw new RuntimeException("요청 URI ["+path+"]가 중복되었습니다.");
				}
				MethodInfo methodInfo = new MethodInfo();
				methodInfo.setClassName(className);
				methodInfo.setPath(path);
				methodInfo.setMethod(method);
				
				requestMappingMethodMap.put(path, methodInfo);
			}
		}
		
		return requestMappingMethodMap;
	}
	
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
	
	private static String getPath(Method method) {
		RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
		return requestMappingAnnotation.value();
	}
	
}
