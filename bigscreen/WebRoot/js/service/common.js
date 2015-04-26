Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	}
	return fmt;
};

/**
 * 时间转换
 * @param obj
 * @returns {String}
 */
function dateConver(obj){
	if (obj==null){
		return obj;
	}
	return new Date(obj).Format("yyyy-MM-dd");
	
}

/**
 * 时间转换
 * @param obj
 * @returns {String}
 */
function dateFormat(obj,patten){
	if (obj==null){
		return obj;
	}
	return new Date(obj).Format(patten);
	
}

// 获得某月的天数
function getMonthDays(myYear,myMonth) {
	var monthStartDate = new Date(myYear, myMonth, 1);
	var monthEndDate = new Date(myYear, myMonth + 1, 1);
	var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
	return days;
}

// 获得日期当月开始日期
function getMonthStartDate(date){
	
	var monthStartDate = new Date(date.getFullYear(),date.getMonth(), 1);
	return monthStartDate.Format("yyyy-MM-dd");
}

//获得日期当月的结束日期     
function getMonthEndDate(date){
	var myYear = date.getFullYear();
	var myMonth = date.getMonth();
	var monthEndDate = new Date(myYear,myMonth, getMonthDays(myYear,myMonth));
	return monthEndDate.Format("yyyy-MM-dd");
}