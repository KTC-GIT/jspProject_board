package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbVo.BoardVo;

@WebServlet("/orderReply")
public class OrderReplyController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		BoardDao dao = BoardDao.getInstance();
		
		int bidx = (int) session.getAttribute("sBidx");
		String order = request.getParameter("order");
		session.setAttribute("sOrder", order);
		
		int pageCount= (int)session.getAttribute("sPageCount");
		int listCount = (int)session.getAttribute("sListCount"); 
		int rPageCount=1;
		int rListCount=1;
		
		session.setAttribute("sOrder", order);
		BoardVo vo = dao.selectById(bidx);
		int hits = vo.getHits()-1;
		dao.maintainHits(bidx,hits);
		
		response.sendRedirect("view?bidx="+bidx+"&order="+order+"&pageCount="+pageCount+"&listCount="+listCount+"&rPageCount="+rPageCount+"&rListCount="+rListCount);
	}
}
