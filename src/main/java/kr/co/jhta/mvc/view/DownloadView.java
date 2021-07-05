package kr.co.jhta.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DownloadView implements View {
	
	private String directory;
	private String filename;
	
	public DownloadView() {}
	public DownloadView(String directory, String filename) {
		this.directory = directory;
		this.filename = filename;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("application/octet-stream");
		OutputStream out = response.getOutputStream();
		File file = new File(directory, filename);
		FileInputStream in = new FileInputStream(file);
		
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "utf-8"));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		
		byte[] buf = new byte[1024*8];
		int len = 0;
		
		while ((len=in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		
		in.close();
	}
}
