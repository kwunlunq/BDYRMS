var xmlHttpInit = new XMLHttpRequest();




$(function(){
	
	$( "#tabs" ).tabs();


	$( "#foodInsertDialog" ).dialog({
				autoOpen: false,
				width: 400,
				buttons: [
					{
						text: "確定",
						click: function() {
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
			});
	
	$( "#foodDialog-link" ).click(function( event ) {
		$( "#foodInsertDialog" ).dialog( "open" );
		event.preventDefault();
	});


});


function fcancel(fdid,fname,fprice,fqty,fdesc,ffkind) {
	document.getElementById("fname"+fdid).innerHTML=fname;
	document.getElementById("fprice"+fdid).innerHTML=fprice;
	document.getElementById("fqty"+fdid).innerHTML=fqty;
	document.getElementById("fdesc"+fdid).innerHTML=fdesc;
	document.getElementById("ffkind"+fdid).innerHTML="<div id='foodk"+fdid+"'>"+ffkind+"</div>";
	document.getElementById("foodbtn"+fdid).innerHTML=
		"<input class='MainBtnColor' type='button' id='foodupdate'  value='修改' onclick='fupdate("+fdid+
		")'><input class='MainBtnColor' type='button' id='fooddelete'  value='刪除' onclick='fdeleteFood("+fdid+")'>";
	
	
};
function fupdate(fdid){
	this.fdid= fdid;
	var count = 0;
	var fname = document.getElementById("fname"+fdid).innerHTML;
	var fprice = document.getElementById("fprice"+fdid).innerHTML;
	var fqty = document.getElementById("fqty"+fdid).innerHTML;
	var fdesc = document.getElementById("fdesc"+fdid).innerHTML;
	var ffkind = document.getElementById("ffkind"+fdid).firstChild.innerHTML;
	$('#TRfood'+fdid+'>td').each(function(){
	
		if(count < 4){
		var str = $(this).text();
		$(this).html("<input type='text' size='7' value='"+str+"'>");
		}
		else if(count==4){
			foodoption(fdid);
		}
		else
		{
			$(this).html("<input class='MainBtnColor' type='button'  value='確定' onclick='fconfirm("+fdid+")'>" +
					" <input class='MainBtnColor' type='button'  value='取消' onclick='fcancel("+fdid+",\""+fname+"\","+fprice+","+fqty+",\""+fdesc+"\",\""+ffkind+"\")'>");
		}
		count++;
	});
}
function fdeleteFood(fdid){
	var b=window.confirm("你確定刪除");
	if(b){
		window.location.href=contextPath+"/secure/deletefood?fid="+fdid;
	}
}
function foodoption(id){
	xmlHttpInit.addEventListener("readystatechange",initcallbackFood,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInit.open("post",urlInit,true);
	xmlHttpInit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInit.send("act=foodinit&id="+id);
}

function initcallbackFood(){
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
	window.location.href=contextPath+"/secure/updatefood?fdid="+fdid+"&fname="+fname+"&fprice="+fprice+"&fqty="+fqty+"&fdesc="+fdesc+"&ffkind="+ffkind;
	//showState("修改完成");
}
