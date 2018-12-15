;(function($, window, document,undefined) {
    //定义Beautifier的构造函数
    var Tip = function(ele, opt) {
        this.$element = ele,
            this.defaults = {
                margainLeft: 10,
                errorText: '错误提示',
                errorPosition: 'right',//参数包括right,top-right,top-left
                errorDisplay: 'show'//参数包括hide,show
            },
            this.options = $.extend({}, this.defaults, opt),
            this.errorId = this.$element.attr("id") + "-error",
            this.tipsWidth = 0;
    }
    //定义Tip的方法
    Tip.prototype = {
        create: function() {

          if($("#" + this.errorId + "-error-icon").length > 0){
            $("#" + this.errorId + "-error-icon").remove();
            $("#" + this.errorId + "-error-tips").remove();
          }

          // var error = "<div id='"+this.errorId+"' class='error-container'><div class='error-icon'></div><div class='error-tips'>"+this.options.errorText+"</div></div>";
          var error = "<div id='"+this.errorId + "-error-icon" +"' class='error-icon'></div><div  id='"+this.errorId + "-error-tips" +"' class='error-tips'>"+this.options.errorText+"</div></div>";

          //针对select2插件的特殊处理，select2插件时，提示框应该在this.$element的祖父节点后生成兄弟节点
          if(this.errorId.indexOf("select2") >= 0){
            this.$element.parent().parent().after($(error));
          } else {
            this.$element.parent().append($(error));
          }


          //设置margain
          $("#" + this.errorId + "-error-icon").css("margin-left",this.options.margainLeft+ "px");

          //记录div宽度
          this.tipsWidth = $("#" + this.errorId).find(".error-tips").outerWidth();

          //处理是否隐藏
          var displayClass = "";
          if(this.options.errorDisplay == 'hide'){
              displayClass = "hide";
          }
          $("#" + this.errorId).find(".error-tips").addClass(displayClass);

          //处理偏移量
          // setLocation(this);

          var obj = this;

          // $(window).bind("resize",function(){setLocation(obj)});
          // $(".content").resize(function(){setLocation(obj)});

          //给图标绑定函数
          if(this.options.errorDisplay == 'hide') {
              $("#" + obj.errorId).find(".error-icon").bind("mouseout", function () {
                  $("#" + obj.errorId).find(".error-tips").addClass(displayClass);
              });
              $("#" + obj.errorId).find(".error-icon").bind("mouseover", function () {
                  $("#" + obj.errorId).find(".error-tips").removeClass(displayClass);
              });
          }

          //红框显示
          this.$element.addClass("error-input");

          return ;
        },
        remove: function() {
            $("#" + this.errorId + "-error-icon").remove();
            $("#" + this.errorId + "-error-tips").remove();
            this.$element.removeClass("error-input");

            return ;
        },
        hide: function () {
            $("#" + this.errorId).hide();
        },
        show:function () {
            $("#" + this.errorId).show();
        }
    }

    //在插件中使用Beautifier对象
    $.fn.createTip = function(options) {
        var tip = new Tip(this, options);
        return tip.create();
    }

    $.fn.removeTip = function(options) {
        var tip = new Tip(this, options);
        return tip.remove();
    }

    $.fn.hideTip = function(options) {
        var tip = new Tip(this, options);
        return tip.hide();
    }

    $.fn.showTip = function(options) {
        var tip = new Tip(this, options);
        return tip.show();
    }


    function setLocation(obj) {
      //设置container的位置
      // var left = (obj.$element.offset().left + obj.$element.outerWidth()+ obj.options.margainLeft) + "px";
      // $("#" + obj.errorId).css("left",left);
      //
      // var hightFall = ($("#" + obj.errorId).outerHeight() - obj.$element.outerHeight())/2;
      // var top = obj.$element.offset().top - hightFall;
      // $("#" + obj.errorId).css("top",top);


      // //设置提示框的相对位置
      // if(obj.options.errorPosition == 'right'){
      //     $("#" + obj.errorId).find(".error-tips").css("margin-top","-17px").css("margin-left","24px");
      // }else if(obj.options.errorPosition == 'top-right'){
      //     $("#" + obj.errorId).find(".error-tips").css("margin-top","0").css("margin-left","0px");
      // }else if(obj.options.errorPosition == 'top-left'){
      //     var marginLeftPx = -(obj.tipsWidth - 14) + "px";
      //     $("#" + obj.errorId).find(".error-tips").css("margin-top","-44px").css("margin-left",marginLeftPx);
      // }else if(obj.options.errorPosition=='bottom'){
      //   $("#" + obj.errorId).find(".error-tips").css("margin-top","3px").css("margin-left",-(obj.$element.outerWidth()+ obj.options.margainLeft));
      // }

      return ;
    }


})(jQuery, window, document);
