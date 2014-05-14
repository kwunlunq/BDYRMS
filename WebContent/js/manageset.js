var xmlHttpInit = new XMLHttpRequest();
$(function(){
	if(pags=="1"){
		$( "#tabs" ).tabs( "option", "active", 1);
		pags = 0;
	}
});

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
				
				if(i==(setId-1)){
					var setOption = setOptions[i].split(",");
					newOption1.innerHTML += "<option  value='"+setOption[0]+"'>"+setOption[1]+"</option>";	
				
				}				
			}
			for(var i=0 ;i<setOptions.length-1;i++){
				if(i!=(setId-1)){
					var setOption = setOptions[i].split(",");
					newOption1.innerHTML += "<option  value='"+setOption[0]+"'>"+setOption[1]+"</option>";	
				}
			}
			
			for(var j=0 ; j<fkOptions.length-1 ; j++){
				if(j==(fkId-1)){
				var fkOption = fkOptions[j].split(",");
				newOption2.innerHTML += "<option  value='"+fkOption[0]+"'>"+fkOption[1]+"</option>";
				}
			}
			for(var j=0 ; j<fkOptions.length-1 ; j++){
				if(j!=(fkId-1)){
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
	alert(setname+"---"+fkname+"---"+detailid)
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





