<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 메시지</title>
<style>
	#listTable{
		width:600px;
		text-align:center;
		margin-left:auto;
		margin-right:auto;
	}
	.subject{
		font-weight:bolder;
	}
	#titleLine{
		border-bottom: 3px solid black;
	}
	.title:hover{
		text-decoration:underline;
		cursor:pointer;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	function viewMsg(n){
		var idx = document.getElementById(n).value;
		var url = "viewMessage.ms?idx="+idx;
		var name="메시지 확인";
		
		window.open(url,name,"width=600,height=600");
	}
	function changeStatus(n){
		var idx = document.getElementById(n).value;
		var titleListId = "titleList"+n;
		var titleId = "title"+n;
		var titleValue = $("#"+titleId).text();
		var receiveSwId = "receiveSw"+n;
		var receiveSw = $("#"+receiveSwId).val();
		
		if(receiveSw=="n"){
			$.ajax({
				type:"POST",
				url: "messageCheck.ms",
				data: {
					"receiveSw":"r",
					"idx": idx
				},
				success: function(result){
					document.getElementById(titleListId).innerHTML = '<span id="'+titleId+'" class="title" onclick="viewMsg('+n+');changeStatus('+n+');">'+titleValue+'</span>';
				},
				error: function(request,status,error){
					alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				}
			});
		}
	}
</script>
</head>
<body>
	<table id="listTable">
		<tr>
			<th class="subject">보낸 사람</th>
			<th class="subject">제목</th>
			<th class="subject">보낸 날짜</th>
		</tr>
		<tr>
			<td id="titleLine" colspan="3"></td>
		</tr>
		<c:forEach var="vo" items="${msgList}" varStatus="status">
			<c:if test="${vo.receiveSw=='n'}">
				<tr>
					<td>${vo.sendId}
						<input type="hidden" value="${vo.idx}" id="${status.count}"/>
						<input type="hidden" value="${vo.receiveSw}" id="receiveSw${status.count}"/>
					</td>
					<td id="titleList${status.count}"><span id="title${status.count}" class="title" onclick="viewMsg(${status.count});changeStatus(${status.count});">${vo.title}</span><img src="./images/NEWicon.png" width="20px" height="20px"/></td>
					<td>${vo.sendDateStr}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>