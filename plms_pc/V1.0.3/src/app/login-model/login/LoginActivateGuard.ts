
/**
 * Created by xiezhilei on 2017/7/3.
 * zne
 */

import {CanActivate} from "@angular/router";
import {Injectable, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {User} from "../../User.service";

@Injectable()
export class LoginActivateGuard implements CanActivate,OnInit {

  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  constructor(private router:Router,
              private user: User){

  }

  canActivate(){
    if(this.user.getIsLogin()){
      this.router.navigate(['/home/main']);
      return false;
    }else{
      return true;
    }
  }

}

