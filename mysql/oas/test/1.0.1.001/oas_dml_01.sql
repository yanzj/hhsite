use oas;
# 插入省份信息
INSERT INTO `oas`.`oas_province` (`code`, `abbr_name`, `full_name`, `status`, `order_by`, `remark`) VALUES ('310000', '上海', '上海市', '01', '1', NULL);

# 插入城市信息
INSERT INTO `oas`.`oas_city` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310100', '上海', '上海市', '310000', '01', '1', NULL);

# 插入行政区信息
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310101', '黄浦', '黄浦区', '310100', '01', '1', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310104', '徐汇', '徐汇区', '310100', '01', '2', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310105', '长宁', '长宁区', '310100', '01', '3', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310106', '静安', '静安区', '310100', '01', '4', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310107', '普陀', '普陀区', '310100', '01', '5', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310109', '虹口', '虹口区', '310100', '01', '6', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310110', '杨浦', '杨浦区', '310100', '01', '7', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310112', '闵行', '闵行区', '310100', '01', '8', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310113', '宝山', '宝山区', '310100', '01', '9', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310114', '嘉定', '嘉定区', '310100', '01', '10', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310115', '浦东', '浦东新区', '310100', '01', '11', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310116', '金山', '金山区', '310100', '01', '12', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310117', '松江', '松江区', '310100', '01', '13', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310118', '青浦', '青浦区', '310100', '01', '14', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310120', '奉贤', '奉贤区', '310100', '01', '15', NULL);
INSERT INTO `oas`.`oas_district` (`code`, `abbr_name`, `full_name`, `parent_code`, `status`, `order_by`, `remark`) VALUES ('310151', '崇明', '崇明区', '310100', '01', '16', NULL);

# 插入材料类型信息
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('01', '产证照片', '一般每个任务只有一条数据', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('10', '室外视频', '小区门口、抵押房屋楼下、抵押房屋门口等', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('11', '室外照片', '小区门口、抵押房屋楼下、抵押房屋门口等', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('20', '室内视频', '客厅（阳台）、主次卧（阳台、洗手间）、客厅、洗手间、厨房等', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('21', '室内照片', '客厅（阳台）、主次卧（阳台、洗手间）、客厅、洗手间、厨房等', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('31', '证明（小区门口）', '小区门口自拍', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('32', '证明（和借款人）', '和借款人拍照', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('41', '材料（外访调查报告）', '外访调查报告', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('42', '材料（借款申请书一）', '借款申请书', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ( '43', '材料（借款申请书二）', '借款申请书', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ( '44', '材料（客户四证）', '客户四证', '01', NULL);
INSERT INTO `oas`.`oas_material_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ( '45', '材料（其他）', '其他', '01', NULL);

# 插入默认区域表
INSERT INTO `oas`.`oas_area` (`code`, `name`, `city_code`, `status`, `order_by`, `remark`) VALUES ('A-001', '默认区1', '310100', '01', NULL, NULL);

# 插入默认小组表
INSERT INTO `oas`.`oas_group` (`code`, `name`, `area_code`, `status`, `order_by`, `remark`) VALUES ('G-001', '默认组1', 'A-001', '01', NULL, NULL);

# 插入操作类型表
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('01', '创建任务', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('02', '指派任务', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('03', '申请关闭', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('04', '审批关闭', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('05', '申请改约', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('06', '审批改约', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('07', '申请改派', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('08', '审批改派', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('09', '完成任务', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('10', '任务过期', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('11', '链接任务', NULL, '01', NULL);
INSERT INTO `oas`.`oas_operation_type` (`code`, `name`, `description`, `status`, `remark`) VALUES ('12', '激活任务', NULL, '01', NULL);

# 插入任务状态表
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('01', '待指派', '创建任务后初始状态', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('02', '待完成', '指派后的状态', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('03', '待关闭', '发起关闭审批', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('04', '待改约', '发起改约审批', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('05', '待改派', '发起改派审批', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('06', '已关闭', '关闭审批完成', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('07', '已过期', '定时任务判定任务过期', '01', NULL);
INSERT INTO `oas`.`oas_task_status` (`code`, `name`, `description`, `status`, `remark`) VALUES ('08', '已完成', '下户人员提交下户材料', '01', NULL);


# 插入序列号信息
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('MATERIAL_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('TASK_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('TASK_HISTORY_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('OPERATION_HISTORY_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('TASK_MATERIAL_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('APPROVAL_SERNO', '0', '1');
INSERT INTO `oas`.`sequence` (`name`, `current_value`, `increment`) VALUES ('VERIFY_SERNO', '0', '1');

