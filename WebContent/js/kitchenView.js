
$(function() {
    $( "#tabs" ).tabs();
    window.onload=function(){
		  cala();	 
		  setInterval(cala,1000);
	  };
	 
    
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

