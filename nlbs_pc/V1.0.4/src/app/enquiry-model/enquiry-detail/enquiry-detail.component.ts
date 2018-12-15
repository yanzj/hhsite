import {Component, OnInit} from '@angular/core';
import {NetworkService} from "../../network.service";
import {User} from "../../User.service";
import {commonService} from "../../commonService.service";
import {Router} from "@angular/router";
import {TaskRunner} from "protractor/built/taskRunner";
import {escape} from "querystring";
import {environment} from "../../../environments/environment";
import {ComSectionComponent} from "../../common-model/com-section/com-section.component";

@Component({
  selector: 'app-enquiry-detail',
  templateUrl: './enquiry-detail.component.html',
  styleUrls: ['./enquiry-detail.component.css']
})
export class EnquiryDetailComponent implements OnInit {
  isL: string = '0';// 0-显示内容 1-显示不支持字眼 2-完全不显示
  isR: string = '0';// 0-显示内容 1-显示不支持字眼 2-完全不显示
  lHouseNo: string = '001';
  rHouseNo: string = '001';
  lValue: string;
  rValue: string;

  cityName: string;
  houseName: string;
  //评估时间
  assessmentTime: string;
  //评估状态的值
  assessmentStatus: string;
  //显示万元的单位
  PriceStatus:boolean;
  //是否显示跳转的提示
  isGotoPerson: any = [];
  //人工显示
  persionEnquiryedTitleList = ['评估价'];

  //公寓评估结果 标题项目列表
  gyEnquiryedTitleList = ["小区地址", "楼栋号", "房号", "总楼层", "所在楼层", "建筑面积", "评估结果"];

  //别墅评估结果 标题项目列表
  bsEnquiryedTitleList = ["行政区域", "房屋地址", "建筑面积", "评估结果"];

  //左边标题列表
  leftDisplayTitleList: any = [];
  //右边标题列表
  rightDisplayTitleList: any = [];

  //通过不同的方法去询价详情获得询价的
  enquireStatus:string;

  //判断是不是人工询价
  //1，公寓自动 2，别墅自动 3，人工或人工介入
  isPersonEnquiry:string;

  //如果是人工询价的话   则显示房屋地址
  address:string;

  //false返回重新询价  true返回列表
  pathStatus:boolean;
  //何种路劲进入的询价页面  如何返回
  path:string;

  //左边显示的结果
  leftValueList: any = ["", "", "", "", "", "", ""];
  //右边显示的结果
  rightValueList: any = ["", "", "", "", "", "", ""];

  //人工上传显示 上传图片
  isShow:boolean = false;
  isShow2:boolean = false;
  imgSrc:string = '';
  imgSrc2:string = '';
  fileName:string = '';
  fileName2:string = '';
  uploadTime:string = '';
  uploadTime2:string = '';

  constructor(private networkService: NetworkService,
              private user: User,
              private commonFunc: commonService,
              private router: Router,
              private comSectionComponent: ComSectionComponent) {
  }

  //页面当前流程状态显示
  itemStatusLists: any = ["房产估价", "进件申请", "风控审批", "合同制作", "协议公证", "抵押登记", "授信完成"];
  itemSelectStatus: string = '房产估价';
  itemSelectIndex: number = 0;//初始化

  setSelectIndex() {
    for (let i = 0; i < this.itemStatusLists.length; i++) {
      if (this.itemStatusLists[i] == this.itemSelectStatus) {
        this.itemSelectIndex = i;
      }
    }
  }

  ngOnInit() {
    this.comSectionComponent.currentLevelMenuId = '2017070315180000000002';
    this.comSectionComponent.currentChildMenuId = '2017070315180000000005';

    //请求询价详情数据
    this.InitEnquiryDataFunc();
  }
  enquireSearch(){
    this.router.navigate(['/home/enquirySearch'])
  }
  history(){
    this.commonFunc.setHistroyStatus('enquiryDetail');
    this.router.navigate(['/home/enquiryHistorySearch'])
  }
  // 详情初始化的请求 获得
  InitEnquiryDataFunc() {

    //初始化的时候就获得是从何种路径进入的

    this.path=this.commonFunc.getEnquiryPath();
    if(this.path=='gy'||this.path=='villa'||this.path=='person'){
      this.pathStatus=false
    }else {
      this.pathStatus=true;
    }
    //清楚缓存
    this.commonFunc.removeEnquiryPath();

    //获得通过哪里调到当前的详情页  01==》自动  02===》人工  03==》详情
    this.enquireStatus=this.commonFunc.getEnquiryStatus();
    if(this.enquireStatus=='03'){
      this.commonFunc.setResubmitStatus("enquirySearch");
    }
    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    var enquiryObj = this.commonFunc.getBusinessData();
    var userNo = userObj.userNo;
    var serialNo = enquiryObj.serialNo;

    var Obj = {
      'head': {
        'functionNo': 'HH000120',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'serialNo': serialNo
      }
    };
    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法
        if (this.networkService.onJudgeSuccessful(res)) {

          this.commonFunc.handleNetworkDaraFormaFunc(res);
          //获得城市
          this.cityName = res.data.body.cityName;
          //获得房屋类型
          this.houseName = res.data.body.houseTypeName;
          //评估时间
          this.assessmentTime = res.data.body.assessmentTime;
          //获得询价的类型
          this.isPersonEnquiry = res.data.body.modelType;

          this.address=res.data.body.address;

          var status = res.data.body.assessmentStatus;

          if (status == '已评估') {
            if (res.data.body.assessmentPrice) {
              this.assessmentStatus = res.data.body.assessmentPrice;
              this.PriceStatus=true;
            } else {
              this.assessmentStatus = '已评估';
            }
          } else {
            this.assessmentStatus = res.data.body.assessmentStatus;
          }

          var companyParamList:any = res.data.body.companyParamList;
          if(companyParamList.length > 0){
            this.isGotoPerson[0] = res.data.body.companyParamList[0].status;

          }
           if(companyParamList.length >1){
            this.isGotoPerson[0] = res.data.body.companyParamList[0].status;
            this.isGotoPerson[1] = res.data.body.companyParamList[1].status;
          }
          this.setUIData(res.data.body);
        }

      }
    });
  }


  //测试数据
  setUIData(obj: any) {
    var objList = obj.companyParamList;
    var modelType = obj['modelType'];
    //设置左右显示情况
    this.setCompangDisplayFunc(objList, modelType);
    //当左边显示的时候 左边标题和内容
    if (this.isL == '0') {
      //判断别墅 公寓 人工
      this.setTitleListDisplayContentFunc(true, modelType);
      this.setValueDisplayFunc(true, modelType, objList[0]);
     //判断当前是人工查询的时候 显示图片
     //获得通过哪里调到当前的详情页  01==》自动  02===》人工  03==》详情
    if (modelType == '3'||modelType == '2') {
      if (objList.length > 0) {
        if(objList[0]['fileName']){
          this.isShow = true;
          this.imgSrc = environment.server + objList[0]['fileNo'];
          this.fileName = objList[0]['fileName'];
          this.uploadTime = objList[0]['fileUploadTime'];
        }
       }
    }

    } else if (this.isL == '1') {
      //leftDetailDIV
      this.setUndisplayUI("leftDetailDIV");
    } else {

    }
    //当右边显示的时候 右边标题和内容
    if (this.isR == '0') {
      //判断别墅 公寓 人工
      this.setTitleListDisplayContentFunc(false, modelType);
      this.setValueDisplayFunc(false, modelType, objList[1]);

      //判断当前是人工查询的时候 显示图片
      //获得通过哪里调到当前的详情页  01==》自动  02===》人工  03==》详情
      if (modelType == '3'||modelType == '2') {
         if (objList.length >= 2) {
           if(objList[1]['fileName']){
             this.isShow2 = true;
             this.imgSrc2 = environment.server + objList[1]['fileNo'];
             this.fileName2 = objList[1]['fileName'];
             this.uploadTime2 = objList[1]['fileUploadTime'];
           }
       }
      }

    } else if (this.isR == '1') {
      //rightDetailDIV
      this.setUndisplayUI("rightDetailDIV");
    } else {

    }


  }

  //设置当前不支持状态显示
  setUndisplayUI(divID: string) {
    document.getElementById(divID).innerHTML = '<div class="col-12">不支持</div>';
  }

  //设置标题显示列表内同
  //判断isLeft：true-左边显示  false-右边显示  isGY：0-自动询价公寓 1-自动询价别墅 2-人工

  //1  gy   2  bs  0,人工
  setTitleListDisplayContentFunc(isLeft: boolean, isGY: string) {
    if (isLeft) {

      if (isGY == '1') {
        this.leftDisplayTitleList = this.gyEnquiryedTitleList;
      } else if (isGY == '2') {
        this.leftDisplayTitleList = this.bsEnquiryedTitleList;

      } else {
        this.leftDisplayTitleList = this.persionEnquiryedTitleList;

      }

    } else {
      if (isGY == '1') {
        this.rightDisplayTitleList = this.gyEnquiryedTitleList;
      } else if (isGY == '2') {
        this.rightDisplayTitleList = this.persionEnquiryedTitleList;
      } else {
        this.rightDisplayTitleList = this.persionEnquiryedTitleList;

      }
    }

  }

  //设置询价内容显示列表
  //判断isLeft：true-左边显示  false-右边显示y  isGY：0-自动询价公寓 1-自动询价别墅 2-人工
  // 公寓- ["小区地址","楼栋号","房号","总楼层","所在楼层","建筑面积","评估结果"];
  //别墅 -["行政区域","房屋地址","建筑面积","评估结果"];
  //人工 - ['评估价']
  setValueDisplayFunc(isLeft: boolean, isGY: string, obj: any) {
    if (isLeft) {
      if (isGY == '1') {
        var plotsName = this.commonFunc.handleNilString(obj.plotsName);
        var unitName = this.commonFunc.handleNilString(obj.unitName);
        var houseName = this.commonFunc.handleNilString(obj.houseName);
        var totalFloor = this.commonFunc.handleNilString(obj.totalFloor);
        var currentFloor = this.commonFunc.handleNilString(obj.currentFloor);
        var area = this.commonFunc.handleNilString(obj.area);
        var status = this.commonFunc.handleNilString(obj.status);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }
        }

        //如果有建筑面积的话，需要加上平方米的单位
        if(area!=''){
          area=area+'  平方米';
        }

        this.leftValueList = [plotsName, unitName, houseName, totalFloor, currentFloor, area, status];
      } else if (isGY == '2') {
        var areaName = this.commonFunc.handleNilString(obj.areaName);
        var address = this.commonFunc.handleNilString(obj.address);
        var area = this.commonFunc.handleNilString(obj.area);
        var status = this.commonFunc.handleNilString(obj.status);


        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }
        }

        //如果有建筑面积的话，需要加上平方米的单位
        if(area!=''){
          area=area+'  平方米';
        }

        this.leftValueList = [areaName, address, area, status];
      } else {
        var status = this.commonFunc.handleNilString(obj.status);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }

        }
        this.leftValueList = [status];
      }
    } else {
      if (isGY == '1') {
        var plotsName = this.commonFunc.handleNilString(obj.plotsName);
        var unitName = this.commonFunc.handleNilString(obj.unitName);
        var houseName = this.commonFunc.handleNilString(obj.houseName);
        var totalFloor = this.commonFunc.handleNilString(obj.totalFloor);
        var currentFloor = this.commonFunc.handleNilString(obj.currentFloor);
        var area = this.commonFunc.handleNilString(obj.area);
        var status = this.commonFunc.handleNilString(obj.status);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2) +'  万元';
          }

        }
        //如果有建筑面积的话，需要加上平方米的单位
        if(area!=''){
          area=area+'  平方米';
        }
        this.rightValueList = [plotsName, unitName, houseName, totalFloor, currentFloor, area, status];
      } else if (isGY == '2') {
        var areaName = this.commonFunc.handleNilString(obj.areaName);
        var address = this.commonFunc.handleNilString(obj.address);
        var area = this.commonFunc.handleNilString(obj.area);
        var status = this.commonFunc.handleNilString(obj.status);



        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2) +'  万元';
          }
        }
        //如果有建筑面积的话，需要加上平方米的单位
        if(area!=''){
          area=area+'  平方米';
        }
        // this.rightValueList = [areaName, address, area, status];
        this.rightValueList = [status];

      } else {
        var status = this.commonFunc.handleNilString(obj.status);
        if(status == '已评估'){
          var price =  this.commonFunc.handleNilString(obj.price);
          if(price != ''){
            // status = <number><any>price;
            var n= <number><any>price/10000;
            status = n.toFixed(2)+'  万元';
          }

        }
        this.rightValueList = [status];
      }
    }
  }


  //设置当前页面显示几个估价公司
  //待评估 评估失败 ：点击     0-显示内容 1-显示不支持字眼 2-完全不显示
  //modelType 1-公寓自动询价 2-别墅自动询价 3-人工
  setCompangDisplayFunc(objList: any, modelType: string) {
    // var modelType = '';
    if (objList.length == 0) {
      this.isL = '2';
      this.isR = '2';
    }
    if (objList.length == 1) {

      this.lValue = objList[0].companyName;
      this.lHouseNo = objList[0].companyCode;

      this.isL = '0';
      this.isR = '2';

      /* add by fenglz --start */
      if (this.lHouseNo == '001') {
        //世联
        this.lValue = "世联评估";
        // if(modelType == '1'){
        //   this.isL = '0';
        //   this.isR = '2';
        // } else if(modelType == '2'){
        //   this.isL = '0';
        //   this.isR = '1';
        // } else if(modelType == '3'){
        //   this.isL = '2';
        //   this.isR = '2';
        // }
      } else if (this.lHouseNo == '002') {
        //城市
        this.lValue = "城市评估";
        // if(modelType == '1'){
        //   this.isL = '0';
        //   this.isR = '2';
        // } else if(modelType == '2'){
        //   this.isL = '1';
        //   this.isR = '2';
        // } else if(modelType == '3'){
        //   this.isL = '2';
        //   this.isR = '2';
        // }
      }
      /* add by fenglz --end */
    }
    if (objList.length == 2) {
      this.isL = '0';
      this.isR = '0';

      this.lValue = objList[0].companyName;
      this.rValue = objList[1].companyName;

      this.lHouseNo = objList[0].companyCode;
      if (this.lHouseNo == '001') {
        this.lValue = '世联评估'
      }
      if (this.lHouseNo == '002') {
        this.lValue = '城市评估'
      }
      this.rHouseNo = objList[1].companyCode;
      if (this.rHouseNo == '001') {
        this.rValue = '世联评估';
      }
      if (this.rHouseNo == '002') {
        this.rValue = '城市评估';
      }

      /* add by fenglz --start */

      // if(this.lHouseNo == '001'){
      //   //世联
      //   if(modelType == '1'){
      //     this.isL = '0';
      //     this.isR = '0';
      //   } else if(modelType == '2'){
      //     this.isL = '0';
      //     this.isR = '1';
      //   } else if(modelType == '3'){
      //     this.isL = '2';
      //     this.isR = '2';
      //   }
      // } else if (this.lHouseNo == '002'){
      //   //城市
      //   if(modelType == '1'){
      //     this.isL = '0';
      //     this.isR = '0';
      //   } else if(modelType == '2'){
      //     this.isL = '1';
      //     this.isR = '0';
      //   } else if(modelType == '3'){
      //     this.isL = '2';
      //     this.isR = '2';
      //   }
      // }
      /* add by fenglz --end */

    }
  }

  // 返回询价查询页面
  onBackEnquiryBtnClick() {
    if(this.pathStatus){
      this.router.navigate(['/home/enquirySearch']);
    }else {
      if(this.path=='gy'||this.path=='villa'){
        this.router.navigate(['/home/enquiry']);
      }
      if(this.path=='person'){
        this.router.navigate(['/home/personEnquiry']);
      }
    }
  }

}
