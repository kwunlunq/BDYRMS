var selMains = [];  // array of mains' names
var setNames = [];
$(function() {
    $( "#tabs" ).tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 },
		disabled: [1]});
	var url = contextPath+"/order/getSetServlet";
	$.getJSON(url, function(result) {
		for (var i = 0; i < result.length; i++) {
			setNames[i] = result[i].name;
		}
//		console.log(setNames);
	});
    getMains();
    
  	$( "#tabs" ).on( "tabsactivate", function( event, ui ) {
  		var active = $( "#tabs" ).tabs( "option", "active" );
  		switch(active) {
  		case 0:
  			selMains = [];
  		    break;
  		case 1:
//  			showSelectMains();
//  			comfirmClick();
  			showSelectMainsAcod();
  		    break;
  		case 2:
  		    break;
  		case 3:
  		    break;
  		case 4:
  		    break;
  		default:
  		    alert("default");
  		}
  	} );

	
	$.ajaxSetup({ cache: false });  //解決IE緩存問題
});

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
		console.log("outer: i = "+i);
		for (var j = 0; j < setNames.length; j++) {
			$(innerDiv).append('<a class="myButton" id="11">'+setNames[j]+'</a>');
		}
		$(accoDiv).append(innerDiv);
	}
	$(accoDiv).accordion({ collapsible: true , 
	      					heightStyle: "content"});
	$("#setArea").append(accoDiv);
}


function getMains() {
	var url = contextPath+"/order/GetMainServlet";
	$.getJSON(url, function(result) {
		$('.page1').hide();
		showMains(result);
	});
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
 
	 
function showMains(mains) {
//	alert("123");
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
	 		<li id="mainTitle">披薩</li>
			<li id="mainContent">夏威夷披薩</li>
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

 