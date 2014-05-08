<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">

<script src="<c:url value="/js/TableSet.js"/>"></script>
<link rel=stylesheet type="/text/css" href="<c:url value="/css/TableSet.css"/>">
<title>BDY RSM - TableSet</title>
</head>
<body>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>
<div id="article">
	<div style="margin: 0px auto; width: 95%">
		<div style="height: 30px; width: 100%;">
			現在樓層:
			<select id="changeFloor">
			</select>
			<input type="button" id="addTB" value="新增桌子">
			<input type="button" id="reset" value="重新擺設">
		</div>
		<div id="picTB"></div>
		<div id="floorBTN">
		<input type="button" id="addFloor" value="新增樓層">
		<input type="button" id="delFloor" value="刪除樓層">
		<input type="button" id="saveFloor" value="儲存樓層">
		</div>
	</div>
	</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>