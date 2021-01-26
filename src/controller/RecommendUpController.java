package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/recommendUp")
public class RecommendUpController extends  HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		BoardDao dao = BoardDao.getInstance();
		BoardVo vo;
		HttpSession session = request.getSession();
		
		int bidx = (int) session.getAttribute("sBidx");
		
		vo = dao.selectById(bidx);
		int recommend = vo.getRecommend()+1;
		int result = dao.recommendUp(bidx,recommend);
		
		if(result==1) {
			response.setContentType("application/json");
			JSONObject jobj = new JSONObject();
			jobj.put("recommend", recommend);
			out.println(jobj.toString());
			System.out.println("jobj: "+jobj.toString());
		}
		
	}
}
