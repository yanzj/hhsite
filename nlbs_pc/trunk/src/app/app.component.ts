import {Component, OnChanges, OnInit, Injectable,  Input} from '@angular/core';
import { Headers, Http ,Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/toPromise';
import {commonService} from "./commonService.service";
declare var $:any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers:[commonService]
})

export class AppComponent {


}
