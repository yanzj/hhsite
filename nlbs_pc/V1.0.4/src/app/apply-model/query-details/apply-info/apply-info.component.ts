import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Router} from "@angular/router";
import {commonService} from "../../../commonService.service";
import {environment} from "../../../../environments/environment";
declare var $: any;
@Component({
  selector: 'app-apply-info',
  templateUrl: './apply-info.component.html',
  styleUrls: ['./../query-details.component.css']
})
export class ApplyInfoComponent implements OnInit {
  @Input() applyInfoData:any={};
  certificateList:any;
  constructor(
    private router:Router,
    private commonFunc: commonService,

  ) { }

  ngOnInit() {
    console.log('dddddddddd',this.applyInfoData)
  }




  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
  queryload(){
    var applyFileId=this.commonFunc.onJudgeStringIsNull(this.applyInfoData['applyFileId'])
    console.log('ppppppp',applyFileId)
    if(applyFileId){
      window.location.href = environment.server + "/fileLoad/downloadFile?serialNo=" + this.applyInfoData['applyFileId'];
     }
      // else{
    //   $('#applyFileId').hide();
    // }

  }
}

