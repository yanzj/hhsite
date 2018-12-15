import {Component, OnInit, ChangeDetectorRef, Input, OnChanges} from '@angular/core';
import {enquiryService} from "./enquiryService.service";
import {commonService} from "../../commonService.service";
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {forEach} from "@angular/router/src/utils/collection";
import {Router} from "@angular/router";

declare var $: any;
@Component({
  selector: 'app-enquiry',
  templateUrl: './enquiry.component.html',
  styleUrls: ['./enquiry.component.css'],
  providers: [enquiryService]
})
export class EnquiryComponent implements OnChanges, OnInit {
  constructor(private networkService: NetworkService,
              private enquiry: enquiryService,
              private commonFunc: commonService,
              public ref: ChangeDetectorRef,
              private router: Router,
              private user: User) {
    this.ref = ref;
    this.rPlotValue = '';
  }

  //城市列表
  cityList: any = [];
  //城市列表的值是否为空
  isCityEmpty: boolean = true;
  cityValue: string;
  //房屋类型
  houseList: any = [];
  //房屋列表的值是否为空
  isHouseEmpty: boolean = true;
  houseValue: string;
  //显示是世联，城市还是全都有
  unitList: any = [];
  //左边显示
  isL: boolean;
  lValue: string;
  //右边显示R
  isR: boolean;
  rValue: string;
  //世联小区数据列表
  kSLDataLists: any = [];
  //城市小区列表
  kCityDataLists: any = [];
  //左边的companyCode
  lHouseNo: string;
  //右边的companyCode
  rHouseNo: string;
  rPlotValue: string;
  //lHouseNo=='001'===》左边是世联===>select框显示
  isVilla: boolean;
  //左侧的楼栋号
  lBuildingValue: string;
  //左侧的房号
  lHomeValue: string;
  //当前选中小区楼道号列表  左边
  kSelectBuildNunLists: any = [];
  //右边的小区楼道号的列表
  kCSelectBuildNunLists: any = [];
  //当前选中 房号列表  左边
  kSelectHouseNumList: any = [];
  //房号列表  右边
  kCSelectHouseNumList: any = [];
  //每次都要传给后台的东西
  serialNo: string;
  //点击提交的时候   要传给后端的值
  towards: string;
  yearBuilt: string;

  //小区地址/名称关键字搜索
  keyCode: string = '';
  buildNo: string = '';
  //世联的楼栋号  获取房号的话  需要传入CompanyCode
  houseComanyCode: string;

  //世联的标记
  slMark: string = '001';
  //城市评估的标记
  cityMark: string = '002';
  //别墅标志
  villaMark: string = '002';
  //行政区域列表
  administrativeArea: any = [];


  //当左边是世联的时候  右边的房号会跟着左边的房号发生改变
  houseName2: string = '';
  //总楼层
  allFloor: string;
  //所在楼层
  currentFloor: string;
  //建筑面积
  area: string;
  //房屋地址
  houseAddress: string;
  //别墅对应的建筑面积
  villaArea: string;

  //是否销毁组件
  isDestroy:boolean = false;


  //询价提交数据校验时，当两家询价公司 其中一家数据通过 另外一家存在空问题时提示信息
  validNotiString: string = '';


  itemStatusLists: any = ["房产估价", "进件申请", "风控审批", "合同制作", "协议公证", "抵押登记", "完成放款"];
  itemSelectStatus: string = '风控审批';
  itemSelectIndex: number = 0;

  userDataKe = this.user.getUserData();
  timeStamp = this.commonFunc.getloginToken();


  setSelectIndex() {
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if (this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }

    }
  }

  ngOnInit() {
    // this.networkService.popNextFailModal("系统错误","系统错误",false);
    // this.networkService.popNextFailModal("系统错误","系统错误",true,function () {
    //   alert('hello');
    // });

    // this.enquiry.ValidateId("buildNo", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");

    //页面初始化赋值
    this.initUITestData();
    this.setVaildDataFunc();
    var that:any = this;
    // $('.msg-input').bind('input propertychange',function(){
    //   var obj:any = this;
    //   var objID:string = obj.id;
    //   if(objID == 'buildArea' || objID == 'buildArea2'){
    //     that.clearErrorNotiInfoAfterInput(objID,false);
    //     that.clearErrorNotiInfoAfterInput(objID+'2',false);
    //   }else if(objID == 'buildNameSearchInputID'){
    //      return;
    //   }else if(objID == 'currentFloor' || objID == 'totalFloor'){
    //     that.clearErrorNotiInfoAfterInput(objID,true);
    //     that.clearErrorNotiInfoAfterInput(objID+'2',true);
    //   }
    //
    // });

 }

 //设置 当提交后 页面数据为空补充数据时，消除对应的错误提示
  //true -正整数  false-两位小数
  clearErrorNotiInfoAfterInput(divID:string,vaildFlag:boolean){
        var needCheckObj = {
          "id": divID,
          "myErrorPosition": "bottom",
          "myMargainLeft": this.enquiry.tipsMarginLeft,
          "errorText": "不能为空"
        };
        //输入框校验验空
       this.enquiry.Validates.checkNull(divID, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");//城市
       this.enquiry.checkObjFormat(needCheckObj, vaildFlag);

  }


  //设置校验方法
  setVaildDataFunc() {
    //页面初始化验证
    $(".selectmsg").select2({});
    // this.enquiry.Validates.checkNullForSelect2("city", 'bottom', this.enquiry.tipsMarginLeft, "不能为空");

    this.enquiry.ValidateIdForSelect2("city", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("house", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //小区地址
    this.enquiry.ValidateIdForSelect2("lPlot", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("rPlot", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //楼栋号
    this.enquiry.ValidateIdForSelect2("buildNo1", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("buildNo2", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("buildNo3", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("buildNo4", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //  房号
    this.enquiry.ValidateId("houseNo2", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    // this.enquiry.ValidateId("houseNo4", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("houseNo1", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateIdForSelect2("houseNo3", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //  总楼层  校驗正整數
    this.enquiry.ValidateId("totalFloor", this.enquiry.Validates.checkNumber2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //  所在楼层 校验正整数
    this.enquiry.ValidateId("currentFloor", this.enquiry.Validates.checkNumber2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //建筑面积  校验带两位小数
    this.enquiry.ValidateId("buildArea", this.enquiry.Validates.checkNumber, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");


    // 房屋类型是别墅时进行的校验
    //  行政区域校验
    this.enquiry.ValidateIdForSelect2("administrative", this.enquiry.Validates.checkNullForSelect2, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //  房屋地址校验
    this.enquiry.ValidateId("houseAddress", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    //  建筑面积校验
    this.enquiry.ValidateId("villa", this.enquiry.Validates.checkNumber, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");

  }


  //清空右边表单的方法
  clearRightFrom(){
    // $("#buildNo3").val('').trigger("change");
    // $("#buildNo4").val('').trigger("change");
    this.kCSelectBuildNunLists=[];
    $("#buildNoSearch2").val('');
    // $("#houseNo3").val('').trigger("change");
    if(this.rValue=='世联'){
      this.kSelectHouseNumList=[];
    }
    // $("#houseNo4").val('');
  //  清楚错误的样式
   var vaildObjArr = ['buildNo3','buildNo4','houseNo3','houseNo4'];
   this.clearSelctConditionChangeCallBack(vaildObjArr);
  }
  //清空左边表单的方法
  clearLeftFrom(){
    // $("#buildNo1").val("").trigger("change");
    // $("#buildNo2").val("").trigger("change");
    this.kSelectBuildNunLists=[];
    $("#buildNoSearch1").html('');
    // $("#houseNo1").val("").trigger("change");
   this.kSelectHouseNumList=[];
    $("#houseNo2").val('');
    $("#houseNo4").val('');
    this.allFloor='';
    this.currentFloor='';
    this.area='';
  //  清除左边的错误样式
    $(".error-input").removeClass("error-input");
    var vaildObjArr = ['buildNo1','buildNo2','buildNoSearch1','houseNo1','houseNo2','totalFloor','totalFloor2','currentFloor','currentFloor2','buildArea','buildArea2'];
   this.clearSelctConditionChangeCallBack(vaildObjArr);
  }
  clearSelctConditionChangeCallBack(vaildObjArr:any){
     var inputLists = document.querySelectorAll('div.error-container');
    for (var i = 0; i < inputLists.length; i++) {
      var nodeObj = <any>inputLists[i];
      var nodeObjIDStr:string = nodeObj.id;
      var nodeIDArr = nodeObjIDStr.split('-');
      if(nodeIDArr.length > 1){
         var vaildStr  = nodeObjIDStr[0];
         if($.inArray(vaildStr,vaildObjArr)){
               nodeObj.remove();
          }
      }

    }


  }

  ngOnDestroy() {
    var inputLists = document.querySelectorAll('div.error-container');
    for (var i = 0; i < inputLists.length; i++) {
      var nodeObj = <any>inputLists[i];
      nodeObj.remove();
    }
  }

  initUITestData() {

    var cityCode2 = this.user.getUserData().cityCode;

    var that = this;
    //初始化发送请求
    var Obj = {
      'head': {
        'functionNo': 'HH000101',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "cityCode": cityCode2
      }
    };
    this.networkService.postData2(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;

          this.cityList = userDataObj.cityList;
          this.houseList = userDataObj.houseTypeList;
          this.serialNo = userDataObj.serialNo;

        }

        if (this.cityList.length == 1) {
          var cityCode = this.commonFunc.handleNilString(this.cityList[0].cityCode);
          this.cityValue = cityCode;
          //如果直接在dom
          $('#city').attr("disabled", true);
          this.isCityEmpty = false;
        }

        if (this.houseList.length == 1) {
          var cityCode = this.commonFunc.handleNilString(this.cityList[0].cityCode);
          this.cityValue = cityCode;
          //如果直接在dom
          $('#house').attr("disabled", true);
          this.isCityEmpty = false;
        }
      }
    });
    // this.cityList = this.enquiry.cityList;
    //当城市列表的长度为1时

    //当城市和房屋类型任一为空时  则下面的内容不现实
    $("#city").change(function () {
      that.isCityEmpty = that.enquiry.isEmpty("city");
      if(that.isDestroy){
        $(".error-input").removeClass("error-input");

        var inputLists = document.querySelectorAll('div.error-container');
        for (var i = 0; i < inputLists.length; i++) {
          var nodeObj = <any>inputLists[i];
          nodeObj.remove();
        }

        that.isDestroy=false;
      }
      var cityValue = $("#city").val();
      //当城市的值发生改变  且不为空
      if (cityValue) {
        var Obj = {
          'head': {
            'functionNo': 'HH000102',
            'userNo': that.userDataKe.userNo,
            'clientTimestamp': that.timeStamp
          },
          'body': {
            "cityCode": cityValue,
            "serialNo": that.serialNo
          }
        };
        that.networkService.postData2(Obj, false).then(res => {
          if (res.retCode == true) {
            // 请求网络通信处理方法
            //res 是后台返回来的数据
            //返回0000
            if (that.networkService.onJudgeSuccessful(res)) {
              //登录成功处理逻辑
              var userDataObj: any = res.data.body;
              var headDataObj: any = res.data.head;
              that.unitList = userDataObj.companyList;
              if (that.unitList) {
                if (that.unitList.length == 0) {
                  that.isL = false;
                  that.isR = false;
                }
                if (that.unitList.length == 1) {
                  that.isL = true;
                  that.isR = false;
                  that.lValue = that.unitList[0].companyName;
                  that.lHouseNo = that.unitList[0].companyCode;

                }
                if (that.unitList.length == 2) {
                  that.isL = true;
                  that.isR = true;
                  that.lValue = that.unitList[0].companyName;
                  that.rValue = that.unitList[1].companyName;

                  that.lHouseNo = that.unitList[0].companyCode;
                  that.rHouseNo = that.unitList[1].companyCode;

                }
              }
            }
            if (that.lHouseNo == that.slMark || that.rHouseNo == that.slMark) {
              if ($("#house").val() == that.villaMark) {
                var cityValue = $("#city").val();
                var Obj2 = {
                  'head': {
                    'functionNo': 'HH000106',
                    'userNo': that.userDataKe.userNo,
                    'clientTimestamp': that.timeStamp
                  },
                  'body': {
                    "cityCode": cityValue
                  }
                };
                that.networkService.postData2(Obj2, false).then(res => {
                  if (res.retCode == true) {
                    // 请求网络通信处理方法
                    //res 是后台返回来的数据
                    //返回0000
                    if (that.networkService.onJudgeSuccessful(res)) {
                      //登录成功处理逻辑
                      var userDataObj: any = res.data.body;
                      var headDataObj: any = res.data.head;
                      that.administrativeArea = userDataObj.areaList;
                    }
                  }
                });
              }
            }

          }
        });

      }

      if (that.lHouseNo == that.slMark || that.rHouseNo == that.slMark) {
        if ($("#house").val() == that.villaMark) {
          var cityValue = $("#city").val();
          var Obj2 = {
            'head': {
              'functionNo': 'HH000106',
              'userNo': that.userDataKe.userNo,
              'clientTimestamp': that.timeStamp
            },
            'body': {
              "cityCode": cityValue
            }
          };
          that.networkService.postData2(Obj2, false).then(res => {
            if (res.retCode == true) {
              // 请求网络通信处理方法
              //res 是后台返回来的数据
              //返回0000
              if (that.networkService.onJudgeSuccessful(res)) {
                //登录成功处理逻辑
                var userDataObj: any = res.data.body;
                var headDataObj: any = res.data.head;
                that.administrativeArea = userDataObj.areaList;
              }

              // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
              //   // alert('9999');
              //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
              // } else if(res.data.head.returnCode == this.networkService.SYSTEM_DISABLE_TOKEN){
              //   // alert('token失效');
              //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
              //     this.router.navigate(['/login']);
              //   })
              // }

            }
          });
        }
      }

    });

    $("#house").change(function () {
      //来去切换的时候  去掉错误样式
      that.isHouseEmpty = that.enquiry.isEmpty("house");
      if(that.isDestroy){
        $(".error-input").removeClass("error-input");

        var inputLists = document.querySelectorAll('div.error-container');
        for (var i = 0; i < inputLists.length; i++) {
          var nodeObj = <any>inputLists[i];
          nodeObj.remove();
        }

        that.isDestroy=false;
      }
      if ($("#house").val() == '002') {
        that.isVilla = true;
        // $(".error-input").removeClass("error-input");
        //  如果有世联的话  则会发送请求
      } else {
        that.isVilla = false;
      }

      var cityValue = $("#city").val();

      //当城市的值发生改变  且不为空
      if (cityValue) {
        var Obj = {
          'head': {
            'functionNo': 'HH000102',
            'userNo': that.userDataKe.userNo,
            'clientTimestamp': that.timeStamp
          },
          'body': {
            "cityCode": cityValue,
            "serialNo": that.serialNo
          }
        };
        that.networkService.postData2(Obj, false).then(res => {
          if (res.retCode == true) {
            // 请求网络通信处理方法
            //res 是后台返回来的数据
            //返回0000
            if (that.networkService.onJudgeSuccessful(res)) {
              //登录成功处理逻辑
              var userDataObj: any = res.data.body;
              var headDataObj: any = res.data.head;
              that.unitList = userDataObj.companyList;
              if (that.unitList) {
                if (that.unitList.length == 0) {
                  that.isL = false;
                  that.isR = false;
                }
                if (that.unitList.length == 1) {
                  that.isL = true;
                  that.isR = false;
                  that.lValue = that.unitList[0].companyName;
                  that.lHouseNo = that.unitList[0].companyCode;
                }
                if (that.unitList.length == 2) {
                  that.isL = true;
                  that.isR = true;
                  that.lValue = that.unitList[0].companyName;
                  that.rValue = that.unitList[1].companyName;

                  that.lHouseNo = that.unitList[0].companyCode;
                  that.rHouseNo = that.unitList[1].companyCode;

                }
              }
              if (that.lHouseNo == that.slMark || that.rHouseNo == that.slMark) {
                if ($("#house").val() == that.villaMark) {
                  var cityValue = $("#city").val();
                  var Obj2 = {
                    'head': {
                      'functionNo': 'HH000106',
                      'userNo': that.userDataKe.userNo,
                      'clientTimestamp': that.timeStamp
                    },
                    'body': {
                      "cityCode": cityValue
                    }
                  };
                  that.networkService.postData2(Obj2, false).then(res => {
                    if (res.retCode == true) {
                      // 请求网络通信处理方法
                      //res 是后台返回来的数据
                      //返回0000
                      if (that.networkService.onJudgeSuccessful(res)) {
                        //登录成功处理逻辑
                        var userDataObj: any = res.data.body;
                        var headDataObj: any = res.data.head;
                        that.administrativeArea = userDataObj.areaList;
                      }

                      // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
                      //   // alert('9999');
                      //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
                      // } else if(res.data.head.returnCode == this.networkService.SYSTEM_DISABLE_TOKEN){
                      //   // alert('token失效');
                      //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
                      //     this.router.navigate(['/login']);
                      //   })
                      // }

                    }
                  });
                }
              }

            }
          }
        });
      }
    });

    //显示左右世联评估与城市评估相对应的关系

    //左右两边的值的列表
    //当右侧的小区地址的值发生变化的时候
    $("#rPlot").change(function () {
      that.clearRightFrom();

    });

    // 当左侧的小区地址的值发生改变的时候
    $("#lPlot").change(function () {
      //当小区地址改变时  清除小区列表以外的其他表单
      that.clearLeftFrom();


      //左侧评估中 小区地址的值
      var lPlot = $("#lPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();
      //若选项为空，清空2家询价公司(或1家)的楼栋号列表和房号列表
      var plotsCode = $(this).val();
      if (that.enquiry.isEmpty("lPlot")) {
      }
    });
    $("#buildNo1").change(function () {

      var unitCode = $(this).val();


      var Obj = {
        'head': {
          'functionNo': 'HH000105',
          'userNo': that.userDataKe.userNo,
          'clientTimestamp': that.timeStamp
        },
        'body': {
          "serialNo": that.serialNo,
          "companyCode": that.lHouseNo,
          "unitCode": unitCode
        }
      };
      that.networkService.postData2(Obj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法
          //res 是后台返回来的数据
          //返回0000
          if (that.networkService.onJudgeSuccessful(res)) {
            var userDataObj: any = res.data.body;
            var headDataObj: any = res.data.head;
            //  将请求到的房号渲染到页面
            that.kSelectHouseNumList = userDataObj.houseList;

          }

          // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
          //   // alert('9999');
          //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
          // } else if(res.data.head.returnCode == that.networkService.SYSTEM_DISABLE_TOKEN){
          //   // alert('token失效');
          //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
          //     this.router.navigate(['/login']);
          //   })
          // }

        }
      });
    });
    $("#buildNo3").change(function () {

      var unitCode = $(this).val();

      var Obj = {
        'head': {
          'functionNo': 'HH000105',
          'userNo': that.userDataKe.userNo,
          'clientTimestamp': that.timeStamp
        },
        'body': {
          "serialNo": that.serialNo,
          "companyCode": that.houseComanyCode,
          "unitCode": unitCode
        }
      };
      that.networkService.postData2(Obj, false).then(res => {
        if (res.retCode == true) {
          // 请求网络通信处理方法
          //res 是后台返回来的数据
          //返回0000
          if (that.networkService.onJudgeSuccessful(res)) {
            var userDataObj: any = res.data.body;
            var headDataObj: any = res.data.head;
            //  将请求到的房号渲染到页面
            that.kSelectHouseNumList = userDataObj.houseList;

          }

          // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
          //   // alert('9999');
          //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
          //
          // } else if(res.data.head.returnCode == that.networkService.SYSTEM_DISABLE_TOKEN){
          //   // alert('token失效');
          //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
          //     this.router.navigate(['/login']);
          //   })
          // }

        }
      });
    });

    $("#houseNo1").change(function () {
      console.log($(this).find("option:selected").text().replace(/[\n]/ig,'').trim());
      that.houseName2 = $(this).find("option:selected").text().replace(/[\n]/ig,'').trim();
    })
  }

//根据输入小区内容 搜索小区地址
  SearchBtnClick() {
    //清除右边的值
    this.clearRightFrom();

    //清楚左边的值
    this.clearLeftFrom();

    if(this.enquiry.isEmpty("keyCode")){
      this.networkService.popNextSuccessModal("询价提醒","小区地址/名称关键字不可以为空",false);
      return false;
    }
    var cityValue = $("#city").val();
    var Obj = {
      'head': {
        'functionNo': 'HH000103',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "cityCode": cityValue,
        "plotsName": this.keyCode,
        "serialNo": this.serialNo
      }
    };

    this.networkService.postData2(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          var userDataObj: any = res.data.body;
          //001是世联   002是城市
          //如果列表的值是两个的话  001==>世联  002==>城市
          if (userDataObj.allPlotsList.length == 2) {
            this.kSLDataLists = userDataObj.allPlotsList[0].plotsList;
            this.kCityDataLists = userDataObj.allPlotsList[1].plotsList;
            var that = this;
            //  如果左边是世联的话,右边是城市的话
            if (this.lHouseNo == '001') {
              this.houseComanyCode = '001';
              $("#lPlot").change(function () {
                //左侧评估中 小区地址的值
                var lPlot = $("#lPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();
                var plotsCode = $(this).val();
                if (!that.enquiry.isEmpty("lPlot")) {
                  //  发送请求以后  将会获得楼栋号
                  var Obj = {
                    'head': {
                      'functionNo': 'HH000104',
                      'userNo': that.userDataKe.userNo,
                      'clientTimestamp': that.timeStamp
                    },
                    'body': {
                      "serialNo": that.serialNo,
                      "companyCode": that.lHouseNo,
                      "plotsCode": plotsCode,
                    }
                  };
                  that.networkService.postData2(Obj, false).then(res => {
                    if (res.retCode == true) {
                      // 请求网络通信处理方法
                      //res 是后台返回来的数据
                      //返回0000
                      if (that.networkService.onJudgeSuccessful(res)) {
                        var userDataObj: any = res.data.body;
                        var headDataObj: any = res.data.head;
                        that.kSelectBuildNunLists = userDataObj.unitList;
                      }

                      // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
                      //   // alert('9999');
                      //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
                      //
                      // }else if(res.data.head.returnCode == that.networkService.SYSTEM_DISABLE_TOKEN){
                      //   // alert('token失效');
                      //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
                      //     this.router.navigate(['/login']);
                      //   })
                      // }


                    }
                  });
                }
              });
            }
            //  如果左边是城市的话,右边是世联的话
            if (that.lHouseNo == '002') {
              this.houseComanyCode = '002';
              $("#rPlot").change(function () {
                //左侧评估中 小区地址的值
                var rPlot = $("#rPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();
                var plotsCode = $(this).val();
                if (!that.enquiry.isEmpty("rPlot")) {
                  //  发送请求以后  将会获得楼栋号
                  var Obj = {
                    'head': {
                      'functionNo': 'HH000104',
                      'userNo': that.userDataKe.userNo,
                      'clientTimestamp': that.timeStamp
                    },
                    'body': {
                      "serialNo": that.serialNo,
                      "companyCode": that.rHouseNo,
                      "plotsCode": plotsCode,
                    }
                  };
                  that.networkService.postData2(Obj, false).then(res => {
                    if (res.retCode == true) {
                      // 请求网络通信处理方法
                      //res 是后台返回来的数据
                      //返回0000
                      if (that.networkService.onJudgeSuccessful(res)) {
                        var userDataObj: any = res.data.body;
                        var headDataObj: any = res.data.head;
                        that.kCSelectBuildNunLists = userDataObj.unitList;
                      }

                      // else if (res.data.head.returnCode == that.networkService.SYSTEM_EXCEPTION) {
                      //   // alert('9999');
                      //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
                      //
                      // }else if(res.data.head.returnCode == that.networkService.SYSTEM_DISABLE_TOKEN){
                      //   // alert('token失效');
                      //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
                      //     this.router.navigate(['/login']);
                      //   })
                      // }

                    }
                  });
                }

              });
            }
          }
          //如果只有一个的话
          if (userDataObj.allPlotsList.length == 1) {
            this.kSLDataLists = userDataObj.allPlotsList[0].plotsList;
            //如果是世联的话
            var that =this;
            if (this.lHouseNo == '001') {
              $("#lPlot").change(function () {
                //左侧评估中 小区地址的值
                var lPlot = $("#lPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();
                var plotsCode = $(this).val();
                if (!that.enquiry.isEmpty("lPlot")) {
                  //  发送请求以后  将会获得楼栋号
                  var Obj = {
                    'head': {
                      'functionNo': 'HH000104',
                      'userNo': that.userDataKe.userNo,
                      'clientTimestamp': that.timeStamp
                    },
                    'body': {
                      "serialNo": that.serialNo,
                      "companyCode": that.lHouseNo,
                      "plotsCode": plotsCode,
                    }
                  };
                  that.networkService.postData2(Obj, false).then(res => {
                    if (res.retCode == true) {
                      // 请求网络通信处理方法
                      //res 是后台返回来的数据
                      //返回0000
                      if (that.networkService.onJudgeSuccessful(res)) {
                        var userDataObj: any = res.data.body;
                        var headDataObj: any = res.data.head;
                        that.kSelectBuildNunLists = userDataObj.unitList;

                      }
                    }
                  });
                }


              });
            }
            if (this.lHouseNo == '002') {
              // this.SearchBtn(this.lHouseNo,event,this.kSelectBuildNunLists);
            }
          }
        }
      }
    });

  }

  /*校验流程方法：
   一 当存在两家估价公司的时候
   1.当两家估价公司中存在格式错误，弹框提示该估价公司提交参数中存在格式错误 弹窗方法
   2.当两家股价公司中 存在一家数据校验通过另外一家仅存在空数据 提示是否继续评估  弹窗方法
   二，当仅存在一家估价公司的时候
   1.逐项判断，提示用用户空或者数据问题
   */

  //流程控制方法
  ValidSubmitCompanyData() {
    //判断当前存在几家公司 并调用对应方法
    var validResult = false;
    if (this.unitList.length == 2) {
      validResult = this.validDoubleCompanySubmitData();
    } else {
      validResult = this.validSingleCompanySubmitData();
    }
    return validResult;
  }


  //仅存在一家评估公司的情况 当提交数据不符合规范-true  符合-false
  validSingleCompanySubmitData() {
    //判断当前公司是哪一家
    var validResult: boolean = false;
    var handleObjData = [];//当前公司数据
    var resultObj: any = {};//当前处理结果
    var curentCompany: string = '';
    //当左边为城市 右边为世联
    if (this.lHouseNo == '002') {
      //城市数据
      handleObjData = ["lPlot", "buildNo2", "houseNo2", "totalFloor", "currentFloor", "buildArea"];
      resultObj = this.validGJCompanyData(handleObjData, true);
      curentCompany = "城市";
    } else if (this.lHouseNo == '001') {
      //世联数据
      handleObjData = ["lPlot", "buildNo1", "houseNo1", "totalFloor", "currentFloor", "buildArea"];
      resultObj = this.validGJCompanyData(handleObjData, false);
      curentCompany = "世联";
    }
    var checkResult = resultObj["result"];
    var FailItemList: any = resultObj["failItem"];
    var NilItem: any = resultObj["nilItem"];
    //当前公司提交数据出现错误格式时 提示用户
    if (!checkResult && (FailItemList.length > 0)) {
      //提示用户数据格式错误
      validResult = true;
      this.popViewFlag = '1';
      this.popContentStr = curentCompany+'评估房产信息错误，请修改';

    }

    //当前公司提交数据 出现空时 提示用户
    if (!checkResult && (NilItem.length > 0)) {
      //提示用户数据格式错误
      validResult = true;
      this.popViewFlag = '1';
      this.popContentStr = curentCompany+'评估房产信息错误，请修改';


    }

    return validResult;

  }

  //1-弹框无回调事件（存在错误  两家都为空 当前只有一家且为空）2-家通过一家存在空
  popViewFlag:string = '';
  popContentStr:string = '';
  onSubmitPopViewAfterValidData(){
    if(this.popViewFlag == '1'){
      this.networkService.popNextSuccessModal("房产信息错误",this.popContentStr,false);

    }else if(this.popViewFlag == '2'){
        // 有一个估计公司通过的场景
        var notiStrArr = this.validNotiString.split('，');
        var content = notiStrArr[1]+"选项有空，点击确定提交"+notiStrArr[0]+"评估，后续前往询价结果查询页面输入评估结果；点击取消修改房产信息";
         this.networkService.popNextSuccessModal('提示',content,true,()=>{
             this.submitEnquiryDataFunc();
         });
    }else{

    }

  }


  //存在两家评估公司的情况
  //当提交数据不符合规范-true  符合-false
  validDoubleCompanySubmitData() {
    /*
     1.组织两家评估公司的校验数据
     2.分别判断两家评估公司 数据结果，若过程中出现数据格式错误 则弹框提示 终止流程；否则继续执行 校验通过与校验存在空的情况
     3.若两家均存在空的现象：则提示两家均存在空现象 提示用户继续选择
     4.一家通过 一家为空的时候，提示另外一家存在空现象 是否提交数据
     5.若两家公司均通过 则直接提交数据
     */

    //第一步 区别估价公司 并取出对应数据
    //当左边为世联 右边为城市
    var validResult = false;
    var SLObjData = ["lPlot", "buildNo1", "houseNo1", "totalFloor", "currentFloor", "buildArea"];
    // var cityObjData = ["rPlot", "buildNo4", "houseNo4", "totalFloor2", "currentFloor2", "buildArea2"];
    //右边是禁用的话   不需要校验
    var cityObjData = ["rPlot", "buildNo4"];
    //当左边为城市 右边为世联
    if (this.lHouseNo == '002') {
      SLObjData = ["rPlot", "buildNo2", "houseNo2", "totalFloor2", "currentFloor2", "buildArea2"];
      cityObjData = ["lPlot", "buildNo4", "houseNo4", "totalFloor", "currentFloor", "buildArea"];
    }

    //第二步
    //判断世联数据 并处理过程中出现数据错误问题
    var SLDataCheckResult = this.validGJCompanyData(SLObjData, false);
    var SLCheckResult = SLDataCheckResult["result"];
    var SLFailItemList: any = SLDataCheckResult["failItem"];
    var SLNilItemList: any = SLDataCheckResult["nilItem"];

    //判断城市数据  并处理相关流程
    var cityDataCheckResult = this.validGJCompanyData(cityObjData, true);
    var cityCheckResult = cityDataCheckResult["result"];
    var cityFailItem: any = cityDataCheckResult["failItem"];
    var cityNilItem: any = cityDataCheckResult["nilItem"];


    //第三步 若两家均存在空的现象：则提示两家均存在空现象 提示用户继续选择
    if ((!SLCheckResult && (SLNilItemList.length > 0)) && (!cityCheckResult && (cityNilItem.length > 0))) {
      //弹窗提示  两家均存在数据为空
      validResult = true;
      this.popViewFlag = '1';
      if(this.lHouseNo=='001'){
        this.popContentStr = '世联，城市评估房产信息错误，请修改';

      }else {
        this.popContentStr = '城市，世联评估房产信息错误，请修改';
      }
    }

    //第四步 一家通过 一家为空的时候，提示另外一家存在空现象 是否提交数据
    var cityCurrtNull = !cityCheckResult && (cityNilItem.length > 0);
    var SLCurrtNull = !SLCheckResult && (SLNilItemList.length > 0);
    if (SLCheckResult && cityCurrtNull) {
      //世联通过 城市为空
      //提示用户 世联通过 城市为空  并在确定时候提交世联数据
      validResult = true;
      this.popViewFlag = '2';
      this.validNotiString = "世联，城市";
    } else if (cityCheckResult && SLCurrtNull) {
      //城市通过 世联为空
      //提示用户 城市通过 世联为空  并在确定时候提交城市数据
      validResult = true;
      this.popViewFlag = '2';
      this.validNotiString = "城市，世联";

    }

    //第四步 当某家询价公司出现数据错误现象时
    if (!SLCheckResult && (SLFailItemList.length > 0)) {
      //弹窗提示  世联存在错误数据
      validResult = true;
      this.validNotiString = '';
      this.popViewFlag = '1';
      this.popContentStr = '世联评估房产信息错误，请修改';

    }
    if (!cityCheckResult && cityFailItem != '') {
      //弹窗提示  城市存在错误数据
      validResult = true;
      this.validNotiString = '';
      this.popViewFlag = '1';
      this.popContentStr = "城市评估房产信息错误，请修改";
    }

    //第五步 若两家公司均通过 则直接提交数据
    if (SLCheckResult && cityCheckResult) {
      //调用提交方法
      validResult = false;
      this.validNotiString = "";

    }


    return validResult;

  }

  /*入参：objData-当前估价公司校验项目 顺序：小区地址 楼栋号 房号 总楼层 所在楼层 建筑面积
   flag-当前公司  true-城市 false-世联
   */
  validGJCompanyData(objData: any, flag: boolean) {
    var checkItems = ['小区地址', '楼栋号', '房号', '总楼层', '所在楼层', '建筑面积'];
    // 当前是否通过校验
    var result: boolean = true;
    //当前存在格式错误是哪一项
    var failItemList: any = [];
    //当前存在空项列表
    var nilItemList: any = [];
    //遍历当前公司提交数据获取当前信息合法性
    for (let index = 0; index < objData.length; index++) {
      var itemResultNull;
      var itemResultFail;
      if (index <= 2) {
        //选择框校验
        //根据估价公司判断第三项校验方法
        if (index == 2 && flag) {
          //城市
          //校验数据格式是否为空
          itemResultNull = this.enquiry.Validates.checkNull(objData[index], 'bottom', this.enquiry.tipsMarginLeft, "不能为空");//城市
        } else {
          //世联
          itemResultNull = this.enquiry.Validates.checkNullForSelect2(objData[index], 'bottom', this.enquiry.tipsMarginLeft, "不能为空");

        }
        if (!itemResultNull) {
          var nilItem = checkItems[index];
          nilItemList.push(nilItem);
          result = false;//当前公司校验数据不通过 存在空项
        }

      } else {
        //验证数据格式问题
        var needCheckObj = {
          "id": objData[index],
          "myErrorPosition": "bottom",
          "myMargainLeft": this.enquiry.tipsMarginLeft,
          "errorText": "不能为空"
        };

        //输入框校验验空
        itemResultNull = this.enquiry.Validates.checkNull(objData[index], 'bottom', this.enquiry.tipsMarginLeft, "不能为空");//城市
        if (!itemResultNull) {
          var nilItem = checkItems[index];
          nilItemList.push(nilItem);
          result = false;//当前公司校验数据不通过 存在空项
        }


        if (index == objData.length - 1) {
          //验证两位小数
          itemResultFail = this.enquiry.checkObjFormat(needCheckObj, false);
        } else {
          //验证正整数
          itemResultFail = this.enquiry.checkObjFormat(needCheckObj, true);
        }

        if (!itemResultFail) {
          result = false;
          failItemList.push(checkItems[index]);
        }


      }
    }

    var retOBj = {"result": result, "failItem": failItemList, "nilItem": nilItem};
    return retOBj;
  }


  //跳转至查看详情页面
  onFirstOperationClick(itemObj) {

    this.commonFunc.setBusinessData(itemObj);
    this.commonFunc.setBusinsessSaveFlagFunc();
    this.router.navigate(['/home/enquiryDetail']);

  }
  //公寓评估
  assess() {
    this.commonFunc.setEnquiryPath("gy");
    this.isDestroy=true;
    //判断当前提交数据是否合法
    var vaildResult =  this.ValidSubmitCompanyData();
     //当校验条件不通过时，提示用户信息
    if(vaildResult){
      this.onSubmitPopViewAfterValidData();
      return;
    }
     this.submitEnquiryDataFunc();
  }
//提交询价信息
  submitEnquiryDataFunc(){
    // alert('exe');
    $("#totalFloor2-error").css("display","none");


    // $("#currentFloor2").removeTip();
    $("#currentFloor").removeTip();
    // //如果左边是世联 右边是城市
    if (this.lHouseNo == '001') {
      var buildNoL = $("#buildNo1").val();
      var buildNoR = $("#buildNo4").val();  //===>unitCode

      var buildLName = $("#buildNo1").find("option:selected").text().replace(/[\n]/ig,'').trim();
      buildLName=buildLName.replace(/[\n]/ig,'');
      var buildRName = $("#buildNo4").find("option:selected").text().replace(/[\n]/ig,'').trim();

      var paramsArray = buildNoR.split(",");
      var unitCode = paramsArray[0];

      var towards = paramsArray[1];
      var yearBuilt = paramsArray[2];
      //房号
      var houseNoL = $("#houseNo1").val();
      var houseNoR = $("#houseNo4").val();


      var houseLValue = $("#houseNo1").find("option:selected").text().replace(/[\n]/ig,'').trim();
      var houseRValue = $("#houseNo4").val();

    }

    //如果左边是城市，右边是世联的话
    if (this.lHouseNo == '002') {
      var buildNoL = $("#buildNo2").val();
      var buildNoR = $("#buildNo3").val();

      var buildLName = $("#buildNo2").find("option:selected").text().replace(/[\n]/ig,'').trim();
      var buildRName = $("#buildNo3").find("option:selected").text().replace(/[\n]/ig,'').trim();

      var paramsArray = buildNoL.split(",");
      var unitCode = paramsArray[0];
      //房号
      var houseNoL = $("#houseNo2").val();
      var houseNoR = $("#houseNo3").val();

      var houseLValue = $("#houseNo2").val();
      var houseRValue = $("#houseNo3").find("option:selected").text().replace(/[\n]/ig,'').trim();
    }
    //  获取城市的值
    var cityCode = $("#city").val();
    //  获取房屋类型列表的值
    var houseTypeCode = $("#house").val();
    //登陆时暂存的消息
    var sessionData = this.user.getUserData();

    var companyId = sessionData.distributorCode;
    var companyName = sessionData.distributorName;
    var userName = sessionData.userName;
    var userId = sessionData.userNo;

    var companyParamList = [];

    // 判断unitList的值的长度
    if (this.unitList.length == 2) {
      //小区地址  左边的 code
      var lPlotValue = $("#lPlot").val();
      //小区地址  左边的 name
      var lPlotName = $("#lPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();

      //小区地址  右边的 code
      var rPlotValue = $("#rPlot").val();
      //小区地址  右边的 name
      var rPlotName = $("#rPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();
      //如果左边是世联，右边是城市的话
      if (this.lHouseNo == '001') {
        companyParamList = [
          //左边的值
          {
            "companyCode": this.lHouseNo,
            "plotsCode": lPlotValue,
            "plotsName": lPlotName,
            "unitCode": buildNoL,
            "unitName": buildLName,
            "houseCode": houseNoL,
            "houseName": houseLValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor
          },
          //右边的值
          {
            "companyCode": this.rHouseNo,
            "plotsCode": rPlotValue,
            "plotsName": rPlotName,
            "unitCode": unitCode,
            "unitName": buildRName,
            "houseCode": houseNoR,
            "houseName": houseRValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor,
            "towards": towards,
            "yearBuilt": yearBuilt
          }
        ];
      }
      //如果左边是城市，右边是世联的话
      if (this.lHouseNo == '002') {
        companyParamList = [
          //左边的值
          {
            "companyCode": this.lHouseNo,
            "plotsCode": lPlotValue,
            "plotsName": lPlotName,
            "unitCode": buildNoL,
            "unitName": buildLName,
            "houseCode": houseNoL,
            "houseName": houseLValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor,
            "towards": towards,
            "yearBuilt": yearBuilt

          },
          //右边的值
          {
            "companyCode": this.rHouseNo,
            "plotsCode": rPlotValue,
            "plotsName": rPlotName,
            "unitCode": unitCode,
            "unitName": buildRName,
            "houseCode": houseNoR,
            "houseName": houseRValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor
          }
        ];
      }

    }
    if (this.unitList.length == 1) {
      //如果左边是世联的话
      //小区地址  左边的 code
      var lPlotValue = $("#lPlot").val();
      //小区地址  左边的 name
      var lPlotName = $("#lPlot").find("option:selected").text().replace(/[\n]/ig,'').trim();

      if (this.lHouseNo == '001') {
        companyParamList = [
          //左边的值
          {
            "companyCode": this.lHouseNo,
            "plotsCode": lPlotValue,
            "plotsName": lPlotName,
            "unitCode": buildNoL,
            "unitName": buildLName,
            "houseCode": houseNoL,
            "houseName": houseLValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor
          }
        ];
      }
      if (this.lHouseNo == '002') {
        companyParamList = [
          {
            "companyCode": this.lHouseNo,
            "plotsCode": lPlotValue,
            "plotsName": lPlotName,
            "unitCode": buildNoL,
            "unitName": buildLName,
            "houseCode": houseNoL,
            "houseName": houseLValue,
            "area": this.area,
            "totalFloor": this.allFloor,
            "currentFloor": this.currentFloor,
            "towards": towards,
            "yearBuilt": yearBuilt
          }
        ]
      }
    }

    var assessData = {
      "head": {
        "functionNo": "HH000110",
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      "body": {
        "cityCode": cityCode,
        "houseTypeCode": houseTypeCode,
        "companyId": companyId,
        "companyName": companyName,
        "userName": userName,
        "userId": userId,
        "companyParamList": companyParamList
      }
    }
    this.networkService.postData2(assessData, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          this.commonFunc.setEnquiryStatus("01");
          this.onFirstOperationClick(userDataObj);
        }

        // else if (res.data.head.returnCode == this.networkService.SYSTEM_EXCEPTION) {
        //   // alert('9999');
        //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
        //
        // }else if(res.data.head.returnCode == this.networkService.SYSTEM_DISABLE_TOKEN){
        //   // alert('token失效');
        //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
        //     this.router.navigate(['/login']);
        //   })
        // }
      }
    });
  }

  //别墅评估
  villaAssess() {

    this.commonFunc.setEnquiryPath("villa");
    this.isDestroy=true;

    var administrative = this.enquiry.Validates.checkNullForSelect2("administrative", 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    var houseAddress = this.enquiry.Validates.checkNull("houseAddress", 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    var villa = this.enquiry.Validates.checkNull("villa", 'bottom', this.enquiry.tipsMarginLeft, "不能为空");

     var needCheckObj = {
          "id": "villa",
          "myErrorPosition": "bottom",
          "myMargainLeft": this.enquiry.tipsMarginLeft,
          "errorText": "不能为空"
        };
    var villaFormatResult = this.enquiry.checkObjFormat(needCheckObj, false);

    if(!villaFormatResult){
      //别墅建筑面积格式错误
      this.networkService.popNextSuccessModal("房产信息错误","世联评估房产信息错误，请修改",false);

      return;

    }
    if(!administrative || !houseAddress || !villa){
      //当前页面存在空现象
      this.networkService.popNextSuccessModal("房产信息错误","世联评估房产信息错误，请修改",false);

      return;

    }

    //  获取城市的值
    var cityCode = $("#city").val();
    //  获取房屋类型列表的值
    var houseTypeCode = $("#house").val();

    var administrativeCode = $("#administrative").val();

    var administrativeText = $("#administrative").find("option:selected").text().replace(/[\n]/ig,'').trim();

    var houseAddressValue = $("#houseAddress").val();

    var area = $("#villa").val();


    var Obj = {
      'head': {
        'functionNo': 'HH000109',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "cityCode": cityCode,
        "houseTypeCode": houseTypeCode,
        "serialNo": this.serialNo,
        "companyId": this.userDataKe.distributorCode,
        "companyName": this.userDataKe.distributorName,
        "userName": this.userDataKe.userName,
        "userId": this.userDataKe.userNo,
        "companyParamList": [{
          "companyCode": "001",
          "address": houseAddressValue,
          "area": area,
          "areaCode": administrativeCode,
          "areaName": administrativeText,

        }]
      }
    };

    this.networkService.postData2(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          //自动询价  状态位是01
          this.commonFunc.setEnquiryStatus("01");
          this.onFirstOperationClick(userDataObj);
        }
      }
    });
  }

  SearchBtn1(houseNo, event) {
    var plotsCode = $(event.target).parent().prev().prev().find(".selectmsg").val()
    var Obj = {
      'head': {
        'functionNo': 'HH000104',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "serialNo": this.serialNo,
        "companyCode": houseNo,
        "unitName": this.buildNo,
        "plotsCode": plotsCode
      }
    };
    this.networkService.postData2(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          this.kSelectBuildNunLists = userDataObj.unitList;
        }
      }
    });
  }

  SearchBtn2(houseNo, event) {
    var plotsCode = $(event.target).parent().parent().prev().prev().find(".selectmsg").val()

    var Obj = {
      'head': {
        'functionNo': 'HH000104',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "serialNo": this.serialNo,
        "companyCode": houseNo,
        "unitName": this.buildNo,
        "plotsCode": plotsCode
      }
    };

    this.networkService.postData2(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          this.kCSelectBuildNunLists = userDataObj.unitList;
        } else if (res.data.head.returnCode == this.networkService.SYSTEM_EXCEPTION) {
          // alert('9999');
          this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);

        }else if(res.data.head.returnCode == this.networkService.SYSTEM_DISABLE_TOKEN){
          // alert('token失效');
          this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,function () {
            this.router.navigate(['/login']);
          })
        }
      }
    });
  }

  ngOnChanges(allFloor) {
    console.log('onONchange 函数调用 the function of NGONCHANGES');
  }

}



