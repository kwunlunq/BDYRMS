<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<c:url value="/images/BDYLogoHeadIcon.png"/>" />
<title>BDY RMS - Login</title>
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/orderinitial.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<script>
function doLogin(){
	$('#titleMainBox').animate({width:"0px"},800,function(){
		$('#titleMainBox').css({display:'none'});
		$('#loginMainBox').css({display:'block'});
		$('#loginMainBox').animate({width:"300px"},1000,function(){
			$('form').submit();
			self.setInterval("countLoginTime()",1000);
		});
	});
}
$(function(){
	$('#titleMainBox').css({display:'block'});
	$('#titleMainBox').animate({left:"72%"},800);
	$(function() {
	    $("body").keypress(function(event){       
	        if (event.keyCode == 13) doLogin();
	    });
	});
});
var count = 1;
function countLoginTime(){
	if(count<=30){
		$('h3').text(count + " s");
	}else{
		$('#loginState').text("登入逾時");
		$('h3').html("<input class='MainBtnColor' type='button' value='再試一次' onclick='location.reload();'><h5>或 繼續等待 "+count+ " s<h5>");
	}
	count++;
}
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
	h1{
		height:30px;
	}
	input{
		border-radius:10px;
		width:80%;
		height:30px;
		font-size:1.2em;
		text-align: center;
		margin-top:3px;
		margin-bottom:3px;]
		outline:0 none;
	}
	input:focus, textarea:focus, select:focus {
		outline:0 none;
	}
	input[type='text']{
		text-transform:uppercase;
	}
	.errorBox{
		color:red;
	}
	#titleMainBox{
		position:absolute;
		height:430px;
		width:300px;
		top:50%;
		margin-top:-200px;
		left:110%;
	}
	#titleTextBox,#loginMessage{
		position:absolute;
		height:430px;
		width:300px;
		text-align:center;
	}
	#titleBGBox,#loginBGBox{
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
	#loginState{margin-top:-10px;}
	h5{margin-top:5px;}
</style>
</head>
<body>
<div  id="titleMainBox">
<div id="titleBGBox">
</div>
<div id="titleTextBox">
<h1>巴豆妖</h1>
<h4>Restaurant Management Syetem</h4>
<img src="<c:url value='/images/BADOYAO_LogoSmall.gif'/>">
<form action="<c:url value="/secure/login.action" />" method="post">
<input type="text" name="userID" maxlength="10" placeholder="帳號" value="${param.userID }"><br>
<input type="password" name="userPW" placeholder="密碼" value="${param.userPW }"><br>
<input class="MainBtnColor" type="button" name="btnGetIn" onclick="doLogin()" value="登入">
<div class="errorBox"><s:property value="%{fieldErrors['userID'][0]}"/></div>
<div class="errorBox"><s:property value="%{fieldErrors['userPW'][0]}"/></div>
</form>
</div>
</div>

<div id="loginMainBox">
<div id="loginBGBox">
</div>
<div  id="loginMessage">
<h1>巴豆妖</h1>
<h4>Restaurant Management Syetem</h4>
<img src="<c:url value='/images/BADOYAO_LogoSmall.gif'/>">
<br><br>
<h1 id="loginState">登入中...</h1>
<img src="<c:url value='/images/loading.gif'/>" />
</div>
</div>
</body>
</html>