import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {FileUploader} from "ng2-file-upload";
import {ComSectionComponent} from "../../common-model/com-section/com-section.component";


import {enquiryService} from "../enquiry/enquiryService.service";


declare var $: any;
@Component({
  selector: 'app-enquiry-resubmit',
  templateUrl: './enquiry-resubmit.component.html',
  styleUrls: ['./enquiry-resubmit.component.css'],
  providers: [enquiryService]
})
export class EnquiryResubmitComponent implements OnInit {



  isL : string = '0';// 0-显示内容 1-显示不支持字眼 2-完全不显示
  isR : string = '0';// 0-显示内容 1-显示不支持字眼 2-完全不显示
  lHouseNo :string = '001';
  rHouseNo :string = '002';
  lValue:string = "";
  rValue:string = "";


  gyMark: string = '01';
  bsMark: string = '02';
  rgMark: string = '03';
  statusMark: string = '';

  isEXE:boolean = false;


  cityList: any = [];
  serialNo: string = '';
  // 文件的fileSerialNo
  fileSerialNo: string = '';
  fileSerialNo2: string = '';
  //估价公司的长度
  unitList: any = [];

  //设置进度条的长度
  progressLength: number = 0;
  progressLength2: number = 0;


  //测试的值
  progress: number = 0;

  //决定进度条是否显示
  isShow: boolean = false;
  isShow2: boolean = false;

  isShow3: boolean = false;
  isShow4: boolean = false;

  //定义是否有文件上载
  isFile: boolean;
  isFile2: boolean;
  //决定是上传中还是上传失败
  isUp: boolean = true;
  isUp2: boolean = true;
  //定义提交文件要传给后台的参数
  fileName: string;
  fileName2: string;
  //城市是否为空的标志位，如果城市是空的话  则区域5不显示
  isCityEmpty: boolean = true;
  //左边的companyCode

  //文件上传成功的时间
  uploadTime: string;
  uploadTime2: string;

  //人工显示
  persionEnquiryedTitleList = ['评估价'];

  //公寓评估结果 标题项目列表
  gyEnquiryedTitleList = ["小区地址", "楼栋号", "房号", "总楼层", "所在楼层", "建筑面积", "评估价"];

  //别墅评估结果 标题项目列表
  bsEnquiryedTitleList = ["行政区域", "房屋地址", "建筑面积", "评估价"];

  //左边标题列表
  leftDisplayTitleList: any = [];
  //右边标题列表
  rightDisplayTitleList: any = [];


  //左边显示的结果
  leftValueList: any = ["", "", "", "", "", "", ""];
  //右边显示的结果
  rightValueList: any = ["", "", "", "", "", "", ""];

 //当一家公司通过 一家为空时 提示信息
 displayCurrentNullString:string = '';
 companyParamList:any = [];


 //
 cityName:string = '';
 houseName:string = '';

  //图片的路径
  imgSrc1:string;
  imgSrc2:string;

  //是否显示跳转的提示
  isGotoPerson: any = [];

  //从何种路径进来的
  resubmit:string = '';
  resubmitFlag:string = '';

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private myEnquiryService:enquiryService,
              public ref: ChangeDetectorRef,
              private comSectionComponent: ComSectionComponent) {

    this.ref = ref
  }

  //页面当前流程状态显示
  itemStatusLists: any = ["房产估价", "进件申请", "风控审批", "合同制作", "协议公证", "抵押登记", "授信完成"];
  itemSelectStatus: string = '房产估价';
  itemSelectIndex: number = 0;//初始化

  setSelectIndex() {
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if (this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }
    }
  }


  ngOnInit() {

    this.comSectionComponent.currentLevelMenuId = '2017070315180000000002';
    this.comSectionComponent.currentChildMenuId = '2017070315180000000005';

    // this.commonFunc.setResubmitStatus("enquirySearch");

    this.resubmitFlag=this.commonFunc.getResubmitStatus();
    if(this.resubmitFlag=='enquirySearch'){
      this.resubmit='询价查询';
    }else if(this.resubmitFlag=='gtasks'){
      this.resubmit="待办任务";
    }else if(this.resubmitFlag=='message'){
      this.resubmit="消息通知";
    }

    this.setVaildFunc();
    //请求询价详情数据
    this.InitEnquiryDataFunc();
    //当输入框输入完成时 校验数据
    $('.msg-input').keyup(function(){
            var c=$(this);
            if(/[^\d]/.test(c.val())){//替换非数字字符
              var temp_amount=c.val().replace(/[^\d]/g,'');
              $(this).val(temp_amount);
            }
         })


  }

  enquireSearch(){
    if(this.resubmitFlag=='enquirySearch'){
      this.router.navigate(['/home/enquirySearch']);
    }else if(this.resubmitFlag=='gtasks'){
      this.router.navigate(['/home/gtasks']);
    }else if(this.resubmitFlag=='message'){
      this.router.navigate(['/home/message'])
    }
  }
  history(){
    this.commonFunc.setHistroyStatus('resubmitEnquiry');
    this.router.navigate(['/home/enquiryHistorySearch'])
    // this.networkService.
  }
  // 详情初始化的请求 获得
  // 详情初始化的请求 获得
InitEnquiryDataFunc() {
  var loginTokenKey = this.commonFunc.getloginToken();
  var userObj = this.user.getUserData();
  var userNo = userObj.userNo;

  var enquiryObj = this.commonFunc.getBusinessData();
  var serialNo = enquiryObj.serialNo;

  var Obj = {
    'head': {
      'functionNo': 'HH000120',
      'userNo': userNo,
      'clientTimestamp': loginTokenKey
    },
    'body': {
      'userNo': userNo,
      'serialNo': serialNo
    }
  };
  this.networkService.postData(Obj, false).then(res => {

    if (res.retCode == true) {
      // 请求网络通信处理方法
      if (this.networkService.onJudgeSuccessful(res)) {
        this.commonFunc.handleNetworkDaraFormaFunc(res);
        this.setUIData(res.data.body);
        //城市
        this.cityName=res.data.body.cityName;

       //获得房屋类型
        this.houseName=res.data.body.houseTypeName;

      //
         var companyParamList = res.data.body.companyParamList;
        if(companyParamList.length == 1){
          this.isGotoPerson[0] = res.data.body.companyParamList[0].status;
        }
        if(companyParamList.length == 2){
          this.isGotoPerson[0] = res.data.body.companyParamList[0].status;
          this.isGotoPerson[1] = res.data.body.companyParamList[1].status;
        }
      }

    }
  });
}


  //测试数据
  //0-显示内容 1-显示不支持字眼 2-完全不显示
  setUIData(obj: any) {
    var objList = obj.companyParamList;
    var modelType = obj['modelType'];
    //设置左右显示情况
    this.setCompangDisplayFunc(objList, modelType);

   //当左边显示的时候 左边标题和内容
    if (this.isL == '0') {
      //判断别墅 公寓 人工
      this.setValueDisplayFunc(true, modelType, objList[0]);

      this.setTitleListDisplayContentFunc(true, modelType);



    } else if (this.isL == '1') {
      this.setUndisplayUI('leftDetailDIV');

    } else {

    }

    //当右边显示的时候 右边标题和内容
    if (this.isR == '0') {
      //判断别墅 公寓 人工
      this.setValueDisplayFunc(false, modelType, objList[1]);

      this.setTitleListDisplayContentFunc(false, modelType);
      //当自动别墅询价时 只显示输入内容 并且当前是城市
      if (modelType == '2' ) {
        // code...
        this.rightDisplayTitleList = ["评估价"];

      }



    } else if (this.isR == '1') {

      this.setUndisplayUI('rightDetailDIV');

	  }else{

    }

    this.isEXE = true;

  }
  //设置校验方法
  ngAfterViewChecked(){
    if (this.isEXE) {
      this.setVaildFunc();

      this.isEXE = false;
    }
  }

  ngOnDestroy() {
    //销毁进来的状态
    // this.commonFunc.removeResubmitStatus();

    var inputLists = document.querySelectorAll('div.error-container');
    for (var i = 0; i < inputLists.length; i++) {
      var nodeObj = <any>inputLists[i];
      nodeObj.remove();
    }

  }


  setVaildFunc(){
    var checkOBj = '';
    if (this.isL == '0') {
      checkOBj = 'leftPriceID';
      // this.commonFunc.checkObjFormat(checkOBj,false);
      this.myEnquiryService.ValidateId(checkOBj, this.myEnquiryService.Validates.checkNull, 'bottom', this.myEnquiryService.tipsMarginLeft, "不能为空");
      this.myEnquiryService.ValidateId(checkOBj, this.myEnquiryService.Validates.checkMoneyFormate, 'bottom', this.myEnquiryService.tipsMarginLeft, "不能为空");
    }
    if (this.isR == '0') {
      checkOBj = 'rightPriceID';
      this.myEnquiryService.ValidateId(checkOBj, this.myEnquiryService.Validates.checkNull, 'bottom', this.myEnquiryService.tipsMarginLeft, "不能为空");
      this.myEnquiryService.ValidateId(checkOBj, this.myEnquiryService.Validates.checkMoneyFormate, 'bottom', this.myEnquiryService.tipsMarginLeft, "不能为空");
    }



  }



  //设置当前不支持状态显示
  setUndisplayUI(divID: string) {
        document.getElementById(divID).innerHTML = '<div class="col-12">不支持</div>';
  }

  //设置标题显示列表内同
  //判断isLeft：true-左边显示  false-右边显示  isGY：0-自动询价公寓 1-自动询价别墅 2-人工
  setTitleListDisplayContentFunc(isLeft: boolean, isGY: string) {
    if (isLeft) {

      if (isGY == '1') {
        this.leftDisplayTitleList = this.gyEnquiryedTitleList;
      } else if (isGY == '2') {
        this.leftDisplayTitleList = this.bsEnquiryedTitleList;

      } else {
        this.leftDisplayTitleList = this.persionEnquiryedTitleList;

      }

    } else {
      if (isGY == '1') {
        this.rightDisplayTitleList = this.gyEnquiryedTitleList;
      } else if (isGY == '2') {
        this.rightDisplayTitleList = this.bsEnquiryedTitleList;

      } else {
        this.rightDisplayTitleList = this.persionEnquiryedTitleList;

      }
    }

  }

  //设置询价内容显示列表
  //判断isLeft：true-左边显示  false-右边显示y  isGY：0-自动询价公寓 1-自动询价别墅 2-人工
  //1.公寓  2.别墅  3.else 人工
  // 公寓- ["小区地址","楼栋号","房号","总楼层","所在楼层","建筑面积"];
  //别墅 -["行政区域","房屋地址","建筑面积"];
  //人工 - ['评估价']

  setValueDisplayFunc(isLeft: boolean, isGY: string, obj: any) {
    if (isLeft) {
      if (isGY == '1') {
        var plotsName = this.commonFunc.handleNilString(obj['plotsName']);
        var unitCode = this.commonFunc.handleNilString(obj['unitName']);
        var houseCode = this.commonFunc.handleNilString(obj['houseName']);
        var totalFloor = this.commonFunc.handleNilString(obj['totalFloor']);
        var currentFloor = this.commonFunc.handleNilString(obj['currentFloor']);
        var area = this.commonFunc.handleNilString(obj['area']);
        var status = this.commonFunc.handleNilString(obj['status']);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }

        }
        this.leftValueList = [plotsName, unitCode, houseCode, totalFloor, currentFloor, area,status];
      } else if (isGY == '2') {
        var areaCode = this.commonFunc.handleNilString(obj['areaName']);
        var address = this.commonFunc.handleNilString(obj['address']);
        var area = this.commonFunc.handleNilString(obj['area']);
        var status = this.commonFunc.handleNilString(obj['status']);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }
        }
        this.leftValueList = [areaCode, address, area,status];
      } else {
        this.leftValueList = [''];
      }
    } else {
      if (isGY == '1') {
        var plotsName = this.commonFunc.handleNilString(obj['plotsName']);
        var unitCode = this.commonFunc.handleNilString(obj['unitName']);
        var houseCode = this.commonFunc.handleNilString(obj['houseName']);
        var totalFloor = this.commonFunc.handleNilString(obj['totalFloor']);
        var currentFloor = this.commonFunc.handleNilString(obj['currentFloor']);
        var area = this.commonFunc.handleNilString(obj['area']);
        var status = this.commonFunc.handleNilString(obj['status']);

        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }
        }
        this.rightValueList = [plotsName, unitCode, houseCode, totalFloor, currentFloor, area,status];
      } else if (isGY == '2') {
        var areaCode = this.commonFunc.handleNilString(obj['areaName']);
        var address = this.commonFunc.handleNilString(obj['address']);
        var area = this.commonFunc.handleNilString(obj['area']);
        this.rightValueList = [areaCode, address, area];
      } else {
        this.rightValueList = [''];
      }
    }
  }


  //设置当前页面显示几个估价公司
  //待评估 评估失败 ：点击     0-显示内容 1-显示不支持字眼 2-完全不显示
  setCompangDisplayFunc(objList: any, modelType: string) {
    // var modelType = '';
    if (objList.length == 0) {
      this.isL = '2';
      this.isR = '2';
    }
    if (objList.length == 1) {

      this.lValue = objList[0].companyName;
      this.lHouseNo = objList[0].companyCode;

      this.isL = '0';
      this.isR = '2';

      /* add by fenglz --start */
      if (this.lHouseNo == '001') {
        //世联
         this.lValue="世联评估";

        // if (modelType == '1') {
        //   this.isL = '0';
        //   this.isR = '2';
        // } else if (modelType == '2') {
        //   this.isL = '0';
        //   this.isR = '1';
        // } else if (modelType == '3') {
        //   this.isL = '2';
        //   this.isR = '2';
        // }
      } else if (this.lHouseNo == '002') {
        //城市
        // if (modelType == '1') {
        //   this.isL = '0';
        //   this.isR = '2';
        // } else if (modelType == '2') {
        //   this.isL = '1';
        //   this.isR = '2';
        // } else if (modelType == '3') {
        //   this.isL = '2';
        //   this.isR = '2';
        // }
        this.lValue="城市评估";


      }

      /* add by fenglz --end */
    }
    if (objList.length == 2) {
      this.isL = '0';
      this.isR = '0';

      this.lValue = objList[0].companyName;
      this.rValue = objList[1].companyName;

      this.lHouseNo = objList[0].companyCode;
      this.rHouseNo = objList[1].companyCode;

      /* add by fenglz --start */

       if(this.lHouseNo=='001'){
        this.lValue='世联评估'
      }
      if(this.lHouseNo=='002'){
        this.lValue='城市评估'
      }
      this.rHouseNo = objList[1].companyCode;
      if (this.rHouseNo=='001'){
        this.rValue='世联评估';
      }
      if(this.rHouseNo=='002'){
        this.rValue='城市评估';
      }

      // if (this.lHouseNo == '001') {
      //   //世联
      //   if (modelType == '1') {
      //     this.isL = '0';
      //     this.isR = '0';
      //   } else if (modelType == '2') {
      //     this.isL = '0';
      //     this.isR = '1';
      //   } else if (modelType == '3') {
      //     this.isL = '2';
      //     this.isR = '2';
      //   }
      // } else if (this.lHouseNo == '002') {
      //   //城市
      //   if (modelType == '1') {
      //     this.isL = '0';
      //     this.isR = '0';
      //   } else if (modelType == '2') {
      //     this.isL = '1';
      //     this.isR = '0';
      //   } else if (modelType == '3') {
      //     this.isL = '2';
      //     this.isR = '2';
      //   }

      // }
      /* add by fenglz --end */

    }

  }

  popNextErrorModal(tipTitle, tipContent) {
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
      return true;
    }
  }
  //删除成功上传的文件
  deleteUploadFile() {
    this.popNextErrorModal("删除上载材料", "将删除已上载的材料，是否继续？");
    var sure: any = document.getElementById("sure");
    sure.onclick = () => {
      this.isShow = false;
      this.isShow2 = false;
      this.fileSerialNo=''
    };


  }

  deleteUploadFile2() {
    this.popNextErrorModal("删除上载材料", "将删除已上载的材料，是否继续？");
    var sure: any = document.getElementById("sure");
    sure.onclick = () => {
      this.isShow3 = false;
      this.isShow4 = false;
      this.fileSerialNo2 = '';
    };


  }


  //当文件上传失败进行重新上传时
  //当文件上传失败进行重新上传时
  upAgiain() {

    this.uploader.queue[0].upload();
    this.isUp = true;
    //
    // this.fileName = this.uploader.queue[0].file.name;
    // var reg = /.*(.bmp|.jpg|.jpeg|.gif|.png)$/;
    // var q_flag = reg.test(this.fileName);
    // if (q_flag) {
    //   this.uploader.queue[0].upload();
    //   this.isUp = true;
    // } else {
    //   alert("文件格式不正确，请重新上传");
    //   // this.isUp = false;
    // }

  }

  upAgiain2() {

    this.uploader2.queue[0].upload();
    this.isUp2 = true;


    // this.fileName = this.uploader2.queue[0].file.name;
    // var reg = /.*(.bmp|.jpg|.jpeg|.gif|.png)$/;
    // var q_flag = reg.test(this.fileName);
    // if (q_flag) {
    //   this.uploader2.queue[0].upload();
    //   this.isUp = true;
    // } else {
    //   alert("文件格式不正确，请重新上传");
    //   this.isUp = false;
    // }

  }

  deleteFile() {
    this.uploader.queue[0].remove();
    //补充材料的提交按钮是否显示issmallPhoto调用
    this.isShow = false;
    this.isShow2 = false;
  }

  deleteFile2() {
    this.uploader2.queue[0].remove();
    console.log(123);
    //补充材料的提交按钮是否显示issmallPhoto调用
    this.isShow3 = false;
    this.isShow4 = false;
  }

  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });
  public uploader2: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });
  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    // 打印文件选择名称
    //正则表达式验证文件是否是压缩文件
    var reg = /.*(.bmp|.jpg|.jpeg|.gif|.png)$/;
    //用来标志验证是否是压缩文件
    var q_flag = reg.test(event.target.value);


    //let header = new Headers({'Content-Type': 'application/json',"filePath":'test'});
    this.uploader.queue[0].withCredentials = false;//验证通配符地址 防止跨域事件发生
    //this.uploader.queue[0].headers = header;
    if (q_flag) {
      //让进度条显示
      this.isShow = true;
      this.uploadFile();
    } else {
      // alert("文件格式不正确");
      // this.isUp = false;
      this.networkService.popNextSuccessModal("文件上载失败", "文件上传格式不正确，请重新上传", false);

      this.uploader.queue[0].remove();
    }

  }

  //定义事件，上传文件
  uploadFile() {
    //开始上传
    this.uploader.queue[0].upload();
    this.isUp = true;
    console.log(this.uploader.queue[0]);
    this.fileName = this.uploader.queue[0].file.name;
    //上传完成
    this.uploader.onCompleteItem = function (response, status, headers) {
    };

    // 上传成功
    this.uploader.queue[0].onSuccess = (response, status, headers) => {
      // 上传文件成功
      console.log("here is function of success");
      if (status == 200) {

        // 上传文件后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        this.progress=100;

        this.fileName = tempRes.body.fileMaps[0].fileName;
        this.fileSerialNo = tempRes.body.fileMaps[0].serialNo;
        this.uploadTime = tempRes.body.fileMaps[0].uploadTime;
        this.isFile = true;
        let reader = new FileReader();
        reader.readAsDataURL(this.uploader.queue[0].some);
        reader.onload = function (e) {
          $("#shiLianImg").attr("src",this.result);
        };
        // this.imgSrc1 = environment.server + tempRes.body.fileMaps[0].url;
        that.isShow = !that.isShow;
        that.isShow2 = !that.isShow2;
        this.uploader.queue[0].remove();

      } else {
        // 上传文件后获取服务器返回的数据错误
        alert("server return error ");
      }
    };

    //上传失败
    this.uploader.queue[0].onError = (response, status, headers) => {
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        that.isFile = true;
      } else {
        // 上传文件后获取服务器返回的数据错误
        this.isUp = false;
        // alert("fail up file");
        this.networkService.popNextSuccessModal("文件上载失败", "文件上传格式不正确，请重新上传", false);
        // this.networkService.popNextFailModal("材料上载失败","材料上载失败，请重新上传",false);
      }

    }
    // 上传进度
    var that = this;
    this.uploader.onProgressItem = (fileItem, progress) => {
      console.log('upload progresss ', progress);
      if(progress==100){
        progress=99;
      }
      var self = this;
      self.progressLength = progress;
      self.ref.markForCheck();
      self.ref.detectChanges();
    }


    //上传进度2
  }

  //定义事件，选择文件
  selectedFileOnChanged2(event: any) {
    // 打印文件选择名称
    //正则表达式验证文件是否是压缩文件
    var reg = /.*(.bmp|.jpg|.jpeg|.gif|.png)$/;
    //用来标志验证是否是压缩文件
    var q_flag = reg.test(event.target.value);

    //let header = new Headers({'Content-Type': 'application/json',"filePath":'test'});
    this.uploader2.queue[0].withCredentials = false;//验证通配符地址 防止跨域事件发生
    //this.uploader.queue[0].headers = header;
    if (q_flag) {
      //让进度条显示
      this.isShow3 = true;
      this.uploadFile2();
    } else {
      this.networkService.popNextSuccessModal("文件上载失败","文件上传格式不正确，请重新上传",false);
      this.uploader2.queue[0].remove();
    }
  }

  //定义事件，上传文件
  uploadFile2() {
    //开始上传
    this.uploader2.queue[0].upload();
    this.isUp2 = true;
    console.log(this.uploader2.queue[0]);
    this.fileName2 = this.uploader2.queue[0].file.name;
    //上传完成
    this.uploader2.onCompleteItem = function (response, status, headers) {
    };

    // 上传成功
    this.uploader2.queue[0].onSuccess = (response, status, headers) => {
      // 上传文件成功
      console.log("here is function of success");
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        this.progress=100;
        this.fileName2 = tempRes.body.fileMaps[0].fileName;
        this.fileSerialNo2 = tempRes.body.fileMaps[0].serialNo;
        this.uploadTime2 = tempRes.body.fileMaps[0].uploadTime;
        this.isFile2 = true;
        let reader = new FileReader();
        reader.readAsDataURL(this.uploader2.queue[0].some);
        reader.onload = function (e) {
          $("#chengShiImg").attr("src",this.result);
        };
        // this.imgSrc2 = environment.server + tempRes.body.fileMaps[0].url;
        console.log(this.fileName, this.fileSerialNo2, this.isFile);
        that.isShow3 = !that.isShow3;
        that.isShow4 = !that.isShow4;
        this.isUp2 = false;
        this.uploader2.queue[0].remove();
      } else {
        // 上传文件后获取服务器返回的数据错误
        alert("server return error ");
      }
    };

    //上传失败
    this.uploader2.queue[0].onError = (response, status, headers) => {
      if (status == 200) {
        // 上传文件后获取服务器返回的数据
        let tempRes = JSON.parse(response);
        that.isFile2 = true;
      } else {
        // 上传文件后获取服务器返回的数据错误
        this.isUp2 = false;
        // alert("fail up file");
        this.networkService.popNextFailModal("文件上传失败", "文件上传失败，请重新上传", false);
      }
    }
    // 上传进度
    var that = this;
    this.uploader2.onProgressItem = (fileItem, progress) => {
      console.log('upload progresss ', progress);
      if(progress==100){
        progress=99;
      }
      var self = this;
      self.progressLength2 = progress;
      self.ref.markForCheck();
      self.ref.detectChanges();
    }


    //上传进度2
  }

 //判断一当前某家询价数据是否正确  根据当前公司询价状态调用对应方法
 vaildCompanySubmitDataCallBack(){
   //判断当前几家
   //根据当前询价信息 调用对应检验方法
   var leftResult:string = '';
   var rightResult:string = '';
   var result:boolean = false;

   for (var i = 0; i < this.isGotoPerson.length; i++) {
     var obj = this.isGotoPerson[i];
     var objResult:string = '';
     if(obj == "待评估"){
       objResult = this.vaildReadyEnquiryDataCallBack(i);
     }else if(obj == "评估失败"){
       objResult = this.vaildFailEnquiryDataCallBack(i);
     }else {
       objResult = '0';
     }
     if(i == 0){
       leftResult = objResult;
     }else{
       rightResult = objResult;
     }


   }

   if(this.isGotoPerson.length == 1){
     result = leftResult == '0';
   }else{
     //如果长度是2的话，有可能左边已经评估好了   有可能右边评估好了
     result = (leftResult == '0' ) && (rightResult == '0');
   }

   if(!result){
     if(leftResult != '0' && rightResult != '0' ){
       this.onSubmitDataErrorNotiCallBack('3');

     }else if(leftResult != '0'){
       this.onSubmitDataErrorNotiCallBack('1');
     }else{
       this.onSubmitDataErrorNotiCallBack('2');
     }


   }

   return result;


 }

 //询价失败数据校验方法 0-左边 1-右边
 //返回 0-正常 1-错误
 vaildFailEnquiryDataCallBack(flag:number){
  var result:string = '';
  var originDataObj:any = {};
  var priceStr:string = '';
  var companyCode:string = '';
  var priceDivID:string = '';
  // 获取价格数据 以及在此过程中判断 文件是否正常上传
  if (flag == 0) {
     // code...
     priceStr = $('#leftPriceID').val();
     originDataObj = this.leftValueList;
     companyCode = this.lHouseNo;
     priceDivID = "leftPriceID";
   }else{
     priceStr = $('#rightPriceID').val();
     originDataObj = this.rightValueList;
     companyCode = this.rHouseNo;
     priceDivID = "rightPriceID";
   }

   priceStr = this.commonFunc.handleNilString(priceStr);
   priceStr = this.commonFunc.handlePagePrice(priceStr);

   //判断价格输入是否正确
   var priceVaildResult = this.validInputPriceFunc(priceDivID);

   //判断当前过程是否正确
   if(priceVaildResult != '0' ){
      result = '1';
      return result;
   }

   result = '0';

   //判断当前是公寓还是别墅
   var postObj:any = {};
   if(this.houseName == '公寓'){
     postObj = {
                'price':priceStr,
                'companyCode':companyCode,
                'plotsName':originDataObj[0],
                'unitCode':originDataObj[1],
                'houseCode':originDataObj[2],
                'totalFloor':originDataObj[3],
                'currentFloor':originDataObj[4],
                'area':originDataObj[5]
              };

   }else{
     postObj = {
                'price':priceStr,
                'companyCode':companyCode,
                'areaCode':originDataObj[0],
                'address':originDataObj[1],
                'area':originDataObj[2],
               };
   }
   this.companyParamList.push(postObj);

   return result;

 }


 //待询价数据校验方法 0-左边 1-右边
  //返回 0-正常 1-错误

 vaildReadyEnquiryDataCallBack(flag:number){
  var result:string = '0';
  var originDataObj:any = {};
  var priceStr:string = '';
  var fileUploadResult:boolean = false;
  var fileSerialNo:string = '';
  var fileName:string = "";
  var companyCode:string = '';
  var priceDivID:string = '';

  // 获取价格数据 以及在此过程中判断 文件是否正常上传
  if (flag == 0) {
     // code...
     priceStr = $('#leftPriceID').val();
     fileUploadResult = this.fileSerialNo != '';
     originDataObj = this.leftValueList;
     fileName = this.fileName;
     fileSerialNo = this.fileSerialNo;
     companyCode = this.lHouseNo;
     priceDivID = 'leftPriceID';
   }else{
     priceStr = $('#rightPriceID').val();
     fileUploadResult = this.fileSerialNo2 != '';
     originDataObj = this.rightValueList;
     fileName = this.fileName2;
     fileSerialNo = this.fileSerialNo2;
     companyCode = this.rHouseNo;
     priceDivID = 'rightPriceID';
   }
   priceStr = this.commonFunc.handleNilString(priceStr);
   priceStr = this.commonFunc.handlePagePrice(priceStr);

   //判断价格输入是否正确
   var priceVaildResult = this.validInputPriceFunc(priceDivID);

   //判断当前过程是否正确
   if(priceVaildResult != '0' || !fileUploadResult){
      result = '1';
      return result;
   }
   result = '0';
   //判断当前是公寓还是别墅
   var postObj:any = {};
   if(this.houseName == '公寓'){
     postObj = {
                'fileSerialNo':fileSerialNo,
                'fileName':fileName,
                'price':priceStr,
                'companyCode':companyCode,
                'plotsName':originDataObj[0],
                'unitCode':originDataObj[1],
                'houseCode':originDataObj[2],
                'totalFloor':originDataObj[3],
                'currentFloor':originDataObj[4],
                'area':originDataObj[5]
              };

   }else{
     postObj = {
                'fileSerialNo':fileSerialNo,
                'fileName':fileName,
                'price':priceStr,
                'companyCode':companyCode,
                'areaCode':originDataObj[0],
                'address':originDataObj[1],
                'area':originDataObj[2],
               };
   }
   this.companyParamList.push(postObj);

   return result;

 }




  //判断当前价格输入结果  返回结果  result：0-格式正确  1-结果为空 2-结果格式错误
 validInputPriceFunc(objID:string){
    var result = '0';
    var objPrice = $("#"+objID ).val();
    objPrice = this.commonFunc.handleNilString(objPrice);

    //1-结果为空
    if(objPrice == '') result = '1';


    var needCheckObj = {
          "id": objID,
          "myErrorPosition": "bottom",
          "myMargainLeft": 3,
          "errorText": "不能为空"
        };
    var errorResult =  this.commonFunc.checkObjFormat(needCheckObj,false);
    if(!errorResult){
      result = '2';
    }

    return result;
  }


//设置当询价提交时  数据判断错误时提示信息 flag当前那个公司信息报错 1-左边报错 2-右边报错
  onSubmitDataErrorNotiCallBack(flag:string){
    if(flag == '1' ){
      this.networkService.popNextSuccessModal("房产信息错误",this.lValue+"评估房产信息错误，请修改",false);
    }else if (flag == '2'){
      this.networkService.popNextSuccessModal("房产信息错误",this.rValue+"评估房产信息错误，请修改",false);
    }else {
      this.networkService.popNextSuccessModal("房产信息错误",this.lValue+this.rValue+"评估房产信息错误，请修改",false);

    }
  }


    //提交上传数据
  submitPersonInputPriceDataInfoFunc(){
    var validResult = this.vaildCompanySubmitDataCallBack();
    if(!validResult) return;

    var loginTokenKey=this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var userNo=userObj.userNo;
    var userName = userObj.userName;

    var enquiryObj = this.commonFunc.getBusinessData();
    var serialNo = enquiryObj.serialNo;

    var Obj = {
            'head': {
              'functionNo': 'HH000123',
              'userNo': userNo,
              'clientTimestamp': loginTokenKey
            },
            'body': {
              "sourceSystem":"nlbs",
              "companyId":"",
              "companyName":"",
              "departmentId":"",
              "departmentName":"",
              "userName":userName,
              "userId":userNo,
              "serialNo":serialNo,
              "companyParamList":this.companyParamList
            }
          };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        if (this.networkService.onJudgeSuccessful(res)) {
          this.pushDetailViewFunc(res.data.body);
        }
      }
  });
  }

  pushDetailViewFunc(itemObj:any) {

    this.commonFunc.setBusinessData(itemObj);
    this.commonFunc.setBusinsessSaveFlagFunc();
    this.router.navigate(['/home/enquiryDetail']);

  }
  // 返回重新估价页面
  onBackEnquiryBtnClick(){
    if(this.resubmitFlag=='enquirySearch'){
      this.router.navigate(['/home/enquirySearch']);
    }else if(this.resubmitFlag=='gtasks'){
      this.router.navigate(['/home/gtasks']);
    }else if(this.resubmitFlag=='message'){
      this.router.navigate(['/home/message'])
    }
  }

}
