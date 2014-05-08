$(function() {
    var icons = {
      header: "ui-icon-circle-arrow-e",
      activeHeader: "ui-icon-circle-arrow-s"
    };
    $( "#accordion" ).accordion({
      icons: icons
    });
    $( "#toggle" ).button().click(function() {
      if ( $( "#accordion" ).accordion( "option", "icons" ) ) {
        $( "#accordion" ).accordion( "option", "icons", null );
      } else {
        $( "#accordion" ).accordion( "option", "icons", icons );
      }
    });
   
  });
//-----------------------------------------------------------------
	windows.onload=function(){
		startTimer();
	};
		var timerID = null
		var timerRunning = false
		function stopTimer(){
		
		        if(timerRunning) {
		                clearTimeout(timerID)
		                timerRunning = false
		        }
		} 
		function startTimer(){
		    stopTimer()
		    runClock()
		}
		function runClock(){
		        document.clock.face.value = timeNow()
		        timerID = setTimeout("runClock()",1000)
		        timerRunning = true
		}
		function timeNow() {
		        now = new Date()
		        hours = now.getHours()
		        minutes = now.getMinutes()
		        seconds = now.getSeconds()
		        timeStr = "" + hours
		        timeStr  += ((minutes < 10) ? ":0" : ":") + minutes
		        timeStr  += ((seconds < 10) ? ":0" : ":") + seconds
		        timeStr  += (hours >= 12) ? " PM" : " AM"
		        return timeStr
		} 
