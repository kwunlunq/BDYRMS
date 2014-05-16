var xmlHttpInitFoodKind = new XMLHttpRequest();


function fkupdate(fkId,maId){
	var count = 0;
	$('#TRfk'+fkId+'>td').each(function(){
		if(count==2){
			foodkindOption(fkId,maId);
		}
		else if(count<4){
		var text = $(this).text();
		$(this).html("<input type='text' size='8' value='"+text+"'>");
		}
		count++;
	});
}

function foodkindOption(fkId,maId){
	xmlHttpInitFoodKind.addEventListener("readystatechange",initcallbackUpdateFoodKind,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitFoodKind.open("post",urlInit,true);
	xmlHttpInitFoodKind.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitFoodKind.send("act=insertFoodKind&fkId="+fkId+"&maId="+maId);
}

function initcallbackUpdateFoodKind(){
	if(xmlHttpInitFoodKind.readyState == 4){
		if(xmlHttpInitFoodKind.status == 200){
			var data = xmlHttpInitFoodKind.responseText;
			var fkId = data.split("-")[0];
			var maId = data.split("-")[1];
			var temps = data.split("-")[2].split(";");
			var maOption = document.createElement("select");
			maOption.setAttribute("id", "maId");
			for(var i=0;i<temps.length-1;i++){
				var temp = temps[i].split(",");
				maOption.innerHTML += "<option value='"+temp[0]+"'>"+temp[1]+"</option>";
			}
			alert(temps)
			$('#maId'+fkId).text("");
			$('#maId'+fkId).append(maOption);
		}
	}
}








