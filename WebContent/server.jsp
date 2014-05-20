<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type='text/javascript' src='/BDYRMS/js/jquery.js'></script>
<script type='text/javascript' src='/BDYRMS/dwr/engine.js'></script>
<script type='text/javascript' src="/BDYRMS/dwr/util.js"></script>
<script type='text/javascript' src='/BDYRMS/dwr/interface/Service.js'></script>
<title>Insert title here</title>
<script type="text/javascript">
	function sendnews() {
// 		var new_content = document.getElementById("newcon").value;
		Service.update({value:"12322"});
// 		myrevsrse.sendMes(new_content);
	}
</script>
</head>

<body onload="dwr.engine.setActiveReverseAjax(true);">
<!-- 	<input type="text" name="newcontent"> -->
	<input type="button" value="send" onclick="sendnews()" />
</body>
</html>