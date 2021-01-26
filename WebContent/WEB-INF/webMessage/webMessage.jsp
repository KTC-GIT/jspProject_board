<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 관리</title>
<style>
	body{
		text-align:center;
	}
	#leftBar{
		width:25%;
		height:620px;
		text-align: center;
		float:left;
		background-color:#ddd;
	}
	#rightSide{
		width:75%;
		height:600px;
		text-align:left;
		background-color:#eee;
	}
	.leftBar-p{
		padding:20px;
		font-size:20px;
		font-style:bold;
	}
	a{
		text-decoration:none;
		color:#000069;
	}
	a:hover{
		text-decoration:underline;
	}
</style>
<script type="text/javascript">
	function openWriteMessage(){
		var url = "writeMessage.ms";
		var name = "메시지 작성";
		
		window.open(url,name,"width=600,height=600");
	}
</script>
</head>
<body>
	<div>
		<h2>메시지 관리</h2>(현재 접속자:${sNick})
	</div>
	<br/>
	<div id="leftBar">
		<p class="leftBar-p"><a href="javascript:void(0)" onclick="openWriteMessage();">메시지 작성</a></p>
		<p class="leftBar-p"><a href="message.ms?msw=1">받은 메시지</a></p>
		<p class="leftBar-p"><a href="message.ms?msw=2">새 메시지</a></p>
		<p class="leftBar-p"><a href="message.ms?msw=3">보낸 메시지</a></p>
		<p class="leftBar-p"><a href="message.ms?msw=4">수신확인</a></p>
		<p class="leftBar-p"><a href="message.ms?msw=5">휴지통</a></p>
		<p class="leftBar-p"><a href="board">게시판으로</a></p>
	</div>
	<div id="rightSide">
		<p>
			<c:if test="${param.msw == null}">
				<h3 style="text-align:center;">받은 메시지</h3>
				<jsp:include page="/WEB-INF/webMessage/messageList.jsp"/>
			</c:if>
			<c:if test="${param.msw == 1 }">
				<h3 style="text-align:center;">받은 메시지</h3>
				<jsp:include page="/WEB-INF/webMessage/messageList.jsp"/>
			</c:if>
			<c:if test="${param.msw == 2 }">
				<h3 style="text-align:center;">새 메시지</h3>
				<jsp:include page="/WEB-INF/webMessage/newMessageList.jsp"/>
			</c:if>
			<c:if test="${param.msw == 3 }">
				<h3 style="text-align:center;">보낸 메시지</h3>
				<jsp:include page="/WEB-INF/webMessage/sendMessageList.jsp"/>
			</c:if>
			<c:if test="${param.msw == 4 }">
				<h3 style="text-align:center;">수신확인</h3>
				<jsp:include page="/WEB-INF/webMessage/readMessageCheck.jsp"/>
			</c:if>
			<c:if test="${param.msw == 5 }">
				<h3 style="text-align:center;">휴지통</h3>
				<jsp:include page="/WEB-INF/webMessage/trashCanList.jsp"/>
			</c:if>
		</p>
	</div>
</body>
</html>