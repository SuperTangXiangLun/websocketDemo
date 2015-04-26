$(document).ready(function(){
	//日历初始化
	var date = new Date();
	var mapping = [];
	Calendar.Init(null, mapping);
	Calendar.RenderCalendar("calendar", date.getMonth() + 1, date.getFullYear());
	
	//事件绑定
	function eventBind()
	{
		//假日编辑事件绑定
		$('.h_calendar td').click(function(event){
			var tdSelf = this;
			//是否新增
			var isNew = true;
			//新增url
			var action = callcenter.basePath+"holiday/add.htm";
			//节假日id
			var data_id = "0";
			//假日信息div
			var $divHoliday = $(tdSelf).children(".holiday_con");
			if($divHoliday.length > 0)
			{
				isNew = false;
				data_id = $divHoliday.attr("data_id");
				action = callcenter.basePath+"holiday/edit.htm";
			}
			
			//弹窗
			$("#holiday_set").modal({
				title: isNew?"新增节假日":"编辑节假日",
				buttons: [
					{
						text: "确定",
						click: function (){
							$("#form").submit();
						}
					},
					{
						text: "取消",
						click: function (){
							$(this).close();
						}
					}
				],afterCreate:function(){
					var minDate = getMonthStartDate(date);
					var maxDate = getMonthEndDate(date);
					//初始化日历控件
					$('#startDate').datepicker({
						minDate: minDate,
						maxDate: maxDate
					});
					$('#endDate').datepicker({
						minDate: minDate,
						maxDate: maxDate
					});
					$('#startDate').val(dateConver(new Date()));
					$('#endDate').val(dateConver(new Date()));
					//颜色选择绑定
					$('.cho_color_tr .cho_color').click(function(){
						$('.cho_color_tr .cho_color').removeClass('choosed_color');
						$(this).addClass('choosed_color');
						var color = $(this).attr("color_id");
						$("#form input[name='color']").val(color);
					});
					
					$("#form").attr("action",action);
					if(isNew)
					{	
						var $firstColor = $('.cho_color_tr span:first');
						$firstColor.addClass("choosed_color");
						$("#form input[name='color']").val($firstColor.attr("color_id"));
						//新增
						$("#form").Form({
							success:function(data)
							{
								refreshUI(date);
								var status = data.status;
								if(status==1)
								{
									$("#holiday_set").close();
									alertui("添加成功!");
								}else
								{
									alertui("添加失败!");
								}
							}
						});
						
					}else
					{	
						//编辑
						$("#form").Form({
							url: callcenter.basePath+"holiday/"+data_id+"/view.htm",
							success: function (data)
							{
								refreshUI(date);
								if(data.status == 1)
								{
									$("#holiday_set").close();
									alertui("修改成功!");
								}else
								{
									alertui("修改失败!");
								}
							},
							afterLoad:function(data)
							{
								//初始化时间
								$('#startDate').val(dateConver(data.startDate));
								$('#endDate').val(dateConver(data.endDate));
								$('.cho_color_tr span[color_id="'+data.color+'"]').addClass("choosed_color");
							}
						});
					}
				}
			});

			
			//删除
			$('.sec_p').click(function(event){
				event.stopPropagation();
				var $holidaySpan = $(this).parents('.holiday_con');
				var data_id = $(this).parents('.holiday_con').attr("data_id");
				$.ajax({
					url:callcenter.basePath+"holiday/"+data_id+"/delete.htm",
					dataType:"json",
					success:function(data)
					{
						refreshUI(date);
						if(data.status == 1)
						{	
							$holidaySpan.remove();
							//alertui("删除成功");
						}else
						{
							alertui("删除失败");
						}
					}
				})
			});
		});
	}
	
	//高度调整
	function hightAdjust()
	{
		var Get_height = $('.mark1').height();
		var con_h = $(window).height();
		$('.mark2').css('height',con_h-Get_height-32);
		$('.mark3').css('height',con_h-Get_height-63);
	}
	
	//判断高度
	function Doclick(){
		$('.pre_month').click(function(){
			hightAdjust();
		});
		$('.nex_month').click(function(){
			hightAdjust();
		});
	};
	
	//上一月追加
	Calendar.prevMonthFn = function(year,month)
	{
		date = new Date(year,month-1,1);
		refreshUI(date);
	};
	//下一月追加
	Calendar.nextMonthFn = function(year,month)
	{
		date = new Date(year,month-1,1);
		refreshUI(date);
		
	};
	
	//获取假日数据
	function getHoildayData(date)
	{
		var hoildayData;
		$.ajax({
			url:callcenter.basePath+"holiday/list.htm",
			type:"post",
			data:{date:date},
			async: false,
			dataType:"json",
			success:function(data)
			{
				grid.load(data.Data)
				hoildayData = data;
			}
		});
		return hoildayData;
	}
	
	//渲染数据
	function rendering (holidayData){
		var array = holidayData.Data
		if(array.length == 0)
		{
			return ;
		}
		var i = 0;
		$("#calendar table tr td").not(".prevMonth,.nextMonth").each(function(index){
			if(i >= array.length)
			{
				return true;
			}
			
			var holiday = array[i];
			var datestr = holiday.dateTime;
			var date = new Date(datestr);
			var val = index+1;
			if(val == date.getDate())
			{
				$(this).html(val+"<div data_id ='"+holiday.id+"' class='holiday_con'><p class='fir_p'>"+holiday.name+"</p><p class='sec_p'>X</p></div>");
				i++;
			}
		})
	}
	
	//表格初始化
	var grid = $("#_grid").Grid();
	
	//刷新UI
	function refreshUI(date)
	{
		
		var hoildayData = getHoildayData(date);
		rendering(hoildayData);
		Doclick();
		eventBind();
	}
	
	refreshUI(date);
	hightAdjust();
});

