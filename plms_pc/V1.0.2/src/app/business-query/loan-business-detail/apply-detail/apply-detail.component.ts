import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-apply-detail',
  templateUrl: './apply-detail.component.html',
  styleUrls: ['./apply-detail.component.css']
})
export class ApplyDetailComponent implements OnInit {

  selectItemPopViewObj:any={};

  @Input() contractCode:any;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }


  currentItemLists:any = {};//当前显示分页内容

//弹窗显示的情况
  popupShow:string='';//borrower人员弹窗 //pawn抵押物弹窗  //credit征信弹窗 //judicial司法信息弹窗


  ngOnInit() {
    this.QueryEntryList();
  }
  getData(event){
    this.popupShow=event;
  }

  //借款人弹窗
  dataInfoBorrowerPopup(i){
    this.selectItemPopViewObj = this.currentItemLists.customerList[i];
    this.popupShow='borrower';
    return false;
  }
  //抵押物弹窗

  ataInfoPawnFuncPopup(i){
    // if(this.catalogueInfoData.existsPerson){

    this.selectItemPopViewObj = this.currentItemLists.mortgageList[i];//选中抵押物信息
    this.popupShow='pawn';
    return false;


  }

//
  //征信弹窗
  dataInfoCreditPopup(i) {
    this.selectItemPopViewObj = this.currentItemLists.creditInvestigationList[i];
    this.popupShow='credit';
    return false;
  }
  //司法信息弹窗信息
  dataInfoJudicialPopup(i) {
    this.selectItemPopViewObj = this.currentItemLists.judicialInfoList[i];
    this.popupShow='judicial';
    return false;
  }


//贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100031',
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

          this.currentItemLists = res.data.body.applyInfo;
        }
      }

    });
    return false;
  }

}
