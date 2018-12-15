import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Data, Router} from "@angular/router";

@Component({
  selector: 'app-popup-judicial',
  templateUrl: './popup-judicial.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css']
})
export class PopupJudicialComponent implements OnInit {

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
