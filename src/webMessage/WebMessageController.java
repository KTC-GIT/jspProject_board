package webMessage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.ms")
public class WebMessageController extends HttpServlet{
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
		
		Command cmd=null;
		String viewPage = "";
		
		String uri = request.getRequestURI();
		String com = uri.substring(uri.lastIndexOf("/"));
		
		if(com.equals("/message.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request,response);
			viewPage = "/WEB-INF/webMessage/webMessage.jsp";
		}
		else if(com.equals("/viewMessage.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request, response);
			viewPage = "/WEB-INF/webMessage/viewMessage.jsp";
		}
		else if(com.equals("/messageCheck.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request, response);
			return;
		}
		else if(com.equals("/writeMessage.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request, response);
			viewPage = "/WEB-INF/webMessage/writeMessage.jsp";
		}
		else if(com.equals("/writeMsgCheck.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request, response);
			return;
		}else if(com.equals("/goToTrashCan.ms")) {
			cmd = new WebMessageCommand();
			cmd.execute(request, response);
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);
	}
}
