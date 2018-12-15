import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from "@angular/router";
import {commonService} from "../../../../commonService.service";
import {User} from "../../../../User.service";
import {NetworkService} from "../../../../network.service";
declare var $: any;
declare var laydate: any;

@Component({
  selector: 'app-popup-modify-repayment',
  templateUrl: './popup-modify-repayment.component.html',
  styleUrls: ['./../../repayment-plan-modify-detail.component.css']
})
export class PopupModifyRepaymentComponent implements OnInit {

  @Input() popShow : string;
  @Input() reCordFlag : boolean;
  @Output() event3 = new EventEmitter();
  @Output() event2 = new EventEmitter();
  @Input() modifyData : any;
  @Output() event = new EventEmitter();
  @Input() repaymentSchedule:any={};
  @Input() paidCode: string;
  @Input() needLoadAgain:boolean;


  constructor(
    private router: Router,
    private commonFunc: commonService,
    private user: User,
    private networkService: NetworkService,
  ) { }


  ngOnInit() {

    this.timedicpker();
    // 应还本金的校验
    $("#principal").blur(() => {
      var principalNull;
      principalNull=this.Validates.checkNumber("principal", 'bottom', this.tipsMarginLeft, "不能为空");
      if(principalNull){
        if($("#principal").val()==='0.00'){
          if(($("#serviceCharge").val()==='0.00')&&($("#accruals").val()==='0.00')&&($("#guarantee").val()=='0.00')){
            this.Validates.addError("accruals", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("principal", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("serviceCharge", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("guarantee", 'bottom', this.tipsMarginLeft, "金额不能都为0");
          }
        }else {
          //前端校验结束,向后台发送请求
          this.addData($("#principal").val(),$("#accruals").val(),$("#applyTimeBegin").val(),$("#serviceCharge").val(),$("#guarantee").val(),false);
        }
      }

    });
    $("#principal").focusin(function () {
      $(this).removeTip()
    });
    // 应还利息的校验
    $("#accruals").blur(() => {
      var accrualsNull;
      accrualsNull = this.Validates.checkNumber("accruals", 'bottom', this.tipsMarginLeft, "不能为空");
      if(accrualsNull){
        if($("#accruals").val()==='0.00'){
          if(($("#serviceCharge").val()==='0.00')&&($("#principal").val()==='0.00'&&($("#guarantee").val()=='0.00'))){
            this.Validates.addError("accruals", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("principal", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("serviceCharge", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("guarantee", 'bottom', this.tipsMarginLeft, "金额不能都为0");

          }
        }else {
          //前端校验结束,向后台发送请求
          this.addData($("#principal").val(),$("#accruals").val(),$("#applyTimeBegin").val(),$("#serviceCharge").val(),$("#guarantee").val(),false);
        }
      }
    });
    $("#accruals").focusin(function () {
      $(this).removeTip()
    });
    //应还服务费
    $("#serviceCharge").blur(() => {
      var accrualsNull;
      accrualsNull = this.Validates.checkNumber("serviceCharge", 'bottom', this.tipsMarginLeft, "不能为空");
      if(accrualsNull){
        if($("#serviceCharge").val()==='0.00'){
          if(($("#accruals").val()==='0.00')&&($("#principal").val()==='0.00')&&($("#guarantee").val()=='0.00')){
            this.Validates.addError("accruals", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("principal", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("serviceCharge", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("guarantee", 'bottom', this.tipsMarginLeft, "金额不能都为0");

          }
        }else {
          //前端校验结束,向后台发送请求
          this.addData($("#principal").val(),$("#accruals").val(),$("#applyTimeBegin").val(),$("#serviceCharge").val(),$("#guarantee").val(),false);        //前端校验结束,向后台发送请求
        }
      }
    });
    $("#serviceCharge").focusin(function () {
      $(this).removeTip()
    });
    //应还保证金
    $("#guarantee").blur(() => {
      var accrualsNull;
      accrualsNull = this.Validates.checkNumber("serviceCharge", 'bottom', this.tipsMarginLeft, "不能为空");
      if(accrualsNull){
        if($("#guarantee").val()==='0.00'){
          if(($("#accruals").val()==='0.00')&&($("#principal").val()==='0.00')&&($("#serviceCharge").val()=='0.00')){
            this.Validates.addError("accruals", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("principal", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("serviceCharge", 'bottom', this.tipsMarginLeft, "金额不能都为0");
            this.Validates.addError("guarantee", 'bottom', this.tipsMarginLeft, "金额不能都为0");

          }
        }else {
          //前端校验结束,向后台发送请求
          this.addData($("#principal").val(),$("#accruals").val(),$("#applyTimeBegin").val(),$("#serviceCharge").val(),$("#guarantee").val(),false);
        }
      }

    });
    $("#guarantee").focusin(function () {
      $(this).removeTip()
    });
  }

  popupCloseBtn(){
    this.popShow='';
    this.event.emit(this.popShow);
  }

  //日历插件
  timedicpker() {
    //因为laydate.js使用的是立即执行函数，所以在组件初始化时，重新加载js，然后再进行我们的日历字段设置。
    // 这个地方以后要考考虑重写laydate，改造成非立即执行函数，这样避免了多次加载。暂时先这样。
    $.getScript("/assets/js/laydate/laydate.js").then(function () {
      /*日期插件*/
      var start = {
        elem: '#applyTimeBegin',
        format: 'YYYY-MM-DD',
        istime: false,
        min: "2000-1-1 00:00:00", //设定最小日期为当前日期
        max: laydate.now(), //最大日期
        istoday: false,
        isclear: true,
        fixed: true,
        choose: function (datas) {
        }
      };
      $("#applyTimeBegin").bind({
        focusout: function () {
          if ($("#applyTimeBegin").val() == "") {
          }
        }
      });
      laydate(start);
    });
  }

  ngOnDestroy() {
    //退出当前组件时，需要销毁页面上的日历节点。
    $("#laydate_box").remove();
  }

  //判断是否为空
  isEmpty(inputId) {
    if (null == $("#" + inputId).val() || '' == $("#" + inputId).val() || '' == $("#" + inputId).val().trim()) {
      return true;
    }
    return false;
  }

  tipsErrorDisplay: string = 'show';
  myErrorPosition: string = 'bottom';
  myMargainLeft = 3;
  tipsMarginLeft = 3;
  isZero = /\b(0+)/gi;

  Validates = {
    // 检测是否为空
    checkNull: (inputId, myErrorPosition, myMargainLeft, errorText) => {
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
    // 添加相应的错误样式  和错误提示
    addError: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      $("#" + inputId).createTip({
        margainLeft: myMargainLeft,
        errorText: errorText,
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
      return false;
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
  };

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


  confirmBtn(){
    var principal = this.Validates.checkNumber("principal", 'bottom', this.tipsMarginLeft, "不能为空");
    var accruals = this.Validates.checkNumber("accruals", 'bottom', this.tipsMarginLeft, "不能为空");
    var serviceCharge = this.Validates.checkNumber("serviceCharge", 'bottom', this.tipsMarginLeft, "不能为空");
    var flag=true;
    flag=principal&&accruals&&serviceCharge;
    if(flag){
      this.addData($("#principal").val(),$("#accruals").val(),$("#applyTimeBegin").val(),$("#serviceCharge").val(),$("#guarantee").val(),true);
    }

    //  前端校验无误后向后台发送请求   校验不通过的话则弹窗提示
    // this.ensureButton("还款计划修改错误","应还本金总额或应还利息总额错误，请重新修改",false);

    //  若修改成功
    // this.popNextSuccessModal("还款计划修改提交成功","还款计划修改提交成功，后台正在处理，请勿重复提交。",false,"秒后自动返回还款计划修改页面。")

  }

  ensureButton(tipTitle, tiptxt,flag:boolean){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }




  }

  cancelButton(tipTitle, tiptxt,flag:boolean){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      that.router.navigate(['/home/repaymentPlanModifyQuery']);
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }




  }
  //带有倒计时的弹窗 obj flag-true显示（确认/取消） false-确定
  popNextSuccessModal(tipTitle:string, tipContent:string,flag:boolean,text:string) {
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>"+text+"</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton:flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      cancelButtonClickCallback:myCancelButtonClickCallback,
      closeImageClickCallback: myCancelButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      return true;
    }
    function myCancelButtonClickCallback(){
      clearInterval(reduceSecondValue);
      $(this).closeModal();
    }
    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          $(this).closeModal();
          clearInterval(reduceSecondValue);
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }


  userNo:string;

  data:any={};
  @Output() event4 = new EventEmitter();
  addData(principal, interest, repaymentDate,serviceCharge,guarantee,confirm) {//传入的参数是本金，利息，还款日期
    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    this.userNo = userObj.userNo;
    // var data  = {};
    var Obj = {
      'head': {
        'functionNo': 'plms100024',
        'userNo': this.userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'paidCode': this.paidCode,
        'principal': principal,
        'interest': interest,
        'bail':guarantee,
        'scheduleDetailTmpCode':this.modifyData.scheduleDetailTmpCode,
        'serviceFee':serviceCharge
      }
    };

    this.networkService.postData(Obj, false).then(res => {
      console.log(res);
      if (res.retCode == true) {
        var returnCode=res.data.head.returnCode;
        if(returnCode=='0000'){
          this.needLoadAgain=true;
          this.data['needLoadAgain']=this.needLoadAgain;
        }else {
          this.needLoadAgain=false;
          this.data['needLoadAgain']=this.needLoadAgain;
          this.data['returnMessage']=res.data.head.returnMessage;
          this.data['returnCode']=res.data.head.returnCode;
        }
        if(this.data.needLoadAgain){
          if(!confirm){
            //如果没有错误的话   那么去掉错误的样式
            $("#applyTimeBegin").removeTip(); //去掉日历插件的错误样式
            $("#principal").removeTip();//去掉应还本金的错误样式
            $("#accruals").removeTip();//去掉应还利息的错误样式
            $("#serviceCharge").removeTip();
            $("#guarantee").removeTip();
          }else {
            //如果校验都通过的话   则将弹窗关闭  重新加载一遍数据
            this.event4.emit(this.needLoadAgain);
            this.popShow = '';
            this.reCordFlag = true;
            this.event3.emit(this.reCordFlag);
            this.event.emit(this.popShow);
          }
        }else {
          if(this.data.returnCode=='1003'){//日期选择错误
            this.Validates.addError("applyTimeBegin", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
          }else if(this.data.returnCode=='1004'){//还款日期已存在
            this.Validates.addError("applyTimeBegin", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
          }else if(this.data.returnCode=='1005'){//金额大于放款金额
            this.Validates.addError("principal", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
            $("#accruals").removeTip();
            $("#serviceCharge").removeTip();
            $("#guarantee").removeTip();
          }else if(this.data.returnCode=='1006'){//金额大于应还总利息
            this.Validates.addError("accruals", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
            $("#principal").removeTip();//去掉应还本金的错误样式
            $("#serviceCharge").removeTip();
            $("#guarantee").removeTip();
          }else if(this.data.returnCode=='1007'){//
            this.Validates.addError("serviceCharge", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
            $("#principal").removeTip();//去掉应还本金的错误样式
            $("#accruals").removeTip();//去掉应还利息的错误样式
            $("#guarantee").removeTip();
          }else if(this.data.returnCode=='1008'){//金额大于总保证金
            this.Validates.addError("guarantee", 'bottom', this.tipsMarginLeft, this.data.returnMessage);
            $("#principal").removeTip();//去掉应还本金的错误样式
            $("#accruals").removeTip();//去掉应还利息的错误样式
            $("#serviceCharge").removeTip();
          }
        }
      }
    });
    // return this.data;
  }
}
