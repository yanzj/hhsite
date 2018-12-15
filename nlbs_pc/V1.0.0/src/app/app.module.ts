import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';

import { AppComponent } from './app.component';
import {LoginComponent} from "./login-model/login/login.component";
import {AppRoutingModule} from "./app-routing.module";
import { ComSectionComponent } from './common-model/com-section/com-section.component';
import { MainComponent } from './main/main.component';
import { QuerySectionComponent } from './apply-model/query-section/query-section.component';
import { ApplySectionComponent } from './apply-model/apply-section/apply-section.component';
import {QueryDetailsComponent} from "./apply-model/query-details/query-details.component";
// 文件上传功能
import { NetworkService }   from './network.service';
import { CommonModule }     from '@angular/common';
import {FileUploadModule} from "ng2-file-upload";
import { FirstLoginComponent } from './login-model/first-login/first-login.component';
//用户数据模型
import {User} from './User.service';
//公共方法服务
import {commonService} from './commonService.service';
import { HistoryComponent } from './apply-model/history/history.component';
import { EnquiryComponent } from './enquiry-model/enquiry/enquiry.component';
import { PersonEnquiryComponent } from './enquiry-model/person-enquiry/person-enquiry.component';
import { EnquirySearchComponent } from './enquiry-model/enquiry-search/enquiry-search.component';
import { EnquiryHistoryListComponent } from './enquiry-model/enquiry-history-list/enquiry-history-list.component';
import { EnquiryDetailComponent } from './enquiry-model/enquiry-detail/enquiry-detail.component';
import { EnquiryResubmitComponent } from './enquiry-model/enquiry-resubmit/enquiry-resubmit.component';
import { GtasksComponent } from './personCenter-model/gtasks/gtasks.component';
import { MessageComponent } from './personCenter-model/message/message.component';
import { MessageHistoryComponent } from './personCenter-model/message-history/message-history.component';
import {FirstLoginActivateGuard} from "./login-model/first-login/FirstLoginActivateGuard";
import {LoginActivateGuard} from "./login-model/login/LoginActivateGuard";
import { ChangePswComponent } from './personCenter-model/change-psw/change-psw.component';
import {ReloadTrueDirective} from "./common-model/com-section/reloadtrue.directive";
import {AfterLoginActiveGuard} from "./login-model/login/AfterLoginActiveGuard";


//引用日历控件

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ComSectionComponent,
    MainComponent,
    QuerySectionComponent,
    ApplySectionComponent,
    FirstLoginComponent,
    QueryDetailsComponent,
    HistoryComponent,
    EnquiryComponent,
    PersonEnquiryComponent,
    EnquirySearchComponent,
    EnquiryHistoryListComponent,
    EnquiryDetailComponent,
    EnquiryResubmitComponent,
    GtasksComponent,
    MessageComponent,
    MessageHistoryComponent,
    ChangePswComponent,
    ReloadTrueDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    CommonModule,
    FileUploadModule,

   ],
  providers: [NetworkService,User,LoginActivateGuard,FirstLoginActivateGuard,AfterLoginActiveGuard,commonService,{ provide: LocationStrategy, useClass: HashLocationStrategy, }],
  bootstrap: [AppComponent]
})
export class AppModule { }
