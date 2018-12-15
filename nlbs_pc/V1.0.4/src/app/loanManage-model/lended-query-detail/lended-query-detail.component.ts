import {AfterViewInit, Component, OnInit, ViewChild, ViewChildren} from '@angular/core';
import set = Reflect.set;
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
//import {lendedQueryDetailService } from './lended-query-detail.service';

@Component({
  selector: 'app-lended-query-detail',
  templateUrl: './lended-query-detail.component.html',
  styleUrls: ['./lended-query-detail.component.css'],
  //providers:[lendedQueryDetailService],
})
export class LendedQueryDetailComponent implements OnInit {
  repaymentSchedule: any;
  confirmed:any;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,) { }
  contractCode:string;

  currentItemLists:any={};
  professionalData:any;
  ngOnInit() {
    this.contractCode = this.activetedRoute.snapshot.params['id'];
    this.QueryEntryList();
    this.tabHighLight ='01';
  }
  tabHighLight:any;
  dependHighLight(tabHighLight) {
    this.tabHighLight = tabHighLight;
  }

  //借款业务初始化接口
  QueryEntryList = () => {
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;


    var Obj = {
      'head': {
        'functionNo': 'plms300011',
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

          this.repaymentSchedule = this.currentItemLists['isInterestInstead'];//利息不代收还款计划和利息代收还款计划
          this.confirmed=this.currentItemLists['confirmed']
          // this.paidInfo = this.currentItemLists['paidInfo']; //放款记录
          // this.notarialAndMortgage = this.currentItemLists['notarialAndMortgage']; //公正抵押
          // this.approvalInfo = this.currentItemLists['approvalInfo'];//审批信息
          // this.applyInfo = this.currentItemLists['applyInfo'];//进件详情
          // this.materialTypeListData = this.currentItemLists['materialTypeListData']; //进件材料

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
    this.router.navigate(["/home/lendedQuery"])
  }
  detailPageShow:boolean=false;

  getData(event){
    this.detailPageShow=event;
  }
}
