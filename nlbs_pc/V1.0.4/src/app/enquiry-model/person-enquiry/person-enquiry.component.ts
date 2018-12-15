import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FileUploader} from "ng2-file-upload";
import {NetworkService} from "./../../network.service";
import {commonService} from "./../../commonService.service";
import {User} from "./../../User.service";
import {personEnquiryService} from "./person-enquiryService.service";
import {enquiryService} from "./../enquiry/enquiryService.service";
import {Router} from "@angular/router";


declare var $: any;
@Component({
  selector: 'app-person-enquiry',
  templateUrl: './person-enquiry.component.html',
  styleUrls: ['./person-enquiry.component.css'],
  providers: [enquiryService]
})
export class PersonEnquiryComponent implements OnInit {
  itemStatusLists: any = ['房产估价', '进件申请', '风控审批', '合作制作', '协议公证', '抵押登记', '授信完成'];
  itemSelectStatus: string = '风控审批';
  itemSelectIndex: number = 0;
  cityValue: string;

  cityList: any = [];
  serialNo: string = '';
  // 文件的fileSerialNo
  fileSerialNo: string = '';
  fileSerialNo2: string = '';
  unitList: any = [];

  //设置进度条的长度
  progressLength: number;
  progressLength2: number;


  //测试的值
  progress: number = 2;

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
  fileName: string = '';
  fileName2: string = '';
  //左边显示
  isL: boolean;
  lValue: string;
  //右边显示R
  isR: boolean;
  rValue: string;
  //城市是否为空的标志位，如果城市是空的话  则区域5不显示
  isCityEmpty: boolean = true;
  //左边的companyCode
  lHouseNo: string;
  //右边的companyCode
  rHouseNo: string;
  //文件上传成功的时间
  uploadTime: string;
  uploadTime2: string;
  //图片的路径
  imgSrc1:string;
  imgSrc2:string;

  constructor(private networkService: NetworkService,
              private commonFunc: commonService,
              private enquiry: enquiryService,
              public ref: ChangeDetectorRef,
              private user: User,
              private router: Router) {
    this.progressLength = 0;
    this.ref = ref;
  }

  userDataKe = this.user.getUserData();
  timeStamp = this.commonFunc.getloginToken();

  setSelectIndex() {
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if (this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }
    }
  }

  ngOnInit() {
    $(".selectmsg").select2({});
    this.initData();
    this.initVaildConditionFunc();
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

  //删除已经上传的文件
  deleteUploadFile() {
    this.popNextErrorModal("删除上载材料", "将删除已上载的材料，是否继续？");
    var sure: any = document.getElementById("sure");
    sure.onclick = () => {
      this.isShow = false;
      this.isShow2 = false;
      this.fileSerialNo ='';
      this.isFile = false;
      this.redXShow = true;
    };


  }

  deleteUploadFile2() {
    this.popNextErrorModal("删除上载材料", "将删除已上载的材料，是否继续？");
    var sure: any = document.getElementById("sure");
    sure.onclick = () => {
      this.isShow3 = false;
      this.isShow4 = false;
      this.fileSerialNo2 = '';
      this.isFile2 = false;
      this.redXShow2 = true;
    };


  }


  initData() {
    var cityCode2 = this.user.getUserData().cityCode;

    var that = this;
    //初始化发送请求
    var Obj = {
      'head': {
        'functionNo': 'HH000101',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "cityCode": cityCode2
      }
    }
    this.networkService.postData2(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;

          this.cityList = userDataObj.cityList;
          this.serialNo = userDataObj.serialNo;

          if (this.cityList.length == 1) {
            var cityCode = this.commonFunc.handleNilString(this.cityList[0].cityCode);
            this.cityValue = cityCode;
            //如果直接在dom
            $('#city').attr("disabled", true);
            this.isCityEmpty = false;

            var cityValue = this.cityValue;
            //当城市的值发生改变  且不为空
            if (cityValue) {
              var Obj = {
                'head': {
                  'functionNo': 'HH000102',
                  'userNo': that.userDataKe.userNo,
                  'clientTimestamp': that.timeStamp
                },
                'body': {
                  "cityCode": cityValue,
                  "serialNo": that.serialNo
                }
              };
              that.networkService.postData2(Obj, false).then(res => {
                if (res.retCode == true) {
                  // 请求网络通信处理方法
                  //res 是后台返回来的数据
                  //返回0000
                  if (that.networkService.onJudgeSuccessful(res)) {
                    //登录成功处理逻辑
                    var userDataObj: any = res.data.body;
                    var headDataObj: any = res.data.head;
                    that.unitList = userDataObj.companyList;
                    if (that.unitList) {
                      if (that.unitList.length == 0) {
                        that.isL = false;
                        that.isR = false;
                      }
                      if (that.unitList.length == 1) {
                        that.isL = true;
                        that.isR = false;
                        that.lValue = that.unitList[0].companyName;
                        that.lHouseNo = that.unitList[0].companyCode;
                      }
                      if (that.unitList.length == 2) {
                        that.isL = true;
                        that.isR = true;
                        that.lValue = that.unitList[0].companyName;
                        that.rValue = that.unitList[1].companyName;

                        that.lHouseNo = that.unitList[0].companyCode;
                        that.rHouseNo = that.unitList[1].companyCode;

                      }
                    }
                  }

                }
              });
            }
          }

        }

      }
    });
    $("#city").change(function () {
      that.isCityEmpty = that.enquiry.isEmpty("city");
      var cityValue = $("#city").val();
      //当城市的值发生改变  且不为空
      if (cityValue) {
        var Obj = {
          'head': {
            'functionNo': 'HH000102',
            'userNo': that.userDataKe.userNo,
            'clientTimestamp': that.timeStamp
          },
          'body': {
            "cityCode": cityValue,
            "serialNo": that.serialNo
          }
        };
        that.networkService.postData2(Obj, false).then(res => {
          if (res.retCode == true) {
            // 请求网络通信处理方法
            //res 是后台返回来的数据
            //返回0000
            if (that.networkService.onJudgeSuccessful(res)) {
              //登录成功处理逻辑
              var userDataObj: any = res.data.body;
              var headDataObj: any = res.data.head;
              that.unitList = userDataObj.companyList;
              if (that.unitList) {
                if (that.unitList.length == 0) {
                  that.isL = false;
                  that.isR = false;
                }
                if (that.unitList.length == 1) {
                  that.isL = true;
                  that.isR = false;
                  that.lValue = that.unitList[0].companyName;
                  that.lHouseNo = that.unitList[0].companyCode;
                }
                if (that.unitList.length == 2) {
                  that.isL = true;
                  that.isR = true;
                  that.lValue = that.unitList[0].companyName;
                  that.rValue = that.unitList[1].companyName;

                  that.lHouseNo = that.unitList[0].companyCode;
                  that.rHouseNo = that.unitList[1].companyCode;

                  console.log(that.lHouseNo, that.rHouseNo);
                }
              }
            }

          }
        });
      }
    });

  }

  //添加校验方法
  initVaildConditionFunc(){

    this.enquiry.ValidateId("address", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateId("price1", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateId("price1", this.enquiry.Validates.checkMoneyFormate, 'bottom', this.enquiry.tipsMarginLeft, "格式错误");
    this.enquiry.ValidateId("price2", this.enquiry.Validates.checkNull, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
    this.enquiry.ValidateId("price2", this.enquiry.Validates.checkMoneyFormate, 'bottom', this.enquiry.tipsMarginLeft, "格式错误");

  }


//校验提交数据合法
//判断当前是否弹窗 0-校验通过 1-只有确定按钮弹窗  2-含有确定 取消 弹框
  popViewFlag:string = '0';
  popViewContentStr:string  = '';
  validInputDataFormatFunc(){

  //unitList的长度如果是2的话，那么左右两边均要校验
    if (this.unitList.length == 2) {
      //校验价格信息
      var vaildPrice1:string = this.vaildCompanyInputData('price1',this.fileSerialNo);
      var vaildPrice2:string = this.vaildCompanyInputData('price2',this.fileSerialNo2);

      //如果价格存在空
      if (vaildPrice1 == "1" || vaildPrice2 == "1") {
        // code...
        if (vaildPrice1 == '1' && vaildPrice2 == "1") {
           this.popViewFlag = '1';
         this.popViewContentStr = this.lValue+this.rValue+'评估房产信息错误，请修改';

        }else if(vaildPrice1 == '1'){
           this.popViewFlag = '1';
         this.popViewContentStr = this.lValue+'评估房产信息错误，请修改';

        }else if(vaildPrice2 == "1"){
         this.popViewFlag = '1';
         this.popViewContentStr = this.rValue+'评估房产信息错误，请修改';

        }
      }

        //如果价格存在有错误的情况
      if (vaildPrice1 == "2" || vaildPrice2 == "2") {
        // code...
        if (vaildPrice1 == '2' && vaildPrice2 == "2") {
         this.popViewFlag = '1';
         this.popViewContentStr = this.lValue+this.rValue+'评估房产信息错误，请修改';

        }else if(vaildPrice1 == '2'){
         this.popViewFlag = '1';
         this.popViewContentStr = this.lValue+'评估房产信息错误，请修改';

        }else if(vaildPrice2 == "2"){
          this.popViewFlag = '1';
         this.popViewContentStr = this.rValue+'评估房产信息错误，请修改';

        }


      }


     //当两家都通过
     if((vaildPrice1 == '0')&&(vaildPrice2=='0')){
       this.popViewFlag = '0';
       this.popViewContentStr = '';
     }

    }
    //如果unitList的长度是1的话，那么只需要校验左边
    if (this.unitList.length == 1) {
     var vaildPrice1:string = this.vaildCompanyInputData('price1',this.fileSerialNo);
     if(vaildPrice1 == '0'){
       this.popViewFlag = '0';
       this.popViewContentStr = '';
     }else if(vaildPrice1 == '1'){

        this.popViewFlag = '1';
       this.popViewContentStr = this.lValue+'评估房产信息错误，请修改';


     }else if(vaildPrice1 == '2'){

       this.popViewFlag = '1';
       this.popViewContentStr = this.lValue+'评估房产信息错误，请修改';

     }
    }


    //校验地址信息
    var addressStr = $('#address').val();

    addressStr = this.commonFunc.handleNilString(addressStr);
    if(addressStr == ''){
      this.popViewFlag = '1';
      this.popViewContentStr = '评估房产信息错误，请修改';
    }

    if(this.popViewFlag == '0'){
      return false;
    }else{
      return true;
    }


  }
  // retsult 0-校验通过 1-空 2-格式错误
  vaildCompanyInputData(priceObj:string,fileSerialNoObj:string){
     var validValuePrice = this.enquiry.Validates.checkNull(priceObj, 'bottom', this.enquiry.tipsMarginLeft, "不能为空");
     var needCheckObj = {
        "id":priceObj,
        "myErrorPosition": "bottom",
        "myMargainLeft": this.enquiry.tipsMarginLeft,
        "errorText": "不能为空"
      };
     var validFormatPrice = this.commonFunc.checkObjFormat(needCheckObj, false);

     var result = '0';
     if(!validValuePrice || fileSerialNoObj == ''){
       result = '1';
     }

     if(!validFormatPrice){
       result = '2';

     }

     return result;


  }
  //1-只有确定按钮弹窗  2-含有确定 取消 弹框
  popViewAfterVaildData(){
    if(this.popViewFlag == '1'){
      this.networkService.popNextSuccessModal("评估信息错误",this.popViewContentStr,false);

    }else if (this.popViewFlag == '2'){

    }


  }


//提交人工查询数据 方法
  redXShow:boolean=true;
  redXShow2:boolean=true;
  submit() {

    this.redXShow=this.isFile;
    this.redXShow2=this.isFile2;

    $("#submit").attr("disabled",true);

    this.commonFunc.setEnquiryPath("person");

    var result =  this.validInputDataFormatFunc();
    if(result){
      $("#submit").attr("disabled",false);
      this.popViewAfterVaildData();
      return;
    }



      var companyParamList;
    //评估价格校验
    var price1 = $("#price1").val();
    var price2 = $('#price2').val();

    price1 = this.commonFunc.handlePagePrice(price1);
    price2 = this.commonFunc.handlePagePrice(price2);





    // 判断unitList的值的长度
    if (this.unitList.length == 2) {
      companyParamList = [
        //左边的值
        {
          "companyCode": this.lHouseNo,
          "fileSerialNo": this.fileSerialNo,
          "fileName":this.fileName,
          "price": price1
        },
        //右边的值
        {
          "companyCode": this.rHouseNo,
          "fileSerialNo": this.fileSerialNo2,
           "fileName":this.fileName2,

          "price": price2
        }
      ];
    }
    if (this.unitList.length == 1) {
      companyParamList = [
        //左边的值
        {
          "companyCode": this.lHouseNo,
          "fileSerialNo": this.fileSerialNo,
          "fileName":this.fileName,
          "price": price1
        }
      ];
    }

    var cityCode = $("#city").val();
    var address = $("#address").val();
    var Obj = {
      'head': {
        'functionNo': 'HH000107',
        'userNo': this.userDataKe.userNo,
        'clientTimestamp': this.timeStamp
      },
      'body': {
        "serialNo": this.serialNo,
        "cityCode": cityCode,
        "address": address,
        "companyId": this.userDataKe.distributorCode,
        "companyName": this.userDataKe.distributorName,
        "userName": this.userDataKe.userName,
        "userId": this.userDataKe.userNo,
        "companyParamList": companyParamList
      }
    };
    this.networkService.postData2(Obj, false).then(res => {
      $("#submit").attr("disabled",false);
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //返回0000
        if (this.networkService.onJudgeSuccessful(res)) {
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          //人工询价设置状态位为2
          this.commonFunc.setEnquiryStatus("02");
          this.pushDetailViewAfterSuccess(userDataObj);


        }

      }
    });

  }
  //提交成功之后 跳转到详情页面
  pushDetailViewAfterSuccess(obj:any){
    this.commonFunc.setBusinessData(obj);
    this.commonFunc.setBusinsessSaveFlagFunc();
    this.router.navigate(['/home/enquiryDetail']);

  }



//  文件上传
  /*
   *文件上传功能  调用示例
   *<input type="file" ng2FileSelect [uploader]="uploader" (change)="selectedFileOnChanged($event)" />
   *
   */
  //初始化定义uploader变量,用来配置input中的uploader属性

  //当文件上传失败进行重新上传时
  upAgiain() {

    this.uploader.queue[0].upload();
    this.isUp = true;

  }

  upAgiain2() {

    this.uploader2.queue[0].upload();
    this.isUp2 = true;

  }

  deleteFile() {
    this.uploader.queue[0].remove();
    //补充材料的提交按钮是否显示issmallPhoto调用
    this.isShow = false;
    this.isShow2 = false;
    this.redXShow = true;
  }

  deleteFile2() {
    this.uploader2.queue[0].remove();
    //补充材料的提交按钮是否显示issmallPhoto调用
    this.isShow3 = false;
    this.isShow4 = false;
    this.redXShow2 = true;
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

        this.progress=100;
        let tempRes = JSON.parse(response);
        this.fileName = tempRes.body.fileMaps[0].fileName;
        this.fileSerialNo = tempRes.body.fileMaps[0].serialNo;
        this.uploadTime = tempRes.body.fileMaps[0].uploadTime;
        this.isFile = true;
        // add by 谢之磊  改成显示本地文件   begin
        let reader = new FileReader();
        reader.readAsDataURL(this.uploader.queue[0].some);
        reader.onload = function (e) {
          $("#shiLianImg").attr("src",this.result);
        };
        // add by 谢之磊  改成显示本地文件   end
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
        // this.networkService.popNextSuccessModal("文件上载失败", "文件上传格式不正确，请重新上传", false);
        this.networkService.popNextSuccessModal("材料上载失败","材料上载失败，请重新上传",false);
      }

    }
    // 上传进度
    var that = this;
    this.uploader.onProgressItem = (fileItem, progress) => {
      console.log('upload progresss ', progress);
      var self = this;
      //这里的文件进度条如果是100，则改为99  在文件上传成功的时候，把progress变为100
      if(progress=='100'){
        progress=99;
      }
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
        this.progress=100;
        let tempRes = JSON.parse(response);
        this.fileName2 = tempRes.body.fileMaps[0].fileName;
        this.fileSerialNo2 = tempRes.body.fileMaps[0].serialNo;
        this.uploadTime2 = tempRes.body.fileMaps[0].uploadTime;
        this.isFile2 = true;
        // this.imgSrc2 = environment.server + tempRes.body.fileMaps[0].url;

        // add by 谢之磊  改成显示本地文件   begin
        let reader = new FileReader();
        reader.readAsDataURL(this.uploader2.queue[0].some);
        reader.onload = function (e) {
          $("#chengShiImg").attr("src",this.result);
        };
        // add by 谢之磊  改成显示本地文件   end

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
    };
    // 上传进度
    var that = this;
    this.uploader2.onProgressItem = (fileItem, progress) => {
      console.log('upload progresss ', progress);
      if(progress=='100'){
        progress=99;
      }
      var self = this;
      self.progressLength2 = progress;
      self.ref.markForCheck();
      self.ref.detectChanges();
    }
    //上传进度2
  }

  ngOnDestroy() {
    var inputLists = document.querySelectorAll('div.error-container');
    for (var i = 0; i < inputLists.length; i++) {
      var nodeObj = <any>inputLists[i];
      nodeObj.remove();
    }

  }
}
