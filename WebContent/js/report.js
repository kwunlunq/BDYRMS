var chart1;
var chart2;
$(function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true
	});
	$("#tabs").tabs();
	chart1 = $('#container1').highcharts({
		chart : {
			type : 'bar'
		},
		title : {
			text : '主餐銷售量'
		},
		subtitle : {
			text : '2013-03-02'
		},
		xAxis : {
			categories : [ '牛排', '披薩', '義大利麵', '燉飯' ],
			title : {
				text : null
			}
		},
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
	chart2 = $('#container2').highcharts(
			{
				chart : {
					zoomType : 'xy',
					events : {
					// 圖表載入後執行GetReportData這個Function
					load : GetReportData
					}
				},
				title : {
					text : '平均消費金額/來客數 分佈'
				},
				subtitle : {
					text : '2013-03-02'
				},
				xAxis : [ {
					categories : [ '8-9', '9-10', '10-11', '11-12', '12-13',
							'13-14', '14-15', '15-16', '16-17', '17-18',
							'18-19', '19-20', '20-21', '21-22', '22-23',
							'23-24' ]
				} ],
				yAxis : [ { // Primary yAxis
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
				}, { // Secondary yAxis
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
				series : [
						{
							name : '來客數',
							type : 'column',
							yAxis : 1,
							data : [],
							tooltip : {
								valueSuffix : ' 位'
							}

						},
						{
							name : '平均消費金額',
							type : 'spline',
							data : [],
							tooltip : {
								valueSuffix : '元'
							}
						} ]
			});
});
function GetReportData() {
	$.ajax({
		url : '${pageContext.request.contextPath}/con.bdy.controller/ReportServlet',
		type : 'GET',
		dataType: 'JSON',
		// 成功之後，會收到Server端返回的資料，也就是自訂的型別ReportData
		// 有兩個屬性可以用
		success : function(result) {
			chart2.series[0].setData(result.sumCustNumByhour);
			chart2.series[1].setData(result.avgPriceDividedByCustNumByhour);
		},
		cache : false
	});
}