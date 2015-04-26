$(document).ready(function(){
	var grid = $("#_grid").Grid({
			url: callcenter.basePath+"sourceline/list.htm",
			method:"POST",
			afterLoad:function()
			{
				init();
			}
		});
	
	//初始化
	function init()
	{
		//编辑
		$("#edit_self,#add_self").click(function (){
			var data_id = $(this).attr("data_id");
			var isNew = true;
			var action = callcenter.basePath+"sourceline/add.htm";
			if(data_id)
			{
				isNew = false;
				action = callcenter.basePath+"sourceline/edit.htm";
			}
			
			$("#idTemplate").modal({
				title: isNew?"新增":"编辑",
				afterCreate:function()
				{
					$("#skill_line_select").selectbox({
						url: callcenter.basePath+"skillline/all.htm"
					});
					$("#form").attr("action",action);
					if(isNew)
					{	
						
						//新增
						$("#form").Form({
							success:function(data)
							{
								var status = data.status;
								if(status==1)
								{
									grid.reload();
									$("#idTemplate").close();
									alertui("添加成功!");
								}else if(status==-2)
								{
									alertui("该资源线名字已存在!");
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
							url: callcenter.basePath+"sourceline/"+data_id+"/view.htm",
							success: function (data)
							{
								var status = data.status;
								if(status==1)
								{
									grid.reload();
									$("#idTemplate").close();
									alertui("修改成功!");
								}else if(status==-2)
								{
									alertui("该资源线名字已存在!");
								}else
								{
									alertui("添加失败!");
								}
							}
						});
					}
				},
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
				]
			});
		});
		
		//删除
		$("#single_del_self").click(function (){
			var data_id = $(this).attr("data_id");
			confirmui("是否删除？", function () {
				del(data_id);
			});
		});
		
		//多选删除
		$("#multiple_del_self").click(function (){
			confirmui("是否删除？", function () {
				var ids = "";
				$("input[type='checkbox']:checked").each(function(){ 
					ids+=$(this).attr("data_id")+','; 
				}); 
				if(ids == "")
				{
					alertui("您还没选择数据哦!");
				}else
				{
					ids += ids+"@0@";
					ids = ids.replace(",@0@","");
					del(ids);
				}
			});
		});
		
		//删除
		function del(params)
		{
			$.ajax({
				url:callcenter.basePath+"sourceline/delete.htm",
				type:"post",
				data:{id:params},
				dataType:"json",
				success:function(data)
				{
					if(data.status==1)
					{
						alertui("删除成功!");
					}else
					{
						alertui("删除失败!");
					}
					grid.refresh();
				}
			});
		}
	}
})