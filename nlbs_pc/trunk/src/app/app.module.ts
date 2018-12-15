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
import {ProfessionalSerialComponent} from "./common-model/professional-serial/professional-serial.component";
import { commonService} from './commonService.service';
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
import { FirstLoginActivateGuard} from "./login-model/first-login/FirstLoginActivateGuard";
import { LoginActivateGuard} from "./login-model/login/LoginActivateGuard";
import { ChangePswComponent } from './personCenter-model/change-psw/change-psw.component';
import { ReloadTrueDirective} from "./common-model/com-section/reloadtrue.directive";
import { AfterLoginActiveGuard} from "./login-model/login/AfterLoginActiveGuard";
import { BreadCrumbComponent} from "./common-model/bread-crumb/bread-crumb.component";
import { CatalogueInfoComponent } from './apply-model/query-details/catalogue-info/catalogue-info.component';
import { ApplyInfoComponent } from './apply-model/query-details/apply-info/apply-info.component';
import { ApplyMaterialComponent } from './apply-model/query-details/apply-material/apply-material.component';
import { PopupPersonInfoComponent } from './apply-model/query-details/popup-person-info/popup-person-info.component';
import { ExamineComponent } from './apply-model/query-details/examine/examine.component';

import { PopupCatalogueComponent } from './apply-model/query-details/popup-catalogue/popup-catalogue.component';
import { PopupCreditComponent } from './apply-model/query-details/popup-credit/popup-credit.component';
import { PopupJudicialComponent } from './apply-model/query-details/popup-judicial/popup-judicial.component';
import { PopupBorrowerComponent } from './apply-model/query-details/popup-borrower/popup-borrower.component';
import { QueryComponent } from './apply-model/query-details/query/query.component';
import { PopupPawnDetailComponent } from './apply-model/query-details/popup-pawn-detail/popup-pawn-detail.component';

import { DealNullPipe } from './pipes/DealNullPipe';
import { DealDate } from './pipes/DealDate';
import { Permil } from './pipes/Permil';
import { LastNoneZero } from './pipes/LastNoneZero';
import { KeepTwoDecimal } from './pipes/KeepTwoDecimal';
import {ChangeUnit} from "./pipes/ChangeUnit";
import { ContractComponent } from './apply-model/query-details/contract/contract.component';

//贷后管理的组件
import { LoanInfoComponent } from './apply-model/query-details/loan-info/loan-info.component';
import { LendedQueryComponent } from './loanManage-model/lended-query/lended-query.component';
import { LendedQueryDetailComponent } from './loanManage-model/lended-query-detail/lended-query-detail.component';
import { RepaymentQueryComponent } from './loanManage-model/repayment-query/repayment-query.component';
import { RepaymentDetailComponent } from './loanManage-model/repayment-detail/repayment-detail.component';
import { OverdueQueryComponent } from './loanManage-model/overdue-query/overdue-query.component';
import { OverdueDetailComponent } from './loanManage-model/overdue-detail/overdue-detail.component';
import { RepaymentPlanComponent } from './loanManage-model/lended-query-detail/repayment-plan/repayment-plan.component';
import { RepaymentRecordComponent } from './loanManage-model/lended-query-detail/repayment-record/repayment-record.component';
import { LoanRecordComponent } from './loanManage-model/lended-query-detail/loan-record/loan-record.component';
import { LoanRecordViewComponent } from './loanManage-model/lended-query-detail/loan-record/loan-record-view/loan-record-view.component';
import { RepaymentRecordViewComponent } from './loanManage-model/lended-query-detail/repayment-record/repayment-record-view/repayment-record-view.component';



//引用日历控件

@NgModule({
  declarations: [
    ProfessionalSerialComponent,
    AppComponent,
    BreadCrumbComponent,
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
    ReloadTrueDirective,
    CatalogueInfoComponent,
    ApplyInfoComponent,
    ApplyMaterialComponent,
    PopupPersonInfoComponent,
    ExamineComponent,

    PopupCatalogueComponent,
    PopupCreditComponent,
    PopupJudicialComponent,
    PopupBorrowerComponent,
    QueryComponent,
    PopupPawnDetailComponent,

    DealNullPipe,
    DealDate,
    Permil,
    LastNoneZero,
    KeepTwoDecimal,
    ChangeUnit,
    ContractComponent,

    LoanInfoComponent,
    LendedQueryComponent,
    LendedQueryDetailComponent,

    RepaymentQueryComponent,
    RepaymentDetailComponent,
    OverdueQueryComponent,
    OverdueDetailComponent,
    RepaymentPlanComponent,
    RepaymentRecordComponent,
    LoanRecordComponent,
    LoanRecordViewComponent,
    RepaymentRecordViewComponent,

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
