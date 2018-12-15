import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-catalogue-info',
  templateUrl: './catalogue-info.component.html',
  styleUrls: ['./../query-details.component.css']
})
export class CatalogueInfoComponent implements OnInit {

  @Input() catalogueInfoData:any =[];


  selectItemPopViewObj:any={};

  mortgageListInvestObj:any={};

  residenceInfList:any={};

  loanInfoList:any={};

  guaranteeList:any={};

  loanCardInfoList:any={};


  //弹窗显示的情况
  popupShow:string='';//person人员弹窗 //pawn抵押物弹窗  //credit征信弹窗 //judicial司法信息弹窗
  constructor( private router:Router,) { }

  ngOnInit() {
  }
  getData(event){
    this.popupShow=event;
  }

  //人员信息弹窗
  dataInfoPersonPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.applyInputPersionInfoLists[i];
    this.popupShow='person';
    return false;
  }
  //借款人弹窗
  dataInfoBorrowerPopup(i){
    this.selectItemPopViewObj = this.catalogueInfoData.borrowerList[i];
    this.popupShow='borrower';
    return false;
  }


  //抵押物弹窗
  ataInfoPawnFuncPopup(i){
    // if(this.catalogueInfoData.existsPerson){

        this.selectItemPopViewObj = this.catalogueInfoData.mortgageList[i];//选中抵押物信息
        this.mortgageListInvestObj = this.selectItemPopViewObj.investInfList;//抵押物产调信息
        this.residenceInfList = this.selectItemPopViewObj.residenceInfList;//抵押物户口信息
        this.popupShow='catalogue';
        return false;
    //
    // }else if(this.catalogueInfoData.existsLoaner){
    //   //抵押物弹窗
    //
    //     this.selectItemPopViewObj = this.catalogueInfoData.mortgageList[i];//选中抵押物信息
    //     this.mortgageListInvestObj = this.selectItemPopViewObj.investInfList[0];//抵押物产调信息
    //     this.residenceInfList = this.selectItemPopViewObj.residenceInfList;//抵押物户口信息
    //     this.popupShow='pawn';
    //     return false;
    //
    // }


  }


  //征信弹窗
  dataInfoCreditPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.creditInfList[i];
    this.loanInfoList = this.selectItemPopViewObj.loanInfoList;
    this.loanCardInfoList = this.selectItemPopViewObj.loanCardInfoList;
    this.guaranteeList=this.selectItemPopViewObj.guaranteeList
    this.popupShow='credit';
    return false;
  }
  //司法信息弹窗信息
  dataInfoJudicialPopup(i) {
    this.selectItemPopViewObj = this.catalogueInfoData.judicialInfList[i];
    this.popupShow='judicial';
    return false;
  }

  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
}
