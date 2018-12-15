import {Component, NgModule, OnInit, SimpleChange} from '@angular/core';
import { NetworkService } from '../../network.service';
import {commonService} from "../../commonService.service";

import {User} from "../../User.service";
import {Headers, Http, Response} from '@angular/http';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {ReloadTrueDirective} from "./reloadtrue.directive";

declare var $:any;
@Component({
  selector: 'app-com-section',
  templateUrl: './com-section.component.html',
  styleUrls: ['./com-section.component.css'],
  providers:[]
})

@NgModule({
  declarations:[ReloadTrueDirective]
})

export class ComSectionComponent implements OnInit {

  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router:Router,
    private routeInfo:ActivatedRoute,

    ) {}

    ll:string='11111';
  //定义变量
  messageCount;    //消息总数
  messageTipsList; //消息列表
  todoCount;       //待办总数
  todoTipsList     //待办列表
  unreadMessageSign:any[] = [];    //标记未读信息

   fullName:string = '';
   distributorName:string = '';

  //add by 谢之磊  根据角色动态加载菜单  begin
  menuList: any = this.user.getUserData().menuList;//从user中拿到菜单列表

  //处理展开情况下的菜单
  menuArrowDown:{} = {menu_arrow_down:true};
  menuArrowUp:{} = {menu_arrow_up:true};
  muneIconIndex:{} = {mune_icon_index:true};  //首页
  muneIconItem:{} = {mune_icon_item:true};    //循环产品管理
  muneIconEnquiry:{} = {mune_icon_enquiry:true}; //业务查询
  moneyManagement:{} = {money_management:true}; //资金管理
  operationManagement:{} = {operation_management:true}; //运营管理
  muneIconPersonCenter:{} = {mune_icon_personCenter:true}; //个人中心
  //一级菜单点方法
  currentLevelMenuId;//展开情况下选中的菜单id
  currentLevelMenuID=this.currentLevelMenuId;
  levelMenuClick(levelMenuId){
    this.currentLevelMenuId = levelMenuId;
    if (this.currentLevelMenuID!=this.currentLevelMenuId){
      this.currentLevelMenuID=this.currentLevelMenuId
    }else {
      this.currentLevelMenuID ='dddd';
      this.currentLevelMenuId ='2222'
    }
  }
  //二级菜单点击方法
  currentChildMenuId;//展开情况下选中的二级菜单id
  childMenuClick(childMenuId){
    this.currentChildMenuId = childMenuId;
  }
  childMenuMouseenter(childMenuId,$event){
    if(childMenuId != this.currentChildMenuId){
      $($event.target).addClass("menu-item-a-hover");
    }
  }
  childMenuMouseleave(childMenuId,$event){
  // if(childMenuId != this.currentChildMenuId){
    $($event.target).removeClass("menu-item-a-hover");
  // }
}

  //处理收缩情况下的菜单
  currentLevelMenuIdForClose;//展开情况下选中的菜单id
  sidebarIconI1:{} = {sidebar_icon_i1:true};
  sidebarIconI2:{} = {sidebar_icon_i2:true};
  sidebarIconI3:{} = {sidebar_icon_i3:true};
  sidebarIconI4:{} = {sidebar_icon_i4:true};
  sidebarIconI5:{} = {sidebar_icon_i5:true};
  sidebarIconI6:{} = {sidebar_icon_i6:true};
  levelMenuMouseenter(levelMenuId){
    this.currentLevelMenuIdForClose = levelMenuId;
  }

  //add by 谢之磊  根据角色动态加载菜单  end

  ngOnInit() {
    var userObj = this.user.getUserData();
    this.fullName = userObj.fullName;
    this.distributorName = userObj.distributorName;
    this.InitMessageList();  //消息的初始化
    // this.personCenterToCom();

    this.routeInfo.params.subscribe((params:Params)=>{

    })



    console.log('123123123123123',this.menuList);
  }

  ngAfterViewChecked(){
    this.setcurrentClasses();

  }

  enquiry(){
    this.router.navigate(['./home/enquiry'])
  }
  //退出登录start
  signOut(){
    var userObj = this.user.getUserData();
    var userNo= this.commonFunc.handleNilString(userObj.userNo);
    var loginTokenKey= this.commonFunc.getloginToken();
      var Obj = {
        'head': {
          'functionNo': 'plms100003',
          'userNo': userNo,
          'clientTimestamp': loginTokenKey
        },
        'body': {
          'userNo': userNo,

        }
      };
    this.networkService.postData(Obj, false).then(res => {});


    this.commonFunc.removeLoginToken();
    //清除当前用户信息和登录状态
    this.user.setIsLogin(false);
    this.user.clearLoginInfo();
    this.router.navigate(['/login']);

  }
//退出登录end

  //头部用户登录点击显示
  isShowtab:boolean=false;
  isheaderPerserTask:boolean=false;
  isheaderPerserInfo:boolean=false;
  isheaderPerserInfoPrimary:boolean;
  isPersonCenterPrimary:boolean;

  toggleTab(event){
    this.isShowtab = !this.isShowtab;
    this.isheaderPerserInfo=false;
    this.isheaderPerserTask=false;
    event.stopPropagation();
  }
  task() {
    this.isShowtab=false;
    this.isheaderPerserTask=false;
    this.isheaderPerserInfo=false;
    this.isEnquiryShowBox=false;
    this.isShowBox=false;
    this.isShowPersonBox=false;
    event.stopPropagation();
    //头部改变的时候   更改消息的显示状态
    if(this.isheaderPerserInfo==false){
      if(this.isheaderPerserInfoPrimary==true){
        this.isheaderPerserInfoPrimary=false;
        this.getUnreadAgain();
      }
    }
    //侧边栏改变的时候  更改消息的显示状态

  }

  //将没有的读的消息发给后台   然后重新获得没有读的消息
  getUnreadAgain(){
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var messageObj = {
      'head': {
        'functionNo': 'HH000019',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        serialNoList:this.unreadMessageSign
      }
    };
    this.networkService.postData(messageObj, false).then(res => {
      // 请求网络通信处理方法
      if (this.networkService.onJudgeSuccessful(res)) {
        this.InitMessageList();
      };

    });
  }
  //
  // ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
  //   for (let propName in changes) {
  //     let changedProp = changes[propName];
  //     if (changedProp.isFirstChange()) {
  //       console.log(window.location.hash);
  //     } else {
  //       console.log(window.location.hash);
  //     }
  //   }
  // }

  //头部待办任务的点击显示
  isheaderPerserTaskFunction(){
    this.isheaderPerserTask = !this.isheaderPerserTask;
    this.isheaderPerserInfo=false;
    this.isShowtab=false;
    event.stopPropagation();
  }
  //头部消息的点击显示
  isheaderPerserInfoFunction(){
    this.isheaderPerserInfo = !this.isheaderPerserInfo;
    this.isheaderPerserInfoPrimary=this.isheaderPerserInfo;
     this.isheaderPerserTask=false;
     this.isShowtab=false;
    event.stopPropagation();

    for(let i=0;i<this.messageTipsList.length;i++){
      if(this.messageTipsList[i].status==0){
          var serialNo={};
          serialNo['serialNo']=this.messageTipsList[i].serialNo;
          this.unreadMessageSign.push(serialNo);
        }
    }
    if(this.isheaderPerserInfo==false){
      // if(this.isheaderPerserInfoPrimary==true){
        var loginTokenKey = this.commonFunc.getloginToken();
        var userNo = this.user.getUserData().userNo;
        var messageObj = {
          'head': {
            'functionNo': 'HH000019',
            'userNo': userNo,
            'clientTimestamp': loginTokenKey
          },
          'body': {
            serialNoList:this.unreadMessageSign
          }
        };
        this.networkService.postData(messageObj, false).then(res => {
          // 请求网络通信处理方法
          if (this.networkService.onJudgeSuccessful(res)) {
            this.InitMessageList();
          };

        });
      // }
    }
  }


  //左侧菜单点击菜单切换isPersonCenter
  //点击侧边栏切换宽窄按钮的宽按钮（侧边栏显示200px）的方法
  isShow:boolean=true;
  currentClasses:{};
  currentClasses2:{}
  currentClasses3:{}
  toggle(){
    this.isShow = !this.isShow;
    this.isShowBox=false;
    this.isInto=false;
    this.isEnquiryShowBox=false;
    this.isPersonCenter=false;
    // this.personCenterToCom();
  }

  //（侧边栏显示200px）"进件管理"点击事件
  isInto:boolean=false;
  isIntoEnquiry:boolean=false;
  isPersonCenter:boolean=false;


  into(){
    this.isInto=!this.isInto;
    this.isIntoEnquiry=false;
    this.isPersonCenter=false;
    // this.personCenterToCom();
  }

  //（侧边栏显示200px）"询价管理"点击事件
  intoEnquiry(){

    //点击 （侧边栏显示200px）点击"进件管理" "询价管理"列表收起
    this.isIntoEnquiry=!this.isIntoEnquiry;
    this.isInto=false;
    this.isPersonCenter=false;
    // this.personCenterToCom();
  }

  //（侧边栏显示200px）"个人中心" 点击事件
  intoPersonCenter(){
     this.isPersonCenter=!this.isPersonCenter;
     this.isInto=false;
     this.isIntoEnquiry=false;
     this.isPersonCenterPrimary = this.isPersonCenter;
     // this.personCenterToCom();
  }


  setcurrentClasses(){
      this.currentClasses =  {
        menu_arrow_down: this.isIntoEnquiry,
        menu_arrow_up: !this.isIntoEnquiry
      };

      this.currentClasses2 =  {
          menu_arrow_down:this.isInto,
          menu_arrow_up:  !this.isInto
      };

      this.currentClasses3 =  {
        menu_arrow_down:this.isPersonCenter,
        menu_arrow_up:  !this.isPersonCenter
      };

  }


  //（侧边栏显示60px）的定义变量
   isShowBox:boolean=false;
   isHideBox:boolean=false;
   isEnquiryShowBox:boolean=false;
   isShowPersonBox:boolean=false;


  //（侧边栏显示60px）点击“切换图标”方法
  Toggle_part(){
    this.isHideBox=!this.isHideBox;
    this.isShowBox=false;
    this.isEnquiryShowBox=false;
    this.isShowPersonBox=false;
      event.stopPropagation();
  }

  //（侧边栏显示60px）点击“询价管理”方法
  Toggle_box1(){
    this.isEnquiryShowBox=!this.isEnquiryShowBox;
    this.isShowBox=false;
    this.isHideBox=false;
    this.isShowPersonBox=false;
    event.stopPropagation();
  }

  //（侧边栏显示60px）点击“进件管理”方法
  Toggle_box2(){
    this.isShowBox=!this.isShowBox;
    this.isHideBox=false;
    this.isEnquiryShowBox=false;
    this.isShowPersonBox=false;
    event.stopPropagation();

  }

  //（侧边栏显示60px）点击“个人中心”方法
  Toggle_box3(){
    this.isShowPersonBox=!this.isShowPersonBox;
    this.isShowBox=false;
    this.isHideBox=false;
    this.isEnquiryShowBox=false;
    event.stopPropagation();
  }

  //头部消息和待办的接口
  InitMessageList(){
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;
    var Obj = {
      'head': {
        'functionNo': 'plms100004',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
      }
    };
    this.networkService.postData(Obj, false).then(res => {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {

          var returnObjData=res.data.body;


          this.messageCount=returnObjData.messageCount;
          this.messageTipsList=returnObjData.messageTipsList;
          this.todoCount=returnObjData.todoCount;
          this.todoTipsList=returnObjData.todoTipsList
          //判读消息列表的链接  显示在头部的列表（<=4）


          for(let i=0;i<this.messageTipsList.length;i++){
            var internalParam=JSON.parse(this.messageTipsList[i].internalParam);
            var messageContent=this.messageTipsList[i].content;
            var messageKeyWords=internalParam.keyWords;
            var messageMsgType=internalParam.msgType;
            if(messageMsgType=='001'){
              var messageLink=`<a href="./#/home/enquiryResubmit"  class="text-link">${messageKeyWords}</a>`
              messageContent=messageContent.replace(messageKeyWords,messageLink)
            }else if(messageMsgType=='002'){
               var messageLink=`<a href="./#/home/enquiryDetail" class="text-link">${messageKeyWords}</a>`
              messageContent=messageContent.replace(messageKeyWords,messageLink)
            }else if(messageMsgType=='003'){

            }

           this.messageTipsList[i].content=messageContent

          };
          //代办任务列表的链接  显示在头部的列表（<=4）
          for(let i=0;i<this.todoTipsList.length;i++){
            var content=this.todoTipsList[i].content;
            var todoKeyWords=this.todoTipsList[i].keyWords;

            let keyWordsIndex = content.indexOf(todoKeyWords);
            let leftContent = content.substring(0,keyWordsIndex);
            let rightContent = content.substring(keyWordsIndex + todoKeyWords.length);

            this.todoTipsList[i].leftContent=leftContent;
            this.todoTipsList[i].rightContent=rightContent;
          }

      };


    });

  }
 // 侧边栏点击后改变
  personCenterToCom(){
      var changedMess=window.sessionStorage.getItem("unreadMessageChange");
      if(changedMess=="changed"){
        window.sessionStorage.removeItem("unreadMessageChange");
        this.InitMessageList();
      }
  }

 //待办和消息的弹窗查看连接
 checkAlltask(){
  this.router.navigate(['/home/gtasks'])
 }
 checkAllmessage(){
  this.router.navigate(['/home/message'])
 }

  //消息的链接带serilNo过去，否则找不到这个属性，会报错
  mesgGotoOtherPage(itemObj){
    this.commonFunc.setBusinessData(itemObj);
  }


}
