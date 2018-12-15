import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Data, Router} from "@angular/router";


@Component({
  selector: 'app-popup-pawn-detail',
  templateUrl: './popup-pawn-detail.component.html',
  styleUrls: ['./popup-pawn-detail.component.css']
})
export class PopupPawnDetailComponent implements OnInit {


  // @Input() selectItemPopViewObj : any = {};
  // @Input()loanInfoList: any = {};
  // @Input()loanCardInfoList: any = {};
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
