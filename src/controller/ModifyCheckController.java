package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/modifyCheck")
public class ModifyCheckController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/modifyCheck.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		BoardDao dao = BoardDao.getInstance();
		BoardVo vo;
		
		int bidx = (int)session.getAttribute("sBidx");
		String nick = session.getAttribute("sNick")==null?"":(String)session.getAttribute("sNick");
		vo = dao.selectById(bidx);
		
		if(nick.equals(vo.getId())) {
			response.sendRedirect("modify?bidx="+bidx);
		}
		
		else {
			String pw = request.getParameter("pw");
			
			if(vo.getPw().equals(pw)) {
				response.sendRedirect("modify?bidx="+bidx);
			}
			else {
				out.println("<script>");
				out.println("alert('비밀번호가 다릅니다.');");
				out.println("history.back()");
				out.println("</script>");
			}
		}
		
	}
}
