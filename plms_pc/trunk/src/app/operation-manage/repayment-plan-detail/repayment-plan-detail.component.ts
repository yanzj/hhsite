import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";


declare var $: any;
@Component({
  selector: 'app-repayment-plan-detail',
  templateUrl: './repayment-plan-detail.component.html',
  styleUrls: ['./repayment-plan-detail.component.css']
})
export class RepaymentPlanDetailComponent implements OnInit {

  contractCode:string; //借款合同数据唯一标识
  professionalData:any=[];
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,) { }
              //private comSectionComponent: ComSectionComponent
  //repaymentList;

  currentItemLists: any= [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '20';//分页 每页请求个数
  creditStartDate: string = '';//开始时间
  creditEndDate: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  cityValue:string;  //城市

  repaymentList:any=[];
  ngOnInit() {


    this.contractCode=this.activetedRoute.snapshot.params['id'];
    this.QueryEntryList();

  }

  //还款计划"确认"按钮接口
  queryEnsure=()=>{
    this.ensureButton('确认还款计划','确认后，当前还款计划将开放给客户和渠道查询，是否继续？');

  }

  queryEnsureFun(){
      var loginTokenKey = this.commonFunc.getloginToken();
      var userNo = this.user.getUserData().userNo;
      var Obj = {
      'head': {
        'functionNo': 'plms100018',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'contractCode':this.contractCode,

      }
    };

      this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
      // 请求网络通信处理方法

      if (this.networkService.onJudgeSuccessful(res)) {

      this.popupEnsureButton('确认还款计划','还款计划确认成功，','秒钟后返回还款计划确认页面');

    }
    }

    });
    return false;
    }
  //还款计划确认详情初始化接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100017',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'contractCode':this.contractCode,
        'pageNo':this.selectPageNo,
        'pageSize':this.pageSize,

      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {


          this.currentItemLists=res.data.body;
          this.professionalData=[
            {content:'借款合同编号：',styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ]
          this.repaymentList=res.data.body.repaymentList;
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
          this.totalItemLists[this.selectPageNo] = this.repaymentList;


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
      this.repaymentList = itemObj;
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
    this.endListIndex = <string><any>(currentPage * pagesize + this.repaymentList.length);
    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }

  }

  ensureButton(tipTitle, tiptxt){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      that.queryEnsureFun(); //确认接口调用
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }




  }
  //点击弹窗里的“确定” 按钮的弹窗
  popupEnsureButton(tipTitle, tiptxt,text) {
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}<span id='secondValue'>5</span>${text}</div>`;
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
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      that.router.navigate(['/home/repaymentPlanConfirmation']);
      return true;
    }

    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function(){
        var secondValue = $("#secondValue").text();
        if( 1 == secondValue){
          $(this).closeModal();
          clearInterval(reduceSecondValue);
        }else{
          $("#secondValue").text(secondValue-1);
        }
      },
      1000
    );

  }

  querySection(){
    this.router.navigate(['/home/repaymentPlanConfirmation'])
  }


}
