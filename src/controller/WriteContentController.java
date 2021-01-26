package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/write")
public class WriteContentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeContent.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		BoardDao dao = BoardDao.getInstance();
		BoardVo vo = new BoardVo();
		String title="";
		String id="";
		String pw="";
		String tag="";
		String content="";
		int memberwrite=0;
		
		String urlCheck = request.getHeader("referer");
		System.out.println(urlCheck);
		int idx = urlCheck.lastIndexOf("/");
		urlCheck = urlCheck.substring(idx);
		System.out.println(urlCheck);
		session.setAttribute("urlCheck", urlCheck);
		
		String ipAddress = request.getRemoteAddr();
		if(ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
			session.setAttribute("ip", ipAddress);
		}
		
			
		if(session.getAttribute("sId")!=null) {
			title = request.getParameter("title");
			id = (String)session.getAttribute("sNick");
			pw = (String)session.getAttribute("sPw");
			tag = request.getParameter("tag");
			content = request.getParameter("content");
			memberwrite=1;
		}
		else {
			title = request.getParameter("title");
			id = request.getParameter("id");
			pw = request.getParameter("pw");
			tag = request.getParameter("tag");
			content = request.getParameter("content");
		}
			
		
		vo.setTitle(title);
		vo.setId(id);
		vo.setPw(pw);
		vo.setTag(tag);
		vo.setContent(content);
		vo.setIp(ipAddress);
		vo.setMemberwrite(memberwrite);
		
		if(vo.getTitle().equals("")) {
			out.println("<script>");
			out.println("alert('제목을 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else if(vo.getId().equals("")) {
			out.println("<script>");
			out.println("alert('아이디를 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else if(vo.getPw().equals("")) {
			out.println("<script>");
			out.println("alert('비밀번호를 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else if(vo.getContent().equals("")) {
			out.println("<script>");
			out.println("alert('내용을 입력하세요');");
			out.println("history.back();");
			out.println("</script>");
		}
		else {
			dao.writeSave(vo);
			response.sendRedirect(request.getContextPath()+"/uploadServer");
		}
			
	}
	
}
