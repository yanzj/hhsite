import {Component, Input, OnInit} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
declare var $: any;
@Component({
  selector: 'app-justice-mortgage',
  templateUrl: './justice-mortgage.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css']
})
export class JusticeMortgageComponent implements OnInit {

   @Input() notarialAndMortgage:any={};
  @Input() contractCode:any={};
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }

  //notarialAndMortgage:any={};
  button: any = {
    'name': '',
    'url': ''
  };
  ngOnInit() {
    var backPath=window.sessionStorage.getItem('backPath');
    if(backPath=='loanBusiness'){//这里要  返回贷款查询
      this.button.name='返回贷款查询';
      this.button.url='loanBusiness';
      this.button.title='贷款业务查询';
    }else if(backPath=='repaymentSuccess'){
      this.button.name='返回还款查询';
      this.button.url='repaymentSuccess';
      this.button.title='还款到账查询';
    }else if(backPath=='deducteRecord'){
      this.button.name='返回扣款查询';
      this.button.url='deducteRecord';
      this.button.title='扣款记录查询';
    }else if(backPath=='lendRecordQuery'){
      this.button.name='返回放款查询';
      this.button.url='lendRecordQuery';
      this.button.title='放款记录查询';
    }else if (backPath=='accountInfo') {
      this.button.name = '返回账户查询';
      this.button.url = 'accountInfo';
      this.button.title = '账户信息查询';
    }
    console.log('notarialAndMortgage;;;;;;;;;;;;',this.notarialAndMortgage)
    this.QueryEntryList();

  }


  ngAfterViewInit(){
    this.defaultSelectFisrtLine()
    //默认显示‘材料清单’ 的显示第一列表的图片，这个是写死的，如果‘材料清单’的列表在试图上变了，这里也要变，和方法defaultSelectFisrtLine()是一起的
    var  signFileList=this.notarialAndMortgage.signFileList;
    this.meterialNumberFunc(signFileList);
  }

//默认选中‘材料分类’ 的样式

  defaultSelectFisrtLine(){
    $('.material-panel').eq(0).css({'backgroundColor':'#f5f5f5','fontWeight':'blod'});
    $('.material-panel').find("input:radio").eq(0).attr("checked",true);


  }

  querySection() {
    this.router.navigate(["/home/"+this.button.url])
  }
  meterialNumber:any=[];
  selectFileList = [];
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

  meterialNumberFunc(fileList){
    if(fileList){
      this.selectFileList=[];
      this.meterialNumber=fileList;
      $(event.target).find("input:radio").attr("checked","checked")
        .parents('.material-panel').css({'backgroundColor':'#f5f5f5','fontWeight':'blod'})
        .siblings('.material-panel').css({'backgroundColor':'#fff','fontWeight':'400'});
      for(var j=0;j<this.meterialNumber.length;j++){
        var defaultImgUrl=this.judgeFileTypeFunc(this.meterialNumber[j].fileSuffix);
        if ('needUrl' == defaultImgUrl) {
          this.selectFileList.push(environment.server + "/fileLoad/getFile.json?serialNo=" + this.meterialNumber[j].fileCode)
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          this.selectFileList.push(defaultImgUrl);
        }

      }
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


  //查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100033',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'contractCode':this.contractCode
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          this.notarialAndMortgage = res.data.body.notarialAndMortgage;
          var  signFileList=this.notarialAndMortgage.signFileList;
          this.meterialNumberFunc(signFileList);
        }
      }

    });
    return false;
  }



}
