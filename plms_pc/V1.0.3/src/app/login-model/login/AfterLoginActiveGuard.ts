
/**
 * Created by xiezhilei on 2017/7/3.
 * zne
 */

import {CanActivate} from "@angular/router";
import {Injectable, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {User} from "../../User.service";

@Injectable()
export class AfterLoginActiveGuard implements CanActivate,OnInit {

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  constructor(private router:Router,
              private user: User){

  }

  canActivate(){
    if(this.user.getIsLogin()){

      //如果是首次登录，无论输入什么都应该到首次登录页面
      if(this.user.getIsFirstLogin()){
        this.router.navigate(['/firstLogin']);
        return false;
      }

      return true;
    }else{
      this.router.navigate(['/login']);
      return false;
    }
  }

}

