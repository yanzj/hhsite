/**
 *文件名: network.service.ts
 *创建人: zhuxz
 *日  期: 2017.05.11
 *修改人:
 *日  期:
 *描  述: 网络请求服务。对系统系统 get post 文件上传 文件下载 四种方式，并对请求公共头部分进行封装
 **/

import { Injectable }  from '@angular/core';
import { Headers, Http ,Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/toPromise';
//  引入FileUpload模块
import {FileUploader} from "ng2-file-upload";
import {User}  from "./User.service";
import {Router} from "@angular/router";

import {environment} from "../environments/environment";


declare var $: any;
@Injectable()
export class NetworkService {
  constructor(public http: Http,private user:User,private router:Router){
    this.http = http;
    this.user = user;
    this.router = router;

  }
  //设置http头
  private headers = new Headers(
    {
      'Content-Type': 'application/json',
    });


  //错误码
  /** 成功 **/
  public  SUCCESS_CODE = "0000";
  /** 系统异常 **/
  public  SYSTEM_EXCEPTION = "9999";
  public  REQUIRED_FIELD_MISSING = "9998";
//用户被锁定
  public USER_HAVE_LOCKED = "9001";
//用户名或密码错误，超过5次锁定
  public  USER_WRONG_PSD_AND_LOCKED = "9002";
//用户名或密码错误，还剩下N次机会
  public  USER_WRONG_PSD = "1002";
//用户不存在
  public USER_WRONG_USER_NO = "9004";
//两次密码输入不一致
  public  USER_DIFF_PASSWORDRESET = "9008";
  /** 登录状态异常（未登录或登录超时） **/
  public  SYSTEM_DISABLE_TOKEN = "1234";

  //本地测试地址
  public fileUploadUrl = environment.server + '/fileLoad/uploadFile.json';//文件上传
  private serverUrl = environment.server +'/backendapi.json';//进件本地调试地址
  private serverUrl2 = environment.server +'/backendapi.json';//询价本地调试地址

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  private showloading(){
    console.log('start loading http service');
  }

  private  hideloading(){
    console.log('hide loading http service')
  }
  //判断当前交易是否成功 true 返回成功 false 返回失败
  onJudgeSuccessful(dataOBJ:any){
    var result : boolean = dataOBJ.data.head.returnCode == '0000';
    if(result){
      return result;
    } else if (dataOBJ.data.head.returnCode == this.SYSTEM_EXCEPTION) {
      // alert('9999');
      this.popNextFailModal("系统出错","后台系统出错，提交申请失败，请联系系统管理员",false);

    }else if(dataOBJ.data.head.returnCode == this.SYSTEM_DISABLE_TOKEN){
      let start = window.location.href.indexOf("#");
      let s = window.location.href.substring(start);
      if(s == "" || s == "#" || s == "/" || s == "#/login" || s == "#/home/main"){
        this.user.clearLoginInfo();
        this.router.navigate(['/login']);
      }else if (dataOBJ.data.head.returnCode=='1006'){
      }else{
        this.popNextFailModal("登录超时","登录超时，请重新登录，5秒钟后自动跳转到登录页面",true,()=> {
          // this.user.setIsLogin(false);
          this.user.clearLoginInfo();
          this.router.navigate(['/login']);
        });
      }
    }

  }

  /**
   * 网络请求 post提交方式
   * requestData   请求参数 使用object对象 包含交易需要的参数信息
   * retErrorInfo  是否将网络异常返回上一层处理

   **/

  //添加登录的url
  postData(requestData:any ,retErrorInfo:boolean){

    //console.log(this.serverUrl);

    this.showloading();

    return this.http
      .post(this.serverUrl,JSON.stringify(requestData), {headers: this.headers})
      .toPromise()
      .then(res => {
        //网络通讯成功
        this.hideloading();
        let resultObj = res.json();

        let retObj = {
          'retCode':true,
          'data':resultObj
        };

        return retObj;})

      .catch(
        error => {
          this.hideloading();
          let errorObj = error.json();
          let retObj = {
            'retCode':false,
            'data':errorObj
          }

          if (retErrorInfo) {
            this.handleError;

          }else{
            return retObj;

          }
        });
  }

  postData2(requestData:any ,retErrorInfo:boolean){

    this.showloading();

    return this.http
      .post(this.serverUrl2,JSON.stringify(requestData), {headers: this.headers})
      .toPromise()
      .then(res => {
        //网络通讯成功
        this.hideloading();
        let resultObj = res.json();
        let retObj = {
          'retCode':true,
          'data':resultObj
        }
        console.log(resultObj);
        return retObj;})

      .catch(
        error => {
          this.hideloading();
          let errorObj = error.json();
          let retObj = {
            'retCode':false,
            'data':errorObj
          }

          if (retErrorInfo) {
            this.handleError;

          }else{
            return retObj;

          }
        });
  }

  //带有倒计时的弹窗 obj flag-true显示（确认/取消） false-确定
  popNextSuccessModal(tipTitle:string, tipContent:string,flag:boolean,fn?) {
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>秒钟后自动关闭</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton:flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      cancelButtonClickCallback:myCancelButtonClickCallback,
      closeImageClickCallback: myCancelButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      //当存在一家询价公司数据存在时，提交数据到后台
      if(flag){
        fn();
      }
      return true;
    }
    function myCancelButtonClickCallback(){
      clearInterval(reduceSecondValue);
      $(this).closeModal();
    }
    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();console.log(secondValue);
        if (1 == secondValue) {
          if(flag){
            fn();
          }
          $(this).closeModal();
          clearInterval(reduceSecondValue);
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }


  popNextFailModal(tipTitle:string, tipContent:string,flag:boolean,fn?) {
    var that = this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "。<span id='secondValue'>5</span>秒钟后自动关闭</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      closeImage: false,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback: myConfirmButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      //当存在一家询价公司数据存在时，提交数据到后台
      if(flag){
        fn();
      }
      return true;
    }

    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          $(this).closeModal();
          clearInterval(reduceSecondValue);
          if(flag){
            fn()
          }
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }

}




