import {Component, EventEmitter, Input, OnInit, Output, SimpleChange} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
declare var $: any;
@Component({
  selector: 'app-interest-agent',
  templateUrl: './interest-agent.component.html',
  styleUrls: ['./interest-agent.component.css']
})
export class InterestAgentComponent implements OnInit {
  @Input() repaymentSchedule: any = {};
  @Input() reCordFlag: boolean;
  @Input() paidCode: string;
  @Input() needLoadAgain:boolean=false;
  @Output() event = new EventEmitter();
  @Output() event2 = new EventEmitter();

  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
    private activetedRoute: ActivatedRoute,

  ) {
  }

  //显示相应的弹窗   popShow
  popShow: string = '';
  contractCode:string='';
  ngOnInit() {
    this.contractCode = this.activetedRoute.snapshot.params['id'];
  }

  //点击添加按钮   触发的事件
  addPopup() {
    this.popShow = 'add';
    //  放款记录已经发生了变更  并且把改变的值发送给父组件
    // this.reCordFlag = true;
    this.event.emit(this.reCordFlag);
  }

  //点击修改按钮   触发的事件
  //将已经有的数据  传给修改的弹窗
  modifyData:any={};
  modifyPopup(item) {
    this.popShow = 'modify';
    //放款记录已经发生了变更  并且把改变的值发送给父组件
    // this.reCordFlag = true;
    this.event.emit(this.reCordFlag);
    this.modifyData=item;
  }

  //点击删除按钮    触发的事件
  deletePopup(repaymentDate,scheduleDetailTmpCode) {
    //方款记录已经发生了变更  并且把改变的值发送给父组件
    // this.reCordFlag = true;
    this.event.emit(this.reCordFlag);
    if(repaymentDate){
      repaymentDate=repaymentDate.split(' ')[0]
    }
    //  删除的话   来个弹窗
    this.delPopup('删除还款计划','将删除当前放款下日期为',repaymentDate,scheduleDetailTmpCode);
  }
  //
  // //点击提交按钮
  // getModify() {
  //   //方款记录已经发生了变更  并且把改变的值发送给父组件
  //   this.reCordFlag = false;
  //   this.event.emit(this.reCordFlag);
  // }

  getData(event) {
    this.popShow = event;
  }
  //
  // getData2(event) {
  //   this.repaymentSchedule = event;
  //   console.log(this.repaymentSchedule);
  // }

  getData3(event){
    this.reCordFlag=event;
    this.event.emit(this.reCordFlag)
  }


  //点击删除以后出现删除弹窗
  delPopup(tipTitle, tiptxt,text,scheduleDetailTmpCode) {
    var that = this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}<span id='secondValue'>${text}</span>的还款计划，确认删除</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myCloseImageClickCallback,
      cancelButtonClickCallback: myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      //点击确定按钮的话  则会发送请求
      that.deletePost(scheduleDetailTmpCode);
      $(this).closeModal();
      return true;
    }

    function myCloseImageClickCallback() {
      $(this).closeModal();
      return true;
    }


  }

  //删除接口  和后台交互
  userNo:string;
  deletePost(scheduleDetailTmpCode) {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    this.userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100025',
        'userNo': this.userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'scheduleDetailTmpCode':scheduleDetailTmpCode,
        'paidCode':this.paidCode,
        'contractCode':this.contractCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        //  如果删除成功的话  返回个标志位给父组件   然后父组件再重新请求一遍获得新的数据
          var returnCode=res.data.head.returnCode;
          var returnMessage=res.data.head.returnMessage;
          if(returnCode=='0000'){
            this.needLoadAgain=true;
            this.event2.emit(this.needLoadAgain);
          }else if(returnCode=='1009') {
            this.changeModedifyPower('还款计划修改失败',returnMessage,'正在修改当前还款计划，您无法修改提交数据');
          }else if(returnCode=='9999') {
            this.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
          }else{
            this.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
              // this.user.setIsLogin(false);
              this.user.clearLoginInfo();
              this.router.navigate(['/login']);
            });
          }
      }
    });
  }
  //新增的一个弹窗（某某某正在修改某某某的还款计划，是否确认改为您来修改）
  changeModedifyPower(tipTitle, modifyName,tiptxt){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'><span id='secondValue'>${modifyName}</span>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: false,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      //返回到还款计划修改页面
      that.router.navigate(['/home/repaymentPlanModifyQuery']);
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      //返回到还款计划修改页面
      that.router.navigate(['/home/repaymentPlanModifyQuery']);
      $(this).closeModal();
      return true;
    }




  }
  popNextFailModal(tipTitle:string, tipContent:string,flag:boolean,fn?) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>秒钟后自动关闭</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: false,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      //当存在一家询价公司数据存在时，提交数据到后台
      if(flag){
        fn();
      }
      return true;
    }

    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          $(this).closeModal();
          clearInterval(reduceSecondValue);
          if(flag){
            fn()
          }
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }

  getData4(event){
    this.needLoadAgain=event;
    this.event2.emit(this.needLoadAgain);
  }
}


