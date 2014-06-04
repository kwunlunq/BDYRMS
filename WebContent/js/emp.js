$(function(){
	
		setTableToMaxStyle("empTable");
		$(document).ready(
		function(){
			$('#mySelect').hide();});
		$("a, button" ).button();
		
		
		$('#table1').DataTable({
		    "jQueryUI": true,
		    "scrollY": ($('#aside').height()-275),
		    "scrollCollapse": true,
		    "paging": false,
		    "aoColumnDefs":[{
		    	bSortable:false
		    }],
		});
});
var empId;
var b = false;
function empUpdate(empId){	
	if(b==true){
		if(this.empId==empId){//同一層
		$("#modifyEmpForm"+empId).append($("#trEmp"+empId));
		$("#modifyEmpForm"+empId).submit();
		}else{
			return;
		}
	}else{
	$('#update'+empId).val("確定").after($("<input type='button' class='MainBtnColor' id='cancel+"+empId+"' value='取消'/>").one("click",goback));
	var count = 0;
	$('#trEmp'+empId+'>td').each(function(){
		if(count==0){//---empId
			var str = $(this).text().trim();
			$(this).after("<input type='hidden' name='emp.empId' size='9'  value='"+str+"'>");
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
			$(this).empty().append($("<select name='emp.sex'></select>").html("<option value='F'>F</option><option value='M'>M</option>"));
			count++;
			return;
		}
		if(count==4){//到職日期
			var str = $(this).text();
			$(this).empty().append($("<input type='text' name='emp.comedate' value='"+str+"'>").attr("id","datepicker").datepicker({
				dateFormat : "yy-mm-dd",
				changeMonth : true,
				changeYear : true
			}));
			count++;
			return;
		}
		if(count==5){//薪水
			var str = $(this).text();
			$(this).html("<input type='text' size='9' name='emp.salary'  value='"+str+"'>");
			count++;
			return;
		}
		if(count==6){//電話
			var str = $(this).text();
			$(this).html("<input type='phone' size='9' name='emp.phone'  value='"+str+"'>");
			count++;
			return;
		}
		if(count==7){//地址
			var str = $(this).text();
			$(this).html("<input type='address' size='9' name='emp.empAddress'  value='"+str+"'>");
			count++;
			return;
		}
		if(count<9){
			if(count==3){//emp.bdyPriority.priId職位
				$(this).empty();
				$(this).append($('#mySelect').clone().attr("name","emp.bdyPriority.priId").show());
				count++;
				return ;
			}else if(count==8){
				$(this).empty().append($("<select name='emp.resign'></select>").html("<option value='0'>在職</option><option value='1'>離職</option>"));
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
