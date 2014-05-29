var xmlHttpInitFoodKind = new XMLHttpRequest();

$(function(){
	 if(pags=="2"){
		$( "#tabs" ).tabs( "option", "active", 2);
		pags = 0;
	}
	 var objfk;
	 if(booleanFoodKind){
		 objfk = {		
				 modal: true,
					autoOpen: true,
					width: 400,
					buttons: [
						{
							text: "確定",
							click: function() {
								insertFoodKind();
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
		 objfk = {
				 modal: true,
				autoOpen: false,
				width: 400,
				buttons: [
					{
						text: "確定",
						click: function() {
							insertFoodKind();
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
	 $( "#foodKindInsertDialog" ).dialog(objfk);
	 
	 $( "#foodKindDialog-link" ).click(function( event2 ) {
			$( "#foodKindInsertDialog" ).dialog( "open" );
			event2.preventDefault();
		});
})

function insertFoodKind(){
	var name = document.getElementById("insertFoodKindName").value;
	var period = document.getElementById("insertFoodKindPeriod").value;
	var ma = document.getElementById("insertFoodKindMa").options[document.getElementById("insertFoodKindMa").selectedIndex].value;;
	var seq = document.getElementById("insertFoodKindSEQ").value;
	window.location.href = contextPath+"/secure/inserFoodKind.action?name="+name+"&period="
	+period+"&ma="+ma+"&seq="+seq;
}

function insertFoodKindOption(){
	xmlHttpInitFoodKind.addEventListener("readystatechange",initcallbackInsertFoodKind,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitFoodKind.open("post",urlInit,true);
	xmlHttpInitFoodKind.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitFoodKind.send("act=insertFoodKind");
	
}

function initcallbackInsertFoodKind(){
	if(xmlHttpInitFoodKind.readyState == 4){
		if(xmlHttpInitFoodKind.status == 200){
			var data = xmlHttpInitFoodKind.responseText;
			var mas = data.split(";");
			var fkSelect = document.createElement("select");
			fkSelect.setAttribute("id","insertFoodKindMa");
			for(var i=0;i<mas.length-1;i++){
				var ma = mas[i].split(",");
				fkSelect.innerHTML += "<option value='"+ma[0]+"'>"+ma[1]+"</option>";
			}
			$('#insertSetFoodKindMa').append(fkSelect);
		}
	}
}

function fkupdate(fkId,maId){
	var count = 0;
	var fkname = document.getElementById("foodkindname"+fkId).innerHTML;
	var fkperiod = document.getElementById("foodkindperiod"+fkId).innerHTML;
	var fkma = document.getElementById("maId"+fkId).innerHTML;
	var fkseq = document.getElementById("foodkindseq"+fkId).innerHTML;
	$('#TRfk'+fkId+'>td').each(function(){
		if(count==2){
			foodkindOption(fkId,maId);
		}
		else if(count<4){
		var text = $(this).text();
		$(this).html("<input type='text' size='8' value='"+text+"'>");
		}
		else{
			$(this).html("<input class='MainBtnColor' type='button' value='確認' onclick='fkconfirm("+fkId+","+maId+")'>" +
					"<input class='MainBtnColor' type='button' value='取消' onclick='fkcancel(\""+fkname+"\","+fkperiod+",\""+fkma
					+"\","+fkseq+","+fkId+","+maId+")'>");
		}
		count++;
	});
}
function fkcancel(fkname,fkperiod,fkma,fkseq,fkId,maId){
	document.getElementById("foodkindname"+fkId).innerHTML=fkname;
	document.getElementById("foodkindperiod"+fkId).innerHTML=fkperiod;
	document.getElementById("maId"+fkId).innerHTML=fkma;
	document.getElementById("foodkindseq"+fkId).innerHTML=fkseq;
	$('#TRfk'+fkId+'>td:last').html
	("<input class='MainBtnColor' type='button' value='修改' onclick='fkupdate("+fkId+","+maId+
			")'><input class='MainBtnColor' type='button' value='刪除' onclick='fdeleteFood("+fkId+")'>");
}
function fkconfirm(fkId){
	var fkname = document.getElementById("foodkindname"+fkId).firstChild.value;
	var fkperiod = document.getElementById("foodkindperiod"+fkId).firstChild.value;
	var fkma = document.getElementById("selectmaId"+fkId).options[document.getElementById("selectmaId"+fkId).selectedIndex].value;
	var fkseq = document.getElementById("foodkindseq"+fkId).firstChild.value;
	updatefk(fkId,fkname,fkperiod,fkma,fkseq);
}
function updatefk(fkId,fkname,fkperiod,fkma,fkseq){
	window.location.href=contextPath+"/secure/updatefoodkind?fkId="+fkId+"&fkname="
	+fkname+"&fkperiod="+fkperiod+"&fkma="+fkma+"&fkseq="+fkseq;
}

function foodkindOption(fkId,maId){
	xmlHttpInitFoodKind.addEventListener("readystatechange",initcallbackUpdateFoodKind,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitFoodKind.open("post",urlInit,true);
	xmlHttpInitFoodKind.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitFoodKind.send("act=foodKindInit&fkId="+fkId+"&maId="+maId);
}

function initcallbackUpdateFoodKind(){
	if(xmlHttpInitFoodKind.readyState == 4){
		if(xmlHttpInitFoodKind.status == 200){
			var data = xmlHttpInitFoodKind.responseText;
			var fkId = data.split("-")[0];
			var maId = data.split("-")[1];
			var temps = data.split("-")[2].split(";");
			var maOption = document.createElement("select");
			maOption.setAttribute("id", "selectmaId"+fkId);
			for(var i=0;i<temps.length-1;i++){
				if(maId==temps[i][0]){
				var temp = temps[i].split(",");
				maOption.innerHTML += "<option value='"+temp[0]+"'>"+temp[1]+"</option>";
				}
			}
			for(var i=0;i<temps.length-1;i++){
				if(maId!=temps[i][0]){
				var temp = temps[i].split(",");
				maOption.innerHTML += "<option value='"+temp[0]+"'>"+temp[1]+"</option>";
				}
			}
			$('#maId'+fkId).text("");
			$('#maId'+fkId).append(maOption);
		}
	}
}
function fkdelete(fkId){
	var b = window.confirm("確定刪除");
	if(b){
		window.location.href = contextPath+"/secure/deletefoodkind?fkId="+fkId;
	}
}







