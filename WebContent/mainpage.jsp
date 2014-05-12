<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div style="text-align:center"><h2>BDYRMS即時公告</h2></div>
	<div id="mainPageAcdion">
	<h3>使用Jquery UI 注意事項  <span style="color:red">(重要  20140512)</span></h3>
		<div>
			Jquery UI套件都是使用 ID名稱控制物件，各位取ID名稱時注意不要使用原來Demo的名稱，<br>
			請加上自己要使用的功能的名字來判斷，避免多人使用同一個套件時大家都取一樣的名稱而造成衝突。<br>
			舉例:<br>
			mainpage.jsp 使用了Accordion(口風琴)套件 Demo的名稱為 $( "#accordion" ).accordion();<br>
			假如同時有人使用同一個套件沒有改名稱，會發生造成衝突的可能，mainpage.jsp使用這個套件時，可修改為 id="mainPageAccordion"<br>
			若覺得名稱太長可以酌量的使用縮寫 => mainPageAcdion or mPageAccor or mPAccordion 請自行發揮 自己看得懂為主
		</div>
		<h3>MainPage樣版更新小撇步  <span style="color:red">(更新 20140512)</span></h3>
		<div>
Step 1. 複製一份mainpage.jsp 名稱改成自己的功能名稱<br>
Step 2. 將複製過來的mainpage.jsp(現在是自己的了)，將&ltdiv id="writeCodeInThisDiv"&gt XXX &lt/div&gt 裡的內容全部刪掉<br>
Step 3. 接著把自己已經寫好的內容 複製到 剛剛刪掉的地方=>&ltdiv id="writeCodeInThisDiv"&gt XXX &lt/div&gt<br>
Finish. 存檔 收工!<br>
		</div>
		<h3>Jquery UI Demo</h3>
		<div>
[<a target="_blank" href="<c:url value="/jqueryUIDemo/index.html"/>">Link</a>]<br>
有需要使用到Jquery UI 的套件可以點Link前往後 按右鍵檢視原始碼 可以參考寫法及CSS樣式的使用方式
以上JS及CSS相關檔案已經匯入 直接寫程式碼即可使用.....
		</div>
		<h3>Button 有固定樣式 (長相請往左邊看)</h3>
		<div>
有需要使用這個樣式 請加上 class="MainBtnColor"
--注意 此樣式只有顏色 若要固定大小及字體大小 請另外寫<br>
方法一 (直接寫style):<br>
&nbsp&nbsp&ltinput type="button" class="MainBtnColor" style="width:100px;"&gt
<br>
方法二 (多寫一個Class):<br>
&nbsp&nbsp&ltstyle&gt<br>
&nbsp&nbsp.btnsize{<br>
&nbsp&nbsp&nbsp&nbspwidth:100px;<br>
&nbsp&nbsp}<br>
&nbsp&nbsp&lt/style&gt<br>
&nbsp&nbsp&ltinput type="button" class="MainBtnColor btnsize"&gt
		</div>
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