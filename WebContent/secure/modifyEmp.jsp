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
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/emp.js"/>"></script>
<script src="<c:url value="/js/jquery.dataTables.min.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RMS - Welcome</title>
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
	<a href="<c:url value="/secure/priorEmp.action" />">新增新進員工</a>
		<a href="<c:url value="/secure/showEmp.action?resign=0" />">查詢在職員工</a>
		<a href="<c:url value="/secure/showEmp.action?resign=1" />">查詢離職員工</a>
	 <s:property value="%{fieldErrors['emp.empId']}"/>
	<s:iterator var="emp" value="emps" status="headcheck" >	
	<s:if test="%{#headcheck.first}">
	<table id="empTable" >		
		<thead>
			<tr>
				<td>員工ID</td>
				<td>員工姓名</td>
				<td>員工性別</td>
				<td>員工職位</td>
				<td>到職日期</td>
<!-- 				<td>年資</td> -->
				<td>薪資</td>
				<td>電話</td>
				<td>住址</td>
				<td>離職</td>
				<td>修改</td>
			</tr>
		</thead>
		<tbody>
		</s:if>
			<form id="modifyEmpForm<s:property value='#headcheck.count'/>" action="<c:url value="/secure/updateEmp.action" />" method="post">
			<tr id="trEmp<s:property value="#headcheck.count"/>">		
				<td><s:property value="#emp.empId" /></td>
				<td><s:property value="#emp.name" /></td>
				<td><s:property value="#emp.sex" /></td>
				<td><s:property value="#emp.bdyPriority.jobname" /></td>
				<td><s:date name="#emp.comedate" format="yyyy-MM-dd" /></td>
<%-- 				<td><s:date name="#emp.comedate" format="yyyy-MM-dd" nice="true" /></td> --%>
				<td><s:property value="#emp.salary" /></td>
				<td><s:property value="#emp.phone" /></td>
				<td><s:property value="#emp.empAddress" /></td>
				<td><s:if test="%{#emp.resign==0}">
					在職
					</s:if>
					<s:if test="%{#emp.resign==1}">
					離職
					</s:if>
				</td>
				<td><input type="hidden" name="empId" value="<s:property value='#emp.empId' />"><input class="MainBtnColor" type="button" id="update<s:property value='#headcheck.count'/>" name="update<s:property value='#headcheck.count'/>" value="修改" onclick="empUpdate(<s:property value='#headcheck.count'/>)" /></td>							
			</tr>
			</form>									
		</s:iterator>
		</tbody>
		</table>
		<s:property value="%{fieldErrors['emp.empId'][0]}"/>
		<s:property value="%{fieldErrors['emp.name'][0]}"/>
		<s:property value="%{fieldErrors['emp.comedate'][0]}"/>
		<s:property value="%{fieldErrors['emp.phone'][0]}"/>
		<s:property value="%{fieldErrors['emp.empAddress'][0]}"/>
		
	</div>
		<s:select
		value="pri" 
		name="mySelect"
		listKey="%{priId}"
		listValue="%{jobname}"
		 list="prior"  

		 />

</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>