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
<script>
function doLogin(){
	$('#titleMainBox').animate({width:"0px"},800,function(){
		$('#titleMainBox').css({display:'none'});
		$('#loginMainBox').css({display:'block'});
		$('#loginMainBox').animate({width:"300px"},800,function(){
			$('form').submit();
		});
	});
}
$(function(){
	$('#titleMainBox').css({display:'block'});
	$('#titleMainBox').animate({left:"72%"},800);
});
</script>
<style>
body,html{
	overflow:hidden;
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
		color:red;
	}
	#titleMainBox{
		float:left;
		position:absolute;
		height:430px;
		width:300px;
		top:50%;
		margin-top:-200px;
		left:110%;
	}
	#titleTextBox{
		position:absolute;
		height:430px;
		width:300px;
		text-align:center;
	}
	#titleBGBox{
		position:absolute;
		height:430px;
		width:300px;
		border-radius:10px;
		background:white;
		opacity:0.5;
	}
	
	
	#loginMainBox{
		display:none;
		position:absolute;
		height:430px;
		width:0px;
		text-align:center;
		top:50%;
		margin-top:-200px;
		left:72%;
	}
	#loginBGBox{
		position:absolute;
		height:430px;
		width:300px;
		border-radius:10px;
		background:white;
		opacity:0.5;
	}
	#loginMessage{
		position:absolute;
		height:430px;
		width:300px;
		text-align:center;;
	}
	
	
	.loninBtnSize{
		width:80%;
	}
	h2{
		height:10px;
	}
</style>
</head>
<body>
<div  id="titleMainBox">
<div id="titleBGBox">
</div>
<div id="titleTextBox">
<h2>巴豆妖</h2>
<h4>Restaurant Management Syetem</h4>
<img src="<c:url value='/images/BADOYAO_LogoSmall.gif'/>">
<form action="<c:url value="/secure/login.action" />" method="post">
<div id="inputBox">
<input type="text" name="userID" placeholder="帳號" value="${param.userID }"><br>
<input type="password" name="userPW" placeholder="密碼" value="${param.userPW }"><br>
<input class="MainBtnColor loninBtnSize" type="button" name="btnGetIn" onclick="doLogin()" value="登入">
</div>
<div class="errorBox">${errors.userID}</div>
<div class="errorBox">${errors.userPW}</div>
</form>
</div>
</div>

<div id="loginMainBox">
<div id="loginBGBox">
</div>
<div  id="loginMessage">
<h2>巴豆妖</h2>
<h4>Restaurant Management Syetem</h4>
<img src="<c:url value='/images/BADOYAO_LogoSmall.gif'/>">
<h1>登入中...</h1>
</div>
</div>
</body>
</html>