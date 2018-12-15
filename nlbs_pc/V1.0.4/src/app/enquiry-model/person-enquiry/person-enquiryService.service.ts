
import {Injectable} from "@angular/core";


declare var $: any;
@Injectable()


export class personEnquiryService {
  tipsErrorDisplay: string = 'show';
  myErrorPosition: string = 'bottom';
  myMargainLeft = 3;
  tipsMarginLeft = 3;
  isZero = /\b(0+)/gi;

  add_option(array, id, arr) {
    var x;
    for (x in array) {
      var name, code;
      for (var y in array[x]) {
        if (y == arr[0]) {
          code = array[x][y];
        } else if (y == arr[1]) {
          name = array[x][y];
        }
        ;
      }
      if (x == 0) {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      } else {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      }
    }
  }

  //判断是否为空
  isEmpty(inputId) {
    if (null == $("#" + inputId).val() || '' == $("#" + inputId).val() || '' == $("#" + inputId).val().trim()) {
      return true;
    }
    return false;
  }

  //验证select框
  ValidateIdForSelect2(id, Out, myErrorPosition, myMargainLeft, errorText) {
    $("#" + id).on('select2:close', function (evt) {
      Out(id, myErrorPosition, myMargainLeft, errorText);
    });
    $("#" + id).on('select2:open', function (evt) {
      $("#" + id + '-select2-selection--single').removeTip();
    });
  }

  //验证input框
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

  Validates = {
    checkNullForSelect2: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      if (this.isEmpty(inputId)) {
        $("#" + inputId + '-select2-selection--single').createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      $("#" + inputId + '-select2-selection--single').removeTip();
      return true;
    },

    checkNull: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      // console.log("this is checkNull of function");
      if (this.isEmpty(inputId)) {
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

    //检查数字为正数，最多只能有两位小数
    checkNumber: (id, myErrorPosition, myMargainLeft, errorText) => {
      var isresidenceNum = /^\d+(?:\.\d{1,2})?$/;
      var str = $("#" + id).val();

      if (this.isEmpty(id)) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      } else if (isresidenceNum.test(str) == false) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: '格式错误',
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      //去除开头的0
      if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
        var noZeroVal = str.replace(this.isZero, "");
        $("#" + id).val(noZeroVal);
      }

      $("#" + id).removeTip();
      return true;
    },
    checkNumber2: (id, myErrorPosition, myMargainLeft, errorText) => {
      var isresidenceNum =  /^[1-9]\d*$/;
      var str = $("#" + id).val();

      if (this.isEmpty(id)) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      } else if (isresidenceNum.test(str) == false) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: '格式错误',
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      //去除开头的0
      if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
        var noZeroVal = str.replace(this.isZero, "");
        $("#" + id).val(noZeroVal);
      }

      $("#" + id).removeTip();
      return true;
    },

    //检查是否超过限额
    checkLimit: (id, myErrorPosition, myMargainLeft, errorText) => {
      $("#" + id).createTip({
        margainLeft: myMargainLeft,
        errorText: errorText,
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
    },
    //只检查格式是否正确   用于检测意向金
    checkFormate: (id, myErrorPosition, myMargainLeft, errorText) => {
      if (!this.isEmpty(id)) {
        var isresidenceNum = /^\d+(?:\.\d{1,2})?$/;
        var str = $("#" + id).val();
        if (isresidenceNum.test(str) == false) {
          $("#" + id).createTip({
            margainLeft: myMargainLeft,
            errorText: '格式错误',
            errorPosition: myErrorPosition,//参数包括right,top-right,top-left
            errorDisplay: this.tipsErrorDisplay//参数包括hide,show
          });
          return false;
        } else {
          return true;
        }
      } else {
        return true;
      }
    },

  };

  //校验数据格式  判断是否符合当前要求
  //obj-校验对象基本信息（id,位置，偏移量，提示语），校验规则（正整数，两位小数）
  checkObjFormat(obj:any,flag:boolean){

    var id = obj["id"];
    var myErrorPosition = obj["myErrorPosition"];
    var myMargainLeft = obj["myMargainLeft"];
    var errorText = obj["errorText"];
    var isresidenceNum ;//校验规则

    if (flag){
      isresidenceNum =  /^[1-9]\d*$/;
    }else{
      isresidenceNum = /^\d+(?:\.\d{1,2})?$/;
    }


    var str = $("#" + id).val();

    if (this.isEmpty(id)) {
      $("#" + id).createTip({
        margainLeft: myMargainLeft,
        errorText: errorText,
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
      return false;
    } else if (isresidenceNum.test(str) == false) {
      $("#" + id).createTip({
        margainLeft: myMargainLeft,
        errorText: '格式错误',
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
      return false;
    }
    //去除开头的0
    if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
      var noZeroVal = str.replace(this.isZero, "");
      $("#" + id).val(noZeroVal);
    }

    $("#" + id).removeTip();
    return true;
  }

}
