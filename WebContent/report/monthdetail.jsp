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
var selectYear = '${param.year}';
var selectMonth = '${param.month}';
var mainId = [];
var mealId = [];
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
<script src="<c:url value="/js/monthdetail.js"/>"></script>
<script src="<c:url value="/js/jquery.dataTables.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables_themeroller.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
<style type="text/css">
table {
	font-size:0.85em;
	text-align: center;
}
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

.DataTables_sort_icon {
	display: none;
}
</style>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>Single Month Report Detail</title>
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

					<!-- START Write -->
					<form action="<c:url value="/report/MonthReportServlet" />" method="get">
						<input type="submit" class="MainBtnColor" value="查詢單月營運狀況">
						<select name="year" id="year">

						</select> 年 <select name="month" id="month">

						</select> 月     
						<span style="color: red">${errorMsgs.dateError}</span>
						<span style="display:inline-block;float:right;margin-top:10px;">
							<a href="<c:url value='/report/reportmenu.jsp'/>">返回報表選單</a>
						</span>
					</form>
					<hr>
					<div id="monthReportTabs">
						<ul>
							<li><a href="#monthReportTabs-1">單月營運報表</a></li>
							<li><a href="#monthReportTabs-2">營收/來客數 統計</a></li>
							<li><a href="#monthReportTabs-3">單月主餐 統計</a></li>
							<li><a href="#monthReportTabs-4">單月餐點 統計</a></li>
						</ul>
						<div id="monthReportTabs-1">
							<c:if test="${not empty bills}">
								<div id="billsHeader">
									${param.year}年${param.month}月報表 | 
									單月來客數 :
									<c:set var="totalNum" value="0" />
									<c:forEach var="bills" items="${bills}">
										<c:set var="totalNum" value="${totalNum+bills.dayTatolCustNum}" />
									</c:forEach>
									${totalNum} 人 | 
									單月營收 : 
									<c:set var="totalPrice" value="0" />
									<c:forEach var="bills" items="${bills}">
										<c:set var="totalPrice" value="${totalPrice+bills.dayTatolFinPrice}" />
									</c:forEach>
									<fmt:formatNumber type="number" value="${totalPrice}" maxFractionDigits="0" /> 元
								</div>
								<table id="bills" style="margin: 0 auto">
									<thead>
										<tr>
											<th>日期</th>
											<th>來客數</th>
											<th>金額</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="bills" items="${bills}">
											<tr>
												<td>${param.year}-${param.month}-${bills.dayInMonth}</td>
												<td>${bills.dayTatolCustNum}</td>
												<td><fmt:formatNumber value="${bills.dayTatolFinPrice}" maxFractionDigits="0"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div id="billsHeader">
								</div>
							</c:if>
						</div>
						<div id="monthReportTabs-2">
							<c:if test="${not empty bills}">
								<div id="monthOperate" style="width: 960px;height: 500px; margin: 0px auto;"></div>
							</c:if>
						</div>
						<div id="monthReportTabs-3">
							<c:if test="${not empty bills}">
								<div id="monthMainsCount">
									<ul>
										<c:forEach var="mainkindNames" items="${mainkindNames}" varStatus="theCount">
											<li><a href="#monthMainsCount-${theCount.index}" style="width: 10em">${mainkindNames.mainkindName}類</a></li>
										</c:forEach>
									</ul>
									<c:forEach var="mainkindNames" items="${mainkindNames}" varStatus="theCount">
									<script type="text/javascript">mainId['${theCount.index}'] = '${theCount.index}';</script>		
										<div id="monthMainsCount-${theCount.index}">
											<div id="mainsCount-${theCount.index}" style="width: 700px;height: 460px; margin: 0px auto"></div>
										</div>
									</c:forEach>
								</div>
							</c:if>
						</div>
						<div id="monthReportTabs-4">
							<c:if test="${not empty bills}">
								<div id="monthMealsCount">
									<ul>
										<c:forEach var="foodkindNames" items="${foodkindNames}" varStatus="theCount">
											<li><a href="#monthMealsCount-${theCount.index}" style="width: 10em">${foodkindNames.foodkindName}類</a></li>
										</c:forEach>
									</ul>
									<c:forEach var="foodkindNames" items="${foodkindNames}" varStatus="theCount">
									<script type="text/javascript">mealId['${theCount.index}'] = '${theCount.index}';</script>		
										<div id="monthMealsCount-${theCount.index}">
											<div id="mealsCount-${theCount.index}" style="width: 700px;height: 460px; margin: 0px auto"></div>
										</div>
									</c:forEach>
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