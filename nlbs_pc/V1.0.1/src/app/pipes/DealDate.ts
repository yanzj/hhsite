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

    // var attr=[];
    // attr=value.split(':').pop();
    // for(let i=0;i<attr.length;i++){
    //   resultStr=attr.join(':')
    // }
    var arr=value.split(':');
    arr.splice(arr.length-1,1);
   // arr.splice(arr.length-1, 0, ":");
    value=arr.join(':');
   return value;








  //   Date.prototype.pattern=function(fmt) {
    //function dateFunc(){

    // var date=new Date();
    //   var o = {
    //     "M+" : date.getMonth()+1, //月份
    //     "d+" : date.getDate(), //日
    //     "h+" : date.getHours()%12 == 0 ? 12 : date.getHours()%12, //小时
    //     "H+" : date.getHours(), //小时
    //     "m+" : date.getMinutes(), //分
    //     "s+" : date.getSeconds(), //秒
    //     "q+" : Math.floor((date.getMonth()+3)/3), //季度
    //     "S" : date.getMilliseconds() //毫秒
    //   };
    //   var week = {
    //     "0" : "/u65e5",
    //     "1" : "/u4e00",
    //     "2" : "/u4e8c",
    //     "3" : "/u4e09",
    //     "4" : "/u56db",
    //     "5" : "/u4e94",
    //     "6" : "/u516d"
    //   };
    //   if(/(y+)/.test(value)){
    //     value=value.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    //   }
    //   if(/(E+)/.test(value)){
    //     value=value.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[date.getDay()+""]);
    //   }
    //   for(var k in o){
    //     if(new RegExp("("+ k +")").test(value)){
    //       value = value.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    //     }
    //   }
    //   return value;
    // }


   }

}
