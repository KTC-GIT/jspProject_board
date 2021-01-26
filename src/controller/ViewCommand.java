package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.BoardDao;
import dbControl.FileDao;
import dbControl.Paging;
import dbVo.BoardVo;
import dbVo.FileVo;

public class ViewCommand extends HttpServlet{
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		
		BoardDao dao = BoardDao.getInstance();
		BoardVo vo;
		Paging pb = new Paging();
		
		int bidx = request.getParameter("bidx")==null?(int)session.getAttribute("sBidx"):Integer.parseInt(request.getParameter("bidx"));
		session.setAttribute("sBidx", bidx);
		String searchOption = (String)session.getAttribute("searchOption");
		
		vo = dao.selectById(bidx);									//get방식으로 넘긴 bidx값으로 게시물 찾기
		session.setAttribute("vo", vo);
		int hits = vo.getHits()+1;									//조회수를 올린다
		dao.hitsUpdate(bidx,hits);
		session.setAttribute("hits", hits);
		
		session.setAttribute("memberwrite", vo.getMemberwrite());
		session.setAttribute("id", vo.getId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd  HH:mm:ss");
		String strDate = "";
		Timestamp tDate = vo.getWritedate();
		strDate = sdf.format(tDate);
		session.setAttribute("strDate", strDate);
		
		int count = dao.countReply(bidx);
		session.setAttribute("count", count);
		
		int lastPage = pb.getLastPage();
		
		FileDao fDao = new FileDao();
		
		int result = fDao.selectNumberOfFile(bidx);
		session.setAttribute("fCount", result);
		
		if(result!=0){
			List<FileVo> list = fDao.selectByBidx(bidx);
			session.setAttribute("fList", list);
		}
	}
}
