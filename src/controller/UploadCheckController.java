package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import dbControl.FileDao;
import dbVo.FileVo;

@WebServlet("/uploadCheck")
public class UploadCheckController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		ServletContext context = request.getServletContext();
		String tmpDir = context.getRealPath("/temp");		//로컬 톰캣서버 내 절대경로
		String mappingTmpDir = "/cj200810/temp";			//로컬 경로로는 사진을 볼 수 없으므로 server.xml에서 매핑한 경로로 간다.
		
		int maxSize = 10*1024*1024;
		
		File tmp = new File(tmpDir);
		
		//commons-fileupload 와 commons-io사용
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(tmp);
		factory.setSizeThreshold(maxSize);
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setSizeMax(maxSize);
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for(FileItem item : items) {
				FileVo vo = new FileVo();
				FileDao dao = new FileDao();
				
				if(item.isFormField()) {
					System.out.println("파라미터 명 : "+item.getFieldName()+" ,파라미터 값:"+item.getString("utf-8"));
				}
				else {
					System.out.println("파라미터 명 : "+item.getFieldName()+" 파일명 : "+item.getName()+", 파일크기:"+item.getSize()+"bytes");
					
					if(item.getSize()>0) {
						String seperator = File.separator;
						int index = item.getName().lastIndexOf(seperator);
						String fileName = item.getName().substring(index+1);
						String orifileName = fileName;
						System.out.println("fileName: "+fileName);
						index = fileName.lastIndexOf(".");
						String noExtName = fileName.substring(0,index);
						String ext = fileName.substring(index).toLowerCase();
						fileName = noExtName+ext;
						
						if(ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".gif") || ext.equals(".png") || ext.equals(".mp4") || ext.equals(".ogg") || ext.equals(".webm") || ext.equals(".zip") ||ext.equals(".xls") ||ext.equals(".xlsx") ||ext.equals(".ppt") ||ext.equals(".docx") ||ext.equals(".hwp") || ext.equals(".pdf")) {
							File uploadFile = new File(tmpDir+seperator+fileName);
							int result = dao.checkName(fileName);
							if(uploadFile.exists() || result==1) {
								noExtName += "_";
								for(int i=1;true;i++) {
									String no = noExtName.substring(noExtName.length()-1);
									if(no.equals(Integer.toString(i))) {
										continue;
									}
									else {
										int nameIndex = noExtName.lastIndexOf("_");
										noExtName = noExtName.substring(0,nameIndex+1)+i;
										uploadFile = new File(tmpDir+seperator+noExtName+ext);
									}
									if(!uploadFile.exists()) {
										break;
									}
									
								}
							}
							
							System.out.println(noExtName+ext);
							
							item.write(uploadFile);
							fileName = noExtName+ext;
							String filePath = tmpDir+seperator+noExtName+ext;
							
							
							vo.setOrifileName(orifileName);
							vo.setFileName(fileName);
							vo.setFilePath(filePath);
							
							result = dao.tmpUpload(vo);
							filePath = mappingTmpDir+"/"+noExtName+ext;
							System.out.println("tmpDir: "+tmpDir);
							
							if(result==1) {
								System.out.println("파일 업로드 완료");
								response.setContentType("application/json");
								JSONObject jobj = new JSONObject();				//json-simple 라이브러리 필요
								jobj.put("filePath", filePath);
								jobj.put("orifileName", orifileName);
								jobj.put("fileName", fileName);
								jobj.put("fileSize",item.getSize()+"bytes");
								out.println(jobj.toString());
								System.out.println("jobj: "+jobj.toString()+"\n");
							}
						}
						else {
							tmp.delete();
							out.println("<script>");
							out.println("alert('지원하지 않는 확장자입니다.');");
							out.println("location.href='board';");
							out.println("</script>");
						}
					}
				}
			}
			
		}catch(Exception e){
			System.out.println("파일 업로드 오류\n"+e.getMessage());
		}
		
		
	}
	
}
