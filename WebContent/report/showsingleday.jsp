<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/jquery.ui.datepicker-zh-TW.js"/>"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script src="<c:url value="/js/report.js"/>"></script>
<style type="text/css">
table,th,td,tr {
	border-style: double;
}
</style>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>Show Single Day Report</title>
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

						<!-- START Write -->
						<form action="<c:url value="/ReportServlet" />" method="get">
							<input type="submit" class="MainBtnColor" value="查詢單日營運狀況" >請選擇日期: <input
								type="text" id="datepicker" name="date" value="">${errorMsgs.dateError1}${errorMsgs.dateError2}${errorMsgs.dateError3}
						</form>
						<hr>
						<div id="tabs">
							<ul>
								<li><a href="#tabs-1">單日收據明細</a></li>
								<li><a href="#tabs-2">單日餐點統計</a></li>
								<li><a href="#tabs-3">平均消費金額/來客數 分佈</a></li>
							</ul>
							<div id="tabs-1">
								<c:if test="${not empty bills}">
									<table style="margin:0 auto">
										<thead>
											<tr>
												<th>點餐單號</th>
												<th>用餐人數</th>
												<th>消費金額</th>
												<th>折扣名稱</th>
												<th>結帳金額</th>
												<th>結帳員工</th>
												<th>結帳時間</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="bills" items="${bills}">
												<tr>
													<td>${bills.bdyOrder.odId}</td>
													<td>${bills.custNum}</td>
													<td><fmt:formatNumber type="number"
															value="${bills.price/bills.bdyDiscount.disPrice}"
															maxFractionDigits="0" /></td>
													<td>${bills.bdyDiscount.name}</td>
													<td><fmt:formatNumber type="number"
															value="${bills.price}" maxFractionDigits="0" /></td>
													<td>${bills.bdyEmp.name}</td>
													<td>${bills.endDate}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
							<div id="tabs-2">
								<div id="container1"></div>
							</div>
							<div id="tabs-3">
								<div id="container2"></div>
							</div>
						</div>
						<!-- END Write-->
				</div>
				<!-- 	id="writeCodeInThisDiv" -->

			</div>
		</div>
		<div id="footer">
			<jsp:include page="/mainpage/footer.jsp" />
		</div>
	</div>
</body>
</html>