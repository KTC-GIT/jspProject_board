package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request,response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		
		String fileName=request.getParameter("fileName");
		String seperator = File.separator;
		String savePath="temp";
		ServletContext context = request.getServletContext();
		
		String sDownPath = context.getRealPath(savePath);
		System.out.println("다운로드 위치:"+sDownPath);
		
		String sFilePath = sDownPath+seperator+fileName;
		
		//문자열을 파일로 인식
		File oFile = new File(sFilePath);
		//읽어오는 파일은 업로드 파일용량을 초과하지 않게..
		byte[] b = new byte[10*1024*1024];
		
		FileInputStream input = new FileInputStream(oFile);
		
		//유형확인-읽어올 경로의 파일의 유형->페이지를 생성할 때 타입을 설정해야 한다.
		String sMimeType = context.getMimeType(sFilePath);
		
		//지정되지 않은 유형 예외처리
		if(sMimeType==null) {
			//관례적인 표현
			sMimeType ="application.octec-stream";//일련된 8bit 스트림형식
		}								//유형이 알려지지 않은 파일에 대한 읽기 형식 지정
		
		//파일 다운로드 시작
		//유형을 알려준다.
		response.setContentType(sMimeType);		//text/html; charset=utf-8을 대체
		
		//업로드 파일의 제목이 깨질 수 있다.URLEncode
		String str1 = new String(fileName.getBytes("euc-kr"),"8859_1");
		String str2 = "utf-8";
		String sEncoding = URLEncoder.encode(str1, str2);
		
		//기타 내용을 헤더에 올려야한다.
		//기타 내용을 보고 브라우저에서 다운로드 시 화면에 출력해준다.
		String str3 = "Content-Disposition";
		String str4 = "attachment; filename="+sEncoding;
		response.setHeader(str3, str4);
		
		//브라우저에 쓰기
		ServletOutputStream output = response.getOutputStream();
		
		int numRead = 0;
		
		while((numRead=input.read(b, 0, b.length))!=-1) {
			output.write(b,0,numRead);
		}
		output.flush();
		output.close();
		input.close();
	}
}
