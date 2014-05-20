$(function() {
	$("#tabs").tabs();
	$('#monthOperate').highcharts({
		chart : {
			zoomType : 'xy',
			width : 600,
			events:{
				load:GetReportData
			}
		},
		title : {
			text : '單日營收/單日來客數 統計'
		},
		subtitle : {
			text : ''
		},
		xAxis : [ {
			categories : []
		} ],
		yAxis : [ {
			min : 0,
			labels : {
				format : '{value} 元',
				style : {
					color : Highcharts.getOptions().colors[0]
				}
			},
			title : {
				text : '',
				style : {
					color : Highcharts.getOptions().colors[0]
				}
			}
		}, {
			labels : {
				format : '{value} 位',
				style : {
					color : Highcharts.getOptions().colors[1]
				}
			},
			title : {
				text : '',
				style : {
					color : Highcharts.getOptions().colors[1]
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
var numData;
var priceData;
var daydata;
function GetReportData() {
	var monthOperateChart = $('#monthOperate').highcharts();
	var year = $('#year').val();
	var month = $('#month').val();
	if (year != null && year.length > 0 && month != null && month.length > 0) {
		$.ajax({
			url : contextPath + '/report/MonthReportJSONServlet',
			type : 'POST',
			dataType : 'JSON',
			data : {
				year : year,
				month : month
			},
			success : function(result) {
				daydata = result.dayInMonth;
				numData = result.dayTatolCustNum;
				priceData = result.dayTatolFinPrice;
				monthOperateChart.setTitle(null, {
					text : year + '年' + month + '月'
				});
				monthOperateChart.addSeries({
					name : '單日來客數 ',
					type : 'column',
					yAxis : 1,
					data : numData,
					tooltip : {
						valueSuffix : ' 位'
					}
				});
				monthOperateChart.addSeries({
					name : '單日營收',
					type : 'spline',
					data : priceData,
					tooltip : {
						valueSuffix : ' 元'
					}
				});
				monthOperateChart.xAxis[0].setCategories(daydata);
			},
			cache : false
		});
	}
}
