<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수신확인</title>
<style>
	#listTable{
		width:600px;
		text-align:center;
		margin-left:auto;
		margin-right:auto;
	}
	.title{
		font-weight:bolder;
	}
	#titleLine{
		border-bottom: 3px solid black;
	}
	#readOrNot1{
		color: #147814;
	}
	#readOrNot2{
		color: #B9062F;
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
	
</script>
</head>
<body>
	<table id="listTable">
		<tr>
			<th class="title">받는 사람</th>
			<th class="title">제목</th>
			<th class="title">보낸 날짜</th>
			<th class="title">수신여부</th>
			<th class="title">수신 날짜</th>
		</tr>
		<tr>
			<td id="titleLine" colspan="5"></td>
		</tr>
		<c:forEach var="vo" items="${msgList}" varStatus="status">
			<c:if test="${vo.sendSw=='s'}">
				<tr>
					<td>${vo.receiveId}<input type="hidden" value="${vo.idx}" id="${status.count}"/></td>
					<td><a href="javascript:void(0)" onclick="viewMsg(${status.count});">${vo.title}</a></td>
					<td>${vo.sendDateStr}</td>
					<c:if test="${vo.receiveSw== 'r'}">
						<td id="readOrNot1">읽음</td>
						<td>${vo.receiveDateStr}</td>
					</c:if>
					<c:if test="${vo.receiveSw== 'n'}">
						<td id="readOrNot2">읽지 않음</td>
						<td></td>
					</c:if>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>