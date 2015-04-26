var dataSaveArray = new Array();
var dataArrayTemp = new Array();
var currentDate;
var refreshChartsCount = 0;
var dataObj;
var dataHwl = [ 0 ];// 话务量数组
var dataYuQi = [ 0 ];// 预期值
var dataFwsp = [ 0 ];// 服务水平
var dataSjFwsp = [ 0 ];// 实际服务水平
var option1 = {};
var ws = new WebSocket("ws://" + callcenter.webProject
		+ "/bigScreenServer?bigScreenServer");
$(function() {
	dingshiqi();
	var iindex = 0;
	ws.onopen = function() {
		// document.getElementById("chatlog").textContent += "websocket
		// connected. \n";
	};
	ws.onmessage = function(message) {
		iindex++;
		var dateGroupAllArray = new Array();
		dateGroupAllArray.length = 0;
		dateGroupAllArray = openDataPackage(message.data);
		// dataSaveArray.push(dateGroupAllArray);
		foreachArray(dateGroupAllArray);
		// 接收完成 做完成确认
		sendMessageToServer();
	};
	ws.onclose = function() {
		// document.getElementById("chatlog").textContent += "websocket closed.
		// \n";
	}
});
// 遍历数组,展示数据
function foreachArray(dataGroup) {
	var begin = new Date();
	dataGroup.forEach(function(e) {
		dataHwl.length = [ 0 ];
		dataYuQi.length = [ 0 ];
		dataFwsp.length = [ 0 ];
		dataSjFwsp.length = [ 0 ];
		e.forEach(function(eo) {
			if (eo.length == 11) {// 上面半部分数据
				var dataType = eo[1];
				if (dataType == "ensemble") {// 总体
					$("#ensemblefwsp").html(eo[9] + "%");
					$("#ensembleJxl").html(eo[2]);
					$("#ensembleJtl").html(eo[3] + "%");
					$("#ensemblewaittime").html(eo[6] + "s");
					$("#ensemblewaitphone").html(eo[5]);
				} else if (dataType == "highend") {// 高端
					$("#highendfwsp").html(eo[9] + "%");
					$("#highendJxl").html(eo[2]);
					$("#highendJtl").html(eo[3] + "%");
					$("#highendwaittime").html(eo[6] + "s");
					$("#highendwaitphone").html(eo[5]);
				} else if (dataType == "platinum") {// 白金
					$("#platinumfwsp").html(eo[9] + "%");
					$("#platinumJxl").html(eo[2]);
					$("#platinumJtl").html(eo[3] + "%");
					$("#platinumwaittime").html(eo[6] + "s");
					$("#platinumwaitphone").html(eo[5]);
				} else if (dataType == "putin") {// 普金
				}
			} else {
				var splictArray=eo[0].split(",");
				$("#qrrs").html(splictArray[0]);
				$("#jtrs").html(splictArray[1]);
				$("#kxrs").html(splictArray[2]);
				$("#qtrs").html(splictArray[3]);
				$("#pjjtsc").html(splictArray[4]);
				$("#fangqiliang").html(splictArray[5]);
			}
		})
	})
	var end = new Date();
}
function getDate(strDate) {
	var date = eval('new Date('
			+ strDate.replace(/\d+(?=-[^-]+$)/, function(a) {
				return parseInt(a, 10) - 1;
			}).match(/\d+/g) + ')');
	return date;

}
function sendMessageToServer() {
	// 服务端发送消息客户端接受完做消息确认
	ws.send("dataGetEndOk");
}
function dingshiqi() {
	// 每隔30秒做一次date操作
	// window.setInterval("dateInit()", 10000);
}
function dateInit(currentDate) {

}
function refreshCharts() {
}
