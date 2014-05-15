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
	$("body").on("click", "#odId", function() {
		alert($(this).text());
		$("#billOrderDialog").dialog("open");
	});
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true
	});
	$("#dayReportTabs").tabs();
	$( "#dayMealsCount" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
    $( "#dayMealsCount li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
	$('#dayOperate').highcharts(
			{
				chart : {
					zoomType : 'xy',
					width : 600,
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
	$('#mainMealCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
		},
		title : {
			text : '主餐銷售量 統計'
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
	$('#appetizerCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
		},
		title : {
			text : '開胃菜銷售量 統計'
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
			data : [ 20 ]
		} ]
	});
	$('#soupCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
		},
		title : {
			text : '湯品銷售量 統計'
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
			data : [ 17, 35]
		} ]
	});
	$('#drinkCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
		},
		title : {
			text : '飲料銷售量 統計'
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
	$('#dessertCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
		},
		title : {
			text : '甜點銷售量 統計'
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
	$('#saladCount').highcharts({
		chart : {
			type : 'bar',
			width : 600,
			events : {
				load : GetReportData
			}
		},
		title : {
			text : '沙拉銷售量 統計'
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
			data : [ 20, 35, 23 ]
		} ]
	});
});
var numData;
var priceData;
var mainKindNameData;
var appetizerNameData;
var soupNameData;
var saladNameData;
function GetReportData() {
	var dayOperateChart = $('#dayOperate').highcharts();
	var mainMealChart = $('#mainMealCount').highcharts();
	var appetizerChart = $('#appetizerCount').highcharts();
	var soupChart = $('#soupCount').highcharts();
	var saladChart = $('#saladCount').highcharts();
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
				numData = result.sumCustNumByhour;
				priceData = result.avgPriceDividedByCustNumByhour;
				mainKindNameData = result.mainkindName;
				appetizerNameData = result.appetizerName;
				soupNameData = result.soupName;
				saladNameData = result.saladName;
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
				mainMealChart.setTitle(null, {
					text : date
				});
				mainMealChart.xAxis[0].setCategories(mainKindNameData);
				appetizerChart.setTitle(null, {
					text : date
				});
				appetizerChart.xAxis[0].setCategories(appetizerNameData);
				soupChart.setTitle(null, {
					text : date
				});
				soupChart.xAxis[0].setCategories(soupNameData);
				saladChart.setTitle(null, {
					text : date
				});
				saladChart.xAxis[0].setCategories(saladNameData);
			},
			cache : false
		});
	}
}