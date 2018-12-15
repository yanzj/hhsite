import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {ComSectionComponent} from "../../common-model/com-section/com-section.component";

declare var $: any;
declare var laydate: any;

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  currentItemLists: any[] = [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '10';//分页 每页请求个数
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前介绍页数
  messageCount:number=0;
  unreadMessage: any[] = [];  //标记未读信息

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private comSectionComponent: ComSectionComponent) {
  }

  ngOnInit() {
    this.comSectionComponent.currentLevelMenuId = '2017070315180000000009';
    this.comSectionComponent.currentChildMenuId = '2017070315180000000011';
    //分页查询接口
    this.QueryEntryList();
  }





 //搜索按钮点击事件
  onSearchBtnClick() {
    //显示当前选中是第一页
    this.selectPageNo='1';
    this.QueryEntryList();
  }



  //分页查询列表接口
  QueryEntryList() {
    this.unreadMessage=[];
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var searchConditions = this.getSearchCondigtion();
    var Obj = {
      'head': {
        'functionNo': 'HH000017',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'content': searchConditions.content,
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo
      }
    };

    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
            console.log('返回的数据',res)
          var pages = res.data.body.pages;
          if (pages == '') pages = '0';
          var currentPage = res.data.body.currentPage;
          if (currentPage == '') currentPage = '0';

          var objData = {
            'totalPage': pages,
            'currentPage': currentPage,
            'total': res.data.body.total

          };
          //设置页面显示 第几条至第几条
          this.totalListsCount = res.data.body.total;
          //首次获取分页信息 设置分页插件
          this.setPageFunc(objData, this);
          this.currentItemLists = res.data.body.messageList;
          this.messageCount= res.data.body.messageCount
          //判读消息列表的链接
          for(let i=0;i<this.currentItemLists.length;i++){
            var internalParam=JSON.parse(this.currentItemLists[i].internalParam);
            var messageContent=this.currentItemLists[i].content;
            var messageKeyWords=internalParam.keyWords;
            var messageMsgType=internalParam.msgType;
            if(messageMsgType=='001'){
              var messageLink=`<a href="./#/home/enquiryResubmit"  class="text-link">${messageKeyWords}</a>`
              messageContent=messageContent.replace(messageKeyWords,messageLink)
              this.commonFunc.setResubmitStatus("message");
            }else if(messageMsgType=='002'){
               var messageLink=`<a href="./#/home/enquiryDetail" class="text-link">${messageKeyWords}</a>`
              messageContent=messageContent.replace(messageKeyWords,messageLink)
            }else if(messageMsgType=='003'){
            }

            this.currentItemLists[i].content=messageContent

            if(this.currentItemLists[i].status==0){
                var serialNo={};
                serialNo['serialNo']=this.currentItemLists[i].serialNo;
                this.unreadMessage.push(serialNo);
            }
          };
          console.log(this.unreadMessage);
          this.comSectionComponent.messageCount = this.comSectionComponent.messageCount - this.unreadMessage.length;
          //第二个接口的传参
          // for(let i=0;i<this.currentItemLists.length;i++){

          // };

          this.setDisplayItemsCount();
          this.totalItemLists[this.selectPageNo] = this.currentItemLists;
        }

        //调用第二个接口（让后台改变消息的是否已读状态）
        if(this.messageCount!=0){
          var messageObj = {
            'head': {
              'functionNo': 'HH000019',
              'userNo': userNo,
              'clientTimestamp': loginTokenKey
            },
            'body': {
              serialNoList:this.unreadMessage

            }
          };

          this.networkService.postData(messageObj, false).then(res => {
            // 请求网络通信处理方法
            if (this.networkService.onJudgeSuccessful(res)) {
              window.sessionStorage.setItem("unreadMessageChange","changed");
            }
          });
        }



      }
    });

  }

    //消息的链接带serilNo过去，否则找不到这个属性，会报错
  gotoOtherPage(itemObj){
    this.commonFunc.setBusinessData(itemObj);
  }
  //分页数据处理
  setCurrentDataFunc(page: string) {
    //设置当前选中页数
    this.selectPageNo = page;

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


  }
   //分页查询前 获取当前选中的查询条件
  getSearchCondigtion() {

    //内容的值
    var content = $("#content").val();
    content = this.commonFunc.handleNilString(content);//消息内容
    return {
      "content": content
    };
  }

 // 设置页面显示 当前消息条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    //var currentObj:any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.currentItemLists.length);
    if(this.endListIndex == '0'){
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



}
