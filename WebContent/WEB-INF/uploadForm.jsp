<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업로드</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<script>
	function checkFile(f){
		
		// files 로 해당 파일 정보 얻기.
		var file = f.files;	
		// file[0].name 은 파일명
		var ext = file[0].name.split(".").pop().toLowerCase(); 
		
		var fileSize = file[0].size;
		var maxSize = 10*1024*1024;
		
		if(fileSize>maxSize){
			alert("10MB까지만 업로드 가능합니다.");
			$("#file").html("<input type='file' name='file1' id='file1' onchange='checkFile(this);imgDel();setThumbnail(event);'/>");
		}
		
		if($.inArray(ext,["gif","png","jpg","jpeg"])==-1){
			alert('올바른 확장자 명이 아닙니다. gif,png,jpg,jpeg만 가능합니다!');
			$("#file").html("<input type='file' name='file1' id='file1' onchange='checkFile(this);imgDel();setThumbnail(event);'/>");
		}
	}
	
	function setThumbnail(event){
		var reader = new FileReader();
		var file = event.target.files;
		
		var ext = file[0].name.split(".").pop().toLowerCase();
		
		if(!($.inArray(ext,["gif","png","jpg","jpeg"])==-1)){
		
			reader.onload = function(event){
				var img = document.createElement("img");
				
				img.setAttribute("src",event.target.result);
				img.setAttribute("width","100px");
				img.setAttribute("height","100px");
				document.querySelector("td#imgContainer").appendChild(img);
				
				/* $("#imgContainer").html("<img src='"+event.target.result+"' width='100px' height='100px'/>"); */
			};
		}
		
		reader.readAsDataURL(file[0]);
	}
	
	function imgDel(){		//자식을 다 삭제하는 스크립트.
		var container = document.querySelector("td#imgContainer");
		while(container.firstChild){
			container.removeChild(container.firstChild);
		}
	}
	
	function uploadFile(){
		var form = $("#uploadForm")[0];
		var formData = new FormData(form);
		formData.append("fileObj",$("#file1")[0].files[0]);
		
		$.ajax({
			type: 'POST',
			url: 'uploadCheck',
			processData: false,
			contentType: false,
			data: formData,
			success: function(data){
				alert('success');
			},
			error: function(request,status,error){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
			}				
		});
	}
	
	function windowClose(){
		
		self.opener = self;
		window.close();
	}
	
</script>
<style>
	#uploadTable{
		width:500px;
	}
	#imgContainer{
		width:100px;
		height:100px;
	}
</style>
</head>
<body>
	<form id="uploadForm" method="post" action="uploadCheck" enctype="multipart/form-data">
		<table id="uploadTable">
			<tr>
				<td>파일 업로드하기</td>
			</tr>
			<tr>
				<td id="file"><input type="file" id="file1" onchange="javascript:checkFile(this);imgDel();setThumbnail(event);"/></td>
			</tr>
			<tr>
				<td id="imgContainer"></td>
			</tr>
			<tr>
				<td>
					이미지 파일은 각각 최대 10MB, 1개까지 업로드 가능합니다.<br/>

					이미지 파일명이 한글, 숫자, 영문이 아닌 다른 언어일 경우 파일이 업로드되지 않거나 깨질 수 있습니다.<br/>
					
					저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 파일은 운영 원칙 및 관계 법률에 의해
					제재를 받을 수 있습니다.
				</td>
			</tr>
			<tr>
				<td><input type="button" id = "submitbtn" value="적용" onclick="javascript:uploadFile();setTimeout('windowClose()',1000);"/></td>
			</tr>
		</table>
	</form>
</body>
</html>