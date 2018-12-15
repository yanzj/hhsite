import {Component, OnInit, Input} from '@angular/core';
import {NetworkService} from '../../network.service';
import {FileUploader} from "ng2-file-upload";
import {commonService} from "../../commonService.service";
import {User} from "../../User.service";
import {Headers, Http, Response} from '@angular/http';
import {Router} from "@angular/router";


declare var $: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['login.component.css'],
  providers: []
})
export class LoginComponent implements OnInit {
  //登录界面用到的变量
  private isPhone: boolean;
  private isLock: boolean;

  //isRemeber 是否记住登录名
  private isRemeber: boolean = false;

  //背景图片的地址
  imgSrc: string;

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router) {
  }

  check() {
    //分为是否存到locallStorange
    if (window.localStorage.getItem("flag")) {
      this.isRemeber = <boolean>(window.localStorage.getItem("flag") == 'true')
    }
    this.isRemeber = !this.isRemeber;
    // this.isRemeber=<boolean>(window.localStorage.getItem("flag"));
    window.localStorage.setItem("flag", this.isRemeber + '');
    if (this.isRemeber) {
      $("#checkImg").css(
        "background", "url('assets/img/login.png') -177px -10px"
      );
    } else {
      $("#checkImg").css(
        "background", "url('assets/img/login.png') -191px -10px"
      );
    }

  }

  // 不是第一次登录
  submit() {
    $('.logo-submit').attr("disabled", true);
    this.isLock = false;
    this.isPhone = false;
    //定义手机的正则表达式
    var myreg = /^((13|14|15|17|18)+\d{9})$/;

    $("#errorMsg").hide();
    if ('' == $("#q_num").val().trim() && '' == $(".q_pass").val().trim()) {
      this.isPhone = true;
      this.isLock = true;
      $("#errorMsg").html("手机号和密码不能为空，请重新输入").show();
      $("#q_num").removeClass("default-input").addClass("error-input");
      $(".q_pass").removeClass("default-input").addClass("error-input");
      $('.logo-submit').attr("disabled", false);
      return false;

    }
    if ('' == $("#q_num").val().trim()) {
      this.isPhone = true;
      this.isLock = false;
      $("#errorMsg").html("手机号不能为空，请重新输入").show();
      $("#q_num").removeClass("default-input").addClass("error-input");
      $('.logo-submit').attr("disabled", false);
      return false;
    }
    if ('' == $(".q_pass").val().trim()) {
      this.isLock = true;
      $("#errorMsg").html("密码不能为空，请重新输入").show();
      $(".q_pass").removeClass("default-input").addClass("error-input");
      $('.logo-submit').attr("disabled", false);
      return false;
    }
    if (!myreg.test($("#q_num").val())) {
      $("#errorMsg").html("手机号错误，请重新输入").show();
      $("#q_num").removeClass("default-input").addClass("error-input");
      $('.logo-submit').attr("disabled", false);
      return false;
    }

    if (window.localStorage.getItem("flag") == "true") {
      var phoneNum = $("#q_num").val();
      window.localStorage.setItem("phoneNum", phoneNum);
    } else {
    }

    var phoneNum = $("#q_num").val();
    var password = $(".q_pass").val();

    window.localStorage.setItem("phoneNum", phoneNum);
    password = $.md5(password);

    var timeStamp = this.commonFunc.getCurrentTimeStamp();
    // window.localStorage.setItem("clientTimestamp",timeStamp);
    this.commonFunc.setloginToken(timeStamp);

    //phoneNum是手机号  password
    var Obj = {
      'head': {
        'functionNo': 'HH000001',
        'userNo': '',
        'clientTimestamp': timeStamp
      },
      'body': {
        'userName': phoneNum,
        'password': password,
        'clientTimestamp': timeStamp
      }
    };


    this.networkService.postData(Obj, false).then(res => {
      $('.logo-submit').attr("disabled", false);
      if (res.retCode == true) {
        // 请求网络通信处理方法
        //res 是后台返回来的数据
        //当用户名不存在时
        if (res.data.head.returnCode == this.networkService.USER_WRONG_USER_NO) {
          $("#errorMsg").html(res.data.head.returnMessage).show();

        }
        //当输入的密码不正确的时候
        if (res.data.head.returnCode == this.networkService.USER_WRONG_PSD) {
          $("#errorMsg").html(res.data.head.returnMessage).show();
          //当密码错误时，input框显示错误样式
          $("#q_num").removeClass("default-input").addClass("error-input");
          $(".q_pass").removeClass("default-input").addClass("error-input");

        }
        //当账户被锁住的时候
        if (res.data.head.returnCode == this.networkService.USER_WRONG_PSD_AND_LOCKED) {
          $("#errorMsg").html(res.data.head.returnMessage).show();
        }
        if (res.data.head.returnCode == this.networkService.USER_HAVE_LOCKED) {
          $("#errorMsg").html(res.data.head.returnMessage).show();
        }

        //当用户时首次登录的时候  跳转
        if (this.networkService.onJudgeSuccessful(res)) {
          //登录成功处理逻辑
          var userDataObj: any = res.data.body;
          var headDataObj: any = res.data.head;
          //存储userNo
          var userNo = userDataObj.userNo;
          window.localStorage.setItem("userNo", userNo);
          // 记录时间戳
          var loginTimeStamp = headDataObj.clientTimestamp;
          //当返回0000时再进行正常的逻辑


          // 当登录成功 并未返回正常时间戳时  处理逻辑
          if (this.commonFunc.onJudgeStringIsNull(loginTimeStamp)) {
            this.commonFunc.setloginToken(loginTimeStamp);
          }

          // 储存用户个人信息
          this.user.setUserData(userDataObj);
          //记录当前有登录状态
          this.user.setIsLogin(true);


          //当用户登录时 判断用户是否第一次登录
          if (res.data.body.firstLogin == "1") {
            this.user.setIsFirstLogin(true);
            this.router.navigate(['/firstLogin']);

          } else {
            this.user.setIsFirstLogin(false);
            //跳转至主页
            this.router.navigate(['/home/main']);
          }


        }

        // else if (res.data.head.returnCode == this.networkService.SYSTEM_EXCEPTION) {
        //   // alert('9999');
        //   this.networkService.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);
        //
        // } else if (res.data.head.returnCode == this.networkService.REQUIRED_FIELD_MISSING) {
        //   alert('9998')
        // }else if(res.data.head.returnCode == this.networkService.SYSTEM_DISABLE_TOKEN){
        //   // alert('token失效');
        //   this.networkService.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,function () {
        //     this.router.navigate(['/login']);
        //   })
        // }

      } else {
        //请求失败处理方法

      }

    });
  }

  ngOnInit() {
    $(".q_pass").val("");

    if (window.localStorage.getItem("flag") == "true") {
      $("#checkImg").css(
        "background", "url('assets/img/login.png') -177px -10px"
      );
      var phoneNum = window.localStorage.getItem("phoneNum");
      $("#q_num").val(phoneNum);

    } else {
      $("#checkImg").css(
        "background", "url('assets/img/login.png') -191px -10px"
      );
    }
    var pg = Math.floor(1 + Math.random() * 4);
    $(".bg").css("background", "url(assets/img/login/bg" + pg + ".jpg) 0/cover fixed");
    $("#q_num").focus(function () {
      $("#q_num").removeClass("error-input").addClass('focus-input');
    });

    $(".q_pass").focus(function () {
      $(".q_pass").removeClass("error-input").addClass('focus-input');
    });


    $(".q_pass").blur(function () {
      $(".q_pass").removeClass("focus-input").addClass('default-input');
    });
    $("#q_num").blur(function () {
      $("#q_num").removeClass("focus-input").addClass('default-input');
    });


    //判断当前是否直接进入主页面
    this.onLoginViewBeforeCheckLoginAccount();


  }


  //判断当前是否有存在登录用户 如果存在则直接跳转至home页面
  onLoginViewBeforeCheckLoginAccount() {
    var loginFlag = this.user.getIsLogin();
    if (loginFlag) {
      this.router.navigate(['/home']);

    }
  }


}
