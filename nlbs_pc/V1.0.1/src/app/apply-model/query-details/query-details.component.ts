import {Component, OnInit, DoCheck} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Data, Router} from "@angular/router";
import {FileUploader} from "ng2-file-upload";
import {ChangeDetectorRef} from "@angular/core";
import {environment} from "../../../environments/environment";
declare var $: any;
@Component({
  selector: 'app-query-details',
  templateUrl: './query-details.component.html',
  styleUrls: ['./query-details.component.css'],
})
export class QueryDetailsComponent implements OnInit {

  approvalNotice:any={};//审批通知

  //申请信息
  applayInfoValueLists: any = [];// 进件基础信息值列表
  applayMainHouseholdLists: any = [];//申请信息 案件概况值列表

  //审批通知
  setApprovalInfoLists: any = [];

  //录单信息
  selectItemPopViewObj: any = {};//当前点击显示的详情信息
  applyInputPersionInfoLists: any = [];//人员物信息
  mortgageList: any = [];//抵押物列表
  loanAccountList:any={};//放款账户信息
  mortgageListInvestObj: any = {};//抵押物产调信息
  residenceInfList: any = [];//抵押物户口信息
  spareHouseList: any = [];//备用房列表信息
  creditInfList: any = [];//征信信息
  judicialInfList: any = [];//司法信息
  loanInfoList: any = [];//征信信息 贷款信息
  loanCardInfoList: any = [];//征信信息 贷记卡信息

  //补充材料
  materialFileList;
  materialTypeListData;//补充材料项目列表（内容 含有文件数）
  selectedMaterial;//补充材料当前选中的对象
  selectedMaterialDisplayImages; //补充材料右边显示图片的列表
  submitFileOBJ: any = {'suc': [], 'fail': []};//补充材料中选中提交文件内容  suc-上传成功文件 fail-上传失败文件
  sucUploadFileNum = 0;//已成功上传文件数


  failUploadFileNum = 0;//上传失败文件个数
  selectFileList = [];
  issmallPhoto: boolean = false;//判断补充材料的提交按钮是显示禁用的灰色按钮还是显示可以提交的黄色按钮
  selectItemNameDisplay: string = '';
  //selectItemNameDisplayCode;//接口传值需要的code值

  isdisplayqueryMateria;//判断是否是从进件列表页面的 “补充材料” 链接过来显示 当前tab项为补充材料
  //tempRes;
  // fileIsUploading;//判断文件此刻是否正在上传

  uploadEXENum: number = 0;//记录当前上传队列 已执行上传文件位置  当再次添加文件时 以上次结尾位置开始执行上传
  sucFileList;//补充材料上传接口的传给后台的参数

  itemStatusLists: any = ['房产估价', '进件申请', '风控审批', '合同制作', '协议公证', '抵押登记', '授信完成'];
  itemSelectStatus: string = '风控审批';
  itemSelectIndex: number = -1;//初始化

  businessStatusName: string;
  loanInfoShow:boolean=false;//授信信息显示
  applyInfoShow:boolean=false;//申请信息
  applyMaterialShow:boolean=false; //=====进件材料
  catalogueInfoShow:boolean=false; //=====录单信息
  approvalNoticeShow:boolean=false;//====审批通知
  applyDetailShow:boolean=false;//进件详情
  contractShow:boolean=false;//签证公约
  equityShow:boolean=false;//公正抵押

  applyInfoData:any = [];
  catalogueInfoData:any =[];
  mortgageData:any={};
  signingNotarization:any=[]; //公证抵押
  loanInfo:any=[];  //授信放款


  //设置tab哪一个为高亮  申请信息高亮是01   审批通知高亮是02  进件材料高亮是03  进件详情是04   录单信息是05
  tabHighLight:string='';

  setSelectIndex() {
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if (this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }
    }
  }

  loginTokenKey = this.commonFunc.getloginToken();//登录时间戳
  userNo = this.user.getUserData().userNo;


  businessObj: Object = {};

  //定义进度条的长度
  progressLength: number;
  //定义文件上传的日期
  uploadTime: string;

  isEXE: boolean = false;

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              public ref: ChangeDetectorRef,
  ) {
    this.progressLength = 0;
    this.ref = ref;

  }

  ngOnInit() {


    //第二步 请求当前进件详细信息
    this.initBusinessDetailInfo();
    this.popupResize();
    //设置进件动态状态显示调用
    // this.setBusinessStatuDisplay();
    this.isEXE = true;
  }

  ngAfterViewChecked() {
    //申请信息、录单信息、补充材料tab切换 初始化的时候根据等到dom加载完成使其隐藏
    this.onSubmitIsShow();
  }

  dependHighLight(str){
    this.tabHighLight=str;

    //进件详情
    if(this.tabHighLight == '04'){
      this.catalogueInfoData.existsLoaner = true;
      this.catalogueInfoData.existsPerson = false;
    }
    //录单信息
    else if(this.tabHighLight == '05'){
      this.catalogueInfoData.existsPerson = true;
      this.catalogueInfoData.existsLoaner = false;
    }
    //公正抵押
    if(this.tabHighLight == '07'){//公正抵押
      this.mortgageData.equity=true;
      this.mortgageData.contract=false;
    }else if(this.tabHighLight == '06'){//签约公正
      this.mortgageData.equity=false;
      this.mortgageData.contract=true;
    }

    //签约公证
  }


  //显示撤单  如果是已过风控  显示三个标签页 ，申请信息，录单信息和进件材料
  //否则显示   申请信息  进件材料
  isPassRiskManage:boolean;


  //拒单，撤单 原因
  refuseReason:string='';
  preBusinessStatus:string='';//撤单是否已经过了风控审批


  //loanSerialNo 暂时历史页面需要
  // toChangeLoanSerialNo:string='123';
  //
  initBusinessDetailInfo() {
    var BusinessData = this.commonFunc.getBusinessData();
    var loanSerialNo = BusinessData.loanSerialNo;
    var Obj = {
      'head': {
        'functionNo': 'HH000010',
        'userNo': this.userNo,
        'clientTimestamp': this.loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'loanSerialNo': loanSerialNo
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          this.businessObj = res.data.body;
          this.setApplyInfoValueFunc(this.businessObj);
          this.setApprovalInfoValueFunc(this.businessObj);
          this.setInputListValueFunc(this.businessObj);
          this.setApplyInfoListFunc(this.businessObj);
          this.setApprovalNoticeFunc(this.businessObj);
          this.setApplyMaterialDataInfo(this.businessObj);



        console.log('----------------+++++++++++:', this.materialTypeListData)
        //  获得撤单所存在的状态
          this.preBusinessStatus= res.data.body.preBusinessStatus;

          this.setBusinessStatuDisplay();

        // 给toChangeLoanSerialNo赋值
        //   this.toChangeLoanSerialNo=res.data.body.loanSerialNo;
        //   this.commonFunc.toChangeLoanSerialNo=res.data.body.loanSerialNo;
        //   this.commonFunc.toChangeLoanSerialNo=res.data.body.loanSerialNo;
          window.sessionStorage.setItem('toChangeLoanSerialNo',res.data.body.loanSerialNo);
          // console.log('-------------------------------',this.commonFunc.toChangeLoanSerialNo);
          //  获得拒单原因
          this.refuseReason= res.data.body.approvalNotice.finalTrialOpinion+res.data.body.approvalNotice.retrialOpinion+res.data.body.approvalNotice.firstTrialOpinion;
        }

      }

    });

  }
  //设置进件动态状态显示
  setBusinessStatuDisplay() {
    //如果是从进件材料进来   最后一个高亮    如果是从查看进来  第一个高亮
    if (sessionStorage.getItem('materia') == "10"){
      this.isdisplayqueryMateria=true;
      window.sessionStorage.setItem('materia','');
    }
    //读取当前进件缓存状态
    var businessObj = this.commonFunc.getBusinessData();
    //获取当前选中状态
    var handleObj = this.commonFunc.setBusinessLocalStatu(businessObj);
    this.itemSelectStatus = handleObj['buisessLocaStatu'];
    this.businessStatusName = handleObj['loanStatusName'];
    //设置当前进件状态显示
    this.setSelectIndex();
    //根据当前进件状态显示   申请信息高亮是01   审批通知高亮是02  进件材料高亮是03  进件详情是04  录单信息是05  签约公正是06

    // applyInfoShow:boolean;//申请信息
    // applyMaterialShow:boolean; //=====进件材料
    // catalogueInfoShow:boolean //=====录单信息
    // approvalNoticeShow:boolean;//====审批通知

    if (this.itemSelectStatus == '风控审批') {//审批中  显示申请信息  录单信息 进件材料
      this.applyInfoShow=true;//申请信息
      this.catalogueInfoShow=true;//录单信息
      this.applyMaterialShow=true;//进件材料
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='01';
      }
    } else if (this.itemSelectStatus == '进件申请') {//录单中
      this.applyInfoShow=true;//申请信息
      this.applyMaterialShow=true;//进件材料
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='01';
      }
    } else if(this.itemSelectStatus == '合同制作'){//制作合同中
      this.applyInfoShow=true;//申请信息
      this.applyMaterialShow=true;//进件材料
      this.approvalNoticeShow=true;//审批通知
      this.applyDetailShow=true;//进件详情
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='02';
      }
    }else if(this.itemSelectStatus == '协议公证'){//协议公正
      this.applyInfoShow=true;//申请信息
      this.applyMaterialShow=true;//进件材料
      this.approvalNoticeShow=true;//审批通知
      this.applyDetailShow=true;//进件详情
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='02';
      }
    }else if(this.itemSelectStatus == '抵押登记') {//抵押登记
      this.applyInfoShow = true;//申请信息
      this.applyMaterialShow = true;//进件材料
      this.approvalNoticeShow = true;//审批通知
      this.applyDetailShow = true;//进件详情
      //在抵押登记中  如果是房款中则显示公正抵押  如果是待抵押则显示签约公正
      if(this.businessStatusName=='放款中'){
        this.equityShow = true;//公正抵押
        this.contractShow = false;//签约公正
        if (this.isdisplayqueryMateria) {
          this.tabHighLight = '03';
        } else {
          this.tabHighLight = '07';
          this.mortgageData.equity=true;
          this.mortgageData.contract=false;
        }
      }else{
        this.contractShow = true;//签约公正
        this.equityShow = false; //公正抵押
        if (this.isdisplayqueryMateria) {
          this.tabHighLight = '03';
        } else {
          this.tabHighLight = '06';
          this.mortgageData.equity=false;
          this.mortgageData.contract=true;
        }
      }
    }else if(this.itemSelectStatus == '授信完成'){//协议公正
      this.loanInfoShow=true;//授信信息
      this.equityShow = true;//公正抵押
      this.applyInfoShow=true;//申请信息
      this.applyMaterialShow=true;//进件材料
      this.approvalNoticeShow=true;//审批通知
      this.applyDetailShow=true;//进件详情
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='08';
      }
    }else if(this.itemSelectStatus == '拒单'){//拒单
      this.applyInfoShow=true;//申请信息
      this.catalogueInfoShow=true;//录单信息
      this.applyMaterialShow=true;//进件材料
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='01';
      }
    }else if(this.itemSelectStatus == '撤单'){
      if(this.preBusinessStatus){
        if(this.preBusinessStatus=='01'||this.preBusinessStatus=='02'){
          this.catalogueInfoShow=false
        }else {
          this.catalogueInfoShow=true;//录单信息
        }
      }
      this.applyInfoShow=true;//申请信息
      this.applyMaterialShow=true;//进件材料
      if(this.isdisplayqueryMateria){
        this.tabHighLight='03';
      }else {
        this.tabHighLight='01';
      }

    }

    //赋值状态
  }


  //处理进件材料信息
  loanSerialNo : string = '';
  setApplyMaterialDataInfo(materialData: any) {
    //处理基础信息
    this.commonFunc.handleNetworkDaraFormaFunc(materialData);
    // this.setFileNumFunc();
    this.materialTypeListData = materialData.materialTypeList;
    this.loanSerialNo = materialData.loanSerialNo;
    // this.materialTypeListData = materialData.materialTypeList;
    // this.materialFileList = materialData.fileList;
  }



  //点击选择补充材料列表的要显示的已有的图片
  onSelectMaterial(materialObj): void {
    if (!(materialObj instanceof Array)) {
      this.selectedMaterial = materialObj;
      this.selectedMaterialDisplayImages = materialObj['fileList'];


    } else {
      this.selectedMaterial = materialObj[0];
      this.selectedMaterialDisplayImages = materialObj[0]['fileList'];

    }
    this.selectItemNameDisplay = this.selectedMaterial['name'];
    //this.selectItemNameDisplayCode = this.selectedMaterial['code'];

    //当单选框外部区域点击时，直接出发单选框的点击方法，调用了上面的方法逻辑
    $(event.target).find("input").click()


  }

//  补充材料，点击"补充材料"按钮切换到上传的界面
  annexMaterialChange(materialObj) {
    $("#material_tab1").hide();
    $("#material_tab2").show();

  }


  //处理进件申请信息数据
  setApplyInfoValueFunc(obj: any) {
    //进件公司
    this.applyInfoData['commpany'] = obj.distributorName;
    //业务经理
    this.applyInfoData['agentName'] = obj.agentName;

    //主借款人姓名
    this.applyInfoData['customerName'] = obj.customerName;

    //手机验证码
    this.applyInfoData['mobilephoneValidateNo'] = obj.mobilephoneValidateNo;

    //产品名称
    this.applyInfoData['productName'] = obj.productName;

    //放款方式
    this.applyInfoData['creditTypeName'] = obj.creditTypeName;

    //申请金额
    this.applyInfoData['loanAmount'] = obj.loanAmount;

    //借款期限
    this.applyInfoData['loanPeriodName'] = obj.loanPeriodName;

    //意向金
    this.applyInfoData['intentionMoney'] = obj.intentionMoney;
    //备注
    this.applyInfoData['remark'] = obj.remark;

    //家庭主要资产
    this.applyInfoData['mainHouseholdAsset'] = obj.mainHouseholdAsset;

    //家庭主要负债
    this.applyInfoData['mainHouseholdLiabilities'] = obj.mainHouseholdLiabilities;

    //家庭主要收入来源
    this.applyInfoData['mainHouseholdIncomeSource'] = obj.mainHouseholdIncomeSource;
    //抵押物实地调研情况
    this.applyInfoData['fieldInvestigationOfCollateral'] = obj.fieldInvestigationOfCollateral;

    //借款用途
    this.applyInfoData['usageOfLoan'] = obj.usageOfLoan;

    //还款来源
    this.applyInfoData['paymentSource'] = obj.paymentSource;

    //抵押物列表
    this.applyInfoData['mortgageList'] = obj.mortgageList;


    //判断“案件概况” 是否显示
    this.applyInfoData['displayCaseInfo'] = obj.displayCaseInfo;
    this.applyInfoData['caseInfo'] = obj.caseInfo;

    // 录单信息 的上载材料
    this.applyInfoData['applyFileId']=obj.applyFileId;
    this.applyInfoData['applyFileName']=obj.applyFileName;
  }

  //处理审批通知的信息数据
  setApprovalInfoValueFunc(obj:any){

  }

//处理进件录单信息数据
  setInputListValueFunc(obj: any) {
    this.catalogueInfoData['applyInputPersionInfoLists'] = obj.personList;//人员信息
    this.catalogueInfoData['borrowerList'] = obj.borrowerList;// 借款人信息
    this.catalogueInfoData['mortgageList'] = obj.mortgageList;//抵押物信息
    this.catalogueInfoData['spareHouseList'] = obj.spareHouseList;//备用房信息
    this.catalogueInfoData['loanAccountInfo']=obj.loanAccountInfo;//放款账户信息
    this.catalogueInfoData['creditInfList'] = obj.creditInfList;//征信信息
    this.catalogueInfoData['judicialInfList'] = obj.judicialInfList;//司法信息
    this.mortgageData['signingNotarization']=obj.signingNotarization//公证抵押
  }
  //处理进件信息的数据
  setApplyInfoListFunc(obj:any){
    this.loanInfo=obj.loanInfoList;

  }

  //处理审批通知的数据
  setApprovalNoticeFunc(obj:any){
    this.approvalNotice=obj.approvalNotice//审批通知
  }

  //弹窗在窗口改变的时候
  popupResize() {
    $(document).resize(function () {

      if (parseInt($(window).height()) < 460) {
        $(".popup").css({'top': '70px', "marginTop": "0"});
      } else {
        $(".popup").css({'top': "50%", "marginTop": "-225px"});
      }

    });
  }

  //页面标题跳转
  querySection() {
    this.router.navigate(['/home/query'])
  }

  history() {
    this.router.navigate(['/home/history'])

  }

// 申请消息tab里面的 下载
  queryload() {
    window.location.href = 'http://192.168.0.136:8080/fms/api/fileLoad/getFile?serialNo=5bf0d013-4b6e-11e7-904c-1866dae83f00';
  }

  //当组件销毁前  移除读取标志
  ngOnDestroy() {
    //  组件销毁前 移除已读取暂存进件标志
    this.commonFunc.removeBusinessSaveFlagFunc();
  }


  //补充材料的提交按钮与禁止提交按钮的显示问题
  onSubmitIsShow() {

    var smallPhoto = $("#smallPhoto").html()
    if (smallPhoto == undefined || smallPhoto == "") {

      this.issmallPhoto = false;


    } else {
      this.issmallPhoto = true;
    }
  }


  //补充材料的 取消按钮  的方法
  cancelPopup() {
    //根据是否有上传 的内容判断取消按钮的是弹窗还是返回上一个页面

    var smallPhoto = $("#smallPhoto").html()
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
  fileTotalExcess(tipTitle, tipContent, num, text) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "<span class='secondValue'>" + num + "</span>" + text + "</div>";
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



}
