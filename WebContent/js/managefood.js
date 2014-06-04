var xmlHttpInitFood = new XMLHttpRequest();
var xmlHttpInitFK = new XMLHttpRequest();
var xmlHttpInitMK = new XMLHttpRequest();
var xmlHttpInitInsertMK = new XMLHttpRequest();

$(function(){
	setTableToMaxStyle("sortTable1");
	var orders = order.split(",");
	if(del=="1"){
		showState("刪除成功");
	}
	insertOption();
	insertSetOption();
	insertFoodKindOption();
	//insertOptionMK();
//	$('#sortTable1').DataTable({
//	    "jQueryUI": true,
//	    "scrollY": ($('#aside').height()-275),
//	    "scrollCollapse": true,
//	    "paging": false,
//	    "aoColumnDefs":[{
//	    	aTargets:[1,2,3,4,5,6],
//	    	bSortable:false
//	    }],
//	});
	$('#sortTable2').DataTable({
    "jQueryUI": true,
    "scrollY": ($('#aside').height()-275),
    "scrollCollapse": true,
    "paging": false,
    "aoColumnDefs":[{
    	bSortable:false
    }],
});
	$('#sortTable3').DataTable({
	    "jQueryUI": true,
	    "scrollY": ($('#aside').height()-275),
	    "scrollCollapse": true,
	    "paging": false,
	    "aoColumnDefs":[{
	    	bSortable:false
	    }],
	});
	
	var obj;
		
	$( "#tabs" ).tabs({
		heightStyle: "fill"
	});
	if(booleanFood){
		obj = {		
				modal: true,
				autoOpen: true,
				width: 400,
				buttons: [
					{
						text: "確定",
						click: function() {
							insertFood();
							$( this ).dialog( "close" );
						}
					},
					{
						text: "取消",
						click: function() {
							cancelInsertFoodOption()
							$( this ).dialog( "close" );
						}
					}
				]
			}
	}else{
	 obj = {
			
			modal: true,
			autoOpen: false,
			width: 400,
			buttons: [
				{
					text: "確定",
					click: function() {
						insertFood();
						$( this ).dialog( "close" );
					}
				},
				{
					text: "取消",
					click: function() {
						cancelInsertFoodOption()
						$( this ).dialog( "close" );
					}
				}
			]
		}
	}
	$( "#foodInsertDialog" ).dialog(obj);
	
	$( "#foodDialog-link" ).click(function( event ) {
		$( "#foodInsertDialog" ).dialog( "open" );
		event.preventDefault();
	});
//	[id^=selectname]
//	$( "select" ).change(function () {
//	   alert("run this")
//	    var mkSelectid = $( "select option:selected" )[0].text();
//	    alert(mkSelectid);
//	  });
	
//	$('div[id^=foodk').on('change', function (e) {
//	    var optionSelected = $("option:selected", this).text();
//	    var a = $(this).prepend().val();
//	    alert(optionSelected);
//	    if(true){
//	    	foodmkOption(fdId,mkId);
//	    }
//	});
	
	

});
function cancelInsertFoodOption(){
	
	document.getElementById("insertFoodName").value="";
	document.getElementById("insertFoodPrice").value="";
	document.getElementById("insertFoodQTY").value="";
	document.getElementById("insertFoodDiscript").value="";
}

function insertOptionMK(){
	
	xmlHttpInitInsertMK.addEventListener("readystatechange",initcallbackInsertMK,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitInsertMK.open("post",urlInit,true);
	xmlHttpInitInsertMK.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitInsertMK.send("act=insertMK");
}


function initcallbackInsertMK(){
	if(xmlHttpInitInsertMK.readyState == 4){
		if(xmlHttpInitInsertMK.status == 200){
			var data = xmlHttpInitInsertMK.responseText;
			var temps = data.split(";");
			var insertOptionMK = document.createElement("select");
			insertOptionMK.setAttribute("id", "OptionMK");
			for(var i=0;i<temps.length-1;i++){
				var temp = temps[i].split(",");
				insertOptionMK.innerHTML +="<option value='"+temp[0]+"'>"+temp[1]+"</option>";
			}
			$('#insertMK').append(insertOptionMK);
			$('#divMK').css("visibility","visible");
		}
	}
}
function insertOption(){
	xmlHttpInitFood.addEventListener("readystatechange",initcallbackInsertFood,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitFood.open("post",urlInit,true);
	xmlHttpInitFood.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitFood.send("act=insertFood");
}
var changeInsertMk="";
function initcallbackInsertFood(){
	if(xmlHttpInitFood.readyState == 4){
		if(xmlHttpInitFood.status == 200){
			var foodData = xmlHttpInitFood.responseText;
			var temps = foodData.split(";");
			var insertOption = document.createElement("select");
			for(var i=0;i<temps.length-1;i++){
				changeInsertMk += temps[i].split(",")[2]+":";
			}
			
			insertOption.setAttribute("onchange", "mkInsertChange()");
			insertOption.setAttribute("id","insetId");
			for(var i=0; i<temps.length-1;i++){
				var temp= temps[i].split(",");
				 
				insertOption.innerHTML +="<option name='insertFood' value='"+temp[0]+"'>"+temp[1]+"</option>"
			}
			$('#insertFoodKind').append(insertOption);
			
		}
	}
}

function mkInsertChange(){
	var val=document.getElementById("insetId").value;
	var isMain = changeInsertMk.split(":");
	if(isMain[val-1]==1){
		insertOptionMK();
	}else{
		$('#insertMK').empty();
		$('#divMK').css("visibility","hidden");
	}
}


function insertFood(){
	var foodname = document.getElementById("insertFoodName").value;
	var foodPrice =  document.getElementById("insertFoodPrice").value;
	var foodQTY = document.getElementById("insertFoodQTY").value;
	var discription = document.getElementById("insertFoodDiscript").value;
	var foodKind = document.getElementById("insetId").options[document.getElementById("insetId").selectedIndex].value;
	var foodMK="";
	
	try {
		foodMK = document.getElementById("OptionMK").options[document
				.getElementById("OptionMK").selectedIndex].value;
	} catch (e) {
	}
	window.location.href = contextPath+"/secure/inserFood.action?foodname="+foodname+"&foodPrice="+foodPrice+"&foodQTY=" +foodQTY+"&discription="+discription+"&foodKind="+foodKind+"&foodMK="+foodMK;
}

function fcancel(fdid,fkId,mkId,fname,fprice,fqty,fdesc,ffkind,fmk) {
	document.getElementById("fname"+fdid).innerHTML=fname;
	document.getElementById("fprice"+fdid).innerHTML=fprice;
	document.getElementById("fqty"+fdid).innerHTML=fqty;
	document.getElementById("fdesc"+fdid).innerHTML=fdesc;
	document.getElementById("ffkind"+fdid).innerHTML="<div id='foodk"+fdid+"'>"+ffkind+"</div>";
	document.getElementById("fmk"+fdid).innerHTML="<div id='foodmk"+fdid+"'>"+fmk+"</div>";
	document.getElementById("foodbtn"+fdid).innerHTML=
		"<input class='MainBtnColor' type='button' id='foodupdate'  value='修改' onclick='fupdate("+fdid+
		","+fkId+","+mkId+")'><input class='MainBtnColor' type='button' id='fooddelete'  value='刪除' onclick='fdeleteFood("+fdid+")'>";
	
	
};
function fupdate(fdid,fkId,mkId){
	this.fdid= fdid;
	var count = 0;
	var fname = document.getElementById("fname"+fdid).innerHTML;
	var fprice = document.getElementById("fprice"+fdid).innerHTML;
	var fqty = document.getElementById("fqty"+fdid).innerHTML;
	var fdesc = document.getElementById("fdesc"+fdid).innerHTML;
	var ffkind = document.getElementById("ffkind"+fdid).firstChild.innerHTML;
	var fmk = document.getElementById("fmk"+fdid).firstChild.innerHTML;
	$('#TRfood'+fdid+'>td').each(function(){
	
		if(count < 4){
		var str = $(this).text();
		
		$(this).html("<input type='text' size='7' value='"+str+"'>");
		}
		else if(count==4){
			foodoption(fdid,fkId);
		}
		else if(count==5){
			foodmkOption(fdid,mkId);
		}
		else
		{
			$(this).html("<input class='MainBtnColor' type='button'  value='確定' onclick='fconfirm("+fdid+","+mkId+")'>" +
					" <input class='MainBtnColor' type='button'  value='取消' onclick='fcancel("+fdid+","+fkId+","+mkId+",\""+fname+"\","+fprice+","+fqty+",\""+fdesc+"\",\""+ffkind+"\",\""+fmk+"\")'>");
		}
		count++;
	});
}
function foodmkOption(fdId,mkId){
	xmlHttpInitMK.addEventListener("readystatechange",initcallbackMK,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitMK.open("post",urlInit,true);
	xmlHttpInitMK.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitMK.send("act=foodMK&fdId="+fdId+"&mkId="+mkId);
}

function initcallbackMK(){
	if(xmlHttpInitMK.readyState == 4){
		if(xmlHttpInitMK.status == 200){
			var data = xmlHttpInitMK.responseText;
			var fdId = data.split("-")[0];
			var mkId = data.split("-")[1];
			var mkDatas = data.split("-")[2].split(";");
			var mkOption = document.createElement("select");
			mkOption.setAttribute("id", "mkOptionId"+fdId);
			for(var i=0;i<mkDatas.length-1;i++){
				if(mkDatas[i][0]==mkId){
				var data = mkDatas[i].split(",");
				mkOption.innerHTML +="<option value='"+data[0]+"'>"+data[1]+"</option>";
				}
			}
			for(var i=0;i<mkDatas.length-1;i++){
				if(mkDatas[i][0]!=mkId){
				var data = mkDatas[i].split(",");
				mkOption.innerHTML +="<option value='"+data[0]+"'>"+data[1]+"</option>";
				}
			}
			$('#foodmk'+fdId).text("");
			if(mkId!=0){
			$('#foodmk'+fdId).append(mkOption);
		}
		}
	}
}

function fdeleteFood(fdid){
	var b=window.confirm("你確定刪除");
	if(b){
		window.location.href=contextPath+"/secure/deletefood?fid="+fdid;
		
		showState("刪除成功");
	}
}
function foodoption(id,fkId){
	
	xmlHttpInitFK.addEventListener("readystatechange",initcallbackFood,true);
	var urlInit = contextPath + "/secure/option";
	xmlHttpInitFK.open("post",urlInit,true);
	xmlHttpInitFK.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInitFK.send("act=foodinit&id="+id+"&fkId="+fkId);
}
var mainKind="";
function initcallbackFood(){
	if(xmlHttpInitFK.readyState == 4){
		if(xmlHttpInitFK.status == 200){
			var data = xmlHttpInitFK.responseText;
			var foodid = data.split("-")[0]; 
			var fkId = data.split("-")[2];
			var fkDatas = data.split("-")[1].split(";"); 
			var newOption = document.createElement("select");
			newOption.setAttribute("id","selectname"+foodid);
			newOption.setAttribute("onchange","mKchange("+foodid+","+fkId+")");
		
			for(var i=0 ;i<fkDatas.length-1;i++){
				mainKind +=fkDatas[i].split(",")[2]+":";
			}			
			
			for(var i=0 ;i<fkDatas.length-1;i++){
				if(fkDatas[i][0]==fkId){
				var fkData = fkDatas[i].split(",");
				newOption.innerHTML += "<option name='fkid' isMain="+fkData[2]+" value='"+fkData[0]+"'>"+fkData[1]+"</option>";
				}
			}
			for(var i=0 ;i<fkDatas.length-1;i++){
				if(fkDatas[i][0]!=fkId){
				var fkData = fkDatas[i].split(",");
				newOption.innerHTML += "<option name='fkid' isMain="+fkData[2]+" value='"+fkData[0]+"'>"+fkData[1]+"</option>";
				}
			}
			$('#foodk'+foodid).text("");
			$('#foodk'+foodid).append(newOption);
			}			
		}
}

function fconfirm(fdid,mkId){
	//var order = getOrder();
	var fname = document.getElementById("fname"+fdid).firstChild.value;
	var fprice = document.getElementById("fprice"+fdid).firstChild.value;
	var fqty = document.getElementById("fqty"+fdid).firstChild.value;
	var fdesc = document.getElementById("fdesc"+fdid).firstChild.value;
	var ffkind = document.getElementById("selectname"+fdid).options[document.getElementById("selectname"+fdid).selectedIndex].value;

	var fmk = 0;
	
	try {
		fmk = document.getElementById("mkOptionId" + fdid).options[document
				.getElementById("mkOptionId" + fdid).selectedIndex].value;
	} catch (e) {
	}
	updateFood(fdid,fname,fprice,fqty,fdesc,ffkind,0,fmk);
}

function updateFood(fdid,fname,fprice,fqty,fdesc,ffkind,order,fmk){
	window.location.href=contextPath+"/secure/updatefood?fdid="+fdid+"&fname="+fname+"&fprice="+fprice+"&fqty="+fqty+"&fdesc="+fdesc+"&ffkind="+ffkind+"&sort="+order+"&fmk="+fmk;
	//showState("修改成功");
	
}

function getOrder(){
	var order = $('#testTable').DataTable().order();
	return order[0];
}

function mKchange(fdId,fkId){
	var val=document.getElementById("selectname"+fdId).value;
	var mkk = mainKind.split(":");
	if(mkk[(val-1)]==1){
		foodmkOption(fdId,1);
	}else{
		$('#foodmk'+fdId).empty();
	}
}



