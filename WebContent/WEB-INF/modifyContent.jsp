<%@page import="dbVo.FileVo"%>
<%@page import="java.util.List"%>
<%@page import="dbControl.FileDao"%>
<%@page import="dbVo.BoardVo"%>
<%@page import="dbControl.BoardDao"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BoardDao dao = BoardDao.getInstance();
	BoardVo vo;
	
	int bidx = (int)session.getAttribute("sBidx");
	int pageCount=(int)session.getAttribute("sPageCount");
	int listCount=(int)session.getAttribute("sListCount");
	String searchOption = (String)session.getAttribute("searchOption");
	vo = dao.selectById(bidx);
	
	FileDao fDao = new FileDao();
	List<FileVo> list = fDao.selectByBidx(bidx);
	pageContext.setAttribute("fList", list);
	pageContext.setAttribute("bidx", bidx);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수 정</title>
<style>
	#wTable{
		width : 900px;
		margin-left : auto;
		margin-right : auto;
		padding : 15px;
	}
	.btn1 {
		text-align : right;
	}
	.smalltext{
		width :100%;
		padding : 10px;
		font-style : bold;
		font-size : 15px;
		border-radius : 3px;
	}
	#id { 
		width : 50%;
	}
	#pw { 
		width : 70%;
	}
	.only-sr{
		overflow: hidden !important;
    	position: absolute !important;
	    left: -9999px !important;
	    width: 1px;
	    height: 1px;
	}
	.radio-items{
		display: table;
	    width: 100%;
	    border: 1px solid #454a60;
	    border-radius: 4px;
	    box-sizing: border-box;
	}
	.tags {
	    display: table-cell;
	    height: 40px;
	    line-height: 40px;
	    border-left: 1px solid #454a60;
	    text-align: center;
	}
	.tags:first-child {
	    border-left: none;
	    width: auto !important;
	}
	label {
	    display: block;
	    width: 100%;
	    height: 100%;
	    color: #454a60;
	    vertical-align: middle;
	    box-sizing: border-box;
	    cursor: pointer;
	}
	input[type="radio"]:checked + label{
	    background-color: #454a60;
	    color: #fff;
	}
	.btn{
		 width : 70px;
		height : 45px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	#addFileNav{
		width: 900px;
		background-color:#dcdcdc;
	}
	#file1,#file2,#file3{
		display:none;
	}
	.fileNav{
		width : 100px;
		float: left;
	}
	#fileContainer{
		border:3px solid #969696;
		text-align:left;
		vertical-align:top;
		height:100px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<script>
	var ext="";
	var count=0;
	
	$(document).ready(function(){
		
		$("#submitbtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#resetbtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#backbtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#content").summernote({
			tabsize: 4,
	        height: 400,
	        toolbar: [
	          ['style', ['style']],
	          ['font', ['bold', 'italic','underline', 'clear']],
	          ['fontsize',['fontsize']],
	          ['color', ['color']],
	          ['para', ['ul', 'ol', 'paragraph']],
	          ['table', ['table']],
	          ['view', ['fullscreen', 'codeview', 'help']]
	        ],
	        disableResizeEditor: true
		});
	});
	
	
	/* function upload(){
		var url = "upload";
		var name = "업로드";
		var popup = window.open(url,name,"width=500,height=500");
		
	} */
	function attachImg(){
		var formData = new FormData();
		formData.append("fileObj1",$("#file1")[0].files[0]);
		
		$.ajax({
			type: "POST",
			url: "uploadCheck",
			data: formData,
			processData: false,
			contentType: false,
			success: function(jobj){
				var filePath = jobj.filePath;
				var orifileName = jobj.orifileName;
				var fileName = jobj.fileName;
				var fileSize = jobj.fileSize;
				var content = $("#content").summernote("code");
				var fileContainerCont = $("#fileContainer").html();
				$("#fileContainer").html(fileContainerCont+"<span class='"+count+"'>"+orifileName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"<a href='javascript:void(0);' onclick='removeFile(\""+fileName+"\",\""+count+"\");'><img src = './images/x_icon.png' width='10px' height='10px'/></a><br/></span>");
				$("#content").summernote("code",content+"<span class='"+count+"'><img src = '"+filePath+"' width='600px'/><br/></span>");
				$("#file1Html").html("<input type='file' id='file1' onchange='checkFile1(this);'/>");
				count = count+1;
			},
			error: function(request,status,error){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				$("#file1Html").html("<input type='file' id='file1' onchange='checkFile1(this);'/>");
			}
		});
	}
	function checkFile1(f){
		
		// files 로 해당 파일 정보 얻기.
		var file = f.files;	
		// file[0].name 은 파일명
		ext = file[0].name.split(".").pop().toLowerCase(); 
		
		var fileSize = file[0].size;
		var maxSize = 10*1024*1024;
		
		if(fileSize>maxSize){
			alert("10MB까지만 업로드 가능합니다.");
			$("#file1Html").html("<input type='file' id='file1' onchange='checkFile1(this);'/>");
		}
		
		if($.inArray(ext,["gif","png","jpg","jpeg"])==-1){
			alert('올바른 확장자 명이 아닙니다.\n "gif","png","jpg","jpeg"만 가능합니다!');
			$("#file1Html").html("<input type='file' id='file1' onchange='checkFile1(this);'/>");
		}
		else{
			attachImg();
		}
	}
	
	
	function attachVideo(){
		var formData = new FormData();
		formData.append("fileobj2",$("#file2")[0].files[0]);
		
		$.ajax({
			type:"POST",
			url : "uploadCheck",
			data: formData,
			processData: false,
			contentType: false,
			success: function(jobj){
				var filePath = jobj.filePath;
				var orifileName = jobj.orifileName;
				var fileName = jobj.fileName;
				var fileSize = jobj.fileSize;
				var content = $("#content").summernote("code");
				var fileContainerCont = $("#fileContainer").html();
				$("#fileContainer").html(fileContainerCont+"<span class='"+count+"'>"+orifileName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"<a href='javascript:void(0);' onclick='removeFile(\""+fileName+"\",\""+count+"\");'><img src = './images/x_icon.png' width='10px' height='10px'/></a><br/></span>");
				$("#content").summernote("code",content+"<span class='"+count+"'><video width='600px' height='600px' controls><source src = '"+filePath+"' type='video/"+ext+"'></video><br/></span>");
				$("#file2Html").html("<input type='file' id='file2' onchange='checkFile2(this);'/>");
				count = count+1;
			},
			error: function(request,status,error){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				$("#file2Html").html("<input type='file' id='file2' onchange='checkFile2(this);'/>");
			}
		});
	}
	function checkFile2(f){
		
		// files 로 해당 파일 정보 얻기.
		var file = f.files;	
		// file[0].name 은 파일명
		ext = file[0].name.split(".").pop().toLowerCase(); 
		
		var fileSize = file[0].size;
		var maxSize = 10*1024*1024;
		
		if(fileSize>maxSize){
			alert("10MB까지만 업로드 가능합니다.");
			$("#file2Html").html("<input type='file' id='file2' onchange='checkFile2(this);'/>");
		}
		
		if($.inArray(ext,["mp4","webm","ogg"])==-1){
			alert('올바른 확장자 명이 아닙니다.\n "mp4","webm","ogg"만 가능합니다!');
			$("#file2Html").html("<input type='file' id='file2' onchange='checkFile2(this);'/>");
		}
		else{
			attachVideo();
		}
	}
	
	function attachFile(){
		var formData = new FormData();
		formData.append("fileobj3",$("#file3")[0].files[0]);
		
		$.ajax({
			type:"POST",
			url : "uploadCheck",
			data: formData,
			processData: false,
			contentType: false,
			success: function(jobj){
				var orifileName = jobj.orifileName;
				var fileName = jobj.fileName;
				var fileSize = jobj.fileSize;
				var fileContainerCont = $("#fileContainer").html();
				$("#fileContainer").html(fileContainerCont+"<span class='"+count+"'>"+orifileName+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+fileSize+"<a href='javascript:void(0);' onclick='removeFile(\""+fileName+"\",\""+count+"\");'><img src = './images/x_icon.png' width='10px' height='10px'/></a><br/></span>");
				$("#file3Html").html("<input type='file' id='file3' onchange='checkFile3(this);'/>");
				count = count+1;
			},
			error: function(request,status,error){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				$("#file3Html").html("<input type='file' id='file3' onchange='checkFile3(this);'/>");
			}
		});
	}
	
	function checkFile3(f){
		
		// files 로 해당 파일 정보 얻기.
		var file = f.files;	
		// file[0].name 은 파일명
		ext = file[0].name.split(".").pop().toLowerCase(); 
		
		var fileSize = file[0].size;
		var maxSize = 10*1024*1024;
		
		if(fileSize>maxSize){
			alert("10MB까지만 업로드 가능합니다.");
			$("#file3Html").html("<input type='file' id='file3' onchange='checkFile3(this);'/>");
		}
		
		if($.inArray(ext,["zip","hwp","xls","xlsx","ppt","docx","pdf","gif","png","jpg","jpeg","mp4","webm","ogg"])==-1){
			alert('올바른 확장자 명이 아닙니다.\n "zip","hwp","xls","xlsx","ppt","docx","pdf","gif","png","jpg","jpeg","mp4","webm","ogg"만 가능합니다!');
			$("#file3Html").html("<input type='file' id='file3' onchange='checkFile3(this);'/>");
		}
		else{
			attachFile();
		}
	}
	
	function backbtnFunc(){
		$.ajax({
			type: "POST",
			url: "deleteFile",
			success: function(result){
				location.href="board";
			},
			error: function(error,request,status){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
			}
		});
	}
	
	function removeMedia(n){
		$("."+n).remove();
	}
	function removeFile(fileName,n){
		var conf = confirm("게시물의 사진도 함께 삭제됩니다. 계속하시겠습니까?");
		if(conf){
			
			$.ajax({
				type: "POST",
				url: "deleteFile",
				data:{
					"fileName": fileName,
					"bidx": "<c:out value='${pageScope.bidx}'/>"
				},
				success: function(result){
					removeMedia(n);
				},
				error: function(error,request,status){
					alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				}
			});
		}
	}

</script>
</head>
<body>
	<form name = "writeForm" method = "post" action = "modify">
	<table id = "wTable">
		<tr>
			<td><h2>게시글 입력</h2></td>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${sNick==vo.getId()}">
					<td style="font-size:17px;font-weight:bolder;"></td>
				</c:when>
				<c:otherwise>
					<td>
						<input type = "text" name = "id"  id = "id" class = "smalltext" value = "<%=vo.getId() %>" size = "10" placeholder = "아이디" readonly/>
						
					</td>
					<td>
						<input type = "password" name = "pw"  id = "pw" class = "smalltext" value = "<%=vo.getPw() %>" size = "10" placeholder = "비밀번호" readonly/>
					</td>
			</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td colspan = "2">
				<input type = "text"  class = "smalltext" name = "title" value = "<%=vo.getTitle() %>" size = "50" placeholder = "제목"/>
			</td>
		</tr>
		<tr>
			<td class = "radio-items" colspan = "3">
				<div class = "tags">
					<input type = "radio" name = "tag" id = "tag1" class = "only-sr" value="일반" checked/>
					<label for = "tag1">일반</label>
				</div>
				<div class = "tags">
					<input type = "radio" name = "tag"  id = "tag2" class = "only-sr"  value="공지"/>
					<label for = "tag2">공지</label>
				</div>
				<div class = "tags">
					<input type = "radio" name = "tag"  id = "tag3" class = "only-sr"  value="유머"/>
					<label for = "tag3">유머</label>
				</div>
			</td>
		</tr>
		<tr>
			<td id="addFileNav" colspan="3">
				<label class="fileNav">
					<img src="./images/iconmonstr-photo-camera-4-240.png" width = "20px" height="20px"/>사진
					<span id="file1Html"><input type="file" id="file1" accept="image/*" onchange="checkFile1(this);"/></span>
				</label>
				<label class="fileNav">
					<img src="./images/iconmonstr-youtube-6-240.png" width = "20px" height="20px"/>동영상
					<span id="file2Html"><input type="file" id="file2" accept="video/*" onchange="checkFile2(this);"/></span>
				</label>
				<label class="fileNav">
					<img src="./images/iconmonstr-save-1-240.png" width = "20px" height="20px"/>파일
					<span id="file3Html"><input type="file" id="file3" onchange="checkFile3(this);"/></span>
				</label>
			</td>
		</tr>
		<tr>
			<td colspan = "2">
				<textarea name = "content" id="content" rows = "15" cols = "100" class = "smalltext"  style="width:100%;"><%=vo.getContent() %></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="3" id="fileContainer">
				<b>첨부파일</b><br/>
				<!-- 파일 DB에 용량도 같이 넣어야 할듯 싶다. 아니면 일단은 파일명하고 삭제버튼만 쓸까?--> 
				<c:forEach var="vo" items="${pageScope.fList}" varStatus="status">
					<span class="${status.count-1}">
						${vo.orifileName}&nbsp;
						<a href='javascript:void(0);' onclick="removeFile('${vo.fileName}','${status.count-1}');">
							<img src = './images/x_icon.png' width='10px' height='10px'/>
						</a><br/>
					</span>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td class = "btn1" colspan = "3">
				<input type = "submit" value = "수정" id = "submitbtn" class = "btn"/>
				<a href = "view?bidx=<%=bidx%>&pageCount=<%=pageCount %>&listCount=<%=listCount%>&searchOption=<%=searchOption%>"><input type = "button" value = "뒤로" id = "backbtn" class = "btn"/></a>
			</td>
		</tr>
	</table>
		
		
	</form>
		
</body>
</html>