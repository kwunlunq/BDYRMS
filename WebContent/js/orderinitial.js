
	var url = contextPath+"/order/getOrderDataServlet";
	
	$.getJSON(url, {"data":"food"});
	
	$.getJSON(url, {"data":"table"});
	
	$.getJSON(url, {"data":"set"});
	
	$.getJSON(url, {"data":"fk"});
	

	$.getJSON(url, {"data":"sessionFood"});
	$.getJSON(url, {"data":"sessionSet"});
	$.getJSON(url, {"data":"sessionEmp", "empId":empId});
	$.getJSON(url, {"data":"sessionTable"});