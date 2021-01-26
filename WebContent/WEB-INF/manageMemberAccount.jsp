<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	
	$(document).ready(function(){
		$("#deleteBtn").click(function(){
			if($("input:checkbox[id='id']").is(":checked")==false){
				alert("아이디를 체크하세요!");				
			}
			else{
				$(".btn").append("<input type='hidden' name='cmd' value='1'/>");
				$("#manageForm").submit();
			}
				
		});
		$("#requestCancelBtn").click(function(){
			if($("input:checkbox[id='id']").is(":checked")==false){
				alert("아이디를 체크하세요!");				
			}
			else{
				$(".btn").append("<input type='hidden' name='cmd' value='2'/>");
				$("#manageForm").submit();
			}
		});
		$("#requestBtn").click(function(){
			if($("input:checkbox[id='id']").is(":checked")==false){
				alert("아이디를 체크하세요!");				
			}
			else{
				$(".btn").append("<input type='hidden' name='cmd' value='3'/>");
				$("#manageForm").submit();
			}
		});
	});
</script>
<style>
	#manageTable{
		margin-left: auto;
		margin-right : auto;
		text-align : center;
		width : 900px;
		font-size : 17px;
	}
	.paging{
		text-decoration : none;
		font-size : 15px;
		padding-top : 10%;
	}
	#titleLine{
		border-bottom : 3px solid #0A8A8A;
		padding-bottom : 10px;
	}
	#itemLine{
		border-bottom : 1px solid #646464;
		padding-bottom : 10px;
	}
	.btn{
		padding : 5%;
	}
	th{
		padding-top : 10%;
		font-size : 20px;
	}
	a{
		color:#0000CD;
	}
	a:visited{
		color:#0000CD;
	}
	#deleteBtn,#requestCancelBtn,#requestBtn,#backBtn{
		width : 135px;
		height : 45px;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	#deleteBtn{
		background-color:#CD1F48;
		border-bottom : 3px solid #CD1039;
	}
	#requestCancelBtn{
		background-color:#2C952C;
		border-bottom : 3px solid #228B22;
	}
	#requestBtn{
		background-color:#D27328;
		border-bottom : 3px solid #D2691E;
	}
	#backBtn{
		background-color : #3c3c3c;
		border-bottom : 3px solid #282828;	
	}
</style>
</head>
<body>
	<form name = "manageForm" id = "manageForm" method="post" action="manageAccount">
	<table id = "manageTable">
		<tr>
			<th></th>
			<th>번호</th>
			<th>아이디</th>
			<th>닉네임</th>
			<th>생년월일</th>
			<th>이메일</th>
			<th>전화번호</th>
			<th></th>
		</tr>
		<tr>
			<td colspan="8" id="titleLine"></td>
		</tr>
		
		<c:set var="count" value="1"/>
		<c:forEach var="AccountVo" items="${sVos}">
		<tr>
			<td>
				<c:if test="${AccountVo.getId()!='admin'}">
					<input type="checkbox" value="${AccountVo.getId()}" name = "id" id="id"/>
				</c:if>
			</td>
			<td>${count}</td>
			<c:set var="count" value="${count+1}"/>
			<td>${AccountVo.getId()}</td>
			<td>${AccountVo.getNick()}</td>
			<td>${AccountVo.getStrBirth()}</td>
			<td>${AccountVo.getEmail()}</td>
			<td>${AccountVo.getTel()}</td>
			<c:if test="${AccountVo.getActivation()==1}">
				<td style="color:#147814;">정상</td>
			</c:if>
			<c:if test="${AccountVo.getActivation()==0}">
				<td style="color:#B9062F;">탈퇴요청</td>
			</c:if>
		</tr>
		<tr>
			<td colspan="8" id="itemLine"></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="9">
				<fmt:formatNumber var="pageLimit" value="5"/>
				<c:set var="pageListCount" value="${param.pageListCount==null? 1:param.pageListCount}"/>
				<c:set var="start" value="${(pageListCount-1)*pageLimit<=0? 1:((pageListCount-1)*pageLimit)+1}"/>
				<c:set var="end" value="${pageListCount*pageLimit>=sLastPage? sLastPage:pageListCount*pageLimit}"/>
				<a href="manageMember?nowPage=1" class="paging">◀◀</a>
				<a href="manageMember?nowPage=<c:out value='${start-1<=0?1:start-1}'/>&pageListCount=<c:out value='${(pageListCount-1)<=0?1:pageListCount-1}' />" class="paging">◀</a>
				<c:if test="${(pageListCount*pageLimit)<sLastPage}">
					<c:forEach var="i" begin="${start}" end="${end}">
						<c:choose>
							<c:when test="${param.nowPage==i}">
								<span style="font-size:17px;font-weight:bolder;"><c:out value="${i}"/></span>
							</c:when>
							<c:otherwise>
								<a href = "manageMember?nowPage=<c:out value='${i}'/>&pageListCount=<c:out value='${pageListCount}'/>" class="paging"><c:out value='${i}'/></a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				<c:if test="${(pageListCount*pageLimit)>=sLastPage}">
					<c:forEach var="i" begin="${start}" end="${sLastPage}">
						<c:choose>
							<c:when test="${param.nowPage==i}">
								<span style="font-size:17px;font-weight:bolder;"><c:out value="${i}"/></span>
							</c:when>
							<c:otherwise>
								<a href = "manageMember?nowPage=<c:out value='${i}'/>&pageListCount=<c:out value='${pageListCount}'/>" class="paging"><c:out value='${i}'/></a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
				<a href="manageMember?nowPage=<c:out value='${end+1>=sLastPage?sLastPage:end+1}'/>&pageListCount=<fmt:formatNumber value='${end==sLastPage?sMaxPageListCount:pageListCount+1}' type='Number'/>" class="paging">▶</a>
				<a href="manageMember?nowPage=<c:out value='${sLastPage}'/>&pageListCount=<c:out value='${sMaxPageListCount}'/>" class="paging">▶▶</a>
			</td>
		</tr>
		<tr>
			<td colspan="8" class="btn">
				<input type="button" id="deleteBtn" value="탈퇴시키기"/>
				<input type = "button" id="requestCancelBtn" value="탈퇴요청 취소"/>
				<input type = "button" id="requestBtn" value="계정비활성화"/><!-- 탈퇴요청과 같다 -->
				<a href="board"><input type = "button" id="backBtn" value="게시판으로"/></a>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>