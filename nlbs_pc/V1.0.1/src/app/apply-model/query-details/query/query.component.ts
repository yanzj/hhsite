import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./../query-details.component.css']
})
export class QueryComponent implements OnInit {
  @Input() catalogueInfoData:any =[];
  // @Input() applyInputPersionInfoLists:any ={};
  // @Input() spareHouseList:any ={};
  // @Input() mortgageList:any ={};
  // @Input() creditInfList:any ={};
  // @Input() judicialInfList:any ={};
  // @Input() loanAccountList:any ={};


  constructor() { }

  ngOnInit() {
  }
  selectItemPopViewObj:any={};

  mortgageListInvestObj:any={};

  residenceInfList:any={};

  loanInfoList:any={};

  loanCardInfoList:any={};


  //弹窗显示的情况
  popupShow:string='';//borrower借款人信息 //pawn抵押物弹窗  //credit征信弹窗 //judicial司法信息弹窗

  getData(event){
    this.popupShow=event;
  }

  //借款人信息弹窗
  dataInfoPersonPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.applyInputPersionInfoLists[i];
    this.popupShow='borrower';
    return false;
  }
  //抵押物弹窗
  dataInfoPawnPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.mortgageList[i];//选中抵押物信息
    this.mortgageListInvestObj = this.selectItemPopViewObj.investInfList[0];//抵押物产调信息
    this.residenceInfList = this.selectItemPopViewObj.residenceInfList;//抵押物户口信息
    this.popupShow='pawn';
    return false;
  }
  //征信弹窗
  dataInfoCreditPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.creditInfList[i];
    this.loanInfoList = this.selectItemPopViewObj.loanInfoList;
    this.loanCardInfoList = this.selectItemPopViewObj.loanCardInfoList;
    this.popupShow='credit';
    return false;
  }
  //司法信息弹窗信息
  dataInfoJudicialPopup(i) {
    this.popupShow='judicial';
    this.selectItemPopViewObj = this.catalogueInfoData.judicialInfList[i];
    this.popupShow='judicial';
    return false;
  }
}
