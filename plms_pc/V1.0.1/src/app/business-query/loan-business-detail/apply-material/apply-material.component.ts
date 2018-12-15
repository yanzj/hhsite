import {Component, Input, OnInit, SimpleChange} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
declare var $: any;

@Component({
  selector: 'app-apply-material',
  templateUrl: './apply-material.component.html',
  styleUrls: ['./../apply-detail/apply-detail.component.css']
})
export class ApplyMaterialComponent implements OnInit {

  @Input() contractCode:any;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }
  button: any = {
    'name': '',
    'url': ''
  };
  ngOnInit() {
    //
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
      this.QueryEntryList();
  }

  //主按钮链接
  querySection() {
    this.router.navigate(["/home/"+this.button.url])
  }
  currentItemLists:any={};
  selectedMaterial:number=0;//补充材料当前选中的对象
  selectedMaterialDisplayImages : any = []; //补充材料右边显示图片的列表
  selectItemNameDisplay: string = '';
  selectItemNameDisplayCode;//接口传值需要的code值
  //点击选择补充材料列表的要显示的已有的图片
  onSelectMaterial(materialObj,i): void {
    if (!(materialObj instanceof Array)) {
      this.selectedMaterial = i;
      this.selectedMaterialDisplayImages = materialObj['fileList'];


    } else {
      this.selectedMaterial = i;
      this.selectedMaterialDisplayImages = materialObj[0]['fileList'];

    }

    this.meterialNumberFunc(materialObj.fileList);
    // this.selectedMaterialDisplayImages = materialObj.fileList;
    this.selectItemNameDisplay = materialObj.materialTypeName;
    this.selectItemNameDisplayCode = materialObj.materialTypeCode;

    //当存在材料补充项目时  默认选中当前项目
    //   if (materialObj.length >= 1) {
    //     this.onSelectMaterial(materialObj[0]);
    //
    //   }
    //当单选框外部区域点击时，直接出发单选框的点击方法，调用了上面的方法逻辑
    // $(event.target).find("input").click()


  }


  meterialNumberFunc(fielList){
    if(fielList){
      this.selectedMaterialDisplayImages = [];
      let file : any = [];
      $(event.target).find("input:radio").attr("checked","checked");
      for(var j=0;j<fielList.length;j++){
        file=[];
        var defaultImgUrl=this.setFileNameCatchImage(fielList[j].fileSuffix);  //y原来用是的 judgeFileTypeFunc
        if ('needUrl' == defaultImgUrl) {
          file['fileUrl'] = environment.server + "/fileLoad/getFile.json?serialNo=" + fielList[j].fileCode;
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          file['fileUrl'] = defaultImgUrl;
        }
        file['fileName'] = fielList[j].fileName;
        file['uploadTime'] = fielList[j].uploadTime;
        file['fileCode'] = fielList[j].fileCode;
        file['fileSuffix'] = fielList[j].fileSuffix;
        this.selectedMaterialDisplayImages.push(file);
      }
    }

  }

  //根据文件名 确认文件类型 对应显示的图片
  setFileNameCatchImage(fileSuffix) {

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

  ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
    for (let propName in changes) {
      let changedProp = changes[propName];
      if (changedProp.isFirstChange()) {
        if(this.currentItemLists.materialInfoList!=undefined){
          this.onSelectMaterial(this.currentItemLists.materialInfoList[0],0);
        }
      } else {
        this.onSelectMaterial(this.currentItemLists.materialInfoList[0],0);
      }
    }

  }

  downloadFile(file){

    var defaultImgUrl=this.setFileNameCatchImage(file.fileSuffix); //y原来用是的 judgeFileTypeFunc
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



//贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100030',
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

          this.currentItemLists = res.data.body.materialInfo;
          this.onSelectMaterial(this.currentItemLists.materialInfoList[0],0);
        }
      }

    });
    return false;
  }


}

