
$(function() {
	getMains();
	//  css掛載
// 	$('#btnArea>:button').mouseover(function() {
// 					$(this).css("background", "#44C3B6");})
// 				.mouseout(function() {
// 					$(this).css("background", "#3BB0A4");});
	/*
	*	解決IE緩存問題
	*/
	$.ajaxSetup({ cache: false });
});

function getMains() {
	var url = contextPath+"/order/GetMainServlet";
	$.getJSON(url, function(result) {
		$('.page1').hide();
		showMains(result);
	});
}

 
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
 

// function orderClick2() {
//	if ($(this)[0].childNodes[0].nodeValue)
//		var value = $(this)[0].childNodes[0].nodeValue;
//		if (value == "點餐")
//			$(this)[0].childNodes[0].nodeValue = 1;
//		else
//			$(this)[0].childNodes[0].nodeValue ++;
//	}
 
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
	var value = $(this)[0].childNodes[0].nodeValue;
//	console.log(value);

	value = $(this).nodeName;
//	console.log(value);
	
	value = $(this)[0].nodeName;
	console.log(value);  //ul
	
	value = $(this)[0].childNodes[0].nodeName;
	console.log(value);  //li

	value = $(this).parent();
//	console.log(value);
//	console.log(value.nodeName);

	value = $(this).val();
	console.log(value);  //
	value = $(this).html();  
	console.log(value); //<li><a href="#" class="myButton"
						// id="btnconfirm">確認</a></li>
	console.log("length : ");
	console.log($(this).parent().children("li").length);

	value = $(this).text(); // 確認
	console.log(value);

	console.log($(this).parent().is("li").length);
	console.log($(this).parent().length);
	console.log("ul's num : "			+$(this).parent().children("ul")		   .length);
	console.log("ul's li's num : "+$(this).parent().children("ul").children("li").length);
	console.log("ul's li's span's num : "+$(this).parent().children("ul").children("li").children("span").length);
	console.log("li's num : "			+$(this).parent().children("li").length);

	console.log("ul's id=mainContent's num :" +$(this).parent().children("ul").children("#mainContent").length);
//	console.log("ul's id=mainContent's num (2):" +$(this).parent().childrens("#mainContent").length);
	console.log("ul's id=mainContent's num (3):" +$(this).parent().find("#mainContent").length);
	var mainContents = $(this).parent().children("ul").children("#mainContent");
	console.log("loop : ");
	var count = [];
	var orderListJson = [];
	var c = 0;
	$(mainContents).children("span").each(function(index) {
		console.log(index +":"+$(this).text());
		count[c] = $(this).text();
		c++;
		var texts = $(this).parent().filter(function() {
			return this.nodeType === 3;
		});
//		console.log('123');
		$(texts).each(function(index2) {
			console.log($(this).text());
		});
//		console.log($(this).parent())
	});
	console.log("loop children :");
	var c1 = 0;
	var oljCount = 0;
	$(mainContents).contents().
    filter(function() {
        return this.nodeType === 3;
      }).each(function(index) {
		console.log(index +":"+$(this).text());
		if(count[c1]>0){
			orderListJson[oljCount] = {'name': $(this).text(),'count': count[c1]};
			oljCount++;
		}
		c1++;
	});
	console.log("loop children and span");
	console.log(orderListJson);
	
//	$(mainContents).contents().each(function(index) {
//			console.log(index + ":"+ $(this).filter(function() {
//				return this.nodeType === 3;
//			}));
//			console.log(index +":"+$(this).text());
//	});
	
//	console.log($(this).parent().html());
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
 