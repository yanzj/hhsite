/**
 * Created by dell on 2017/7/25.
 */
import {Injectable} from '@angular/core';
import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {ActivatedRoute, Router} from "@angular/router";
@Injectable()
export class loanBusinessDetailService {

  public tabHighLight: string ;
  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private activetedRoute: ActivatedRoute,) { }
  dependHighLight(tabHighLight) {
    this.tabHighLight = tabHighLight;
  }
  setHighLight(value){
    this.tabHighLight=value;
  }
  getHighLight(){
    return this.tabHighLight;
  }

  //根据审批信息的抵押物的个数 如果是一个的话 显示approval-information组件
  //根据审批信息的抵押物的个数 如果是多个的话 显示some-approval-info组件

  //审批信息
  public approvalInfo = {
    "firstApprovalSuggestion":"初审审批意见",
    "secondApprovalSuggesion":"复审审批意见",
    "finalApprovalSuggesion":"终审审批意见",
    "fundSideSuggesion":"资方审批意见",
    "guaranteeSuggesion":"担保审批意见",
    "insuranceSuggesion":"保险审批意见",
    "productName": "产品名称",
    "agentName": "业务经理",
    "approvalTime": "批复日期",
    "customerName": "借款人",
    "approvalQuota": "批复额度",
    "approvalQuotaTotal": "批复总额",
    "approvalPeriod": "批复期限",
    "annualizedInterest": "年化利率",
    "inquiryInformation": "抵押物评估价",
    "creditorTotalAmount": "抵押权债权总额",
    "mortgageType": "抵押类型",
    "mortgageRate": "综合抵押率",
    "houseValue": "合同抵押物价值",
    "guaranteeLimit": "担保额度",
    "guaranteeFeeRate": "担保费率",
    "guaranteeFeeAmount": "担保费",
    "bailRate": "保证金比例",
    "bailAmount": "保证金金额",
    "serviceChargeAmount": "服务费",
    "lendingTerms": "放款条件",
    "guaranteeCondition": "担保条件",
    "certificateNumber": "产证编号",
    "mortgageList": [
      {
        "certificateNumber":"产证编号",
        "inquiryInformation": "评估价",
        "creditorTotalAmount": "抵押权债权总额",
        "approvalQuota": "批复金额",
        "guaranteeLimit": "担保额度",
        "annualizedInterest": "年化利率",
        "mortgageType": "抵押类型",
        "mortgageRate": "抵押率",
        "houseValue": "合同抵押物价值",
      },
      {
        "certificateNumber":"产证编号",
        "inquiryInformation": "评估价",
        "creditorTotalAmount": "抵押权债权总额",
        "approvalQuota": "批复金额",
        "guaranteeLimit": "担保额度",
        "annualizedInterest": "年化利率",
        "mortgageType": "抵押类型",
        "mortgageRate": "抵押率",
        "houseValue": "合同抵押物价值",
      }
    ]
  };

  //材料上传
  public materialInfo = {
    "materialInfoList": [
      {
        "materialTypeCode": "1",//材料类型代码
        "materialTypeName": "身份证",//材料类型名称
        "fileNum": "1",//文件个数 ===对应之前的fileSize
        "fileList": [
          {
            "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",//文件编码==对应之前的fileId
            "fileName": "身份证_f.jpg",//文件名称====对应之前的originalFileName
            "uploadTime": "2017-07-15 12:12:12",//上载时间
          }
        ]
      },
      {
        "materialTypeCode": "2",//材料类型代码
        "materialTypeName": "户口本",//材料类型名称
        "fileNum": "0",//文件个数 ===对应之前的fileSize
        "fileList": [
          {
            "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",//文件编码==对应之前的fileId
            "fileName": "身份证_f.jpg",//文件名称====对应之前的originalFileName
            "uploadTime": "2017-07-15 12:12:12",//上载时间
          }
        ]
      },
      {
        "materialTypeCode": "3",//材料类型代码
        "materialTypeName": "结婚证明",//材料类型名称
        "fileNum": "2",//文件个数 ===对应之前的fileSize
        "fileList": [
          {
            "fileCode": "07fc0013-5cb9-11e7-a91f-1866dae83f00",//文件编码==对应之前的fileId
            "fileName": "结婚证.jpg",//文件名称====对应之前的originalFileName
            "uploadTime": "2017-07-15 12:12:12",//上载时间
          },
          {
            "fileCode": "f5c49a88-4c3d-11e7-904c-1866dae83f00",//文件编码==对应之前的fileId
            "fileName": "QQ截图20170526112334.png",//文件名称====对应之前的originalFileName
            "uploadTime": "2017-07-15 12:12:12",//上载时间
          }
        ]
      },

    ]
  };

  //公正抵押
  public notarialAndMortgage = {
    "signTime": "签约公证时间",
    "signRemark": "签约公证备注",
    "registrationTime": "抵押时间",
    "registrationRemark": "抵押备注",
    "signFileList": [  //签约公正材料
      {
        "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",
        "fileName": "文件名称",
        "uploadTime": "上载时间",
      }
    ],
    "guaranteeFileList": [		//担保材料
      {
        "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",
        "fileName": "文件名称",
        "uploadTime": "上载时间",
      }
    ],
    "insuranceFileList": [		//保险材料
      {
        "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",
        "fileName": "文件名称",
        "uploadTime": "上载时间",
      }
    ],
    "mortgageFileList": [		//抵押材料
      {
        "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",
        "fileName": "文件名称",
        "uploadTime": "上载时间",
      }
    ],
    "investigationFileList": [		//产调查询材料
      {
        "fileCode": "efd2faa6-6c84-11e7-a91f-1866dae83f00",
        "fileName": "文件名称",
        "uploadTime": "上载时间",
      }
    ]

  }


  //贷款业务查询列表接口

  // QueryEntryList=() =>{
  //   var loginTokenKey = this.commonFunc.getloginToken();
  //   var userNo = this.user.getUserData().userNo;
  //
  //
  //   var Obj = {
  //     'head': {
  //       'functionNo': 'plms100010',
  //       'userNo': userNo,
  //       'clientTimestamp': loginTokenKey
  //     },
  //     'body': {
  //       'userNo': userNo,
  //
  //     }
  //   };
  //
  //
  //
  //   this.networkService.postData(Obj, false).then(res => {
  //
  //     if (res.retCode == true) {
  //       // 请求网络通信处理方法
  //
  //       if (this.networkService.onJudgeSuccessful(res)) {
  //
  //         console.log("88888888888888查询列表返回的数据：",res.data.body);
  //
  //         //登录成功处理逻辑
  //         var pages = this.commonFunc.handleNilString(res.data.body.pages);
  //         if (pages == '') pages = '0';
  //         var currentPage = this.commonFunc.handleNilString(res.data.body.currentPage);
  //         if (currentPage == '') currentPage = '0';
  //
  //         var objData = {
  //           'totalPage': pages,
  //           'currentPage': currentPage,
  //           'total': res.data.body.total
  //
  //         };
  //
  //         //设置页面显示 第几条至第几条
  //         this.totalListsCount = res.data.body.total;
  //
  //         //首次获取分页信息 设置分页插件
  //         //  if(this.currentItemLists.length == 0){
  //         this.setPageFunc(objData, this);
  //
  //         //  }
  //         this.currentItemLists = res.data.body.dataList;
  //         console.log(">>>>>>>>>>>55555555555", this.currentItemLists);
  //         this.setDisplayItemsCount();
  //
  //
  //         //修改查询列表状态 暂时前端映射 待需求确认之后 以后台为准
  //         //material 充材料为 1-不显示  2-显示
  //
  //         for (var i = 0; i < this.currentItemLists.length; i++) {
  //           var obj = this.currentItemLists[i];
  //           var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
  //           this.currentItemLists[i]["statusName"] = resultObj['statusName'];
  //           this.currentItemLists[i]["operate"] = resultObj['operate'];
  //           this.currentItemLists[i]["material"] = resultObj['material'];
  //
  //           this.currentItemLists[i]["supplementaryMateria"] = resultObj['supplementaryMateria'];
  //
  //
  //         }
  //
  //         this.totalItemLists[this.selectPageNo] = this.currentItemLists;
  //
  //
  //       }
  //     }
  //
  //   });
  //   return false;
  // }


}
