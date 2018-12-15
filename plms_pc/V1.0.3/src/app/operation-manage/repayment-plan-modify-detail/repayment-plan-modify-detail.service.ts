/**
 * Created by dell on 2017/8/1.
 */
import {Injectable} from '@angular/core';

declare var $: any;
@Injectable()

export class repaymentPlanModifyDetailService {


//  添加还款计划的弹窗
  addPop(tipTitle){
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>
     <div class="row">
         <div class="float-left filed-name width-160"><span>*</span>还款日期：</div>
         <input type="text" class="msg-input float-left filed-text width-160  margin-left-20">
     </div>
     <div class="row">
         <div class="float-left filed-name width-160"><span>*</span>应还本金：</div>
         <input type="text" class="msg-input float-left filed-text width-160  margin-left-20">
         <div class="uinit">元</div>
         <div class="float-left filed-name width-160 margin-left-20"><span>*</span>应还利息：</div>
        <input type="text" class="msg-input float-left filed-text width-160  margin-left-20">
        <div class="uinit">元</div>
     </div>
    </div>`;

    $(this).popModal({
      width: '780px',
      padding:'20px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }

  }
}
