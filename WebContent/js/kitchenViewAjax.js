$(function() {
		$( "#tabs" ).tabs();
		
		getAllSortData();
			  cala();	 
			  setInterval(cala,1000);
		$(":button").addClass("MainBtnColor");
});
window.onload=function(){
	cala();	 
	setInterval(cala,1000);
};
function getAllSortData(){
	var strUrl = contextPath+"/kitchen/kitchenAllmeal.action";
	$.getJSON(strUrl,function(datas){
		
		var eleTable=document.createElement("table");
			eleTable.setAttribute("border","1");
		var eleThead=document.createElement("thead");
		var eleTbody=document.createElement("tbody");
		var eleTr=document.createElement("tr");	
			var eleTd  = document.createElement("th");
				eleTd.appendChild(document.createTextNode("桌號"));
			var eleTd2 = document.createElement("th");
				eleTd2.appendChild(document.createTextNode("餐點名稱"));
			var eleTd3 = document.createElement("th");
				eleTd3.appendChild(document.createTextNode("點餐時間"));
			var eleTd4 = document.createElement("th");
			 	eleTd4.appendChild(document.createTextNode("出餐時間"));
			var eleTd5 = document.createElement("th");
				eleTd5.appendChild(document.createTextNode("距離時間"));
			var eleTd6 = document.createElement("th");
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
			
			for(var i=0;i<datas[0].allmeall.length;i++){
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
			elehidden.setAttribute("value",datas[0].allmeall[i].出餐點);
			
			
			var nodeTID = document.createTextNode(datas[0].allmeall[i].桌號);	
			var nodeName = document.createTextNode(datas[0].allmeall[i].食物名稱);
			var nodeOrderTime= document.createTextNode(datas[0].allmeall[i].點單時間);
			var nodeOutTime= document.createTextNode(datas[0].allmeall[i].實際出餐時間);
			var eletextButton = document.createElement("input");
				eletextButton.setAttribute("type","button");
				eletextButton.setAttribute("name","outMeal");
				eletextButton.setAttribute("value","出餐");
				eletextButton.setAttribute("onclick","deleteItem("+datas[0].allmeall[i].點單明細編號+",0)");
				eletextButton.setAttribute("class", "MainBtnColor");
				
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
			//-------------------tabs-all-----------------------------------------
			
			for(var k=0;k<datas[1].foodkind.length;k++){
				console.log("foodkind種類ID"+datas[1].foodkind[k].種類編號);//1.2.3.4.5.6
			}
			
			for(var k=0;k<datas[0].allmeall.length;k++){
				console.log("種類ID"+datas[0].allmeall[k].食物種類);//1.2.3
			}
			
//			.add("種類編號",temp.getFkId())
//			.add("種類名稱",temp.getName())
//			.add("製作區域",temp.getBdyMakearea().getName())
			var b = false;//-------------出始為false title
			for(var i=0;i<datas[1].foodkind.length;i++){
				console.log(datas[1].foodkind[i].種類編號+":第1次檢查點");
				for(var j=0;j<datas[0].allmeall.length;j++){					
					if((datas[1].foodkind[i].種類編號)==(datas[0].allmeall[j].食物種類)){//1==1
						console.log(datas[1].foodkind[i].種類編號+":第2次檢查點:"+datas[0].allmeall[j].食物種類+":BBB="+b);
						if(b==false){//-----第一次畫表
							console.log("第一次該進來");
							var eleTable=document.createElement("table");//---table
								eleTable.setAttribute("border","1");
								eleTable.setAttribute("id","table"+datas[1].foodkind[i].種類編號);//--table Id
							var eleThead=document.createElement("thead");
							var eleTbody=document.createElement("tbody");
							var eleTr=document.createElement("tr");	
							var eleTd  = document.createElement("th");
								eleTd.appendChild(document.createTextNode("桌號"));
							var eleTd2 = document.createElement("th");
								eleTd2.appendChild(document.createTextNode("餐點名稱"));
							var eleTd3 = document.createElement("th");
								eleTd3.appendChild(document.createTextNode("點餐時間"));
							var eleTd4 = document.createElement("th");
							 	eleTd4.appendChild(document.createTextNode("出餐時間"));
							var eleTd5 = document.createElement("th");
								eleTd5.appendChild(document.createTextNode("距離時間"));
							var eleTd6 = document.createElement("th");
								eleTd6.appendChild(document.createTextNode("確定出餐"));
								eleTr.appendChild(eleTd);
								eleTr.appendChild(eleTd2);
								eleTr.appendChild(eleTd3);
								eleTr.appendChild(eleTd4);
								eleTr.appendChild(eleTd5);
								eleTr.appendChild(eleTd6);
								eleTable.appendChild(eleTr);
							var itemDiv=document.getElementById("tabs-"+datas[1].foodkind[i].種類編號);
							itemDiv.appendChild(eleTable);//------ 接上div
							b=true;
						}
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
						elehidden.setAttribute("value",datas[0].allmeall[j].出餐點);
						
						
						var nodeTID = document.createTextNode(datas[0].allmeall[j].桌號);	
						var nodeName = document.createTextNode(datas[0].allmeall[j].食物名稱);
						var nodeOrderTime= document.createTextNode(datas[0].allmeall[j].點單時間);
						var nodeOutTime= document.createTextNode(datas[0].allmeall[j].實際出餐時間);
						var eletextButton = document.createElement("input");
							eletextButton.setAttribute("type","button");
							eletextButton.setAttribute("name","outMeal");
							eletextButton.setAttribute("value","出餐");
							eletextButton.setAttribute("onclick","deleteItem("+datas[0].allmeall[j].點單明細編號+",0)");
							eletextButton.setAttribute("class", "MainBtnColor");
							
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
							
							document.getElementById("table"+datas[1].foodkind[i].種類編號).appendChild(eleTr);
					}else{
						if(j==((datas[0].allmeall.length)-1)){
							break;
						}
						console.log("第3次檢查點BBBB==="+b+":FKID="+datas[1].foodkind[i].種類編號+":OrderListFoodKind="+datas[0].allmeall[j].食物種類);
						continue;
					}
				}
				b=false;
			}
		
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
function deleteItem(id,page){
	console.log(id + "----" +page);
	var b=window.confirm("你確定出餐?");
	if(b){
		window.location=contextPath+"/kitchen/outMeal.action?id="+id+"&page="+page;
	}
}

function ckeckOrder(){
	$("#tabs-all").empty();
	$("#tabs-1").empty();
	$("#tabs-2").empty();
	$("#tabs-3").empty();
	$("#tabs-4").empty();
	$("#tabs-5").empty();
	$("#tabs-6").empty();
	getAllSortData();
}

function allOrderout(){
	var b=window.confirm("你確定全部出餐?");
	if(b){
		window.location=contextPath+"/kitchen/outallmeal.action";
	}
}