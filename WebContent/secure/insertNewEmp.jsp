<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 27~29行JavaScript程式碼 作用等同於 ＜c:url＞的功能 
	   如果有掛載JS檔案 且 利用JavaScript產生有包含路徑讀取專案內其他文件或檔案的時候
	   請在路徑前面 加上 第28行程式碼所產生的變數 "contextPath" 
	   以確保能夠與 ＜c:url＞ 有相同的功能 正確的讀取專案內的文件或檔案
	 for example: (掛載的JS檔案中必須要 動態加入images資料夾下的一張圖片 圖片名稱為 01.jpg )
	 
	 function showIMG(){
	 	var createIMG = document.createElement("img");
	 	var url_IMG = contextPath + "/images/01.jpg";
	 	createIMG.setAttribute( "src" ,  url_Pic );
	 	$('body').append(createIMG);
	 }
	 
	 以上可以確保檔案移動時  不用更改路徑 還是可以正確的讀取所需要的文件及圖片!
-->
<!-- 必要的 Script 與 CSS 外掛 (以下) -->
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<%-- <script src="<c:url value="/js/jquery.js"/>"></script> --%>
<script src=http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/mainpage.js"/>"></script>
<script src="<c:url value="/js/insertEmp.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RMS - Welcome</title>
</head>
<body>
	<div id="loadingControl"></div>
	<div id="loading" style=""><img src="<c:url value="/images/loading.gif"/>"></div>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="mainPageContent">
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>
<div id="article">

		
		
		
		
	<div id="writeCodeInThisDiv" style="text-align: center;">
		<fieldset>
			<legend>新增員工</legend>
		<form action="<c:url value="/secure/insertEmp.action" />" method="post">
			<label>姓名:</label><input type="text" name="emp.name" size="10"  placeholder="guest" autofocus autocomplete="off" value="${param['emp.name']}"><s:property value="%{fieldErrors['emp.name'][0]}"/><br>
			<label>密碼:</label><input type="password" name="emp.passwd" value="${param['emp.passwd']}" tabindex="1"><br>
			<label>性別:</label><input type="radio" name="emp.sex" value="M">男
				<input type="radio" name="emp.sex" value="W">女<br>
			<label>身分證字號:</label><input type="text" name="emp.empId" tabindex="2" value="${param['emp.id']}" pattern="[a-zA-Z]{1}[1-2]{1}\d{8}" accesskey="1"><s:property value="%{fieldErrors['emp.empId'][0]}"/><br>
			<label>職位:</label><s:select
					value="pri" 
					name="emp.bdyPriority.priId"
					listKey="%{priId}"
					listValue="%{jobname}"
					 list="#session['prior']"/><br>
			<label>到職日期:</label><input type="text" name="emp.comedate" value="${param['emp.comedate']}" id="datepicker"><s:property value="%{fieldErrors['emp.comedate'][0]}"/><br>
			<label>薪資</label><input type="text" name="emp.salary" value="${param['emp.salary']}"><br>
			<label>電話</label><input type="text" name="emp.phone" value="${param['emp.phone']}"><s:property value="%{fieldErrors['emp.phone'][0]}"/><br>
			<label>住址</label><input type="text" name="emp.empAddress" value="${param['emp.empAddress']}"><s:property value="%{fieldErrors['emp.empAddress'][0]}"/><br>
				<input type="hidden" name="emp.resign" value="0">					 			
			<input class='MainBtnColor' type="submit" value="送出">
			<input class='MainBtnColor' type="reset" value="清除">
			</form>
			</fieldset>
			<a href="<c:url value="/secure/showEmp.action" />">回員工資料表</a>
	</div><!-- 	id="writeCodeInThisDiv" -->
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>