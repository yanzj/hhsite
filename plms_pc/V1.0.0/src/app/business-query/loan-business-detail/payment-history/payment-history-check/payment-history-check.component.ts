import {Component, EventEmitter, Input, OnInit, Output, SimpleChange} from '@angular/core';
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-payment-history-check',
  templateUrl: './payment-history-check.component.html',
  styleUrls: ['./payment-history-check.component.css']
})
export class PaymentHistoryCheckComponent implements OnInit {

  @Input() paymentDetailPageShow:boolean;
  @Input() receiptsRecordList:any={};
  //@Input() meterialNumber:any={};
  // @Input() selectFileList:any={};
  @Output() event = new EventEmitter();
  constructor() { }

  ngOnInit() {
   this.meterialNumberFunc(this.receiptsRecordList);
  }

  //paidInfoDetail传给子组件的数据
  paidInfoDetail:any={};
  //要显示的文件的列表
  meterialNumber:any=[];

  selectFileList = [];

  meterialNumberFunc(fileList){
    if(fileList){
      this.selectFileList=[];

      this.meterialNumber=fileList.fileList;

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
      window.open(environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode;
    }

  }
  setDetailPageShow(){
    this.paymentDetailPageShow=false;
    this.event.emit(this.paymentDetailPageShow);
  }

  ngOnDestroy(){
    this.paymentDetailPageShow=false;
    this.event.emit(this.paymentDetailPageShow);
  }
}
