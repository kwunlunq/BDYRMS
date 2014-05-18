$(function(){
		$(document).ready(
		function(){
			$('#mySelect').hide();	
		}		
		);
		
});
var b = false;
function empUpdate(empId,btn){
	if(b==true){
		$(':text').each(function(){
			console.log($(this).val());
		});
	}else{
	$("#update"+empId+"").val("確定").attr("type","submit").after($("<input type='button' class='MainBtnColor' value='取消'/>").one("click",goback));
	function goback(){
		window.location = contextPath+"/secure/showEmp.action";
	}
	var count = 0;
	console.log($("#trEmp"+empId).html());
	$('#trEmp'+empId+'>td').each(function(){
		if(count==0){
			count++;
			return;
		}
		if(count==2){
			$(this).empty().append($("<select name='sex"+empId+"'></select>").html("<option value='0'>W</option><option value='1'>M</option>"));
			count++;
			return;
		}
		if(count<9){
			if(count==3){
				$(this).empty();
				$(this).append($('#mySelect').clone().attr("id","mySelect"+empId).show());
				count++;
				return ;
			}else if(count==8){
				$(this).empty().append($("<select id='resign'"+empId+"></select>").html("<option value='0'>在職</option><option value='1'>離職</option>"));
				count++;
				return ;
			}
		var str = $(this).text().trim();
		$(this).html("<input type='text' size='9'  value='"+str+"'>");
		count++;			
		}
	});	
	b=true;
	}
}
function empOption(empId){
	var sele=document.createElement("select");
	sele.setAttribute("id","myPri");
	
}
