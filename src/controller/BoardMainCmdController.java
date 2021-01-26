package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbControl.Paging;
import dbVo.BoardVo;

public class BoardMainCmdController extends HttpServlet{
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		BoardDao dao = BoardDao.getInstance();
		List<BoardVo> list;
		Paging p = new Paging();
		
		String searchOption = request.getParameter("searchOption") == null? "":request.getParameter("searchOption");
		session.setAttribute("searchOption", searchOption);
		String searchBox = session.getAttribute("searchBox")==null?"":(String)session.getAttribute("searchBox");
		int idxCount=0;
		int pagingLimit = p.pagingLimit;
		int pageListLimit = p.pageListLimit;
		
		if(searchOption.equals("id")) {
			list = dao.selectById(searchBox);
			idxCount = p.getIdxCount(list);
		}
		else if(searchOption.equals("title")) {
			list = dao.selectByTitle(searchBox);
			idxCount = p.getIdxCount(list);
		}
		else if(searchOption.equals("content")) {
			list = dao.selectByContent(searchBox);
			idxCount = p.getIdxCount(list);
		}
		else {
			list = dao.selectList();
			idxCount = p.getIdxCount(list);
		}
		
		int lastPage = p.getLastPage();
		int pageCount = 1;
		if(request.getParameter("pageCount")==null || request.getParameter("pageCount").equals("0")) {
			pageCount = 1;
		}
		else if(Integer.parseInt(request.getParameter("pageCount"))>lastPage) {
			pageCount = lastPage;
		}
		else {
			pageCount = Integer.parseInt(request.getParameter("pageCount"));
		}
		
		
		int listCount = request.getParameter("listCount") == null? 1:Integer.parseInt(request.getParameter("listCount"));
		int listTotal = p.getListTotal(lastPage, pageListLimit);

		if(listCount==0) {
			listCount=1;
		}
		else if(listCount==listTotal) {
			listCount = listTotal;
		}
		
		int idx = (pageCount-1)*pagingLimit;
		
		if(searchOption.equals("id")) {
			list = dao.selectByIdLimit(searchBox,idx,pagingLimit);
		}
		else if(searchOption.equals("title")) {
			list = dao.selectByTitleLimit(searchBox,idx,pagingLimit);
		}
		else if(searchOption.equals("content")) {
			list = dao.selectByContentLimit(searchBox,idx,pagingLimit);
		}
		else {
			list = dao.selectListLimit(idx,pagingLimit);
		}
		
		String ipAddress = request.getRemoteAddr();
		if(ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		}
		
		session.setAttribute("sList", list);
		session.setAttribute("idxCount", idxCount);
		session.setAttribute("lastPage", lastPage);
		session.setAttribute("listTotal", listTotal);
		session.setAttribute("sPageCount", pageCount);
		session.setAttribute("sListCount", listCount);
		session.setAttribute("dao", BoardDao.getInstance());
		session.setAttribute("ip", ipAddress);
		
	}
}
