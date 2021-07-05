package kr.co.jhta.mvc.servlet;

import java.util.HashMap;
import java.util.Map;

import kr.co.jhta.mvc.view.View;

public class ModelAndView {

	private Map<String, Object> model = new HashMap<>();
	private String viewName;
	private View view;
	
	public ModelAndView() {}
	
	public ModelAndView(String viewName) {
		this.viewName = viewName;
	}

	Map<String, Object> getModel() {
		return model;
	}
	public void addAttribute(String name, Object value) {
		model.put(name, value);
	}
	
	String getViewName() {
		return viewName;
	}
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
}
