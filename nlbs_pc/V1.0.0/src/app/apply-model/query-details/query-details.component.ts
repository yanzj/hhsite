import { Component, OnInit,DoCheck } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Data, Router} from "@angular/router";
import {FileUploader} from "ng2-file-upload";
import {ChangeDetectorRef} from "@angular/core";
import {environment} from "../../../environments/environment";
declare var $:any;
@Component({
  selector: 'app-query-details',
  templateUrl: './query-details.component.html',
  styleUrls: ['./query-details.component.css']
})
export class QueryDetailsComponent implements OnInit {

  //申请信息
  applayInfoValueLists:any = [];// 进件基础信息值列表
  applayMainHouseholdLists : any = [];//申请信息 案件概况值列表

  //录单信息
  selectItemPopViewObj:any = {};//当前点击显示的详情信息
  applyInputPersionInfoLists:any = [];//人员物信息
  mortgageList:any = [];//抵押物列表
  mortgageListInvestObj :any = {};//抵押物产调信息
  residenceInfList:any = [];//抵押物户口信息
  spareHouseList:any = [];//备用房列表信息
  creditInfList:any = [];//征信信息
  judicialInfList:any = [];//司法信息
  loanInfoList:any =[];//征信信息 贷款信息
  loanCardInfoList:any = [];//征信信息 贷记卡信息

  //补充材料
  materialFileList;
  materialTypeListData;//补充材料项目列表（内容 含有文件数）
  selectedMaterial;//补充材料当前选中的对象
  selectedMaterialDisplayImages; //补充材料右边显示图片的列表
  submitFileOBJ:any = {'suc':[],'fail':[]};//补充材料中选中提交文件内容  suc-上传成功文件 fail-上传失败文件
  sucUploadFileNum = 0;//已成功上传文件数

  failUploadFileNum = 0;//上传失败文件个数
  selectFileList = [];
  issmallPhoto: boolean=false;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮
  selectItemNameDisplay:string = '';
  //selectItemNameDisplayCode;//接口传值需要的code值

  isdisplayqueryMateria;//判断是否是从进件列表页面的 “补充材料” 链接过来显示 当前tab项为补充材料
  //tempRes;
 // fileIsUploading;//判断文件此刻是否正在上传

  uploadEXENum:number=0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
  sucFileList;//补充材料上传接口的传给后台的参数

  itemStatusLists :any= ['房产估价','进件申请','风控审批','合作制作','协议公证','抵押登记','完成放款'];
  itemSelectStatus:string = '风控审批';
  itemSelectIndex : number = 2;//初始化

  businessStatusName:string;




  setSelectIndex(){
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if(this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }

    }
  }

  loginTokenKey=this.commonFunc.getloginToken();//登录时间戳
  userNo=this.user.getUserData().userNo;



  businessObj:Object = {};

  //定义进度条的长度
  progressLength:number;
  //定义文件上传的日期
  uploadTime:string;

  isEXE : boolean = false;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router:Router,
    public ref: ChangeDetectorRef
  ) {
    this.progressLength = 0;
    this.ref = ref;
  }

  ngOnInit() {


    //第二步 请求当前进件详细信息
    this.initBusinessDetailInfo();
    this. popupResize();
    //设置进件动态状态显示调用
    this.setBusinessStatuDisplay();
    this.isEXE = true;

    // this.returnPictureList()
    // this.onSubmitIsShow()
    //this.isdisplayqueryMateriafun() //判断是否是从进件列表页面的 “补充材料” 链接过来显示 当前tab项为补充材料调用


  }
  //实现文件下载
  downLoad(){

  }



  ngAfterViewChecked(){
    //申请信息、录单信息、补充材料tab切换 初始化的时候根据等到dom加载完成使其隐藏
    if(this.isEXE){
       this.tabSwitch();
       this.isEXE = false;
    }


    this.onSubmitIsShow();


  }

  //设置进件动态状态显示
  setBusinessStatuDisplay(){
    //读取当前进件缓存状态
    var businessObj = this.commonFunc.getBusinessData();

    //获取当前选中状态
    var handleObj = this.commonFunc.setBusinessLocalStatu(businessObj);

    this.itemSelectStatus = handleObj['buisessLocaStatu'];
    this.businessStatusName=handleObj['statusName'];


    //设置当前进件状态显示
    this.setSelectIndex();

    //根据当前进件状态显示
   if(this.itemSelectStatus=='风控审批'){
      $(".tab-menu-ul").find("li").eq(1).show();
      $(".tab_content").eq(1).show();

    }else if(this.itemSelectStatus=='进件申请'){
      $('.tab-menu-ul').find("li").eq(1).hide();
      $(".tab_content").eq(1).hide();

    }



    //赋值状态
  }

  initBusinessDetailInfo(){
     var BusinessData = this.commonFunc.getBusinessData();
     var bmsLoanCode = BusinessData.bmsLoanCode;
     var Obj = {
      'head': {
        'functionNo': 'HH000010',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
          'userNo': this.userNo,
          'bmsLoanCode': bmsLoanCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          this.businessObj = res.data.body;

          // console.log("进件详情请求信息接口 ++++",this.businessObj);
          this.setApplyInfoValueFunc(this.businessObj);
          this.setInputListValueFunc(this.businessObj);

          this.setApplyMaterialDataInfo(this.businessObj);


        }

      }

    });

  }

  //补充材料初始化的时候显示要补充的材料列表的文件个数
  setFileNumFunc(){
    var fileList = this.businessObj['fileList'];
    var materialTypeList = this.businessObj['materialTypeList'];


    for (let index = 0; index < materialTypeList.length; index++) {
      var marterObj = materialTypeList[index];
      var code = marterObj.code;
      var list=[];
      for (let i = 0; i < fileList.length; i++) {
        var fileObj = fileList[i];
        var fileType = fileObj['fileType'];
        if (code == fileType) {
          this.businessObj['materialTypeList'][index]['fileSize'] = fileObj['fileSize'];
          this.businessObj['materialTypeList'][index]['filePostfix'] = '个文件';
          this.businessObj['materialTypeList'][index]['displayFileNumStr'] = "（"+fileObj['fileSize']+'个文件'+"）";
          this.businessObj['materialTypeList'][index]['fileType'] = fileObj['fileType'];

          var image = this.setFileNameCatchImage(fileObj['originalFileName']);
          if('needUrl' == image){
              image = "http://pic38.nipic.com/20140304/251960_133602029000_2.jpg";
              // image = environment.server + "/fileLoad/getFile?serialNo=" + fileObj['fileId'];
          }
          fileObj['FileImage'] = image;
          list.push(fileObj);
        }
        this.businessObj['materialTypeList'][index]['fileList'] = list;
      }
    }
  //将当前材料补充项目中不含有文件时  组织页面中显示的数据为 无
    for(let index = 0; index < materialTypeList.length ; index++) {
      var marterObj = materialTypeList[index];
      if(!marterObj.hasOwnProperty('fileSize')){
        this.businessObj['materialTypeList'][index]['fileId'] = '';
        this.businessObj['materialTypeList'][index]['fileSize'] = '无';
        this.businessObj['materialTypeList'][index]['displayFileNumStr'] = "";
        this.businessObj['materialTypeList'][index]['filePostfix'] =  '';
        this.businessObj['materialTypeList'][index]['fileType'] = '';
        this.businessObj['materialTypeList'][index]['originalFileName'] = '';
      }
    }

    //当存在材料补充项目时  默认选中当前项目
    if(this.businessObj['materialTypeList'].length >= 1){
      this.onSelectMaterial(this.businessObj['materialTypeList'][0]);

    }





  }


  //根据文件名 确认文件类型 对应显示的图片
  setFileNameCatchImage(fileName:string){
      var fileNameArr = fileName.split('.');
      var number = fileNameArr.length - 1;
      var fileType = fileNameArr[number];

      fileType = fileType.toLowerCase();
      if (fileType == "jpg"||fileType == "jpeg"||fileType == "png"||fileType == "jif"){
        //图片类
        return 'needUrl';
      }else if(fileType == "doc"||fileType == "docx"){
        //word
        return '/assets/img/word.png';
      }else if (fileType == "xlsx"||fileType == "xls"){
      //exce;
        return 'assets/img/excl.png';
      }else if (fileType == "pdf"){
      //pdf
        return 'assets/img/pdf.png';
      }else if (fileType == "htm"||fileType == "html"){
      //html
        return 'assets/img/html.png';
      }else{
        //文本
        return 'assets/img/txt.png';
      }



  }




  //处理进件材料信息
  setApplyMaterialDataInfo(materialData:any){
    //处理基础信息
    this.commonFunc.handleNetworkDaraFormaFunc(materialData);
    this.setFileNumFunc();
     this.materialTypeListData = materialData.materialTypeList;
     this.materialFileList=materialData.fileList;




  }




  //点击选择补充材料列表的要显示的已有的图片
  onSelectMaterial(materialObj): void {
     if(!(materialObj instanceof Array)){
        this.selectedMaterial = materialObj;
        this.selectedMaterialDisplayImages = materialObj['fileList'];


     }else{
      this.selectedMaterial = materialObj[0];
      this.selectedMaterialDisplayImages = materialObj[0]['fileList'];

     }
     this.selectItemNameDisplay = this.selectedMaterial['name'];
     //this.selectItemNameDisplayCode = this.selectedMaterial['code'];

     //当单选框外部区域点击时，直接出发单选框的点击方法，调用了上面的方法逻辑
      $(event.target).find("input").click()



  }

//  补充材料，点击"补充材料"按钮切换到上传的界面
  annexMaterialChange(materialObj){
    $("#material_tab1").hide();
    $("#material_tab2").show();

  }


  //处理进件申请信息数据
  setApplyInfoValueFunc(obj:any){
    //进件公司
    var commpany = this.commonFunc.handleNilString(obj.distributorName);
    //业务经理
    var agentName = this.commonFunc.handleNilString(obj.agentName);

    //主借款人姓名
    var customerName = this.commonFunc.handleNilString(obj.customerName);

    //手机验证码
    var mobilephoneValidateNo = this.commonFunc.handleNilString(obj.mobilephoneValidateNo);

    //产品名称
     var productName = this.commonFunc.handleNilString(obj.productName);

    //放款方式
     var creditTypeName = this.commonFunc.handleNilString(obj.creditTypeName);

    //申请金额
    var loanAmount = this.commonFunc.handleNilString(obj.loanAmount);

    //借款期限
    var loanPeriodName = this.commonFunc.handleNilString(obj.loanPeriodName);

    //意向金
    var intentionMoney = this.commonFunc.handleNilString(obj.intentionMoney);
    //备注
    var remark = this.commonFunc.handleNilString(obj.remark);

    //家庭主要资产
    var mainHouseholdAsset = this.commonFunc.handleNilString(obj.mainHouseholdAsset);

    //家庭主要负债
    var mainHouseholdLiabilities = this.commonFunc.handleNilString(obj.mainHouseholdLiabilities);

    //家庭主要收入来源
    var mainHouseholdIncomeSource = this.commonFunc.handleNilString(obj.mainHouseholdIncomeSource);
    //抵押物实地调研情况
    var fieldInvestigationOfCollateral = this.commonFunc.handleNilString(obj.fieldInvestigationOfCollateral);

    //借款用途
    var usageOfLoan = this.commonFunc.handleNilString(obj.usageOfLoan);

   //还款来源
    var paymentSource = this.commonFunc.handleNilString(obj.paymentSource);



    this.applayInfoValueLists = [commpany,agentName,customerName,mobilephoneValidateNo,
                                 productName,creditTypeName,loanAmount,loanPeriodName,intentionMoney,remark];

    this.applayMainHouseholdLists = [mainHouseholdAsset,mainHouseholdLiabilities,
                                    mainHouseholdIncomeSource,fieldInvestigationOfCollateral,usageOfLoan,paymentSource];

  }

//处理进件录单信息数据
  setInputListValueFunc(obj:any){
    // //1、人员信息（总共10条）
    // //(1）姓名
    // var personListName = this.commonFunc.handleNilString(obj.personList.name);
    // //（2）证件类型
    // var personListIdType= this.commonFunc.handleNilString(obj.personList.idType);
    // //（3）证件号码
    // var personListIdNo = this.commonFunc.handleNilString(obj.personList.idNo);
    // //（4）证件有效期
    // var personListPeriodOfValidity = this.commonFunc.handleNilString(obj.personList.periodOfValidity);
    // //（5）手机验证码
    // var personListMobilePhone = this.commonFunc.handleNilString(obj.personList.mobilePhone);
    // //（6）工作单位
    // var personListOrganization = this.commonFunc.handleNilString(obj.personList.organization);
    // //（7）职位
    // var personListPosition = this.commonFunc.handleNilString(obj.personList.position);
    // //（8）年收入
    // var personListAnnual = this.commonFunc.handleNilString(obj.personList.annual);
    // //（9）家庭地址
    // var personListAddress = this.commonFunc.handleNilString(obj.personList.address);
    // //（10）婚史信息
    // var personListMarriage = this.commonFunc.handleNilString(obj.personList.marriage);

    // this.InputListValueLists = [personListName,personListIdType,personListIdNo,personListPeriodOfValidity,
    //   personListMobilePhone,personListOrganization,personListPosition,personListAnnual,personListAddress,personListMarriage];


      this.applyInputPersionInfoLists = obj.personList;//人员物信息
      this.mortgageList = obj.mortgageList;//抵押物信息
      this.spareHouseList = obj.spareHouseList;//备用房信息
      this.creditInfList = obj.creditInfList;//征信信息
      this.judicialInfList = obj.judicialInfList;//司法信息


  }

 //弹窗在窗口改变的时候
  popupResize(){
    $(document).resize(function() {

      if(parseInt($(window).height())<460){
        $(".popup").css({'top':'70px',"marginTop":"0"});
      }else{
        $(".popup").css({'top':"50%","marginTop":"-225px"});
      }

    });
  }
  //录单信息 四个弹窗的弹出隐藏
  //人员物信息弹窗
   dataInfoPersonPopup(i){
      this.selectItemPopViewObj = this.applyInputPersionInfoLists[i];
      $(".dataInfoPersonPopup ").show()
      return false;
  }
  //抵押物信息弹窗
  dataInfoPawnPopup(i){
    this.selectItemPopViewObj = this.mortgageList[i];//选中抵押物信息
    this.mortgageListInvestObj = this.selectItemPopViewObj.investInfList[0];//抵押物产调信息
    this.residenceInfList = this.selectItemPopViewObj.residenceInfList;//抵押物户口信息

    $(".dataInfoPawnPopup ").show()
    return false;
  }
  //征信弹窗
  dataInfoCreditPopup(i){
    this.selectItemPopViewObj = this.creditInfList[i];
    this.loanInfoList = this.selectItemPopViewObj.loanInfoList;
    this.loanCardInfoList = this.selectItemPopViewObj.loanCardInfoList;

    $(".dataInfoCreditPopup ").show()
    return false;
  }
  //司法信息弹窗信息
  dataInfoJudicialPopup(i){
    this.selectItemPopViewObj = this.judicialInfList[i];
    $(".dataInfoJudicialPopup ").show()
    return false;
  }
  popupCloseBtn(){
    $(".popup").hide()
    return false;
  }

    //tab切换
    tabSwitch(){

        if(sessionStorage.getItem('materia')=="10"){
          this.isdisplayqueryMateria=true;
              $('.tab_content').hide().eq($('.tab_content').length-1).show();
              $('.tab-menu-ul').find("li").on('click',function(){
                var index = $(this).index();
                $(this).addClass("current").siblings('li').removeClass('current');
                $('.tab_content').eq(index).show().siblings('.tab_content').hide();
              })
              sessionStorage.setItem('materia','');
              return this.isdisplayqueryMateria

          }else{
            this.isdisplayqueryMateria=false
              $('.tab_content').hide().eq(0).show();
              $('.tab-menu-ul').find("li").on('click',function(){
                var index = $(this).index();
                $(this).addClass("current").siblings('li').removeClass('current');
                $('.tab_content').eq(index).show().siblings('.tab_content').hide();
              })
              return this.isdisplayqueryMateria

          }


      }


  /*
   *文件上传功能  调用示例
   *<input type="file" ng2FileSelect [uploader]="uploader" (change)="selectedFileOnChanged($event)" />
   *
   */
  //初始化定义uploader变量,用来配置input中的uploader属性

  public uploader: FileUploader = new FileUploader({
    url: this.networkService.fileUploadUrl,
    method: "POST",
    itemAlias: "file",
  });

  //定义事件，选择文件
  selectedFileOnChanged(event: any) {
    //遍历已选择的文件，判断合法性（文件大小，（重名？））
    for(let i=0;i<this.uploader.queue.length;i++){
        var needReselect = false;
        //获取文件名称
        var fileNameStr:string  = this.uploader.queue[i]['file']['name'];

        //判断文件格式是否合法
        var fileFormart = this.judgeFileFormat(fileNameStr);
        if(!fileFormart){
          //将当前不合法文件移除

          this.wrongfulPop("文件不合法", "您上传的文件不合法，请重新上传");
          needReselect = true;

        }
        //判断文件上传个数大于30个
        if(this.uploader.queue.length > 30){
          this.fileTotalExcess("选择文件过多", "已选择文件超过单次限制","30","个，请重新选择。");
            needReselect = true;
        }
        //判断文件大小
        var fileSize = this.uploader.queue[i]['file']['size'];
        var maxFileSizeHH= 1024*1024* 10;

        if(fileSize >maxFileSizeHH ){

          //提示存在当个文件大于10M
          this.fileTotalExcess("选择文件过大", "已选择的文件中有超过","10M","的文件，请重新选择。");
          needReselect = true;

        }

        // if(判断文件是否重名){
        //   needReselect = true;
        // }
        //如果需要重新选择，则把刚刚选择的文件清除出队列
        if(needReselect){
          for(let k=this.uploadEXENum;k<this.uploader.queue.length;k++){
                  this.uploader.removeFromQueue(this.uploader.queue[k]);
                  k--;
            }
          return;
        }

        // var displayImage = this.setFileNameCatchImage(fileNameStr);

        // this.selectFileList.push(displayImage);//delete by xiezhilei 不清楚具体功能，暂时注释。
        this.uploader.queue[i].withCredentials = false;
    }

    // alert(1)
      $("#disableButton").show()
     $("#onSubmitButton").hide()
      // alert(2)
    this.uploadFile();
    // alert(3)




  }

  // 验证普通格式
  judgeFileFormat(fileName:string){
    var reg=/^.*(.jpg|.jpeg|.png|.gif|.doc|.docx|.xls|.xlsx|.pdf|.htm|.html|txt)$/;
    //判断选中文件格式是否合法
    let fileNameToLowerCase=fileName.substring(fileName.lastIndexOf('.')).toLowerCase();//把上传的文件名转成小写格式

    var q_flag=reg.test(fileNameToLowerCase);

    return q_flag;

    }


  //定义事件，上传文件
  uploadFile() {

    for(let j=this.uploadEXENum;j<this.uploader.queue.length;j++){

      this.uploader.queue[j].upload();

      //文件上传成功执行的回调函数
      this.uploader.queue[j].onSuccess = (response, status, headers)=> {//fileMaps数组只有一个上传的成功文件，是单个上传成功
        // 上传文件成功
        if (status == 200) {
          // 上传文件后获取服务器返回的数据

          let tempRes = JSON.parse(response);
         // console.log("上传文件的返回信息>>>>>>>>>>>>>>>>>>>>>>>>>>>：",tempRes)

          if(tempRes.body.returnCode == '0000'){
                //文件上传成功
                this.uploadTime=tempRes.body.fileMaps[0].uploadTime;
                var fileObj = tempRes.body.fileMaps[0]; //fileMaps数组只有一个上传的成功文件，是单个上传成功
                var fileNameObj=fileObj.fileName;
                var uploadFileList =   this.submitFileOBJ['suc'];  //是一个对象

                //判断img的类型
                let imageSrc = this.setFileNameCatchImage(fileNameObj);
                //如果是图片类型，直接使用本地文件base64数据
                if('needUrl' == imageSrc){
                  let selectFileListObj = this.selectFileList;
                  let reader = new FileReader();
                  reader.readAsDataURL(this.uploader.queue[j].some);
                  reader.onload = function (e) {
                    $("#uploadImg"+j).attr("src",this.result);
                    selectFileListObj.push(this.result);
                  };
                }else{
                //如果不是图片类型，使用setFileNameCatchImage返回的默认图片。
                  this.selectFileList.push(imageSrc);
                  // $("#uploadImg"+j).attr("src",imageSrc);console.log($("#uploadImg"+j));
                }
                // this.selectFileList.push(imageSrc);
                console.log("+++++++++++++++++++++++++++++++++++++++this.selectFileList:",this.selectFileList);


                uploadFileList.push(fileObj);

                this.sucUploadFileNum ++;

          }else {
              //文件上传失败
              var fileObj = tempRes.body.fileMaps[0];
              var failFileList =  this.submitFileOBJ['fail'];

              failFileList.push(this.uploader.queue[j].file);

              //failFileList.push(fileObj);
              this.failUploadFileNum ++;


          }
          this.ref.markForCheck();
          this.ref.detectChanges();
          this.uploader.queue[j]['uploadTime'] = this.uploadTime;
          //在当前选中的材料项目中  放置当前上传成功文件对象列表
        } else {
          // 上传文件后获取服务器返回的数据错误
          this.failUploadFileNum ++;
          alert("server return error ");

        }
      };

       //  文件上传失败执行的回调函数
      this.uploader.queue[j].onError = (response, status, headers)=> {
        this.failUploadFileNum ++;

        if(status == 200){
        let tempRes = JSON.parse(response);
        var failFileList =  this.submitFileOBJ['fail'];
        failFileList.push(this.uploader.queue[j].file);

        }else{

        }


      };

      //上传进度2
      this.uploader.onProgressItem=(fileItem, progress)=>{

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

      this.uploadEXENum ++;
    }

        //上传完成
        this.uploader.onCompleteItem = function (response, status, headers) {
            $("#disableButton").hide()
            $("#onSubmitButton").show()


            // $("#uploadedfileNumber").hide();
            // $("#uploadingfileNumber").show();

        };

        this.uploader.onCompleteAll=()=>{

            if( this.sucUploadFileNum > 0 ){ //提交按钮在全部上传失败后需要显示禁止按钮
                $("#disableButton").hide()
                $("#onSubmitButton").show()

           } else if( this.sucUploadFileNum == 0 && this.failUploadFileNum > 0 ){
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

   upAgiain(i){
    this.failUploadFileNum--;
    if(this.failUploadFileNum<=0){
      this.failUploadFileNum=0
    }
    this.uploader.queue[i].upload();

  }

  //删除上传文件
  deleteFile(i, isSucDel){

    this.uploader.queue[i].remove();
    i--;
    if(isSucDel == 0){
        this.failUploadFileNum--;
    }else{
        this.sucUploadFileNum--;
    }
    this.uploadEXENum--;

   // console.log('uploadEXENum:'+this.uploadEXENum+'，删除this.sucUploadFileNum:'+this.sucUploadFileNum+",i："+i);


  }




  //材料补充补充接口
  onSubmitUploadFileFunc(){
   this.sucFileList =  this.submitFileOBJ['suc'];
   var failFileList = this.submitFileOBJ['fail'];


    if(this.failUploadFileNum > 0){
      //存在上传情况  需要提示用户是否继续上传文件
      this.fileUploadlPopup();
    }else if(this.failUploadFileNum ==0){
        this.popNextSuccessModal("补充材料成功",'成功上传',"个文件，","秒钟返回进件材料页面")
    }



      this.onSubmitUploadFileInterfaceFunc();
    // var Obj = {
    //   'head': {
    //     'functionNo': 'HH000015',
    //     'userNo': this.userNo,
    //     'clientTimestamp': this.loginTokenKey
    //   },
    //   'body': {
    //     'userNo': this.userNo,
    //     'uploadFileList':sucFileList


    //   }
    // };

    // this.networkService.postData(Obj, false).then(res => {

    //   if (res.retCode == true) {
    //     // 请求网络通信处理方法
    //     if (this.networkService.onJudgeSuccessful(res)) {
    //       //提交成功  提示用户


    //     }else{
    //       //提交失败 提示用户
    //     }

    //   }else{
    //     //网络出现问题 提示用户

    //   }

    // });

  }



 //补充材料接口

   onSubmitUploadFileInterfaceFunc(){
     var Obj = {
      'head': {
        'functionNo': 'HH000015',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'uploadFileList':this.sucFileList


      }
    };

    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //提交成功  提示用户


        }else{
          //提交失败 提示用户
        }

      }else{
        //网络出现问题 提示用户

      }

    });
   }


  //页面标题跳转
  querySection(){
    this.router.navigate(['/home/query'])
  }
  history(){
    this.router.navigate(['/home/history'])

  }

// 申请消息tab里面的 下载
  queryload(){
  window.location.href='http://192.168.0.136:8080/fms/api/fileLoad/getFile?serialNo=5bf0d013-4b6e-11e7-904c-1866dae83f00';
}
  //当组件销毁前  移除读取标志
  ngOnDestroy() {
    //  组件销毁前 移除已读取暂存进件标志
    this.commonFunc.removeBusinessSaveFlagFunc();
  }


 //补充材料的提交按钮与禁止提交按钮的显示问题
  onSubmitIsShow(){

     var smallPhoto=$("#smallPhoto").html()
     if(smallPhoto==undefined||smallPhoto==""){

     this.issmallPhoto=false;


    }else{
      this.issmallPhoto=true;
    }
  }

  //提交按钮的弹窗
  fileUploadlPopup(){
    this.popUploadModal('文件上传提交',"有","个文件上传失败，是否提交已成功上传的文件？");
  }

  //补充材料的 取消按钮  的方法
  cancelPopup(){
    //根据是否有上传 的内容判断取消按钮的是弹窗还是返回上一个页面

      var smallPhoto=$("#smallPhoto").html()
        if(smallPhoto==undefined||smallPhoto==""){
        //没有有上传文件 取消按钮返回上一个页面
          $("#material_tab1").show()
          $("#material_tab2").hide()
          $("#uploadFileBox").html("");

        }else{
            //有上传文件 取消按钮弹窗
              this.popNextErrorModal("取消上传", "已上传的文件将不会保存，是否继续？");

        }



  }



  //  补充材料点击按钮弹出窗口

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
      return true;
    }
  }


  //2.点击"提交" 按钮的  文件过大过多的弹窗
  fileTotalExcess(tipTitle, tipContent,num,text){
    var that = this;
    var content =  "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "<span class='secondValue'>"+num+"</span>" + text + "</div>";
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
        return true;
      }
  }
  //3.点击"取消" 按钮的弹窗
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
      $(".upload-file-box").remove();
      $("#material_tab1").show()
      $("#material_tab2").hide()
      return true;
    }
  }
  //4.点击"提交" 按钮的弹窗(这个是上传有失败的文件处理的弹窗)
  popUploadModal(tipTitle, tipContent,text){
      var that = this;
       var content =  `<div style='margin-bottom: 50px; font-weight: bold;'>${tipContent} <span style='color:#ffa00a;'>${this.failUploadFileNum}</span> ${text}</div>`
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
      return true;
    }

    function myConfirmButtonClickCallback() {

      that.onSubmitUploadFileInterfaceFunc();//接口调用
      that.sucUploadFileNum = 0;//已成功上传文件数
      that.failUploadFileNum = 0;//上传失败文件个数
      that.uploadEXENum=0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
      that.uploader = new FileUploader({
      url: this.networkService.fileUploadUrl,
      method: "POST",
      itemAlias: "file",
      maxFileSize: 1024   * 10,


  });
      $(".upload-file-box").remove();
      $("#material_tab1").show()
      $("#material_tab2").hide()
      $(this).closeModal();
      return true;
    }
  }

  //5.点击"提交" 按钮的弹窗(这个是上传没有失败的文件处理的弹窗)
  popNextSuccessModal(tipTitle,tiptxt, tipContent, text) {
    var that = this;
     var content =`<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}<span style='color:#ffa00a;'>${this.sucUploadFileNum}</span>${tipContent}<span id='secondValue'>5</span>${text}</div>`
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
      that.onSubmitUploadFileInterfaceFunc();//接口调用
      that.sucUploadFileNum = 0;//已成功上传文件数
      that.failUploadFileNum = 0;//上传失败文件个数
      that.uploadEXENum=0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传

      that.uploader.clearQueue();

      $(".upload-file-box").remove();
      $("#material_tab1").show()
      $("#material_tab2").hide()
      $(this).closeModal();

      return true;
    }


    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          that.onSubmitUploadFileInterfaceFunc();//接口调用
          that.sucUploadFileNum = 0;//已成功上传文件数
          that.failUploadFileNum = 0;//上传失败文件个数
          that.uploadEXENum=0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
          that.uploader.clearQueue();
          $(this).closeModal();
          clearInterval(reduceSecondValue);
          $(".upload-file-box").remove();
          $("#material_tab1").show()
          $("#material_tab2").hide()
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }



}
