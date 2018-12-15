;(function($, window, document,undefined) {
    //定义Beautifier的构造函数
    var Beautifier = function(ele, opt) {
        this.$element = ele,
            this.defaults = {
                width: '360px',
                height: '',
                title: '提示信息',
                content: '',
                confirmButton: true,
                cancelButton: false,
                closeImage: true,
                draggable:false,
                confirmButtonClickCallback: null,
                cancelButtonClickCallback: null,
                closeImageClickCallback: null
            },
            this.options = $.extend({}, this.defaults, opt)
    }
    //定义Beautifier的方法
    Beautifier.prototype = {
        create: function() {

            var popHtml =
                '<div id="pop_div" style="width: '+this.options.width+';">                                                         ' +
                    '    <div id="pop_div_title">                                               ' +
                    '        <span>'+this.options.title+'</span>                                                      ' ;
            if(this.options.closeImage){
                popHtml += '        <span id="pop_div_close">X</span>                                  ';
            }
            popHtml +=
                    '    </div>                                                                 ' +
                    '    <hr id="pop_div_line">                                                 ' +
                    '    <div id="pop_div_content">                                             ' +
                    this.options.content +
                    '    </div>                                                                 ' +
                    '    <div id="pop_div_button" align="center">                               ' ;
            if(this.options.confirmButton){
                popHtml += '        <input type="button" value="确定" id="sure" class="orange-button"/> ';
            }
            if(this.options.cancelButton){
                popHtml += '        <input type="button" value="取消" id="cancle" class="gray-button" style="margin-left: 20px;"/> ';
            }
            popHtml +=
                    '    </div>                                                                 ' +
                    '</div>                                                                     ' ;

            $(document.body).append($(popHtml));

            popModelDiv();

            if(this.options.draggable){
                $("#pop_div").draggable();
            }

            var options = this.options;

            $("#sure").bind("click",function () {
                //未提供回调方法，默认关闭模态框
                if(!options.confirmButtonClickCallback){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法返回true，才关闭模态框
                else if(options.confirmButtonClickCallback(this)){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法未返回true，不关闭模态框
                else{
                    //do nothing
                }
            });

            $("#cancle").bind("click",function () {
                //未提供回调方法，默认关闭模态框
                if(!options.cancelButtonClickCallback){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法返回true，才关闭模态框
                else if(options.cancelButtonClickCallback(this)){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法未返回true，不关闭模态框
                else{
                    //do nothing
                }
            });

            $("#pop_div_close").bind("click",function () {
                //未提供回调方法，默认关闭模态框
                if(!options.closeImageClickCallback){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法返回true，才关闭模态框
                else if(options.closeImageClickCallback(this)){
                    hideModelDiv();
                }
                //如果提供了回调方法，并且回调方法未返回true，不关闭模态框
                else{
                    //do nothing
                }
            });

            return ;
        },
        close: function() {
            hideModelDiv();

            return ;
        }
    }

    var mark_id = "xp_mark";

    function popModelDiv() {
        showMark();
        showDiv("pop_div");
    }

    function hideModelDiv() {
        hideMark();
        closeDiv("pop_div");
        $("#pop_div").remove();
        $("#"+mark_id).remove();
    }

    function showMark() {
        //添加并显示遮罩层
        var xp_mark= document.getElementById(mark_id);
        if(xp_mark!=null) {
            $("#"+mark_id).show();
            if(document.all) {
                var Ie_ver=navigator["appVersion"].substr(22,1);
                if(Ie_ver==6||Ie_ver==5){hideSelectBoxes();}
            }

            resizeMark(mark_id);
        }else{
            //页面添加div explainDiv,注意必须是紧跟body 内的第一个元素.否则IE6不正常.
            $("<div id='"+mark_id+"'></div>").appendTo("body").fadeIn(200);
            showMark();//继续回调自己
        }

        $(window).scroll(function(){
            resizeMark(mark_id);
        });
        $(window).resize(function(){
            resizeMark(mark_id);
        });
    }

    //变更遮罩层宽度和高度
    function resizeMark(obj){
        var height=XP_getPageSize().h;
        var width=XP_getPageSize().w;
        if(height<600){
            height=620;
        }
        $("#" + obj).width(width).height(height);
    }

    //隐藏遮罩层
    function hideMark(){
        $("#" + mark_id).hide();
        var Ie_ver=navigator["appVersion"].substr(22,1);
        if(Ie_ver==6||Ie_ver==5){showSelectBoxes();}
    }

    //获取页面的高度与宽度
    function XP_getPageSize(){
        var pt = {w:0,h:0};
        if (window.innerHeight && window.scrollMaxY){
            pt.w = document.body.scrollWidth;
            pt.h = window.innerHeight + window.scrollMaxY;
        }
        else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
            pt.w = document.body.scrollWidth;
            pt.h = document.body.scrollHeight;
        }
        else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
            pt.w = document.body.offsetWidth;
            pt.h = document.body.offsetHeight;
        }
        return pt;
    }

    //显示所有的下拉列表框
    function showSelectBoxes(){
        var selects = document.getElementsByTagName("select");
        for (i = 0; i != selects.length; i++) {selects[i].style.visibility = "visible"; }
    }

    //隐藏所有的下拉列表框
    function hideSelectBoxes(){
        var selects = document.getElementsByTagName("select");
        for (i = 0; i != selects.length; i++) {selects[i].style.visibility = "hidden";}
    }

    //让层居中显示
    function showDiv(div_id){
        var obj = "#" + div_id;
        $(obj).show();
        center(obj);
        $(window).scroll(function(){
            center(obj);
        });
        $(window).resize(function(){
            center(obj);
        });
    }

    function center(obj){//页面可以用obj == document.getElementById();
        var windowWidth = document.documentElement.clientWidth;
        var windowHeight = document.documentElement.clientHeight;

        var popupHeight =$(obj).height();
        var popupWidth =$(obj).width();

        $(obj).css({
            "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),
            "left": (windowWidth-popupWidth)/2
        });
    }

    //隐藏弹出框
    function closeDiv(div_id){
        $("#" + div_id).hide();
        $(window).unbind();
    }

    //在插件中使用Beautifier对象
    $.fn.popModal = function(options) {
        var beautifier = new Beautifier(this, options);
        return beautifier.create();
    }

    $.fn.closeModal = function(options) {
        var beautifier = new Beautifier(this, options);
        return beautifier.close();
    }


})(jQuery, window, document);
