<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴지통</title>
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
</script>
</head>
<body>
	<table id="listTable">
		<tr>
			<th class="subject"><input type="checkbox" id="checkAll"/></th>
			<th class="subject">보낸 사람</th>
			<th class="subject">제목</th>
			<th class="subject">보낸 날짜</th>
			<th class="subject"><input type="button" id="restoreButton" value="선택 복구"/></th>
			<th class="subject"><input type="button" id="checkDelButton" value="선택 삭제"/></th>
		</tr>
		<tr>
			<td id="titleLine" colspan="4"></td>
		</tr>
		<c:forEach var="vo" items="${msgList}" varStatus="status">
			<c:if test="${vo.receiveSw=='g'}">
				<tr>
					<td><input type="checkbox" name="idxCheck" value="${vo.idx}"/></td>
					<td>${vo.sendId}
						<input type="hidden" value="${vo.idx}" id="${status.count}"/>
						<input type="hidden" value="${vo.receiveSw}" id="receiveSw${status.count}"/>
					</td>
					<td id="titleList${status.count}"><span id="title${status.count}" class="title" onclick="viewMsg(${status.count});">${vo.title}</span></td>
					<td>${vo.sendDateStr}</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>