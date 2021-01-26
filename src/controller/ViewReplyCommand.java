package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbControl.Paging;
import dbVo.ReplyVo;

public class ViewReplyCommand extends HttpServlet{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		BoardDao dao = BoardDao.getInstance();
		List<ReplyVo> rList;
		Paging pb = new Paging();
		
		int bidx = (int) session.getAttribute("sBidx");
		String order = request.getParameter("order") == null? "" : request.getParameter("order");
		
		int count = dao.countReply(bidx);
		
		
		pb.getRidxCount(dao.viewReply(bidx));
		int lastPage = pb.getLastPage();
		
		int rPageCount = 1;
		if(request.getParameter("rPageCount") == null || request.getParameter("rPageCount").equals("0")){
			rPageCount = 1;
		}
		else if(Integer.parseInt(request.getParameter("rPageCount"))>lastPage){
			rPageCount = lastPage;
		}
		else{
			rPageCount = Integer.parseInt(request.getParameter("rPageCount"));
		}
		
		
		int rListCount = request.getParameter("rListCount") == null? 1 : Integer.parseInt(request.getParameter("rListCount"));		//section의 구획 1~5 6~10..
		int rListTotal = pb.getListTotal(lastPage, pb.pageListLimit);
		
		if(rListCount==0){		//0이 되면 강제적으로 1값을 가지게 함.
			rListCount = 1;
		}
		else if(rListCount>rListTotal){	//listCount가 listTotal보다 커지지 못하게..
			rListCount = rListTotal;
		}
		
		int pagingLimit = pb.pagingLimit;
		int start = ((rPageCount-1)*pagingLimit)<0?0:(rPageCount-1)*pagingLimit;
		
		if(order.equals("writeorder")){
			rList= dao.viewReplyLimit(bidx,start,pagingLimit);
		}
		else if(order.equals("recentorder")){
			rList= dao.viewReplyDescLimit(bidx,start,pagingLimit);
		}
		else{
			rList= dao.viewReplyLimit(bidx,start,pagingLimit);
		}
		
		session.setAttribute("count", count);
		session.setAttribute("order", order);
		session.setAttribute("rList", rList);
		session.setAttribute("rPageCount", rPageCount);
		session.setAttribute("rListCount", rListCount);
		session.setAttribute("rLastPage", lastPage);
		session.setAttribute("rListTotal", rListTotal);
		session.setAttribute("pageListLimit", pb.pageListLimit);
		
	}
}
