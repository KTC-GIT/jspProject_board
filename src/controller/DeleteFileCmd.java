package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbControl.FileDao;
import dbVo.FileVo;

public class DeleteFileCmd extends HttpServlet{
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		
		String fileName = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		int bidx = 0;
		if(request.getParameter("bidx")!=null) {
			bidx = Integer.parseInt(request.getParameter("bidx"));
		}
		else if(request.getAttribute("bidx")!=null) {
			bidx = (int)request.getAttribute("bidx");
		}
		else {
			bidx = 0;
		}
		
		ServletContext contextPath = request.getServletContext();
		String tmpDir = contextPath.getRealPath("/temp");
		String seperator = File.separator;
		
		FileDao dao = new FileDao();
		
		if(fileName.equals("")) {
			if(bidx==0) {
				List<FileVo> list = dao.selectTempFile();
				for(FileVo vo:list) {
					File file = new File(tmpDir+seperator+vo.getFileName());
					if(file.exists()) {
						file.delete();
						System.out.println(vo.getFileName()+" 삭제되었습니다.");
					}
				}
				
				dao.deleteTempFile();
			}
			else {
				List<FileVo> list = dao.selectByBidx(bidx);
				for(FileVo vo:list) {
					File file = new File(tmpDir+seperator+vo.getFileName());
					if(file.exists()) {
						file.delete();
						System.out.println(vo.getFileName()+" 삭제되었습니다.");
					}
				}
				DeleteFromServerCmd cmd = new DeleteFromServerCmd();
				cmd.setBidx(bidx);
				cmd.execute();
				dao.deleteFileByBidx(bidx);
			}
		}
		else {
			File file = new File(tmpDir+seperator+fileName);
			if(file.exists()) {
				file.delete();
				System.out.println(fileName+" 삭제되었습니다.");
			}
			if(bidx==0) {
				dao.deleteFileByFileName(0,fileName);
			}
			else {
				DeleteFromServerCmd cmd = new DeleteFromServerCmd();
				cmd.setBidx(bidx);
				cmd.setFileName(fileName);
				cmd.execute();
				dao.deleteFileByFileName(bidx, fileName);
			}
		}
		
	}
}

