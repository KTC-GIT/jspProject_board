package realTimeSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import webMessage.Command;


public class realTimeSearchCmd implements Command{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		RealTimeDao dao = new RealTimeDao();
		List<RealTimeSearchVo> list = null;
		
		String searchField = request.getParameter("searchField")==null?"":request.getParameter("searchField");
		String searchName = request.getParameter("searchName")==null?"":request.getParameter("searchName");
		
		if(searchField.equals("") && searchName.equals("")) {
			list = dao.selectList();
			request.setAttribute("list", list);
		}
		else {
			list = dao.userSearch(searchField,searchName);
			response.setContentType("application/json");
			JSONArray jArray = new JSONArray();
			for(RealTimeSearchVo vo: list) {
				JSONObject jobj = new JSONObject();
				
				jobj.put("idx", vo.getIdx());
				jobj.put("name", vo.getName());
				jobj.put("age", vo.getAge());
				jobj.put("gender", vo.getGender());
				jobj.put("address", vo.getAddress());
				
				jArray.add(jobj);
			}
			out.println(jArray.toString());
		}
	}
}
