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
var activeFloor = '${param.f}';
</script>
<%-- <script src="<c:url value="/js/jquery.js"/>"></script> --%>
<script src=http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/TableSet.css"/>">
<script src="<c:url value="/js/opentable.js"/>"></script>
<%-- <script src="<c:url value="/js/updateTable.js"/>"></script> --%>
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
	<div id="writeCodeInThisDiv">
		<div style="margin: 0px auto; width: 95%; height: 100%">
			<div style="width:980px;position:relative;font-size:1.2em">
				<input class="MainBtnColor" id="chooseFloor" type="button" value="選擇場地"><span class="ui-state-highlight ui-corner-all" style="padding:5px;font-weight: bold;">目前場地：<span id="activeFloor"></span></span>
				<div title="選擇場地" id="selectFloor" style="display:none"></div>
				<div class="stateColorStyle" title='0-閒置中...' style="background-color:green;right:170px;">閒置</div>
				<div class="stateColorStyle" title="1-等待點餐..." style="background-color:orange;right:110px;">點餐</div>
				<div class="stateColorStyle" title="2-用餐中..." style="background-color:purple;right:50px;">用餐</div>
				<div class="stateColorStyle" title="3-有客人預約" style="background-color:#057fd0;right:-10px;">預約</div>
			 </div>
			<div id="picTB"></div>
		</div>
		<div id="tbClickDialog" title="詳細資訊" style="display:none">
			<div>桌子名稱：<span id="tbNameLable">-</span></div>
			<div>容納客數：<span id="tbSizeLable">-</span></div>
			<div>目前狀態：<span id="tbStateLable">-</span></div>
			<div id="countP" style="display:none">
				來客數　：<input id="peopleCount" size="4" type="text" value="0"><br>
				<button type="button" id="numBtn" class="MainBtnColor">0</button>
				<button type="button" id="numBtn" class="MainBtnColor">1</button>
				<button type="button" id="numBtn" class="MainBtnColor">2</button>
				<button type="button" id="numBtn" class="MainBtnColor">3</button>
				<button type="button" id="numBtn" class="MainBtnColor">4</button>
				<button type="button" id="numBtn" class="MainBtnColor">5</button>
				<button type="button" id="numBtn" class="MainBtnColor">6</button>
				<button type="button" id="numBtn" class="MainBtnColor">7</button>
				<button type="button" id="numBtn" class="MainBtnColor">8</button>
				<button type="button" id="numBtn" class="MainBtnColor">9</button>
				<button type="button" id="numBtn" class="MainBtnColor">C</button>
			</div>
			<hr>
			<div id="buttonBar" style='text-align:right;'></div>
		</div>
	</div><!-- 	id="writeCodeInThisDiv" -->
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>