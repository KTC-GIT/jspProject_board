package controller;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/view")
public class ViewController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		ViewCommand cmd = new ViewCommand();
		cmd.execute(request, response);
		
		String rListCount = request.getParameter("rListCount")==null? "" : request.getParameter("rListCount");
		int bidx = Integer.parseInt(request.getParameter("bidx"));
		
		String ipAddress = request.getRemoteAddr();
		if(ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
			session.setAttribute("ip", ipAddress);
		}
		
		if(!(rListCount.equals(""))) {
			BoardDao dao = BoardDao.getInstance();
			BoardVo vo = new BoardVo();
			vo = dao.selectById(bidx);
			
			int hits = vo.getHits()-1;
			
			dao.maintainHits(bidx, hits);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/viewContent.jsp");
		rd.forward(request, response);
	}
}
