var xmlHttp = new XMLHttpRequest();
var xmlHttp2 = new XMLHttpRequest();
var xmlHttpInit = new XMLHttpRequest();
function getFloor(){
	xmlHttpInit.addEventListener("readystatechange",initcallback,false);
	var urlInit = contextPath + "/tableset";
	xmlHttpInit.open("post",urlInit,true);
	xmlHttpInit.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpInit.send("act=init");
}
function initcallback(){
	if(xmlHttpInit.readyState == 4){
		if(xmlHttpInit.status == 200){
			var data = xmlHttpInit.responseText;
			var floorDatas = data.split(";");
			for(var i=0 ;i<floorDatas.length-1;i++){
				floorData = floorDatas[i].split(",");
				$('#changeFloor').append("<option value='"+floorData[0]+"'>"+floorData[1]+"</option>");
			}
		}
	}
}

	$(function() {
		getFloor();
		resizeWindow();
		var count = 0;
		var idCount = 0;
		var delTBlist = "";
		
		window.onresize = resizeWindow;
		function resizeWindow(){
			var picTBHeight = $( document ).height()- ($(document).height()*0.27);
			var picTBWidth = $('#picTB').width();
			$('#picTB').css("height",picTBHeight);
			$('#picTB').css("width",picTBWidth);
		}
		
		function doLoadTable(){
			$('#picTB>div').fadeToggle(800,function(){
				$(this).remove();
			});
			count = 0;
			idCount = 0;
			var floor = $('#changeFloor').find(":selected").val();
			xmlHttp2.addEventListener("readystatechange",loadcallback,false);
			var url = contextPath + "/tableset";
			xmlHttp2.open("post",url,true);
			xmlHttp2.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlHttp2.send("act=load&f="+floor);
		}

		function loadcallback(){
			if(xmlHttp2.readyState == 4){
				if(xmlHttp2.status == 200){
					$('#picTB>div').remove();
					var data = eval('(' + xmlHttp2.responseText +')');
					if(data == null || data.length < 1)
					{
						showState("尚未有擺設");
					}else{
						for(var i=0; i<data.length;i++){
							var tbId = data[i].tbId;
							var tbName = data[i].tbName;
							var tbSize = data[i].tbSize;
							var tbLocation = data[i].tbLocation;
							var tbState = data[i].tbState;
							addTB(tbId ,tbName , tbSize , tbState , tbLocation);
						}
						showState("讀取完成");
					}
				}
			}
		}
		
		$('body').on('change','#changeFloor',doLoadTable);
		
		$('body').on('click','#reset',doLoadTable);
		
		$('#picTB').on('click','#delTB',function(){
			$('#saveFloor').attr("disabled","disabled");
			$(this).attr("id","");
			$(this).parents('#picTB>div').fadeToggle(800,function(){
				$(this).remove();
				$('#saveFloor').removeAttr("disabled");
			});
			var TBcount = $(this).parents('#picTB>div').attr("id");
			delTBlist += TBcount + ",";
			$('#dataTB>div[id='+TBcount+']').slideToggle(500,function(){
				$(this).remove();	
			});
		});
		
		$('body').on('click','#saveFloor',function(){
			var str = "";
			var floor = $('#changeFloor').find(":selected").val();
			$('#picTB>div').each(function(){
				var TBid =  $(this).attr("id");
				var tbName = $('#'+TBid+'>input[id="tbName"]').val();
				var tbSize = $('#'+TBid+'>input[id="tbSize"]').val();
				var state = $('#'+TBid+'>input[id="tbState"]').val();
				//location
				var pic_Offset = $('#picTB').position();
				var pic_top = parseInt(pic_Offset.top);
				var pic_left = parseInt(pic_Offset.left);
				var myOffset = $(this).position();
				var top = parseInt(myOffset.top - pic_top);
				var left = parseInt(myOffset.left - pic_left);
				

				var tableData = {
						"tbId" : TBid,
						"tbName" : tbName,
						"tbSize" : tbSize,
						"pos" : top+","+left,
						"tbFloor" : floor,
						"tbState" : state
				};
				str += JSON.stringify( tableData )+";";
			});
			xmlHttp.addEventListener("readystatechange",callback,false);

			var url = contextPath + "/tableset";
			xmlHttp.open("post",url,true);
			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlHttp.send("act=save&data="+str+"&f="+floor+"&DTL="+delTBlist);
		});
		
		function callback(){
			if(xmlHttp.readyState == 4){
				if(xmlHttp.status == 200){
					showState("儲存完成");
					delTBlist = "";
				}
			}
		}

		$('#picTB').on('click','#lockIMG',function(){
			$(this).parents("#picTB>div").draggable();
			var state = $(this).attr('alt');
			if(state=="unLock")
			{
				$(this).parents("#picTB>div").draggable("destroy");
				$(this).attr('alt','Lock');
				$(this).attr('src', contextPath + '/images/Lock.jpg');
			}else
			{
				$(this).parents("#picTB>div").draggable("option", "containment", "parent");
				$(this).attr('alt','unLock');
				$(this).attr('src',contextPath + '/images/unLock.jpg');
			}
		});
		
		$('#addTB').click(function() {
			var tbName;
			var tbSize;
			if(tbName=prompt("請輸入桌子名稱或者代號",""))
			{
				if(tbName.length > 0 && tbName != null && tbName != "")
				{
					if(tbSize=prompt("請輸入桌子可容納人數",""))
					{
						if(tbSize > 0 && tbSize < 99999)
						{
							addTB(-1,tbName , tbSize, 0 , 0);
						}else
						{
							alert("必須為一個整數");
						}
					}
				}
			}
		});
		
		function addTB(tbId ,tbName , tbSize , tbState , tbLocation){
			idCount++;
			count++;
			var tbStateName;
			if(tbState == 0)
				tbStateName = "未使用";
			else
				tbStateName = "使用中";
			
			if(tbId == -1 || tbId == null)
				tbId = "tb"+idCount;
			var myOffset = $('#picTB').position();
			var mot = parseInt(myOffset.top);
			var mol = parseInt(myOffset.left);
			var topCount = mot;
			var leftCount = mol;	
			if(tbLocation == 0 || tbLocation == null || tbLocation == ""){
				topCount = mot + (count*5);
				leftCount = mol + (count*5);	
			}else
			{
				tbLocation = tbLocation.split(",");
				//alert( (myOffset.top+tbLocation[0]) + ", " + (myOffset.left+tbLocation[1]) );
				var tt = parseInt(tbLocation[0]);
				var tl = parseInt(tbLocation[1]);
				//alert( (mot+tt) + ", " + (mol+tl));
				topCount = mot+tt;
				leftCount = mol+tl;
			}
		
			//Create Table
			var newTbDiv = document.createElement("div");
			newTbDiv.innerHTML = tbName;
			newTbDiv.innerHTML += "<br>"+tbSize+" 人桌";
			newTbDiv.innerHTML += "<br>"+tbStateName;
			newTbDiv.innerHTML += '<input type="hidden" id="tbName" value="'+tbName+'">';
			newTbDiv.innerHTML += '<input type="hidden" id="tbSize" value="'+tbSize+'">';
			newTbDiv.innerHTML += '<input type="hidden" id="tbState" value="'+tbState+'">';
			newTbDiv.innerHTML += '<br><img class="tbimg" id="lockIMG" alt="unLock" src="'+contextPath+'/images/unLock.jpg">';
			newTbDiv.innerHTML += '<img class="tbimg" id="delTB" alt="unLock" src="'+contextPath+'/images/del.jpg">';
			newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
			newTbDiv.setAttribute("class","divTB");
			newTbDiv.setAttribute("id",tbId);
			
			var newd =$(newTbDiv);
			newd.draggable();
			newd.draggable( "option", "containment", "parent" );
			newd.draggable( "option", "cursor", "crosshair" );
			$('#picTB').append(newd);
		}
	} );