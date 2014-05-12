<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BDY Login</title>
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<style>
body,html{
	width:100%;
	heght:100%;
	margin:0 auto;
	background-image: url("/BDYRMS/images/loginBg.jpg");
	background-size: 100% 100%;
	background-repeat: no-repeat;
	background-position: center;
	}
	#inputBox{
		margin-top:10px;
	}
	input[type='text'],input[type='password']{
		width:80%;
		height:30px;
		font-size:1.2em;
		text-align: center;
		margin-top:3px;
		margin-bottom:3px;
	}
	.errorBox{
		color:blue;
	}
	#titleTextBox{
		text-align:center;
		position:absolute;
		width:300px;
		top:30%;
		left:70%;
	}
	#titleBGBox{
		position:absolute;
		width:300px;
		top:30%;
		left:70%;
		background:white;
		opacity:0.5;
		height:300px;
	}
	.loninBtnSize{
		width:80%;
	}
</style>
</head>
<body>
<div id="titleBGBox">
</div>
<div id="titleTextBox">
<h2>BaDoYaw</h2>
<h4>Restaurant Management Syetem</h4>
<form action="<c:url value="/secure/login.action" />" method="post">
<div id="inputBox">
<input type="text" name="userID" placeholder="帳號" value="${param.userID }"><br>
<input type="password" name="userPW" placeholder="密碼" value="${param.userPW }"><br>
<input class="MainBtnColor loninBtnSize" type="submit" name="btnLogin" value="登入">
</div>
<div class="errorBox">${errors.userID}</div>
<div class="errorBox">${errors.userPW}</div>
</form>
</div>
</body>
</html>