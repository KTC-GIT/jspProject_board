<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="contextpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게 시 글</title>
<style>
	#viewTable{
		width : 900px;
		margin-left : auto;
		margin-right : auto;
		font-size : 15px;
		color : #323232;
	}
	#contentArea{
		height : 200px;
		text-align : left;
		vertical-align : top;
	}
	#btn{
		width : 900px;
		margin-left : auto;
		margin-right : auto;
		text-align : right;
		padding : 20px;
	}
	#recbtn{
		padding : 20px;
	}
	#recbtn2{
		width : 60px;
		height : 30px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 2px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	#viewTitle{
		font-size : 20px;
		padding : 8px;
		padding-top : 40px;
	}
	#idDate{
		font-size : 12px;
		padding-bottom : 40px;
	}
	#hits,#reco,#cntrep{
		width : 100px;
		text-align : right;
		font-size : 12px;
		padding-bottom : 20px;
	}
	#listbtn, #modifybtn, #deletebtn{
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
	#modifybtn, #deletebtn{
		background-color : #3c3c3c;
		border-bottom : 3px solid #282828;
	}
	#modifyForm,#deleteForm{
		width : 80px;
		height : 45px;
		float : right;
		text-align : right;
	}
	#file{
		text-align : left;
		border : 2px solid #00BFFF;
		margin-top : 2%;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#deletebtn").click(function(){
			var val = confirm("삭제하시겠습니까?");
			if(val){
				$("#deleteForm").submit();
			}
		});
	});
	
	function recommendUp(){
		var bidx = ${sBidx};
		
		$.ajax({
			url: "recommendUp",
			type: "POST",
			data: bidx,
			success: function(jobj){
				var recommend = jobj.recommend;
				var txt = "추천 "+recommend;
				$("#reco").html(txt);
			},
			error: function(request,status,error){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
			}				
		});
	}
</script>
</head>
<body>
	<table id = "viewTable">
		<tr>
			<td id = "viewTitle" colspan = "4"><b>${vo.title}</b></td>
		</tr>
		<tr class = "postcontent">
			<td id = "idDate">${vo.id}(${vo.ipView}) | &nbsp;${strDate}</td>
			<td id = "hits">조회  ${hits} </td>
			<td id = "reco">추천  ${vo.recommend} </td>
			<td id = "cntrep">댓글  ${count}</td>
		</tr>
		<tr>
			<td colspan = "4" id = "contentArea">${vo.content}</td>
		</tr>
		<tr>
			<td colspan = "4" align = "center" id = "recbtn"><img src="./images/iconmonstr-thumb-15-240.png" title="추천" width="80px" height="80px"  onclick="recommendUp();"/></td>
		</tr>
		<tr>
			<c:choose>
			<c:when test="${fCount!=0 }">
				<td colspan="4" id="file">
					<span style="font-style:bold;font-size:16px;">첨부파일</span>
					<c:forEach items="${fList}" var="fVo">
						<br/><a href="download?fileName=${fVo.fileName}">
							<img src = "./images/iconmonstr-download-thin-240.png" width="15px" height="15px"/>
							${fVo.orifileName}
						</a>
					</c:forEach>
				</td>
			</c:when>
			<c:otherwise>
				<td></td>
			</c:otherwise>
			</c:choose>
		</tr>
	</table>
	
	<c:import url="/viewR"/>
		<div id = "btn">
			<a href = "board"><button id = "listbtn">목록</button></a>
			<c:set var="memberwrite" value="${memberwrite}"/>
			<c:set var="id" value="${id}"/>
			<c:choose>
				<c:when test="${sNick==id && memberwrite==1}">
					<form name="modifyForm" id="modifyForm" method="post" action="modifyCheck">
						<input type="submit" value="수정"	id="modifybtn"/>
						<input type="hidden" value="${vo.id}" name="nick"/>
					</form>
					<form name="deleteForm" id ="deleteForm" method="post" action="delete">
						<input type="button" value="삭제" id="deletebtn"/>
						<input type="hidden" value="${vo.id}" name="nick"/>
					</form>
				</c:when>
				<c:when test="${memberwrite==0 }">
					<a href = "modifyCheck?bidx=${sBidx}"><input type="button" value="수정" id = "modifybtn"/></a>
					<a href = "delete?bidx=${sBidx}"><input type="button" value="삭제" id = "deletebtn"/></a>
				</c:when>
				<c:when test="${sNick!=null && sNick!=id && memberwrite==1}">
					<td></td>
				</c:when>
			</c:choose>
		</div>
	<c:import url="/board"/>
</body>
</html>