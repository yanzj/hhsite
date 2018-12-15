import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Data, Router} from "@angular/router";

@Component({
  selector: 'app-popup-pawn',
  templateUrl: './popup-pawn.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css']
})
export class PopupPawnComponent implements OnInit {
  @Input() selectItemPopViewObj:any={};
  @Input() popupShow : string;
  @Output() event = new EventEmitter();
  constructor(private router:Router,) { }
  secondMortgagePrice:boolean=false; //二抵金额
  firstMortgagePrice:boolean=false;   //一抵金额
  firstMortgageBalance:boolean=false;//一抵余额



  ngOnInit() {
    this.mortgageTypeCodeFunc();
  }

  mortgageTypeCodeFunc(){
    if(this.selectItemPopViewObj.mortgageTypeCode=='01'){
      this.selectItemPopViewObj.mortgageTypeName="一抵"
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=false;

    }else if(this.selectItemPopViewObj.mortgageTypeCode=='02'){
      this.selectItemPopViewObj.mortgageTypeName="二抵";
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=true;
    }else if(this.selectItemPopViewObj.mortgageTypeCode=='03'){
      this.selectItemPopViewObj.mortgageTypeName="一抵转单"
      this.secondMortgagePrice=false;
      this.firstMortgagePrice=true;
      this.firstMortgageBalance=false;
    }else if(this.selectItemPopViewObj.mortgageTypeCode=='04'){
      this.selectItemPopViewObj.mortgageTypeName="二抵转单"
      this.secondMortgagePrice=true;
      this.firstMortgagePrice=false;
      this.firstMortgageBalance=true;
    }
  }


  popupCloseBtn(){
    this.popupShow='';
    this.event.emit(this.popupShow);
  }

}
