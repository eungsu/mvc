package kr.co.jhta.mvc.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jhta.mvc.annotation.parser.ControllerAnnotationProcessor;
import kr.co.jhta.mvc.annotation.parser.MethodInfo;
import kr.co.jhta.mvc.annotation.parser.RequestMappingAnnotationProcessor;
import kr.co.jhta.mvc.view.ForwardView;
import kr.co.jhta.mvc.view.RedirectView;
import kr.co.jhta.mvc.view.View;

public class FrontControllerServlet extends HttpServlet { 
	
	private Map<String, Object> controllerObjectMap = new HashMap<String, Object>();
	private Map<String, MethodInfo> requestMappingMethodMap = new HashMap<String, MethodInfo>();
	
	@Override
	public void init() throws ServletException {
		
		ServletConfig config = this.getServletConfig();
		try {
			String packages = config.getInitParameter("packages");
		
			Set<Class<?>> clazz = ControllerAnnotationProcessor.scanClass(packages);
			for (Class<?> claz : clazz) {
				Object object = claz.newInstance();
				controllerObjectMap.put(claz.getName(), object);
			}
			
			requestMappingMethodMap = RequestMappingAnnotationProcessor.process(clazz);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		if (!contextPath.equals("/")) {
			requestURI = requestURI.replace(contextPath, "");
		}
		
		if (!requestMappingMethodMap.containsKey(requestURI)) {
			throw new ServletException("요청 URI ["+requestURI+"]와 매핑되는 메소드가 존재하지 않습니다.");
		}
		
		MethodInfo methodInfo = requestMappingMethodMap.get(requestURI);
		ModelAndView modelAndView = null;
		try {
			modelAndView = (ModelAndView) methodInfo.getMethod().invoke(controllerObjectMap.get(methodInfo.getClassName()), request, response);
		} catch (Exception e) {
			throw new ServletException("컨트롤러 메소드 ["+ methodInfo.getMethod().getName()+"] 수행중 오류가 발생하였습니다.", e);
		}
		
		if (modelAndView == null) {
			throw new ServletException("컨트롤러 메소드 ["+methodInfo.getMethod().getName()+"] 은 ModelAndView가 비어있습니다.");
		}
		
		try {
			handleModelAndView(modelAndView, request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		
//		if (path.startsWith("redirect:")) {
//			path = path.replace("redirect:", "");
//			response.sendRedirect(path);
//		} else {
//			path = "WEB-INF/views/" + path;
//			request.getRequestDispatcher(path).forward(request, response);
//		}
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
