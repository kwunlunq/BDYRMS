$(function() {
	updateInfo();
	updateDiscount();
	updateIncome();
});

function updateInfo() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"updateTable"}, function(result) {
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
		$('#tableNotUse').text(tableNotUse);
		$('#custInside').text(custNum);
	});

	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"orderNotCheckAndCustNum"}, function(result) {
		
		$('#orderNum').text(result.orderNum);
		$('#odlistNum').text(result.odlistNum);
	});
	
}

function updateIncome() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"todayIncome"}, function(result) {
		console.log(result);
			$('#todayIncome').text(result);
});
	
	
	
}
function updateDiscount() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"discount"}, function(result) {
		for (var i = 0; i < result.length; i++) {
			var disTitle = $('<p><span class="disName">'+result[i].disName+'</span>');
			var disContent = document.createElement("span");
			$(disContent).attr("class", "disValue");
			$(disContent).append(document.createTextNode(result[i].disPrice));
			
			$(disTitle).append(disContent);
			$(disTitle).append('<span>折</span>');
			$('#dicsountDiv').append(disTitle);
			$('#dicsountDiv').addClass("ui-state-highlight");
		}
	});
}











