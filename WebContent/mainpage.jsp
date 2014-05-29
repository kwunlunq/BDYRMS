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
<%-- <script src="<c:url value="/js/jquery.js"/>"></script> --%>
<script src=http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/mainpage.js"/>"></script>
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
	<div id="mainPageAcdionTittle">
		<h2>最新消息~
			<span id="newsBtnBox" style="float:right">
			<input class="MainBtnColor" id="btnAddNews" type="button" value="發佈消息">
			</span>
		</h2>
	</div>
	<div id="addNewsDIV" style="display:none">
		<form id="addNewsForm" method="post" action="<c:url value='/news?act=postNews' />">
			<span style="font-size:1.1em;">發佈人: ${empData.name}</span><br>
			<div style="float:left;width:70%">
				<span style="font-size:1.1em;">標題:</span><span style="color:red" id="errorTitle"></span>
				<input name="newsTitle" style="width:100%;font-size:1.1em;" type="text">	
			</div>		
			<div style="float:left;margin-left:10px;width:28.5%">
				<span style="font-size:1.1em;">類別:</span>
				<select name="newsType" style="font-size:1.1em;width:100%">
					<option value="餐廳管理">餐廳管理</option>
					<option value="系統管理">系統管理</option>
					<option value="其　　他">其　　他</option>
				</select>
			</div>
			<div style="clear:both">
			<span style="font-size:1.1em;">內容:</span><span style="color:red" id="errorContent"></span>
			<textarea name="newsContent" rows="7" style="width:100%;font-size:1.1em;"></textarea>
			</div>
			<input name='newsPostname' type='hidden' value="${empData.name}">
		</form>
	</div>
	<div id="mainPageAcdion">
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