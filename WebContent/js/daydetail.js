$(function() {
	setTableToMaxStyle("bills");
	$("#billOrderDialog").dialog({
		autoOpen : false,
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "explode",
			duration : 200
		},
		maxHeight : 600,
		minHeight : 450,
		maxWidth : 450,
		minWidth : 450,
		title : "點菜單明細"
	});
	$("body").on("click", "#showdetail", function() {
		var billId = $(this).attr("billId");
		$("#billOrderDialog").dialog("open");
		$.ajax({
			url : contextPath + '/report/OrderDetailJSONServlet',
			type : 'POST',
			data : {
				"billId" : billId
			},
			dataType : 'json',
			success : function(orderdetails) {
				var odId = orderdetails.orderId;
				var foodName = orderdetails.foodName;
				var foodPrice = orderdetails.foodPrice;
				var foodSet = orderdetails.foodSet;
				var foodAddMoney = orderdetails.foodAddMoney;
				$('#billOrderDialog').empty();
				var table = $("<table></table>");
				var tr = $("<tr></tr>");
				var th = $("<th></th>");
				$(th).append("單號");
				$(tr).append(th);
				var th = $("<th></th>");
				$(th).append("品名");
				$(tr).append(th);
				var th = $("<th></th>");
				$(th).append("單價");
				$(tr).append(th);
				var th = $("<th></th>");
				$(th).append("類別");
				$(tr).append(th);
				var th = $("<th></th>");
				$(th).append("差額");
				$(tr).append(th);
				$(table).append(tr);
				for ( var i = 0; i < foodName.length; i++) {
					var tr = $("<tr></tr>");
					var td = $("<td></td>");
					$(td).append(odId[i]);
					$(tr).append(td);
					var td = $("<td></td>");
					$(td).append(foodName[i]);
					$(tr).append(td);
					var td = $("<td></td>");
					$(td).append(foodPrice[i]);
					$(tr).append(td);
					var td = $("<td></td>");
					$(td).append(foodSet[i]);
					$(tr).append(td);
					var td = $("<td></td>");
					$(td).append(foodAddMoney[i]);
					$(tr).append(td);
					$(table).append(tr);
				};
				$(table).attr("style", "margin: 0 auto");
				$('#billOrderDialog').append(table);
			}
		});
	});
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		showAnim:"slideDown",
		showButtonPanel: true,
		beforeShow : function(input) {
			$(input).css({
				"position" : "relative",
				"z-index" : 99
			});
		}
	});
	$("#dayReportTabs").tabs();
	$("#dayMainsCount").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#dayMainsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$("#dayMealsCount").tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
	$("#dayMealsCount li").removeClass("ui-corner-top").addClass(
			"ui-corner-left");
	$('#dayOperate').highcharts(
			{
				chart : {
					zoomType : 'xy',
					height : 500,
					width : 960,
				},
				title : {
					text : '平均消費金額/來客數 統計',
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
var mainkindName;
var foodkindName;
var mainsCount = [];
var mealsCount = [];
var foodAmountDate = [];
var foodNameDate = [];
function GetReportData() {
	var dayOperateChart = $('#dayOperate').highcharts();
	var date = $('#datepicker').val();
	for ( var j = 0; j < mainId.length; j++) {
		mainsCount[mainId[j]] = $('#mainsCount-' + mainId[j]).highcharts();
	}
	for ( var i = 0; i < mealId.length; i++) {
		mealsCount[mealId[i]] = $('#mealsCount-' + mealId[i]).highcharts();
	}
	if (date != null && date.length > 0) {
		$
				.ajax({
					url : contextPath + '/report/DayReportJSONServlet',
					type : 'POST',
					dataType : 'JSON',
					data : {
						date : date
					},
					success : function(result) {
						numData = result.sumCustNumByhour;
						priceData = result.avgPriceDividedByCustNumByhour;
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
								text : date
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
								text : date
							});
							mealsCount[mealId[i]].addSeries({
								name : '數量',
								data : foodAmountDate,
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