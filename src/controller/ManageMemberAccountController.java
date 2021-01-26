package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbControl.AccountDao;
import dbControl.PagingForAccount;
import dbVo.AccountVo;


@WebServlet("/manageMember")
public class ManageMemberAccountController extends HttpServlet{
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
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		AccountDao dao = new AccountDao();
		List<AccountVo> vos;
		PagingForAccount pfa = new PagingForAccount();
		
		String id = (String)session.getAttribute("sId");
		
		int result = dao.idCheck(id);
		
		if(result==0) {
			dao = new AccountDao();
			
			vos = dao.selectAccount();
			int totalIdx = pfa.getTotalIdx(vos);
			int lastPage = pfa.getLastPage(totalIdx);
			int maxPageListCount=lastPage%pfa.pageLimit==0?lastPage/pfa.pageLimit:(lastPage/pfa.pageLimit)+1;
			int pageListCount = request.getParameter("pageListCount")==null? 1 : Integer.parseInt(request.getParameter("pageListCount"));
			session.setAttribute("sTotalIdx", totalIdx);
			session.setAttribute("sLastPage", lastPage);
			session.setAttribute("sMaxPageListCount", maxPageListCount);
			session.setAttribute("pfa", new PagingForAccount());
			
			if(vos!=null) {
				int idxLimit = pfa.idxLimit;
				int nowPage = request.getParameter("nowPage")==null ? pfa.nowPage:Integer.parseInt(request.getParameter("nowPage"));
				int startIdx = 0+(idxLimit*(nowPage-1));
				
				dao = new AccountDao();
				vos = dao.selectAccountPaging(startIdx,idxLimit);
				
				session.setAttribute("sVos", vos);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/manageMemberAccount.jsp?nowPage="+nowPage+"&pageListCount="+pageListCount);
				rd.forward(request,response);
			}
		
		}
		else {
			out.println("<script>");
			out.println("alert('잘못된 접근입니다!');");
			out.println("location.href='board';");
			out.println("</script>");
			
		}
	}
}
