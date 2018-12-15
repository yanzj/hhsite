import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Data, Router} from "@angular/router";

@Component({
  selector: 'app-popup-borrower',
  templateUrl: './popup-borrower.component.html',
  styleUrls: ['./../query-details.component.css','./popup-borrower.component.css']
})
export class PopupBorrowerComponent implements OnInit {


  @Input() selectItemPopViewObj : any = {};

  @Input() popupShow : string;
  @Output() event = new EventEmitter();
  constructor(private router:Router,) { }


  ngOnInit() {
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
