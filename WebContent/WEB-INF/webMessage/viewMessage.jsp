<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#closeButton").click(function(){
			self.opener = self;
			window.close();
		});
		$("#replyButton").click(function(){
			var receiveId = "<c:out value='${msgVo.sendId}'/>"
			var sendId = "<c:out value='${sId}'/>"
			location.href = "writeMessage.ms?sendId="+sendId+"&receiveId="+receiveId;
		});
		$("#deleteButton").click(function(){
			var idx = '<c:out value="${msgVo.idx}"/>';
			
			var conf = confirm("삭제하시겠습니까?");
			if(conf){
				$.ajax({
					type:"post",
					url:"goToTrashCan.ms",
					data:{
						"receiveSw":"g",
						"idx":idx
					},
					success: function(result){
						alert("삭제되었습니다.");
						opener.location.reload();
						windowClose();
					},
					error: function(request,status,error){
						alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
					}
				});
			}
			
		});
	});
	function windowClose(){
		self.opener=opener;
		window.close();
	}
</script>
<style>
	#viewMsgTable{
		width:500px;
		text-align:left;
	}
	#content{
		width:300px;
		height:200px;
		padding:10px;
		text-align:left;
		vertical-align:top;
		border: 1px solid #3c3c3c;
		border-radius:3px;
	}
</style>
</head>
<body>
	<table id="viewMsgTable">
		<tr>
			<td>제목: <b>${msgVo.title}</b></td>
		</tr>
		<tr>
			<td>발신자: ${msgVo.sendId}</td>
		</tr>
		<tr>
			<td>발신일: ${msgVo.sendDateStr}</td>
		</tr>
		<tr>
			<td id="content">${msgVo.content}</td>
		</tr>
		<tr>
			<td style="text-align:center;">
				<input type="button" id="replyButton" value="답장하기"/>
				<input type="button" id="deleteButton" value="삭제"/>
				<input type="button" id="closeButton" value="닫기"/> 
			</td>
		</tr>
	</table>
</body>
</html>