/*!
 * uScroll 滚动列表控件
 * 创建人：dingxinfa
 * 创建时间：2014/12/18
 */

(function ($) {
    $.fn.uScroll= function (opstions) {
        var ops = $.extend(true, {
            url: "",
            data: null,
            dataformat: {data:"Data",count:"Count"},
            method: "get",
            dataType: "json",
            putJson: true,
            cache: true,
            div:null,//滚动条容器
            bottomSpacing:10,//滚动条与底部距离px
            getDataButton:{//获取数据按钮
            	button:null,//按钮对象
            	endPlayStyle: "hide"//列表数据最终获取完成后按钮状态 ： hide  隐藏 | disabled  不可编辑| 其他  按钮不变
            },
            page:{
                pageSize: 30,
                pageIndex: 1,
                reqindex: "pageindex",
                reqsize: "pagesize",
                lazyUrl:null,
                lazyCount: null
            },
            afterInit:null,
            afterLoad: null,
            afterAppend:null
        }, opstions);



        var objthis = $(this);
        var thisid = $(objthis).attr("id");
        var template;
        if (!document.getElementById(thisid + "_template")) {
            template = findTemp(objthis, ops);
            $("body").append("<script type='text/template' id='" + thisid + "_template' >" + template + "</script>");
        } else {
            template = $("#" + thisid + "_template").html();
        }
        var tempbox = findTempBox(objthis);
        $(tempbox).empty();

        if(!ops.div){
            ops.div=objthis;
        }

        var pagecount = 0;
        var listcount = 0;
        var pageIndex = 1;
        var requesturl = "";
        var daval=null;
        var nextAppend=true;


        var func = {
            reload:function(opst){//重新新拉取数据并刷新列表（原有展示数据清理掉，列表展示第一分页数据）
            	if(opst instanceof Object){
                    ops.data=opst;
                } else {
                    ops.data = null;
                     ops.url=opst;
                 }
                pageIndex = 1;
                $(ops.div).scrollTop(0);
                $(tempbox).empty();
                loaddata(true);
            },
            load:function(opst){//拉取数据并附加显示在列表尾部，原有展示数据不清理
                if(opst instanceof Object){
                    ops.data=opst;
                } else {
                    ops.data = null;
                     ops.url=opst;
                 }
                pageIndex = 1;
                loaddata(false);
            },
            append: function () {//在列表底部附加新一页数据内容
            	append();
            },
        }

        loaddata(true);
        /**
         * 初始化请求
         * @method initRequest
         */
        function initRequest(){
            var args={};
            args[ops.page.reqindex]=((ops.page.pageIndex - 1) + pageIndex);
            args[ops.page.reqsize]=ops.page.pageSize;
            if (ops.url.indexOf('?')>-1) {
                var urlparam=ops.url.split('?')[1].split('&'); 
                var param,name,value;
                for(var i=0;i<urlparam.length;i++){
                    param=urlparam[i].split('=');
                    name=param[0];
                    value=param[1];
                    if(name=="")name="unkown";
                    if(typeof args[name]=="undefined"){ //参数尚不存在
                        args[name]=value;
                    }else if(typeof args[name]=="string"){ //参数已经存在则保存为数组
                        args[name]=[args[name]];
                        args[name].push(value);
                    }else{ //已经是数组的
                        args[name].push(value);
                    }
                }
                requesturl=ops.url.split('?')[0]; 
            } else {
                requesturl=ops.url; 
            }
            if (ops.putJson) {
                var argsStr=[];
                for(var k in args){
                    var v;
                    if(args[k]==null){
                    	continue;
                    }else if($.trim(args[k])==""){
                        v="'"+$.trim(args[k])+"'";
                    }else if(typeof(args[k])=="string"&& isNaN(args[k])){//字符串
                        v="'"+args[k]+"'";
                    }else if(args[k] instanceof Array){//数组
                        v="["+args[k]+"]";
                    }else if(!isNaN(parseInt(args[k]))){ //数字
                        v=parseInt(args[k]);  
                    }else{
                        v=args[k];
                    }
                    argsStr.push(k+':'+v);
                }
                daval = stringToJSON('{'+argsStr.join(',')+'}');
            } else {
                if (!$(objthis).find("form[fm-search]").get(0)) {
                   $(objthis).append("<form fm-search ></form>");
                }else{
                    $(objthis).find("form[fm-search]").empty();
                }

                for(var k in args){
                    if(args[k] instanceof Array){//数组
                        var v=args[k].split(',');
                        for(var i=0;i<v.length;i++){
                            $(objthis).find("form[fm-search]").append("<input type='hidden' name='"+k+"' value='" +v[i]+ "' />");
                        }
                    }else{ //其他
                        $(objthis).find("form[fm-search]").append("<input type='hidden' name='"+k+"' value='" +args[k]+ "' />");
                    }
                }
                daval = $(objthis).find("[fm-search]").serialize();
            }
            
        }

        /**
         * 数据加载
         * @method loaddata
         */
        function loaddata(isInit) {
            if (ops.data != null &&(ops.url == ""|| ops.url == null)) {
                fillData(ops, template);
                if (ops.page.lazyCount) {
                    listcount = ops.page.lazyCount;
                }
                if (ops.page.lazyUrl) {
                	ops.url  = ops.page.lazyUrl;
                }
                loaddatafinish(isInit) ;
            } else if (ops.url != "" && ops.url != null) {
                initRequest();
                $.ajax({
                    url: requesturl,
                    data: daval,
                    type: ops.method,
                    dataType: ops.dataType,
                    cache:ops.cache,
                    success: function (result) {
                        try {
                            if (result.utrymsg && result.utrymsg != "") {
                                alertui(result.utrymsg);
                                return false;
                            }
                        } catch (e) {

                        }
                        listcount = result[ops.dataformat.count];
                        ops.data = result[ops.dataformat.data];
                        fillData(ops, template);
                        nextAppend=true;
                        loaddatafinish(isInit) ;
                    },
                    error: function (result) {
                        errormsg("数据获取失败！");
                    }
                });
            }
        }
        /**
         * 数据加载完成后，执行后续操作
         */
        function loaddatafinish(isInit) {
            converfunc();
            pagecount = GetCountPage(listcount,ops.page.pageSize);
            if (ops.afterInit && isInit) {
                ops.afterInit();
            }
            if (ops.afterLoad) {
                ops.afterLoad();
            }
            if (ops.getDataButton.button!= null && pageIndex >= pagecount) {
            	if(ops.getDataButton.endPlayStyle=="hide"){
            		$(ops.getDataButton.button).hide();
            	}
            	if(ops.getDataButton.endPlayStyle=="disabled"){
            		$(ops.getDataButton.button).attr("disabled",true);
            	}
            }
        }
        
        /**
         * 转换控制
         * @method converfunc
         */
        function converfunc() {
            var clist = $(objthis).find("[fm-conver]");
            $.each(clist, function (i, n) {
                var txt = $(n).html()
                var convfn = $(n).attr("fm-conver");
                var fn = eval(convfn)
                var cvres = fn(txt);
                $(n).html(cvres);
            });
        }

        /**
         * 初始化事件
         */
        $(document).ready(function () {
        	/***
        	 * 获取数据按钮绑定单击事件
        	 */
            if (ops.getDataButton.button!= null) {
            	  $(ops.getDataButton.button).click(function(){
            		  append();
                  });
            }
            /**
             * 滚动监控
             */
            $(ops.div).scroll(function(){
                var nScrollHight = $(this)[0].scrollHeight;
                var nScrollTop = $(this)[0].scrollTop;
                var nDivHight = $(this).height();
                if(nScrollHight>nDivHight && (nScrollTop >= nScrollHight-nDivHight -ops.bottomSpacing)){
                	append();
                    $(this).scrollTop(nScrollTop);
                }
                });
        });
        /**
         * 列表附加数据
         */
        function append(){
            if(pageIndex<pagecount){
            	if( nextAppend){
                    ops.data = null;
                    pageIndex += 1;
                    loaddata(false);
                    nextAppend=false;
            	}
            }else  if (pageIndex >= pagecount && ops.afterAppend) {
                    ops.afterAppend();
            }
        }
        
        /**
         * json大小
         * @method jsonsize
         */
        function jsonsize(obj) {
            var count = 0;
            for (var i in obj) {
                count++;
            }
            return count;
        }

        /**
    * 计算页数
    * @method GetCountPage
    * @param {int} pageCount 数据总项数
    * @param {int} pageSize 分页大小
    */
        function GetCountPage(pageCount, pageSize) {
            if (pageCount < pageSize) {
                return 1;
            } else if (pageCount > 0) {
                if (pageCount % pageSize == 0) {
                    return maxpage = pageCount / pageSize;
                } else {
                    return parseInt((pageCount / pageSize)) + 1;
                }
            }
            return 0;
        }

        /**
        * 填充数据
        * @method fillData
        * @param {object} ops 控件参数对象
        * @param {string} template 填充模版
        */
        function fillData(ops, template) {
            var d = ops.data;
            if (d && d.length > 0) {
                try {
                    $.each(d, function (i, n) {
                        $(tempbox).append($(template.fill(n).replaceo("fm-row-num", i)).data("jsondata",n));
                    });
                } catch (e) {
                    console.debug(template);
                    console.debug(e);
                }
            }
        }


        /**
        * 查找模版
        * @method fillData
        * @param {object} obj jquery对象
        * @param {object} ops 控件参数对象
        */
        function findTemp(obj, ops) {
            var temphtml = $(obj).find("[fm-body]").html();
            return temphtml;
        }
        /**
        * 查找模板外壳
        * @method fillData
        * @param {object} obj jquery对象
        */
        function findTempBox(obj) {
            return $(obj).find("[fm-body]");
        }
        return func;
    }
})(jQuery);