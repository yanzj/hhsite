import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FileUploader} from "ng2-file-upload";
import {environment} from "../../../environments/environment";

declare var $: any;
declare var laydate: any;

@Component({
  selector: 'app-batch-upload',
  templateUrl: './batch-upload.component.html',
  styleUrls: ['./batch-upload.component.css']
})
export class BatchUploadComponent implements OnInit {
  progressLength: number;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,
              public ref: ChangeDetectorRef) {
    this.progressLength = 0;
    this.ref = ref; }

  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  creditStartDate: string = '';//开始时间
  creditEndDate: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  currentItemLists: any=[];


  ngOnInit() {
    //1、日历插件调用
    this.timedicpker();
    this.InitQueryEntryList();

    $("#applyStatusList").select2({});

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
      $("#applyTimeEnd").bind({
        focusout: function () {
          if ($("#applyTimeEnd").val() == "") {
            start.max = laydate.now();
          }
        }
      });

      laydate(start);
      laydate(end);
    });

  }

  //初始化查询查询条件方法start
  InitQueryEntryList() {

    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo = userObj.userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100042',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          var statusList = res.data.body.statusList;

          //绑定成熟数据
          this.add_option(statusList, '#applyStatusList', ['statusCode', 'statusName']);


          //分页查询接口
          this.QueryEntryList()
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

  //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {
    var uploadUser = this.commonFunc.handleNilString($("#uploadUser").val());//上载用户
    var uploadTimeStart = this.commonFunc.handleNilString($("#applyTimeBegin").val()); //还款开始日期
    var uploadTimeEnd = this.commonFunc.handleNilString($("#applyTimeEnd").val());     //还款结束日期
    var statusCode = this.commonFunc.handleNilString($("#applyStatusList").val()); //选择状态

    return {
      "uploadUser": uploadUser,  //上载用户
      "uploadTimeStart": uploadTimeStart,
      "uploadTimeEnd": uploadTimeEnd,
      "statusCode": statusCode
    };

  }

  //批量上载查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var searchConditions = this.getSearchCondigtion();
    var Obj = {
      'head': {
        'functionNo': 'plms100043',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'uploadUser':searchConditions.uploadUser,   //上载用户
        'uploadTimeStart': searchConditions.uploadTimeStart, //还款开始日期
        'uploadTimeEnd': searchConditions.uploadTimeEnd,  //还款结束日期
        'statusCode': searchConditions.statusCode,//选择状态
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {

          //登录成功处理逻辑
          var pages = this.commonFunc.handleNilString(res.data.body.pages);
          if (pages == '') pages = '0';
          var currentPage = this.commonFunc.handleNilString(res.data.body.currentPage);
          if (currentPage == '') currentPage = '0';

          var objData = {
            'totalPage': pages,
            'currentPage': currentPage,
            'total': res.data.body.total

          };

          //设置页面显示 第几条至第几条
          this.totalListsCount = res.data.body.total;

          //首次获取分页信息 设置分页插件
          //  if(this.currentItemLists.length == 0){
          this.setPageFunc(objData, this);

          //  }
          this.currentItemLists = res.data.body.dataList;
          this.setDisplayItemsCount();


          //修改查询列表状态 暂时前端映射 待需求确认之后 以后台为准
          //material 充材料为 1-不显示  2-显示

          for (var i = 0; i < this.currentItemLists.length; i++) {
            var obj = this.currentItemLists[i];
            var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
            this.currentItemLists[i]["statusName"] = resultObj['statusName'];
          }

          this.totalItemLists[this.selectPageNo] = this.currentItemLists;


        }
      }

    });
    return false;
  };

  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    var currentObj: any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.currentItemLists.length);
    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }

  }

  // 初始化分页工具
  setPageFunc(data: any, obj: any) {

    //设置分页插件
    $(".tablePageCode").createPage({
      pageCount: data.totalPage,
      current: this.selectPageNo,
      backFn: function (p) {
        //p当前页


        obj.setCurrentDataFunc(p.toString());

      }
    });

  }


  //分页数据处理
  setCurrentDataFunc(page: string) {
    //设置当前选中页数
    this.selectPageNo = page;

    //1.根据当前请求页 查询totalItems中是否存在这条数据 存在则直接赋值与current
    var pageKey: string = page;
    if (this.totalItemLists.hasOwnProperty(pageKey)) {
      var itemObj = this.totalItemLists[pageKey];
      this.currentItemLists = itemObj;

      //显示第几条数据
      this.setDisplayItemsCount();
      return;
    }

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


  }



  //点击‘文件上载’


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
  fileCodeNum;
  issmallPhoto: boolean = true;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮
  failUploadFileNum = 0;//上传失败文件个数
  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });
  // 验证普通格式
  judgeFileFormat(fileName: string) {
    var reg = /^.*(.xls|.xlsx)$/;
    //判断选中文件格式是否合法
    let fileNameToLowerCase = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();//把上传的文件名转成小写格式

    var q_flag = reg.test(fileNameToLowerCase);

    return q_flag;

  }

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

        this.wrongfulPop("文件格式错误", "上载文件必须为xls或xlsx格式，请重新选择文件上载。");
        needReselect = true;

      }

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


    this.uploadFile();




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

            fileObj['fileCode']=fileObj.serialNo;
            this.fileCodeNum=fileObj['fileCode'];



            // fileObj.removeItem("serialNo");
            delete fileObj.serialNo;

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
            // this.selectFileList.push(imageSrc);






            uploadFileList.push(fileObj);



            this.sucUploadFileNum++;
            this.onSubmitUploadFileInterfaceFunc();

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
  //根据文件名 确认文件类型 对应显示的图片
  setFileNameCatchImage(fileSuffix) {
    if(fileSuffix){
      fileSuffix = fileSuffix.toLowerCase();
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

  remark:string='';

  onSubmitUploadFileInterfaceFunc(uploadSuccess?) {

    var Obj = {
      'head': {
        'functionNo': 'plms100044',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'fileCode':this.fileCodeNum,
      }
    };

    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {
          //提交成功  提示用户

          this.checkoutPop('批量上载校验中','系统正在校验上载文件，稍后请刷新页面查看校验结果。')
          this.QueryEntryList();
        } else {
          //提交失败 提示用户
        }

      } else {
        //网络出现问题 提示用户
        // alert("网络有问题");
        //this.netIsOk=false;
      }

    });
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

  //2.弹出文件不合法弹出
  checkoutPop(tipTitle, tipContent) {
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

  download(){
    window.location.href = environment.server + "/fileLoad/getModelFile.json?type=00";
  }
  downloadTemplate(file){
    window.location.href = environment.server + "/fileLoad/getFile.json?serialNo=" + file.fileCode;
  }
  //搜索按钮点击事件
  onSearchBtnClick() {

    this.selectPageNo = '1';
    this.QueryEntryList();

  }

  //主按钮链接
  querySection(){
    this.router.navigate(["/home/repaymentRegisterQuery"])
  }

}
