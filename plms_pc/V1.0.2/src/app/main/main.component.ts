import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  professionalData:any=[]

  constructor() { }

  ngOnInit() {
    this.professionalData=[
      {content:'欢迎使用上海宏获资产管理有限公司贷后管理系统。如有任何技术问题，请联系技术支持邮箱：',styleName:'message-title'},
      {content:'tech@vilio.com.cn',styleName:'message-number font-weight-bold main-color'}
    ]
  }

}
