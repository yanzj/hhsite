import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {FileUploader} from "ng2-file-upload";

declare var $: any;
declare var laydate: any;

@Component({
  selector: 'app-repayment-account-detail',
  templateUrl: './repayment-account-detail.component.html',
  styleUrls: ['./repayment-account-detail.component.css']
})
export class RepaymentAccountDetailComponent implements OnInit {
  contractCode:string; //借款合同数据唯一标识
  professionalData:any=[];
  progressLength: number;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,
              public ref: ChangeDetectorRef) {
    this.progressLength = 0;
    this.ref = ref; }


    receiptValue:string;
    manulRegisterInit:any={};
    accountTypeCode=this.receiptValue;
    meterialNumber:any=[];

    selectFileList:any = [];

//补充材料

  userNo = this.user.getUserData().userNo;
  loginTokenKey = this.commonFunc.getloginToken();//登录时间戳
  sucFileList;//补充材料上传接口的传给后台的参数
  loanSerialNo: string = '';
  selectItemNameDisplayCode;//接口传值需要的code值
  submitFileOBJ: any = {'suc': [], 'fail': []};//补充材料中选中提交文件内容  suc-上传成功文件 fail-上传失败文件
  uploadEXENum: number = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
  sucUploadFileNum = 0;//已成功上传文件数
  failUploadFileNum = 0;//上传失败文件个数
  uploadTime: string;//图片上传的时间
  issmallPhoto: boolean = true;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮
  currentItemLists:any={}
  receiptsCode:any;
  fileCode:any;
  ngOnInit() {
    this.receiptsCode=this.activetedRoute.snapshot.params["id"];
    this.InitQueryEntryList();
  }


  //初始化查询查询条件方法start
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100056',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'receiptsCode':this.receiptsCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          this.currentItemLists=res.data.body;
          this.professionalData=[
            {content:'借款合同编号：',styleName:'message-title font-weight-bold'},
            {content:this.currentItemLists.contractNo,styleName:'message-number'}
          ];
          if(this.currentItemLists.fileList){
            if(this.currentItemLists.fileList.length==0){
              $("#disableButton").show();
              $("#onSubmitButton").hide();
            }
            this.meterialNumberFunc(this.currentItemLists.fileList);
          }else {

          }

        }

      }

    });

  }


  getFileList:any=[];
  fileListCode:any=[];
  meterialNumberFunc(fileList){
    if(fileList){

      this.meterialNumber=fileList;

      for(var j=0;j<this.meterialNumber.length;j++){
        var defaultImgUrl=this.setFileNameCatchImage(this.meterialNumber[j].fileSuffix);

        if ('needUrl' == defaultImgUrl) {
          this.getFileList.push(environment.server + "/fileLoad/getFile.json?serialNo=" + this.meterialNumber[j].fileCode);
          this.fileListCode.push(this.meterialNumber[j].fileCode);
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          //  file['fileUrl'] = defaultImgUrl;
          this.getFileList.push(defaultImgUrl);
        }
      }
    }

  }

  //下载文件
  downloadFile(file){
    var defaultImgUrl=this.setFileNameCatchImage(file.fileSuffix); //y原来用是的 judgeFileTypeFunc
    if ('needUrl' == defaultImgUrl) {
      window.open(environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileId);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileId;
    }

  }

  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });
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
            var fileSuffix = fileObj.fileSuffix;
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
                selectFileListObj.push(this.result);
              };
            } else {
              //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
              this.selectFileList.push(imageSrc);
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


  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    //遍历已选择的文件，判断合法性（文件大小）
    for (let i = 0; i < this.uploader.queue.length; i++) {
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

      // var displayImage = this.setFileNameCatchImage(fileNameStr);

      // this.selectFileList.push(displayImage);//delete by xiezhilei 不清楚具体功能，暂时注释。
      this.uploader.queue[i].withCredentials = false;
    }


    $("#disableButton").show()
    $("#onSubmitButton").hide()

    this.uploadFile();



  }
   deleteNum:number=0; //记录原有的图片删除个数
  // deleteFile=(i, isSucDel) =>{
  //
  //   console.log('222222222222i:',i,)
  //   this.uploader.queue[i].remove();
  //     if (isSucDel == 0) {
  //
  //       this.failUploadFileNum--;
  //     } else if(isSucDel==1) {
  //       this.sucUploadFileNum--;
  //       this.selectFileList.splice(i,1);
  //     }
  //     this.uploadEXENum--;
  //
  //
  //
  //   if(this.uploader.queue.length==0&&this.currentItemLists.fileList.length==0){
  //     $("#disableButton").show();
  //     $("#onSubmitButton").hide();
  //   }else{
  //     $("#disableButton").hide();
  //     $("#onSubmitButton").show()
  //   }
  //
  //
  // }
  deleteFile=(i, isSucDel) =>{

    this.uploader.queue[i].remove();
    if (isSucDel == 0) {

      this.failUploadFileNum--;
    } else if(isSucDel==1) {
      this.sucUploadFileNum--;
      this.selectFileList.splice(i,1);
    }
    this.uploadEXENum--;



    if(this.uploader.queue.length==0&&this.currentItemLists.fileList.length==0){
      $("#disableButton").show();
      $("#onSubmitButton").hide();
    }else{
      $("#disableButton").hide();
      $("#onSubmitButton").show()
    }


  };


  deleteFileList:any=[];//从后台请求的数据中获得的文件list  然后删除
  deleteFileFun(i,item){
    item.splice(i,1);
    this.deleteFileList.push({'fileCode':this.fileListCode[i]});
    this.fileListCode.splice(i,1);
    this.getFileList.splice(i,1);
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
      closeImageClickCallback: mycloseImageClickCallback
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
      that.router.navigate(["/home/repaymentAccountModify"])
      return true;
    }
    function mycloseImageClickCallback() {
      $(this).closeModal();
      $(".upload-file-box").remove();
      $("#material_tab1").show();
      $("#material_tab2").hide()
      that.uploader.clearQueue();
      that.submitFileOBJ = {'suc': [], 'fail': []};
      that.sucUploadFileNum = 0;//已成功上传文件数
      that.failUploadFileNum = 0;//上传失败文件个数
      that.uploadEXENum = 0;
      return true;
    }
  }

  netIsOk:boolean=true;
  //4.点击"提交" 按钮的弹窗(未有上传凭证的弹窗)
  // popUploadModalsucsess(tipTitle, text) {
  //   var that = this;
  //   var content = `<div style='margin-bottom: 50px; font-weight: bold;'> ${text}</div>`;
  //   $(this).popModal({
  //     width: '360px',
  //     title: tipTitle,
  //     content: content,
  //     confirmButton: true,
  //     cancelButton: true,
  //     closeImage: true,
  //     confirmButtonClickCallback: myConfirmButtonClickCallback,
  //     cancelButtonClickCallback: myCancelButtonClickCallback,
  //     closeImageClickCallback: myCancelButtonClickCallback
  //   });
  //   function myConfirmButtonClickCallback() {
  //
  //     // if(that.uploader.queue.length==0){
  //     //   $("#disableButton").show();
  //     //   $("#onSubmitButton").hide();
  //     // }else{
  //       $("#disableButton").hide();
  //       $("#onSubmitButton").show()
  //    // }
  //     $("#onSubmitButton").attr('disabled',false);
  //
  //     $(this).closeModal();
  //     that.router.navigate(["/home/repaymentAccountModify]);
  //     return true;
  //   }
  //
  //   function myCancelButtonClickCallback() {
  //
  //     // if(that.uploader.queue.length==0){
  //     //   $("#disableButton").show();
  //     //   $("#onSubmitButton").hide();
  //     // }else{
  //     $("#disableButton").hide();
  //     $("#onSubmitButton").show()
  //     // }
  //     $("#onSubmitButton").attr('disabled',false);
  //
  //     $(this).closeModal();
  //     return true;
  //   }
  //
  // }

  //5.点击"提交" 按钮的弹窗(这个是上传有失败的文件处理的弹窗)
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

      that.router.navigate(["/home/repaymentAccountModify"]);
      return true;
    }
  }


  //6.点击"提交" 按钮的弹窗(这个是上传没有失败的文件处理的弹窗)
  popNextSuccessModal(tipTitle, tiptxt,  text) {
    var that = this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}<span id='secondValue'>5</span>${text}</div>`
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
      that.router.navigate(["/home/repaymentAccountModify"]);
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
          that.router.navigate(["/home/repaymentAccountModify"]);
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



  // uploadSuccess:boolean=false;

  //补充材料接口
  //点击提交  文件上传成功以后   弹出提示框  显示文件已经上传成功  （之前是把提交的交互放在弹窗中的
  // 现在的话要放在点击提交按钮中，那么只有当交互成功以后才可以弹出提示窗 所以应该把弹窗放在交互成功以后，不然的话，执行顺序不一定）
  onSubmitUploadFileInterfaceFunc(uploadSuccess?) {
    let fileList: any=[];
    for(let i=0;i<this.submitFileOBJ['suc'].length;i++){
      var fileCode = {"fileCode":this.submitFileOBJ['suc'][i].serialNo};
      fileList.push(fileCode);

    }
    var Obj = {
      'head': {
        'functionNo': 'plms100047',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'receiptsRecordCode':this.currentItemLists.receiptsRecordCode,
        'fileList':fileList,
        'deleteFileList':this.deleteFileList,

      }
    };

    this.networkService.postData(Obj, false).then(res => {

      $("#onSubmitButton").attr('disabled',false);

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          this.popNextSuccessModal("还款到账修改成功", '还款到账修改成功，', "秒钟后返回还款到账修改页面。")
          this.netIsOk=true;

          // this.InitQueryEntryList();
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





  //点击‘提交按钮’
  onSubmitUploadFileFunc() {
    $("#onSubmitButton").attr('disabled',true);
    this.sucFileList = this.submitFileOBJ['suc'];
    var failFileList = this.submitFileOBJ['fail'];

    if (this.failUploadFileNum > 0) {
      //存在上传情况  需要提示用户是否继续上传文件
      this.popUploadModal('还款凭证上传失败提醒', "有", "个文件上传失败，将不会保存，是否确认提交还款到账登记结果？");
    // } else if (this.sucUploadFileNum == 0) {
    //   //存在上传情况  需要提示用户是否继续上传文件
    //   this.popUploadModalsucsess('还款凭证未上传提醒', "当前登记的还款到账记录未上传凭证，是否确认提交？");
    } else if (this.failUploadFileNum == 0) {
      this.onSubmitUploadFileInterfaceFunc(true);//接口调用

    }

  }

  //补充材料的 取消按钮  的方法
  cancelPopup() {
    //根据是否有上传 的内容判断取消按钮的是弹窗还是返回上一个页面

    var smallPhoto = $("#smallPhoto").html();
    if (smallPhoto == undefined || smallPhoto == "") {
      //没有有上传文件 取消按钮返回上一个页面
      $("#material_tab1").show()
      $("#material_tab2").hide()
      $("#uploadFileBox").html("");
      this.router.navigate(["/home/repaymentAccountModify"])

    } else {
      //有上传文件 取消按钮弹窗
      this.popNextErrorModal("取消修改还款到账记录", "已做的修改将不会保存，是否确认？");

    }


  }
//面包屑导航链接
  querySection(){
    this.router.navigate(["/home/repaymentAccountModify"])
  }
}
