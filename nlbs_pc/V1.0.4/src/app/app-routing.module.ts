/**
 * Created by hp on 2017/5/15.
 */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login-model/login/login.component";
import {ComSectionComponent} from "./common-model/com-section/com-section.component";
import { QuerySectionComponent } from './apply-model/query-section/query-section.component';
import { ApplySectionComponent } from './apply-model/apply-section/apply-section.component';
import {FirstLoginComponent} from "./login-model/first-login/first-login.component";
import {MainComponent} from "./main/main.component";
import {QueryDetailsComponent} from "./apply-model/query-details/query-details.component";
import {HistoryComponent} from "./apply-model/history/history.component";
import {EnquiryComponent} from "./enquiry-model/enquiry/enquiry.component";
import {PersonEnquiryComponent} from "./enquiry-model/person-enquiry/person-enquiry.component";
import { EnquirySearchComponent } from './enquiry-model/enquiry-search/enquiry-search.component';
import { EnquiryHistoryListComponent } from './enquiry-model/enquiry-history-list/enquiry-history-list.component';
import { EnquiryDetailComponent } from './enquiry-model/enquiry-detail/enquiry-detail.component';
import { EnquiryResubmitComponent } from './enquiry-model/enquiry-resubmit/enquiry-resubmit.component';
import {GtasksComponent} from "./personCenter-model/gtasks/gtasks.component";
import { MessageComponent } from './personCenter-model/message/message.component';
import { MessageHistoryComponent } from './personCenter-model/message-history/message-history.component';
import {LoginActivateGuard} from "./login-model/login/LoginActivateGuard";
import {FirstLoginActivateGuard} from "./login-model/first-login/FirstLoginActivateGuard";
import {ChangePswComponent} from "./personCenter-model/change-psw/change-psw.component";
import {AfterLoginActiveGuard} from "./login-model/login/AfterLoginActiveGuard";

//贷后管理
import { LendedQueryComponent } from './loanManage-model/lended-query/lended-query.component';
import { LendedQueryDetailComponent } from './loanManage-model/lended-query-detail/lended-query-detail.component';
import {RepaymentScheduleQueryComponent} from "./loanManage-model/repayment-schedule-query/repayment-schedule-query.component";
import {RepaymentScheduleDetailComponent} from "./loanManage-model/repayment-schedule-detail/repayment-schedule-detail.component";
import {OverdueRecordComponent} from "./loanManage-model/overdue-record/overdue-record.component";
import {OverdueRecordDetailComponent} from "./loanManage-model/overdue-record-detail/overdue-record-detail.component";

const routes: Routes = [
  {
    path:'',
    redirectTo:'/login',
    pathMatch:'full'
  },
  {
    path:'login',
    component:LoginComponent,
    canActivate: [LoginActivateGuard]
  },
  {
    path:'firstLogin',
    component:FirstLoginComponent,
    canActivate: [FirstLoginActivateGuard]
  },

  {
    path:'personEnquiry',
    component:PersonEnquiryComponent,
    canActivate: [AfterLoginActiveGuard]

  },

  {
    path:'home',
    component:ComSectionComponent,
    canActivate: [AfterLoginActiveGuard],
    children:[
      {
        path:'',
        redirectTo:'/login',
        pathMatch:'full'
      },
      {
        path:'main',
        component:MainComponent,

      },
      {
        path:'apply',
        component:ApplySectionComponent
      },
      {
        path:'query',
        component:QuerySectionComponent,

      },
      {
        path:'itemDetails',
        component:QueryDetailsComponent,
      },
      {
        path:'query/apply',
        component:ApplySectionComponent
      },
      {
        path:'history',
        component:HistoryComponent,
      },
      //询价管理
      {
        path:'enquiry',
        component:EnquiryComponent
      },

      {
        path:'enquirySearch',
        component:EnquirySearchComponent
      },
      {
        path:'personEnquiry',
        component:PersonEnquiryComponent

      },
      {
        path:'enquiryHistorySearch',
        component:EnquiryHistoryListComponent

      },
      {
        path:'enquiryDetail',
        component:EnquiryDetailComponent

      },
      {
        path:'enquiryResubmit',
        component:EnquiryResubmitComponent

      },
      //贷后管理loadManage-model

      {
        path:'lendedQuery',  //借款业务查询
        component:LendedQueryComponent

      },
      {
        path:'lendedQuery/:id',  //借款业务查询详情
        component:LendedQueryDetailComponent

      },
      {
        path:'repaymentQuery', //还款计划查询
        component:RepaymentScheduleQueryComponent

      },
      {
        path:'repaymentQuery/:id', //还款计划查询详情
        component:RepaymentScheduleDetailComponent

      },
      {
        path:'overdueQuery',//逾期记录查询
        component:OverdueRecordComponent

      },
      {
        path:'overdueQuery/:id',//逾期记录查询详情
        component:OverdueRecordDetailComponent

      },
      // 个人中心MessageComponent
      {
        path:'gtasks',
        component:GtasksComponent
      },
      {
        path:'message',
        component:MessageComponent
      },
      {
        path:'messageHistory',
        component:MessageHistoryComponent
      },
      {
        path:'ChangePsw',
        component:ChangePswComponent
      }

    ]
  }
  ]

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
