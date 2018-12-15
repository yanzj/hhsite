import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-overdue-record-detail',
  templateUrl: './overdue-record-detail.component.html',
  styleUrls: ['./overdue-record-detail.component.css']
})
export class OverdueRecordDetailComponent implements OnInit {

  repaymentScheduleCode:string;

  professionalData;
  currentItemLists:any={};
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,) { }

  ngOnInit() {
    this.repaymentScheduleCode=this.activetedRoute.snapshot.params['id'];
    this.QueryEntryList();//调用初始化接口
  }

  //还款计划确认详情初始化接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var Obj = {
      'head': {
        'functionNo': 'plms100060',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'repaymentScheduleCode':this.repaymentScheduleCode,

      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          this.currentItemLists=res.data.body;
          this.professionalData=[
            {content:"借款合同编号:",styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ]

        }
      }

    });
    return false;
  }


  querySection(){
    this.router.navigate(['/home/overdueRecord']);
  }
}
