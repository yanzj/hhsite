import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Data, Router} from "@angular/router";

@Component({
  selector: 'app-popup-credit',
  templateUrl: './popup-credit.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css']
})
export class PopupCreditComponent implements OnInit {

  @Input() selectItemPopViewObj:any={};
  @Input() popupShow : string;
  @Output() event = new EventEmitter();
  constructor(private router:Router,) { }



  ngOnInit() {
  }


  popupCloseBtn(){
    this.popupShow='';
    this.event.emit(this.popupShow);
  }
}
