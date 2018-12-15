import {Component, OnInit, SimpleChange} from '@angular/core';
import {repaymentPlanModifyDetailService} from "./repayment-plan-modify-detail.service";
import {withIdentifier} from "codelyzer/util/astQuery";
import {NetworkService} from "../../network.service";
import {commonService} from "../../commonService.service";
import {User} from "../../User.service";
import {ActivatedRoute, Router} from "@angular/router";
declare var $: any;
@Component({
  selector: 'app-repayment-plan-modify-detail',
  templateUrl: './repayment-plan-modify-detail.component.html',
  styleUrls: ['./repayment-plan-modify-detail.component.css'],
  providers:[repaymentPlanModifyDetailService]
})
export class RepaymentPlanModifyDetailComponent implements OnInit {

  constructor(
    private activetedRoute: ActivatedRoute,
    private repaymentPlanModifyDetail:repaymentPlanModifyDetailService,
    private networkService: NetworkService,
    private user: User,
    private commonFunc: commonService,
    private router: Router,
  ) { }
  selectPageNo: string = '1';//当前选中页数
  pageSize: string = '20';//分页 每页请求个数

  loanBusinessData:any={};
  //传给子组件的值
  repaymentSchedule:any={
    };

  professionalData:any=[];
  //用来标记  原放款记录是否发生变更 1.如果点击了添加，删除，修改按钮  则改为true   点击提交按钮以后改为false
  reCordFlag:boolean=false;
  //下拉列表的值
  recordValue:string;
  recordValue2:string='';//用来记录select框之前的值
  paidCode:string;
  ngOnInit() {
    $("#recordLending").select2({});
    //初始化接口
    this.initDataFunc();

    //根据逻辑判断是否要显示弹框的提示


    $("#recordLending").change(()=>{
      // 下拉选项发生改变时， ？是否要通知后台？
      if(this.reCordFlag){//若原放款记录选项下的还款计划发生了变更但未提交，弹出窗口提示
        //如果点击确定  原修改计划将会被取消  则根据改变的值发送请求
        //如果点击取消  这个时候select的值已经发生了改变  则变回原来选中的值
        this.ensureButton("还款计划修改提醒", "当前借款合同下的另一笔放款记录下的还款计划已修改但未提交，变更选项后原修改记录将取消，是否确认变更？");

      }else{
        this.recordValue2=$("#recordLending").val();
        this.paidCode=this.recordValue2;
        this.QueryEntryList(this.paidCode);
      }


      //下拉框的选项发生改变的时候要向后台发送请求

    })
  }

  ensureButton(tipTitle, tiptxt){
    var that =this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: true,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {// 原修改计划将会被取消  则根据改变的值发送请求
      that.reCordFlag=false;
      //  发送分页请求  并且记录select2的值
      that.recordValue2=$("#recordLending").val();
      that.paidCode=that.recordValue2;
      that.QueryEntryList(that.paidCode);
      $(this).closeModal();

      return true;
    }

    function  myCloseImageClickCallback(){
      //  发送分页请求  将数值改为之前的数值
      that.recordValue=that.recordValue2;
      that.paidCode=that.recordValue2;
      $("#recordLending").val(that.recordValue2).trigger("change");
      that.QueryEntryList(that.paidCode);
      $(this).closeModal();
      return true;
    }

  }

  userNo:string;
  contractCode:string;
  //初始化接口  获得loanBusinessData的值（包括列表和下拉框）
  initDataFunc(){
    var loginTokenKey = this.commonFunc.getloginToken();
    var userObj = this.user.getUserData();
    this.userNo = userObj.userNo;
    this.contractCode = this.activetedRoute.snapshot.params['id'];

    var Obj = {
      'head': {
        'functionNo': 'plms100021',
        'userNo': this.userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'pageSize':this.pageSize,
        'pageNo':this.selectPageNo,
        'contractCode':this.contractCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        if (this.networkService.onJudgeSuccessful(res)) {
          this.loanBusinessData=res.data.body;
          //获取到数据以后   借款合同渲染  列表初始化
          this.professionalData=[
            {content:'借款合同编号：',styleName:'message-title font-weight-bold'},
            {content:this.loanBusinessData.contractNo,styleName:'message-number'}
          ];


            this.add_option(this.loanBusinessData.paidInfoList, '#recordLending', ['paidCode', 'paidDesc']);
          if(this.loanBusinessData.paidInfoList.length==1){//如果长度是1的话  默认选中
            this.recordValue=this.loanBusinessData.paidInfoList[0].paidCode;
            this.recordValue2=this.recordValue;
            this.paidCode=this.recordValue2;

            //  发送分页请求
            this.QueryEntryList(this.paidCode);

          }
        }

      }

    });


  }


  getData(event){
    this.reCordFlag=event;
  }

  //当删除  添加 修改成功以后  需要重新刷新页面
  needLoadAgain:boolean=false;
  getData2(event){
    this.needLoadAgain=event;
    if(this.needLoadAgain){
    //  再次发送分页请求
      this.QueryEntryList(this.paidCode);
    }
  }

  //绑定数据 待重构该部分代码
  add_option(array, id, arr) {
    var x;
    for (x in array) {
      var name, code;
      for (var y in array[x]) {
        if (y == arr[0]) {
          code = array[x][y];
        } else if (y == arr[1]) {
          name = array[x][y];
        }
        ;
      }
      if (x == 0) {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      } else {
        $("<option>" + name + "</option>").attr({value: code}).appendTo(id);
      }
    }
  }
  totalListsCount: string = '';//总条数
  startListIndex: string = '';//当前开始页数
  endListIndex: string = '';//当前结束页数
  totalItemLists = new Object();//当前获取到的所有列表内容，key为对应的当前页数
  QueryEntryList(paidCode){
    var paidCode=paidCode;
    var loginTokenKey = this.commonFunc.getloginToken();

    var Obj = {
      'head': {
        'functionNo': 'plms100022',
        'userNo': this.userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'pageSize':this.pageSize,
        'pageNo':this.selectPageNo,
        'paidCode':paidCode
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        if (this.networkService.onJudgeSuccessful(res)) {
          this.repaymentSchedule['repaymentList']=res.data.body.repaymentList;

          //开始处理分页
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
          this.setPageFunc(objData, this);
          this.setDisplayItemsCount();

        }
      }
  });
}
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

  //点击提交按钮
  getModify() {
    //方款记录已经发生了变更  并且把改变的值发送给父组件
    this.reCordFlag = false;
  //  提交修改的时候   与后台进行交互
    var loginTokenKey = this.commonFunc.getloginToken();
    var Obj = {
      'head': {
        'functionNo': 'plms100027',
        'userNo': this.userNo,
        'clientTimestamp': loginTokenKey
      },
      'body': {
        'userNo': this.userNo,
        'contractCode':this.contractCode,
      }
    };
    this.networkService.postData(Obj, false).then(res => {
      if (res.retCode == true) {
        if (this.networkService.onJudgeSuccessful(res)) {
          if(res.data.head.returnCode=='0000'){
            this.popNextSuccessModal("还款计划修改提交成功","还款计划修改提交成功，后台正在处理，请勿重复提交。",false,"秒后自动返回还款计划修改页面")
          }else {
            this.ensureButtonClick("还款计划修改错误","应还本金总额或应还利息总额错误，请重新修改",false);
          }
        }else {

        }
      }
    });
  }

  goBack(){
    //  点击取消按钮的话   则会弹出提示框
    this.cancelButton("取消还款计划修改","当前还款计划已修改但未提交，确定返回还款计划修改页面",true);
  }


  cancelButton(tipTitle, tiptxt,flag:boolean){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      that.router.navigate(['/home/repaymentPlanModifyQuery']);
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }




  }

  //    若修改成功
  popNextSuccessModal(tipTitle:string, tipContent:string,flag:boolean,text:string) {
    var that=this;
    var content = "<div style='margin-bottom: 50px; font-weight: bold;'>" + tipContent + "<span id='secondValue'>5</span>"+text+"。</div>";
    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton:flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      cancelButtonClickCallback:myCancelButtonClickCallback,
      closeImageClickCallback: myCancelButtonClickCallback
    });
    function myConfirmButtonClickCallback() {
      clearInterval(reduceSecondValue);
      $(this).closeModal();
      that.router.navigate(['/home/repaymentPlanModifyQuery']);
      return true;
    }
    function myCancelButtonClickCallback(){
      clearInterval(reduceSecondValue);
      $(this).closeModal();
    }
    //处理弹出窗口衰减
    var reduceSecondValue = setInterval(
      function () {
        var secondValue = $("#secondValue").text();
        if (1 == secondValue) {
          that.router.navigate(['/home/repaymentPlanModifyQuery']);
          $(this).closeModal();
          clearInterval(reduceSecondValue);
        } else {
          $("#secondValue").text(secondValue - 1);
        }
      },
      1000
    );
  }

  //  前端校验无误后向后台发送请求   校验不通过的话则弹窗提示
  ensureButtonClick(tipTitle, tiptxt,flag:boolean){
    var that=this;
    var content = `<div style='margin-bottom: 50px; font-weight: bold;'>${tiptxt}</div>`;

    $(this).popModal({
      width: '360px',
      title: tipTitle,
      content: content,
      confirmButton: true,
      cancelButton: flag,
      closeImage: true,
      confirmButtonClickCallback: myConfirmButtonClickCallback,
      closeImageClickCallback:myCloseImageClickCallback,
      cancelButtonClickCallback:myCloseImageClickCallback,
    });

    function myConfirmButtonClickCallback() {
      $(this).closeModal();
      return true;
    }

    function  myCloseImageClickCallback(){
      $(this).closeModal();
      return true;
    }




  }

  // 设置页面显示 当前进件条数页数
  setDisplayItemsCount() {
    //页面显示条数与当前条数
    var currentPage = parseInt(this.selectPageNo) - 1;
    var pagesize = parseInt(this.pageSize);
    this.startListIndex = <string><any>(currentPage * pagesize) + 1;
    var currentObj: any = this.totalItemLists[this.selectPageNo];
    this.endListIndex = <string><any>(currentPage * pagesize + this.repaymentSchedule.repaymentList.length);
    if (this.endListIndex == '0') {
      this.startListIndex = '0';
    }
  }


  //分页数据处理
  setCurrentDataFunc(page: string) {
    //设置当前选中页数
    this.selectPageNo = page;

    //1.根据当前请求页 查询totalItems中是否存在这条数据 存在则直接赋值与current
    var pageKey: string = page;
    if (this.totalItemLists.hasOwnProperty(pageKey)) {
      var itemObj = this.totalItemLists[pageKey];
      this.repaymentSchedule.repaymentList = itemObj;

      //显示第几条数据
      this.setDisplayItemsCount();
      return;
    }

    //2.请求当前页获取到的数据
     this.QueryEntryList(this.paidCode);

    //3.存入totalItems中，并赋值current


  }
  querySectionFun(){
    this.router.navigate(['/home/loanBusiness'])
  }

}
