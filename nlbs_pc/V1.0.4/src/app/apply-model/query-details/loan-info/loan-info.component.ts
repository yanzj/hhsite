import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
@Component({
  selector: 'app-loan-info',
  templateUrl: './loan-info.component.html',
  styleUrls: ['./loan-info.component.css']
})
export class LoanInfoComponent implements OnInit {
  @Input() loanInfo:any=[];

  //loanAccountInfo:any = [];

  constructor( private router:Router,
  ) {
  }

  ngOnInit() {
  }


  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }

}
