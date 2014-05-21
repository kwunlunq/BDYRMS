$(function() {
	$("#monthReportTabs").tabs();
	$("#monthMealsCount").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#monthMealsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$('#monthOperate').highcharts({
		chart : {
			zoomType : 'xy',
			height: 500,
			width : 960,
		},
		title : {
			text : '單月營收/單月來客數 統計'
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
	for (var i = 0; i < ids.length; i++) {
		if (i == ids.length - 1) {
			$('#mealsCount-' + ids[i]).highcharts({
				chart : {
					type: 'column',
					height: 460,
					width : 700,
					events : {
						load : GetReportData
					}
				},
				title : {
					margin:50
				},
				subtitle : {
					y:40
				},
				xAxis : {},
				yAxis : {
					min : 0,
					title : {
						text : '',
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
				series : []
			});
		} else {
			$('#mealsCount-' + ids[i]).highcharts({
				chart : {
					type: 'column',
					height: 460,
					width : 700,
				},
				title : {
					margin:50
				},
				subtitle : {
					y:40
				},
				xAxis : {},
				yAxis : {
					min : 0,
					title : {
						text : '',
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
				series : []
			});
		}
	};
});
var numData;
var priceData;
var daydata;
var foodkindName;
var mealsCount = [];
var foodAmountDate = [];
var foodNameDate =[];
function GetReportData() {
	var monthOperateChart = $('#monthOperate').highcharts();
	var year = $('#year').val();
	var month = $('#month').val();
	for(var i=0;i<ids.length;i++){
		mealsCount[ids[i]] = $('#mealsCount-'+ids[i]).highcharts();
	}
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
				foodkindName = result.foodkindName;
				for(var i=0;i<ids.length;i++){
					foodAmountDate[ids[i]] = result[ids[i]][0].foodAmount;
					foodNameDate[ids[i]] = result[ids[i]][0].foodName;
					mealsCount[ids[i]].xAxis[0].setCategories(foodNameDate[ids[i]]);
					mealsCount[ids[i]].setTitle({ text: foodkindName[i] + "類" });
					mealsCount[ids[i]].setTitle(null, {
						text : year + '年' + month + '月'
					});
					mealsCount[ids[i]].addSeries({
						name : '數量',
						data : foodAmountDate[ids[i]],
					});
				}
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
