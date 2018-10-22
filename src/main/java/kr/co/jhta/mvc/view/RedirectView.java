package kr.co.jhta.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectView implements View {
	
	private String path;
	
	public RedirectView(String path) {
		this.path = path;
	}

	@Override
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.sendRedirect(path);
	}
}
