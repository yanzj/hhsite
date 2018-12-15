import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";


declare var $: any;
@Component({
  selector: 'app-repayment-schedule-detail',
  templateUrl: './repayment-schedule-detail.component.html',
  styleUrls: ['./repayment-schedule-detail.component.css']
})
export class RepaymentScheduleDetailComponent implements OnInit {
  repaymentScheduleCode:string;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedrRoute: ActivatedRoute,) { }
  professionalData:any=[];
  repaymentDetailInfoList:any=[];
  currentItemLists: any = {};//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '20';//分页 每页请求个数
  creditStartDate: string = '';//开始时间
  creditEndDate: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  cityValue:string;  //城市
  ngOnInit() {
    // this.repaymentScheduleCode=this.activetedRoute.snapshot.params["id"];
    this.activetedrRoute.params.subscribe((params) => {

      this.repaymentScheduleCode=params['id'];
    });
    console.log(';;;;;;;;;',this.repaymentScheduleCode)
    this.QueryEntryList();//调用初始化接口

  }


  //还款计划确认详情初始化接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var Obj = {
      'head': {
        'functionNo': 'plms300014',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'repaymentScheduleCode':this.repaymentScheduleCode,
        'pageNo':this.selectPageNo,
        'pageSize':this.pageSize,

      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          this.currentItemLists=res.data.body;
          this.repaymentDetailInfoList=res.data.body.repaymentDetailInfoList;
          this.professionalData=[
            {content:"借款合同编号:",styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ]
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
          this.setPageFunc(objData, this);
          this.setDisplayItemsCount();

          this.totalItemLists[this.selectPageNo] = this.repaymentDetailInfoList;




        }
      }

    });
    return false;
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
      this.repaymentDetailInfoList = itemObj;

      //显示第几条数据
      this.setDisplayItemsCount();
      return;
    }

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


  }
  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    var currentObj: any = this.totalItemLists[this.selectPageNo];
    if(this.repaymentDetailInfoList){
      this.endListIndex = <string><any>(currentPage * pagesize + this.repaymentDetailInfoList.length);
    }

    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }

  }

  //按钮页面跳转
  querySection(){
    this.router.navigate(['/home/repaymentQuery']);
  }
}
