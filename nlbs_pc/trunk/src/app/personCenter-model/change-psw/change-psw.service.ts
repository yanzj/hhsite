import {Injectable} from "@angular/core";


declare var $: any;
@Injectable()

export class changepswService {

  tipsErrorDisplay: string = 'show';
  myErrorPosition: string = 'bottom';
  myMargainLeft = 3;
  tipsMarginLeft = 3;
  isZero = /\b(0+)/gi;

  ValidateId(id, Out, myErrorPosition, myMargainLeft, errorText) {
    $("#" + id).bind({
        focusout: function () {
          Out(id, myErrorPosition, myMargainLeft, errorText);
        },
        focusin: function () {
          $(this).removeTip();
        }
      }
    );
  }
  //判断是否为空
  isEmpty(inputId) {
    var inputObj = $("#" + inputId);
    var inputValue = $("#" + inputId).val();
    if (null == $("#" + inputId).val() || '' == $("#" + inputId).val() || '' == $("#" + inputId).val().trim()) {
      return true;
    }
    return false;
  }
  Validates = {
    //用于校验输入的字符的个数
    checkLength: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      var inputValue=$("#"+inputId).val();
      var inputLength=inputValue.length;
      //如果输入的个数  小于指定的个数  显示格式错误 （修改密码的地方 新密码至少六位）
      if(this.isEmpty(inputId)){
        $("#" + inputId).createTip({
          margainLeft: myMargainLeft,
          errorText: "不能为空",
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      if (inputLength<6) {
        $("#" + inputId).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      $("#" + inputId).removeTip();
      return true;
    },

  //  创建错误提示
    createError:(inputId, myErrorPosition, myMargainLeft, errorText) =>{
      $("#" + inputId).createTip({
        margainLeft: myMargainLeft,
        errorText: errorText,
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
      return false;
    }



  };
}
