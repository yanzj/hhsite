/**
 * Created by dell on 2017/8/2.
 */

import { Pipe, PipeTransform } from '@angular/core';
declare var $: any;
@Pipe({
  name: 'changeUnit'
})
export class ChangeUnit implements PipeTransform {

  transform(value: any, args?: any): any {

    var resultStr: any = '';
    if (value == '' || value == undefined || value == null || value == 'undefined') {
      resultStr = '0.00';
    } else {
      value=+value;
      resultStr =(value*10000)/(10000*10000);
    }
    // //把"元"转为"万元"，并且去掉前后无效0
    // let temp:any = +value/10000;

    return resultStr;
  }

  // let temp:any = +value;
  //
  // return temp=(temp*10000)/(10000*10000)
//
//   function accDiv(num1,num2){
//   var t1,t2,r1,r2;
//   try{
//     t1 = num1.toString().split('.')[1].length;
//   }catch(e){
//     t1 = 0;
//   }
//   try{
//     t2=num2.toString().split(".")[1].length;
//   }catch(e){
//     t2=0;
//   }
//   r1=Number(num1.toString().replace(".",""));
//   r2=Number(num2.toString().replace(".",""));
//   return (r1/r2)*Math.pow(10,t2-t1);
// }
//
//

}

