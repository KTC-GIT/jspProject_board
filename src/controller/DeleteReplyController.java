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

import dbControl.BoardDao;
import dbVo.ReplyVo;

@WebServlet("/deleteR")
public class DeleteReplyController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteReply.jsp");
		rd.forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		BoardDao dao = BoardDao.getInstance();
		ReplyVo vo;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		int bidx = (int) session.getAttribute("sBidx");
		int ridx = (int) session.getAttribute("sRidx");
		String pw = request.getParameter("pw");
		
		vo = dao.selectReplybyRidx(ridx);
		
		int pageCount= (int)session.getAttribute("sPageCount");
		int listCount = (int)session.getAttribute("sListCount"); 
		
		if(vo.getPw().equals(pw)) {
			dao.deleteReply(ridx,pw);
			out.println("<script>");
			out.println("alert('댓글이 삭제되었습니다');");
			out.println("location.href = 'view?bidx="+bidx+"&pageCount="+pageCount+"&listCount="+listCount+"';");
			out.println("</script>");
		}
		else {
			out.println("<script>");
			out.println("alert('비밀번호가 다릅니다');");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}		
}
