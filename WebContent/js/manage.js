var xmlHttp = new XMLHttpRequest();
var xmlHttpInit = new XMLHttpRequest();

$(function(){
	$( "#tabs" ).tabs();
});

function fupdate(id){
	var count = 0;
	$('#TRfood'+id+'>td').each(function(){
		if(count < 4){
		var str = $(this).text();
		$(this).html("<input type='text' size='7' value='"+str+"'>");
		}
		else if(count==4){
			option(id);
		}
		else
		{
			$(this).html("<input type='button' name='btn' value='確定' onclick='fconfirm("+id+")'><input type='button' name='btn' value='取消'>");
		}
		count++;
	});
}
function option(id){
	xmlHttpInit.addEventListener("readystatechange",initcallback,true);
	var urlInit = contextPath + "/change";
	xmlHttpInit.open("post",urlInit,true);
	xmlHttpInit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInit.send("act=init&id="+id);
}

function initcallback(){
	if(xmlHttpInit.readyState == 4){
		if(xmlHttpInit.status == 200){
			var data = xmlHttpInit.responseText;
			var fkid = data.split("-")[0]; 
			var fkDatas = data.split("-")[1].split(";"); 
			var newOption = document.createElement("select");
			newOption.setAttribute("name","selectname");
			for(var i=0 ;i<fkDatas.length-1;i++){
				var fkData = fkDatas[i].split(",");
				newOption.innerHTML += "<option value='"+fkData[0]+"'>"+fkData[1]+"</option>";	
			}
			$('#foodk'+fkid).text("");
			$('#foodk'+fkid).append(newOption);
			}			
		}
}

function fconfirm(id){
	alert("id="+id);
	window.location="";
	var fname = document.getElementsByName("fname");
}
