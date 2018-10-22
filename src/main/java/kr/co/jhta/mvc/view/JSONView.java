package kr.co.jhta.mvc.view;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONView implements View {

	@Override
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();

		Set<String> keySet = model.keySet();
		if (keySet.size() != 1) {
			out.println(gson.toJson(model));
		} else {
			for (String key : keySet) {
				out.println(gson.toJson(model.get(key)));
			}
		}
	}
}
