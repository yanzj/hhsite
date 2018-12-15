package com.vilio.plms.glob;

public enum GlobDict {

	//订单状态（借款申请状态）
	order_status_loan_ing("01","放款中"),
	order_status_audity_ing("02","审核中"),
	order_status_loan_succ("03","放款成功"),
	order_status_audity_fail("04","审核未通过"),

	//还款申请
	repayment_apply_status("02","还款确认中"),
	repayment_apply_status_pay_success("01","还款成功"),
	repayment_apply_status_pay_false("03","还款失败"),


	//借款审批状态
	borrow_check_succ("01","审批通过"),
	borrow_check_fail("02","审批未通过"),


	//在途订单类型
	transit_order_borrow("01","借款"),
	transit_order_paid("02","还款"),

	//产品是否可循环
	product_circle("1","可循环"),
	product_uncircle("0","不可循环"),

	//证件类型
	id_type_id_card("01","身份证"),
	id_type_temp_id_card("02","临时身份证"),
	id_type_birth_card("03","出生证"),


	//首笔放款是否借款最高额
	is_first_max("1","是"),
	no_is_first_max("0","否"),

	//期数是否可选
	is_period_select("1","可选"),
	is_period_unselect("0","不可选"),

	//还款方式
	first_interest("01","先息后本"),
	confirm_interest("02","固定收息"),
	after_interest("03","后息后本"),

	//产品表
	product_repayment_methods_first_interest("01","先息后本"),
	//产品表- 放款天数计算规则
	product_paid_days_computational_rule_both("01","算头算尾"),
	product_paid_days_computational_rule_only_begin("02","算头不算尾"),
	//产品表- 息差是否一次性收取
	product_is_spread_one_time_yes("1","是"),
	product_is_spread_one_time_no("0","否"),

	//操作历史类型（操作历史专用）
	audit_sull("02","审批通过"),
	audit_fail("03","审批未通过"),
	receipts_init("04","资金入账初始化入库"),
	receipts_delete("05","资金入账删除"),


	//数据状态
	valid("1","有效"),
	un_valid("0","无效"),

	//是否结清
	cleared("02","已结清"),
	uncleared("01","未结清"),

	//是否代收付
	instead("1","代收代付"),
	un_Instead("0","不代收代付"),

	//利率调整方式
	interesting_adjust_fixation("01","固定利率"),

	//还款明细表科目
	repayment_detail_subject_prepayment_penalty("1","保证金违约金"),
	repayment_detail_subject_service_fee_penalty("2","服务费违约金"),
	repayment_detail_subject_interest_overdue("3","利息罚息"),
	repayment_detail_subject_principal_overdue("4","本金罚息"),
	repayment_detail_subject_bail("5","保证金"),
	repayment_detail_subject_service_fee("6","服务费"),
	repayment_detail_subject_interest("7","利息"),
	repayment_detail_subject_principal("8","本金"),
	repayment_detail_subject_prepayment_interest_penalty("9","提前还款利息违约金"),
	repayment_detail_subject_prepayment_service_fee_penalty("10","提前还款服务费违约金"),
	repayment_detail_subject_overdue("23","罚息"),//仅供前端展示用，不作为数据库存储使用

	//还款明细表状态
	repayment_detail_status_avaliable("1","有效"),
	repayment_detail_status_unavaliable("0","无效"),


	//还款计划表状态
	repayment_schedule_status_unavaliable("00","无效"),
	repayment_schedule_status_paid("01","已结清"),
	repayment_schedule_status_paying_and_not_overdue("02","正常还款中"),
	repayment_schedule_status_overdue("03","逾期中"),

	//账户明细表状态
	account_detail_status_unavaliable("0","无效"),
	account_detail_status_avaliable("1","有效"),
	account_detail_confirmed_confirmed("02", "已确认"),
	account_detail_confirmed_unconfirmed("01", "未确认"),

	//账户表状态
	account_status_avaliable("1","有效"),
	account_status_unavaliable("0","无效"),

	//罚息表状态
	overdue_status_paying("02","未结清"),
	overdue_status_paid("01","已结清"),
	//罚息表科目
	overdue_subject_principal("01","本金罚息"),
	overdue_subject_interest("02","利息罚息"),
	overdue_subject_fee("03","服务费违约金"),
	overdue_subject_bail("04","保证金违约金"),


	//还款计划明细表状态
	repayment_schedule_detail_status_unvalid("00","无效"),
	repayment_schedule_detail_status_paid("01","已结清"),
	repayment_schedule_detail_status_paying_and_not_overdue("02","正常还款中"),
	repayment_schedule_detail_status_overdue("03","逾期中"),
	//未结清 仅作为业务展示状态，数据库中该状态包含 正常还款中和逾期中
	repayment_schedule_detail_status_paying("04","未结清"),

	//计息周期
	product_interest_cycle_month("02","月"),
	product_interest_cycle_day("01","天"),

	/* 收息方式 - */
	interest_collection_method_preposition("01","前置收息"),
	interest_collection_method_postposition("02","后置收息"),
	interest_collection_method_fixed_day("03","固定日收息"),


	//账户类型
	account_type_vilio("00","宏获账户"),
	account_type_fund_side("01","资方账户"),

	//账户类型-简写
	account_type_abbr_vilio("00","宏获"),
	account_type_abbr_fund_side("01","资方"),

	//资金入账处理状态
	receipts_deal_stauts_init("00","待处理"),
	receipts_deal_stauts_ing("01","处理中"),
	receipts_deal_stauts_succ("02","处理成功"),
	receipts_deal_stauts_fail("03","处理失败"),
	receipts_deal_stauts_delete("04","删除待处理"),
	receipts_deal_stauts_delete_ing("05","删除处理中"),
	receipts_deal_stauts_delete_fail("06","删除失败"),

	account_item_id("ACCOUNT_LOCK","账务类操作科目ID"),
	account_lock("Y","账务类操作锁定"),
	account_unlock("N","账务类操作没有锁定"),

	//罚息信息状态
	overdue_status_cleared("01","已结清"),
	overdue_status_uncleared("02","未结清"),
	overdue_status_unvalid("00","无效"),

	pay_schedule_job_item_id("PAY_SCHEDULE_JOB","扣款操作科目ID"),
	calculate_overdue_interest_item_id("CALCULATE_OVERDUE_INTEREST_JOB","计算逾期罚息操作科目ID"),

	//01、实际逾期金额单利;02、实际逾期金额复利；03、本金单利；04、本息单利
	actual_overdue_simple_interest("01","实际逾期金额单利"),
	actual_overdue_compound_interest("02","实际逾期金额复利"),
	principal_simple_interest("03","本金单利"),
	principal_interest_simple_interest("04","本息单利"),

	//归档材料类型
	pigeonhole_type_sign("01","签约公证材料"),
	pigeonhole_type_guarantee("02","担保材料"),
	pigeonhole_type_insurance("03","保险材料"),
	pigeonhole_type_mortgage("04","抵押材料"),
	pigeonhole_type_investigation("05","产调查询材料"),

	//放款信息表状态
	paid_info_status_unvalid("00","无效"),
	paid_info_status_cleared("01","已结清"),
	paid_info_status_paying_and_not_overdue("02","正常还款中"),
	paid_info_status_overdue("03","逾期中"),
	paid_info_status_delete("04","删除待处理"),
	paid_info_status_deleting("05","删除处理中"),



	//借款借据表
	iou_status_valid("1","有效"),
	iou_valid("0","无效"),

	//合同表
	contract_status_valid("1","有效"),
	contract_status_unvalid("0","无效"),

	//抵押物信息表
	house_status_valid("1","有效"),
	house_status_unvalid("0","无效"),
	house_land_property_national("01","国有"),
	house_land_property_collective("02","集体"),
	house_acquisition_form_sell("01","出让"),
	house_acquisition_form_allotted("02","划拨"),
	house_acquisition_form_transfer("03","转让"),
	house_type_apartment("01","公寓"),
	house_type_villa("02","别墅"),
	house_share_type_common("01", "共同共有"),
	house_share_type_several("02", "按份共有"),
	house_usage_detail_self("01", "自住"),
	house_usage_detail_record_rent("02", "已备案出租"),
	house_usage_detail_unrecord_rent("03", "未备案出租"),
	house_usage_detail_vacancy("04", "空置"),
	house_is_unique_yes("01", "是"),
	house_is_unique_no("02", "否"),
	house_mortgage_type_first("01", "一抵"),
	house_mortgage_type_second("02", "二抵"),
	house_mortgage_type_first_transfer("03", "一抵转单"),
	house_mortgage_type_second_transfer("04", "二抵转单"),
	house_first_balance_type_left("01", "余额"),
	house_first_balance_type_max("02", "最高额"),

	//产调信息表
	property_investigation_status_valid("1","有效"),
	property_investigation_status_unvalid("0","无效"),
	//产调明细表
	invest_detail_status_valid("1","有效"),
	invest_detail_status_unvalid("0","无效"),
	//产调明细表 -- 债权类型
	invest_detail_creditor_rights_type("01", "一般抵押"),
	//债权性质
	invest_detail_creditor_property_bank("01", "银行借贷"),
	invest_detail_creditor_property_private("02", "民间借贷"),
	//户口信息表
	household_registration_status_valid("1","有效"),
	household_registration_status_unvalid("0","无效"),
	//抵押物审批信息表
	house_approval_status_valid("1","有效"),
	house_approval_status_unvalid("0","无效"),
	//银行账户表
	account_info_status_valid("1","有效"),
	account_info_status_unvalid("0","无效"),

	//进件利息表
	apply_interesting_status_valid("1","有效"),
	apply_interesting_status_unvalid("0","无效"),
	//进件利息表-利息是否代收代付
	apply_interesting_is_interest_Instead_yes("1","代收代付"),
	apply_interesting_is_interest_Instead_no("0","否"),
	//进件利息表-是否全额回购
	apply_interesting_is_full_repurchase_yes("1","是"),
	apply_interesting_is_full_repurchase_no("0","否"),


	//资方信息表
	fund_side_status_valid("1","有效"),
	fund_side_status_unvalid("0","无效"),
	//担保公司信息表
	guarantee_corporation_status_valid("1","有效"),
	guarantee_corporation_status_unvalid("0","无效"),
	//保险公司信息表
	insurance_company_status_valid("1","有效"),
	insurance_company_status_unvalid("0","无效"),
	//客户费用信息表
	customer_fee_info_status_valid("1","有效"),
	customer_fee_info_status_unvalid("0","无效"),
	//签约及公证信息表
	sign_notarial_status_valid("1","有效"),
	sign_notarial_status_unvalid("0","无效"),
	//抵押登记信息表
	mortgage_status_valid("1","有效"),
	mortgage_status_unvalid("0","无效"),
	//产调查询表
	invest_query_status_valid("1","有效"),
	invest_query_status_unvalid("0","无效"),
	//归档材料表
	pigeonhole_info_status_valid("1","有效"),
	pigeonhole_info_status_unvalid("0","无效"),
	pigeonhole_info_type_notarization("01","签约公正材料"),
	pigeonhole_info_type_guarantee("02","担保材料"),
	pigeonhole_info_type_insurance("03","保险材料"),
	pigeonhole_info_type_mortgage("04","抵押材料"),
	pigeonhole_info_type_invest_query("05","产调查询材料"),
	pigeonhole_info_type_loan_voucher("06","放款凭证"),
	//资金入账上载状态
	receipts_record_upload_status_checking("01","校验中"),
	receipts_record_upload_status_success("02","校验成功"),
	receipts_record_upload_status_fail("03","校验失败"),


	//资金入账资金来源
	receipts_record_fund_source_customer("01","客户"),
	receipts_record_fund_source_honghuo("02","宏获"),
	receipts_record_fund_source_danbao("03","担保"),

	//资金入账账户类型
	receipts_record_account_type_honghuo("00","宏获还款账户"),
	receipts_record_account_type_fund_side("01","资方还款账户"),
	receipts_record_account_type_honghuo_bail("02","宏获保证金账户"),

	//推送类型
	message_type_overdue("00","逾期"),
	message_type_repayment("01","扣款"),

	//短信推送类型
	sms_type_overdue("00","逾期"),
	sms_type_repayment("01","扣款"),

	//待处理信息表发送方式
	send_method_delay("1","定时发送"),
	send_method_timely("0","立刻发送"),

	//推送信息表发送状态
	send_init("0","初始化成功"),
	send_succ("1","发送成功（请求成功）"),
	send_unknown("2","状态未知"),
	send_fail("3","发送失败"),
	send_ing("4","发送处理中"),
    //公司表
    company_status_valid("1","有效"),
    company_status_unvalid("0","无效"),
    company_type_insurance("01","保险公司"),
    company_type_guarantee("02","担保公司"),
    company_type_fundside("03","资方"),

	//放款天数计算规则
	paid_days_computational_rule_end("01","算头算尾"),
	paid_days_computational_rule_no_end("02","算头不算尾"),

	//固定还款日表
	repayment_date_status_valid("1","有效"),
	repayment_date_status_unvalid("0","无效"),

	//bms同步
	bms_paid_method_permits_loan("01","permits_loan"),
	bms_paid_method_collection_receipt_loan("02","collection_receipt_loan"),

	bms_main_loanner_true("1","true"),
	bms_main_loanner_false("0","false"),

	bms_marital_status_true("1","true"),
	bms_marital_status_false("0","false"),

	bms_interest_circle_year("03","01"),
	bms_interest_circle_month("02","02"),
	bms_interest_circle_day("01","03"),

	bms_repayment_method_rate("01","02"),
	bms_repayment_method_amount("02","01"),

	bms_interest_collection_method_pre("01","前置收息"),
	bms_interest_collection_method_back("02","后置收息"),
	bms_interest_collection_method_fix("03","固定日收息"),

	bms_is_spread_one_time_once("02","01"),
	bms_is_spread_one_time_period("01","02"),

	bms_synchronize_status_init("01","初始化"),
	bms_synchronize_status_executing("02","执行中"),
	bms_synchronize_status_executed("03","完成"),
	bms_synchronize_status_exception("04","异常"),
	bms_synchronize_status_invalid("00","无效"),

	bms_synchronize_company_type_insurance("01","insurance_company"),
	bms_synchronize_company_type_guarantee("02","guarantee_corporation"),
	bms_synchronize_company_type_fundside("03","management"),

	bms_is_closed_true("1","是"),
	bms_is_closed_false("0","否"),

	fund_side_receive_interest_method_pre("01","前置收息"),
	fund_side_receive_interest_method_back("02","后置收息"),
	fund_side_receive_interest_method_fix("03","固定日收息"),


	email_type_monitor("00","监控邮件"),


	//借款人信息表-是否主借款人
	customer_is_main_yes("1","主借款人"),
	customer_is_main_no("0","非主借款人"),

	//
	email_switch_monitor_off("0","关闭"),
	email_switch_monitor_no("1","开启"),

	;

	private String key;
	private String desc;

	GlobDict(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
