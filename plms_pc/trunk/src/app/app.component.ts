import {Component, OnChanges, OnInit, Injectable,  Input} from '@angular/core';
import { Headers, Http ,Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/toPromise';
import {loanBusinessDetailService} from "./business-query/loan-business-detail/loan-business-detail.service";

declare var $:any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers:[loanBusinessDetailService]
})

export class AppComponent {


}
