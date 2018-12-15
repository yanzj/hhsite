import {ChangeDetectorRef, Component, Input, OnInit, SimpleChange} from '@angular/core';
import {FileUploader} from "ng2-file-upload";
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import { QuerySectionComponent } from './../../../apply-model/query-section/query-section.component';

declare var $: any;
@Component({
  selector: 'app-apply-material',
  templateUrl: './apply-material.component.html',
  styleUrls: ['./../query-details.component.css','./apply-material.component.css']
})
export class ApplyMaterialComponent implements OnInit {

  @Input() materialTypeListData: any = []; //是‘材料分类’的一排列表15个
  @Input() loanSerialNo: string = '';


  //补充材料
  selectedMaterial:number;//补充材料当前选中的对象
  selectedMaterialDisplayImages : any = []; //补充材料右边显示图片的列表
  submitFileOBJ: any = {'suc': [], 'fail': []};//补充材料中选中提交文件内容  suc-上传成功文件 fail-上传失败文件
  sucUploadFileNum = 0;//已成功上传文件数


  failUploadFileNum = 0;//上传失败文件个数
  selectFileList :any = [];
  issmallPhoto: boolean = false;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮
  selectItemNameDisplay: string = '';
  progressLength: number;
  //定义文件上传的日期
  uploadTime: string;
  userNo = this.user.getUserData().userNo;
  loginTokenKey = this.commonFunc.getloginToken();//登录时间戳

  isSubmitDisabled:boolean=false; //提交按钮的开关
  selectItemNameDisplayCode;//接口传值需要的code值

  isdisplayqueryMateria;//判断是否是从进件列表页面的 “补充材料” 链接过来显示 当前tab项为补充材料
  //tempRes;
  // fileIsUploading;//判断文件此刻是否正在上传

  uploadEXENum: number = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
  sucFileList;//补充材料上传接口的传给后台的参数

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              public ref: ChangeDetectorRef) {
    this.progressLength = 0;
    this.ref = ref;
  }

  ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
    for (let propName in changes) {
      let changedProp = changes[propName];
      if (changedProp.isFirstChange()) {

        if(this.materialTypeListData!=undefined){

          this.onSelectMaterial(this.materialTypeListData[0],0);

        }
      } else {
          this.onSelectMaterial(this.materialTypeListData[0],0);

      }
    }
  }

  ngOnInit() {

  }


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
        file = [];
        var defaultImgUrl=this.setFileNameCatchImage(fielList[j].fileSuffix);  //y原来用是的 judgeFileTypeFunc
        if ('needUrl' == defaultImgUrl) {
          file['fileUrl'] = environment.server + "/fileLoad/getFile?serialNo=" + fielList[j].fileId;
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          file['fileUrl'] = defaultImgUrl;
        }
        file['fileName'] = fielList[j].originalFileName;
        file['uploadTime'] = fielList[j].uploadTime;
        file['fileId'] = fielList[j].fileId;
        file['fileSuffix'] = fielList[j].fileSuffix;
        this.selectedMaterialDisplayImages.push(file);
      }
    }

  }



  //下载文件
  downloadFile(file){
    var defaultImgUrl=this.setFileNameCatchImage(file.fileSuffix); //y原来用是的 judgeFileTypeFunc
    if ('needUrl' == defaultImgUrl) {
      let str='<!DOCTYPE html><html>' +
        '<title>宏获资产 - 贷后管理系统</title>' +
        '<meta name="viewport" content="width=device-width, initial-scale=1">' +
        '<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">' +
        '<link rel="icon" type="image/x-icon" href="http://www.vilio.com.cn/images/favicon.ico">' +
        '<body >' +
        '<img src="'+environment.server + "/fileLoad/getFile?serialNo=" + file.fileId+'" />' +
        '</body></html>';
      var a=window.open("");
      a.document.write(str);
    } else {
      window.location.href = environment.server + "/fileLoad/downloadFile?serialNo=" + file.fileId;
    }

  }


  //定义事件，上传文件
  uploadFile() {

    for (let j = this.uploadEXENum; j < this.uploader.queue.length; j++) {


      this.uploader.queue[j].upload();

      //文件上传成功执行的回调函数
      this.uploader.queue[j].onSuccess = (response, status, headers) => {//fileMaps数组只有一个上传的成功文件，是单个上传成功
        // 上传文件成功
        if (status == 200) {
          // 上传文件后获取服务器返回的数据

          let tempRes = JSON.parse(response);

          if (tempRes.body.returnCode == '0000') {
            //文件上传成功
            this.uploadTime = tempRes.body.fileMaps[0].uploadTime;
            var fileObj = tempRes.body.fileMaps[0]; //fileMaps数组只有一个上传的成功文件，是单个上传成功
            var fileNameObj = fileObj.fileName;
            if(fileNameObj){
              var nameArray  = fileNameObj.split('.');
              var fileSuffix=nameArray[1];
            }
            var uploadFileList = this.submitFileOBJ['suc'];  //是一个对象



            //判断img的类型
            let imageSrc = this.setFileNameCatchImage(fileSuffix);
            //如果是图片类型，直接使用本地文件base64数据
            if ('needUrl' == imageSrc) {
              let selectFileListObj = this.selectFileList;
              let reader = new FileReader();
              reader.readAsDataURL(this.uploader.queue[j].some);
              reader.onload = function (e) {
                $("#uploadImg" + j).attr("src", this.result);
                selectFileListObj.push({'src':this.result,'serialNo':fileObj.serialNo});
              };
            } else {
              //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
              this.selectFileList.push({'src':imageSrc,'serialNo':fileObj.serialNo});
            }



            uploadFileList.push(fileObj);

            this.sucUploadFileNum++;

          } else {
            //文件上传失败
            var fileObj = tempRes.body.fileMaps[0];
            var failFileList = this.submitFileOBJ['fail'];

            failFileList.push(this.uploader.queue[j].file);

            //failFileList.push(fileObj);
            this.failUploadFileNum++;


          }
          this.ref.markForCheck();
          this.ref.detectChanges();
          this.uploader.queue[j]['uploadTime'] = this.uploadTime;
          //在当前选中的材料项目中  放置当前上传成功文件对象列表
        } else {
          // 上传文件后获取服务器返回的数据错误
          this.failUploadFileNum++;
          alert("server return error ");

        }
      };

      //  文件上传失败执行的回调函数
      this.uploader.queue[j].onError = (response, status, headers) => {
        this.failUploadFileNum++;

        if (status == 200) {
          let tempRes = JSON.parse(response);
          var failFileList = this.submitFileOBJ['fail'];
          failFileList.push(this.uploader.queue[j].file);

        } else {

        }


      };

      //上传进度2
      this.uploader.onProgressItem = (fileItem, progress) => {

        // 提交按钮在全部上传失败后需要显示禁止按钮
        $("#disableButton").show()
        $("#onSubmitButton").hide()

        //提交按钮在全部上传失败后需要显示禁止按钮
        $("#uploadedfileNumber").hide();
        $("#uploadingfileNumber").show();

        var self = this;
        self.progressLength = progress;
        self.ref.markForCheck();
        self.ref.detectChanges();
      };

      this.uploadEXENum++;
    }

    //上传完成
    this.uploader.onCompleteItem = function (response, status, headers) {
      $("#disableButton").hide()
      $("#onSubmitButton").show()


      // $("#uploadedfileNumber").hide();
      // $("#uploadingfileNumber").show();

    };

    this.uploader.onCompleteAll = () => {

      if (this.sucUploadFileNum > 0) { //提交按钮在全部上传失败后需要显示禁止按钮
        $("#disableButton").hide()
        $("#onSubmitButton").show()

      } else if (this.sucUploadFileNum == 0 && this.failUploadFileNum > 0) {
        $("#disableButton").show()
        $("#onSubmitButton").hide()

      }

      //显示是否正在是上传状态
      $("#uploadedfileNumber").show();
      $("#uploadingfileNumber").hide();
    }

    //取消上传
    this.uploader.onCancelItem = function (response, status, headers) {

    };


  }

  //重新上传文件
  upAgiain(i) {
    this.failUploadFileNum--;
    if (this.failUploadFileNum <= 0) {
      this.failUploadFileNum = 0
    }
    this.uploader.queue[i].upload();

  }

  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",                                                                                                                                                                                                                                   
  });

  uploadList:any=[];

  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    //遍历已选择的文件，判断合法性（文件大小，（重名？））
    for (let i = 0; i < this.uploader.queue.length; i++) {
      this.uploadList=this.uploader.queue;
      var needReselect = false;
      //获取文件名称
      var fileNameStr: string = this.uploader.queue[i]['file']['name'];

      //判断文件格式是否合法
      var fileFormart = this.judgeFileFormat(fileNameStr);
      if (!fileFormart) {
        //将当前不合法文件移除

        this.wrongfulPop("文件不合法", "您上传的文件不合法，请重新上传");
        needReselect = true;

      }
      //判断文件上传个数大于30个
      if (this.uploader.queue.length > 30) {
        this.fileTotalExcess("选择文件过多", "已选择文件超过单次限制", "30", "个，请重新选择。");
        needReselect = true;
      }
      //判断文件大小
      var fileSize = this.uploader.queue[i]['file']['size'];
      var maxFileSizeHH = 1024 * 1024 * 10;

      if (fileSize > maxFileSizeHH) {

        //提示存在单个文件大于10M
        this.fileTotalExcess("选择文件过大", "已选择的文件中有超过", "10M", "的文件，请重新选择。");
        needReselect = true;

      }

      // if(判断文件是否重名){
      //   needReselect = true;
      // }
      //如果需要重新选择，则把刚刚选择的文件清除出队列
      if (needReselect) {
        for (let k = this.uploadEXENum; k < this.uploader.queue.length; k++) {
          this.uploader.removeFromQueue(this.uploader.queue[k]);
          k--;
        }
        return;
      }

      //var displayImage = this.setFileNameCatchImage(fileNameStr);

      //this.selectFileList.push(displayImage);//delete by xiezhilei 不清楚具体功能，暂时注释。
      this.uploader.queue[i].withCredentials = false;
    }


    $("#disableButton").show();
    $("#onSubmitButton").hide();

    this.uploadFile();



  }

  deleteFile(i, isSucDel,serialNo) {
    // this.uploader.removeFromQueue(i);
  this.uploader.queue[i].remove();
    if (isSucDel == 0) {
      this.failUploadFileNum--;
    } else {
      this.sucUploadFileNum--;
      //循环删除掉   成功上传的文件
      for(var j=0;j<this.submitFileOBJ['suc'].length;j++){
        if(serialNo==this.submitFileOBJ['suc'][j].serialNo){
          this.submitFileOBJ['suc'].splice(j,1);
        }
      }
      this.selectFileList.splice(i,1);

    }
    this.uploadEXENum--;

    if(this.uploader.queue.length==0){
      $("#disableButton").show();
      $("#onSubmitButton").hide();
    }else{
      $("#disableButton").hide();
      $("#onSubmitButton").show()
    }


  }


  //1.弹出文件不合法弹出
  wrongfulPop(tipTitle, tipContent) {
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent;
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      // cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      $("#onSubmitButton").attr('disabled',false);
      return true;
    }
  }

  //2.点击"提交" 按钮的  文件过大过多的弹窗
  fileTotalExcess(tipTitle, tipContent, num, text) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "<span style='color:#ffa00a;'>" + num + "</span>" + text + "</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      $("#onSubmitButton").attr('disabled',false);
      return true;
    }
  }

  //3.点击"取消" 按钮的弹窗
  popNextErrorModal(tipTitle, tipContent) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent;
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      $(".upload-file-box").remove();
      $("#material_tab1").show();
      $("#material_tab2").hide()
      that.uploader.clearQueue();
      that.submitFileOBJ = {'suc': [], 'fail': []};
      that.sucUploadFileNum = 0;//已成功上传文件数
      that.failUploadFileNum = 0;//上传失败文件个数
      that.uploadEXENum = 0
      return true;
    }
  }

  netIsOk:boolean=true;
  //4.点击"提交" 按钮的弹窗(这个是上传有失败的文件处理的弹窗)
  popUploadModal(tipTitle, tipContent, text) {
    var that = this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tipContent} <span style='color:#ffa00a;'>${this.failUploadFileNum}</span> ${text}</div>`
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      cancelButtonClickCallback: myCancelButtonClickCallback,
      closeImageClickCallback: myCancelButtonClickCallback
    });
    function myCancelButtonClickCallback() {
      $(this).closeModal();
      if(that.uploader.queue.length==0){
        $("#disableButton").show();
        $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      $("#onSubmitButton").attr('disabled',false);
      return true;
    }

    function myConfirmButtonClickCallback() {
      that.onSubmitUploadFileInterfaceFunc();//接口调用
      //that.uploader.clearQueue();
      // that.submitFileOBJ = {'suc': [], 'fail': []};
      // that.sucUploadFileNum = 0;//已成功上传文件数
      // that.failUploadFileNum = 0;//上传失败文件个数
      // that.uploadEXENum = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传


      // $(".upload-file-box").remove();
      if(that.netIsOk){
        alert('网断了');
      }else {
        $("#material_tab1").show();
        $("#material_tab2").hide();
        that.submitFileOBJ = {'suc': [], 'fail': []};
        that.sucUploadFileNum = 0;//已成功上传文件数
        that.failUploadFileNum = 0;//上传失败文件个数
        $(".upload-file-box").remove();
        that.uploadEXENum = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
        that.uploader = new FileUploader({
          url: that.networkService.fileUploadUrl,
          method: "POST",
          itemAlias: "file",

        });
      }
      $(this).closeModal();

      if(that.uploader.queue.length==0){
        $("#disableButton").show();
        $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      $("#onSubmitButton").attr('disabled',false);


      return true;
    }
  }


  //5.点击"提交" 按钮的弹窗(这个是上传没有失败的文件处理的弹窗)
  popNextSuccessModal(tipTitle, tiptxt, tipContent, text) {
    var that = this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}<span style='color:#ffa00a;'>${this.sucUploadFileNum}</span>${tipContent}<span id='secondValue'>5</span>${text}</div>`
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      // that.onSubmitUploadFileInterfaceFunc();//接口调用
      that.sucUploadFileNum = 0;//已成功上传文件数
      that.failUploadFileNum = 0;//上传失败文件个数
      that.uploadEXENum = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
      that.selectFileList=[];

      that.uploader.clearQueue();
      that.submitFileOBJ = {'suc': [], 'fail': []};

      $(".upload-file-box").remove();
      $("#material_tab1").show();
      $("#material_tab2").hide();
      $(this).closeModal();
      if(that.uploader.queue.length==0){
        $("#disableButton").show();
        $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      $("#onSubmitButton").attr('disabled',false);
      return true;
    }


    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          // that.onSubmitUploadFileInterfaceFunc();//接口调用
          that.sucUploadFileNum = 0;//已成功上传文件数
          that.failUploadFileNum = 0;//上传失败文件个数
          that.uploadEXENum = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
          that.uploader.clearQueue();
          that.submitFileOBJ = {'suc': [], 'fail': []};
          that.selectFileList=[];

          clearInterval(reduceSecondValue);

          $(".upload-file-box").remove();
          $("#material_tab1").show()
          $("#material_tab2").hide()

          $(this).closeModal();
          if(that.uploader.queue.length==0){
            $("#disableButton").show();
            $("#onSubmitButton").hide();
          }else{
            $("#disableButton").hide();
            $("#onSubmitButton").show()
          }
          $("#onSubmitButton").attr('disabled',false);
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
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

  // 验证普通格式
  judgeFileFormat(fileName: string) {
    var reg = /^.*(.jpg|.jpeg|.png|.gif|.doc|.docx|.xls|.xlsx|.pdf|.htm|.html|txt)$/;
    //判断选中文件格式是否合法
    let fileNameToLowerCase = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();//把上传的文件名转成小写格式

    var q_flag = reg.test(fileNameToLowerCase);

    return q_flag;

  }


//  补充材料，点击"补充材料"按钮切换到上传的界面
  annexMaterialChange(materialObj) {
    $("#material_tab1").hide();
    $("#material_tab2").show();

  }


  // uploadSuccess:boolean=false;

  //补充材料接口
  //点击提交  文件上传成功以后   弹出提示框  显示文件已经上传成功  （之前是把提交的交互放在弹窗中的
  // 现在的话要放在点击提交按钮中，那么只有当交互成功以后才可以弹出提示窗 所以应该把弹窗放在交互成功以后，不然的话，执行顺序不一定）
  onSubmitUploadFileInterfaceFunc(uploadSuccess?) {
    var Obj = {
      'head': {
        'functionNo': 'HH000015',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'uploadFileList': this.sucFileList,
        'loanSerialNo': this.loanSerialNo,
        "fileType": this.selectItemNameDisplayCode
      }
    };

    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        // if(uploadSuccess){
        //   this.popNextSuccessModal("补充材料成功", '成功上传', "个文件，", "秒钟返回进件材料页面")
        // }
        if (this.networkService.onJudgeSuccessful(res)) {


          //提交成功  提示用户
          this.popNextSuccessModal("补充材料成功", '成功上传', "个文件，", "秒钟返回进件材料页面");
          this.netIsOk=true;
          //调用查询进件详情信息，将当前选中的材料类型文件列表摘出来，赋值给selectedMaterialDisplayImages
          this.getFileList(this.selectItemNameDisplayCode);

        } else {
          //提交失败 提示用户
        }

      } else {
        //网络出现问题 提示用户
        // alert("网络有问题");
        this.netIsOk=false;
      }

    });
  }

  //获取当前刚刚上传材料类型的最新文件列表
  getFileList(materialTypeCode) {
    var BusinessData = this.commonFunc.getBusinessData();
    //var bmsLoanCode = BusinessData.bmsLoanCode;
    var Obj = {
      'head': {
        'functionNo': 'HH000010',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'loanSerialNo': this.loanSerialNo
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          let materialTypeList = res.data.body.materialTypeList;
          for(let i=0;i<materialTypeList.length;i++){
            let materialType = materialTypeList[i];
            if(materialType.materialTypeCode == materialTypeCode){

              //调用页面文件列表数据处理方法
              this.meterialNumberFunc(materialType.fileList);
            }
          }
          this.materialTypeListData = res.data.body.materialTypeList;
        }

      }

    });

  }

  // fileUploadlPopup() {
  //   this.popUploadModal('文件上传提交', "有", "个文件上传失败，是否提交已成功上传的文件？");
  // }

  //材料补充补充接口
  onSubmitUploadFileFunc() {
    $("#onSubmitButton").attr('disabled',true);
    this.sucFileList = this.submitFileOBJ['suc'];
    var failFileList = this.submitFileOBJ['fail'];

    if (this.failUploadFileNum > 0) {
      //存在上传情况  需要提示用户是否继续上传文件
      this.popUploadModal('文件上传提交', "有", "个文件上传失败，是否提交已成功上传的文件？");
    } else if (this.failUploadFileNum == 0) {
      this.onSubmitUploadFileInterfaceFunc(true);//接口调用


      // if(this.uploadSuccess){
      //   console.log('+++++++++++++++++++++++++++++++++++',this.uploadSuccess);
      //   this.popNextSuccessModal("补充材料成功", '成功上传', "个文件，", "秒钟返回进件材料页面")
      // }
    }
    // this.onSubmitUploadFileInterfaceFunc();
  }

  //补充材料的 取消按钮  的方法
  cancelPopup() {
    //根据是否有上传 的内容判断取消按钮的是弹窗还是返回上一个页面

    var smallPhoto = $("#smallPhoto").html();
    if (smallPhoto == undefined || smallPhoto == "") {
      //没有有上传文件 取消按钮返回上一个页面
      $("#material_tab1").show()
      $("#material_tab2").hide()
      $(".uploadFileBox").html("");

    } else {
      //有上传文件 取消按钮弹窗
      this.popNextErrorModal("取消上传", "已上传的文件将不会保存，是否继续？");

    }


  }

  //页面标题跳转
  querySection() {
    this.router.navigate(['/home/query'])
  }


}
