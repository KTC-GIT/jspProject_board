package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbVo.ReplyVo;

@WebServlet("/writeR")
public class WriteReplyCheckController extends HttpServlet{
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
		
		BoardDao dao = BoardDao.getInstance();
		ReplyVo vo = new ReplyVo();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		
		int pageCount = (int)session.getAttribute("sPageCount");
		int listCount = (int)session.getAttribute("sListCount");
		int rListCount = 1;
		int rPageCount = 1;
		String order = (String)session.getAttribute("sOrder");
		String searchOption = request.getParameter("searchOption");

		int bidx;
		String id;
		String pw;
		String content;
		int memberwrite=0;
		
		if(session.getAttribute("sId")!=null) {
			bidx = (int) session.getAttribute("sBidx");
			id = (String)session.getAttribute("sNick");
			pw = (String)session.getAttribute("sPw");
			content = request.getParameter("content");
			memberwrite=1;
		}
		else {
			bidx = (int) session.getAttribute("sBidx");
			id = request.getParameter("id");
			pw = request.getParameter("pw");
			content = request.getParameter("content");
		}
		
		if(id.equals("")) {
			out.println("<script>");
			out.println("alert('아이디를 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else if(pw.equals("")) {
			out.println("<script>");
			out.println("alert('비밀번호를 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else if(content.equals("")) {
			out.println("<script>");
			out.println("alert('내용을 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else {
			String ipAddress = request.getRemoteAddr();
			if(ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
				InetAddress inetAddress = InetAddress.getLocalHost();
				ipAddress = inetAddress.getHostAddress();
				session.setAttribute("ip", ipAddress);
			}
			
			vo.setBidx(bidx);
			vo.setId(id);
			vo.setPw(pw);
			vo.setIp(ipAddress);
			vo.setContent(content);
			vo.setMemberwrite(memberwrite);
			dao.writeReply(vo);
			response.sendRedirect("view?bidx="+bidx+"&searchOption="+searchOption+"&order="+order+"&pageCount="+pageCount+"&listCount="+listCount+"&rListCount="+rListCount+"&rPageCount="+rPageCount);
		}
	}
}
