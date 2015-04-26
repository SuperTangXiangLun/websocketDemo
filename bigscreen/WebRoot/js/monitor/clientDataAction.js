/**
 * websocket前端解包封装
 * 
 * @author sharktang
 */
// 解包方法
function openDataPackage(dataStringJs) {
	/*
	 * var dataStringJs = "userName1,女,这是第1个用户 &userName3," + "女,这是第3个用户
	 * $女,这是第1个用户 &男,这是第2个用户 &女,这是第3个用户 " + "$女,这是第1个用户 &男,这是第2个用户 &女,这是第3个用户
	 * &女,这是第4个用户 ";
	 */
	// var dataStringJs =
	// "userName1,女,这是第1个用户&userName2,女,这是第3个用户&userName3,女,这是第3个用户 ";
	var dateGroupAllArray = new Array();
	//var dataStringJs = "userName1,女,这是第1个用户";
	// 先判断 $是否存在
	if (dataIsHas(dataStringJs, "$")) {
		// 存在
		var dataGroup = dataStringJs.split("$");
		// alert(dataGroup.length);数据组大小
		// 遍历数据组
		var dateGroupArray = new Array();
		dataGroup.forEach(function(e) {
			if (dataIsHas(e, "&")) {
				var dataRow = e.split("&");
				var dateRowArray = new Array();// 每行数据存入数组
				// dateRowArray.push(dataRow);
				var dateRowAllArray = new Array();
				dataRow.forEach(function(er) {
					// 每次遍历行时，把每行数据保存到dateRowAllArray
					// dateRowAllArray.push(er);
					if (dataIsHas(er, ",")) {
						var dateCellArray = new Array();// 每列数据存入数组
						var dataCell = er.split(",");
						// 拿到对象
						var i = 0;
						dataCell.forEach(function(da) {
							// alert(da);//得到单个字符串
							dateCellArray[i] = da;
							i++;
						})
						// alert(dateCellArray);
						// 对象循环完成
						dateRowAllArray.push(dateCellArray);
					}else{
						var dataRowNull = e.split("&");
						var dateCellArray = new Array();// 每列数据存入数组
						// 拿到对象
						var i = 0;
						dataRowNull.forEach(function(das) {
							// alert(da);//得到单个字符串
							dateCellArray[i] = das;
							i++;
						})
						dateRowAllArray.push(dateCellArray);
					}
				})
				// 行循环完成
				dateGroupAllArray.push(dateRowAllArray);
			}else{
				var dateCellArray = new Array();// 每列数据存入数组
				dateCellArray.push(e);
				var dateRowAllArray = new Array();
				dateRowAllArray.push(dateCellArray);
				dateGroupAllArray.push(dateRowAllArray);
			}
		})

	} else {
		var dateRowAllArray = new Array();
		// 不存在数据组，判断是否存在&
		if (dataIsHas(dataStringJs, "&")) {
			// 存在
			var dataGroupYes = dataStringJs.split("&");
			// 遍历
			dataGroupYes.forEach(function(dy) {
				var dateCellArray = new Array();// 每列数据存入数组
				var dataRowYes = dy.split(",");
				// 拿到对象
				var i = 0;
				dataRowYes.forEach(function(da) {
					// alert(da);//得到单个字符串
					dateCellArray[i] = da;
					i++;
				})
				// alert(dateCellArray);
				// 对象循环完成
				dateRowAllArray.push(dateCellArray);
			})
		} else {
			// 不存判断是否存在,不存在不做处理
			if (dataIsHas(dataStringJs, ",")) {
				var dateCellArray = new Array();// 每列数据存入数组
				var dataRowNo = dataStringJs.split(",");
				var i = 0;
				dataRowNo.forEach(function(da) {
					// alert(da);//得到单个字符串
					dateCellArray[i] = da;
					i++;
				})
				// alert(dateCellArray);
				// 对象循环完成
				dateRowAllArray.push(dateCellArray);
			}
		}
		// 行循环完成
		dateGroupAllArray.push(dateRowAllArray);
	}
	return dateGroupAllArray;
}

/*
 * 判断dataVal中是否有str字符串
 */
function dataIsHas(dataVal, str) {
	if (dataVal.indexOf(str) > 0) {
		return true;
	} else {
		return false;
	}
}