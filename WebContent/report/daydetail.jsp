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
<script src="<c:url value="/js/daydetail.js"/>"></script>
<style type="text/css">
table,th,td,tr {
	border-style: double;
}

.ui-tabs-vertical {
	width: 55em;
}

.ui-tabs-vertical .ui-tabs-nav {
	padding: .2em .1em .2em .2em;
	float: left;
	width: 12em;
}

.ui-tabs-vertical .ui-tabs-nav li {
	clear: left;
	width: 100%;
	border-bottom-width: 1px !important;
	border-right-width: 0 !important;
	margin: 0 -1px .2em 0;
}

.ui-tabs-vertical .ui-tabs-nav li a {
	display: block;
}

.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active {
	padding-bottom: 0;
	padding-right: .1em;
	border-right-width: 1px;
	border-right-width: 1px;
}

.ui-tabs-vertical .ui-tabs-panel {
	padding: 1em;
	float: right;
	width: 40em;
}
</style>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>Single Day Report Detail</title>
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
					<form action="<c:url value="/DayReportServlet" />" method="get">
						<input type="submit" class="MainBtnColor" value="查詢單日營運狀況">請選擇日期
						: <input type="text" id="datepicker" name="date"
							value="${param.date}">${errorMsgs.dateError1}${errorMsgs.dateError2}${errorMsgs.dateError3}
					</form>
					<a href="http://localhost:8080/BDYRMS/report/reportmenu.jsp">返回報表選單</a>
					<hr>
					<div id="dayReportTabs">
						<ul>
							<li><a href="#dayReportTabs-1">單日收據明細</a></li>
							<li><a href="#dayReportTabs-2">平均消費金額/來客數 統計</a></li>
							<li><a href="#dayReportTabs-3">單日餐點 統計</a></li>
						</ul>
						<div id="dayReportTabs-1">
							<c:if test="${not empty bills}">
								<h3>日期 : ${param.date}</h3>
								<h3>
									用餐人數 :
									<c:set var="totalNum" value="0" />
									<c:forEach var="bills" items="${bills}">
										<c:set var="totalNum" value="${totalNum+bills.custNum}" />
									</c:forEach>
									${totalNum} 人
								</h3>
								<h3>
									營收 :
									<c:set var="totalPrice" value="0" />
									<c:forEach var="bills" items="${bills}">
										<c:set var="totalPrice" value="${totalPrice+bills.finPrice}" />
									</c:forEach>
									<fmt:formatNumber type="number" value="${totalPrice}"
										maxFractionDigits="0" />
									元
								</h3>
								<table style="margin: 0 auto">
									<thead>
										<tr>
											<th>帳單編號</th>
											<th>用餐人數</th>
											<th>原始金額</th>
											<th>折扣名稱</th>
											<th>消費金額</th>
											<th>結帳員工</th>
											<th>結帳時間</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="bills" items="${bills}">
											<tr>
												<td id="billId" style="cursor: pointer">${bills.billId}</td>
												<td>${bills.custNum}</td>
												<td>${bills.price}</td>
												<td>${bills.bdyDiscount.name}</td>
												<td>${bills.finPrice}</td>
												<td>${bills.bdyEmp.name}</td>
												<td>${bills.endDate}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
						</div>
						<div id="billOrderDialog" title="帳單明細">
							<p>這裡是帳單明細</p>
						</div>
						<div id="dayReportTabs-2">
							<c:if test="${not empty bills}">
								<div id="dayOperate" style="width: 600px; margin: 0px auto;"></div>
							</c:if>
						</div>
						<div id="dayReportTabs-3">
							<c:if test="${not empty bills}">
								<div id="dayMealsCount">
									<ul>
										<li><a href="#dayMealsCount-1" style="width: 10em">主餐
												統計</a></li>
										<li><a href="#dayMealsCount-2" style="width: 10em">開胃菜
												統計</a></li>
										<li><a href="#dayMealsCount-3" style="width: 10em">湯品
												統計</a></li>
										<li><a href="#dayMealsCount-4" style="width: 10em">飲料
												統計</a></li>
										<li><a href="#dayMealsCount-5" style="width: 10em">甜點
												統計</a></li>
										<li><a href="#dayMealsCount-6" style="width: 10em">沙拉
												統計</a></li>
									</ul>
									<div id="dayMealsCount-1">
										<div id="mainMealCount" style="width: 600px; margin: 0px auto"></div>
									</div>
									<div id="dayMealsCount-2">
										<div id="appetizerCount"
											style="width: 600px; margin: 0px auto"></div>
									</div>
									<div id="dayMealsCount-3">
										<div id="soupCount" style="width: 600px; margin: 0px auto"></div>
									</div>
									<div id="dayMealsCount-4">
										<div id="drinkCount" style="width: 600px; margin: 0px auto"></div>
									</div>
									<div id="dayMealsCount-5">
										<div id="dessertCount" style="width: 600px; margin: 0px auto"></div>
									</div>
									<div id="dayMealsCount-6">
										<div id="saladCount" style="width: 600px; margin: 0px auto"></div>
									</div>
								</div>
							</c:if>
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