package kr.co.jhta.mvc.view;

import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JSONView implements View {

	@Override
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting()
			.setDateFormat("yyyy-MM-dd")
			.serializeNulls();
		Gson gson = builder.create();

		if (model.size() == 1) {
			String key = new TreeSet<>(model.keySet()).first();
			Object value = model.get(key);
			if (isPrimitiveValue(value)) {
				out.println(gson.toJson(model));
			} else {
				out.print(gson.toJson(value));
			}
		} else {
			out.println(gson.toJson(model));
		}
	}
	
	private boolean isPrimitiveValue(Object value) {
		if (value instanceof Boolean) {
			return true;
		}
		if (value instanceof Byte) {
			return true;
		}
		if (value instanceof Short) {
			return true;
		}
		if (value instanceof Integer) {
			return true;
		}
		if (value instanceof Long) {
			return true;
		}
		if (value instanceof Float) {
			return true;
		}
		if (value instanceof Double) {
			return true;
		}
		if (value instanceof Character) {
			return true;
		}
		if (value instanceof String) {
			return true;
		}
		return false;
	}
}
