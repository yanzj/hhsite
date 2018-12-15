import { Component, OnInit } from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {withIdentifier} from "codelyzer/util/astQuery";

declare var $:any;
@Component({
  selector: 'app-first-login',
  templateUrl: './first-login.component.html',
  styleUrls: ['./first-login.component.css']
})
export class FirstLoginComponent implements OnInit {

  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router:Router
  ) { }

  //第一次登录界面用到的变量
  private original:boolean;
  private new:boolean;
  private confirm:boolean;

  //第一次登录时，页面的保存
  save(){
    $(".psw-save").attr("disabled", true);
    //原密码新密码没填的出现相应的红框提示
    if(''==$(".original_word").val().trim() || ''==$(".new_word").val().trim() || ''==$(".confirm_word").val().trim()){
      this.original=<boolean>(''==$(".original_word").val().trim());
      this.new=<boolean>(''==$(".new_word").val().trim());
      this.confirm=<boolean>(''==$(".confirm_word").val().trim());
      if(this.original){
        $(".original_word").removeClass("default-input").addClass("error-input");
      }
      if(this.new){
        $(".new_word").removeClass("default-input").addClass("error-input");
      }
      if(this.confirm){
        $(".confirm_word").removeClass("default-input").addClass("error-input");
      }
      $("#warn_msg").html("原密码和新密码不能为空，请重新输入").show();
      $(".psw-save").attr("disabled", false);
      return false;
    }
    //新密码与原始密码不一致，红框出现在确认密码的框
    if($(".new_word").val()!=$(".confirm_word").val()){
      this.confirm=true;
      $("#warn_msg").html("两次密码不一致").show();
      $(".confirm_word").removeClass("default-input").addClass("error-input");
      $(".psw-save").attr("disabled", false);
      return false;
    }
    //  两次输入密码一致，但是不符合密码要求 提示“新密码至少6位字符，请重新输入”
    if($(".new_word").val()==$(".confirm_word").val()){
      if($(".new_word").val().length<6){
        $("#warn_msg").html("新密码至少6位字符，请重新输入").show();
        $(".new_word").removeClass("default-input").addClass("error-input");
        $(".confirm_word").removeClass("default-input").addClass("error-input");
        $(".psw-save").attr("disabled", false);
        return false;
      }
    }
    //获取手机号，密码
    var phoneNum = $("#telPhone").val();
    var password = $(".password").val();
    var newPassword=$(".newPassword").val();
    var newPasswordAgain=$(".newPasswordAgain").val();
    //用md5进行加密
    password=$.md5(password);
    newPassword=$.md5(newPassword);
    newPasswordAgain=$.md5(newPasswordAgain);

    //获得时间戳
    // var timeStamp = this.commonFunc.getCurrentTimeStamp();

    var timeStamp=this.commonFunc.getloginToken();

    var userNo=window.localStorage.getItem("userNo");
    // var userNO = this.user.getUserData();

    //phoneNum是手机号  password
    var Obj = {
      'head': {
        'functionNo': 'HH000002',
        'userNo': userNo,
        'clientTimestamp': timeStamp
      },
      'body': {
        'userName': userNo,
        'userNo':userNo,
        'password': password,
        'newPassword':newPassword,
        'newPasswordAgain':newPasswordAgain
      }
    };

    this.networkService.postData(Obj, false).then(res => {
      $(".psw-save").attr("disabled", false);
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //当用户名不存在时
        if(res.data.head.returnCode==this.networkService.USER_WRONG_USER_NO){
          $("#warn_msg").html(res.data.head.returnMessage).show();
          // this.router.navigate(['/firstLogin']);
        }
        //当输入的密码不正确的时候
        if(res.data.head.returnCode==this.networkService.USER_WRONG_PSD){
          $("#warn_msg").html(res.data.head.returnMessage).show();
          $(".original_word").css({"borderColor":"#e00"}).removeClass("default-input").addClass("error-input");
        }
        //当账户被锁住的时候
        if(res.data.head.returnCode==this.networkService.USER_WRONG_PSD_AND_LOCKED){
          $("#warn_msg").html(res.data.head.returnMessage).show();
          $(".original_word").css({"borderColor":"#e00"}).removeClass("default-input").addClass("error-input");
        }
        if(res.data.head.returnCode==this.networkService.USER_HAVE_LOCKED){
          $("#warn_msg").html(res.data.head.returnMessage).show();
        }

        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑

          //首次登录成功后，变更首次登录属性
          this.user.setIsFirstLogin(false);

          //跳转至主页
          this.router.navigate(['/home/main']);

        }


      } else {
        //请求失败处理方法


      }

    });
  }
  ngOnInit() {
    var pg=Math.floor(1+Math.random()*4);
    $(".bg").css("background","url(assets/img/login/bg"+pg+".jpg) 0/cover fixed");
    var phoneNum=window.localStorage.getItem("phoneNum");
    $("#telPhone").val(phoneNum);

    $(".original_word").focus(function () {
      $(".original_word").removeClass("error-input").addClass('focus-input');
    })
    $(".new_word").focus(function () {
      $(".new_word").removeClass("error-input").addClass('focus-input');
    })
    $(".confirm_word").focus(function () {
      $(".confirm_word").removeClass("error-input").addClass('focus-input');
    })
  }

}
