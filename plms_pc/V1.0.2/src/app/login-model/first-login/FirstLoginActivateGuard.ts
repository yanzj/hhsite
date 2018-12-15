
/**
 * Created by xiezhilei on 2017/7/3.
 * zne
 */

import {CanActivate} from "@angular/router";
import {Injectable, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {User} from "../../User.service";

@Injectable()
export class FirstLoginActivateGuard implements CanActivate,OnInit {

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  constructor(private router:Router,
              private user: User){

  }

  canActivate(){
    if(this.user.getIsLogin()){
      if(this.user.getIsFirstLogin()){
        // console.log("是首次登录，放行.");
        return true;
      }else{
        // console.log("非首次登录，直接到主页.");
        this.router.navigate(['/home/main']);
        return false;
      }
    }else{
      this.router.navigate(['/login']);
      return false;
    }
  }

}

