import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NetworkService} from "./../../../network.service";
import {User} from "./../../../User.service";
import {commonService} from "./../../../commonService.service";
import {environment} from "../../../../environments/environment";
@Component({
  selector: 'app-check-repayment',
  templateUrl: './check-repayment.component.html',
  styleUrls: ['./check-repayment.component.css']
})
export class CheckRepaymentComponent implements OnInit {

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,
  ) { }

  selectFileList:any = [];

  currentItemLists:any={};
  professionalData:any=[];
  meterialNumber:any;
  contractCode:string;
    ngOnInit() {
      this.contractCode=this.activetedRoute.snapshot.params['id'];
     this.QueryEntryList();
      if(this.currentItemLists.fileList){
        this.meterialNumberFunc(this.currentItemLists.fileList)
      }
    }


  //贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var Obj = {
      'head': {
        'functionNo': 'plms100056',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'receiptsCode':this.contractCode,

      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {
          this.currentItemLists = res.data.body;
          this.meterialNumberFunc(this.currentItemLists.fileList);

          this.professionalData=[
            {content:'借款合同编号：',styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ]

        }
      }

    });
    return false;
  };


  meterialNumberFunc(fileList){
    if(fileList){
      this.selectFileList=[];

      this.meterialNumber=fileList;

      for(var j=0;j<this.meterialNumber.length;j++){

        var defaultImgUrl=this.judgeFileTypeFunc(this.meterialNumber[j].fileSuffix);

        if ('needUrl' == defaultImgUrl) {

          this.selectFileList.push(environment.server + "/fileLoad/getFile.json?serialNo=" + this.meterialNumber[j].fileCode)
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          // file['fileUrl'] = defaultImgUrl;
          this.selectFileList.push(defaultImgUrl);
        }

      }

    }

  }

//判断文件的类型
  judgeFileTypeFunc(fileSuffix){
    if(fileSuffix){
      fileSuffix=fileSuffix.toLowerCase();
    }else {
      return 'assets/img/txt.png';
    }
    if (fileSuffix == "jpg" || fileSuffix == "jpeg" || fileSuffix == "png" || fileSuffix == "jif") {
      //图片类
      return 'needUrl';
    } else if (fileSuffix == "doc" || fileSuffix == "docx") {
      //word
      return '/assets/img/word.png';
    } else if (fileSuffix == "xlsx" || fileSuffix == "xls") {
      //exce;
      return 'assets/img/excl.png';
    } else if (fileSuffix == "pdf") {
      //pdf
      return 'assets/img/pdf.png';
    } else if (fileSuffix == "htm" || fileSuffix == "html") {
      //html
      return 'assets/img/html.png';
    } else {
      //文本
      return 'assets/img/txt.png';
    }
  }

  //下载文件
  downloadFile(file){
    var defaultImgUrl=this.judgeFileTypeFunc(file.fileSuffix);
    if ('needUrl' == defaultImgUrl) {
      window.open(environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode;
    }

  }

  //主按钮链接
  querySection(){
    this.router.navigate(["/home/repaymentRegisterQuery"])
  }

}
