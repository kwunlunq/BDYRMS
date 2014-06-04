$(function() {
	var d = new Date();
	var thisYear = d.getFullYear();
	for ( var i = 2010; i <= thisYear; i++) {
		var opt;
		if (selectYear == i) {
			opt = '<option selected="selected" value="' + i + '" selected="selected">' + i
			+ '</option>';
		} else {
			opt = '<option value="' + i + '" >' + i
					+ '</option>';
		}

		$("select[id=year]").append(opt);
	}
	for ( var i = 1; i <= 12; i++) {
		var opt;
		if (selectMonth == i) {
			opt = '<option selected="selected" value="' + i + '" selected="selected">' + i
			+ '</option>';
		} else {
			opt = '<option value="' + i + '" >' + i
					+ '</option>';
		}
		$("select[id=month]").append(opt);
	}
	setTableToMaxStyle("bills");
	$("#monthReportTabs").tabs();
	$("#monthMainsCount").tabs()
			.addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#monthMainsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$("#monthMealsCount").tabs()
			.addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#monthMealsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$('#monthOperate').highcharts({
		chart : {
			zoomType : 'xy',
			height : 500,
			width : 960,
		},
		title : {
			text : '單月營收/單月來客數 統計',
			style : {
				"color": "#333333",
				"fontSize": "20px",
				"fontWeight": 'bold'
			}
		},
		subtitle : {
			text : '',
			style : {
				"color": "#008866",
				"fontSize": "16px",
			}
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
	for ( var j = 0; j < mainId.length; j++) {
		$('#mainsCount-' + mainId[j]).highcharts({
			chart : {
				type : 'column',
				height : 460,
				width : 700,
			},
			title : {
				margin : 50,
				style : {
					"color": "#333333",
					"fontSize": "20px",
					"fontWeight": 'bold'
				}
			},
			subtitle : {
				y : 40,
				style : {
					"color": "#008866",
					"fontSize": "16px",
				}
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
	for ( var i = 0; i < mealId.length; i++) {
		if (i == mealId.length - 1) {
			$('#mealsCount-' + mealId[i]).highcharts({
				chart : {
					type : 'column',
					height : 460,
					width : 700,
					events : {
						load : GetReportData
					}
				},
				title : {
					margin : 50,
					style : {
						"color": "#333333",
						"fontSize": "20px",
						"fontWeight": 'bold'
					}
				},
				subtitle : {
					y : 40,
					style : {
						"color": "#008866",
						"fontSize": "16px",
					}
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
			$('#mealsCount-' + mealId[i]).highcharts({
				chart : {
					type : 'column',
					height : 460,
					width : 700,
				},
				title : {
					margin : 50,
					style : {
						"color": "#333333",
						"fontSize": "20px",
						"fontWeight": 'bold'
					}
				},
				subtitle : {
					y : 40,
					style : {
						"color": "#008866",
						"fontSize": "16px",
						
					}
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
	}
	;
	hideLoading();
});
var numData;
var priceData;
var daydata;
var mainkindName;
var foodkindName;
var mainsCount = [];
var mealsCount = [];
var foodAmountDate = [];
var foodNameDate = [];
function GetReportData() {
	var monthOperateChart = $('#monthOperate').highcharts();
	var year = $('#year').val();
	var month = $('#month').val();
	for ( var j = 0; j < mainId.length; j++) {
		mainsCount[mainId[j]] = $('#mainsCount-' + mainId[j]).highcharts();
	}
	for ( var i = 0; i < mealId.length; i++) {
		mealsCount[mealId[i]] = $('#mealsCount-' + mealId[i]).highcharts();
	}
	if (year != null && year.length > 0 && month != null && month.length > 0) {
		$
				.ajax({
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
						mainkindName = result.mainkindNames;
						foodkindName = result.foodkindNames;
						for ( var j = 0; j < mainId.length; j++) {
							foodNameDate = result.mainkind[0].mainkindName[j].foodName;
							foodAmountDate = result.mainkind[0].mainkindName[j].foodAmount;
							mainsCount[mainId[j]].xAxis[0]
									.setCategories(foodNameDate);
							mainsCount[mainId[j]].setTitle({
								text : mainkindName[j] + "類"
							});
							mainsCount[mainId[j]].setTitle(null, {
								text : year + "年" + month + "月"
							});
							mainsCount[mainId[j]].addSeries({
								name : '數量',
								data : foodAmountDate,
							});
						}
						for ( var i = 0; i < mealId.length; i++) {
							foodNameDate = result.foodkind[0].foodkindName[i].foodName;
							foodAmountDate = result.foodkind[0].foodkindName[i].foodAmount;
							mealsCount[mealId[i]].xAxis[0]
									.setCategories(foodNameDate);
							mealsCount[mealId[i]].setTitle({
								text : foodkindName[i] + "類"
							});
							mealsCount[mealId[i]].setTitle(null, {
								text : year + "年" + month + "月"
							});
							mealsCount[mealId[i]].addSeries({
								name : '數量',
								data : foodAmountDate,
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
