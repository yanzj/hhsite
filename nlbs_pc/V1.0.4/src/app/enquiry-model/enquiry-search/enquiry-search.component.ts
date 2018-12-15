import {Component, OnInit, OnDestroy} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ComSectionComponent} from "../../common-model/com-section/com-section.component";

// 申请查询
declare var $: any;
declare var laydate: any;
@Component({
  selector: 'app-enquiry-search',
  templateUrl: './enquiry-search.component.html',
  styleUrls: ['./enquiry-search.component.css']
})
export class EnquirySearchComponent implements OnInit {

  currentItemLists: any[] = [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  startTimeStr: string = '';//开始时间
  endTimeStr: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前介绍页数
  applyStatusValue:string = '';

  cityValue:string = '';
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private comSectionComponent:ComSectionComponent,
              private activetedRoute: ActivatedRoute
  ) {
  }


  ngOnInit() {

    this.comSectionComponent.currentLevelMenuId = '2017070315180000000002';
    this.comSectionComponent.currentChildMenuId = '2017070315180000000005';

    //获取页面参数
    this.getApplyStatusParam();

    $("#cityList").select2({});
    $("#applyStatusList").select2({});

    this.timedicpker();

    //初始化查询条件接口信息
    this.InitQueryEntryList();
    //分页查询接口

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
        isclear:true,
        fixed:true,
        choose: function(datas){
          end.min = datas; //开始日选好后，重置结束日的最小日期
          // end.start = datas //将结束日的初始值设定为开始日
        }
      };
      var end = {
        elem: '#applyTimeEnd',
        format: 'YYYY-MM-DD',
        min:"2000-1-1 00:00:00",
        max: '2099-06-16 23:59:59',
        istime: false,
        istoday: false,
        isclear:true,
        choose: function(datas){
          start.max = datas; //结束日选好后，重置开始日的最大日期
        }
      };

      $("#applyTimeBegin").bind({
        focusout: function () {
          if($("#applyTimeBegin").val()==""){
            end.min="2000-1-1";
          }
        }
      });
      $("#applyTimeEnd").bind({
        focusout: function () {
          if($("#applyTimeEnd").val()==""){
            start.max=laydate.now();
          }
        }
      });

      laydate(start);
      laydate(end);
    });

  }


  //获取Link传过来的申请状态参数
  private applyStatus: string = '';
  private sub: any;
  getApplyStatusParam(){
    this.sub = this.activetedRoute.queryParams.subscribe(params => {
      this.applyStatus = params['applyStatus'];
    });
  }


  ngOnDestroy(){
    //退出当前组件时，需要销毁页面上的日历节点。
    $("#laydate_box").remove();

    this.sub.unsubscribe();
  }

  //初始化查询查询条件方法start
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;
    var cityCode = userObj.cityCode;

    var Obj = {
      'head': {
        'functionNo': 'HH000121',
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
          //登录成功处理逻辑
          var cityList = res.data.body.cityList;
          var loanStatusList = res.data.body.statusList;

          //绑定成熟数据
          this.add_option(cityList, '#cityList', ['cityCode', 'cityName']);
          this.add_option(loanStatusList, '#applyStatusList', ['status', 'name']);
          // $("#applyStatusList").val(this.applyStatus);
          $("#applyStatusList").select2().val(this.applyStatus).trigger("change");
          // if(this.comSectionComponent.Unevaluate){
          //   this.comSectionComponent.Unevaluate=false;
          //   //  将状态设置为待评估
          //   this.applyStatusValue='00';
          //   console.log(this.applyStatusValue);
          //   $('#applyStatusList').attr("disabled", true);
          // }

          if (cityList.length == 1) {
            var cityCode = this.commonFunc.handleNilString(cityList[0].cityCode);
            this.cityValue = cityCode;
            //如果直接在dom
            $('#cityList').attr("disabled", true);
            // this.QueryEntryList();
            // console.log(this.cityValue);
          }
        }

      }

      this.QueryEntryList();
    });

  }
  //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {
    var city = this.cityValue;
    if(this.cityValue == ''){
      city = this.commonFunc.handleNilString($("#cityList").val());//选择城市
    }

    var loadStatu = this.commonFunc.handleNilString($("#applyStatusList").val());
    // var startTimeObj = $(".iDate.full").data("DateTimePicker").date();
    // var endTimeObj = $(".iDate.date").data("DateTimePicker").date();
    // var startTime = this.commonFunc.handleNilString(this.commonFunc.getFormatDate(startTimeObj));
    // var endTime = this.commonFunc.handleNilString(this.commonFunc.getFormatDate(endTimeObj));
    var startTime = this.commonFunc.handleNilString($("#applyTimeBegin").val());
    var endTime = this.commonFunc.handleNilString($("#applyTimeEnd").val());


    var address = $("#address").val();
     address = this.commonFunc.handleNilString(address);//借款人
    return {
      "cityCode": city,
      "address": address,
      "startDate": startTime,
      "endDate": endTime,
      "loadStatu": loadStatu
    };
  }


//分页查询列表接口
  QueryEntryList() {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var searchConditions = this.getSearchCondigtion();
    var Obj = {
      'head': {
        'functionNo': 'HH000034',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'startDate': searchConditions.startDate,
        'endDate': searchConditions.endDate,
        'cityCode': searchConditions.cityCode,
        'address': searchConditions.address,
        'status': searchConditions.loadStatu,
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo
      }
    };

    console.log(Obj.body.cityCode);

    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {


          var pages = res.data.body.pages;
          if (pages == '') pages = '0';
          var currentPage = res.data.body.currentPage;
          if (currentPage == '') currentPage = '0';

          var objData = {
            'totalPage': pages,
            'currentPage': currentPage,
            'total': res.data.body.total

          };
          //设置页面显示 第几条至第几条
          this.totalListsCount = res.data.body.total;
          //首次获取分页信息 设置分页插件
          this.setPageFunc(objData, this);
          this.currentItemLists = res.data.body.inquiryApplyList;
          this.setDisplayItemsCount();
          this.totalItemLists[this.selectPageNo] = this.currentItemLists;
        }
      }
    });

  }

  //搜索按钮点击事件
  onSearchBtnClick() {
    //显示当前选中是第一页
    this.selectPageNo='1';

    this.QueryEntryList();
  }

  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    //var currentObj:any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.currentItemLists.length);
    if(this.endListIndex == '0'){
      this.startListIndex = '0';
    }

  }

  //查询列表end
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
    // var pageKey: string = page;
    // if (this.totalItemLists.hasOwnProperty(pageKey)) {
    //   var itemObj = this.totalItemLists[pageKey];
    //   this.currentItemLists = itemObj;
    //
    //   //显示第几条数据
    //   this.setDisplayItemsCount();
    //   return;
    // }
      //将所有的评估价格除以1w

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


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

//操作第一按钮 相应事件
  //跳转至查看详情页面
  onFirstOperationClick(itemObj) {
    this.commonFunc.setEnquiryStatus("03");
    this.commonFunc.setResubmitStatus("enquirySearch");
    this.commonFunc.setBusinessData(itemObj);
    this.commonFunc.setBusinsessSaveFlagFunc();
    this.router.navigate(['/home/enquiryDetail']);

  }

//跳转至 评估页面
  onSecondOperationClick(itemObj) {
    //设置这是从查询列表进去的标志
    this.commonFunc.setResubmitStatus("enquirySearch");
    this.requstEnquiryStats(itemObj);
  }

//重新估价页面跳转前 判断当前询价是否被锁定
  requstEnquiryStats(itemObj: any) {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var serialNo = this.commonFunc.handleNilString(itemObj['serialNo']);

    var Obj = {
      'head': {
        'functionNo': 'HH000038',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'serialNo': serialNo

      }
    };

    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          this.commonFunc.setBusinessData(itemObj);
          this.commonFunc.setBusinsessSaveFlagFunc();
          this.router.navigate(['/home/enquiryResubmit']);

        }else{
          var notiMessage = res.data.head.returnMessage;
          this.networkService.popNextSuccessModal("提示",notiMessage,false);
          this.QueryEntryList();
        }


      }

    });

  }


}
