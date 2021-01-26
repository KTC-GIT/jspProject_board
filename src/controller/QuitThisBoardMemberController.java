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

@WebServlet("/requestDeleteAccount")
public class QuitThisBoardMemberController extends HttpServlet{
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		AccountDao dao = new AccountDao();
		
		String id = (String)session.getAttribute("sId");
		String pw = request.getParameter("pw");
		
		int result = dao.accountDisable(id,pw);
		
		if(result==1) {
			session.removeAttribute("sId");
			session.removeAttribute("sNick");
			session.removeAttribute("sBirth");
			session.removeAttribute("sEmail");
			session.removeAttribute("sTel");
			
			out.println("<script>");
			out.println("alert('탈퇴요청 처리되었습니다');");
			out.println("location.href = 'board';");
			out.println("</script>");
		}
		else {
			out.println("<script>");
			out.println("alert('에러가 발생하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}
}
