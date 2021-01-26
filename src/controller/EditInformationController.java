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

import dbControl.AccountDao;

@WebServlet("/editAccount")
public class EditInformationController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accountInformation.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String pw = request.getParameter("pw")==null?"":request.getParameter("pw");
		String sPw = (String)session.getAttribute("sPw");
		
		if(sPw.equals(pw)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/editInformation.jsp");
			rd.forward(request, response);
		}
		else {
			out.println("<script>");
			out.println("alert('비밀번호가 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
