<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓 글 보 기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#hideR").click(function(){
			$(".reply").toggle();
		});
	});
</script>
<style>
	#rvTable{
		width : 900px;
		height : 100%;
		margin-left : auto;
		margin-right : auto;
		color : #323232;
		font-size : 15px;
		padding-top : 2%;
	}
	#rTitle{
		width : 800px;
		text-align : left;
	}
	.main{
		width : 600px;
	}
	.id{
		width : 100px;
		font-size : 12px;
	}
	.rdate{
		width : 100px;
		font-size : 6px;
		text-align : right;
	}
	.cont{
		padding : 3px;
		text-align : left;
	}
	#order{
		border : 2px solid #aaaaaa;
	}
	#hideR{
		width : 100px;
	}
	#hideRbtn{
		width : 100px;
		border : 0;
		outline : 0;
		font-size : 10px;
	}
	#hideRbtn:hover{
		cursor : pointer;
	}
	#replyline{
		border-bottom : 3px solid #4646CD;
		width : 850px;
	}
	#pagingNum{
		width : 900px;
		font-size : 17px;
		text-align : center;
		padding : 20px;
	}
	#page{
		font-weight : bold;
		font-size : 20px;
		color : #B93232;
	}
	.pagingNum:visited{
		color : #323232;
	}
	
</style>

</head>
<body>
	<table id = "rvTable">
		<tr>
			<th id = "rTitle" colspan = "2">
				<form name = "orderForm" method = "post" action = "orderReply">
					댓글 총  ${count}개
					<select name="order" id = "order" onchange = "orderForm.submit();">
						<c:choose>
							<c:when test="${order=='recentorder'}">
								<option value="recentorder">최근순서</option>
								<option value="writeorder">작성순서</option>
							</c:when>
							<c:otherwise>
								<option value="writeorder">작성순서</option>
								<option value="recentorder">최근순서</option>
							</c:otherwise>
						</c:choose>
					</select>
				</form>
			</th>
			<th id = "hideR"><button id = "hideRbtn">댓글 숨기기/보이기</button></th>
		</tr>
		<tr class = "reply">
			<td id = "replyline" colspan = "3"></td>
		</tr>
		<c:forEach var="rVo" items="${rList}">
			<tr class = "reply">
				<td class = "cont id">${rVo.id}(${rVo.ipView})</td>
				<td class = "cont main">${rVo.content}</td>
				<td class = "cont rdate">
					${rVo.writedateStr}
					<a href = "deleteR?ridx=${rVo.ridx}&bidx=${sBidx}">
						<img src = "./images/x_icon.png" width="10px" height = "10px"/>
					</a>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="3"  id="pagingNum" class = "reply">
				
				<c:set var="start" value="${((rListCount-1)*pageListLimit)+1<1?1:((rListCount-1)*pageListLimit)+1}"/>
				<c:set var="end" value="${(rListCount*pageListLimit)>=rLastpage?rLastPage:(rListCount*pageListLimit) }"/>
				
				<c:if test="${rListCount!=0}">
					<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=1&rListCount=1" class = "pagingNum">◀◀</a>
					<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=${start}&rListCount=${rListCount-1<1?1:rListCount-1}" class = "pagingNum">◀</a>
				</c:if>
				<c:if test="${end<rLastPage}">
					<c:forEach var="i" begin="${start}" end="${end}">
						<c:choose>
							<c:when test="${rPageCount==i}">
								<span id="page">${i}</span>
							</c:when>
							<c:otherwise>
								<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=${i}&rListCount=${rListCount}" class = "pagingNum">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				<c:if test="${end>=rLastPage}">
					<c:forEach var="i" begin="${start}" end="${rLastPage}">
						<c:choose>
							<c:when test="${rPageCount==i}">
								<span id="page">${i}</span>
							</c:when>
							<c:otherwise>
								<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=${i}&rListCount=${rListCount}" class = "pagingNum">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				
				<c:if test="${rListCount!=0}">
					<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=${(rListCount*pageListLimit)+1>rLastPage?rLastPage:(rListCount*pageListLimit)+1 }&rListCount=${rListCount+1>rListTotal?rListTotal:rListCount+1}" class = "pagingNum">▶</a>
					<a href = "view?bidx=${sBidx}&order=${order}&pageCount=${sPageCount}&listCount=${sListCount}&rPageCount=${rLastPage}&rListCount=${rListTotal}" class = "pagingNum">▶▶</a>
				</c:if>
			</td>
		</tr>
	</table>
	<c:import url="/writeRView"/>
</body>
</html>