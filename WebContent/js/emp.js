$(function(){
		$(document).ready(
		function(){
			$('#mySelect').hide();	
		}		
		);
});
var empId;
var b = false;
function empUpdate(empId){	
	if(b==true){
		if(this.empId==empId){//同一層
		console.log($("input[name$=Id]").val());
		$("#modifyEmpForm"+empId).append($("#trEmp"+empId));
		$("#modifyEmpForm"+empId).submit();
		}else{
			return;
		}
//		$(':text').each(function(){
//			console.log($(this).val());
//		});
	}else{
		alert("stop");
	$('#update'+empId).val("確定").after($("<input type='button' class='MainBtnColor' id='cancel+"+empId+"' value='取消'/>").one("click",goback));
	var count = 0;
	//console.log($("#trEmp"+empId).html());
	$('#trEmp'+empId+'>td').each(function(){
		if(count==0){//---empId
			var str = $(this).text().trim();
			$(this).html("<input type='text' name='emp.empId' size='9'  value='"+str+"'>");
			count++;
			return;
		}
		if(count==1){//emp.name
			var str = $(this).text();
			$(this).html("<input type='text' size='9' name='emp.name'  value='"+str+"'>");
			count++;
			return;
		}
		if(count==2){//sex
			$(this).empty().append($("<select name='emp.sex'></select>").html("<option value='W'>W</option><option value='M'>M</option>"));
			count++;
			return;
		}
		if(count==4){//到職日期
			var str = $(this).text();
			$(this).empty().append($("<input type='text' name='emp.comedate'/>").attr("id","datepicker").datepicker({
				dateFormat : "yy-mm-dd",
				changeMonth : true,
				changeYear : true
			}));
			count++;
			return;
		}
		if(count<9){
			if(count==3){//emp.bdyPriority.priId
				$(this).empty();
				$(this).append($('#mySelect').clone().attr("name","emp.bdyPriority.priId").show());
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
	this.empId = empId;
	}
}

function goback(){
	window.location = contextPath+"/secure/showEmp.action";
}

function empOption(empId){
	var sele=document.createElement("select");
	sele.setAttribute("id","myPri");
	
}
