

import { Pipe, PipeTransform } from '@angular/core';


@Pipe({
  name: 'keepTwoDecimal'
})


export class KeepTwoDecimal implements PipeTransform {

    transform(value: any, args?: any): any {

       // return value=value.toFixed(2)
      var resultStr : string = '';
      if (value == ''|| value == undefined || value == null || value == 'undefined') {
        resultStr = '0.00';
      }else{
        resultStr = parseFloat(value).toFixed(2);
      }

      return resultStr;


  }



}
