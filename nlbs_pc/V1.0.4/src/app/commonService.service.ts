import { Injectable } from '@angular/core';
declare var $: any;

@Injectable()
export class commonService {

  // public toChangeLoanSerialNo:string='';

  getChangeLoanSerialNo():void{

  }

	private loginTokenKey:string = 'loginTokenKey';//登录时间戳
	private businessSessionKey:string = 'bussinessSessonKey';//保存当前选中的询价 或者 进件信息
	private businessSaveKey:string =  'businessSaveKey';
	public businessReadValue:string = '1';//缓存进件  询价信息是否读取

  public enquiryStatus:string = 'enquiryStatus';

  public histroyStatus:string = 'histroyStatuReadFlag';

  public resubmitStatus:string ='resubmitStatusFlag';

	tipsErrorDisplay: string = 'show';
	isZero = /\b(0+)/gi;

	//询价的方法   进入详情后  对应返回相应的页面
  public enquiryPath:string ='enquiryPath';

	//空字符串转换成‘’
	setStr2Legal(objStr:any){
		if(objStr == undefined || objStr == '')objStr = "";
		return objStr;
	}

	//字符串判断是否非空
	onJudgeStringIsNull(objStr:any){
		var result:boolean = true;
		if (objStr == undefined || objStr == ''||objStr==null) result = false;
		return result;
	}

	//获取当前系统时间戳
	getCurrentTimeStamp(){
		return new Date().getTime().toString();
	}

	//timeStamp 为''时 清楚本地缓存时间戳 否则记录登录时间戳
	setloginToken(timeStamp:string){
		window.localStorage.setItem(this.loginTokenKey, timeStamp);
	}
	//移除本地登录时间戳
  removeLoginToken(){
	  window.localStorage.removeItem(this.loginTokenKey);
  }
    //设置查看询价的状态
  setEnquiryStatus(enquiryStatus){
    window.sessionStorage.setItem(this.enquiryStatus,enquiryStatus);
  }
  //获得查看询价的状态
  getEnquiryStatus(){
    return window.sessionStorage.getItem(this.enquiryStatus);
  }

  //返回相应的询价页面
  setEnquiryPath(enquiryPath){
    window.sessionStorage.setItem(this.enquiryPath,enquiryPath)
  }
  //获得 相应需要跳转的询价页面
  getEnquiryPath(){
    return window.sessionStorage.getItem(this.enquiryPath);
  }
  //清除相应的状态
  removeEnquiryPath(){
    window.sessionStorage.setItem(this.enquiryPath,'')
  }

  //设置历史的状态
  setHistroyStatus(histroyStatus){
    window.sessionStorage.setItem(this.histroyStatus,histroyStatus);
  }
  //获得历史的状态
  getHistroyStatus(){
    return window.sessionStorage.getItem(this.histroyStatus);
  }
  //移除历史的状态
  removeHistroyStatus(){
    return window.sessionStorage.removeItem(this.histroyStatus);
  }

  //设置进入录入评估的方法
  setResubmitStatus(resubmitStatus){
    window.sessionStorage.setItem(this.resubmitStatus,resubmitStatus);
  }
  //获得进入录入评估的方法
  getResubmitStatus(){
    return window.sessionStorage.getItem(this.resubmitStatus);
  }
  //移除录入评估的状态
  removeResubmitStatus(){
    window.sessionStorage.removeItem(this.resubmitStatus);
  }

	//获取当前缓存登录时间戳
	getloginToken(){
		let objStr =  window.localStorage.getItem(this.loginTokenKey);
		if (objStr == '' || !objStr){
			return '' ;
		}else{
			return objStr ;
		}
	}

	//获取到当前格式化日期
	getFormatDate(obj:any){
		var objDate = obj._d;
		var year = objDate.getFullYear() ;
		var month = (objDate.getMonth() + 1);
		var day = objDate.getDate();
		if (month < 10) month = '0'+ month;
		if (day < 10) day = '0'+ day;
		var resultDate = year+ '-' + month+ '-' + day;

       	return resultDate;

	}
	/*
		在点击进件列表跳转进件详情等方法钱，存储该进件信息信息方法存储方法的思路是：
		1. 跳转前 在session中缓存当前的进件信息 和 下个页面是否要获取该session信息
		2. 在进件提交页面中，初始化该组件时 根据读取到的session中存取标志  获悉当前组件将要实现的功能是暂存再提交，因此去请求暂存信息并进行之后流程
		3. 点击历史时，直接在session中获取到当前缓存进件信息
		3.当进件组件被销毁时 ，移除进件读取标志。

		目的是：犹豫session在销毁时，会移除读取标志 但并未移除进件session信息  因此在该进件中去跳转历史页面时  不影响之后读取进件信息
		并且，在下次直接点击进件提交页面功能时，可以区分是首次提交进件还是暂存再提交。
	*/


	//设置存储进件基本信息
	setBusinessData(obj:Object){
		let objStr = JSON.stringify(obj);
		window.sessionStorage.setItem(this.businessSessionKey, objStr);
	}
	//获取进件基本信息
	getBusinessData(){
		let obj = window.sessionStorage.getItem(this.businessSessionKey);
		 return  JSON.parse(obj);
	}
	//移除进件基础信息
	removeBusinessData(){
		window.sessionStorage.removeItem(this.businessSessionKey);
	}
	//设置进件读取标志
	setBusinsessSaveFlagFunc(){
		window.sessionStorage.setItem(this.businessSaveKey,this.businessReadValue);
	}
	//获取进件读取标志
	getBusinessSaveFlagFunc(){
		return window.sessionStorage.getItem(this.businessSaveKey);
	}
	//移除进件读取标志
	removeBusinessSaveFlagFunc(){
		window.sessionStorage.removeItem(this.businessSaveKey);
	}




	/*
		遍历解决网络请求返回数据中 出现的null undefined
		解决思路：1、解决根本方法：obj、arr数据类型作为参数传递处理过程中 形参仅指向的是该对象的存储地址（浅拷贝），在过程中可修改其指向地址中的内容

				 2、使用递归策略 将问题划分为：1.循环当前数据对象并判断对象类型控制循环流程
									   2.当循环的当前层对象是null undefined 类型时 ，将其设置为空字符串

		步骤：1. 判断当前数据类型，调用对应处理方法
			2、arr处理方法：遍历arr，若当前对象是 null，undefined 则置为空字符串；否则继续调用判断当前对象数据类型
			3、obj处理方法：遍历obj，若当前对象是 null，undefined 则置为空字符串；否则继续调用判断当前对象数据类型

		问题及解决方法：该方法仅仅解决数据对象中出现的 null undefined 等不合法数据类型，防止在页面显示的时候 显示错误数据。
		但无法判断当前 数据应该属于数据类型，仅仅单一转化为空字符
		因此：在使用过程中需要根据接口定义数据类型，对特定字段数据 尽心判断 赋予正确数据。


	*/

	//网络请求获取到数据 处理流程方法

	//判断当前对象数据类型
	 handleNetworkDaraFormaFunc(obj:any){

		if (typeof obj == 'string'){
			//处理对象仅为字符串不做处理
		}else if(obj instanceof Array){
			this.handleArrNullArr(obj);
		}else if(obj instanceof Object){
			this.handleObjCurtNullValueFunc(obj);
		}

	}


	 //arr 对象循环处理方法
	 handleArrNullArr(obj:any){
	 	for (var i = 0; i < obj.length; i++) {
	 		//1. obj[i] 对象是string时 遍历对象处理value
			//2.obj[i] 非string对象时 判断类型进行处理
			var objvalue = obj[i];
			if(typeof objvalue ==  "undefined" || objvalue == null){
				obj[i] = this.handleNilString(obj[i]);
			}else{
				 this.handleNetworkDaraFormaFunc(obj[i]);
			}
	 	}



	 }

	//obj 对象循环处理方法
	 handleObjCurtNullValueFunc(myObject:any){

	 	var keysArr:any = Object.keys(myObject);
	 	for (var i = 0; i < keysArr.length; i++) {
	 		var key = keysArr[i];
	 		if(myObject.hasOwnProperty(key)) {
				//1. 当myObject[key] 对象是string时 遍历对象处理value

				//2、当myObject[key] 非string对象时 判断类型进行处理

				var objvalue = myObject[key];
				if(typeof objvalue ==  "undefined" || objvalue == null){
					myObject[key] = this.handleNilString(myObject[key]);

				}else{
					this.handleNetworkDaraFormaFunc(myObject[key]);
				}
      }

	 	}


	 }


	 //空字符串处理方法
	handleNilString(objStr:string){
		var resultStr : string = '';
		if (objStr == undefined || objStr == null ) {
			resultStr = '';
		}else{
			resultStr = objStr;
		}
    return resultStr;
  }

	 //判断当前对象是不是数组，如果是空字符串 则转换成目标对象
	 handleBeforeBindDataType(obj,key,typeObj){
	 	var handleObj = obj[key];
	 	if ((typeof handleObj == 'string') && handleObj == '') {
	 		obj[key] = typeObj;
	 	}

	 }



 setBusinessLocalStatu(obj:any){
	   var resturnObj = {};
    if(obj.loanStatusCode == '01'){
      resturnObj["loanStatusName"] = '暂存';
      resturnObj["operate"] = '申请';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '';
	  resturnObj['supplementaryMateria'] = '';

    }else if(obj.loanStatusCode == '02'){
      resturnObj["loanStatusName"] = '录单中';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '2';
      resturnObj['buisessLocaStatu'] = '进件申请';
	   resturnObj['supplementaryMateria'] = '补充材料';

    }else if(obj.loanStatusCode == '03') {
      resturnObj["loanStatusName"] = '审批中';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '2';
      resturnObj['buisessLocaStatu'] = '风控审批';
      resturnObj['supplementaryMateria'] = '补充材料';
    }else if(obj.loanStatusCode == '04') {
      resturnObj["loanStatusName"] = '制作合同中';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '2';
      resturnObj['buisessLocaStatu'] = '合同制作';
      resturnObj['supplementaryMateria'] = '';

    }else if(obj.loanStatusCode == '05'){
        resturnObj["loanStatusName"] = '待签约公证';
        resturnObj["operate"] = '查看';
        resturnObj["material"] = '1';
        resturnObj['buisessLocaStatu'] = '协议公证';
        resturnObj['supplementaryMateria'] = '';

    }else if(obj.loanStatusCode == '06'){
      resturnObj["loanStatusName"] = '待抵押';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '抵押登记';
      resturnObj['supplementaryMateria'] = '';

    }else if(obj.loanStatusCode == '07'){
      resturnObj["loanStatusName"] = '授信放款中';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '抵押登记';
      resturnObj['supplementaryMateria'] = '';

    }else if(obj.loanStatusCode == '08'){
      resturnObj["loanStatusName"] = '授信完成';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '授信完成';
      resturnObj['supplementaryMateria'] = '';


    }else if(obj.loanStatusCode == '97'){
      resturnObj["loanStatusName"] = '撤单';
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '撤单';
      resturnObj['supplementaryMateria'] = '';
    }else{
      resturnObj["loanStatusName"] = '拒单';//98
      resturnObj["operate"] = '查看';
      resturnObj["material"] = '1';
      resturnObj['buisessLocaStatu'] = '拒单';
      resturnObj['supplementaryMateria'] = '';

    }
    return resturnObj;


  }

  //校验数据格式
   //校验数据格式  判断是否符合当前要求
  //obj-校验对象基本信息（id,位置，偏移量，提示语），校验规则（true-正整数，false-两位小数）
  checkObjFormat(obj:any,flag:boolean){
    var id = obj["id"];
	   //判断当前元素是否存在
    var  vaildObj = document.getElementById(id);
    if(!vaildObj){
      return ;
    }
    var id = obj["id"];
    var myErrorPosition = obj["myErrorPosition"];
    var myMargainLeft = obj["myMargainLeft"];
    var errorText = obj["errorText"];
    var isresidenceNum ;//校验规则

    if (flag){
      isresidenceNum =  /^[1-9]\d*$/;
    }else{
      isresidenceNum = /^\d+(?:\.\d{1,2})?$/;
    }

	  var str = $("#" + id).val();
    var strValue = <number>str;
      if (isresidenceNum.test(str) == false) {
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: '格式错误',
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }else if(strValue>10000){
        $("#" + id).createTip({
          margainLeft: myMargainLeft,
          errorText: '评估价格不可以超过1亿',
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      //去除开头的0
      if (str.indexOf(".") == -1 && !("0" == $.trim(str))) {
        var noZeroVal = str.replace(this.isZero, "");
        $("#" + id).val(noZeroVal);
      }
      $("#" + id).removeTip();
      return true;
  }


    //判断是否为空
  isEmpty(inputId) {
    if (null == $("#" + inputId).val() || '' == $("#" + inputId).val() || '' == $("#" + inputId).val().trim()) {
      return true;
    }
    return false;
  }

  //验证input框
  ValidateId(id, Out, myErrorPosition, myMargainLeft, errorText) {
    $("#" + id).bind({
        focusout: function () {
          Out(id, myErrorPosition, myMargainLeft, errorText);
        },
        focusin: function () {
          $(this).removeTip();
        }
      }
    );
  }

  Validates = {
    checkNull: (inputId, myErrorPosition, myMargainLeft, errorText) => {
      if (this.isEmpty(inputId)) {
        $("#" + inputId).createTip({
          margainLeft: myMargainLeft,
          errorText: errorText,
          errorPosition: myErrorPosition,//参数包括right,top-right,top-left
          errorDisplay: this.tipsErrorDisplay//参数包括hide,show
        });
        return false;
      }
      $("#" + inputId).removeTip();
      return true;
    }

  };



  //价格转换  将当前传入价格*10000返回
  handlePagePrice(objPrice:string){
    var price1Number = <number><any>objPrice * 10000;
    var resultObj = price1Number.toString();
    return resultObj;
  }







}
