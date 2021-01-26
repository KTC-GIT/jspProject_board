package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String nick = (String)session.getAttribute("sNick");
		
		session.removeAttribute("sIdx");
		session.removeAttribute("sId");
		session.removeAttribute("sPw");
		session.removeAttribute("sNick");
		session.removeAttribute("sBirth");
		session.removeAttribute("sEmail");
		session.removeAttribute("sTel");
		
		out.println("<script>");
		out.println("alert('"+nick+"님 로그아웃 되었습니다.');");
		out.println("location.href='board';");
		out.println("</script>");
		
	}
}
