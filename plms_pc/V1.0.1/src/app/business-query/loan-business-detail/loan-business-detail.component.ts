import {AfterViewInit, Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import set = Reflect.set;
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
import {BackButtonComponent} from "../../common-model/back-button/back-button.component";

@Component({
  selector: 'app-loan-business-detail',
  templateUrl: './loan-business-detail.component.html',
  styleUrls: ['./loan-business-detail.component.css'],
})
export class LoanBusinessDetailComponent implements OnInit {
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,) {
    // var ss=loanBusinessDetail.getHighLight();
    // console.log('++++++++++++++++++++++++++++this is ss',ss);
  }

  button: any = {
    'name': '',
    'url': ''
  };

  currentItemLists: any = {};
  contractCode: string; //借款合同数据唯一标识
  repaymentSchedule: any = {};//利息不代收还款计划和利息代收还款计划
  paidInfo: any = {}; //放款记录
  notarialAndMortgage = {};//公正抵押
  approvalInfo: any = {};//审批信息
  applyInfo: any = {};//进件详情
  materialTypeListData: any = {}; //进件材料
  totalListsCount: any = {};
  professionalData: any = [];
  tabHighLight: string = '';


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
    }else if (backPath=='accountInfo'){
      this.button.name='返回账户查询';
      this.button.url='accountInfo';
      this.button.title='账户信息查询';
    }


    this.contractCode = this.activetedRoute.snapshot.params['id'];
    this.tabHighLight = window.sessionStorage.getItem('tabHighLight') || '01';

    //如果把所有的数据请求都放到service里面可以么？？
    //   if(this.loanBusinessDetail.approvalInfo.mortgageList.length==1){
    //     this.showSomeApproval=true;
    //   }else {
    //     this.showSomeApproval=false;
    //   }

    this.QueryEntryList();



  }

  dependHighLight(tabHighLight) {
    this.tabHighLight = tabHighLight;
  }

  //贷款业务查询列表接口
  QueryEntryList = () => {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;


    var Obj = {
      'head': {
        'functionNo': 'plms100011',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        "contractCode": this.contractCode,
      }
    };


    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {


          this.currentItemLists = res.data.body;
          this.professionalData = [
            {content: '借款合同编号：', styleName: 'message-title font-weight-bold'},
            {content: this.currentItemLists.contractNo, styleName: 'message-number'}
          ];

          this.repaymentSchedule = this.currentItemLists['repaymentSchedule'];//利息不代收还款计划和利息代收还款计划
          this.paidInfo = this.currentItemLists['paidInfo']; //放款记录
          this.notarialAndMortgage = this.currentItemLists['notarialAndMortgage']; //公正抵押
          this.approvalInfo = this.currentItemLists['approvalInfo'];//审批信息
          this.applyInfo = this.currentItemLists['applyInfo'];//进件详情
          this.materialTypeListData = this.currentItemLists['materialTypeListData']; //进件材料

          //登录成功处理逻辑
          // var pages = this.commonFunc.handleNilString(res.data.body.pages);
          // if (pages == '') pages = '0';
          // var currentPage = this.commonFunc.handleNilString(res.data.body.currentPage);
          // if (currentPage == '') currentPage = '0';
          //
          // var objData = {
          //   'totalPage': pages,
          //   'currentPage': currentPage,
          //   'total': res.data.body.total
          //
          // };

          //设置页面显示 第几条至第几条
          // this.totalListsCount = res.data.body.total;

          //首次获取分页信息 设置分页插件
          //  if(this.currentItemLists.length == 0){
          //this.setPageFunc(objData, this);

          //  }
          // this.currentItemLists = res.data.body.dataList;

          //this.setDisplayItemsCount();


          //修改查询列表状态 暂时前端映射 待需求确认之后 以后台为准
          //material 充材料为 1-不显示  2-显示

          // for (var i = 0; i < this.currentItemLists.length; i++) {
          //   var obj = this.currentItemLists[i];
          //   var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
          //   this.currentItemLists[i]["statusName"] = resultObj['statusName'];
          //   this.currentItemLists[i]["operate"] = resultObj['operate'];
          //   this.currentItemLists[i]["material"] = resultObj['material'];
          //
          //   this.currentItemLists[i]["supplementaryMateria"] = resultObj['supplementaryMateria'];
          //
          //
          // }

          //this.totalItemLists[this.selectPageNo] = this.currentItemLists;


        }
      }

    });
    return false;
  }
//主按钮链接
  querySection() {
    this.router.navigate(["/home/"+this.button.url])
  }

  paymentDetailPageShow:boolean=false;

  getPaymentData(event){
    this.paymentDetailPageShow=event;
  }

  recordDetailPageShow:boolean=false;
  getRecordData(event){
    this.recordDetailPageShow=event;
  }
}
