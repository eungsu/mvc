package kr.co.jhta.mvc.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.jhta.mvc.annotation.method.RequestMethod;
import kr.co.jhta.mvc.annotation.parser.ControllerAnnotationProcessor;
import kr.co.jhta.mvc.annotation.parser.MethodDetail;
import kr.co.jhta.mvc.annotation.parser.MethodKey;
import kr.co.jhta.mvc.annotation.parser.RequestMappingAnnotationProcessor;
import kr.co.jhta.mvc.view.ForwardView;
import kr.co.jhta.mvc.view.RedirectView;
import kr.co.jhta.mvc.view.View;

public class FrontControllerServlet extends HttpServlet { 
	private static final long serialVersionUID = -5311977520092694652L;

	private Map<String, Object> controllerObjectMap = new HashMap<String, Object>();
	private Map<MethodKey, MethodDetail> requestMappingMethodMap = new HashMap<MethodKey, MethodDetail>();
	
	@Override
	public void init() throws ServletException {
		
		ServletConfig config = this.getServletConfig();
		try {
			String packages = config.getInitParameter("packages");
		
			Set<Class<?>> clazz = ControllerAnnotationProcessor.scanClass(packages);
			for (Class<?> claz : clazz) {
				Object object = claz.getDeclaredConstructor().newInstance();
				controllerObjectMap.put(claz.getName(), object);
			}
			
			requestMappingMethodMap = RequestMappingAnnotationProcessor.process(clazz);
			
			Set<MethodKey> keys = requestMappingMethodMap.keySet();
			for (MethodKey key : keys) {
				System.out.println("["+key.getPath()+"] " + requestMappingMethodMap.get(key));
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String method = request.getMethod();
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		requestURI = getRequestURI(contextPath, requestURI);
		
		MethodKey methodKey = new MethodKey(RequestMethod.valueOf(method), requestURI);
		
		if (!requestMappingMethodMap.containsKey(methodKey)) {
			throw new ServletException("요청 URI ["+requestURI+"]와 매핑되는 메소드가 존재하지 않습니다.");
		}
		
		MethodDetail methodInfo = requestMappingMethodMap.get(methodKey);
		ModelAndView modelAndView = null;
		try {
			modelAndView = (ModelAndView) methodInfo.getMethod().invoke(controllerObjectMap.get(methodInfo.getClassName()), request, response);
		} catch (Exception e) {
			throw new ServletException("컨트롤러 메소드 ["+ methodInfo.getMethod().getName()+"] 수행중 오류가 발생하였습니다.", e);
		}
		
		if (modelAndView == null) {
			throw new ServletException("컨트롤러 메소드 ["+methodInfo.getMethod().getName()+"] 은 ModelAndView가 비어있습니다.");
		}
		if (modelAndView.getView() == null && modelAndView.getViewName() == null) {
			throw new ServletException("컨트롤러 메소드 ["+methodInfo.getMethod().getName()+"] 은 ModelAndView의 viewName이 비어있습니다.");
		}
		
		try {
			handleModelAndView(modelAndView, request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private String getRequestURI(String contextPath, String requestURI) {
		
		if (contextPath.equals("/")) {
			return requestURI;
		}
		
		return requestURI.substring(contextPath.length());
	}
	
	private void handleModelAndView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws Exception {
		View view = modelAndView.getView();
		Map<String, Object> model = modelAndView.getModel();
		
		if (view == null) {
			String path = modelAndView.getViewName();
			if (path.startsWith("redirect:")) {
				view = new RedirectView( path.replace("redirect:", ""));
			} else {
				view = new ForwardView(path);
			}
		}
		
		view.render(model, request, response);
	}
}
