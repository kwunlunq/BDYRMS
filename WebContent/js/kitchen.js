$(function() {
    var icons = {
      header: "ui-icon-circle-arrow-e",
      activeHeader: "ui-icon-circle-arrow-s"
    };
    $( "#accordion" ).accordion({
      icons: icons,
      heightStyle: "content"
    });
    $( "#toggle" ).button().click(function() {
      if ( $( "#accordion" ).accordion( "option", "icons" ) ) {
        $( "#accordion" ).accordion( "option", "icons", null );
      } else {
        $( "#accordion" ).accordion( "option", "icons", icons );
      }
    });
//    var resultlist = new Array();
//    var object=$(':hidden');
//    var i=0;//---------------先抓好所有出餐的物件時間
//    	$.each(object,function(){
//    		var now = new Date();//----------------------創造目前時間物件
//    		var nowMill = now.getTime();//-----------------拿到目前時間物件的LONG
//    		var temp = Math.floor((nowMill-$(this).val())/1000/60);
//    		console.log(" 怪怪ㄉ" + temp);//-----------------目前時間減出餐時間點
//    		if(temp<0){//----------小於0代表在標準出菜時間內
//    			console.log("距離"+ temp + "分鐘出菜");
//    			resultlist.push(temp);
//    		}else{//---------------大於0帶表delay
//    			console.log("目前了Delay---" + temp + "分鐘出菜");
//    			resultlist.push(temp);
//    		}
//    		resultlist.push(temp);
//    	});
//    	console.log(resultlist[1]/1000/60);
    function cala(){
    var myArray = new Array();
    var results=document.getElementsByName("result");
    var objects=document.getElementsByName("calc");
    for(var i=0;i<objects.length;i++){
    	var now = new Date();
    	var nowMill = now.getTime();
    	var judge = nowMill-objects[i].value;
    	if(judge<0){
    		var tempD = Math.floor(Math.abs(judge/(1000*60*60*24)));//---------------天
    		if(tempD==0){
    			tempD="";
    		}else{
    			tempD = Math.floor(Math.abs(judge/(1000*60*60*24)))+"天";
    		}
    		var tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24;//------------------小時
    		if(tempH==0){
    			tempH=="";
    		}else{
    			tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24+"小時";
    		}
    		var tempM = Math.floor(Math.abs(judge/(1000*60)))%60;//-------------------分鐘
    		if(tempM==0){
    			tempM="";
    		}else{
    			tempM = Math.floor(Math.abs(judge/(1000*60)))%60+"分鐘";
    		}
    		var tempS = Math.floor(Math.abs(judge/1000))%60+"秒";//-----------------------秒
    		results[i].innerHTML="距離"+tempD+tempH+tempM+tempS;
    	}else{
    		var tempD = Math.floor(Math.abs(judge/(1000*60*60*24)));//---------------天
    		if(tempD==0){
    			tempD="";
    		}else{
    			tempD = Math.floor(Math.abs(judge/(1000*60*60*24)))+"天";
    		}
    		var tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24;//------------------小時
    		if(tempH==0){
    			tempH=="";
    		}else{
    			tempH = Math.floor(Math.abs(judge/(1000*60*60)))%24+"小時";
    		}
    		var tempM = Math.floor(Math.abs(judge/(1000*60)))%60;//-------------------分鐘
    		if(tempM==0){
    			tempM="";
    		}else{
    			tempM = Math.floor(Math.abs(judge/(1000*60)))%60+"分鐘";
    		}
    		var tempS = Math.floor(Math.abs(judge/1000))%60+"秒";//-----------------------秒
    		results[i].innerHTML="<span style='color:red'>Delay<span>" +tempD+ tempH+ tempM+tempS;
    	}//<span style='color:red'>Delay<span>
    }
    }
  window.onload=cala();
	  setInterval(cala,1000);
  });
//-----------------------------------------------------------------
//	windows.onload=function(){
//		startTimer();
//	};
//		var timerID = null
//		var timerRunning = false
//		function stopTimer(){
//		
//		        if(timerRunning) {
//		                clearTimeout(timerID)
//		                timerRunning = false
//		        }
//		} 
//		function startTimer(){
//		    stopTimer()
//		    runClock()
//		}
//		function runClock(){
//		        document.clock.face.value = timeNow()
//		        timerID = setTimeout("runClock()",1000)
//		        timerRunning = true
//		}
//		function timeNow() {
//		        now = new Date()
//		        hours = now.getHours()
//		        minutes = now.getMinutes()
//		        seconds = now.getSeconds()
//		        timeStr = "" + hours
//		        timeStr  += ((minutes < 10) ? ":0" : ":") + minutes
//		        timeStr  += ((seconds < 10) ? ":0" : ":") + seconds
//		        timeStr  += (hours >= 12) ? " PM" : " AM"
//		        return timeStr
//		} 
