$(function() {
	window.onload=function(){
		  cala();	 
		  setInterval(cala,1000);
	  };
	  function deleteItem(id){
		  console.log(id);
	  }
    $( "#tabs" ).tabs();
    $( "a,button" )
    .button()
    .click(function( event ) {
    	event.preventDefault();
    });
      
    
    function cala(){
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
    	}
    }
    }
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
