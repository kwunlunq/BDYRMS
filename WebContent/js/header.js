$(function() {
	updateInfo();
	updateDiscount();
});

function updateInfo() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"table"}, function(result) {
		var sizeSum = 0;
		var tableCapacity = 0;
		var tableInUse = 0;
		var tableNotUse = 0;
		for (var i = 0; i < result.length; i++) {
			var tbs = result[i].tables;
			for (var j = 0; j < tbs.length; j++) {
				sizeSum += tbs[j].tbSize;
				tableCapacity ++;
				if (tbs[j].tbState == 0) {
					tableNotUse ++;
				} else {
					tableInUse ++;
				}
			}
		}
//		$('#custCapacity').text(sizeSum);
//		$('#tableCapacity').text(tableCapacity);
		$('#tableNotUse').text(tableNotUse);
//		$('#tableInUse').text(tableInUse);
	});

	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"orderNotCheckAndCustNum"}, function(result) {
		$('#custInside').text(result.custNum);
		$('#orderNum').text(result.orderNum);
		$('#odlistNum').text(result.odlistNum);
	});
	
}

function updateDiscount() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"discount"}, function(result) {
		for (var i = 0; i < result.length; i++) {
			var disTitle = $('<p><span class="topItem">可用活動'+(i+1)+':</span><span class="topValue">');
			var disContent = document.createElement("span");
			$(disContent).attr("class", "topValue");
			$(disContent).append(document.createTextNode(result[i].disName));
			
			$(disTitle).append(disContent);
			$('#dicsountDiv').append(disTitle);
			$('#dicsountDiv').addClass("ui-state-highlight");
		}
	});
}











