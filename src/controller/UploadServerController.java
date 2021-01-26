package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.ftp.FTPClient;

import dbControl.BoardDao;
import dbControl.CreateSHA256;
import dbControl.FileDao;
import dbVo.FileVo;

@WebServlet("/uploadServer")
public class UploadServerController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			process(request,response);
		} catch (NoSuchAlgorithmException e) {} 
		catch (UnsupportedEncodingException e) {}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			process(request,response);
		} catch (NoSuchAlgorithmException e) {} 
		catch (UnsupportedEncodingException e) {}
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NoSuchAlgorithmException,UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		FTPClient ftp = null;
		String tmpDir = request.getServletContext().getRealPath("/temp");
		String seperator = File.separator;
		CreateSHA256 sha256 = new CreateSHA256();
		
		BoardDao bDao = BoardDao.getInstance();
		FileDao fDao = new FileDao();
		int bidx = bDao.selectBidxLatest();
		
		List<FileVo> list = fDao.tmpSelect();
		
		for(FileVo vo: list) {
			fDao = new FileDao();
			
			String fileName = vo.getFileName();
			String hashCode = sha256.getHashCode(fileName);
			
			try {
				
				String filePath = tmpDir+seperator+fileName;
				System.out.println("filePath: "+filePath);
				
				ftp = new FTPClient();
				ftp.setControlEncoding("utf-8");
				
				ftp.connect("nas.monlini.pe.kr",2121);		//(접속할 ip,port)
				ftp.login("atom", "matal3");			//(접속할 id, pw)
				
				ftp.enterLocalPassiveMode();			//수동형 설정  (수신 송신중 포트가 한개만 열려있기 때문에 수동형)
				ftp.changeWorkingDirectory("/web/");	//ftp저장할 장소
				
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);	//파일 깨짐 방지
				File uploadFile = new File(filePath);			//저장할 파일 선택
				FileInputStream input = null;
				
				try {
					input = new FileInputStream(uploadFile);
					
					//ftp에 파일 저장
					boolean isSuccess = ftp.storeFile(hashCode, input);		//(저장할 이름, 업로드하는 소스경로)
					if(isSuccess) {
						System.out.println("upload to backup server success\n");
						fDao.uploadServer(bidx,hashCode,fileName);
					}
					else {
						System.out.println("upload to backup server failed\n");
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				}finally {
					if(input!=null) {
						input.close();
					}
				}
			}catch(SocketException e) {
				System.out.println("socket : "+e.getMessage());
			}catch(IOException e) {
				System.out.println("IO : "+e.getMessage());
			}
		}
		if(ftp!=null && ftp.isConnected()) {
			ftp.logout();
			ftp.disconnect();
		}
		
		// 글쓰기에서 왔는지 글 수정에서 왔는지 확인하여 알맞는 곳으로 보내기
		String urlCheck = (String)session.getAttribute("urlCheck");		
		if(urlCheck.equals("/write")) {
			response.sendRedirect("board");
		}
		else {
			int pageCount= (int)session.getAttribute("sPageCount");
			int listCount = (int)session.getAttribute("sListCount");
			
			response.sendRedirect("view?bidx="+bidx+"&pageCount="+pageCount+"&listCount="+listCount);
		}
	}
}
