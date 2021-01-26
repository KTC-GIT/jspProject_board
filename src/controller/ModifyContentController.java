package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/modify")
public class ModifyContentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifyContent.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		BoardDao dao = BoardDao.getInstance();
		int bidx = (int)session.getAttribute("sBidx");
		String pw="";
		
		String urlCheck = request.getHeader("referer");		//클릭해서 들어온 경우만 url알 수 있음.
		System.out.println(urlCheck);
		int idx = urlCheck.lastIndexOf("/");
		urlCheck = urlCheck.substring(idx);
		System.out.println(urlCheck);
		session.setAttribute("urlCheck", urlCheck);
		
		if(session.getAttribute("sId")!=null) {
			pw = (String)session.getAttribute("sPw");
		}
		else {
			pw = request.getParameter("pw");
		}
		
		String title = request.getParameter("title");
		String tag = request.getParameter("tag");
		String content = request.getParameter("content");
		
		dao.modifyContent(bidx,title,pw,tag,content);
		
		BoardVo vo = dao.selectById(bidx);
		int hits = vo.getHits()-1;
		dao.maintainHits(bidx,hits);
		
		response.sendRedirect("uploadServer");
	}
}
