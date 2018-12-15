import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {Router} from "@angular/router";
import {isNullOrUndefined} from "util";
import {environment} from "../../../../environments/environment";
declare var $: any;
@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit {

  @Input() mortgageData={};
  constructor(private router:Router,) { }
  meterialNumber:any=[];
  selectFileList:any = [];
  ngOnInit() {

    //默认显示‘材料清单’ 的显示第一列表的图片，这个是写死的，如果‘材料清单’的列表在试图上变了，这里也要变，和方法defaultSelectFisrtLine()是一起的
    var  signFileList=this.mortgageData['signingNotarization'].signFileList;
    this.meterialNumberFunc(signFileList)
  }
  ngAfterViewInit(){
   this.defaultSelectFisrtLine()

  }

//默认选中‘材料分类’ 的样式

 defaultSelectFisrtLine(){
    $('.material-panel').eq(0).css({'backgroundColor':'#f5f5f5','fontWeight':'blod'});
    $('.material-panel').find("input:radio").eq(0).attr("checked",true);


}



  meterialNumberFunc(fileList){

      this.selectFileList=[];
      this.meterialNumber=fileList;
      $(event.target).find("input:radio").attr("checked","checked")
        .parents('.material-panel').css({'backgroundColor':'#f5f5f5','fontWeight':'blod'})
        .siblings('.material-panel').css({'backgroundColor':'#fff','fontWeight':'400'});

      for(let j=0;j<this.meterialNumber.length;j++){

        var defaultImgUrl=this.judgeFileTypeFunc(this.meterialNumber[j].fileName);
        if ('needUrl' == defaultImgUrl) {
          this.selectFileList.push(environment.server + "/fileLoad/getFile?serialNo=" + this.meterialNumber[j].fileId)
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          this.selectFileList.push(defaultImgUrl);
        }

      }



  }

  // 判断文件的类型
  judgeFileTypeFunc(fileName){
    var fileNameArr = fileName.split('.');
    var number = fileNameArr.length - 1;
    var fileType = fileNameArr[number];
    if (fileType == "jpg" || fileType == "jpeg" || fileType == "png" || fileType == "jif") {
      //图片类
      return 'needUrl';
    } else if (fileType == "doc" || fileType == "docx") {
      //word
      return '/assets/img/word.png';
    } else if (fileType == "xlsx" || fileType == "xls") {
      //exce;
      return 'assets/img/excl.png';
    } else if (fileType == "pdf") {
      //pdf
      return 'assets/img/pdf.png';
    } else if (fileType == "htm" || fileType == "html") {
      //html
      return 'assets/img/html.png';
    } else {
      //文本
      return 'assets/img/txt.png';
    }
  }

  //下载文件
  downloadFile(file){
    var defaultImgUrl=this.judgeFileTypeFunc(file.fileName);
    if ('needUrl' == defaultImgUrl) {
      window.open(environment.server + "/fileLoad/getFile?serialNo=" + file.fileId);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile?serialNo=" + file.fileId;
    }

  }
  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }


}
