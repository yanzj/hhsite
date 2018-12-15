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

    //把"元"转为"万元"，并且去掉前后无效0
    let temp:any = +value/10000;

    return temp=parseFloat(temp);


  }



}

