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

@WebServlet("/manageAccount")
public class ManageAccountController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/boardMain.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		AccountDao dao = new AccountDao();
		int result=0;
		
		if(request.getParameterValues("id")==null) {
			out.println("<script>");
			out.println("alert('아이디를 선택하세요!');");
			out.println("history.back();");
			out.println("</script>");
		}
		else {
			String id[] = request.getParameterValues("id");
			String cmd = request.getParameter("cmd");
			
			if(cmd.equals("1")) {
				for(int i=0;i<id.length;i++) {
					if(!(id[i].equals("admin"))) {
						result = dao.deleteAccountForAdmin(id[i]);
						dao = new AccountDao();
					}
				}
				
				if(result>=1) {
					out.println("<script>");
					out.println("alert('삭제완료');");
					out.println("location.href = 'manageMember';");
					out.println("</script>");
				}
				else {
					out.println("<script>");
					out.println("alert('처리오류');");
					out.println("history.back();");
					out.println("</script>");
				}
			}
			else if(cmd.equals("2")) {
				for(int i=0;i<id.length;i++) {
					if(!(id[i].equals("admin"))) {
						result = dao.accountEnableForAdmin(id[i]);
						dao = new AccountDao();
					}
				}
				
				if(result>=1) {
					out.println("<script>");
					out.println("alert('탈퇴요청 취소완료');");
					out.println("location.href = 'manageMember';");
					out.println("</script>");
				}
				else {
					out.println("<script>");
					out.println("alert('처리오류');");
					out.println("history.back();");
					out.println("</script>");
				}
			}
			else if(cmd.equals("3")) {
				for(int i=0;i<id.length;i++) {
					if(!(id[i].equals("admin"))) {
						result = dao.accountDisableForAdmin(id[i]);
						dao = new AccountDao();
					}
				}
				
				if(result>=1) {
					out.println("<script>");
					out.println("alert('비활성화 완료');");
					out.println("location.href = 'manageMember';");
					out.println("</script>");
				}
				else {
					out.println("<script>");
					out.println("alert('처리오류');");
					out.println("history.back();");
					out.println("</script>");
				}
			}
		}
		
	}
}
	
	

