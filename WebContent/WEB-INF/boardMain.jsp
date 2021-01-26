<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게 시 판</title>
<style>
	#boardTable{
		width : 900px;
		text-align : center;
		margin-left:auto;
		margin-right : auto;
		padding-top : 30px;
		color : #323232;
		font-size : 15px;
	}
	.title{
		padding : 6px;
	}
	.bcont{
		width : 300px;
	}
	#titletopline{
		border-top : 3px solid #4646CD;
		padding : 3px;
	}
	#titlebotline{
		border-bottom : 3px solid #4646CD;
		padding : 3px;
	}
	.item2{
		padding : 6px;
	}
	#btn1{
		text-align : left;
		padding-top : 20px;
		
	}
	#btn2{
		text-align : right;
		padding-top : 20px;
		
	}
	
	.datetime{
		font-size : 12px;
	}
	.mhits{
		font-size : 12px;
	}
	.mreco{
		font-size : 12px;
	}
	#boardbotline{
		border-bottom : 3px solid #4646CD;
	}
	a{
		text-decoration : none;
		color : #323232;
	}
	a:hover{
		text-decoration : underline;
	}
	a:visited{
		color : #960a96;
	}
	#mypage:visited{
		color : #323232;
	}
	#sTable{
		width : 900px;
		text-align : center;
		margin-left : auto;
		margin-right : auto;
	}
	#sCell{
		padding : 10px;
	}
	#searchOption, #searchBox, #searchBtn{
		padding : 10px;
		border  : 3px solid #1478FF;
		font-size : 15px;
	}
	#listbtn, #writebtn,#searchBtn,#deleteSubBtn{
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
	#pagingNum{
		font-size : 17px;
	}
	#page{
		font-weight : bold;
		font-size : 20px;
		color : #B93232;
	}
	.pagingNum:visited{
		color : #323232;
	}
	#login{
		text-align : right;
		vertical-align : top;
	}
	.upperbtn{
		border : 0;
		outline : 0;
	}
	.upperbtn:hover{
		text-decoration : underline;
		cursor : pointer;
	}
	.toplink{
		padding:10px;
	}
	.bidxCheck:hover{
		text-decoration : underline;
		cursor : pointer;
	}
	.bidxCheck:visited{
		color : #323232;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#listbtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#writebtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#searchBtn").click(function(){
			$(this).css("border","2px solid black");
		});
		$("#deleteSubBtn").click(function(){
			var cf = confirm("삭제하시겠습니까?");
			if(cf){
				if($("input:checkbox[id='val']").is(":checked")==false){
					alert("삭제할 게시물을 선택하세요");
				}
				else{
					$("#boardForm").submit();
				}
			}
		});
		$(".bidxCheck").click(function(){
			var bidx = $(this).attr("id");
			
			$.ajax({
				url: "serverDownload",
				type: "post",
				data:{
					"bidx": bidx
				},
				success: function(result){
					movePage(bidx);
				},
				error: function(request,status,error){
					alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
				}
				
			});
		});
	});
	
		
	function movePage(bidx){
		var searchOption = "${searchOption}";
		var pageCount= ${sPageCount};
		var listCount= ${sListCount};
		
		location.href = "view?searchOption="+searchOption+"&bidx="+bidx+"&pageCount="+pageCount+"&listCount="+listCount;
	}
</script>
</head>
<body>
		<form name="boardForm" id="boardForm" method="post" action="delete">
		<table id = "boardTable">
			<tr>
				<c:choose>
					<c:when test="${sId=='admin'}">
						<td id = "login" colspan = "8">
							<a href="mypage" id="mypage" class="toplink"><img src="./images/agent.png" width="20px" height="20px"/>${sNick}</a>
							<a href="message.ms" class="toplink"><img src="./images/iconmonstr-gmail-1-240.png" width="20px" height="20px" title="메시지"/></a>
							<a href="manageMember" class="toplink"><img src="./images/iconmonstr-id-card-5-240.png" title="회원관리" width="20px" height="20px"/></a>
							<a href = "logout" class="toplink"><img src = "./images/iconmonstr-door-6-240.png" title="로그아웃" width="20px" height="20px"/></a>
						</td>
					</c:when>
					<c:when test= "${sNick==null}">
						<td id = "login" colspan = "8">
							<a href ="login" class="toplink"><img src = "./images/iconmonstr-door-5-240.png" title="로그인" width="20px" height="20px"/></a>
							<a href = "createAccount" class="toplink"><img src="./images/iconmonstr-friend-1-240.png" title="회원가입" width="20px" height="20px"/></a>							
						</td>
					</c:when>
					<c:otherwise>
						<td id = "login" colspan = "8">
							<a href="mypage" id="mypage" class="toplink"><img src="./images/iconmonstr-medium-4-240.png" width="15px" height="15px"/>${sNick}</a>
							<a href="message.ms" class="toplink"><img src="./images/iconmonstr-gmail-1-240.png" width="20px" height="20px" title="메시지"/></a>
							<a href = "logout" class="toplink"><img src = "./images/iconmonstr-door-6-240.png" title="로그아웃" width="20px" height="20px"/></a>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td id = "titletopline" colspan = "8"></td>
			</tr>
			<tr>
				<th class = "title"></th>
				<th class = "title">번호</th>
				<th class = "title">말머리</th>
				<th class = "title bcont">제목</th>
				<th class = "title">작성자</th>
				<th class = "title date">작성일</th>
				<th class = "title">조회수</th>
				<th class = "title">추천</th>
			</tr>
			<tr>
				<td id = "titlebotline" colspan = "8"></td>
			</tr>
			<c:if test="${sId!='admin'}">
			<c:choose>
				<c:when test="${idxCount==0}">
					<tr><td colspan = "8"> 작성된 게시물이 없습니다.</td></tr>	
				</c:when>
				<c:otherwise>
					<c:forEach var="vo" items="${sList}">
						<c:set var="rCount" value="${dao.countReply(vo.bidx)}"/>
							<tr>
								<td class = "item2"></td>
								<td class = "item2">${vo.bidx}</td>
								<td class = "item2">${vo.tag}</td>
								<td class = "item2 content"><span class = "bidxCheck" id="${vo.bidx}">${vo.title}</span>&nbsp;[${rCount}]</td>
								<c:choose>
									<c:when test="${vo.id=='완장맨' && vo.memberwrite==1}">
										<td class = "item2"><img src="./images/agent.png" width="15px" height="15px"/>${vo.id}</td>
									</c:when>
									<c:when test="${vo.memberwrite==1}">
										<td class = "item2"><img src="./images/iconmonstr-medium-4-240.png" width="15px" height="15px"/>${vo.id}</td>
									</c:when>
									<c:otherwise>
										<td class = "item2">${vo.id}(${vo.ipView})</td>
									</c:otherwise>
								</c:choose>
								<td class = "item2 datetime">${vo.writedateStr}</td>
								<td class = "item2 mhits">${vo.hits}</td>
								<td class = "item2 mreco">${vo.recommend}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</c:if>
				<c:if test="${sId=='admin'}">
				<c:choose>
					<c:when test="${idxCount==0}">
						<tr><td colspan = "8"> 작성된 게시물이 없습니다.</td></tr>	
					</c:when>
					<c:otherwise>
						<c:forEach var="vo" items="${sList}">
							<c:set var="rCount" value="${dao.countReply(vo.bidx)}"/>
								<tr>
									<td class = "item2" id="check">
										<input type="checkbox" value="${vo.bidx}/${searchOption}/${sPageCount}/${ssListCount}" name = "val" id="val"/>
									</td>
									<td class = "item2">${vo.bidx}</td>
									<td class = "item2">${vo.tag}</td>
									<td class = "item2 content"><span class = "bidxCheck" id="${vo.bidx}" onclick="serverDownload();movePage();">${vo.title}</span>&nbsp;[<c:out value="${rCount}"/>]</td>
									<c:choose>
										<c:when test="${vo.id=='완장맨' && vo.memberwrite==1}">
											<td class = "item2"><img src="./images/agent.png" width="15px" height="15px"/>${vo.id}</td>
										</c:when>
										<c:when test="${vo.memberwrite==1}">
											<td class = "item2"><img src="./images/iconmonstr-medium-4-240.png" width="15px" height="15px"/>${vo.id}</td>
										</c:when>
										<c:otherwise>
											<td class = "item2">${vo.id}(${vo.ipView})</td>
										</c:otherwise>
									</c:choose>
									<td class = "item2 datetime">${vo.writedateStr}</td>
									<td class = "item2 mhits">${vo.hits}</td>
									<td class = "item2 mreco">${vo.recommend}</td>
								</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				</c:if>
			<tr>
				<td colspan = "8" id = "boardbotline"></td>
			</tr>
			<tr>
				<td id = "btn1">
					<a href = "board?searchOption=">
					<input type = "button" value = "목록" id = "listbtn"/></a>
				</td>
				<td colspan="4"  id="pagingNum">
					<fmt:formatNumber var="pageListLimit" value="5"/>
					<c:set var="start" value="${((sListCount-1)*pageListLimit)+1<=0?1:((sListCount-1)*pageListLimit)+1}"/>
					<c:set var="end" value="${sListCount*pageListLimit>=lastpage?lastPage:sListCount*pageListLimit}"/>
					
					<a href = "board?searchOption=${searchOption}&pageCount=1&listCount=1" class = "pagingNum">◀◀</a>
					<a href = "board?searchOption=${searchOption}&pageCount=${start-1<=0?1:start-1}&listCount=${sListCount-1==0?1:sListCount-1}" class = "pagingNum">◀</a>
				
						<c:if test="${(sListCount*pageListLimit)<lastPage}">
							<c:forEach var="i" begin="${start}" end="${end}">
								<c:choose>
									<c:when test="${sPageCount == i}">
										<span id="page"><c:out value="${i}"/></span>
									</c:when>
									<c:otherwise>
										<a href = "board?searchOption=<c:out value='${searchOption}'/>&pageCount=<c:out value='${i}'/>&listCount=<c:out value='${sListCount}'/>" class = "pagingNum"><c:out value="${i}"/></a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					<c:if test="${(sListCount*pageListLimit)>=lastPage}">
						<c:forEach var="i" begin="${start}" end="${lastPage}">
							<c:choose>
								<c:when test="${sPageCount == i}">
									<span id="page"><c:out value="${i}"/></span>
								</c:when>
								<c:otherwise>
									<a href = "board?searchOption=<c:out value='${searchOption}'/>&pageCount=<c:out value='${i}'/>&listCount=<c:out value='${sListCount}'/>" class = "pagingNum"><c:out value="${i}"/></a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					<a href = "board?searchOption=<c:out value='${searchOption}'/>&pageCount=<c:out value='${end+1>=lastPage?lastPage:end+1}'/>&listCount=<c:out value='${sListCount+1>listTotal?listTotal:sListCount+1}'/>" class = "pagingNum">▶</a>
					<a href = "board?searchOption=<c:out value='${searchOption}'/>&pageCount=<c:out value='${lastPage}'/>&listCount=<c:out value='${listTotal}'/>" class = "pagingNum">▶▶</a>
				</td>
				
				
				<td id = "btn2" colspan="3">
					<c:if test="${sId=='admin'}">
						<input type="button" value="글삭제" id="deleteSubBtn"/>
					</c:if>
					<a href = "write">
					<input type = "button" value = "글쓰기" id = "writebtn"/></a>
				</td>
			</tr>
		</table>
		</form>
			<form method = "post" action = "search">
			<table id = "sTable">
				<tr>
					<td id = "sCell">
						<select name = "searchOption" id = "searchOption">
							<option value = "id">작성자</option>
							<option value = "title">제목</option>
							<option value = "content">내용</option>
						</select>
						<input type = "text" name = "searchBox" id = "searchBox" placeholder = "검색할 내용을 입력하세요."/>
						<input type = "submit" value = "검색" id = "searchBtn"/>
					</td>
				</tr>
			</table>
		</form>
		<p><br/></p>
</body>
</html>