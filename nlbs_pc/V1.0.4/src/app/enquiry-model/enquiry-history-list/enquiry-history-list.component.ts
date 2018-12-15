import {Component, OnInit} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-enquiry-history-list',
  templateUrl: './enquiry-history-list.component.html',
  styleUrls: ['./enquiry-history-list.component.css']
})
export class EnquiryHistoryListComponent implements OnInit {

  operationList: any = [];
  originFlag: string = '';// resubmitEnquiry-录入估价  enquiryDetail-估价详情
  //从何种路径进来的
  resubmit: string = '';
  resubmitFlag: string = '';

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router) {
  }

  ngOnInit() {
    //历史的第一个参数是消息通知   还是代办任务
    this.resubmitFlag = this.commonFunc.getResubmitStatus();
    if (this.resubmitFlag == 'enquirySearch') {
      this.resubmit = '询价查询';
    } else if (this.resubmitFlag == 'gtasks') {
      this.resubmit = "待办任务";
    } else if(this.resubmitFlag=='message'){
      this.resubmit = "消息通知"
    }

    var historyFlag = this.commonFunc.getHistroyStatus();
    if (historyFlag == 'resubmitEnquiry') {
      this.originFlag = '房产评估';
    } else if (historyFlag == 'enquiryDetail') {
      this.originFlag = '查看询价';
    } else {
    }

    this.historyoperationList();

  }

  //初始化查询查询条件方法start
  historyoperationList() {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var businessObj = this.commonFunc.getBusinessData();
    var serialNo = this.commonFunc.handleNilString(businessObj.serialNo);

    var Obj = {
      'head': {
        'functionNo': 'HH000036',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'serialNo': serialNo,

      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          this.operationList = res.data.body.operationHistoryList;
        }
      }

    });
    return false;
  }

  //页面标题跳转
  //返回询价详情查询页面
  querySection() {
    if (this.resubmitFlag == 'enquirySearch') {
      this.router.navigate(['/home/enquirySearch']);
    } else if (this.resubmitFlag == 'gtasks') {
      this.router.navigate(['/home/gtasks']);
    } else if(this.resubmitFlag=='message'){
      this.router.navigate(['/home/message'])
    }

  }

  itemDetails() {
    //返回询价列表查询页面
    if (this.originFlag == "房产评估") {
      this.router.navigate(['/home/enquiryResubmit'])
    } else {
      this.router.navigate(['/home/enquiryDetail'])
    }

  }

  ngOnDestroy() {
    // this.commonFunc.removeHistroyStatus();
    // this.commonFunc.removeResubmitStatus();
  }


}
