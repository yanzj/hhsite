/*
import { Injectable } from '@angular/core';
import { Headers,Response, Http } from '@angular/http';
import "../config/rxjs-operators";
import { CREDENTIAL, ENDPOINT } from "../config/config";
import {  MODEL  } from "../config/objects";
@Injectable()
export class ModelsService {
  constructor(private http: Http) {}
  //get
  getModels(): Promise<MODEL[]>{
    const auth = JSON.parse(sessionStorage["auth"]);
    let url = 地址;
    const headers = new Headers({
      "Authorization": 权限,
      "Accept": 格式
    });
    return this.http
      .get(url, { headers: headers })
      .toPromise()
      .then(response => response.json() as MODEL[])
      .catch(this.handleError);
  }
  //delete
  delete(urlparams): Promise<void>{
    const auth = JSON.parse(sessionStorage["auth"]);
    const url = 地址;
    const headers = new Headers({
      "Authorization": 权限,
    });
    return this.http
      .delete(url, { headers: headers })
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }
  //error-message
  handleError(error: any): Promise<any> {
    console.error('ModelsService', error);
    return Promise.reject(error.message || error);
  }

}

*/
