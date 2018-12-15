import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Data, Router} from "@angular/router";
import {QueryDetailsComponent} from "./../query-details.component";
@Component({
  selector: 'app-popup-catalogue',
  templateUrl: './popup-catalogue.component.html',
  styleUrls: ['./../query-details.component.css','./popup-catalogue.component.css']
})
export class PopupCatalogueComponent implements OnInit {

  @Input() selectItemPopViewObj : any = {};
  @Input() mortgageListInvestObj : any = [];
  @Input() catalogueInfoData:boolean;
  @Input() popupShow : string;
  @Output() event = new EventEmitter();
  endDate='2003'
  catalogueInfoDatafn=this.catalogueInfoData;
  constructor(
    private router:Router,
    public queryDetail:QueryDetailsComponent
  ) { }
  mortgageTypeCode:any={};
  mortgageTypeName:any={};
  secondMortgagePrice:boolean=false; //二抵金额
  firstMortgagePrice:boolean=false;   //一抵金额
  firstMortgageBalance:boolean=false;//一抵余额


  //investTime:string=''
  ngOnInit() {
    //从进件详情页面传值，判断是“进件详情抵押物详情”还是“录单信息抵押物详情”。
    this.catalogueInfoDatafn=this.queryDetail.catalogueInfoData
    this.mortgageTypeCode=this.selectItemPopViewObj.mortgageTypeCode
    //判断“录单抵押物详情”的抵押类型的判断
    this.mortgageTypeCodeFunc();
  }


  mortgageTypeCodeFunc(){
    if(this.mortgageTypeCode=='01'){
      this.mortgageTypeName="一抵"
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=false;

    }else if(this.mortgageTypeCode=='02'){
      this.mortgageTypeName="二抵";
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=true;
    }else if(this.mortgageTypeCode=='03'){
      this.mortgageTypeName="一抵转单"
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=true;
      this.firstMortgageBalance=false;
    }else if(this.mortgageTypeCode=='04'){
      this.mortgageTypeName="二抵转单"
      this.secondMortgagePrice=true;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=true;
    }
  }

  popupCloseBtn(){
    this.popupShow='';
    this.event.emit(this.popupShow);
  }
  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }

}
