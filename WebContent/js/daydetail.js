$(function() {
	$("#billOrderDialog").dialog({
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
	$("body").on("click", "#showdetail", function() {
		alert($(this).attr("billId"));
		$("#billOrderDialog").dialog("open");
	});
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true
	});
	$("#dayReportTabs").tabs();
	$("#dayMealsCount").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#dayMealsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$('#dayOperate').highcharts(
			{
				chart : {
					zoomType : 'xy',
					height: 500,
					width : 960,
				},
				title : {
					text : '平均消費金額/來客數 統計'
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
var foodkindName;
var mealsCount = [];
var foodAmountDate = [];
var foodNameDate =[];
function GetReportData() {
	var dayOperateChart = $('#dayOperate').highcharts();
	var date = $('#datepicker').val();
	for(var i=0;i<ids.length;i++){
		mealsCount[ids[i]] = $('#mealsCount-'+ids[i]).highcharts();
	}
	if (date != null && date.length > 0) {
		$.ajax({
			url : contextPath + '/report/DayReportJSONServlet',
			type : 'POST',
			dataType : 'JSON',
			data : {
				date : date
			},
			success : function(result) {
				numData = result.sumCustNumByhour;
				priceData = result.avgPriceDividedByCustNumByhour;
				foodkindName = result.foodkindName;
				for(var i=0;i<ids.length;i++){
					foodAmountDate[ids[i]] = result[ids[i]][0].foodAmount;
					foodNameDate[ids[i]] = result[ids[i]][0].foodName;
					mealsCount[ids[i]].xAxis[0].setCategories(foodNameDate[ids[i]]);
					mealsCount[ids[i]].setTitle({ text: foodkindName[i] + "類" });
					mealsCount[ids[i]].setTitle(null, {
						text : date
					});
					mealsCount[ids[i]].addSeries({
						name : '數量',
						data : foodAmountDate[ids[i]],
					});
				}
				dayOperateChart.setTitle(null, {
					text : date
				});
				dayOperateChart.addSeries({
					name : '來客數 ',
					type : 'column',
					yAxis : 1,
					data : numData,
					tooltip : {
						valueSuffix : ' 位'
					}
				});
				dayOperateChart.addSeries({
					name : '平均消費金額 ',
					type : 'spline',
					data : priceData,
					tooltip : {
						valueSuffix : ' 元'
					}
				});
			},
			cache : false
		});
	}
}