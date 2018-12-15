import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
declare var $: any;
declare var laydate: any;
@Component({
  selector: 'app-gtasks',
  templateUrl: './gtasks.component.html',
  styleUrls: ['./gtasks.component.css']
})
export class GtasksComponent implements OnInit {
  currentItemLists: any[] = [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  startTimeStr: string = '';//开始时间
  endTimeStr: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前介绍页数
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,) {
  }


  ngOnInit() {
    $("#cityList").select2({});
    $("#applyStatusList").select2({});
    this.timedicpker();
    //初始化查询条件接口信息
    this.InitQueryEntryList();
    //分页查询接口
    this.QueryEntryList();
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

  ngOnDestroy(){
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
        'functionNo': 'HH000042',
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
          //登录成功处理逻辑 获得搜索中的类型  并且绑定
          var loanStatusList = res.data.body.todoTaskList;
          //绑定成熟数据
          this.add_option(loanStatusList, '#applyStatusList', ['todoTypeCode', 'todoTypeNode']);
        }
      }
    });
  }
  //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {
    //类型的值
    var loadStatu = this.commonFunc.handleNilString($("#applyStatusList").val());
    //分配日期的开始和结束
    var startTime = this.commonFunc.handleNilString($("#applyTimeBegin").val());
    var endTime = this.commonFunc.handleNilString($("#applyTimeEnd").val());
    //内容的值
    var content = $("#content").val();
    content = this.commonFunc.handleNilString(content);//借款人
    return {
      "content": content,
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
        'functionNo': 'HH000041',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'startDate': searchConditions.startDate,
        'endDate': searchConditions.endDate,
        // 'cityCode': searchConditions.cityCode,
        'content': searchConditions.content,
        'status': searchConditions.loadStatu,
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo
      }
    };

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
          this.currentItemLists = res.data.body.todoTaskList;
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


  goResubmit(itemObj,todoType){
    //当todoType是001的时候  进入询价的录入评估页面
    if(todoType=='001'){
      this.commonFunc.setResubmitStatus("gtasks");
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
}
