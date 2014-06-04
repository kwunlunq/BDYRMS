		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="shortcut icon" href="<c:url value="/images/BDYLogoHeadIcon.png"/>" />
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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/TableSet.css"/>">
<script src="<c:url value="/js/TableSet.js"/>"></script>
<script src="<c:url value="/js/orderinitial.js"/>"></script>
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
<div id="mainPageContent">
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>
<div id="article">
	<div id="writeCodeInThisDiv">
		<div style="margin: 0px auto; width: 95%; height: 100%">
			<div style="width: 100%;font-size:1.25em">
				目前場地:
				<select id="changeFloor">
				</select>
				<input class="MainBtnColor" type="button" id="addTB" value="新增桌子">
				<input class="MainBtnColor" type="button" id="reLoad" value="重新讀取">
				<span>擺設數量：<span id="tableCount" style="color:red">0</span></span>
				<span style="display:inline-block;float:right;">
				<input class="MainBtnColor" type="button" id="addFloor" value="新增場地">
				<input class="MainBtnColor" type="button" id="editFloor" value="編輯場地">
				<input class="MainBtnColor" type="button" id="saveFloor" value="儲存擺設">
				</span>
			</div>
			<div id="picTB"></div>
		</div>
		<div id="addTbDialog" title="新增桌子" style='display:none'>
			<p>桌子名稱：<span id='addTbNameError' style='color:red;font-size:0.8em'></span></p>
			<input id='addTbNameText' type="text">
			<p>容納人數：<span id='addTbSizeError' style='color:red;font-size:0.8em'></span></p>
			<input id='addTbSizeText' type="number">
		</div>
		<div id="addFloorDialog" title="新增場地" style='display:none'>
			<p>場地名稱：<span id='addFloorNameError' style='color:red;font-size:0.8em'></span></p>
			<input id='addFloorNameText' type="text">
		</div>
		<div id="editFloorDialog" title="編輯場地" style='display:none'>
			<ul id="sortableFloor">
			</ul>
		</div>
		<div id="editTableDialog" title="編輯桌子" style='display:none'>
			<p>桌子名稱：<span id='editTbNameError' style='color:red;font-size:0.8em'></span></p>
			<input id='editTbNameText' type="text">
			<p>容納人數：<span id='editTbSizeError' style='color:red;font-size:0.8em'></span></p>
			<input id='editTbSizeText' type="number">
		</div>
	</div>
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>