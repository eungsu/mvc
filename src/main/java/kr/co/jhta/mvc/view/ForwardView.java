package kr.co.jhta.mvc.view;

import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForwardView implements View {
	
	private String path;
	private String prefix = "/WEB-INF/views/";
	
	public ForwardView(String path) {
		this.path = path;
	}
	
	@Override
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Set<String> keySet = model.keySet();
		for (String key : keySet) {
			request.setAttribute(key, model.get(key));
		}
		
		request.getRequestDispatcher(prefix + path).forward(request, response);
		
	}
}
