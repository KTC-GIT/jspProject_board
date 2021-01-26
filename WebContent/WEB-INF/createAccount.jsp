<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회 원 가 입</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	var yearCheckVal = 0;			//윤년이면 1, 아니면 0
	
	var idCheck = 0;		//체크버튼을 누르면 1, 아니면 0
	var nickCheck = 0;		//체크버튼을 누르면 1, 아니면 0
	
	$(document).ready(function(){
		$("#tel1").keyup(function(){
			var num = /^[0-9]*$/g;
			var tel = $("#tel1").val();
			if(!num.test(tel)){
				$("#tel1").val("");
			}
			else if(tel.length == 3){
				$("#tel2").focus();
			}
		});
		$("#tel2").keyup(function(){
			var num = /^[0-9]*$/g;
			var tel = $("#tel2").val();
			if(!num.test(tel)){
				$("#tel2").val("");
			}
			else if(tel.length == 4){
				$("#tel3").focus();
			}
		});
		$("#tel3").keyup(function(){
			var num = /^[0-9]*$/g;
			var tel = $("#tel3").val();
			if(!num.test(tel)){
				$("#tel3").val("");
			}
		});
		
		
		$("#pw").keyup(function(){
			var keyValue = /^[0-9a-zA-Z!@#$%]*$/g;
			var pw = $("#pw").val();
			if(!keyValue.test(pw)){
				$("#pw").val("");
			}
		});
		$("#pwCheck").keyup(function(){
			var keyValue = /^[0-9a-zA-Z!@#$%]*$/g;
			var pwCheck = $("#pwCheck").val();
			if(!keyValue.test(pwCheck)){
				$("#pwCheck").val("");
			}
		});
		
		$("#pwCheck").keyup(function(){
			if($("#pw").val() == $("#pwCheck").val()){
				$("#pwCheckLabel").text("비밀번호가 일치합니다!");
				$("#pwCheckLabel").css("color","#0A6E0A");
			}
			else{
				$("#pwCheckLabel").text("비밀번호가 일치하지 않습니다!");
				$("#pwCheckLabel").css("color","#B91A4D");
			}
		});
		
		$("#id").keyup(function(){
			var keyValue = /^[0-9a-zA-Z]*$/g;
			var pw = $("#id").val();
			if(!keyValue.test(pw)){
				$("#id").val("");
			}
		});
		$("#nick").keyup(function(){
			var keyValue = /^[0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]*$/g;
			var pw = $("#nick").val();
			if(!keyValue.test(pw)){
				$("#nick").val("");
			}
		});
		
	});
	
	function idCheckFunc(){
		idCheck = 1;
		
		var id = cAForm.id.value;
		var url = "idCheck?id="+id;
		var name = "아이디 중복 체크";
		
		window.open(url,name,"width=610,height=300");
		
	}
	
	function nickCheckFunc(){
		nickCheck = 1;
		
		var nick = cAForm.nick.value;
		var url = "nickCheck?nick="+nick;
		var name = "닉네임 중복 체크";
		
		window.open(url,name,"width=610,height=300");
		
	}
	
	function submitFunc(){
		var id = cAForm.id.value;
		var pw = cAForm.pw.value;
		var pwCheck = cAForm.pwCheck.value;
		var nick = cAForm.nick.value;
		var emailId = cAForm.emailId.value;
		var emailDomain = cAForm.emailDomain.value;
		var tel1 = cAForm.tel1.value;
		var tel2 = cAForm.tel2.value;
		var tel3 = cAForm.tel3.value;
		var year = cAForm.year.value;
		var month = cAForm.month.value;
		var day = cAForm.day.value;
		
		if(id == ""){
			alert("아이디를 입력하세요");
			$("#id").focus();
		}
		if(id.length<6 || id.length>20){
			alert("아이디는 6~20자리만 가능합니다!");
			$("#id").focus();
		}
		else if(pw == ""){
			alert("비밀번호를 입력하세요");
			$("#pw").focus();
		}
		else if(pw.length<6 || pw.length>20){
			alert("비밀번호는 6~20자리만 가능합니다!");
			$("#pw").focus();
		}
		else if(pw != pwCheck){
			alert("비밀번호를 확인하세요!");
			$("#pw").focus();
		}
		else if(nick == ""){
			alert("닉네임을 입력하세요");
			$("#nick").focus();
		}
		else if(nick.length<1 || nick.length>10){
			alert("닉네임은 1~10자리만 가능합니다!");
			$("#nick").focus();
		}
		else if(year == "" || month == "" || day == ""){
			alert("생년월일을 선택하세요!");
		}
		else if(emailId == ""){
			alert("이메일을 입력하세요");
			$("#emailId").focus();
		}
		else if(emailDomain == ""){
			alert("도메인을 선택하세요");
		}
		else if(tel1 == "" || tel2 == "" || tel3 == ""){
			alert("전화번호를 정확히 입력하세요");
		}
		else if(idCheck==0){
			alert("아이디 중복확인하세요!");
		}
		else if(nickCheck==0){
			alert("닉네임 중복확인 하세요!");
		}
		else{
			idCheck = 0;
			nickCheck = 0;
			cAForm.submit();
		}
		
	}
	
	function yundalCheck(){
		var year = parseInt(cAForm.year.value);
		
		if(year%4==0 && year%100==0){
			yearCheckVal=0;
		}
		else if(year%4==0){
			yearCheckVal=1;
		}
		else if(year%4==0 && year%100==0 && year%400==0){
			yearCheckVal=1;
		}
		else{
			yearCheckVal=0;
		}
	}
	
	function monthCheck(){
		var month = cAForm.month.value;
		var text = "";
		
		if(month=="1" || month=="3" || month=="5" || month=="7" || month=="8" || month=="10" || month=="12") {
			txt = "<select name = 'day' id = 'day'><option value=''>선택하세요</option>";
			for(var i=1;i<=31;i++){
				txt += "<option value='"+i+"'>"+i+"</option>";
			}
			txt += "</select>일";
			
			document.getElementById("day").innerHTML = txt;
		}
		else if(month=="2" && yearCheckVal==1){
			txt = "<select name = 'day' id = 'day'><option value=''>선택하세요</option>";
			for(var i=1;i<=29;i++){
				txt += "<option value='"+i+"'>"+i+"</option>";
			}
			txt += "</select>일";
			document.getElementById("day").innerHTML = txt;
		}
		else if(month=="2"){
			txt = "<select name = 'day' id = 'day'><option value=''>선택하세요</option>";
			for(var i=1;i<=28;i++){
				txt += "<option value='"+i+"'>"+i+"</option>";
			}
			txt += "</select>일";
			document.getElementById("day").innerHTML = txt;
		}
		else{
			txt = "<select name = 'day' id = 'day'><option value=''>선택하세요</option>";
			for(var i=1;i<=30;i++){
				txt += "<option value='"+i+"'>"+i+"</option>";
			}
			txt += "</select>일";
			document.getElementById("day").innerHTML = txt;
		}
	}
	
	function emailFunc(){
		var domain = cAForm.emailDomainSelect.value;
		
		if(domain == "직접 입력"){
			document.getElementById("emailDomainText").innerHTML = "<input type = 'text' name = 'emailDomain' class='txtbox' placeholder = '직접 입력'/>";
		}
		else{
			document.getElementById("emailDomainText").innerHTML = "<input type = 'text' name = 'emailDomain' id='emailDomain' class='txtbox' value='"+domain+"' readonly/>";
		}
	}
</script>
<style>
	#cATable{
		margin-left : auto;
		margin-right : auto;
		text-align : left;
		width : 900px;
		padding-top : 8%;
		
	}
	#pwText{
		font-size : 10px;
	}
	#emailDomain{
		background-color : #aaaaaa;
	}
	#btns{
		text-align : center;
	}
	.txtbox{
		padding : 10px;
		font-size : 15px;
	}
	.btn{
		width : 135px;
		height : 45px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	#cancelbtn{
		background-color : #3c3c3c;
		border-bottom : 3px solid #282828;		
	}
	.title{
		width : 130px;
		font-size : 17px;
		font-weight : bolder;
	}
	select{
		padding:10px;
		font-size:15px;
	}
	td{
		padding:10px;
	}
	.txt{
		font-size:17px;
		font-weight:bolder;
		padding:5px;
	}
	#btns{
		text-align : left;
		padding-left : 20%;
		padding-top : 20px;
	}
</style>
</head>
<body>
	<form name = "cAForm" method="post" action="createAccountCheck">
		<table id = "cATable">
			<tr>
				<td class="title"><label for="id"><img src="./images/iconmonstr-id-card-8-240.png" width="15px" height="15px"/>아이디</label></td>
				<td  colspan = 3 id="idTextBox">
					<input type="text" name = "id" id = "id" class="txtbox" size = "40" maxlength = "20" placeholder = "6~20자리 가능/영문대소문자와 숫자만 가능"/>
					<input type = "button" value="ID중복확인" class="btn" onclick="idCheckFunc()"/>
				</td>
			</tr>
			<tr>
				<td class="title"><label for="pw"><img src="./images/iconmonstr-key-thin-240.png" width="15px" height="15px"/>비밀번호</label></td>
				<td colspan = 3><input type="password" name = "pw" id="pw" class="txtbox" placeholder="6~20자리만 가능/숫자,영문 소대문자,!,@,#,$,%가능" size = "40" maxlength = "20"/></td>
			</tr>
			<tr>
				<td class="title"><label for="pwCheck"><img src="./images/iconmonstr-key-thin-240.png" width="15px" height="15px"/>비밀번호 확인</label></td>
				<td colspan = 2><input type="password" name = "pwCheck" class="txtbox" id="pwCheck" placeholder="비밀번호와 똑같이 입력할 것" size = "40" maxlength = "20"/></td>
				<td id="pwCheckLabel">&nbsp;</td>
			</tr>
			<tr>
				<td class="title"><label for="nick"><img src="./images/iconmonstr-tag-7-240.png" width="15px" height="15px"/>닉네임</label></td>
				<td colspan = 3 id = "nickTextBox">
					<input type = "text" name = "nick" id = "nick" class="txtbox" size = "40" maxlength="10" placeholder = "1~10자리 가능"/>
					<input type = "button" value = "닉네임 중복확인" class="btn" onclick="nickCheckFunc()"/>
				</td>
			</tr>
			<tr>
				<td class="title"><img src="./images/iconmonstr-candy-18-240.png" width="15px" height="15px"/>생년월일</td>
				<td>
					<select name = "year" onchange="yundalCheck()">
						<option value="">선택하세요</option>
						<c:forEach var="i" begin="0" end="${2020-1940}">
							<c:set var="year" value="${2020-i}"/>
							<option value="${year}">${year}</option>
						</c:forEach>
					</select><span class = "txt">년</span>
				
					<span>
						<select name = "month" onchange="monthCheck()">
							<option value="">선택하세요</option>
							<c:forEach var="i" begin="1" end="12">
								<option value="${i}">${i}</option>
							</c:forEach>
						</select><span class = "txt">월</span>
					</span>
					<span id="dayCombo" class="title">
						<select name = "day" id = "day">
							<option value="">선택하세요</option>
							<c:forEach var="i" begin="1" end="31">
								<option value="${i}">${i}</option>
							</c:forEach>
						</select><span class = "txt">일</span>
					</span>
				</td>
			</tr>
			<tr>
				<td class="title"><label for="emailId"><img src="./images/iconmonstr-gmail-1-240.png" width="15px" height="15px"/>E-mail</label></td>
				<td colspan="3">
					<input type="text" class="txtbox" name="emailId" id="emailId"/><span class = "txt">@</span>
					<span id = "emailDomainText"><input type = "text" name = "emailDomain" id="emailDomain" class="txtbox" readonly/></span>
					<select name="emailDomainSelect" id="emailDomainSelect" onchange="emailFunc()">
						<option value="">선택하세요</option>
						<option value="google.com">google.com</option>
						<option value="naver.com">naver.com</option>
						<option value="hanmail.net">hanmail.net</option>
						<option value="nate.com">nate.com</option>
						<option value="직접 입력">직접 입력</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title"><label for="tel1"><img src="./images/iconmonstr-mobile-thin-240.png" width="15px" height="15px"/>휴대전화</label></td>
				<td colspan="3">
					<input type = "text" name = "tel1" id = "tel1" class="txtbox" size = "10" maxlength="3"/><span class="txt">-</span>
					<input type = "text" name = "tel2" id = "tel2" class="txtbox" size = "10" maxlength="4"/><span class="txt">-</span>
					<input type = "text" name = "tel3" id = "tel3" class="txtbox" size = "10"maxlength="4"/>
				</td>
			</tr>
			<tr>
				<td colspan = "3" id = "btns">
					<input type = "button" value = "가입" class="btn" onclick="submitFunc()"/>
					<input type = "reset" class="btn" value = "다시쓰기"/>
					<input type = "button" id="cancelbtn" class="btn" value = "뒤로" onclick="history.back();"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>