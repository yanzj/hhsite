import {Directive, Component, OnInit, OnDestroy} from '@angular/core';

import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";

// 申请查询
declare var $: any;
declare var laydate: any;
@Component({
  selector: 'app-query-section',
  templateUrl: './query-section.component.html',
  styleUrls: ['./query-section.component.css'],
})


export class QuerySectionComponent implements OnInit {

  currentItemLists: any[] = [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  startTimeStr: string = '';//开始时间
  endTimeStr: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  cityValue:string;

  applyStatusValue:string = '';
  //当前结束页数

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute
  ) {
  }


  ngOnInit() {

    //获取页面参数
    this.getApplyStatusParam();

    $("#cityList").select2({});
    $("#applyStatusList").select2({});
    //日历插件调用
    this.timedicpker();


    //初始化查询条件接口信息
    this.InitQueryEntryList()

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

  //获取Link传过来的申请状态参数
  private applyStatus: string = '';
  private sub: any;
  getApplyStatusParam(){
    this.sub = this.activetedRoute.queryParams.subscribe(params => {
      this.applyStatus = params['applyStatus'];
    });
  }


  ngOnDestroy() {
    //退出当前组件时，需要销毁页面上的日历节点。
    $("#laydate_box").remove();
    this.sub.unsubscribe();
  }

  //初始化查询查询条件方法start
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    // var userNo=this.user.getUserData().userNo;
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;
    var cityCode = userObj.cityCode;

    var Obj = {
      'head': {
        'functionNo': 'HH000016',
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
          var loanStatusList = res.data.body.loanStatusList;
          //绑定成熟数据
          this.add_option(cityList, '#cityList', ['cityCode', 'cityName']);
          this.add_option(loanStatusList, '#applyStatusList', ['code', 'name']);
          $("#applyStatusList").select2().val(this.applyStatus).trigger("change");

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

    });
    //分页查询接口
    this.QueryEntryList()
  }


  //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {
    var city = this.commonFunc.handleNilString($("#cityList").val());//选择城市
    var loadStatu = this.commonFunc.handleNilString($("#applyStatusList").val());
    var startTime = this.commonFunc.handleNilString($("#applyTimeBegin").val());
    var endTime = this.commonFunc.handleNilString($("#applyTimeEnd").val());

    var customerName = this.commonFunc.handleNilString($("#loanCustomer").val());//借款人
    return {
      "cityCode": city,
      "customerName": customerName,
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
        'functionNo': 'HH000011',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'startDate': searchConditions.startDate,
        'endDate': searchConditions.endDate,
        'cityCode': searchConditions.cityCode,
        'customerName': searchConditions.customerName,
        'loadStatu': searchConditions.loadStatu,
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
          this.currentItemLists = res.data.body.applyLoanList;

          this.setDisplayItemsCount();


          //修改进件状态 暂时前端映射 待需求确认之后 以后台为准
          //material 充材料为 1-不显示  2-显示

          for (var i = 0; i < this.currentItemLists.length; i++) {
            var obj = this.currentItemLists[i];
            var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
            this.currentItemLists[i]["statusName"] = resultObj['statusName'];
            this.currentItemLists[i]["operate"] = resultObj['operate'];
            this.currentItemLists[i]["material"] = resultObj['material'];

            this.currentItemLists[i]["supplementaryMateria"] = resultObj['supplementaryMateria'];


          }

          this.totalItemLists[this.selectPageNo] = this.currentItemLists;

          //otal

        }
      }

    });
    return false;
  }

  //搜索按钮点击事件
  onSearchBtnClick() {
    this.selectPageNo = '1';
    this.QueryEntryList();

  }

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
  //跳转至进件详情 或者 进件提交页面
  onFirstOperationClick(itemObj) {
    this.commonFunc.setBusinessData(itemObj);
    this.commonFunc.setBusinsessSaveFlagFunc();
    if (itemObj.operate == "申请") {
      this.router.navigate(['/home/apply']);
    } else {
      this.router.navigate(['/home/itemDetails']);

    }

  }

//跳转至 进件提交页面
  onSecondOperationClick(itemObj) {
    this.commonFunc.setBusinessData(itemObj);
    this.commonFunc.setBusinsessSaveFlagFunc();


    window.sessionStorage.setItem('materia', '10')
    this.router.navigate(['/home/itemDetails']);
  }

  //申请后面有删除按钮  点击删除之后  向后台发送请求，然后将状态改为作废
  onSecondOperationClickDelete(itemObj) {
    this.networkService.popNextSuccessModal("删除进件申请", `将删除借款人为<span class="main-color font-weight-bold">${itemObj.customerName}</span>的进件申请`, true, () => {

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
            //将删除的消息数据发送给后台以后  刷新当前页
            this.QueryEntryList();
          }

        }

      });
    });
  }


}
