var dataArrayTemp = new Array();
var chinaMapCity = new Array();
var optionChinaMap;
var currentDate;
var refreshChartsCount = 0;
var dataObj;
var dataHwl = [ 0 ];// 话务量数组
var dataYuQi = [ 0 ];// 预期值
var dataFwsp = [ 0 ];// 服务水平
var dataSjFwsp = [ 0 ];// 实际服务水平
var optionHwl = {};
var ws = new WebSocket("ws://" + callcenter.webProject
		+ "/bigScreenTwoServer?bigScreenTwoServer");
$(function() {
	chartInit();//初始化图表
	dingshiqi();//定时刷新
	ws.onopen = function() {
	};
	ws.onmessage = function(message) {
		var dateGroupAllArray = new Array();
		dateGroupAllArray.length = 0;
		dateGroupAllArray = openDataPackage(message.data);
		foreachArray(dateGroupAllArray);
		// 接收完成 做完成确认
		sendMessageToServer();
	};
	ws.onclose = function() { 
	}
});
function chinaMapFunction(){
	optionChinaMap = {
			tooltip : {
				trigger: 'item',
				formatter: '{b}'
			},
			series : [
				{
					name: '中国',
					type: 'map',
					mapType: 'china',
					selectedMode : 'multiple',
					itemStyle:{
						normal:{
							borderWidth:2,
							borderColor:'#275a9a',
							color: '#79c8ff',
							label:{show:true}
						},
						emphasis:{
							borderWidth:2,
							borderColor:'#275a9a',
							color: '#fb8e4c',
							label:{show:true}
						}
					},
					data: chinaMapCity
				}
			]
		};
}
// 遍历数组,展示数据
function foreachArray(dataGroup) {
	dataGroup
			.forEach(function(e) {
				dataHwl.length = [ 0 ];
				dataYuQi.length = [ 0 ];
				dataFwsp.length = [ 0 ];
				dataSjFwsp.length = [ 0 ];
				if (e.length == 5) {// 中国地图
					var chinaMapArray1= new Array(); 
					for(var kl=0;kl<5;kl++){
						 chinaMapArray1.push(e[kl]);
					}
					var chinaNameArrayData= new Array();  
					var city1="";
					var city2="";
					var city3="";
					var city4="";
					var city5="";
					for(var kl=0;kl<chinaMapArray1.length;kl++){
						var tempArray=chinaMapArray1[kl];
						 $("#sname"+kl).html(tempArray[0]);
						 $("#hwl"+kl).html(tempArray[1]);
						 $("#bfb"+kl).html(tempArray[2]+"%");
						 if(kl==0){
							 city1=tempArray[0];
						 }else if(kl==1){
							 city2=tempArray[0];
						 }else if(kl==2){
							 city3=tempArray[0];
						 }else if(kl==3){
							 city4=tempArray[0];
						 }else if(kl==4){
							 city5=tempArray[0];
						 }
					}
					chinaMapCity = [ {
						name : city1,
						selected : true
					}, {
						name : city2,
						selected : true
					}, {
						name : city3,
						selected : true
					}, {
						name : city4,
						selected : true
					}, {
						name : city5,
						selected : true
					} ];
					chinaMapFunction();
				    $("#graphic1").charts("map", optionChinaMap);
				}
				e.forEach(function(eo) {
				 if (eo.length == 10) {// 第一组
								var dataType = eo[0];// 技能线
								var isredArray = eo[9].split("*");
								if (dataType == "ensemble") {// 总体
									$("#ensembleJtl").html(eo[1]);
									$("#ensemblewaitphone").html(eo[4]);
									$("#ensemblewaittime").html(eo[5] + "s");
									$("#ensemblebackOutCount").html(eo[3]);
									$("#ensembleserviceHorizontal1")
											.html(eo[8]);
									$("#ensembleserviceHorizontal2").html(
											eo[8] + "%");
									$("#ensembleconnectRate1").html(eo[2]);
									$("#ensembleconnectRate2")
											.html(eo[2] + "%");
									var fwspnum = eo[8] * 3.6;
									var jtlnum = eo[2] * 3.6;
									if (isredArray[0] == 1) {
										$('#circlejtl').addClass(
												'over_some_color');
										$('.out_percent_numbers1 span').css(
												"color", '#f00');
									} else {
										$('#circlejtl').removeClass(
												'over_some_color');
										$('.out_percent_numbers1 span').css(
												'color', '#0f0');
									}
									if (isredArray[1] == 1) {
										$('#circlefwsp').addClass(
												'over_some_color');
										$('.out_percent_numbers2 span').css(
												'color', '#f00');
									} else {
										$('#circlefwsp').removeClass(
												'over_some_color');
										$('.out_percent_numbers2 span').css(
												'color', '#0f0');
									}
									setClass(fwspnum, "circlefwsp");
									setClass(jtlnum, "circlejtl");
								} else if (dataType == "highend") {// 高端
									$("#highendName").html("高端");
									$("#highendJxl").html(eo[1]);
									$("#highendfwsp").html(eo[8] + "%");
									$("#highendJtl").html(eo[2] + "%");
									$("#highendwaitphone").html(eo[4]);
									$("#highendwaittime").html(eo[5] + "s");
								} else if (dataType == "platinum") {// 白金
									$("#platinumName").html("白金");
									$("#platinumJxl").html(eo[1]);
									$("#platinumfwsp").html(eo[8] + "%");
									$("#platinumJtl").html(eo[2] + "%");
									$("#platinumwaitphone").html(eo[4]);
									$("#platinumwaittime").html(eo[5] + "s");
								} else if (dataType == "putin") {// 普金
									$("#putinName").html("普金");
									$("#putinJxl").html(eo[1]);
									$("#putinfwsp").html(eo[8] + "%");
									$("#putinJtl").html(eo[2] + "%");
									$("#putinwaitphone").html(eo[4]);
									$("#putinwaittime").html(eo[5] + "s");
								}
							} else if (eo.length == 7) {
								var resoureLine = eo[0];
								var skillLineCount = eo[1];
								var waitPhoneCount = eo[3];
								var waitTime = eo[4];
								var isredArray = eo[6].split("*");
								if ("588" == resoureLine) {
									$("#resNameFiveBaBa").html("588中心");
									$("#fivebabajxl").html(skillLineCount);
									$("#fivewaitPhoneCount").html(
											waitPhoneCount);
									$("#fivewaitTime").html(waitTime + "s");
									$("#fiveBabafwsp").html(eo[5] + "%");
									$("#fiveBabajtl").html(eo[2] + "%");
									setClass(eo[5] * 3.6, "fivebabacirclefwsp");
									setClass(eo[2] * 3.6, "fivebabacirclejtl");
									// 服务水平
									if (isredArray[0] == 1) {
										$('#fivebabacirclefwsp').addClass(
												'over_some_color');
										$('.fivebabaout_percent_numbers1 span')
												.css("color", '#f00');
									} else {
										$('#fivebabacirclefwsp').removeClass(
												'over_some_color');
										$('.fivebabaout_percent_numbers1 span')
												.css('color', '#0f0');
									}
									// 接通率
									if (isredArray[1] == 1) {
										$('#fivebabacirclejtl').addClass(
												'over_some_color');
										$('.fivebabaout_percent_numbers2 span')
												.css('color', '#f00');
									} else {
										$('#fivebabacirclejtl').removeClass(
												'over_some_color');
										$('.fivebabaout_percent_numbers2 span')
												.css('color', '#0f0');
									}
								} else {
									$("#resNamezd").html("中达中心");
									$("#zdjxl").html(skillLineCount);
									$("#zdwaitPhoneCount").html(waitPhoneCount);
									$("#zdwaitTime").html(waitTime + "s");
									$("#zdfwsp").html(eo[5] + "%");
									$("#zdjtl").html(eo[2] + "%");
									setClass(eo[5] * 3.6, "zdcirclefwsp");
									setClass(eo[2] * 3.6, "zdcirclejtl");
									// 服务水平
									if (isredArray[0] == 1) {
										$('#zdcirclefwsp').addClass(
												'over_some_color');
										$('.zdout_percent_numbers1 span').css(
												"color", '#f00');
									} else {
										$('#zdcirclefwsp').removeClass(
												'over_some_color');
										$('.zdout_percent_numbers1 span').css(
												'color', '#0f0');
									}
									// 接通率
									if (isredArray[1] == 1) {
										$('#zdcirclejtl').addClass(
												'over_some_color');
										$('.zdout_percent_numbers2 span').css(
												'color', '#f00');
									} else {
										$('#zdcirclejtl').removeClass(
												'over_some_color');
										$('.zdout_percent_numbers2 span').css(
												'color', '#0f0');
									}
								}
							} else if(eo.length==4){ 
								refreshChartsCount++;
								 dataHwl.push(eo[0]);// 话务量数组
								 dataFwsp.push(eo[1]);// 服务水平
								 dataYuQi.push(eo[3]);// 预期值
								 if(refreshChartsCount==10){
									 refreshCharts();
								 }
								 if(refreshChartsCount==10){
									   refreshChartsCount=0;
										dataHwl.length = [ 0 ];
										dataFwsp.length = [ 0 ];
										dataYuQi.length = [ 0 ];
								 }
								
							}else if (eo.length == 1) {
								var exArray = eo[0].split(",");
								$("#aprs").html(exArray[0]);
								$("#qrrs").html(exArray[1]);
								$("#kxrs").html(exArray[2]);
								$("#zxrs").html(exArray[3]);
								$("#jtrs").html(exArray[4]);
								$("#zxjcrs").html(exArray[5]);
								$("#zxpxrs").html(exArray[6]);
								$("#zxwhrs").html(exArray[7]);
								$("#shclrs").html(exArray[8]);
								$("#xxrs").html(exArray[9]);
								$("#hengding1").html(exArray[10]);
								$("#hengding2").html(exArray[10]);
								$("#hengding3").html(exArray[10]);
								$("#hengding4").html(exArray[10]);
								$("#countSize").html(exArray[11]);
								var countSize=exArray[11];//总人数
								$(".bsl3_div2_div_2_ac_1").css("height",exArray[0]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_2").css("height",exArray[1]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_3").css("height",exArray[2]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_4").css("height",exArray[3]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_5").css("height",exArray[4]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_6").css("height",exArray[5]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_7").css("height",exArray[6]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_8").css("height",exArray[7]/countSize*300);//所占百分比
								$(".bsl3_div2_div_2_ac_9").css("height",exArray[8]/countSize*300);//所占百分比
								var cadasd=12;
							}
						})
			})
}
function setClass(num, classname) {
	if (num <= 180) {
		$('#' + classname).find('.pie_left_l').css('transform', "rotate(0deg)");
		$('#' + classname).find('.pie_right_r').css('transform',
				"rotate(" + num + "deg)");
	} else {
		$('#' + classname).find('.pie_right_r').css('transform',
				"rotate(180deg)");
		$('#' + classname).find('.pie_left_l').css('transform',
				"rotate(" + (num - 180) + "deg)");
	}
} 
function sendMessageToServer() {
	// 服务端发送消息客户端接受完做消息确认
	ws.send("dataGetEndOk");
}
function dingshiqi() {
	// 每隔5秒做一次数组清空操作
	//window.setInterval("arrayInit()", 5000);
}
function arrayInit() {
	if (refreshChartsCount > 48) {
		refreshChartsCount = 0;
		dataHwl.length = [ 0 ];
		dataFwsp.length = [ 0 ];
		dataYuQi.length = [ 0 ];
		/*$("#graphic").charts("bar", optionHwl, function(ec) {
			dataObj = ec;
		});*/
	}
	refreshCharts();
}
function refreshCharts() {
/*	dataFwsp=[2.0, 4.9, 7.0, 23.2, 25.6, 76.7];
	dataHwl=[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2];
	dataYuQi=[10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0];*/
	dataObj.setOption({
		series : [ {
			data : dataHwl
		}, {
			data : dataFwsp
		},  {
			data : dataYuQi
		} ]
	});
	dataObj.refresh();
}
function chartInit() {  
	optionHwl = {
			tooltip : {
				trigger: 'axis'
			},
			toolbox: {
				show : false
			},
			calculable : true,
			 legend: {
				show:false,
				data:['','']
			},
			xAxis : [
				{
					axisLine : {    // 轴线
						show: true,
						lineStyle: {
							color: '#fff',
							type: 'solid',
							width: 1
						}
					},
					type : 'category',
					axisLabel : {
						textStyle: {
							color: '#fff',
							fontSize: 18,
						}
					},
					data : ['0:00','1:00','2:00','3:00','4:00','5:00','6:00','7:00','8:00','9:00',
							'10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00',
							'20:00','21:00','22:00','23:00']
				}
			],
			yAxis : [
				{
					axisLine : {    // 轴线
						show: true,
						lineStyle: {
							color: '#fff',
							type: 'solid',
							width: 2
						}
					},
					type : 'value',
					name : '话务量',
					splitNumber: 15,
					splitLine : {
						show:false,
						lineStyle: {
							color: '#483d8b',
							type: 'dotted',
							width: 2
						}
					},
					axisLabel : {
						textStyle: {
							color: '#fff',
							fontSize: 12,
							fontWeight: 'normal'
						},
						formatter: '{value}'
					}
				},
				{
					axisLine : {    // 轴线
						show: true,
						lineStyle: {
							color: '#fff',
							type: 'solid',
							width: 2
						}
					},
					type : 'value',
					name : '服务水平',
					splitLine : {
						show:false,
						lineStyle: {
							color: '#483d8b',
							type: 'dotted',
							width: 2
						}
					},
					splitArea : {
						show: true,
						areaStyle:{
							color:['#79c8ff','#79c8ff']
						}
					},
					axisLabel : {
						textStyle: {
							color: '#fff',
							fontSize: 12,
							fontWeight: 'normal'
						},
						formatter: '{value}'
					}
				}
			],
			series : [
				{	
					name:'话务量',
					type:'bar',
					itemStyle:{
						normal:{
							color: '#f1f9fe'
						}
					},
					data:dataHwl,
				},
				{
					name:'服务水平',
					type:'line',
					itemStyle:{
						normal:{
							color: '#024482',
							lineStyle: {
								width:7,
								color:'#024482',
							}
						}
					},
					symbol: 'emptyCircle',
					symbolSize: 8,
					yAxisIndex: 1,
					data:dataFwsp,
				},
				{
					name:'指标',
					type:'line',
					itemStyle:{
						normal:{
							lineStyle: {
								width:6,
								color:'#f77766'
							}
						}
					},
					symbol: 'none',
					yAxisIndex: 1,
					data:dataYuQi,
				}
			]
		};
	$("#graphic").charts("bar", optionHwl, function(ec) {
		dataObj = ec;
	});
}
