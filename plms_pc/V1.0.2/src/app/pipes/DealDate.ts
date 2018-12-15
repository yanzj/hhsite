/**
 * Created by dell on 2017/7/18.
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dealDate'
})
export class DealDate implements PipeTransform {

  transform(value: any, args?: any): any {
     var resultStr : string = '';
    // if (value == undefined || value == null || value == 'undefined') {
    //   resultStr = '';
    // }else{
    //   resultStr = value;
    // }


    if(value){
      var arr=value.split(':');
      arr.splice(arr.length-1,1);
      value=arr.join(':');
      return value;
    }
   // arr.splice(arr.length-1, 0, ":");











   }

}
