/**
 * Created by dell on 2017/7/18.
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dealNullPipe'
})
export class DealNullPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    var resultStr : string = '';
    if (value == undefined || value == null || value == 'undefined') {
      resultStr = '';
    }else{
      resultStr = value;
    }
    return resultStr;
  }

}
