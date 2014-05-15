$(function() {
	$("#dialog").dialog({
		autoOpen : false,
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "explode",
			duration : 1000
		}
	});
	$("body").on("click", "#odId", function() {
		alert($(this).text());
		$("#dialog").dialog("open");
	});
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true
	});
	$("#tabs").tabs();
	$('#container1').highcharts({
		chart : {
			type : 'bar',
			width : 600,
			events : {
				load : GetReportData,
			}
		},
		title : {
			text : '主餐銷售量'
		},
		subtitle : {
			text : ''
		},
		xAxis : {},
		yAxis : {
			min : 0,
			title : {
				text : '份',
				align : 'high'
			},
			labels : {
				overflow : 'justify'
			}
		},
		tooltip : {
			valueSuffix : '份'
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		credits : {
			enabled : false
		},
		series : [ {
			name : '數量',
			data : [ 107, 51, 235, 203 ]
		} ]
	});
	$('#container2').highcharts(
			{
				chart : {
					zoomType : 'xy',
					width : 600,
					events : {
						load : GetReportData,
					}
				},
				title : {
					text : '平均消費金額/來客數 分佈'
				},
				subtitle : {
					text : ''
				},
				xAxis : [ {
					categories : [ '8-9', '9-10', '10-11', '11-12', '12-13',
							'13-14', '14-15', '15-16', '16-17', '17-18',
							'18-19', '19-20', '20-21', '21-22', '22-23',
							'23-24' ]
				} ],
				yAxis : [ {
					min : 0,
					labels : {
						format : '{value} 元',
						style : {
							color : Highcharts.getOptions().colors[1]
						}
					},
					title : {
						text : '',
						style : {
							color : Highcharts.getOptions().colors[1]
						}
					}
				}, {
					title : {
						text : '',
						style : {
							color : Highcharts.getOptions().colors[0]
						}
					},
					labels : {
						format : '{value} 位',
						style : {
							color : Highcharts.getOptions().colors[0]
						}
					},
					opposite : true
				} ],
				tooltip : {
					shared : true
				},
				series : []
			});
});
var data1;
var data2;
var data3;
function GetReportData() {
	var chart1 = $('#container1').highcharts();
	var chart2 = $('#container2').highcharts();
	var date = $('#datepicker').val();
	if (date != null && date.length > 0) {
		$.ajax({
			url : contextPath + '/DayReportJSONServlet',
			type : 'POST',
			dataType : 'JSON',
			data : {
				date : date
			},
			success : function(result) {
				data1 = result.sumCustNumByhour;
				data2 = result.avgPriceDividedByCustNumByhour;
				data3 = result.mainkindName;
				chart1.setTitle(null, {
					text : date
				});
				chart1.xAxis[0].setCategories(data3);
				chart2.setTitle(null, {
					text : date
				});
				chart2.addSeries({
					name : '來客數 ',
					type : 'column',
					yAxis : 1,
					data : data1,
					tooltip : {
						valueSuffix : ' 位'
					}
				});
				chart2.addSeries({
					name : '平均消費金額 ',
					type : 'spline',
					data : data2,
					tooltip : {
						valueSuffix : ' 元'
					}
				});
			},
			cache : false
		});
	}
}