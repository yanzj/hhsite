import {Component, Input, OnInit} from '@angular/core';
import {NetworkService} from "../../../network.service";
import {User} from "../../../User.service";
import {commonService} from "../../../commonService.service";
import {Router} from "@angular/router";

declare var $: any;

@Component({
  selector: 'app-repay-schedule',
  templateUrl: './repay-schedule.component.html',
  styleUrls: ['./repay-schedule.component.css']
})
export class RepayScheduleComponent implements OnInit {

  @Input() contractCode:any;
  constructor(
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }

  repaymentList:any=[];
  currentItemLists: any[] = [];//当前显示分页内容
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '20';//分页 每页请求个数
  creditStartDate: string = '';//开始时间
  creditEndDate: string = ''; //结束时间
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数

  ngOnInit() {
    this.QueryEntryList();
  }


//贷款业务查询列表接口
  QueryEntryList=() =>{
    var loginTokenKey = this.commonFunc.getloginToken();
    var userNo = this.user.getUserData().userNo;

    var Obj = {
      'head': {
        'functionNo': 'plms100035',
        'userNo': userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': userNo,
        'pageSize': this.pageSize,
        'pageNo': this.selectPageNo,
        'contractCode':this.contractCode
      }
    };



    this.networkService.postData(Obj, false).then(res => {

      if (res.retCode == true) {
        // 请求网络通信处理方法

        if (this.networkService.onJudgeSuccessful(res)) {


          //登录成功处理逻辑
          var pages = this.commonFunc.handleNilString(res.data.body.pages);
          if (pages == '') pages = '0';
          var currentPage = this.commonFunc.handleNilString(res.data.body.currentPage);
          if (currentPage == '') currentPage = '0';

          var objData = {
            'totalPage': pages,
            'currentPage': currentPage,
            'total': res.data.body.total

          };

          //设置页面显示 第几条至第几条
          this.totalListsCount = res.data.body.total;

          //首次获取分页信息 设置分页插件
          //  if(this.currentItemLists.length == 0){
          this.setPageFunc(objData, this);

          //  }
          this.currentItemLists = res.data.body.repaymentSchedule;
          this.repaymentList=res.data.body.repaymentSchedule.repaymentList
          this.setDisplayItemsCount();


          //修改查询列表状态 暂时前端映射 待需求确认之后 以后台为准
          //material 充材料为 1-不显示  2-显示

          // for (var i = 0; i < this.currentItemLists.length; i++) {
          //   var obj = this.currentItemLists[i];
          //   var resultObj = this.commonFunc.setBusinessLocalStatu(obj);
          //   this.currentItemLists[i]["statusName"] = resultObj['statusName'];
          //
          //
          // }

          this.totalItemLists[this.selectPageNo] = this.repaymentList;


        }
      }

    });
    return false;
  }


  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    var currentObj: any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.repaymentList.length);
    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }

  }


  //贷款业务查询列表end
  // 初始化分页工具
  setPageFunc(data: any, obj: any) {

    //设置分页插件
    $(".tablePageCode").createPage({
      pageCount: data.totalPage,
      current: this.selectPageNo,
      backFn: function (p) {
        //p当前页


        obj.setCurrentDataFunc(p.toString());

      }
    });

  }

  //分页数据处理
  setCurrentDataFunc(page: string) {
    //设置当前选中页数
    this.selectPageNo = page;

    //1.根据当前请求页 查询totalItems中是否存在这条数据 存在则直接赋值与current
    var pageKey: string = page;
    if (this.totalItemLists.hasOwnProperty(pageKey)) {
      var itemObj = this.totalItemLists[pageKey];
      this.repaymentList = itemObj;

      //显示第几条数据
      this.setDisplayItemsCount();
      return;
    }

    //2.请求当前页获取到的数据
    this.QueryEntryList();

    //3.存入totalItems中，并赋值current


  }

  //主按钮链接
  querySection(){
    this.router.navigate(["/home/loanBusiness"])
  }
}
