/**
 * Created by dell on 2017/7/18.
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dealYearMonth'
})
export class DealYearMonth implements PipeTransform {

  transform(value: any, args?: any): any {
     var resultStr : string = '';


    if(value){
      var value=value.replace('-','年');
      value=value+'月'
      return value;
    }









   }

}
