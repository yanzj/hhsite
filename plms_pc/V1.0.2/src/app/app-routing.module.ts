/**
 * Created by hp on 2017/5/15.
 */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login-model/login/login.component";
import {ComSectionComponent} from "./common-model/com-section/com-section.component";
import {FirstLoginComponent} from "./login-model/first-login/first-login.component";
import {MainComponent} from "./main/main.component";
import {LoginActivateGuard} from "./login-model/login/LoginActivateGuard";
import {FirstLoginActivateGuard} from "./login-model/first-login/FirstLoginActivateGuard";
import {ChangePswComponent} from "./personCenter-model/change-psw/change-psw.component";
import {AfterLoginActiveGuard} from "./login-model/login/AfterLoginActiveGuard";


import {GtasksComponent} from "./personCenter-model/gtasks/gtasks.component";
import { MessageComponent } from './personCenter-model/message/message.component';
import { MessageHistoryComponent } from './personCenter-model/message-history/message-history.component';
import { LoanBusinessQueryComponent} from "./business-query/loan-business-query/loan-business-query.component";
import { LoanBusinessDetailComponent} from "./business-query/loan-business-detail/loan-business-detail.component";
import { RepaymentScheduleQueryComponent} from "./business-query/repayment-schedule-query/repayment-schedule-query.component";
import { RepaymentScheduleDetailComponent} from "./business-query/repayment-schedule-detail/repayment-schedule-detail.component";
import { RepaymentPlanConfirmationComponent } from './operation-manage/repayment-plan-confirmation/repayment-plan-confirmation.component';
import { RepaymentPlanDetailComponent } from './operation-manage/repayment-plan-detail/repayment-plan-detail.component';
import { RepaymentPlanModifyQueryComponent} from "./operation-manage/repayment-plan-modify-query/repayment-plan-modify-query.component";
import { RepaymentPlanModifyDetailComponent} from "./operation-manage/repayment-plan-modify-detail/repayment-plan-modify-detail.component";
import { RepaymentRegisterQueryComponent } from './money-manage/repayment-register-query/repayment-register-query.component';
import {RepaymentAccountModifyComponent} from "./money-manage/repayment-account-modify/repayment-account-modify.component";
import {RepaymentAccountDetailComponent} from "./money-manage/repayment-account-detail/repayment-account-detail.component";
import {LendRecordQueryComponent} from "./business-query/lend-record-query/lend-record-query.component";
import { SelectLoanContractComponent } from './money-manage/repayment-register-detail/select-loan-contract.component';
import { BatchUploadComponent } from './money-manage/batch-upload/batch-upload.component';
import { CheckRepaymentComponent } from './money-manage/repayment-register-detail/check-repayment/check-repayment.component';
import { ManualRegistrationComponent } from './money-manage/repayment-register-detail/manual-registration/manual-registration.component';
import { LendRecordCheckupComponent } from './business-query/lend-record-query/lend-record-checkup/lend-record-checkup.component';
import {RepaymentSuccessComponent} from "./business-query/repayment-success/repayment-success.component";
import {DeducteRecordComponent} from "./business-query/deducte-record/deducte-record.component";
import {OverdueRecordComponent} from "./business-query/overdue-record/overdue-record.component";
import {AccountInfoComponent} from "./business-query/account-info/account-info.component";
import {RepaymentSuccessDetailComponent} from "./business-query/repayment-success/repayment-success-detail/repayment-success-detail.component";
import {DeducteRecordDetailComponent} from "./business-query/deducte-record/deducte-record-detail/deducte-record-detail.component";
import {AccountInfoDetailComponent} from "./business-query/account-info/account-info-detail/account-info-detail.component";
import { OverdueRecordDetailComponent } from './business-query/overdue-record/overdue-record-detail/overdue-record-detail.component';

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
    path:'home',
    component:ComSectionComponent,
    canActivate: [AfterLoginActiveGuard],
    children:[
      {
        path:'',
        redirectTo:'/home/lendRecordQuery',
        pathMatch:'full'
      },
      {
        path:'main',
        component:MainComponent,

      },
      //业务查询
      {
        path:'loanBusiness',  //3.0贷款业务查询
        component:LoanBusinessQueryComponent,
      },
      {
        path:'loanBusiness/:id', //贷款业务查询详情
        component:LoanBusinessDetailComponent,
      },
      {
        path:'repaymentSchedule',//4.0还款计划查询
        component:RepaymentScheduleQueryComponent,
      },
      {
        path:'repaymentSchedule/:id',//4.1还款计划查询详情
        component:RepaymentScheduleDetailComponent,
      },
      {
        path:'lendRecordQuery', //9。0 放款记录查询列表
        component:LendRecordQueryComponent
      },

      {
        path:'lendRecordQuery/:id', //9。2放款记录查看详情
        component:LendRecordCheckupComponent
      },
      {
        path:'repaymentSuccess', //10.0还款到账查询
        component:RepaymentSuccessComponent
      },
      {
        path:'repaymentSuccess/:id',//10。2查看还款到账
        component:RepaymentSuccessDetailComponent
      },
      {
        path:'deducteRecord',//11扣款记录查询
        component:DeducteRecordComponent
      },
      {
        path:'deducteRecord/:id',//11。2扣款记录查询
        component:DeducteRecordDetailComponent
      },
      {
        path:'overdueRecord',//12逾期记录查询
        component:OverdueRecordComponent
      },
      {
        path:'overdueRecord/:id',//12.1逾期记录查询详情
        component:OverdueRecordDetailComponent
      },
      {
        path:'accountInfo',//13账户信息查询
        component:AccountInfoComponent
      },
      // {
      //   path:'accountInfo',//13.2账户信息查询
      //   component:AccountInfoComponent
      // },
      {
        path:'accountInfo/:id',//13.2账户信息查询详情
        component:AccountInfoDetailComponent
      },
      //资金管理
      {
        path:'repaymentRegisterQuery',//7.0还款到账登记
        component:RepaymentRegisterQueryComponent
      },
      {
        path:'repaymentRegisterQuery/:id',//7.3还款到账登记查看详情
        component:CheckRepaymentComponent
      },
      {
        path:'repaymentRegisterQuery/repayment-register-detail/selectLoan',//7.1选择借款合同
        component:SelectLoanContractComponent
      },
      {
        path:'repaymentRegisterQuery/repayment-register-detail/selectLoan/:id',//7.4手工登记
        component:ManualRegistrationComponent
      },
      {
        path:'repaymentRegisterQuery/batch-upload/batchUpload',//7.2批量上传
        component:BatchUploadComponent
      },
      {
        path:'repaymentAccountModify',//8.0还款到账修改
        component:RepaymentAccountModifyComponent
      },
      {
        path:'repaymentAccountModify/:id',//8.1还款到账修改
        component:RepaymentAccountDetailComponent
      },
      //运营管理
      {
        path:'repaymentPlanConfirmation', //5.0还款计划确认
        component:RepaymentPlanConfirmationComponent,
      },
      {
        path:'repaymentPlanConfirmation/:id',//5.1还款计划确查看认详情
        component:RepaymentPlanDetailComponent,
      },
      {
        path:'repaymentPlanModifyQuery',//6.0还款计划修改
        component:RepaymentPlanModifyQueryComponent
      },
      {
        path:'repaymentPlanModifyQuery/:id', //6.1还款计划修改
        component:RepaymentPlanModifyDetailComponent
      },


      //个人中心
      {
        path:'gtasks',  //待办任务
        component:LoanBusinessQueryComponent
      },
      {
        path:'message',//消息通知
        component:MessageComponent
      },
      {
        path:'messageHistory',//消息通知历史
        component:MessageHistoryComponent
      },

      {
        path:'ChangePsw',//修改密码
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
