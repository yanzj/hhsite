import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';

import {AppComponent} from './app.component';
import {LoginComponent} from "./login-model/login/login.component";
import {AppRoutingModule} from "./app-routing.module";
import {ComSectionComponent} from './common-model/com-section/com-section.component';
import {MainComponent} from './main/main.component';
// 文件上传功能
import {NetworkService}   from './network.service';
import {CommonModule}     from '@angular/common';
import {FileUploadModule} from "ng2-file-upload";
import {FirstLoginComponent} from './login-model/first-login/first-login.component';
//用户数据模型
import {User} from './User.service';
//公共方法服务
import {commonService} from './commonService.service';
//import {LoanBusinessQueryService} from './business-query/loan-business-query/loan-business-query-service.service';
import {FirstLoginActivateGuard} from "./login-model/first-login/FirstLoginActivateGuard";
import {LoginActivateGuard} from "./login-model/login/LoginActivateGuard";
import {ChangePswComponent} from './personCenter-model/change-psw/change-psw.component';
import {ReloadTrueDirective} from "./common-model/com-section/reloadtrue.directive";
import {AfterLoginActiveGuard} from "./login-model/login/AfterLoginActiveGuard";

import {GtasksComponent} from './personCenter-model/gtasks/gtasks.component';
import {MessageComponent} from './personCenter-model/message/message.component';
import {MessageHistoryComponent} from './personCenter-model/message-history/message-history.component';

import {DealNullPipe} from './pipes/DealNullPipe';
import {LastNoneZero} from './pipes/LastNoneZero';
import {ChangeUnit} from './pipes/ChangeUnit';
import {Permil} from './pipes/Permil';
import {KeepTwoDecimal} from  './pipes/KeepTwoDecimal';
import {DealDate} from  './pipes/DealDate';
import {DealYearMonth} from  './pipes/DealYearMonth';
import {ProfessionalSerialComponent} from "./common-model/professional-serial/professional-serial.component";

import { LoanBusinessQueryComponent} from './business-query/loan-business-query/loan-business-query.component';
import { LoanBusinessDetailComponent } from './business-query/loan-business-detail/loan-business-detail.component';
import { RecordLendingComponent } from './business-query/loan-business-detail/record-lending/record-lending.component';
import { JusticeMortgageComponent } from './business-query/loan-business-detail/justice-mortgage/justice-mortgage.component';
import { ApprovalInformationComponent } from './business-query/loan-business-detail/approval-information/approval-information.component';
import { ApplyDetailComponent } from './business-query/loan-business-detail/apply-detail/apply-detail.component';
import { ApplyMaterialComponent } from './business-query/loan-business-detail/apply-material/apply-material.component';
import { RepayScheduleComponent} from "./business-query/loan-business-detail/repay-schedule/repay-schedule.component";
import { RepayScheduleAgentComponent} from "./business-query/loan-business-detail/repay-schedule-agent/repay-schedule-agent.component";
import { RecordLendingDetailComponent } from './business-query/loan-business-detail/record-lending/record-lending-detail/record-lending-detail.component';
import { SomeApprovalInfoComponent} from "./business-query/loan-business-detail/some-approval-info/some-approval-info.component";
import { PopupBorrowerComponent } from './business-query/loan-business-detail/popup-borrower/popup-borrower.component';
import { PopupPawnComponent } from './business-query/loan-business-detail/popup-pawn/popup-pawn.component';
import { PopupCreditComponent } from './business-query/loan-business-detail/popup-credit/popup-credit.component';
import { PopupJudicialComponent } from './business-query/loan-business-detail/popup-judicial/popup-judicial.component';
import { RepaymentScheduleQueryComponent } from './business-query/repayment-schedule-query/repayment-schedule-query.component';
import { RepaymentScheduleDetailComponent } from './business-query/repayment-schedule-detail/repayment-schedule-detail.component';
import { ChannelInterestCollectionComponent } from './business-query/repayment-schedule-detail/channel-interest-collection/channel-interest-collection.component';
import { ChannelInterestNonCollectionComponent } from './business-query/repayment-schedule-detail/channel-interest-non-collection/channel-interest-non-collection.component';
import { PaymentHistoryCheckComponent } from './business-query/loan-business-detail/payment-history/payment-history-check/payment-history-check.component';
import { DebitRecordComponent } from './business-query/loan-business-detail/debit-record/debit-record.component';
import { PaymentHistoryComponent } from './business-query/loan-business-detail/payment-history/payment-history.component';
import { LendRecordCheckupComponent } from './business-query/lend-record-query/lend-record-checkup/lend-record-checkup.component';
import { RepaymentPlanConfirmationComponent } from './operation-manage/repayment-plan-confirmation/repayment-plan-confirmation.component';
import { RepaymentPlanDetailComponent } from './operation-manage/repayment-plan-detail/repayment-plan-detail.component';
import { RepaymentPlanModifyDetailComponent } from './operation-manage/repayment-plan-modify-detail/repayment-plan-modify-detail.component';
import { InterestAgentComponent } from './operation-manage/repayment-plan-modify-detail/interest-agent/interest-agent.component';
import { InterestUnagentComponent } from './operation-manage/repayment-plan-modify-detail/interest-unagent/interest-unagent.component';
import { RepaymentPlanModifyQueryComponent } from './operation-manage/repayment-plan-modify-query/repayment-plan-modify-query.component';
import { InterestCollectionListComponent } from './operation-manage/repayment-plan-detail/interest-collection-list/interest-collection-list.component';
import { InterestNonCollectionListComponent } from './operation-manage/repayment-plan-detail/interest-non-collection-list/interest-non-collection-list.component';
import { PopupAddComponent} from "./operation-manage/repayment-plan-modify-detail/interest-agent/popup-add/popup-add.component";
import { PopupModifyComponent } from './operation-manage/repayment-plan-modify-detail/interest-agent/popup-modify/popup-modify.component';
import { PopupAddRepaymentComponent } from './operation-manage/repayment-plan-modify-detail/interest-unagent/popup-add-repayment/popup-add-repayment.component';
import { PopupModifyRepaymentComponent } from './operation-manage/repayment-plan-modify-detail/interest-unagent/popup-modify-repayment/popup-modify-repayment.component';
import { RepaymentRegisterQueryComponent } from './money-manage/repayment-register-query/repayment-register-query.component';
import { RepaymentAccountModifyComponent } from './money-manage/repayment-account-modify/repayment-account-modify.component';
import { RepaymentAccountDetailComponent } from './money-manage/repayment-account-detail/repayment-account-detail.component';
import { LendRecordQueryComponent } from './business-query/lend-record-query/lend-record-query.component';
import { RepaymentSuccessComponent } from './business-query/repayment-success/repayment-success.component';
import { SelectLoanContractComponent } from './money-manage/repayment-register-detail/select-loan-contract.component';
import { BatchUploadComponent } from './money-manage/batch-upload/batch-upload.component';
import { CheckRepaymentComponent } from './money-manage/repayment-register-detail/check-repayment/check-repayment.component';
import { ManualRegistrationComponent } from './money-manage/repayment-register-detail/manual-registration/manual-registration.component';
import {DeducteRecordComponent} from "./business-query/deducte-record/deducte-record.component";
import { OverdueRecordComponent } from './business-query/overdue-record/overdue-record.component';
import { AccountInfoComponent } from './business-query/account-info/account-info.component';
import {loanBusinessDetailService} from "./business-query/loan-business-detail/loan-business-detail.service";
import {RepaymentSuccessDetailComponent} from "./business-query/repayment-success/repayment-success-detail/repayment-success-detail.component";
import {DeducteRecordDetailComponent} from "./business-query/deducte-record/deducte-record-detail/deducte-record-detail.component";
import {BackButtonComponent} from "./common-model/back-button/back-button.component";
import {AccountInfoDetailComponent} from "./business-query/account-info/account-info-detail/account-info-detail.component";

import { OverdueRecordDetailComponent } from './business-query/overdue-record/overdue-record-detail/overdue-record-detail.component';





//引用日历控件

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ComSectionComponent,
    BackButtonComponent,
    MainComponent,
    FirstLoginComponent,
    ChangePswComponent,
    ReloadTrueDirective,
    GtasksComponent,
    MessageComponent,
    MessageHistoryComponent,
    ProfessionalSerialComponent,
    DealNullPipe,
    LastNoneZero,
    ChangeUnit,
    Permil,
    KeepTwoDecimal,
    DealDate,
    DealYearMonth,
    LoanBusinessQueryComponent,
    LoanBusinessDetailComponent,
    RepayScheduleComponent,
    RepayScheduleAgentComponent,
    RecordLendingComponent,
    JusticeMortgageComponent,
    ApprovalInformationComponent,
    ApplyDetailComponent,
    ApplyMaterialComponent,
    SomeApprovalInfoComponent,
    RecordLendingDetailComponent,
    PopupBorrowerComponent,
    PopupPawnComponent,
    PopupCreditComponent,
    PopupJudicialComponent,
    RepaymentScheduleQueryComponent,
    RepaymentScheduleDetailComponent,
    RepaymentPlanConfirmationComponent,
    RepaymentPlanDetailComponent,
    RepaymentPlanModifyDetailComponent,
    InterestAgentComponent,
    InterestUnagentComponent,
    RepaymentPlanModifyQueryComponent,
    InterestCollectionListComponent,
    InterestNonCollectionListComponent,
    ChannelInterestCollectionComponent,
    ChannelInterestNonCollectionComponent,
    PopupAddComponent,
    PopupModifyComponent,
    PopupAddRepaymentComponent,
    PopupModifyRepaymentComponent,
    RepaymentRegisterQueryComponent,
    SelectLoanContractComponent,
    BatchUploadComponent,
    CheckRepaymentComponent,
    ManualRegistrationComponent,
    PaymentHistoryComponent,
    LendRecordCheckupComponent,
    PaymentHistoryCheckComponent,
    DebitRecordComponent,
    RepaymentAccountModifyComponent,
    RepaymentAccountDetailComponent,
    LendRecordQueryComponent,
    RepaymentSuccessComponent,
    DeducteRecordComponent,
    OverdueRecordComponent,
    AccountInfoComponent,
    RepaymentSuccessDetailComponent,
    DeducteRecordDetailComponent,
    AccountInfoDetailComponent,
    OverdueRecordDetailComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    CommonModule,
    FileUploadModule,
  ],
  providers: [NetworkService, User, LoginActivateGuard, FirstLoginActivateGuard, AfterLoginActiveGuard, commonService,loanBusinessDetailService,{
    provide: LocationStrategy,
    useClass: HashLocationStrategy,
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
