
$(function() {
	var obj;
	console.log("page="+page);
	if(page==null){
		$( "#tabs" ).tabs();
	}else{
		obj = {active:page};
		$( "#tabs" ).tabs(obj);
	}
 
//	$( "#tabs" ).tabs();
   
   $('a[name="change"]').button();
    window.onload=function(){
		  cala();	 
		  setInterval(cala,1000);
		  hide();
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
    
    function hide(){
    			$.each($("table[name='tabFood']"),function(){
    				if(($(this).find("tr").length)==1){
    					$(this).parent().append("<h3 style='color:red'> 目前無資料 </h3>");
    					$(this).hide();
    				}
    				
//    				if($(this).hasClass("noFood")){
//    					$(this).hide();
//    				}
    			});
 
    			
    		}
  });

function deleteItem(id,page){
	console.log(id + "----" +page);
	var b=window.confirm("你確定出餐?");
	if(b){
		window.location=contextPath+"/kitchen/outMeal.action?id="+id+"&page="+page;
	}
} 