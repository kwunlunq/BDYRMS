<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script src="<c:url value="/js/header.js"/>"></script>


<div id="LogoLabel">
<img id="bannerLogo" src="<c:url value="/images/BDY_Logo.png"/>">
</div>


<div class="topLabel ui-state-highlight ui-corner-all">
<strong class="strongLabel">餐廳資訊</strong>
<p><span class="topItem">餐聽內人數  </span><span id="custInside" class="topValueint"></span><span>人</span></p>
<p><span class="topItem">未結點餐單 </span><span id="orderNum" class="topValueint"></span><span>張</span></p>
<p><span class="topItem">未出餐點數 </span><span id="odlistNum" class="topValueint"></span><span>份</span></p>
<p><span class="topItem">未使用桌數 </span><span id="tableNotUse" class="topValueint"></span><span>桌</span></p>
</div>

<div class="topLabel ui-state-highlight ui-corner-all" id="incomeDiv">
<strong class="strongLabel">營運狀況</strong>
<p><span class="topItem">目前營收  </span><span class="unit">元</span><span id="todayIncome" class="incomeValue"></span></p>
<p><span class="topItem">翻桌次數  </span><span class="unit">翻</span><span id="todayBillsCount" class="incomeValue"></span></p>
</div>

<div class="topLabel" id="dicsountDiv" class="ui-state-highlight">
<strong class="strongLabel">優惠資訊</strong>
</div>

<div class="topLabel">
</div>


