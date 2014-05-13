var selMains = [];  // array of mains' names
var setNames = [];
var setIds   = [];
var orderlistIndex = 0;
$(function() {
	// 畫出兩個tabs
	getFoods();
	getSets();
	
	$('#orderarea').on('click','input',function(){
		addOrderAreaBtn('orderlist-'+orderlistIndex,$(this).attr("fdId"),$(this).attr("isMain"),$(this).val());
	});

	$( "#orderlist" ).on("tabsactivate", function( event, ui ) {
  		orderlistIndex = $("#orderlist").tabs("option", "active");
  		console.log(orderlistIndex);
  		
  	} );
	
	// 單點點餐, 變套餐
	$('#orderlist-0').on('click','input', orderlistClick);

	$("#ChooseSetDialog").dialog({
		autoOpen: false,
		modal:true,
		width: 200,
	      show: {
	        effect: "clip",
	        duration: 800
	      },
	      hide: {
	        effect: "clip",
	        duration: 800
	      }
	});
	
	// 解決IE緩存問題
	$.ajaxSetup({ cache: false });  
});

function orderlistClick() {
	$("#ChooseSetDialog").empty();
	if($(this).attr("isMain") == 'true'){
		for (var i = 0; i < setNames.length; i++) {
			var btn = document.createElement("input");
			$(btn).attr("type", "button");
			$(btn).attr("class", "MainBtnColor");
			$(btn).attr("setId", setIds[i]);
			$(btn).attr("FBId", $(this).attr("id"));
			$(btn).val(setNames[i]);
			$(btn).click(setOnClick);
			$("#ChooseSetDialog").append(btn);
		}
		$("#ChooseSetDialog").dialog( "open" );
	}else{
		$(this).effect("highlight");
	}
}
var setCount = 1;
function setOnClick() {
//	console.log($(this).val());
//	console.log($(this).attr("FBId"));
	var li = document.createElement("li");
	var tagsid = "orderlist-"+ setCount;
//	$(li).attr("id", "li1");
	$("#ul-detail").append("<li><a href='#"+ tagsid +"'>"+$(this).val()+"</a></li>");
	$("#orderlist").append("<div id='"+tagsid+"'></div>");
	$("#orderlist").tabs("refresh");
	var fdBtn = $('#'+$(this).attr("FBId"));
//	console.log
	addOrderAreaBtn(tagsid, $(fdBtn).attr("fdId"), true,  $(fdBtn).val());
	$("#ChooseSetDialog").dialog( "close" );
	$(fdBtn).remove();
	$("#orderlist").tabs( "option", "active", setCount);
	setCount++;
}

function getFoods() {
	var url = contextPath+"/order/getAllFoodsServlet";
	$.getJSON(url, function(result) {
		drawTab(result);
	});
}
function drawTab(result) {
	var mains = result.isMain;
	var tagscount = 0;
	for (; tagscount < mains.length; tagscount ++) {
		$("#orderarea").append("<div id='orderarea-"+tagscount+"'></div>");
		for (key in mains[tagscount]) {
			var li = document.createElement("li");
			$(li).append("<a href='#orderarea-"+tagscount+"'>"+key+"</a></li>");
			var foods = mains[tagscount][key];
			for (var j = 0; j < foods.length; j++) {
				addOrderAreaBtn("orderarea-"+tagscount,foods[j].fdId,true,foods[j].fdName);
			}
			$("#ul-order").append(li);
		}
	}


	var others = result.notMain;
	for (var i = 0; i < others.length; i++) {
		tagscount++;
		$("#orderarea").append("<div id='orderarea-"+tagscount+"'></div>");
		for (key in others[i]) {
			var li = document.createElement("li");
			$(li).append("<a href='#orderarea-"+tagscount+"'>"+key+"</a></li>");
			var foods = others[i][key];
			for (var j = 0; j < foods.length; j++) {
				addOrderAreaBtn("orderarea-"+tagscount,foods[j].fdId,false,foods[j].fdName);
			}
			$("#ul-order").append(li);
		}
	}

	$("#orderarea").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});
	$("#orderlist").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});

	$("li").draggable();
}
var FBId = 0;
function addOrderAreaBtn(foodTag,fdId,isMain,foodName){
	var foodBtnId = "foodBtnId"+FBId;
//	console.log(foodBtnId);
	var newOABtn = $("<input class='MainBtnColor' type='button'>");
	newOABtn.attr("fdId",fdId);
	newOABtn.attr("isMain",isMain);
	newOABtn.attr("value",foodName);
	newOABtn.attr("id", foodBtnId);
	$('#'+foodTag).append(newOABtn);
	FBId++;
}

function getSets() {

	var url = contextPath+"/order/getSetServlet";
	$.getJSON(url, function(result) {
		for (var i = 0; i < result.length; i++) {
			setNames[i] = result[i].name;
			setIds[i] = result[i].id;
		}
	});
}
function showSelectMains() {
	$("#selectMainUl").remove();
	
	var ul = document.createElement("ul");
	ul.setAttribute("id", "selectMainUl");
	for (var i = 0; i < selMains.length; i++) {
		$(ul).append("<li class='listItem'>"+selMains[i]+"</li>");
	}
	$("#setArea").append(ul);
}

function showSelectMainsAcod() {
	$("#accoDiv").remove();
	var accoDiv = document.createElement("div");
	accoDiv.setAttribute("id", "accoDiv");

	var url = contextPath+"/order/getSetServlet";
	$.getJSON(url, function(result) {
	
	});
	for (var i = 0; i < selMains.length; i++) {
		var title = document.createElement("h3");
		$(title).append(document.createTextNode(selMains[i]));
		$(accoDiv).append(title);
		var innerDiv = document.createElement("div");
		innerDiv.setAttribute("id", "mainaco"+i);
//		console.log("outer: i = "+i);
		for (var j = 0; j < setNames.length; j++) {
			
			$(innerDiv).append('<a class="myButton" setId="'+setIds[j]+'"id="setBtn'+j+'">'+setNames[j]+'</a>');
			$(innerDiv).attr("setId", setIds[j]);
//			alert("setId = "+ setIds[j]);
			innerDiv.setAttribute("setId", setIds[j]);
		}
		$(accoDiv).append(innerDiv);
	}
	$(accoDiv).accordion({ collapsible: true , 
	      					heightStyle: "content" ,
	      					create: function(event, ui) {
	      						
	      					}});
	$("#setArea").append(accoDiv);
	$("a[id^=setBtn]").button()
      				  .click(function( event ) {
      					  	event.preventDefault();
      					  	getSetDetails(this);
      				  });
}

function getSetDetails(thisbtn) {
	var setId = $(thisbtn).attr("setId");
	var mainacoId = $(thisbtn).parent().attr("id");
	var url = contextPath+"/order/getSetDetailServlet?setId="+setId;
	$.getJSON(url, function(result) {
		showSetDetails(result, mainacoId);
	});
}

function showSetDetails(details, mainacoId) {
	var mainaco = document.getElementById(mainacoId);
	$(mainaco).empty(); // 清空mainaco中的子元素
	
	for (var i = 0; i < details.length; i++) {
		var ul = document.createElement('ul'); 	// <ul>
		var title = document.createElement("li");  // <li id=setTitle>
		title.setAttribute("id", "setTitle");
		$(ul).append(title);
		$(mainaco).append(ul);
		
		for (key in details[i]) {
			$(title).append(document.createTextNode(key));
			 
			 var detail = details[i][key];
			 for (var j = 0; j < detail.length; j++) {
				 var li = $("<li class='listItem' id='setContent'></li>");
				 $(li).append(document.createTextNode(detail[j].fdName));
				 
				 // ＋
				 var addbutton = $('<a id="btnst" num="1" class="myButton">＋</a>');
				 $(addbutton).click(setClick);
				 $(li).append(addbutton);
				 // 0
				 var count = $('<span id="setcount">0</span>');				 
				 $(li).append(count);
				 // －
				 var subbutton = $('<a id="btnst" num="-1" class="myButton">－</a>');
				 $(subbutton).click(setClick);
				 $(li).append(subbutton);
				 
				 $(ul).append(li);
			 }
		}		
	}
}

function getMains() {
	var url = contextPath+"/order/GetMainServlet";
	$.getJSON(url, function(result) {
		showMains(result);
	});
}


function showMains(mains) {
	var div = document.getElementById("mainArea");

	var btnConfirm = $('<ul><li><a href="#" class="myButton" id="btnconfirm">確認</a></li></ul>');
	btnConfirm.click(comfirmClick);
	$(div).append(btnConfirm);
	 
	var c = 0;
	for (var i = 0; i < mains.length; i++) {
		var outerUl = document.createElement('ul');
		var title = document.createElement("li");
		 
		 title.setAttribute("id", "mainTitle");
		 $(outerUl).append(title);
		 $(div).append(outerUl);
		 for (key in mains[i]) {
			 var titleText = document.createTextNode(key);
			 $(title).append(titleText);
			 var innerUl = document.createElement("ul");
			 var contents = mains[i][key];
			 for (var j = 0; j < contents.length; j++) {
			 	var content = document.createElement("li");
			 	content.setAttribute("id", "mainContent");
				 var contextText = document.createTextNode(contents[j]);
				 $(content).append(contextText);
				 
				 var addbutton = $('<a id="order'+c+'" num="1" class="myButton">＋</a>');
				 var subbutton = $('<a id="order'+c+'" num="-1" class="myButton">－</a>');
				 var count = $('<span id="count'+c+'">0</span>');				 
				 $(addbutton).click(orderClick);
				 $(subbutton).click(orderClick);
				 $(content).append(addbutton);
				 $(content).append(count);
				 $(content).append(subbutton);
				 
				 $(outerUl).append(content);
				 c++;
			 }
		 }
	 }
}


 
function orderClick() {
	var id = $(this).attr("id").substring(5);
	var count = $('#count' + id).text();
	var numatr = parseInt($(this).attr("num"));
	var num = parseInt(count)+numatr;
		
	if (num >= 0) {
		$('#count' + id).text(num);
	}
}

function setClick() {
	var num = parseInt($(this).attr("num"));
	var setcount = parseInt($(this).siblings("#setcount").text());
	setcount += num;
	if (setcount >= 0) {
		$(this).siblings("#setcount").text(setcount);
	}
}
 
function comfirmClick() {
	$('#tabs').tabs("option", "disabled", []);
	$("#tabs").tabs( "option", "active", 1 );
	var mainContents = $(this).parent().children("ul").children("#mainContent");
	var count = [];
	var orderListJson = [];
	var c = 0;
	$(mainContents).children("span").each(function(index) {
//		console.log(index +":"+$(this).text());
		count[c] = $(this).text();
		c++;
		var texts = $(this).parent().filter(function() {
			return this.nodeType === 3;
		});
		$(texts).each(function(index2) {
//			console.log($(this).text());
		});
	});
	var c1 = 0;
	var oljCount = 0;
	var mainIndex = 0;
	$(mainContents).contents().
    filter(function() {
        return this.nodeType === 3;
      }).each(function(index) {
//		console.log(index +":"+$(this).text());
		if(count[c1]>0){
			orderListJson[oljCount] = {'name': $(this).text(),'count': count[c1]};
			oljCount++;
			for (var i = 0; i < count[c1]; i++) {
				selMains[mainIndex++] = $(this).text();
			}
		}
		c1++;
	});
	console.log(orderListJson);
	console.log(selMains);
}
 
	 
//	css掛載
//	$('#btnArea>:button').mouseover(function() {
//					$(this).css("background", "#44C3B6");})
//				.mouseout(function() {
//					$(this).css("background", "#3BB0A4");});


/*
 * <div id="mainArea">
		<a href="#" class="myButton" id="confirm">確認</a>
		background: #e8edff; 
		<ul>
			<li id="mainTitle">牛排</li>
			<li id="mainContent">
				牛小排
	 			<a href="#" id="order" class="myButton">+</a>
	 			<a  id="count" >0</a>
	 			<a href="#" id="order" class="myButton">-</a>
			</li>
			<li id="mainContent">菲力牛排</li>
			<li id="mainContent">XX牛排</li>
			<li id="mainContent">OO牛排</li>
		</ul>
		<ul>
	 		<li id="mainTitle">披薩</li>
			<li id="mainContent">夏威夷披薩</li>
			<li.....></li>
			...
	 	</ul>
	 </div>
*/

/*
[  {"牛排":["牛小排","菲力牛排","肋眼牛排"]}, 
   {"披薩":["夏威夷披薩","海鮮披薩","龍蝦披薩"]},
   {"義大利麵":["奶油蛤蠣義大利麵","青醬海鮮義大利麵","好吃的義大利麵"]},
   {"燉飯":["OO燉飯","XX燉飯","VV燉飯"]}
  ]
*/

 