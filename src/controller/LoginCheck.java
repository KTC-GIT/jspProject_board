package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.AccountDao;
import dbVo.AccountVo;

@WebServlet("/loginCheck")
public class LoginCheck extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		AccountDao dao = new AccountDao();
		AccountVo vo = new AccountVo();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		
		int result = dao.loginCheck(id,pw);
		
		if(result == 2) {
			dao = new AccountDao();
			
			vo = dao.selectByIdPw(id,pw);
			session.setAttribute("sIdx", vo.getIdx());
			session.setAttribute("sId", vo.getId());
			session.setAttribute("sPw", vo.getPw());
			session.setAttribute("sNick", vo.getNick());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String strBirth = sdf.format(vo.getBirth());
			session.setAttribute("sBirth", strBirth);
			
			session.setAttribute("sEmail", vo.getEmail());
			session.setAttribute("sTel", vo.getTel());
			
			response.sendRedirect("board");
		}
		
		else {
			out.println("<script>");
			out.println("alert('아이디나 비밀번호가 다릅니다. 다시 확인해주세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		
	}
}
