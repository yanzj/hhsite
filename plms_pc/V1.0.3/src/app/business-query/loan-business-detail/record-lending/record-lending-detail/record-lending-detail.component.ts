import {Component, EventEmitter, Input, OnInit, Output, SimpleChange} from '@angular/core';
import {environment} from "../../../../../environments/environment";
@Component({
  selector: 'app-record-lending-detail',
  templateUrl: './record-lending-detail.component.html',
  styleUrls: ['./../../apply-detail/apply-detail.component.css']
})
export class RecordLendingDetailComponent implements OnInit {
  @Input() recordDetailPageShow:boolean;
  @Input() receiptsRecordList:any={};
  //@Input() meterialNumber:any={};
  //@Input() selectFileList:any={};
  @Output() event = new EventEmitter();
  constructor() { }

  ngOnInit() {
    this.meterialNumberFunc(this.receiptsRecordList)
  }

  //paidInfoDetail传给子组件的数据
  paidInfoDetail:any={};
  //要显示的文件的列表
  meterialNumber:any=[];

  selectFileList = [];
  meterialNumberFunc(fileList){

    if(fileList.fileList){
      this.selectFileList=[];

      this.meterialNumber=fileList.fileList;
      if(this.meterialNumber){
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


  }

  // 判断文件的类型
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
  setDetailPageShow(){
    this.recordDetailPageShow=false;
    this.event.emit(this.recordDetailPageShow);
  }


  ngOnDestroy(){
    this.recordDetailPageShow=false;
    this.event.emit(this.recordDetailPageShow);
  }
}
