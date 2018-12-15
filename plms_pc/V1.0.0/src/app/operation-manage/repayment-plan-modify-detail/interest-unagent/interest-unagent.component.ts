import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
declare var $: any;
@Component({
  selector: 'app-interest-unagent',
  templateUrl: './interest-unagent.component.html',
  styleUrls: ['./interest-unagent.component.css']
})
export class InterestUnagentComponent implements OnInit {

  @Input() repaymentSchedule: any = {};
  @Input() paidCode: string;
  @Input() reCordFlag: boolean;
  @Output() event = new EventEmitter();
  @Output() event2 = new EventEmitter();
  @Input() needLoadAgain:boolean=false;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
  ) { }

  ngOnInit() {
  }




  //显示相应的弹窗   popShow
  popShow: string = '';
  //点击添加按钮   触发的事件
  addPopup() {
    this.popShow = 'add';
    //  放款记录已经发生了变更  并且把改变的值发送给父组件
    // this.reCordFlag = true;
    // this.event.emit(this.reCordFlag);
  }
  //点击修改按钮   触发的事件
  //将已经有的数据  传给修改的弹窗
  modifyData:any={}
  modifyPopup(item) {
    this.popShow = 'modify';
    //放款记录已经发生了变更  并且把改变的值发送给父组件
    this.reCordFlag = true;
    this.event.emit(this.reCordFlag);
    this.modifyData=item;
  }
  //点击删除按钮    触发的事件
  deletePopup(repaymentDate,scheduleDetailTmpCode) {
    //方款记录已经发生了变更  并且把改变的值发送给父组件
    this.reCordFlag = true;
    this.event.emit(this.reCordFlag);
    //  删除的话   来个弹窗
    this.delPopup('删除还款计划','将删除当前放款下日期为',repaymentDate,scheduleDetailTmpCode);
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

  //点击提交按钮
  getModify() {
    //方款记录已经发生了变更  并且把改变的值发送给父组件
    this.reCordFlag = false;
    this.event.emit(this.reCordFlag);
  }

  getData(event) {
    this.popShow = event;
  }

  getData2(event) {
    this.repaymentSchedule = event;
  }

  getData3(event){
    this.reCordFlag=event;
    this.event.emit(this.reCordFlag)
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
        'paidCode':this.paidCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        if (this.networkService.onJudgeSuccessful(res)) {
          //  如果删除成功的话  返回个标志位给父组件   然后父组件再重新请求一遍获得新的数据
          this.needLoadAgain=true;
          this.event2.emit(this.needLoadAgain);

        }
      }
    });
  }

}
