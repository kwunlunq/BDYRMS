var xmlHttpInit = new XMLHttpRequest();



$(function(){	
	if(pags=="1"){
		$( "#tabs" ).tabs( "option", "active", 1);
		pags = 0;
	}
//	else if(pags=="2"){
//		$( "#tabs" ).tabs( "option", "active", 2);
//		pags = 0;
//	}
	
	var objset;	
	 if(booleanSetDetail){
		 objset = {		
					
					autoOpen: true,
					width: 400,
					buttons: [
						{
							text: "確定",
							click: function() {
								insertSet();
								$( this ).dialog( "close" );
							}
						},
						{
							text: "取消",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
					]
				}
	 }else{	 
		 objset = {
				autoOpen: false,
				width: 400,
				buttons: [
					{
						text: "確定",
						click: function() {
							insertSet();
							$( this ).dialog( "close" );
						}
					},
					{
						text: "取消",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]			
	 }
	 }
	
	
	
	$( "#setInsertDialog" ).dialog(objset);
	
	$( "#setDialog-link" ).click(function( event1 ) {
		$( "#setInsertDialog" ).dialog( "open" );
		event1.preventDefault();
	});
	
	
});

function insertSet(){
	var a = document.getElementById("setId");
	var setId = document.getElementById("setId").options[document.getElementById("setId").selectedIndex].value;
	var foodId = document.getElementById("foodId").options[document.getElementById("foodId").selectedIndex].value;
	var setDetailPrice = document.getElementById("setDetailPrice").value;

	window.location.href = contextPath+"/secure/inserSetDetail.action?setId="+setId+"&foodId="+foodId+"&setDetailPrice="+setDetailPrice;
}

function insertSetOption(){
	xmlHttpInit.addEventListener("readystatechange",initcallbackInsertSet,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInit.open("post",urlInit,true);
	xmlHttpInit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInit.send("act=insertSet");
}
function initcallbackInsertSet(){
	if(xmlHttpInit.readyState == 4){
		if(xmlHttpInit.status == 200){ 
			var setData =xmlHttpInit.responseText;
			var setOption1 = document.createElement("select");
			var setOption2 = document.createElement("select");
			setOption1.setAttribute("id", "setId");
			setOption2.setAttribute("id", "foodId");
			var sets = setData.split("-")[0].split(";");
			var foods = setData.split("-")[1].split(";");
			for(var i=0;i<sets.length-1;i++){
				var set = sets[i].split(",");
				setOption1.innerHTML +="<option  value='"+set[0]+"'>"+set[1]+"</option>";
			}
			for(var i=0;i<foods.length-1;i++){
				var food = foods[i].split(",");
				setOption2.innerHTML +="<option  value='"+food[0]+"'>"+food[1]+"</option>";
			}
			$('#insertSetName').append(setOption1);
			$('#insertSetFoodKind').append(setOption2);
			
		}
	 }
}


function setoption(detailid,sid,fkid){
	xmlHttpInit.addEventListener("readystatechange",initcallbackSet,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInit.open("post",urlInit,true);
	xmlHttpInit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInit.send("act=setinit&sid="+sid+"&fkid="+fkid+"&detailid="+detailid);
}
function initcallbackSet(){
	if(xmlHttpInit.readyState == 4){
		if(xmlHttpInit.status == 200){
			var data = xmlHttpInit.responseText;
			var detailId = data.split("-")[0];
			var setId = data.split("-")[1]; 
			var fkId = data.split("-")[2]; 
			var setOptions = data.split("-")[3].split(";");;
			var fkOptions = data.split("-")[4].split(";");;
			var newOption1 = document.createElement("select");
			var newOption2 = document.createElement("select");
			var setname = document.getElementById("sname"+detailId).innerHTML;
			var fkname = document.getElementById("fkname"+detailId).innerHTML;
			newOption1.setAttribute("id","setselectname"+detailId);
			newOption2.setAttribute("id","fkselectname"+detailId);
			for(var i=0 ;i<setOptions.length-1;i++){
				
				if(setId==setOptions[i][0]){
					var setOption = setOptions[i].split(",");
					newOption1.innerHTML += "<option  value='"+setOption[0]+"'>"+setOption[1]+"</option>";	
				
				}				
			}
			for(var i=0 ;i<setOptions.length-1;i++){
				if(setId!=setOptions[i][0]){
					var setOption = setOptions[i].split(",");
					newOption1.innerHTML += "<option  value='"+setOption[0]+"'>"+setOption[1]+"</option>";	
				}
			}
			
		
			for(var j=0 ; j<fkOptions.length-1 ; j++){
				
				if(fkId==fkOptions[j][0]){
				var fkOption = fkOptions[j].split(",");
				newOption2.innerHTML += "<option  value='"+fkOption[0]+"'>"+fkOption[1]+"</option>";
				}
			}
			for(var j=0 ; j<fkOptions.length-1 ; j++){
				
				if(fkId!=fkOptions[j][0]){
				var fkOption = fkOptions[j].split(",");
				newOption2.innerHTML += "<option  value='"+fkOption[0]+"'>"+fkOption[1]+"</option>";
				}
			}
			$('#sname'+detailId).text("");
			$('#sname'+detailId).append(newOption1);
			$('#fkname'+detailId).text("");
			$('#fkname'+detailId).append(newOption2);
			$('#TRdetail'+detailId+'>td:last').html
			("<input class='MainBtnColor' type='button' value='確定' onclick='sconfirm("+detailId+","+setId+","+fkId+")'>" +
					"<input class='MainBtnColor' type='button' value='取消'  onclick='scancel(\""+setname+"\",\""+fkname+"\","+detailId+","+setId+","+fkId+")'>");
			}
		}	
}
function sconfirm(detailId,setId,fkId){
	var setId = document.getElementById("setselectname"+detailId).options[document.getElementById("setselectname"+detailId).selectedIndex].value;
	var fkId = document.getElementById("fkselectname"+detailId).options[document.getElementById("fkselectname"+detailId).selectedIndex].value;
	
	updateSet(detailId,setId,fkId);
}
function updateSet(detailId,setId,fkId){
	window.location.href=contextPath+"/secure/updateset?sdetailId="+detailId+"&ssetId="+setId+"&sfkId="+fkId;
	
}
function scancel(setname,fkname,detailid,setid,fkid){
	document.getElementById("sname"+detailid).innerHTML=setname;
	document.getElementById("fkname"+detailid).innerHTML=fkname;
	$('#TRdetail'+detailid+'>td:last').html
	("<input class='MainBtnColor' type='button' value='修改' onclick='setoption("+detailid+","+setid+","+fkid+
			")'><input class='MainBtnColor' type='button' value='刪除'>")
}

function setdelete(detailid){
	var b=window.confirm("你確定刪除");
	if(b){
		window.location.href=contextPath+"/secure/deleteset?detailid="+detailid;
	}
}





