import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-apply-info',
  templateUrl: './apply-info.component.html',
  styleUrls: ['./../query-details.component.css']
})
export class ApplyInfoComponent implements OnInit {
  @Input() applyInfoData:any={};
  certificateList:any;
  constructor(private router:Router,) { }

  ngOnInit() {
  console.log('000000000000000000001applyInfoData:',this.applyInfoData)
  }



  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
  queryload(){
    window.location.href = environment.server + "/fileLoad/getFile?serialNo=" + this.applyInfoData['applyFileId'];
  }
}

