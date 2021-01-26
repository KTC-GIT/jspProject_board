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

import dbControl.AccountDao;
import dbControl.BoardDao;
import dbControl.FileDao;
import dbVo.AccountVo;
import dbVo.BoardVo;

@WebServlet("/delete")
public class DeleteContentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteContent.jsp");
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
		BoardVo vo;
		
		FileDao fDao = new FileDao();
		DeleteFileCmd cmd = new DeleteFileCmd();
		
		String id = (String) session.getAttribute("sId");
		int bidx;
		String pw;
		
		
		if(id!=null) {
			if(id.equals("admin")) {
				if(request.getParameterValues("val")==null) {
					out.println("<script>");
					out.println("alert('삭제할 게시물을 선택하세요!');");
					out.println("history.back();");
					out.println("</script>");
				}
				else {
					
					String val[] = request.getParameterValues("val");
					int bidxArr[] = new int[val.length];
					String searchOption="";
					String pageCount="";
					String listCount="";
					boolean sw=true;
					
					
					for(int i=0;i<val.length;i++) {
						String valArr[] = val[i].split("/");
						for(int j=0;j<valArr.length;j++) {
							bidxArr[i] = Integer.parseInt(valArr[0]);
							if(fDao.selectNumberOfFile(bidxArr[i])!=0) {
								request.setAttribute("bidx", bidxArr[i]);
								cmd.execute(request, response);		//손을 봐야한다.
								fDao.deleteFileByBidx(bidxArr[i]);
							}
							dao.deleteContentForAdmin(bidxArr[i]);
							if(sw) {
								searchOption = valArr[1];
								pageCount = valArr[2];
								listCount = valArr[3];
								sw=false;
							}
						}
					}
					
					out.println("<script>");
					out.println("alert('삭제되었습니다!');");
					out.println("location.href='board?searchOption="+searchOption+"&pageCount="+pageCount+"&listCount="+listCount+"';");
					out.println("</script>");
					
				}
			}
			
			else {
				AccountVo accountVo = new AccountVo();
				AccountDao accountDao = new AccountDao();
				bidx = (int) session.getAttribute("sBidx");
				pw = (String) session.getAttribute("sPw");
				String nick = request.getParameter("nick");
				
				accountVo = accountDao.selectByIdPw(id, pw);
				if(accountVo.getNick().equals(nick)) {
					request.setAttribute("bidx", bidx);
					cmd.execute(request, response);
					fDao.deleteFileByBidx(bidx);
					dao.deleteContent(bidx,pw);
					out.println("<script>");
					out.println("alert('삭제되었습니다');");
					out.println("location.href = 'board';");
					out.println("</script>");
				}
				else {
					out.println("<script>");
					out.println("alert('잘못된 접근.');");
					out.println("history.back();");
					out.println("</script>");
				}
				
			}
		}
		else {
			bidx = (int) session.getAttribute("sBidx");
			pw = request.getParameter("pw");
			
			vo = dao.selectById(bidx);
			
			if(vo.getPw().equals(pw)) {
				request.setAttribute("bidx", bidx);
				cmd.execute(request, response);
				fDao.deleteFileByBidx(bidx);
				dao.deleteContent(bidx,pw);
				out.println("<script>");
				out.println("alert('삭제되었습니다');");
				out.println("location.href = 'board';");
				out.println("</script>");
			}
			else {
				out.println("<script>");
				out.println("alert('비밀번호가 다릅니다. 다시 입력하세요.');");
				out.println("history.back();");
				out.println("</script>");
			}
		}
	}
}
