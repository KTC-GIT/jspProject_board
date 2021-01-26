package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbControl.AccountDao;

@WebServlet("/nickCheck")
public class NickDuplicateCheckController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		
		AccountDao dao = new AccountDao();
		
		String nick = request.getParameter("nick");
		int checkResult = dao.nickCheck(nick);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nickDuplicateCheck.jsp?checkResult="+checkResult+"&nick="+nick);
		rd.forward(request, response);
		
	}
}
