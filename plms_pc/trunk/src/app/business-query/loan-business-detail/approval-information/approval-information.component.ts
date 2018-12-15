import {Component, Input, OnInit} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-approval-information',
  templateUrl: './approval-information.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css','./approval-information.component.css']
})
export class ApprovalInformationComponent implements OnInit {



  @Input() contractCode:any={};
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }

  currentItemLists: any[] = [];//当前显示分页内容

  ngOnInit() {
    this.QueryEntryList()
  }


  //查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100032',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'contractCode':this.contractCode
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          this.currentItemLists = res.data.body.approvalInfo;


        }
      }

    });
    return false;
  }






  //主按钮链接
  querySection(){
    this.router.navigate(["/home/loanBusiness"])
  }


}
