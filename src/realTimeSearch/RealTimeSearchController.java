package realTimeSearch;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webMessage.Command;

@WebServlet("/realtime")
public class RealTimeSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		Command cmd = new realTimeSearchCmd();
		cmd.execute(request, response);
		
		RequestDispatcher rd = request.getRequestDispatcher("/realTimeSearch/realTimeSearchList.jsp");
		rd.forward(request, response);
	}
	
}
