$(function() {
		$( "#tabs" ).tabs();
		getAllSortData();
			  cala();	 
			  setInterval(cala,1000);
		
});

function getAllSortData(){
	var strUrl = contextPath+"/kitchen/kitchenAllmeal.action";
	$.getJSON(strUrl,{},function(datas){
		var eleTable=document.createElement("table");
			eleTable.setAttribute("border","1");
		var eleThead=document.createElement("thead");
		var eleTbody=document.createElement("tbody");
		var eleTr=document.createElement("tr");
		
		
			var eleTd  = document.createElement("td");
				eleTd.appendChild(document.createTextNode("桌號"));
			var eleTd2 = document.createElement("td");
				eleTd2.appendChild(document.createTextNode("餐點名稱"));
			var eleTd3 = document.createElement("td");
				eleTd3.appendChild(document.createTextNode("點餐時間"));
			var eleTd4 = document.createElement("td");
			 	eleTd4.appendChild(document.createTextNode("出餐時間"));
			var eleTd5 = document.createElement("td");
				eleTd5.appendChild(document.createTextNode("距離時間"));
			var eleTd6 = document.createElement("td");
				eleTd6.appendChild(document.createTextNode("確定出餐"));
			
				eleTr.appendChild(eleTd);
				eleTr.appendChild(eleTd2);
				eleTr.appendChild(eleTd3);
				eleTr.appendChild(eleTd4);
				eleTr.appendChild(eleTd5);
				eleTr.appendChild(eleTd6);
//			.add("桌號",notOutMealitem.getTableID())
//            .add("點單明細編號",notOutMealitem.getOrderlistID())
//            .add("食物名稱", notOutMealitem.getOrderlistname())
//            .add("點單時間", notOutMealitem.getOrderDate().toString())                 
//            .add("實際出餐時間",sdf.format(notOutMealitem.getOutMealTime()))
//            .add("食物種類",notOutMealitem.getFoodkindID())
//            .add("出餐點", notOutMealitem.getOutMealTime().getTime())
			eleThead.appendChild(eleTr);
			eleTable.appendChild(eleThead);
			
			for(var i=0;i<datas.length;i++){
			var eleTr=document.createElement("tr");
			var eleTID=document.createElement("td");
			var eleName=document.createElement("td");
			var eleOrderTime=document.createElement("td");
			var eleOutTime=document.createElement("td");
			var eleDistime=document.createElement("td");
			 eleDistime.setAttribute("name","result");
			var eleButton=document.createElement("td");
			
			var elehidden=document.createElement("input");
			elehidden.setAttribute("type","hidden");
			elehidden.setAttribute("name","calc");
			elehidden.setAttribute("value",datas[i].出餐點);
			
			
			var nodeTID = document.createTextNode(datas[i].桌號);	
			var nodeName = document.createTextNode(datas[i].食物名稱);
			var nodeOrderTime= document.createTextNode(datas[i].點單時間);
			var nodeOutTime= document.createTextNode(datas[i].實際出餐時間);
			var eletextButton = document.createElement("input");
				eletextButton.setAttribute("type","button");
				eletextButton.setAttribute("value","出餐");
				
				eleTID.appendChild(nodeTID);
				eleTID.appendChild(elehidden);
				eleName.appendChild(nodeName);
				eleOrderTime.appendChild(nodeOrderTime);
				eleOutTime.appendChild(nodeOutTime);
				eleButton.appendChild(eletextButton);
				
				eleTr.appendChild(eleTID);
				eleTr.appendChild(eleName);
				eleTr.appendChild(eleOrderTime);
				eleTr.appendChild(eleOutTime);
				eleTr.appendChild(eleDistime);
				eleTr.appendChild(eleButton);	
				eleTbody.appendChild(eleTr);
			}
			eleTable.appendChild(eleTbody);
			document.getElementById("tabs-all").appendChild(eleTable);
		
	});
}

function cala(){
    var results=document.getElementsByName("result");
    var objects=document.getElementsByName("calc");
    for(var i=0;i<objects.length;i++){
    	var now = new Date();
    	var nowMill = now.getTime();
    	var judge = nowMill-objects[i].value;
    	if(judge<0){
    		var tempD = Math.floor(Math.abs(judge/(1000*60*60*24)));//---------------天
    		if(tempD==0){
    			tempD="";
    		}else{
    			tempD = Math.floor(Math.abs(judge/(1000*60*60*24)))+"天";
    		}
    		var tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24;//------------------小時
    		if(tempH==0){
    			tempH=="";
    		}else{
    			tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24+"小時";
    		}
    		var tempM = Math.floor(Math.abs(judge/(1000*60)))%60;//-------------------分鐘
    		if(tempM==0){
    			tempM="";
    		}else{
    			tempM = Math.floor(Math.abs(judge/(1000*60)))%60+"分鐘";
    		}
    		var tempS = Math.floor(Math.abs(judge/1000))%60+"秒";//-----------------------秒
    		results[i].innerHTML="距離"+tempD+tempH+tempM+tempS;
    	}else{
    		var tempD = Math.floor(Math.abs(judge/(1000*60*60*24)));//---------------天
    		if(tempD==0){
    			tempD="";
    		}else{
    			tempD = Math.floor(Math.abs(judge/(1000*60*60*24)))+"天";
    		}
    		var tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24;//------------------小時
    		if(tempH==0){
    			tempH=="";
    		}else{
    			tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24+"小時";
    		}
    		var tempM = Math.floor(Math.abs(judge/(1000*60)))%60;//-------------------分鐘
    		if(tempM==0){
    			tempM="";
    		}else{
    			tempM = Math.floor(Math.abs(judge/(1000*60)))%60+"分鐘";
    		}
    		var tempS = Math.floor(Math.abs(judge/1000))%60+"秒";//-----------------------秒
    		results[i].innerHTML="<span style='color:red'>Delay<span>" +tempD+ tempH+ tempM+tempS;
    	}
    }
    }