import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NetworkService} from "./../../../network.service";
import {User} from "./../../../User.service";
import {commonService} from "./../../../commonService.service";
import {environment} from "../../../../environments/environment";
@Component({
  selector: 'app-lend-record-checkup',
  templateUrl: './lend-record-checkup.component.html',
  styleUrls: ['./lend-record-checkup.component.css']
})
export class LendRecordCheckupComponent implements OnInit {

  contractCode:any;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
    private activetedRoute: ActivatedRoute,

  ) { }
  meterialNumber:any=[];

  selectFileList = [];

  currentItemLists:any={};
  professionalData:any=[];
  ngOnInit() {
    this.contractCode = this.activetedRoute.snapshot.params['id'];
    this.QueryEntryList();


  }

  //贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var Obj = {
      'head': {
        'functionNo': 'plms100053',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'paidInfoCode':this.contractCode,
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {


          this.currentItemLists = res.data.body;
          if(this.currentItemLists.fileList){
            this.meterialNumberFunc(this.currentItemLists.fileList)

          }
          this.professionalData=[
            {content:'借款合同编号：',styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ];



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
          //  file['fileUrl'] = defaultImgUrl;
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
      let str='<!DOCTYPE html><html>' +
        '<title>宏获资产 - 贷后管理系统</title>' +
        '<meta name="viewport" content="width=device-width, initial-scale=1">' +
        '<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">' +
        '<link rel="icon" type="image/x-icon" href="http://www.vilio.com.cn/images/favicon.ico">' +
        '<body >' +
        '<img src="'+environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode+'" />' +
        '</body></html>';
      var a=window.open("");
      a.document.write(str);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode;
    }

  }
  querySection(){
    this.router.navigate(['/home/lendRecordQuery'])
  }
}
