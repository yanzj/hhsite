import { Injectable } from '@angular/core';
 @Injectable()
export class User {
	private userDataKey	 = "userDataKey";
	private isLoginKey:string = "loginStatuKey";//判断当前是否是登录状态，true-登录  false-未登录
  private isFirstLoginKey:string = "firstLoginKey";

	setIsLogin(login:boolean){
		let objStr = login ? "1":"2";//登录状态 存储1 非登录装填存储2
		window.localStorage.setItem(this.isLoginKey, objStr);
	}

	getIsLogin(){
		let objStr = window.localStorage.getItem(this.isLoginKey);
		return objStr == '1';

	}

	//设置是否首次登录
	setIsFirstLogin(firstLogin:boolean) {
	  let objStr = firstLogin?"Y":"N";
	  window.localStorage.setItem(this.isFirstLoginKey, objStr);
  }

  //获取是否首次登录信息
   getIsFirstLogin() {
	  let objStr = window.localStorage.getItem(this.isFirstLoginKey);
	  return objStr == 'Y';
   }

	//设置获取用户信息
	setUserData(user:Object){
		let objStr = JSON.stringify(user);
		window.localStorage.setItem(this.userDataKey, objStr);
	}

	getUserData(){
		let obj = window.localStorage.getItem(this.userDataKey);
		 return  JSON.parse(obj);
	}
	removeUserData(){
		window.localStorage.removeItem(this.userDataKey);
	}

}
