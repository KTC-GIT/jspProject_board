package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.ftp.FTPClient;

import dbControl.FileDao;
import dbVo.FileVo;

@WebServlet("/serverDownload")
public class ServerDownloadController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		int bidx = request.getParameter("bidx")==null? 0:Integer.parseInt(request.getParameter("bidx"));
		
		FileDao dao = new FileDao();
		List<FileVo> list = dao.selectByBidx(bidx);
		FTPClient ftp = null;
		
		for(FileVo vo: list) {
			
			String fileName = vo.getFileName();
			String hashCode = vo.getHashCode();
			String tmpDir = request.getServletContext().getRealPath("/temp");
			String seperator = File.separator;
			
			File downFile = new File(tmpDir+seperator+fileName);	//다운로드할 위치와 파일명 설정.
				
				try {
					if(!downFile.exists()) {								//다운할 위치에 파일이 존재 하지 않는다면 다운로드!
						ftp = new FTPClient();
						ftp.setControlEncoding("utf-8");		//언어설정
						
						ftp.connect("nas.monlini.pe.kr",2121);		//ip
						ftp.login("atom", "matal3");			//계정, 비밀번호
						ftp.enterLocalPassiveMode();			//수동형 설정  (수신 송신중 포트가 한개만 열려있기 때문에 수동형)
						ftp.changeWorkingDirectory("/web/");	//작업할 폴더로 이동.
						
						FileOutputStream output = null;
						
						try {
							output = new FileOutputStream(downFile);
							boolean isSuccess = ftp.retrieveFile(hashCode, output);		//다운로드
							System.out.println(ftp.getReplyString());
							System.out.println(ftp.getReplyCode());
							if(isSuccess) {
								System.out.println("server to temp folder download Success");
							}
							else {
								System.out.println("server to temp folder download failed");
							}
						}catch(IOException e) {
							System.out.println(e.getMessage());
						}
					}
				}catch(SocketException e) {
					System.out.println("socket : "+e.getMessage());
				}catch(IOException e) {
					System.out.println("IO : "+e.getMessage());
				}
			}
		
		if(ftp!=null && ftp.isConnected()) {
			ftp.logout();		//로그아웃
			ftp.disconnect();		//접속종료
		}
	}
			
}

