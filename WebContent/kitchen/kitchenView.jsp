<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/kitchen.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RSM - Welcome</title>
</head>
<body>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="mainContent">
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>
<div id="article">

	<div id="writeCodeInThisDiv">
	<div id="accordion" style="width:70%">
	  <h3>出餐總覽</h3>
	  <div>
	  <table border="1">
	  	<tr>
			<th>桌號</th>
			<th>餐點名稱</th>
			<th>點餐時間</th>
			<th>出餐時間</th>
			<th>製作時間</th>
			<th>距離時間</th>	
	  	</tr>
	  		<s:iterator var="item" value="viewlist">
				<tr>
					<td><input type="hidden" name="calc" value="<s:property value="#item.outMealTime.time"/>"><s:property value="#item.tableID"/></td>
					<td><s:property value="#item.orderlistname"/></td>
					<td><s:date name="#item.orderDate" format="yyyy-MM-dd EEEE HH:mm"  /></td>
					<td ><s:date name="#item.outMealTime" format="yyyy-MM-dd EEEE HH:mm"/></td>
					<td><s:property value="%{(#item.outMealTime.time-#item.orderDate.time)/1000/60}"/>分鐘</td>
					<td name="result"></td>
				</tr>
			</s:iterator>
		</table>		
	  </div>
	  <h3>沙拉區</h3>
	  <div>
	    <p>123</p>
	  </div>
	  <h3>湯區</h3>
	  <div>
	    <p>123</p>
	    <ul>
	      <li>List item one</li>
	      <li>List item two</li>
	      <li>List item three</li>
	    </ul>
	  </div>
	  <h3>主餐區</h3>
	  <div>
	    <p>123</p>
	  </div>
	  <h3>甜點區</h3>
	  <div>
	    <p>321 </p>
	  </div>
	</div> 
	<button id="toggle">Toggle icons</button>
	<FORM NAME="clock"><INPUT TYPE="text" NAME="face" SIZE=10></FORM>
	
	
	</div><!-- 	id="writeCodeInThisDiv" -->

</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>

</body>
</html>