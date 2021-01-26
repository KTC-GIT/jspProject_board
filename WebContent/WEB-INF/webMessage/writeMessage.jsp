<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 작성</title>
<style>
	#writeTable{
		width:500px;
		text-align:center;
		margin-left:auto;
		margin-right:auto;
	}
	.title{
		width:100px;
	}
	.writeArea{
		text-align:left;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#submitButton").click(function(){
			if($("#receiveId").val()==""){
				alert("수신자를 입력하세요");
			}
			else if($("#title").val()==""){
				alert("제목을 입력하세요");
			}
			else if($("#content").val()==""){
				alert("내용을 입력하세요");
			}
			else{
				var queryString = $("#writeForm").serialize();

				$.ajax({
					type: "POST",
					url: "writeMsgCheck.ms",
					data: queryString,
					success: function(result){
						alert("메시지를 보냈습니다!");
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
		self.opener = opener;
		window.close();
	}
</script>
</head>
<body>
	<form id="writeForm">
		<table id="writeTable">
			<tr>
				<td colspan="2"><h3 style="text-align:center;">메시지 작성</h3></td>
			</tr>
			<tr>
				<td class="title">수신자</td>
				<c:if test="${param.receiveId != null }">
					<td class="writeArea"><input type="text" id = "receiveId" name = "receiveId" value = "${param.receiveId}" size="20" maxlength="20"/></td>
				</c:if>
				<c:if test="${param.receiveId == null }">
					<td class="writeArea"><input type="text" id = "receiveId" name = "receiveId" size="20" maxlength="20"/></td>
				</c:if>
			</tr>
			<tr>
				<td class="title">제목</td>
				<td class="writeArea"><input type="text" id="title" name="title" size="49" maxlength="50"/></td>
			</tr>
			<tr>
				<td class="title">내용</td>
				<td class="writeArea"><textarea id="content" name="content" rows="10" cols="50" style="resize:none;"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="padding-top:20px;">
					<input type="button" id="submitButton" value="보내기"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>