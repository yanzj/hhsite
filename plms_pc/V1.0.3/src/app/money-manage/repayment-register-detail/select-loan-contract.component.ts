import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";




declare var $: any;
@Component({
  selector: 'app-select-loan-contract',
  templateUrl: './select-loan-contract.component.html',
  styleUrls: ['./select-loan-contract.component.css']
})
export class SelectLoanContractComponent implements OnInit {

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,
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
    this.InitQueryEntryList();

    $("#cityList").select2({});
    $("#channelList").select2({});


  }

  //初始化
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100038',
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
          //绑定成熟数据
          this.add_option(cityList, '#cityList', ['cityCode', 'cityName']);
          this.add_option(distributorList, '#channelList', ['distributorCode', 'distributorName']);

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
    var cityCode = this.commonFunc.handleNilString($("#cityList").val()) || this.cityValue;//选择城市
    var distributorCode = this.commonFunc.handleNilString($("#channelList").val());//选择渠道
    var customerName = this.commonFunc.handleNilString($("#loanCustomer").val());//借款人
    var contractNo = this.commonFunc.handleNilString($("#loanContractNumber").val());//借款合同编号


    return {
      "cityCode": cityCode,
      "distributorCode": distributorCode,
      "customerName": customerName,
      "contractNo":contractNo
    };

  }

  //贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var searchConditions = this.getSearchCondigtion();
    var Obj = {
      'head': {
        'functionNo': 'plms100039',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'cityCode': searchConditions.cityCode,
        'distributorCode':searchConditions.distributorCode,
        'customerName': searchConditions.customerName,
        "contractNo":searchConditions.contractNo,
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

  //点击查看页面跳转
  onSecondOperationClick(item){
    this.router.navigate(['/home/repaymentRegisterQuery/repayment-register-detail/selectLoan', item.contractCode]);

  }

  //主按钮链接
  querySection(){
    this.router.navigate(["/home/repaymentRegisterQuery"])
  }

}
