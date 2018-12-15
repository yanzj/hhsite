(function($){
	var ms = {
		init:function(obj,args){
			return (function(){
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				//上一页
				if(args.current > 1){
					obj.append('<a href="javascript:;" class="prevPage">上页</a>');
				}else{
					obj.remove('.prevPage');
					obj.append('<span class="hidden">上页</span>');
				}
				//中间页码
				if(args.pageCount<=9){
				    print(1,args.pageCount);
				}else if(args.current<=6){
               	    print(1,7);
               	    obj.append('<span class="ellipsis" style="font-weight: bold;">. . .</span>');
               	    obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
               }else if(args.current>=args.pageCount-5 && args.current<=args.pageCount){
               	   obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
               	   obj.append('<span class="ellipsis"  style="font-weight: bold;">. . .</span>');
               	   print(args.pageCount-6,args.pageCount);
               }else{
               	  obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
               	  obj.append('<span class="ellipsis"  style="font-weight: bold;">. . .</span>');
               	  print(args.current-2,args.current+2);
               	  obj.append('<span class="ellipsis"  style="font-weight: bold;">. . .</span>');
               	  obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
                }

                //下一页
				if(args.current < args.pageCount){
					obj.append('<a href="javascript:;" class="nextPage">下页</a>');
				}else{
					obj.remove('.nextPage');
					obj.append('<span class="hidden">下页</span>');
				}

				function print(start,end){
                    for (;start <= end; start++) {
						if(start <= args.pageCount && start >= 1){
							if(start != args.current){
								obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
							}else{
								obj.append('<span class="current">'+ start +'</span>');
							}
						}
				    }
				}
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){
			  // 在angular下，需要先清理掉绑定函数
			  obj.off("click","a.tcdNumber");
        obj.off("click","a.prevPage");
        obj.off("click","a.nextPage");

				obj.on("click","a.tcdNumber",function(){
					var current = parseInt($(this).text());
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click","a.prevPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current-1);
					}
				});
				//下一页
				obj.on("click","a.nextPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current+1);
					}
				});
			})();
		}
	}
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 15,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);
