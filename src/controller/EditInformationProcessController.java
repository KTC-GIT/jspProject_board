package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.AccountDao;
import dbVo.AccountVo;

@WebServlet("/editAccountProcess")
public class EditInformationProcessController extends HttpServlet{
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
		AccountVo vo = new AccountVo();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String strBirth = year+"-"+month+"-"+day;
		String birthStr = year+"-"+month+"-"+day+" 00:00:00";
		Timestamp birth = Timestamp.valueOf(birthStr);
		
		String emailId = request.getParameter("emailId");
		String emailDomain = request.getParameter("emailDomain");
		String email = emailId+"@"+emailDomain;
		
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String tel = tel1+"-"+tel2+"-"+tel3;
		
		vo.setId(id);
		vo.setPw(pw);
		vo.setBirth(birth);
		vo.setEmail(email);
		vo.setTel(tel);
		
		int result = dao.updateAccountInfo(vo);
		
		if(result==1) {
			session.setAttribute("sBirth", strBirth);
			session.setAttribute("sEmail", email);
			session.setAttribute("sTel", tel);
			
			out.println("<script>");
			out.println("alert('수정되었습니다!');");
			out.println("location.href = 'mypage';");
			out.println("</script>");
		}
		else {
			out.println("<script>");
			out.println("alert('오류가 발생하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}
}
