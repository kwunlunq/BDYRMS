$(function() {
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$("#datepicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true
	});
	$("#tabs").tabs();
	$('#container')
			.highcharts(
					{
						chart : {
							zoomType : 'xy'
						},
						title : {
							text : '平均消費金額/來客數 分佈'
						},
						subtitle : {
							text : '2013-03-02'
						},
						xAxis : [ {
							categories : [ '8-9', '9-10', '10-11', '11-12', '12-13', '13-14',
									'14-15', '15-16', '16-17', '17-18', '18-19', '19-20', '20-21',
									'21-22', '22-23', '23-24' ]
						} ],
						yAxis : [ { // Primary yAxis
								min:0,
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
									data : [ 0, 20, 30, 50, 55, 60, 20, 10, 30,
											40, 49, 33, 22, 60, 49, 0 ],
									tooltip : {
										valueSuffix : ' 位'
									}

								},
								{	
									name : '平均消費金額',
									type : 'spline',
									data : [ 0, 300, 400, 450, 500, 700, 800,
											300, 600, 670, 700, 580, 600, 300,
											500, 0 ],
									tooltip : {
										valueSuffix : '元'
									}
								} ]
					});
});