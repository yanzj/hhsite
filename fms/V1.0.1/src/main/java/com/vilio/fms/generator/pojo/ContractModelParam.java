package com.vilio.fms.generator.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同上的参数
 */
public class ContractModelParam {

    //进件信息的唯一标识
    private String bmsLoanCode = "";
    //合同抵押方式   01：联抵联押 02：分抵分押
    private String mortgageType = "";
    //主借款人姓名
    private String customerName = "";

    //主借款人证件号码
    private String customerIdNo = "";
    //主借款人地址
    private String customerAddress = "";
    //主借款人联系方式
    private String customerPhone = "";

    //借款期限名称
    private String loanPeriodName = "";
    //放款方式名称
    private String creditTypeName = "";
    //产品名称
    private String productName = "";
    //申请金额
    private String loanAmount = "";
    //意向金
    private String intentionMoney = "";

    //业务编号
    private String businessCode = "";
    //放款账户开户行
    private String loanAccountBank = "";
    //放款账户名
    private String loanAccountName = "";
    //放款账号
    private String loanAccount = "";

    //借款人列表
    List<ContractUser> borrowerList = new ArrayList<>();
    //抵押物列表
    List<ContractMortgage> mortgageList = new ArrayList<>();

}
