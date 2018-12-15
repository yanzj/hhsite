/**
 * Created by dell on 2017/7/28.
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lastNoneZero'
})
export class LastNoneZero implements PipeTransform {

  transform(value: any, args?: any): any {
    var resultStr : any = +value;
    // if(value.indexOf(".") > 0){
    //   value = value.replaceAll("0+?$", "");//去掉多余的0
    //   value = value.replaceAll("[.]$", "");//如最后一位是.则去掉
    // }
    // return value;

    resultStr=parseFloat(resultStr);

    return resultStr

  }




}
