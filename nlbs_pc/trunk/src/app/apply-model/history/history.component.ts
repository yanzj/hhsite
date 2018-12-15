import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

    //根据状态不同显示不同的面包屑
    isdisplayqueryLink;

  operationList:any = []/*{"time":"2012-12-15","address":"世纪大道福山路21号202室","name":"XXX","operation":'录单中'},
    {"time":"2012-10-06","address":"世纪大道福山路21号202室","name":"圆圆","operation":'审批中'},
    {"time":"2012-10-06","address":"世纪大道福山路21号202室","name":"圆圆","operation":'审批中'}*/
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router:Router,
  ) { }

  ngOnInit() {
    this.historyoperationList();
    this.isdisplayqueryLinkfun();
  }

  toChangeLoanSerialNo:string;
  //初始化查询查询条件方法start
  historyoperationList(){

    var loginTokenKey=this.commonFunc.getloginToken();
    var userNo=this.user.getUserData().userNo;
    var businessObj = this.commonFunc.getBusinessData();
    var toChangeLoanSerialNo=window.sessionStorage.getItem('toChangeLoanSerialNo');
    var Obj = {
      'head': {
        'functionNo': 'HH000021',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'loanSerialNo': toChangeLoanSerialNo,

      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          this.operationList=res.data.body.operationHistoryList;
          /*var cityList = res.data.body.cityList;
          var loanStatusList  = res.data.body.loanStatusList;*/
          //绑定成熟数据
       /*   this.add_option(cityList,'#cityList',['cityCode','cityName']);
          this.add_option(loanStatusList,'#applyStatusList',['code','codeName'])*/;


        }

      }

    });
    return false;
  }

  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
  querysubLink(){
    this.router.navigate(['/home/apply'])
  }
  itemDetails(){
    this.router.navigate(['/home/itemDetails'])

  }

//根据状态显示面包屑的不同显示问题
  isdisplayqueryLinkfun(){
      if(this.commonFunc.getBusinessData().loanStatusName=="暂存"){
        this.commonFunc.setBusinsessSaveFlagFunc();
        this.isdisplayqueryLink=true;
        return this.isdisplayqueryLink;
      }else{
        //标记为暂存后再申请的状态
        this.isdisplayqueryLink=false;
        return this.isdisplayqueryLink;
      }

  }

}
