import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {commonService} from "../../commonService.service";
import {User} from "../../User.service";
import {NetworkService} from "../../network.service";

declare var $: any;
declare var laydate: any;

@Component({
  selector: 'app-repayment-account-modify',
  templateUrl: './repayment-account-modify.component.html',
  styleUrls: ['./repayment-account-modify.component.css']
})
export class RepaymentAccountModifyComponent implements OnInit {

  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }


  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  creditStartDate: string = '';//开始时间
  creditEndDate: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  cityValue:string;  //城市
  currentItemLists: any=[];

  ngOnInit() {
    //1、日历插件调用
    this.timedicpker();
    this.InitQueryEntryList();

    $("#cityList").select2({});
    $("#channelList").select2({});
    $("#applyStatusList").select2({});
    $("#repaymentOrigin").select2({});
    $("#accountTypeList").select2({});
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
          end.min = datas; //开始日选好后，重置结束日的最小日期
          // end.start = datas //将结束日的初始值设定为开始日
        }
      };
      var end = {
        elem: '#applyTimeEnd',
        format: 'YYYY-MM-DD',
        min: "2000-1-1 00:00:00",
        max: '2099-06-16 23:59:59',
        istime: false,
        istoday: false,
        isclear: true,
        choose: function (datas) {
          start.max = datas; //结束日选好后，重置开始日的最大日期
        }
      };

      $("#applyTimeBegin").bind({
        focusout: function () {
          if ($("#applyTimeBegin").val() == "") {
            end.min = "2000-1-1";
          }
        }
      });
      $("#applyTimeEnd").bind({
        focusout: function () {
          if ($("#applyTimeEnd").val() == "") {
            start.max = laydate.now();
          }
        }
      });

      laydate(start);
      laydate(end);
    });

  }

  ngOnDestroy() {
    //退出当前组件时，需要销毁页面上的日历节点。
    $("#laydate_box").remove();
  }
  //初始化查询查询条件方法start
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100045',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          var cityList = res.data.body.cityList;
          var distributorList=res.data.body.distributorList
          var receiptsRecordStatusList = res.data.body.receiptsRecordStatusList; //
          var accountTypeList = res.data.body.accountTypeList;
          var fundSourceList = res.data.body.fundSourceList;
          //绑定成熟数据
          this.add_option(cityList, '#cityList', ['cityCode', 'cityName']);
          this.add_option(distributorList, '#channelList', ['distributorCode', 'distributorName']);
          this.add_option(receiptsRecordStatusList, '#applyStatusList', ['receiptsRecordStatusCode', 'receiptsRecordStatusName']);
          this.add_option(fundSourceList,'#repaymentOrigin',['fundSourceCode','fundSourceName']);
          this.add_option(accountTypeList, '#accountTypeList', ['accountTypeCode', 'accountTypeName']);

          if (cityList.length == 1) {
            var cityCode = this.commonFunc.handleNilString(cityList[0].cityCode);
            this.cityValue = cityCode;
            $('#cityList').attr("disabled", true);

          }
          //分页查询接口
          this.QueryEntryList()
        }

      }

    });

  }
  //绑定数据 待重构该部分代码
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
  //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {
    var city = this.commonFunc.handleNilString($("#cityList").val()) || this.cityValue;//选择城市
    var distributorCode = this.commonFunc.handleNilString($("#channelList").val());//选择渠道
    var customerName = this.commonFunc.handleNilString($("#loanCustomer").val());//借款人
    var receiptsStartDate = this.commonFunc.handleNilString($("#applyTimeBegin").val()); //还款开始日期
    var receiptsEndDate = this.commonFunc.handleNilString($("#applyTimeEnd").val());     //还款结束日期
    var accountTypeCode = this.commonFunc.handleNilString($("#accountTypeList").val()); //收款方
    var receiptsRecordStatusCode = this.commonFunc.handleNilString($("#applyStatusList").val()); //选择状态
    var fundSourceCode = this.commonFunc.handleNilString($("#repaymentOrigin").val());
    return {
      "cityCode": city,
      "distributorCode": distributorCode,
      "customerName": customerName,
      "receiptsStartDate": receiptsStartDate,
      "receiptsEndDate": receiptsEndDate,
      "accountTypeCode":accountTypeCode,
      "receiptsRecordStatusCode": receiptsRecordStatusCode,
      "fundSourceCode":fundSourceCode
    };

  }

  //贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var searchConditions = this.getSearchCondigtion();
    var Obj = {
      'head': {
        'functionNo': 'plms100046',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'cityCode': searchConditions.cityCode,
        'distributorCode':searchConditions.distributorCode,
        'customerName': searchConditions.customerName,
        'receiptsStartDate': searchConditions.receiptsStartDate,
        'receiptsEndDate': searchConditions.receiptsEndDate,
        'accountTypeCode': searchConditions.accountTypeCode,
        'receiptsRecordStatusCode': searchConditions.receiptsRecordStatusCode,
        'fundSourceCode':searchConditions.fundSourceCode,
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          //登录成功处理逻辑
          var pages = this.commonFunc.handleNilString(res.data.body.pages);
          if (pages == '') pages = '0';
          var currentPage = this.commonFunc.handleNilString(res.data.body.currentPage);
          if (currentPage == '') currentPage = '0';

          var objData = {
            'totalPage': pages,
            'currentPage': currentPage,
            'total': res.data.body.total

          };

          //设置页面显示 第几条至第几条
          this.totalListsCount = res.data.body.total;

          //首次获取分页信息 设置分页插件
          //  if(this.currentItemLists.length == 0){
          this.setPageFunc(objData, this);

          //  }
          this.currentItemLists = res.data.body.dataList;

          this.setDisplayItemsCount();


          //修改查询列表状态 暂时前端映射 待需求确认之后 以后台为准
          //material 充材料为 1-不显示  2-显示

          for (var i = 0; i < this.currentItemLists.length; i++) {
            var obj = this.currentItemLists[i];
            var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
            this.currentItemLists[i]["statusName"] = resultObj['statusName'];
          }

          this.totalItemLists[this.selectPageNo] = this.currentItemLists;


        }
      }

    });
    return false;
  };

  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    var currentObj: any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.currentItemLists.length);
    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }

  }

  // 初始化分页工具
  setPageFunc(data: any, obj: any) {

    //设置分页插件
    $(".tablePageCode").createPage({
      pageCount: data.totalPage,
      current: this.selectPageNo,
      backFn: function (p) {
        //p当前页


        obj.setCurrentDataFunc(p.toString());

      }
    });

  }


  //分页数据处理
  setCurrentDataFunc(page: string) {
    //设置当前选中页数
    this.selectPageNo = page;

    //1.根据当前请求页 查询totalItems中是否存在这条数据 存在则直接赋值与current
    var pageKey: string = page;
    if (this.totalItemLists.hasOwnProperty(pageKey)) {
      var itemObj = this.totalItemLists[pageKey];
      this.currentItemLists = itemObj;

      //显示第几条数据
      this.setDisplayItemsCount();
      return;
    }

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


  }


  //搜索按钮点击事件
  onSearchBtnClick() {

    this.selectPageNo = '1';
    this.QueryEntryList();

  }


  //点击修改按钮执行的函数
  modifyFunc(item){

    this.router.navigate(['/home/repaymentAccountModify',item.receiptsRecordCode]);
    //this.router.navigate(['/home/repaymentAccountModify',item.contractNo]);

  }
  //点击删除按钮执行的函数
  deleteFunc(item){
    var repaymentDateItem=item.receiptsDate;
    var repaymentDateLink=repaymentDateItem.split(' ')[0].split('-');

    var repaymentDate=(~~repaymentDateLink[0])+'年'+(~~repaymentDateLink[1])+'月'+(~~repaymentDateLink[2])+'日';


    this.ensureButtonClick('删除还款到账',
      '将删除借款人<span id="secondValue">'+item.customerName+'</span>于'+repaymentDate+'<span id="secondValue">'+item.receiptsAmount+'</span>元的资金到账记录，确认删除？',true,item);

    // this.ensureButtonClick('删除还款到账',
    //   '将删除借款人<span id="secondValue">'+item.customerName+'</span>于',item.receiptsDate,'<span id="secondValue">'+item.receiptsAmount+'</span>元的资金到账记录，确认删除？',true,item);

  }
  //点击删除按钮出现的弹窗
  ensureButtonClick(tipTitle, tiptxt,flag:boolean,item){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;
    //var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}(${tttt }|date:'yyyy年MM月dd日')${ddd}+flag+item</div>`;

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
      //点击确定就会被删除    向后台进行数据交互  并且再重新请求一遍
      that.deleteList(item);
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }



  }

  //删除接口
  deleteList(item) {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100048',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'receiptsRecordCode':item.receiptsRecordCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑


          //分页查询接口
          this.QueryEntryList()
        }

      }

    });

  }

}
