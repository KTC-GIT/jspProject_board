<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로 그 인</title>
<style>
	#loginTable{
		width : 400px;
		margin-right : auto;
		margin-left : auto;
		text-align:center;
		padding-top:15%;
	}
	#id,#pw{
		padding:15px;
		font-size:15px;
	}
	.btn{
		width : 100px;
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
	#btns{
		padding-top:10px;
	}
</style>
</head>
<body>
	<form method="post" action="loginCheck">
		<table id = "loginTable">
			<tr>
				<td><input type="text" name="id" id = "id" size="25" placeholder = "아이디"/></td>
			</tr>
			<tr>
				<td><input type = "password" name = "pw" id="pw"  size="25" placeholder = "비밀번호"/></td>
			</tr>
			<tr>
				<td id="btns">
					<input type = "submit" value = "로그인" id="loginbtn" class="btn"/>
					<a href="createAccount"><input type = "button" value = "회원가입" id="CAbtn" class="btn"/></a>
					<input type = "button" value = "취소" id = "cancelbtn"  class="btn"onclick = "history.back();"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>