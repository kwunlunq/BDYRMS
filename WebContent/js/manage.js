var xmlHttp = new XMLHttpRequest();
var xmlHttpInit = new XMLHttpRequest();

$(function(){
	$( "#tabs" ).tabs();
});
var fdid;
function fupdate(fdid){
	this.fdid= fdid;
	var count = 0;
	$('#TRfood'+fdid+'>td').each(function(){
		if(count < 4){
		var str = $(this).text();
		$(this).html("<input type='text' size='7' value='"+str+"'>");
		}
		else if(count==4){
			option(fdid);
		}
		else
		{
//			alert("是否近來?");
//			var fname = document.getElementById("fname"+fdid).value;
//			var fprice = document.getElementById("fprice"+fdid).value;
//			var fqty = document.getElementById("fqty"+fdid).value;
//			var fdesc = document.getElementById("fdesc"+fdid).value;
//			var ffkind = document.getElementById("ffkind"+fdid).value;
			$(this).html("<input type='submit' name='btn' value='確定' onclick='fconfirm("+fdid+")'><input type='button' name='btn' value='取消'>");
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
			newOption.setAttribute("id","selectname"+fdid);
			for(var i=0 ;i<fkDatas.length-1;i++){
				var fkData = fkDatas[i].split(",");
				newOption.innerHTML += "<option name='fkid' value='"+fkData[0]+"'>"+fkData[1]+"</option>";	
			}
			$('#foodk'+fkid).text("");
			$('#foodk'+fkid).append(newOption);
			}			
		}
}

function fconfirm(fdid){
//	alert("fconfirm function");
	var fname = document.getElementById("fname"+fdid).firstChild.value;
	var fprice = document.getElementById("fprice"+fdid).firstChild.value;
	var fqty = document.getElementById("fqty"+fdid).firstChild.value;
	var fdesc = document.getElementById("fdesc"+fdid).firstChild.value;
	var ffkind = document.getElementById("selectname"+fdid).options[document.getElementById("selectname"+fdid).selectedIndex].value;
	updateFood(fdid,fname,fprice,fqty,fdesc,ffkind);
}

function updateFood(fdid,fname,fprice,fqty,fdesc,ffkind){
//	alert("updateFood function");
//	alert("ffkind="+ffkind);
	window.location.href=contextPath+"/secure/update?fdid="+fdid+"&fname="+fname+"&fprice="+fprice+"&fqty="+fqty+"&fdesc="+fdesc+"&ffkind="+ffkind;
}
