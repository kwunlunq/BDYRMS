
$(function() {
	 getSetdetail();
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

var contextPath = "${pageContext.request.contextPath}";

/*
 * 當"點套餐"按鈕按下時, 利用$.getJSON呼叫/order/getSetDetailServlet
 * 將回傳結果(result)傳給showSetBtnWithDetail()
 */
function getSetdetail() {
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
 

 function orderClick() {
// 		alert("123");
// 		alert($(this)[0].childNodes[0].nodeValue);
	if ($(this)[0].childNodes[0].nodeValue)
		var value = $(this)[0].childNodes[0].nodeValue;
		if (value == "點餐")
			$(this)[0].childNodes[0].nodeValue = 1;
		else
			$(this)[0].childNodes[0].nodeValue ++;
// 		alert("3");
		
		
	}
 
 function orderClick2() {
// 		alert($(this)[0].childNodes[0].nodeValue);
// 		alert($(this)[0].innerHTML);
// 		alert($(this)[0].nodeName);
// 		alert($(this)[0].parentNode.nodeName);
// 		alert($(this)[0].parentNode.firstChild.nodeName);
// 		var parent = $(this)[0].parentNode;
// 		$(parent).$('#count');
// 		alert($(parent+" #count"));

// 		for (var i = 0; i <= 3; i++) {
// 			alert(i + "  " + $(this)[0].parentNode.childNodes[i].nodeName);
// 		}
		
// 		alert($(this)[0].parentNode.nodeName);  //li
		//alert($(this)[0].parentNode.getElementById("count").nodeName);
		var id = $(this).attr("id").substring(5);
		//$(id).text = count++;
		var count = $('#count' + id).text();
		var num = parseInt(count)+1;
// 		alert(count);
		$('#count' + id).text(num);
		
// 		alert($(this));
//		alert($(this).parentNode.getElementById("count").nodevalue);
// 		alert($(this)[0].parantNode[0][0].childNodes[1].nodeValue);
	}
 
	 
function showMains(mains) {
	 var div = document.getElementById("mainArea");

	 var btnConfirm = $('<ul><li><a href="#" class="myButton" id="btnconfirm">確認</a></li></ul>');
	 $(div).append(btnConfirm);
// 	 	   .append('<br>');
	 
		var c = 0;
	 for (var i = 0; i < mains.length; i++) {
		 var outerUl = document.createElement('ul');
		 var title = document.createElement("li");
		 
		 
		 title.setAttribute("id", "mainTitle");
		 $(outerUl).append(title);
		 $(div).append(outerUl);
// 	 	   .append('<hr>');
		 for (key in mains[i]) {
			 var titleText = document.createTextNode(key);
			 console.log(key);
			 $(title).append(titleText);
			 var innerUl = document.createElement("ul");
			 var contents = mains[i][key];
			 for (var j = 0; j < contents.length; j++) {
			 	var content = document.createElement("li");
			 	content.setAttribute("id", "mainContent");
				 console.log(contents[j]);
				 var contextText = document.createTextNode(contents[j]);
				 console.log(contextText);
				 $(content).append(contextText);
				 var addbutton = $('<a id="order'+c+'" class="myButton">＋</a>');
				 var subbutton = $('<a id="order'+c+'" class="myButton">－</a>');
				 var count = $('<span id="count'+c+'">0</span>');				 
// 				 var count = $('<span class="myButton" id="count">0</span>');
				 //$(count).click(orderClick);
				 $(addbutton).click(orderClick2);
				 $(subbutton).click(orderClick2);
// 				 $(button).click(orderClick);
// 				 $('body').on("click","#order", orderClick);
// 				 button.addEventListener("click",orderClick);
// 				 $("#element1").bind("click", doSomething2, false);
// 				 var button = document.createElement("a");
// 				 button.setAttribute("class", "myButton");
// 				 button.setAttribute("value", "點餐");
				 
// 				 var contextText2 = document.createTextNode("123");
// 				 $(button).append(contextText2);
				$(content).append(addbutton);
				$(content).append(count);
// 				textarea
// 				 $(content).append(button);
				 $(content).append(subbutton);
				 
// 				 <a href="#" class="myButton">點餐</a>
				 $(outerUl).append(content);
// 				 $(outerUl).append(innerUl);
				 c++;
			 }
		 }
// 		 $(div).append('<br>');
	 }
}
 