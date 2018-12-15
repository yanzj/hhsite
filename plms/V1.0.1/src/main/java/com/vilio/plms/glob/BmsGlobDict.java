package com.vilio.plms.glob;

/**
 * Created by dell on 2017/9/4.
 */
public enum BmsGlobDict {
    //土地性质
    house_land_property_national ("01" ,"国有"),
    house_land_property_collective ("02" ,"集体"),
    //使用权取得方式
    house_acquisition_form_sell ("01" ,"出让"),
    house_acquisition_form_allotted ("02" ,"划拨"),
    house_acquisition_form_transfer ("03" ,"转让"),
    //房屋类型
    house_type_apartment ("01" ,"公寓"),
    house_type_villa ("02" ,"别墅"),
    //共有类型
    house_share_type_several ("01" ,"按份共有"),
    house_share_type_common ("02" ,"共同共有"),
    //房屋使用情况 01、自住;02、已备案出租;03、未备案出租;04、空置
    house_usage_detail_self ("01" ,"自住"),
    house_usage_detail_record_rent ("02" ,"已备案出租"),
    house_usage_detail_unrecord_rent ("03" ,"未备案出租"),
    house_usage_detail_vacancy ("04" ,"空置"),
    //抵押类型
    house_mortgage_type_first ("01" ,"一抵"),
    house_mortgage_type_second ("02" ,"二抵"),
    house_mortgage_type_first_transfer ("03" ,"一抵转单"),
    house_mortgage_type_second_transfer ("04" ,"二抵转单"),
    //收息方式
    fund_side_receive_interest_method_pre ("18" ,"前置收息"),
    fund_side_receive_interest_method_back ("20" ,"后置收息"),
    fund_side_receive_interest_method_fix ("19" ,"固定日收息"),
    //01、余额;02、最高额
    first_balance_type_left ("01" ,"余额"),
    first_balance_type_top ("02" ,"最高额"),
    //债权类型 一般抵押
    creditor_rights_type ("01" ,"一般抵押"),
    //债权类型 最高额抵押
    creditor_rights_max ("02" ,"最高额抵押"),
    //债券性质：银行借贷、民间借贷，
    creditor_property_bank ("01" ,"银行借贷"),
    creditor_property_private ("02" ,"民间借贷"),
    //归档材料表类别
    pigeonhole_info_type_notarization("16","签约公正材料"),
    pigeonhole_info_type_guarantee("17","担保材料"),
    pigeonhole_info_type_insurance("18","保险材料"),
    pigeonhole_info_type_mortgage("19","抵押材料"),
    pigeonhole_info_type_invest_query("20","产调查询材料"),
    pigeonhole_info_type_loan_voucher("21","放款凭证"),
    //证件类型
    certificate_type_idCard("01","身份证"),
    certificate_type_interim_idCard("02","'临时身份证'"),
    certificate_type_birth_certificate("03","'出生证'"),
    //婚姻状况
    marital_status_type_never ("01","未婚"),
    marital_status_type_married ("02","已婚"),
    marital_status_type_divorced ("03","离异"),
    marital_status_type_widowed ("04","丧偶"),
    marital_status_type_bigamy ("05","重婚"),
    marital_status_type_remarry ("06","再婚"),

    ;

    private Object key;
    private String desc;

    BmsGlobDict(Object key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
