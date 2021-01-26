package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import dbControl.FileDao;
import dbVo.FileVo;

public class DeleteFromServerCmd{
	private int bidx=0;
	private String fileName="";
	
	public void setBidx(int bidx) {
		this.bidx = bidx;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	protected void execute() {
		
		FileDao dao = new FileDao();
		List<FileVo> list = null;
		FTPClient ftp = null;
		
		if(fileName.equals("")) {
			list = dao.selectByBidx(bidx);
		}
		else {
			list = dao.selectByFileName(bidx,fileName);
		}
		
		for(FileVo vo: list) {
			
			String hashCode = vo.getHashCode();
			
				try {
					ftp = new FTPClient();
					ftp.setControlEncoding("utf-8");		//언어설정
					
					ftp.connect("nas.monlini.pe.kr",2121);		//ip
					ftp.login("atom", "matal3");			//계정, 비밀번호
					ftp.enterLocalPassiveMode();			//수동형 설정  (수신 송신중 포트가 한개만 열려있기 때문에 수동형)
					ftp.changeWorkingDirectory("/web/");	//작업할 폴더로 이동.
					
					
					try {
						boolean isSuccess = ftp.deleteFile(hashCode);		//다운로드
						System.out.println(ftp.getReplyString());
						System.out.println(ftp.getReplyCode());
						if(isSuccess) {
							System.out.println("delete file from server Success");
						}
						else {
							System.out.println("delete file from server failed");
						}
					}catch(IOException e) {
						System.out.println(e.getMessage());
					}
				}
	
			catch(SocketException e) {
				System.out.println("socket : "+e.getMessage());
			}catch(IOException e) {
				System.out.println("IO : "+e.getMessage());
			}
		}
			
		if(ftp!=null && ftp.isConnected()) {
			try {
				ftp.logout();				//로그아웃
				ftp.disconnect();			//접속종료	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


