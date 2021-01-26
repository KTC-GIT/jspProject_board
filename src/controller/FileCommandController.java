package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dbControl.FileDao;
import dbVo.FileVo;

@WebServlet("/fileCommand")		//각종 FileDao에 지시할 것을 ajax로 처리할때 쓰는 controller
public class FileCommandController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		List<FileVo> list;
		FileVo vo = new FileVo();
		FileDao dao = new FileDao();
		
		String cmd = request.getParameter("cmd");
		
		if(cmd.equals("tmpSelect")) {
			list = dao.tmpSelect();
			
			for(FileVo tmpVo: list) {
				String filePath = tmpVo.getFilePath();
			try {
				JSONObject jobj = new JSONObject();
				jobj.put("img", filePath);
				
				response.setContentType("application/json");
				System.out.println(jobj.toString());
				response.getWriter().print(jobj.toJSONString());
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
		}
	}
}
