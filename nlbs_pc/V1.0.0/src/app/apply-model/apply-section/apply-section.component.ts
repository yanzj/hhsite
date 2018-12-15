import {Component, enableProdMode, OnInit} from '@angular/core';
import {FileUploader} from "ng2-file-upload";
import {NetworkService} from '../../network.service';
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {ChangeDetectorRef} from "@angular/core";
import {setTimeout} from "timers";

//申请提交
declare var $: any;
@Component({
  selector: 'app-apply-section',
  templateUrl: './apply-section.component.html',
  styleUrls: ['./apply-section.component.css']
})
export class ApplySectionComponent implements OnInit {

  //统计抵押物个数
  num: Object[] = [{}];

  //设置进度条的长度
  progressLength: number = 0;

  //测试的值
  progress: number = 2;

  //决定进度条是否显示
  isShow: boolean = false;
  isShow2: boolean = false;

  //控制是否在ngAfterViewChecked方法中执行
  isExe: boolean = false;

  isReloadBuinessData:boolean = false;//是否是进件暂存再提交数据进行初始化数据

  //是否是录单员的标志
  isCatalogue: string;
  //根据录单员调用select2
  isSelect2: boolean;
  //渠道===进件公司
  distributorList: any;
  //渠道列表的值
  distributor: string = '';
  //业务经理
  agentList: any;
  //产品名称的列表
  productList: any;
  //放款方式
  creditTypeList: any;
  //借款期限
  loanPeriodList: any;
  //抵押物信息
  mortgageTypeList: any;
  //定义提交文件要传给后台的参数
  fileName: string;
  serialNo: string;
  uploadTime: string;

  //定义是否有文件上载
  isFile: boolean;
  //决定是上传中还是上传失败
  isUp: boolean = true;

  //主借款人姓名
  customName:string;
  //暂存之后再申请（要给的值）和直接申请（要给空）
  bmsLoanCode:string='';

  //面包屑导航根据点击进来的路径不同显示不同
  breadcrumbNavDisplay:boolean=false;

  itemStatusLists :any= ['房产估价','进件申请','风控审批','合作制作','协议公证','抵押登记','完成放款'];
  itemSelectStatus:string = '风控审批';
  itemSelectIndex : number = 1;

  //删除按钮在进件再申请中显示
  isDeleteShow:boolean=false;

  setSelectIndex(){
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if(this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }

    }
  }
 //面包屑导航根据点击进来的路径不同显示不同的方法
  breadcrumbNavDisplayFun(){
    if(this.commonFunc.getBusinessSaveFlagFunc()  == this.commonFunc.businessReadValue){
      this.breadcrumbNavDisplay=true;
    }else{
      this.breadcrumbNavDisplay=false;
    }
  }


  userDataKe = this.user.getUserData();
  timeStamp = this.commonFunc.getloginToken();


  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              public ref: ChangeDetectorRef) {
    this.progressLength = 0;
    this.ref = ref;
  }


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

  popNextErrorModal(tipTitle, tipContent) {
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent;
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      return true;
    }
  }

  popNextSuccessModal(tipTitle, tipContent, text) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>" + text + "</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      that.router.navigate(['/home/query']);
      clearInterval(reduceSecondValue);
      return true;
    }

    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          $(this).closeModal();
          clearInterval(reduceSecondValue);
          that.router.navigate(['/home/query']);
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }

  popNextSuccessModal2(tipTitle, tipContent, text) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>" + text + "</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      clearInterval(reduceSecondValue);
      return true;
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

  delete(event, i) {
    var className1: any = document.getElementsByClassName("firstNum" + i)[0];
    var className2: any = document.getElementsByClassName("secondNum" + i)[0];
    //两个框框只要有一个不为空，就会弹出提示框
    //判断抵押物1是否为空
    var guarantee1 = className1.value == '' || null == className1.value || '' == className1.value.trim();
    var guarantee2 = className2.value == '' || null == className2.value || '' == className2.value.trim();
    if ((!guarantee1) || (!guarantee2)) {
      this.popNextErrorModal("删除抵押物", "将删除的抵押物有已填写的信息，是否继续？");
      var sure: any = document.getElementById("sure");
      sure.onclick = () => {
        this.num.splice(i, 1);

      }
    } else {
      this.num.splice(i, 1);
    }


    //删除一行数据时，应清楚可能存在的提示
    $("#firstNum" + i).removeTip();
    $("#secondNum" + i).removeTip();
    $("#mortgage" + i+'-select2-selection--single').removeTip();



    //因为当前的delete函数执行完成后，才ng才会渲染dom结构。如果我们立即执行reBind，此时的抵押物个数仍然为未删除前的。所以我们需要延迟200ms，等待ng渲染dom结构。
    var certificate = this.num.length;
    var obj = this;
    setTimeout(function () {
      reBind(obj,certificate);
    },200);
    function reBind(obj,certificate){
      for(let certificateIndexAfterDelete = 0; certificateIndexAfterDelete < certificate; certificateIndexAfterDelete++){
        //先解除绑定
        $("#firstNum" + certificateIndexAfterDelete).unbind();
        $("#secondNum" + certificateIndexAfterDelete).unbind();
        //$("#mortgage" + certificateIndexAfterDelete).unbind();
        obj.ValidateId("firstNum" + certificateIndexAfterDelete, obj.Validates.checkNull, 'bottom', obj.tipsMarginLeft, "不能为空");
        obj.ValidateId("secondNum" + certificateIndexAfterDelete, obj.Validates.checkNull, 'bottom', obj.tipsMarginLeft, "不能为空");
       // obj.ValidateIdForSelect2("mortgage" + certificateIndexAfterDelete, obj.Validates.checkNull, 'bottom', obj.tipsMarginLeft, "不能为空");
      }
    }
  }

  tipsErrorDisplay: string = 'show';
  myErrorPosition: string = 'bottom';
  myMargainLeft = 3;
  tipsMarginLeft = 3;
  isZero = /\b(0+)/gi;

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
      var str = $("#" + id).val();
       if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
        var noZeroVal = str.replace(this.isZero, "");
        $("#" + id).val(noZeroVal);
      }
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

  addCertificateListFunc() {
    this.num.push({});
    if (this.num.length > 1) {
      $(".selectmsg").select2({});
      var obj: number = this.num.length - 1;
      this.ValidateId("firstNum" + obj, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
      this.ValidateId("secondNum" + obj, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
    }
    this.isExe = true;
  }


  //删除已经上传的文件
  deleteUploadFile() {
    this.popNextErrorModal("删除上载材料", "将删除已上载的材料，是否继续？");
    var sure: any = document.getElementById("sure");
    sure.onclick = () => {
      this.isShow = false;
      this.isShow2 = false;
    };


  }

  ngOnInit() {
    //第一步 初始化组件各个输入框校验信息
    this.ValidateIdForSelect2("company", this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "进件公司不能为空");
    this.ValidateIdForSelect2("manager", this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "业务经理不能为空");
    this.ValidateId("mainName", this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "主借款人姓名不能为空");
    this.ValidateIdForSelect2('proDuctName', this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "产品名称不能为空");
    this.ValidateIdForSelect2('method', this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "放款方式不能为空");
    this.ValidateId("applyMoney", this.Validates.checkNumber, 'bottom', this.tipsMarginLeft, "申请金额不能为空");
    this.ValidateIdForSelect2('deadline', this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "借款期限不能为空");
    this.ValidateId("intentionM", this.Validates.checkFormate, 'bottom', this.tipsMarginLeft, "格式错误");

    $(".selectmsg").select2({});
    //第二步 获取进件提交初始化信息
    this.initBusinseeSelectInfo();

    //第四步 当选择公司变化时 绑定响应事件
    this.onBusinseeCompantValueChangeFunc();



  }

  //获取进件列表初始化信息（城市等信息）
  initBusinseeSelectInfo() {

    var Obj = {
      'head': {
        'functionNo': 'HH000005',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        'userNo': this.userDataKe.userNo,
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          //  如果是录单员的话，进件公司业务经理显示
          this.isCatalogue = userDataObj.beRecordClerk;
          if (userDataObj.beRecordClerk == 'Y') {
            //isSelect2 调用下面的生命周期函数试一下
            //将进件公司和业务经理显示出来
            // $(".isCatalogue").css({"visibility": "visible"}).addClass("row");
            this.isSelect2 = true
          }
          //添加到进件公司列表
          this.distributorList = userDataObj.distributorList;
          this.add_option(this.distributorList, '#company', ['distributorCode', 'distributorName']);
          //添加到业务经理列表
          this.agentList = userDataObj.agentList;
          this.add_option(this.agentList, '#manager', ['agentId', 'agentName']);
          //添加产品到产品列表
          this.productList = userDataObj.productList;
          for (var i = 0; i < this.productList.length; i++) {
            $("<option>" + this.productList[i].productName + "</option>").attr({value: this.productList[i].productCode + ',' + this.productList[i].maxLoanAmount}).appendTo("#proDuctName");
          }
          //将放款方式添加
          this.creditTypeList = userDataObj.creditTypeList;
          this.add_option(this.creditTypeList, '#method', ['creditTypeCode', 'creditTypeName']);
          //将借款期限添加
          this.loanPeriodList = userDataObj.loanPeriodList;
          this.add_option(this.loanPeriodList, '#deadline', ['loanPeriodCode', 'loanPeriodName']);


          //获取到当前抵押物 选项信息
          this.mortgageTypeList = userDataObj.mortgageTypeList;
          //this.isExe = true;//执行添加值
          this.initMortgageSelectFunc();

          //第三步 获取暂存进件信息
          this.getCurrentBusinsessStatuInfo();
        } else {
          //接口请求成功  登录失败 按返回码处理错误信息

        }

      } else {
        //请求失败处理方法


      }

    });

  }
  //进件页面出事化时  对抵押物信息进行初始化
  initMortgageSelectFunc(){

    var lengthNum = this.num.length -1 ;
    this.add_option(this.mortgageTypeList, '#mortgage'+lengthNum , ['mortgageTypeCode', 'mortgageTypeName']);
    this.ValidateId("firstNum"+lengthNum, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
    this.ValidateId("secondNum"+lengthNum, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
    this.ValidateIdForSelect2("mortgage"+lengthNum, this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "不能为空");
    //将抵押物信息添加
    $(".selectmsg").select2({});




  }

  initResubmitMortgageSelectFunc(){
    for (var i=0;i<this.num.length;i++){
          this.add_option(this.mortgageTypeList, '#mortgage'+i , ['mortgageTypeCode', 'mortgageTypeName']);
          this.ValidateId("firstNum"+i, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
          this.ValidateId("secondNum"+i, this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");
          this.ValidateIdForSelect2("mortgage"+i, this.Validates.checkNullForSelect2, 'bottom', this.tipsMarginLeft, "不能为空");

         $("#mortgage"+i).val(this.num[i]["mortgageTypeCode"]).trigger("change");

      }
      $(".selectmsg").select2({});
  }







ngAfterViewChecked(){
   if(this.isExe){
      this.initMortgageSelectFunc();

      this.isExe = false;
   }
   if (this.isReloadBuinessData) {
      this.initResubmitMortgageSelectFunc();
      this.isReloadBuinessData = false;
   }

    //面包屑导航根据点击进来的路径不同显示不同的方法的掉用
      this.breadcrumbNavDisplayFun();



}


  //获取当前组件显示的是否是暂存进件信息
  getCurrentBusinsessStatuInfo() {
    //获取是否读取该信息
    var readFlag: string = this.commonFunc.getBusinessSaveFlagFunc();
    if (readFlag != this.commonFunc.businessReadValue) {
      // 当前组件不符合读取暂存进件条件
      return;
    }

    var BusinessData = this.commonFunc.getBusinessData();

    if (BusinessData != null) {
      this.isDeleteShow=true;
      //  当是暂存后再申请的情况下，向后台发送数据，请求之前暂存过后的情况
      var bmsLoanCode = BusinessData.bmsLoanCode;
      var temporaryObj = {
        'head': {
          'functionNo': 'HH000006',
          'userNo': this.userDataKe.userNo,
          'clientTimestamp': this.timeStamp
        },
        'body': {
          'userNo': this.userDataKe.userNo,
          'bmsLoanCode': bmsLoanCode
        }
      }
      this.networkService.postData(temporaryObj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法

          //res 是后台返回来的数据
          //返回0000
          if (this.networkService.onJudgeSuccessful(res)) {
            var userDataObj: any = res.data.body;
            console.log("this is the data of userDataObj",userDataObj);

            //1 获取渠道
            var distributorCode = this.commonFunc.handleNilString(userDataObj.distributorCode);

            $("#company").val(distributorCode).trigger("change");
            // 2 获取业务经理
            var agentId = this.commonFunc.handleNilString(userDataObj.agentId);
            $("#manager").val(agentId).trigger("change");
            // 3 主借款人姓名
            var customerName = this.commonFunc.handleNilString(userDataObj.customerName);
            this.customName=customerName;
            $("#mainName").val(customerName);
            // 4 手机验证码
            var mobilephoneValidateNo = this.commonFunc.handleNilString(userDataObj.mobilephoneValidateNo);
            $("#phoneVerificate").val(mobilephoneValidateNo);
            // 5 产品名称
            var productCode = this.commonFunc.handleNilString(userDataObj.productCode);
            $("#proDuctName").val(productCode).trigger("change");
            // 6 放款方式
            var creditTypeCode = this.commonFunc.handleNilString(userDataObj.creditTypeCode);
            $("#method").val(creditTypeCode).trigger("change");
            // 7 申请金额
            var loanAmount = this.commonFunc.handleNilString(userDataObj.loanAmount);
            $("#applyMoney").val(loanAmount);
            // 8 借款期限
            var loanPeriodCode = this.commonFunc.handleNilString(userDataObj.loanPeriodCode);
            $("#deadline").val(loanPeriodCode).trigger("change");
            // 9 意向金
            var intentionMoney = this.commonFunc.handleNilString(userDataObj.intentionMoney);
            $("#intentionM").val(intentionMoney);
            // 10 备注
            var remark = this.commonFunc.handleNilString(userDataObj.remark);
            $("#remark").val(remark);
            this.bmsLoanCode = this.commonFunc.handleNilString(userDataObj.bmsLoanCode);
            //  在这里获得抵押物的信息 如果当前抵押物列表的长度不为0   则需要渲染到页面上面

            //将抵押物类型添加到列表
            //更新抵押物列表信息（dom）
            this.num = userDataObj.certificateList;
            //赋值
            this.isReloadBuinessData = true;




          }
        }
      })
    }
  }

  //当进件公司的值发生改变的时候    业务经理和产品重新进行渲染
  onBusinseeCompantValueChangeFunc() {
    //当进件公司的值发生改变的时候    业务经理和产品重新进行渲染
    //如果下面不用箭头函数  here
    var that = this;
    $("#company").change(function () {
      var distributorCode = $("#company").val();

      //重新发送一次请求
      var Obj = {
        'head': {
          'functionNo': 'HH000007',
          'userNo': that.userDataKe.userNo,
          'clientTimestamp': that.timeStamp
        },
        'body': {
          'userNo': that.userDataKe.userNo,
          'distributorCode': distributorCode
        }
      };

      that.networkService.postData(Obj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法
          //res 是后台返回来的数据
          //返回0000
          if (that.networkService.onJudgeSuccessful(res)) {
            var userDataObj: any = res.data.body;
            //将原有的业务经理和产品直接移除
            $("#manager").find("option").remove();
            $("<option></option>").appendTo($('#manager'));
            $("#proDuctName").find("option").remove();
            $("<option></option>").appendTo($('#proDuctName'));
            //添加到业务经理列表
            that.agentList = userDataObj.agentList;
            that.add_option(that.agentList, '#manager', ['agentId', 'agentName']);
            //添加产品到产品列表
            that.productList = userDataObj.productList;
            for (var i = 0; i < that.productList.length; i++) {
              $("<option>" + that.productList[i].productName + "</option>").attr({value: that.productList[i].productCode + ',' + that.productList[i].maxLoanAmount}).appendTo("#proDuctName");
            }
          } else {
            //  网络已经请求通，但是返回错误码时的错误的提示

          }
        } else {
          //  网络请求不通时  错误提示
        }
      })
    })
  }


//组件销毁时调用方法
  ngOnDestroy() {
    //  组件销毁前 移除已读取暂存进件标志
    this.commonFunc.removeBusinessSaveFlagFunc();

    var inputLists = document.querySelectorAll('div.error-container');
    for (var i = 0; i < inputLists.length; i++) {
      var nodeObj = <any>inputLists[i];
      nodeObj.remove();

    }

  }

  /*
   *文件上传功能  调用示例
   *<input type="file" ng2FileSelect [uploader]="uploader" (change)="selectedFileOnChanged($event)" />
   *
   */
  //初始化定义uploader变量,用来配置input中的uploader属性

  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });
  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    // 打印文件选择名称
    // console.log(event.target.value);
    //正则表达式验证文件是否是压缩文件
    var reg = /.*(.rar|.zip|.7z)$/;
    //用来标志验证是否是压缩文件
    var q_flag = reg.test(event.target.value);

    //let header = new Headers({'Content-Type': 'application/json',"filePath":'test'});
    this.uploader.queue[0].withCredentials = false;//验证通配符地址 防止跨域事件发生
    //this.uploader.queue[0].headers = header;

    if (q_flag) {//让进度条显示
      this.isShow = true;
      this.uploadFile();
    } else {
      this.networkService.popNextSuccessModal("文件上载失败","文件上传格式不正确，请重新上传",false);
      this.uploader.queue[0].remove();
    }

  }

  //定义事件，上传文件
  uploadFile() {
    //开始上传
    this.uploader.queue[0].upload();
    this.isUp = true;
    console.log(this.uploader.queue[0]);
    this.fileName = this.uploader.queue[0].file.name;
    //上传完成
    this.uploader.onCompleteItem = function (response, status, headers) {
      console.log("success of upload");
    };

    // 上传成功
    this.uploader.queue[0].onSuccess = (response, status, headers) => {
      // 上传文件成功
      console.log("here is function of success");
      if (status == 200) {
        console.log("inside of function");
        // 上传文件后获取服务器返回的数据

        this.progress=100;
        let tempRes = JSON.parse(response);
        console.log("the value of tempRes", tempRes);
        this.fileName = tempRes.body.fileMaps[0].fileName;
        this.serialNo = tempRes.body.fileMaps[0].serialNo;
        this.uploadTime = tempRes.body.fileMaps[0].uploadTime;
        this.isFile = true;

        that.isShow = !that.isShow;
        that.isShow2 = !that.isShow2;

        this.uploader.queue[0].remove();

      } else {
        // 上传文件后获取服务器返回的数据错误
        alert("server return error ");
      }
    };

    //上传失败
    this.uploader.queue[0].onError = (response, status, headers) => {
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        that.isFile = true;
      } else {
        // 上传文件后获取服务器返回的数据错误
        this.isUp = false;
        // alert("fail up file");
        // this.networkService.popNextSuccessModal("文件上载失败", "文件上传格式不正确，请重新上传", false);
        this.networkService.popNextSuccessModal("材料上载失败","材料上载失败，请重新上传",false);
      }

    }
    // 上传进度
    var that = this;
    this.uploader.onProgressItem = (fileItem, progress) => {
      console.log('upload progresss ', progress);
      var self = this;
      //这里的文件进度条如果是100，则改为99  在文件上传成功的时候，把progress变为100
      if(progress=='100'){
        progress=99;
      }
      self.progressLength = progress;
      self.ref.markForCheck();
      self.ref.detectChanges();
    }
    //上传进度2
  }


  //当文件上传失败进行重新上传时
  upAgiain() {
    this.uploader.queue[0].upload();
    this.isUp = true;
  }

  deleteFile() {
    this.uploader.queue[0].remove();
    //补充材料的提交按钮是否显示issmall-photo调用
    this.isShow = false;
    this.isShow2 = false;
  }


  //进件提交的方法
  q_submit() {
    var that = this;
    var company = this.Validates.checkNullForSelect2("company", 'bottom', this.tipsMarginLeft, "进件公司不能为空");
    var manager = this.Validates.checkNullForSelect2("manager", 'bottom', this.tipsMarginLeft, "业务经理不能为空");
    var mainName = this.Validates.checkNull("mainName", 'bottom', this.tipsMarginLeft, "主借款人姓名不能为空");
    var proDuctName = this.Validates.checkNullForSelect2('proDuctName', 'bottom', this.tipsMarginLeft, "产品名称不能为空");
    var method = this.Validates.checkNullForSelect2('method', 'bottom', this.tipsMarginLeft, "放款方式不能为空");
    var applyMoney = this.Validates.checkNumber("applyMoney", 'bottom', this.tipsMarginLeft, "申请金额不能为空");
    var deadline = this.Validates.checkNullForSelect2('deadline', 'bottom', this.tipsMarginLeft, "借款期限不能为空");
    var intentionM = this.Validates.checkFormate("intentionM", 'bottom', this.tipsMarginLeft, "格式错误");


    var obj: number = this.num.length;
    //用来验证是否存在空的抵押物
    var flag: boolean = true;
    var firstNum = true;
    var secondNum = true;
    var thirdNum = true;
    for (var i = 0; i < obj; i++) {
      firstNum = this.Validates.checkNull("firstNum" + i, 'bottom', this.tipsMarginLeft, "不能为空");
      secondNum = this.Validates.checkNull("secondNum" + i, 'bottom', this.tipsMarginLeft, "不能为空");
      thirdNum = this.Validates.checkNullForSelect2("mortgage" + i, 'bottom', this.tipsMarginLeft, "不能为空");
      flag = flag && firstNum && secondNum && thirdNum;
    }

    //循环获取抵押物信息
    var mortgageTypeList = [];
    for (var i = 0; i < obj; i++) {
      mortgageTypeList.push({
        "firstNum": $("#firstNum" + i).val(),
        "secondNum": $("#secondNum" + i).val(),
        "thirdNum": $("#mortgage" + i).val()
      })
    }

    //判断文件是否上传
    flag = flag && this.isFile;

    //用来验证页面所有的表单是否符合要求
    flag = flag && company && manager && mainName && proDuctName && method && applyMoney && deadline && intentionM;
    if (!flag) {
      this.popNextErrorModal("进件申请错误", "将提交的进件申请存在错误，请修改");
    } else {
      //当验证都通过时
      //渠道
      var distributorCode = $("#company").val();
      //业务经理
      var agentId = $("#manager").val();
      //住借款人姓名
      var customName = $("#mainName").val();
      //手机验证方法
      var phoneVerificate = $("#phoneVerificate").val();
      //产品名称
      var productValue = $("#proDuctName").val();
      var productArray = productValue.split(",");
      var productName = productArray[0];
      //申请金额的额度
      var limit = productArray[1];
      //放款方式
      var creditTypeCode = $("#method").val();
      //申请金额
      var applyMony = $("#applyMoney").val();
      //借款期限
      var loanPeriodCode = $("#deadline").val();
      //  意向金
      var intentionMoney = $("#intentionM").val();
      //  备注
      var remark = $("#remark").val();

      var userDataKe = this.user.getUserData();
      var timeStamp = this.commonFunc.getloginToken();


      if (limit > 0) {
        limit = limit - 0;
        applyMony = applyMony - 0;
        if (applyMony > limit) {
          this.Validates.checkLimit("applyMoney", 'bottom', this.tipsMarginLeft, "不能大于最高可借额度" + limit + "万元");
          return false;
        }
      }

      var Obj = {
        'head': {
          'functionNo': 'HH000008',
          'userNo': userDataKe.userNo,
          'clientTimestamp': timeStamp
        },
        'body': {
          'userNo': userDataKe.userNo,
          "distributorCode": distributorCode,
          "agentId": agentId,
          "customName": customName,
          "phoneVerificate": phoneVerificate,
          "productName": productName,
          "creditTypeCode": creditTypeCode,
          "applyMony": applyMony,
          "loanPeriodCode": loanPeriodCode,
          "intentionMoney": intentionMoney,
          "remark": remark,
          "operationType": '02',
          "fileName": this.fileName,
          "serialNo": this.serialNo,
          "mortgageTypeList": mortgageTypeList,
          "bmsLoanCode":this.bmsLoanCode
        }
      };

      this.networkService.postData(Obj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法
          //res 是后台返回来的数据
          //返回0000
          if (this.networkService.onJudgeSuccessful(res)) {
            //登录成功处理逻辑
            var userDataObj: any = res.data.body;
            var headDataObj: any = res.data.head;
            // 当进件申请成功以后  跳转到申请查询页面
            this.popNextSuccessModal("进件申请成功", "进件申请提交成功", "秒钟后转入进件查询页面。");
          }
        }
      })
    }
  }


  //进件暂存的方法
  q_Temporary() {
    // 暂存不需要验证
    var that = this;
    //渠道
    var distributorCode = $("#company").val();
    //业务经理
    var agentId = $("#manager").val();
    //住借款人姓名
    var customName = $("#mainName").val();
    //手机验证方法
    var phoneVerificate = $("#phoneVerificate").val();
    //产品名称
    var productName = $("#proDuctName").val();
    //放款方式
    var creditTypeCode = $("#method").val();
    //申请金额
    var applyMony = $("#applyMoney").val();
    //借款期限
    var loanPeriodCode = $("#deadline").val();
    //  意向金
    var intentionMoney = $("#intentionM").val();
    //  备注
    var remark = $("#remark").val();

    var userDataKe = this.user.getUserData();
    var timeStamp = this.commonFunc.getloginToken();

    var obj: number = this.num.length;

    var mortgageTypeList = [];
    for (var i = 0; i < obj; i++) {
      mortgageTypeList.push({
        "firstNum": $("#firstNum" + i).val(),
        "secondNum": $("#secondNum" + i).val(),
        "thirdNum": $("#mortgage" + i).val()
      })
    }

    var Obj = {
      'head': {
        'functionNo': 'HH000008',
        'userNo': userDataKe.userNo,
        'clientTimestamp': timeStamp
      },
      'body': {
        'userNo': userDataKe.userNo,
        "distributorCode": distributorCode,
        "agentId": agentId,
        "customName": customName,
        "phoneVerificate": phoneVerificate,
        "productName": productName,
        "creditTypeCode": creditTypeCode,
        "applyMony": applyMony,
        "loanPeriodCode": loanPeriodCode,
        "intentionMoney": intentionMoney,
        "remark": remark,
        "operationType": '01',
        "fileName": this.fileName,
        "serialNo": this.serialNo,
        "mortgageTypeList": mortgageTypeList,
        "bmsLoanCode":this.bmsLoanCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          this.popNextSuccessModal2("进件暂存成功", "进件申请暂存成功", "秒钟后自动关闭。");
        }
      }
    })
  }

  //删除按钮  删除
  deleteFunc(){
    this.networkService.popNextSuccessModal("删除进件申请", `将删除借款人为<span class="main-color font-weight-bold">${this.customName}</span>的进件申请`, true, () => {
      var loginTokenKey = this.commonFunc.getloginToken();
      var userObj = this.user.getUserData();
      var userNo = userObj.userNo;
      var cityCode = userObj.cityCode;

      var Obj = {
        'head': {
          'functionNo': 'HH000023',
          'userNo': userNo,
          'clientTimestamp': loginTokenKey
        },
        'body': {
          'userNo': userNo,
          'cityCode': cityCode
        }
      };
      this.networkService.postData(Obj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法
          if (this.networkService.onJudgeSuccessful(res)) {
            console.log(res);
            //将删除的消息数据发送给后台以后  跳转到查询列表的界面
            this.router.navigate(['/home/enquirySearch'])
          }
        }
      });
    });
  }

  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
  history(){
    this.router.navigate(['/home/history'])

  }
}

