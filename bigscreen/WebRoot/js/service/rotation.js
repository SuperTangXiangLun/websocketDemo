	$(document).ready(function() {
		//初始化布局高度
		var con_h = $(window).height();
		$('.mark2').css('height', con_h - 240 - 72);
		$('.mark3').css('height', con_h - 240 - 103 - 35);
		$(window).resize(function() {
			var con_h = $(window).height();
			$('.mark2').css('height', con_h - 240 - 72);
			$('.mark3').css('height', con_h - 240 - 103 - 35);
		})
			
		//日历初始化
		var date = new Date();
		var mapping = [];
		Calendar.Init(null, mapping);
		Calendar.RenderCalendar("calendar", date.getMonth() + 1,date.getFullYear());
		
		
		var calendarShow = function(year,month)
		{
			var dateStr = $("#searchForm input[name='startDate']").val();
			var selectedDate = new Date(dateStr);
			var selectedyear = selectedDate.getFullYear();
			var selectedMonth = selectedDate.getMonth();
			var selectedDay = selectedDate.getDate();
			//上一页
			var prevMonth = month-1;
			if(month == 1)
			{
				prevMonth = 12;
			}
			
			//上一页
			var nextMonth = month +1;
			if(month == 12)
			{
				nextMonth = 1;
			}
			
			var $record;
			if(prevMonth == selectedMonth)
			{
				var prevMonthDay = parseInt($("#calendar table tr td.prevMonth:first").html());
				if(prevMonthDay > selectedDay)
				{
					return ;
				}
				
				$record = $("#calendar table tr td.prevMonth");
			}else if(month == selectedMonth )
			{
				$record = $("#calendar table tr td").not(".prevMonth,.nextMonth")
			}else if(nextMonth == selectedMonth)
			{
				var nextMonthDay = parseInt($("#calendar table tr td.nextMonth:last").html());
				if(nextMonthDay < selectedDay)
				{
					return ;
				}
				
				$record = $("#calendar table tr td.nextMonth");
			}else
			{
				return ;
			}
			
			$record.each(function(index){
				var currentDay = parseInt($(this).html());
				if(currentDay == selectedDay)
				{
					$(this).addClass('on_td');
					return false;
				}
			})
			
		}
		
		//上一页自定义事件
		Calendar.prevMonthFn = calendarShow;
		//下一页自定义事件
		Calendar.nextMonthFn = calendarShow;
		Doclick();
		td_click();
		
		
		setSerachParams(date.getFullYear(),date.getMonth(),date.getDate())
		//查询参数设置
		function setSerachParams(year,month,day)
		{
			var startDate = dateFormat(new Date(year,month,day),"yyyy-MM-dd HH:mm:ss");
			var endDate = dateFormat(new Date(year,month,day,23,59,59),"yyyy-MM-dd HH:mm:ss");
			$("#searchForm input[name='startDate']").val(startDate);
			$("#searchForm input[name='endDate']").val(endDate);
		}
		
		//设置当前日期高亮
		$("#calendar table tr td").not(".prevMonth,.nextMonth").each(function(index){
			var val = index+1;
			if(date.getDate()==val)
			{
				$('.h_calendar td').removeClass('on_td');
				$(this).addClass('on_td');
			}
		})
		
		//弹出选择部门人员弹窗
		$('.alert_apart').click(function() {
			$("#starff_a_peo").modal({
				title : "选择部门人员",
				buttons : [ {
					text : "确定",
					click : function() {
						$(this).close();
					}
				}, {
					text : "取消",
					click : function() {
						$(this).close();
					}
				} ],
				afterCreate : function() {
					$(document).ready(function() {
						var data = [ {
							Id : 1,
							Icon : "/plugIn/css/img/1_open.png",
							Name : "张三",
							Child : null
						}, {
							Id : 2,
							Name : "李思",
							Child : [ {
								Id : 21,
								Name : "张三",
								Child : null
							}, {
								Id : 22,
								Name : "李思",
								Child : [ {
									Id : 221,
									Name : "王五",
									Child : null
								} ]
							}, {
								Id : 2200,
								Name : "李思",
								Child : [ {
									Id : 22100,
									Name : "王五",
									Child : null
								} ]
							}, {
								Id : 3,
								Name : "王五",
								Child : null
							} ]
						}, {
							Id : 31,
							Name : "王五",
							Child : [ {
								Id : 311,
								Name : "张三",
								Child : null
							}, {
								Id : 312,
								Name : "李思",
								Child : null
							}, {
								Id : 313,
								Name : "王五",
								Icon : "/plugIn/css/img/5.png",
								Child : [ {
									Id : 3131,
									Name : "王五",
									Child : null
								}, {
									Id : 3131,
									Name : "王五1",
									Child : null
								} ]
							} ]
						} ];
						var app = $("#div_tree_box").Tree({
							data : data,
							expand : true,
							treeModel : "checkbox",
							key : "Id",
							text : "Name",
							multiple : true
						});
					});
				}
			})
		})
		//
		$('.rot_man_alert tr').click(function() {
			$('.rot_man_alert tr').removeClass('checked_tr');
			$(this).addClass('checked_tr');
			$("#rotation_information").modal({
				title : "轮岗详情",
				buttons : [ {
					text : "确定",
					click : function() {
						$(this).close();
					}
				}, {
					text : "取消",
					click : function() {
						$(this).close();
					}
				} ]
			})
		})
			
		//日历td的单击事件
		function td_click() {
			$('.h_calendar td').click(function() {
				$('.h_calendar td').removeClass('on_td');
				$(this).addClass('on_td');
				setSerachParams(Calendar.year,Calendar.month,$(this).html());
				$("#searchForm").submit();
			});
		};
			
		//判断高度
		function Doclick() {
			$('.pre_month').click(function() {
				var Get_height = $('.mark1').height();
				var con_h = $(window).height();
				$('.mark2').css('height', con_h - Get_height - 72);
				$('.mark3').css('height', con_h - Get_height - 103 - 35);
				Doclick();
				td_click();
			});
			
			$('.nex_month').click(function() {
				var Get_height = $('.mark1').height();
				var con_h = $(window).height();
				$('.mark2').css('height', con_h - Get_height - 72);
				$('.mark3').css('height', con_h - Get_height - 103 - 35);
				Doclick();
				td_click();
			});
		};
			
		//grid
		var grid = $("#table_box").Grid({
			url : callcenter.basePath+"rotation/list.htm",
			method:"post",
			putJson: false,
			cache : false,
			page : {
				pageSize : 10,
				reqindex : "pageIndex",
				reqsize : "pageSize"
			}
		});
		
		//图表初始化
		$.init({
			'echarts' : callcenter.basePath+'plugIn/charts/echarts',
			'echarts/chart/bar' : callcenter.basePath+'plugIn/charts/echarts-map',
			'echarts/chart/line' : callcenter.basePath+'plugIn/charts/echarts-map',
			'echarts/chart/pie' : callcenter.basePath+'plugIn/charts/echarts-map',
			'echarts/chart/radar' : callcenter.basePath+'plugIn/charts/echarts-map'
		});
		
		//图表数据
		$.ajax({
			url : callcenter.basePath+"rotation/chart.htm",
			type:"post",
			data:{date:$("#searchForm input[name='startDate']").val()},
			dataType:"json",
			success:function(data)
			{
				var category = new Array();
				var values = new Array();
				$.each(data,function(index,value){
					category.push(value.name);
					values.push(value.totalNum);
				})
				
				var data = {
					category : category,
					values : [ {
						value : values
					}]
				};
				
			$("#divbarntag").bar({
				data : data,
				showtag : false
			});
			}
		})
			
	})