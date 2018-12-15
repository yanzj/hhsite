import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Data, Router} from "@angular/router";


@Component({
  selector: 'app-popup-person-info',
  templateUrl: './popup-person-info.component.html',
  styleUrls: ['./../query-details.component.css','./popup-person-info.component.css']
})
export class PopupPersonInfoComponent implements OnInit {

  constructor(
    private router:Router,
  ) { }

  @Input() selectItemPopViewObj : any = {};
  @Input() popupShow : string;
  @Output() event = new EventEmitter();

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
