import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
import {environment} from "../../../../environments/environment";
import {FileUploader} from "ng2-file-upload";

declare var $: any;
declare var laydate: any;
@Component({
  selector: 'app-manual-registration',
  templateUrl: './manual-registration.component.html',
  styleUrls: ['./manual-registration.component.css']
})
export class ManualRegistrationComponent implements OnInit {
  progressLength: number;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,
              public ref: ChangeDetectorRef) {
    this.progressLength = 0;
    this.ref = ref; }

  contractCode:any;
  receiptValue:string;
  manulRegisterInit:any={};
  accountTypeCode=this.receiptValue;
  failUploadFileNum = 0;//上传失败文件个数

  ngOnInit() {

    this.contractCode=this.activetedRoute.snapshot.params['id'];
    this.timedicpker();
    this.InitQueryEntryList();

    $("#receipt").select2({});
    $("#repayment").select2({});

    $("#receipt").change( ()=> {
      this.accountTypeCode=$("#receipt").val();
    });
    $("#repayment").change( ()=> {
      this.accountTypeCode=$("#repayment").val();
    });

  //  校验到账日期（暂时无法完成）


  //  校验还款金额（小数点后最多两位  不能为空）

    this.ValidateId("applyMoney", this.Validates.checkNumber, 'bottom', this.tipsMarginLeft, "不能为空");

    //收款账户不能为空
    this.ValidateIdForSelect2("receipt", this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");

    //还款来源不能为空
    this.ValidateIdForSelect2("repayment", this.Validates.checkNull, 'bottom', this.tipsMarginLeft, "不能为空");

  }

  //日历插件
  timedicpker() {
    //因为laydate.js使用的是立即执行函数，所以在组件初始化时，重新加载js，然后再进行我们的日历字段设置。
    // 这个地方以后要考考虑重写laydate，改造成非立即执行函数，这样避免了多次加载。暂时先这样。
    $.getScript("/assets/js/laydate/laydate.js").then(function () {

      /*日期插件*/
      var start = {
        elem: '#applyTimeBegin',
        format: 'YYYY-MM-DD',
        istime: false,
        min: "2000-1-1 00:00:00", //设定最小日期为当前日期
        max: laydate.now(), //最大日期
        istoday: false,
        isclear: true,
        fixed: true,
        choose: function (datas) {
          end.min = datas; //开始日选好后，重置结束日的最小日期
          // end.start = datas //将结束日的初始值设定为开始日
        }
      };
      var end = {
        elem: '#applyTimeEnd',
        format: 'YYYY-MM-DD',
        min: "2000-1-1 00:00:00",
        max: '2099-06-16 23:59:59',
        istime: false,
        istoday: false,
        isclear: true,
        choose: function (datas) {
          start.max = datas; //结束日选好后，重置开始日的最大日期
        }
      };

      $("#applyTimeBegin").bind({
        focusout: function () {
          if ($("#applyTimeBegin").val() == "") {
            end.min = "2000-1-1";
          }
        }
      });
      // $("#applyTimeEnd").bind({
      //   focusout: function () {
      //     if ($("#applyTimeEnd").val() == "") {
      //       start.max = laydate.now();
      //     }
      //   }
      // });

      laydate(start);
      // laydate(end);
    });

  }
  //初始化
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100040',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'contractCode':this.contractCode,
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          this.manulRegisterInit=res.data.body;
          var accountTypeList = res.data.body.accountTypeList;
          var fundSourceList=res.data.body.fundSourceList
          //绑定成熟数据
          this.add_option(accountTypeList, '#receipt', ['accountTypeCode', 'accountTypeName']);
          this.add_option(fundSourceList, '#repayment', ['fundSourceCode', 'fundSourceName']);

        }

      }

    });

  }

  //绑定数据 待重构该部分代码
  add_option(array, id, arr) {
    var x;
    for (x in array) {
      var name, code;
      for (var y in array[x]) {
        if (y == arr[0]) {
          code = array[x][y];
        } else if (y == arr[1]) {
          name = array[x][y];
        }
        ;
      }
      if (x == 0) {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      } else {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      }
    }
  }

  //面包屑导航链接
  querySection(){
    this.router.navigate(["/home/repaymentRegisterQuery"])
  }
  //主按钮链接
  querySectionLink(){
    this.router.navigate(["home/repaymentRegisterQuery/repayment-register-detail/selectLoan"])
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
  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    //遍历已选择的文件，判断合法性（文件大小，（重名？））
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


    // $("#disableButton").show()
    // $("#onSubmitButton").hide()

    this.uploadFile();



  }

  deleteFile(i, isSucDel) {
    this.uploader.queue[i].remove();
    //把列表删除
    // this.selectFileList.remove()

    if (isSucDel == 0) {
      this.failUploadFileNum--;
    } else {
      this.sucUploadFileNum--;
      this.selectFileList.splice(i,1);
    }
    this.uploadEXENum--;

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
      that.router.navigate(['/home/repaymentRegisterQuery/repayment-register-detail/selectLoan']);
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
        // $("#disableButton").show();
        // $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      // $("#onSubmitButton").attr('disabled',false);
      return true;
    }

    function myConfirmButtonClickCallback() {
      that.onSubmitUploadFileInterfaceFunc();//接口调用
      that.router.navigate(["/home/repaymentRegisterQuery/repayment-register-detail/selectLoan"]);
      //that.uploader.clearQueue();
      // that.submitFileOBJ = {'suc': [], 'fail': []};
      // that.sucUploadFileNum = 0;//已成功上传文件数
      // that.failUploadFileNum = 0;//上传失败文件个数
      // that.uploadEXENum = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传


      // $(".upload-file-box").remove();
      if(!that.netIsOk){
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
        // $("#disableButton").show();
        // $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      $("#onSubmitButton").attr('disabled',false);


      return true;
    }
  }


  //5.点击"提交" 按钮的弹窗(这个是上传没有失败的文件处理的弹窗)
  popNextSuccessModal(tipTitle, tiptxt, text) {
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
        // $("#disableButton").show();
        // $("#onSubmitButton").hide();
      }else{
        $("#disableButton").hide();
        $("#onSubmitButton").show()
      }
      $("#onSubmitButton").attr('disabled',false);

      that.router.navigate(["/home/repaymentRegisterQuery"])
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
            // $("#disableButton").show();
            // $("#onSubmitButton").hide();
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

  //6.还款凭证未上传提醒
  ensureButton(tipTitle, tiptxt,flag:boolean){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      that.onSubmitUploadFileInterfaceFunc(true);//接口调用
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
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

  // 验证普通格式
  judgeFileFormat(fileName: string) {
    var reg = /^.*(.jpg|.jpeg|.png|.gif|.doc|.docx|.xls|.xlsx|.pdf|.htm|.html|txt)$/;
    //判断选中文件格式是否合法
    let fileNameToLowerCase = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();//把上传的文件名转成小写格式

    var q_flag = reg.test(fileNameToLowerCase);

    return q_flag;

  }



  //补充材料
  selectedMaterial:number;//补充材料当前选中的对象
  userNo = this.user.getUserData().userNo;
  loginTokenKey = this.commonFunc.getloginToken();//登录时间戳
  sucFileList;//补充材料上传接口的传给后台的参数
  loanSerialNo: string = '132323';
  selectItemNameDisplayCode;//接口传值需要的code值
  submitFileOBJ: any = {'suc': [], 'fail': []};//补充材料中选中提交文件内容  suc-上传成功文件 fail-上传失败文件
  selectedMaterialDisplayImages : any = []; //补充材料右边显示图片的列表
  uploadEXENum: number = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
  sucUploadFileNum = 0;//已成功上传文件数
  uploadTime: string;
  selectFileList = [];
  selectItemNameDisplay: string = '';
  issmallPhoto: boolean = true;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮

  // uploadSuccess:boolean=false;

  //补充材料接口
  //点击提交  文件上传成功以后   弹出提示框  显示文件已经上传成功  （之前是把提交的交互放在弹窗中的
  // 现在的话要放在点击提交按钮中，那么只有当交互成功以后才可以弹出提示窗 所以应该把弹窗放在交互成功以后，不然的话，执行顺序不一定）
  remark:string='';
  onSubmitUploadFileInterfaceFunc(uploadSuccess?) {
    var receiptsDate = $("#applyTimeBegin").val();//到账日期
    var receiptsAmount = $("#applyMoney").val();//还款金额
    var fundSource = $("#repayment").val();//还款来源
    var Obj = {
      'head': {
        'functionNo': 'plms100041',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'contractCode':this.contractCode,
        'receiptsDate':receiptsDate,
        'receiptsAmount':receiptsAmount,
        'fundSource':fundSource, //还款来源
        'accountTypeCode':this.accountTypeCode,
        'remark':this.remark,
        'userNo': this.userNo,
        'fileList': this.sucFileList,
        'loanSerialNo': this.loanSerialNo,
        "fileType": this.selectItemNameDisplayCode
      }
    };

    this.networkService.postData(Obj, false).then(res => {
      $("#onSubmitButton").attr('disabled',true);

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if(uploadSuccess){
          this.popNextSuccessModal("还款到账登记成功", '还款到账登记成功', "秒钟返回选择借款合同页面")
        }
        if (this.networkService.onJudgeSuccessful(res)) {
          //提交成功  提示用户
          this.netIsOk=true;
          //调用查询进件详情信息，将当前选中的材料类型文件列表摘出来，赋值给selectedMaterialDisplayImages
          // this.getFileList(this.selectItemNameDisplayCode);

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
  // getFileList(materialTypeCode) {
  //   var BusinessData = this.commonFunc.getBusinessData();
  //   var bmsLoanCode = BusinessData.bmsLoanCode;
  //   var Obj = {
  //     'head': {
  //       'functionNo': 'HH000010',
  //       'userNo': this.userNo,
  //       'clientTimestamp': this.loginTokenKey
  //     },
  //     'body': {
  //       'userNo': this.userNo,
  //       'bmsLoanCode': bmsLoanCode
  //     }
  //   };
  //   this.networkService.postData(Obj, false).then(res => {
  //
  //     if (res.retCode == true) {
  //       // 请求网络通信处理方法
  //       if (this.networkService.onJudgeSuccessful(res)) {
  //         //登录成功处理逻辑
  //         let materialTypeList = res.data.body.materialTypeList;
  //
  //         for(var i=0;i<materialTypeList.length;i++){
  //           let materialType = materialTypeList[i];
  //           if(materialType.materialTypeCode == materialTypeCode){
  //             //console.log("----------",materialType.fileList);
  //             //调用页面文件列表数据处理方法
  //             this.meterialNumberFunc(materialType.fileList);
  //           }
  //         }
  //
  //       }
  //
  //     }
  //
  //   });
  //
  // }
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
          file['fileUrl'] = environment.server + "/fileLoad/getFile.json?serialNo=" + fielList[j].fileId;
        } else {
          //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
          file['fileUrl'] = defaultImgUrl;
        }
        file['fileName'] = fielList[j].originalFileName;
        file['uploadTime'] = fielList[j].uploadTime;
        file['fileId'] = fielList[j].fileId;
        this.selectedMaterialDisplayImages.push(file);
      }
    }

  }

  fileUploadlPopup() {
    this.popUploadModal('还款凭证上传失败提醒', "有", "个文件上传失败，是否确认提交还款到账登记结果？");
  }

  //材料补充补充接口
  onSubmitUploadFileFunc() {
    // //  校验格式 //日期  本金   资方
    var applyMoneyNull=this.Validates.checkNumber("applyMoney", 'bottom', this.tipsMarginLeft, "不能为空");
    var receiptNull =this.Validates.checkNull("receipt", 'bottom', this.tipsMarginLeft, "不能为空");
    var repaymentNull =this.Validates.checkNull("repayment", 'bottom', this.tipsMarginLeft, "不能为空");
    //  判断校验是否全部通过
    var validFlag = applyMoneyNull&&receiptNull&&repaymentNull;
    if(!validFlag){
      return false;
    }else {
      $("#onSubmitButton").attr('disabled',true);
      this.sucFileList = this.submitFileOBJ['suc'];
      var failFileList = this.submitFileOBJ['fail'];

      if (this.failUploadFileNum > 0) {
        //存在上传情况  需要提示用户是否继续上传文件
        this.fileUploadlPopup();
      } else if (this.failUploadFileNum == 0) {
        if(this.sucUploadFileNum==0){
        //  弹框提示  还款凭证未上传
          this.ensureButton('还款凭证未上传提醒','当前登记的还款到账记录未上传凭证，是否确认提交',true);
        }else {
          this.onSubmitUploadFileInterfaceFunc(true);//接口调用

        }

        //
        // if(this.uploadSuccess){
        //   console.log('+++++++++++++++++++++++++++++++++++',this.uploadSuccess);
        //   this.popNextSuccessModal("补充材料成功", '成功上传', "个文件，", "秒钟返回进件材料页面")
        // }
      }
      // this.onSubmitUploadFileInterfaceFunc();
    }
  }

  //补充材料的 取消按钮  的方法
  cancelPopup() {
    //根据是否有上传 的内容判断取消按钮的是弹窗还是返回上一个页面

    var smallPhoto = $("#smallPhoto").html();
    if (smallPhoto == undefined || smallPhoto == "") {
      //没有有上传文件 取消按钮返回上一个页面
      // $("#material_tab1").show()
      // $("#material_tab2").hide()
      // $("#uploadFileBox").html("");
      this.router.navigate(['/home/repaymentRegisterQuery/repayment-register-detail/selectLoan'])
    } else {
      //有上传文件 取消按钮弹窗
      this.popNextErrorModal("取消登记还款凭证提醒", "已登记的结果和上传文件将不会保存，是否确认？");

    }


  }

  //定义事件，上传文件

  uploadFileList:any=[];
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
            fileObj['fileCode']=fileObj.serialNo;
            // fileObj.removeItem("serialNo");
            delete fileObj.serialNo;

            var fileNameObj = fileObj.fileName;
            var fileSuffix  =fileObj.fileSuffix;
            this.uploadFileList = this.submitFileOBJ['suc'];  //是一个对象
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
            // this.selectFileList.push(imageSrc);
            this.uploadFileList.push(fileObj);

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
      // $("#disableButton").hide()
      // $("#onSubmitButton").show()


      // $("#uploadedfileNumber").hide();
      // $("#uploadingfileNumber").show();

    };

    this.uploader.onCompleteAll = () => {

      if (this.sucUploadFileNum > 0) { //提交按钮在全部上传失败后需要显示禁止按钮
        $("#disableButton").hide()
        $("#onSubmitButton").show()

      } else if (this.sucUploadFileNum == 0 && this.failUploadFileNum > 0) {
        // $("#disableButton").show()
        // $("#onSubmitButton").hide()

      }

      //显示是否正在是上传状态
      $("#uploadedfileNumber").show();
      $("#uploadingfileNumber").hide();
    }

    //取消上传
    this.uploader.onCancelItem = function (response, status, headers) {

    };


  }


  //下载文件
  downloadFile(file){
    var defaultImgUrl=this.setFileNameCatchImage(file.fileSuffix); //y原来用是的 judgeFileTypeFunc
    if ('needUrl' == defaultImgUrl) {
      window.open(environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode);
    } else {
      window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode;
    }

  }

  //数字校验
  tipsErrorDisplay: string = 'show';
  myErrorPosition: string = 'bottom';
  myMargainLeft = 3;
  tipsMarginLeft = 3;
  isZero = /\b(0+)/gi;

  //判断是否为空
  isEmpty(inputId) {
    if (null == $("#" + inputId).val() || '' == $("#" + inputId).val() || '' == $("#" + inputId).val().trim()) {
      return true;
    }
    return false;
  }

  Validates = {
    // 检测是否为空
    checkNull: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      if (this.isEmpty(inputId)) {
        $("#" + inputId).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      $("#" + inputId).removeTip();
      return true;
    },
    // 添加相应的错误样式  和错误提示
    addError: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      $("#" + inputId).createTip({
        margainLeft: myMargainLeft,
        errorText: errorText,
        errorPosition: myErrorPosition,//参数包括right,top-right,top-left
        errorDisplay: this.tipsErrorDisplay//参数包括hide,show
      });
      return false;
    },
    //检查数字为正数，最多只能有两位小数
    checkNumber: (id, myErrorPosition, myMargainLeft, errorText) => {
      var isresidenceNum = /^\d+(?:\.\d{1,2})?$/;
      var str = $("#" + id).val();

      if (this.isEmpty(id)) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      } else if (isresidenceNum.test(str) == false) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: '格式错误',
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      //去除开头的0
      if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
        var noZeroVal = str.replace(this.isZero, "");
        $("#" + id).val(noZeroVal);
      }

      $("#" + id).removeTip();
      return true;
    },
  };

  //验证input框
  ValidateId(id, Out, myErrorPosition, myMargainLeft, errorText) {
    $("#" + id).bind({
        focusout: function () {
          Out(id, myErrorPosition, myMargainLeft, errorText);
        },
        focusin: function () {
          $(this).removeTip();
        }
      }
    );
  }


  ValidateIdForSelect2(id, Out, myErrorPosition, myMargainLeft, errorText) {
    $("#" + id).on('select2:close', function (evt) {
      Out(id, myErrorPosition, myMargainLeft, errorText);
    });
    $("#" + id).on('select2:open', function (evt) {
      $("#" + id + '-select2-selection--single').removeTip();
    });
  }

}
