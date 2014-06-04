$(function() {
	updateDiscount();
	updateInfo(true);
	updateIncome();
	billsCount();
	setInterval(function(){
		billsCount();
		updateInfo(false);
		updateIncome();
	}, 10000);
});

function billsCount(){
	var url = contextPath+"/header/getHeaderDataServlet";
	$.getJSON(url, {"data":"bills"}, function(result) {
		$('#todayBillsCount').text(result);
	});
}

function updateInfo(firstTime) {
	var url = contextPath+"/header/getHeaderDataServlet?data=";
	var param = "updateTable";
	if (firstTime) {
		param = "table";
	}
	$.getJSON(url+param, function(result) {
		var sizeSum = 0;
		var tableCapacity = 0;
		var tableInUse = 0;
		var tableNotUse = 0;
		var custNum = 0;
		for (var i = 0; i < result.length; i++) {
			var tbs = result[i].tables;
			for (var j = 0; j < tbs.length; j++) {
				sizeSum += tbs[j].tbSize;
				tableCapacity ++;
				custNum += tbs[j].custNum;
				if (tbs[j].tbState == 0) {
					tableNotUse ++;
				} else {
					tableInUse ++;
				}
			}
		}
		// 餐廳內人數
		$('#custInside').text(custNum);
		// 未使用桌數
		$('#tableNotUse').text(tableNotUse);
	});

	var url = contextPath+"/header/getHeaderDataServlet";
	$.getJSON(url, {"data":"orderNotCheckAndCustNum"}, function(result) {
		// 未結點餐單
		$('#orderNum').text(result.orderNum);
		// 未出餐點數
		$('#odlistNum').text(result.odlistNum);
	});
	
}

function updateIncome() {
	var url = contextPath+"/header/getHeaderDataServlet";
	$.getJSON(url, {"data":"todayIncome"}, function(result) {
//		console.log(result);
			$('#todayIncome').text(result);
});
	
	
	
}
function updateDiscount() {
	var url = contextPath+"/header/getHeaderDataServlet";
	$('#dicsountDiv').empty();
	$('#dicsountDiv').append('<strong class="strongLabel">優惠資訊</strong>');
	$.getJSON(url, {"data":"updateDiscount"}, function(result) {
		for (var i = 0; i < result.length; i++) {
			var disTitle = $('<p><span class="disName">'+result[i].disName+'</span>');
			var disContent = document.createElement("span");
			$(disContent).attr("class", "disValue");
//			alert("")
			$(disContent).append(document.createTextNode(parseFloat(result[i].disPrice)*10));
			
			$(disTitle).append(disContent);
			$(disTitle).append('<span>折</span>');
			$('#dicsountDiv').append(disTitle);
			$('#dicsountDiv').addClass("ui-state-highlight");
		}
	});
}











