package kr.co.jhta.mvc.view;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface View {

	void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
