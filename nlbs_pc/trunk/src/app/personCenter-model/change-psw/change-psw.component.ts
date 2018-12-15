import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {User} from "../../User.service";
import {changepswService} from "./change-psw.service";
declare var $: any;
@Component({
  selector: 'app-change-psw',
  templateUrl: './change-psw.component.html',
  styleUrls: ['./change-psw.component.css'],
  providers: [changepswService]

})
export class ChangePswComponent implements OnInit {
  public primaryPswValue:string;
  public newPswValue:string;
  public confirmPswValue:string;
  public mobile:string;//手机号
  public fullName:string;//用户名
  public distributorName:string;//公司名
  public userNo:string;//给后台的useNo

  constructor(private networkService: NetworkService,
              private commonFunc: commonService,
              private router: Router,
              private user: User,
              private changepsw:changepswService,
  ) {
  }

  ngOnInit() {
    this.initData();
    this.checkFormateFunc();
    //当新密码  和确认密码失去焦点的时候  需要校验
    $("#newPsw").blur(()=> {
      this.accordanceFunc();
    });
    $("#confirmPsw").blur(()=>{
      this.accordanceFunc();
    });

  }

  initData(){
    //获取用户的信息
    var userObj = this.user.getUserData();
    this.mobile=userObj.mobile;
    this.fullName=userObj.fullName;
    this.distributorName=userObj.distributorName;
    this.userNo=userObj.userNo;
  }

//  设置校验格式的方法
  checkFormateFunc(){
  //  校验格式
    this.commonFunc.ValidateId("primaryPsw", this.commonFunc.Validates.checkNull, 'bottom',3, "不能为空");
    this.commonFunc.ValidateId("confirmPsw", this.commonFunc.Validates.checkNull, 'bottom',3, "不能为空");
    this.changepsw.ValidateId("newPsw",this.changepsw.Validates.checkLength,'bottom','3','格式错误');
  }

//  当新密码不为空，确认密码也不为空的时候
// 校验新密码和确认密码是否一致  如果不一致的话，则在确认密码的地方显示 两次密码是否一致
  accordanceFunc(){
  //  判断新密码是否为空
    var newPswIsNull=this.changepsw.isEmpty('newPsw');//如果是空的话  为true
  //  判断确认密码是否为空
    var confirmPswIsNull=this.changepsw.isEmpty('confirmPsw');//如果是空的话 为true

    if(newPswIsNull||confirmPswIsNull){
      //其中有一个是空的，则返回
      return ;
    }else {
      //都不为空，校验两次密码是否一致
      if(this.newPswValue!=this.confirmPswValue){
        $("#confirmPsw").createTip({
          margainLeft: 3,
          errorText: "两次密码不一致",
          errorPosition: 'bottom',//参数包括right,top-right,top-left
          errorDisplay:'show'//参数包括hide,show
        });
        return false;
      }else {
        $("#confirmPsw").removeTip();
        return true;
      }
    }

  }
  //点击保存时
  // 1.如果前端有校验错误的话  则会报密码信息错误
  // 2，如果前端没有校验错误，后台返回原密码错误  则提示原密码错误的情况
  // 3,如果修改密码正确，则弹框提示 修改密码成功
  conserve(){
  //  校验之前将原来的删掉
    $("#primaryPsw").removeTip();
    $("#confirmPsw").removeTip();
    $("#newPsw").removeTip();
  //  1.前端校验
    var primaryPswValue =this.commonFunc.Validates.checkNull("primaryPsw","bottom",3,"不能为空");
    var confirmPswValue =this.commonFunc.Validates.checkNull("confirmPsw","bottom",3,"不能为空");
    var newPswValue     =this.changepsw.Validates.checkLength("newPsw","bottom",3,"格式错误");
  //两次密码不一致时也不发送请求
    var accordanceFunc = this.accordanceFunc();
  //  用来判断是否全部通过的标识
    var allCheckValue = true;
    allCheckValue = primaryPswValue&&confirmPswValue&&newPswValue&&accordanceFunc;
  //  如果有一个不通过的话  则弹窗提示密码信息错误
    if(!allCheckValue){
      this.networkService.popNextSuccessModal("密码信息错误","密码信息存在错误，请修改",false);
      return false;
    }

  // 2. 向后台发送请求，如果原密码错误 弹出原密码错误提示框

    //用md5进行加密 原密码
    var primaryValue=$.md5(this.primaryPswValue);
    var newValue=$.md5(this.newPswValue);
    var confirmValue=$.md5(this.confirmPswValue);
    //给后台的数据


    //获得时间戳
    var timeStamp=this.commonFunc.getloginToken();
    var userNo=window.localStorage.getItem("userNo");
    console.log(timeStamp);
    //phoneNum是手机号  password
    var Obj = {
      'head': {
        'functionNo': 'HH000002',
        'userNo': this.userNo,
        'clientTimestamp': timeStamp
      },
      'body': {
        'userName': this.userNo,
        'userNo':this.userNo,
        'password': primaryValue,
        'newPassword': newValue,
        'newPasswordAgain':confirmValue
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        console.log(res);
        //1 用户名或密码错误，还剩下N次机会
        if(res.data.head.returnCode==this.networkService.USER_WRONG_PSD){
          //  截取后面的还剩几次机会
          var messageArray =res.data.head.returnMessage.split("，");
          //弹窗提示还剩几次锁定
          this.networkService.popNextSuccessModal("原密码错误","原密码错误，请修改。"+messageArray[1],false);
          //给原密码错误提示样式
          this.changepsw.Validates.createError("primaryPsw",'bottom','3','原密码错误');
          return false;
        }
        //2 如果已经被锁定的话，弹窗
        if(res.data.head.returnCode==this.networkService.USER_WRONG_PSD_AND_LOCKED){
          this.networkService.popNextSuccessModal("账户已被锁定","账户被锁定，请与管理员联系",false);
          return false;
        }
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {
          this.networkService.popNextFailModal("修改密码成功","密码已修改，5秒钟后或点击确定转入登录页面",true,()=> {
            this.user.setIsLogin(false);
            this.router.navigate(['/login']);
          })
        }
      } else {
        //请求失败处理方法
      }

    });
  }
  //
  back(){
    this.router.navigate(['/home/main'])
  }
}
