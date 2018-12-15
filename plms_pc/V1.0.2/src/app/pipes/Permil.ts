
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'permil'
})

export class Permil implements PipeTransform {

  transform(value: any, args?: any): any {

    //专门处理千分号
    var parts = value.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
  }

}
