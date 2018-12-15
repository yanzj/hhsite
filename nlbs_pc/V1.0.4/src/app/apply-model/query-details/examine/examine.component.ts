import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Router} from "@angular/router";
@Component({
  selector: 'app-examine',
  templateUrl: './examine.component.html',
  styleUrls: ['./../query-details.component.css','./examine.component.css']
})
export class ExamineComponent implements OnInit {
  @Input() approvalNotice:any={};
  mortgageNum:number;
  constructor(private router:Router,) {
  }
  ngOnInit() {
  }
  //监听值发生改变
  // ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
  //   let log: string[] = [];
  //   for (let propName in changes) {
  //     let changedProp = changes[propName];
  //     if (changedProp.isFirstChange()) {
  //     } else {
  //       this.mortgageNum=this.approvalNotice.mortgageList.length;
  //     }
  //   }
  // }


  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }

}
