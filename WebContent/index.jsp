<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BDY Login</title>
</head>
<body style="background-color: #D1E9E9">
<div style="width: 500px; height: 300px; position:absolute;left:50%;top:40%;margin-left:-250px;margin-top:-150px;">
<img src="<c:url value="/images/BDY_LogoColorful.png" />">
<div style="width: 500px;text-align:center;">

<form action="<c:url value="/secure/login.action" />" method="get">

帳號:<input type="text" name="userID" value="${param.userID }">

密碼:<input type="password" name="userPW" value="${param.userPW }">


<input style="width:100px;" type="submit" name="btnLogin" value="登入">
<td><span style="color: red">${errors.userID}</span></td>
<td ><span style="color: red">${errors.userPW}</span></td>

</form>
</div>
</div>
</body>
</html>