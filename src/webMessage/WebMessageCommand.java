package webMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class WebMessageCommand extends HttpServlet implements Command{
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		int result=0;
		
		int msw = request.getParameter("msw")==null?9:Integer.parseInt(request.getParameter("msw"));
		String[] idxCheck = request.getParameterValues("idxCheck");
		int idx = request.getParameter("idx")==null?0:Integer.parseInt(request.getParameter("idx"));
		String receiveSw = request.getParameter("receiveSw")==null?"":request.getParameter("receiveSw");
		String id = (String)session.getAttribute("sId");
		
		//-----메시지 보내기에 필요한 것들-------
		String receiveId = request.getParameter("receiveId")==null?"":request.getParameter("receiveId");
		String title=request.getParameter("title")==null?"":request.getParameter("title");
		String content=request.getParameter("content")==null?"":request.getParameter("content");
		//-----------------------------
		
		WebMessageDao dao = new WebMessageDao();
		WebMessageVo vo = new WebMessageVo();
		List<WebMessageVo> list = null;
		
		if(!(receiveId.equals("")) && !(title.equals("")) && !(content.equals(""))) {
			vo.setReceiveId(receiveId);
			vo.setSendId(id);
			vo.setTitle(title);
			vo.setContent(content);
			
			result = dao.writeMessage(vo);
			if(result==1) {
				System.out.println("send message success");
			}
			else {
				System.out.println("send message failed");
			}
		}
		
		if(msw==1 || msw==2 || msw==5 || msw==9) {
			list = dao.selectList(id);
			session.setAttribute("msgList", list);
		}
		else if(msw==3 || msw==4) {
			list = dao.selectListBySendId(id);
			session.setAttribute("msgList", list);
		}
		
		if(receiveSw.equals("r")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowStr = sdf.format(now);
			Timestamp receiveDate = Timestamp.valueOf(nowStr);
			result = dao.updateReceiveSwR(receiveSw,receiveDate,idx);
			
			if(result==1) {
				System.out.println("success");
			}
			else {
				System.out.println("failed");
			}
		}
		else if(receiveSw.equals("g")) {
			if(idxCheck!=null) {
				for(int i=0;i<idxCheck.length;i++) {
					dao = new WebMessageDao();
					
					idx = Integer.parseInt(idxCheck[i]);
					result = dao.updateReceiveSwG(receiveSw, idx);
					
					if(result==1) {
						System.out.println("success");
					}
					else {
						System.out.println("failed");
					}
				}
			}
			else {
				result = dao.updateReceiveSwG(receiveSw,idx);
				
				if(result==1) {
					System.out.println("success");
				}
				else {
					System.out.println("failed");
				}
			}
		}
		else if(idx!=0) {
			vo = dao.selectByIdx(idx);
			session.setAttribute("msgVo", vo);
		}
	}
}
