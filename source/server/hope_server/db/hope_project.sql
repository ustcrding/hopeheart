/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : hope_project

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2019-07-04 20:58:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for doctorinfo
-- ----------------------------
DROP TABLE IF EXISTS `doctorinfo`;
CREATE TABLE `doctorinfo` (
  `doctor_id` char(20) collate utf8_bin NOT NULL default '' COMMENT '医师ID',
  `doctor_name` varchar(8) collate utf8_bin NOT NULL default '' COMMENT '医师姓名',
  `doctor_gender` char(1) collate utf8_bin NOT NULL default '' COMMENT '"性别F-女M-男"',
  `doctor_certype` char(2) collate utf8_bin NOT NULL default '' COMMENT '医师证件类型',
  `doctor_certno` varchar(30) collate utf8_bin NOT NULL default '' COMMENT '医师证件号码',
  `certificate` varchar(30) collate utf8_bin NOT NULL default '' COMMENT '医师执业资格证号',
  `address_code` char(20) collate utf8_bin NOT NULL default '' COMMENT '医师所在地区',
  `affiliate` varchar(100) collate utf8_bin default NULL COMMENT '医师所在单位',
  `psyledge_type` char(2) collate utf8_bin NOT NULL default '' COMMENT '医师掌握心理知识大类',
  `psyledge_subtype` char(2) collate utf8_bin default NULL COMMENT '医师掌握心理知识小类',
  `psytests_type` char(2) collate utf8_bin NOT NULL default '' COMMENT '医师掌握心理测评知识大类',
  `psytests_subtype` char(2) collate utf8_bin default NULL COMMENT '医师掌握心理测评知识小类',
  `counseling` char(1) collate utf8_bin NOT NULL default '' COMMENT '"当前是否参与辅导Y-是N-否"',
  `counseling_num` varchar(5) collate utf8_bin default NULL COMMENT '当前辅导数',
  `satisficing` char(1) collate utf8_bin NOT NULL default '' COMMENT '"医师总体满意度W-WONDERFUL 非常满意G-GOOD      基本满意Y-YAWP      不满意"',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备注1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备注2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备注3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备注4',
  PRIMARY KEY  USING BTREE (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='医师录基本信息表';

-- ----------------------------
-- Records of doctorinfo
-- ----------------------------
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00001', '赵军', 'M', '01', '510123199808281234', '20111011SC0001', '510100', '四川大学华西医院', 'B', 'B3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00002', '李敏', 'F', '01', '510123199808281235', '20111011SC0002', '510100', '四川省人民医院', 'E', 'E2', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00003', '张宫', 'M', '01', '510123199808281235', '20111011SC0003', '510100', '成都军区总医院', 'E', 'E3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00004', '曹雪', 'F', '01', '510123199808281236', '20111011SC0004', '510100', '西南医科大学附属医院', 'I', 'I1', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00005', '华为民', 'M', '01', '510123199808281237', '20111011SC0005', '510100', '成都市第三人民医院', 'C', 'C3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00006', '王朝', 'M', '01', '510123199808281238', '20111011SC0006', '510700', '绵阳市中心医院', 'P', 'P4', 'CQ', 'C3', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00007', '张仲民', 'M', '01', '510123199808281234', '20111011SC0007', '510500', '川北医学院附属医院', 'B', 'B3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00008', '李在心', 'F', '01', '510123199808281235', '20111011SC0008', '510100', '成都中医药大学附属医院', 'E', 'E2', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00009', '陈栋', 'M', '01', '510123199808281235', '20111011SC0009', '510100', '成都市第一人民医院', 'E', 'E3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00010', '黄兴华', 'F', '01', '510123199808281236', '20111011SC0010', '510100', '成都市第二人民医院', 'I', 'I1', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00011', '华为民', 'M', '01', '510123199808281237', '20111011SC0011', '510300', '自贡第二人民医院', 'C', 'C3', 'SD', 'sd', 'Y', '1', 'G', '', '', '', '');
INSERT INTO `doctorinfo` VALUES ('HOPEDOCID00012', '张东风', 'M', '01', '510123199808281238', '20111011SC0012', '510300', '自贡第四人民医院', 'P', 'P4', 'CQ', 'C3', 'Y', '1', 'G', '', '', '', '');

-- ----------------------------
-- Table structure for psycity
-- ----------------------------
DROP TABLE IF EXISTS `psycity`;
CREATE TABLE `psycity` (
  `country_code` varchar(6) collate utf8_bin NOT NULL default '' COMMENT '国家编码',
  `province_code` varchar(6) collate utf8_bin NOT NULL default '' COMMENT '省编码',
  `city_code` varchar(6) collate utf8_bin NOT NULL default '' COMMENT '地级市编码',
  `province_name` varchar(50) collate utf8_bin NOT NULL default '' COMMENT '省名称',
  `city_name` varchar(30) collate utf8_bin NOT NULL default '' COMMENT '地级市名称',
  `city_state` char(1) collate utf8_bin NOT NULL default '' COMMENT '"地级市状态H-预警S-正常"',
  `address_code` char(20) collate utf8_bin default NULL COMMENT '地区编号（唯一）',
  `disaster_type` char(2) collate utf8_bin default NULL COMMENT '地区灾难类型',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备用1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备用2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备用3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备用4',
  PRIMARY KEY  USING BTREE (`country_code`,`province_code`,`city_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='城市代码表';

-- ----------------------------
-- Records of psycity
-- ----------------------------
INSERT INTO `psycity` VALUES ('CN', '110100', '110101', '北京', '东城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110102', '北京', '西城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110105', '北京', '朝阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110106', '北京', '丰台', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110107', '北京', '石景山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110108', '北京', '海淀', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110109', '北京', '门头沟', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110111', '北京', '房山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110112', '北京', '通州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110113', '北京', '顺义', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110114', '北京', '昌平', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110115', '北京', '大兴', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110116', '北京', '怀柔', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110117', '北京', '平谷', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110118', '北京', '密云', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '110100', '110119', '北京', '延庆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120101', '天津', '和平', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120102', '天津', '河东', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120103', '天津', '河西', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120104', '天津', '南开', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120105', '天津', '河北', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120106', '天津', '红桥', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120110', '天津', '东丽', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120111', '天津', '西青', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120112', '天津', '津南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120113', '天津', '北辰', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120114', '天津', '武清', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120115', '天津', '宝坻', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120116', '天津', '滨海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120117', '天津', '宁河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120118', '天津', '静海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '120100', '120119', '天津', '蓟州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130100', '河北', '石家庄', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130200', '河北', '唐山 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130300', '河北', '秦皇岛', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130400', '河北', '邯郸', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130500', '河北', '邢台', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130600', '河北', '保定', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130700', '河北', '张家口', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130800', '河北', '承德 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '130900', '河北', '沧州 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '131000', '河北', '廊坊 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '130000', '131100', '河北', '衡水', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140100', '山西', '太原', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140200', '山西', '大同', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140300', '山西', '阳泉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140400', '山西', '长治', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140500', '山西', '晋城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140600', '山西', '朔州 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140700', '山西', '晋中', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140800', '山西', '运城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '140900', '山西', '忻州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '141000', '山西', '临汾 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '140000', '141100', '山西', '吕梁 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150100', '内蒙古', '呼和浩特', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150200', '内蒙古', '包头', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150300', '内蒙古', '乌海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150400', '内蒙古', '赤峰', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150500', '内蒙古', '通辽', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150600', '内蒙古', '鄂尔多斯', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150700', '内蒙古', '呼伦贝尔', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150800', '内蒙古', '巴彦淖尔', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '150900', '内蒙古', '乌兰察布', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '152200', '内蒙古', '兴安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '152500', '内蒙古', '锡林郭勒', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '150000', '152900', '内蒙古', '阿拉善', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210100', '辽宁', '沈阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210200', '辽宁', '大连', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210300', '辽宁', '鞍山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210400', '辽宁', '抚顺 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210500', '辽宁', '本溪', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210600', '辽宁', '丹东', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210700', '辽宁', '锦州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210800', '辽宁', '营口 ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '210900', '辽宁', '阜新', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '211000', '辽宁', '辽阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '211100', '辽宁', '盘锦', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '211200', '辽宁', '铁岭  ', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '211300', '辽宁', '朝阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '210000', '211400', '辽宁', '葫芦岛', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220100', '吉林', '长春', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220200', '吉林', '吉林', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220300', '吉林', '四平', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220400', '吉林', '辽源', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220500', '吉林', '通化', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220600', '吉林', '白山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220700', '吉林', '松原', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '220800', '吉林', '白城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '220000', '222400', '吉林', '延边', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230100', '黑龙江', '哈尔滨', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230200', '黑龙江', '齐齐哈尔', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230300', '黑龙江', '鸡西', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230400', '黑龙江', '鹤岗', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230500', '黑龙江', '双鸭山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230600', '黑龙江', '大庆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230700', '黑龙江', '伊春', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230800', '黑龙江', '佳木斯', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '230900', '黑龙江', '七台河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '231000', '黑龙江', '牡丹江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '231100', '黑龙江', '黑河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '231200', '黑龙江', '绥化', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '230000', '232700', '黑龙江', '大兴安岭', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310101', '上海', '黄浦', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310104', '上海', '徐汇', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310105', '上海', '长宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310106', '上海', '静安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310107', '上海', '普陀', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310109', '上海', '虹口', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310110', '上海', '杨浦', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310112', '上海', '闵行', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310113', '上海', '宝山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310114', '上海', '嘉定', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310115', '上海', '浦东', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310116', '上海', '金山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310117', '上海', '松江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310118', '上海', '青浦', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310120', '上海', '奉贤', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '310000', '310151', '上海', '崇明', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320100', '江苏', '南京', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320200', '江苏', '无锡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320300', '江苏', '徐州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320400', '江苏', '常州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320500', '江苏', '苏州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320600', '江苏', '南通', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320700', '江苏', '连云港', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320800', '江苏', '淮安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '320900', '江苏', '盐城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '321000', '江苏', '扬州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '321100', '江苏', '镇江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '321200', '江苏', '泰州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '320000', '321300', '江苏', '宿迁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330100', '浙江', '杭州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330200', '浙江', '宁波', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330300', '浙江', '温州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330400', '浙江', '嘉兴', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330500', '浙江', '湖州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330600', '浙江', '绍兴', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330700', '浙江', '金华', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330800', '浙江', '衢州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '330900', '浙江', '舟山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '331000', '浙江', '台州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '330000', '331100', '浙江', '丽水', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340100', '安徽', '合肥', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340200', '安徽', '芜湖', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340300', '安徽', '蚌埠', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340400', '安徽', '淮南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340500', '安徽', '马鞍山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340600', '安徽', '淮北', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340700', '安徽', '铜陵', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '340800', '安徽', '安庆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341000', '安徽', '黄山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341100', '安徽', '滁州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341200', '安徽', '阜阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341300', '安徽', '宿州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341500', '安徽', '六安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341600', '安徽', '亳州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341700', '安徽', '池州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '340000', '341800', '安徽', '宣城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350100', '福建', '福州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350200', '福建', '厦门', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350300', '福建', '莆田', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350400', '福建', '三明', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350500', '福建', '泉州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350600', '福建', '漳州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350700', '福建', '南平', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350800', '福建', '龙岩', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '350000', '350900', '福建', '宁德', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360100', '江西', '南昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360200', '江西', '景德镇', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360300', '江西', '萍乡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360400', '江西', '九江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360500', '江西', '新余', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360600', '江西', '鹰潭', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360700', '江西', '赣州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360800', '江西', '吉安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '360900', '江西', '宜春', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '361000', '江西', '抚州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '360000', '361100', '江西', '上饶', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370100', '山东', '济南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370200', '山东', '青岛', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370300', '山东', '淄博', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370400', '山东', '枣庄', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370500', '山东', '东营', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370600', '山东', '烟台', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370700', '山东', '潍坊', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370800', '山东', '济宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '370900', '山东', '泰安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371000', '山东', '威海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371100', '山东', '日照', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371200', '山东', '莱芜', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371300', '山东', '临沂', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371400', '山东', '德州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371500', '山东', '聊城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371600', '山东', '滨州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '370000', '371700', '山东', '菏泽', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410100', '河南', '郑州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410200', '河南', '开封', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410300', '河南', '洛阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410400', '河南', '平顶山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410500', '河南', '安阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410600', '河南', '鹤壁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410700', '河南', '新乡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410800', '河南', '焦作', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '410900', '河南', '濮阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411000', '河南', '许昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411100', '河南', '漯河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411200', '河南', '三门峡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411300', '河南', '南阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411400', '河南', '商丘', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411500', '河南', '信阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411600', '河南', '周口', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '411700', '河南', '驻马店', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '410000', '419001', '河南', '济源', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420100', '湖北', '武汉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420200', '湖北', '黄石', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420300', '湖北', '十堰', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420500', '湖北', '宜昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420600', '湖北', '襄阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420700', '湖北', '鄂州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420800', '湖北', '荆门', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '420900', '湖北', '孝感', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '421000', '湖北', '荆州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '421100', '湖北', '黄冈', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '421200', '湖北', '咸宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '421300', '湖北', '随州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '422800', '湖北', '恩施', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '429004', '湖北', '仙桃', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '429005', '湖北', '潜江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '429006', '湖北', '天门', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '420000', '429021', '湖北', '神农架林区', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430100', '湖南', '长沙', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430200', '湖南', '株洲', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430300', '湖南', '湘潭', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430400', '湖南', '衡阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430500', '湖南', '邵阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430600', '湖南', '岳阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430700', '湖南', '常德', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430800', '湖南', '张家界', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '430900', '湖南', '益阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '431000', '湖南', '郴州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '431100', '湖南', '永州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '431200', '湖南', '怀化', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '431300', '湖南', '娄底', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '430000', '433100', '湖南', '湘西', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440100', '广东', '广州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440200', '广东', '韶关', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440300', '广东', '深圳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440400', '广东', '珠海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440500', '广东', '汕头', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440600', '广东', '佛山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440700', '广东', '江门', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440800', '广东', '湛江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '440900', '广东', '茂名', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441200', '广东', '肇庆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441300', '广东', '惠州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441400', '广东', '梅州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441500', '广东', '汕尾', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441600', '广东', '河源', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441700', '广东', '阳江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441800', '广东', '清远', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '441900', '广东', '东莞', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '442000', '广东', '中山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '445100', '广东', '潮州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '445200', '广东', '揭阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '440000', '445300', '广东', '云浮', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450100', '广西', '南宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450200', '广西', '柳州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450300', '广西', '桂林', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450400', '广西', '梧州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450500', '广西', '北海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450600', '广西', '防城港', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450700', '广西', '钦州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450800', '广西', '贵港', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '450900', '广西', '玉林', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '451000', '广西', '百色', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '451100', '广西', '贺州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '451200', '广西', '河池', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '451300', '广西', '来宾', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '450000', '451400', '广西', '崇左', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '460100', '海南', '海口', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '460200', '海南', '三亚', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '460300', '海南', '三沙', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '460400', '海南', '儋州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469001', '海南', '五指山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469002', '海南', '琼海', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469005', '海南', '文昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469006', '海南', '万宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469007', '海南', '东方', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469021', '海南', '定安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469022', '海南', '屯昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469023', '海南', '澄迈', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469024', '海南', '临高', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469025', '海南', '白沙', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469026', '海南', '昌江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469027', '海南', '乐东', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469028', '海南', '陵水', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469029', '海南', '保亭', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '460000', '469030', '海南', '琼中', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500101', '重庆', '万州区', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500102', '重庆', '涪陵', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500103', '重庆', '渝中', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500104', '重庆', '大渡口', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500105', '重庆', '江北', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500106', '重庆', '沙坪坝', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500107', '重庆', '九龙坡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500108', '重庆', '南岸', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500109', '重庆', '北碚', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500110', '重庆', '綦江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500111', '重庆', '大足', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500112', '重庆', '渝北', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500113', '重庆', '巴南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500114', '重庆', '黔江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500115', '重庆', '长寿', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500116', '重庆', '江津', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500117', '重庆', '合川', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500118', '重庆', '永川', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500119', '重庆', '南川', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500120', '重庆', '璧山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500151', '重庆', '铜梁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500152', '重庆', '潼南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500153', '重庆', '荣昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500154', '重庆', '开州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500155', '重庆', '梁平', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '500000', '500156', '重庆', '武隆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510100', '四川', '成都', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510300', '四川', '自贡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510400', '四川', '攀枝花', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510500', '四川', '泸州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510600', '四川', '德阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510700', '四川', '绵阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510800', '四川', '广元', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '510900', '四川', '遂宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511000', '四川', '内江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511100', '四川', '乐山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511300', '四川', '南充', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511400', '四川', '眉山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511500', '四川', '宜宾', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511600', '四川', '广安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511700', '四川', '达州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511800', '四川', '雅安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '511900', '四川', '巴中', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '512000', '四川', '资阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '513200', '四川', '阿坝', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '513300', '四川', '甘孜', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '510000', '513400', '四川', '凉山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520100', '贵州', '贵阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520200', '贵州', '六盘水', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520300', '贵州', '遵义', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520400', '贵州', '安顺', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520500', '贵州', '毕节', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '520600', '贵州', '铜仁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '522300', '贵州', '黔西南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '522600', '贵州', '黔东南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '520000', '522700', '贵州', '黔南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530100', '云南', '昆明', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530300', '云南', '曲靖', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530400', '云南', '玉溪', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530500', '云南', '保山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530600', '云南', '昭通', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530700', '云南', '丽江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530800', '云南', '普洱', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '530900', '云南', '临沧', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '532300', '云南', '楚雄', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '532500', '云南', '红河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '532600', '云南', '文山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '532800', '云南', '西双版纳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '532900', '云南', '大理', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '533100', '云南', '德宏', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '533300', '云南', '怒江', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '530000', '533400', '云南', '迪庆', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '540100', '西藏', '拉萨', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '540200', '西藏', '日喀则', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '540300', '西藏', '昌都', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '540400', '西藏', '林芝', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '542200', '西藏', '山南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '542400', '西藏', '那曲', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '540000', '542500', '西藏', '阿里', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610100', '陕西', '西安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610200', '陕西', '铜川', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610300', '陕西', '宝鸡', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610400', '陕西', '咸阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610500', '陕西', '渭南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610600', '陕西', '延安', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610700', '陕西', '汉中', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610800', '陕西', '榆林', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '610900', '陕西', '安康', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '610000', '611000', '陕西', '商洛', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620100', '甘肃', '兰州', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620200', '甘肃', '嘉峪关', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620300', '甘肃', '金昌', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620400', '甘肃', '白银', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620500', '甘肃', '天水', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620600', '甘肃', '武威', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620700', '甘肃', '张掖', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620800', '甘肃', '平凉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '620900', '甘肃', '酒泉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '621000', '甘肃', '庆阳', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '621100', '甘肃', '定西', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '621200', '甘肃', '陇南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '622900', '甘肃', '临夏', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '620000', '623000', '甘肃', '甘南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '630100', '青海', '西宁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '630200', '青海', '海东', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632200', '青海', '海北', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632300', '青海', '黄南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632500', '青海', '海南', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632600', '青海', '果洛', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632700', '青海', '玉树', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '630000', '632800', '青海', '海西', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '640000', '640100', '宁夏', '银川', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '640000', '640200', '宁夏', '石嘴山', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '640000', '640300', '宁夏', '吴忠', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '640000', '640400', '宁夏', '固原', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '640000', '640500', '宁夏', '中卫', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '650100', '新疆', '乌鲁木齐', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '650200', '新疆', '克拉玛依', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '650400', '新疆', '吐鲁番', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '650500', '新疆', '哈密', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '652300', '新疆', '昌吉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '652700', '新疆', '博尔塔拉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '652800', '新疆', '巴音郭楞', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '652900', '新疆', '阿克苏', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '653000', '新疆', '克孜勒', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '653100', '新疆', '喀什', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '653200', '新疆', '和田', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '654000', '新疆', '伊犁', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '654200', '新疆', '塔城', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '654300', '新疆', '阿勒泰', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659001', '新疆', '石河子', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659002', '新疆', '阿拉尔', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659003', '新疆', '图木舒克', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659004', '新疆', '五家渠', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659005', '新疆', '北屯', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659006', '新疆', '铁门关', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659007', '新疆', '双河', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659008', '新疆', '可克达拉', 'S', '', '', '', '', '', '');
INSERT INTO `psycity` VALUES ('CN', '650000', '659009', '新疆', '昆玉', 'S', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for psyknowledge
-- ----------------------------
DROP TABLE IF EXISTS `psyknowledge`;
CREATE TABLE `psyknowledge` (
  `psyknowledge_id` char(16) collate utf8_bin NOT NULL default '' COMMENT '心理知识库号',
  `psyledge_type` char(2) collate utf8_bin NOT NULL default '' COMMENT '心理知识大类',
  `psyledge_subtype` char(2) collate utf8_bin NOT NULL default '' COMMENT '心理知识小类',
  `psyledge_detail` varchar(300) collate utf8_bin NOT NULL default '' COMMENT '心理知识详情',
  `psytests_id` char(16) collate utf8_bin NOT NULL default '' COMMENT '可关联心理测评知识库号',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备注1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备注2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备注3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备注4',
  PRIMARY KEY  USING BTREE (`psyknowledge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='心理知识库信息表';

-- ----------------------------
-- Records of psyknowledge
-- ----------------------------
INSERT INTO `psyknowledge` VALUES ('PSYKID0001', 'B', 'B3', '1、设置合理目标，一般来说，具体的、短期内能实现的、难度中等、可接受的的目标可有效激发个体动机；\r\n2、有效利用反馈与评价，对制定目标达到与否的反馈或评价有助于激发动机；\r\n3、增加目标实现过程中的趣味性，增强任务过程的趣味性是激发内部动机的有效策略之一，使之保持兴趣和注意力。', 'PSYTID0001', '', '', '', '');
INSERT INTO `psyknowledge` VALUES ('PSYKID0002', 'E', 'E2', '任何一种情绪都是促使我们采取行动的驱力，是我们在面临各种情境时所固有的能及时拟定的反应计划。情绪与行为的关系从动物或孩童的举止观察最容易直接显现出来。同时，在成熟个体又有分离的情况。情绪与行为的先天关联行可从情绪表现及表情中显现出来，情绪表现是情绪在有机体身上的外显行为。它包括面部表情、言语表情与身段表情。', 'PSYTID0002', '', '', '', '');
INSERT INTO `psyknowledge` VALUES ('PSYKID0003', 'E', 'E3', '情绪能够影响一个人的精神状态，提高或降低一个人的学习和工作效率。它也是观察一个人对于某人或某事的真实情感的窗口。健康情绪的养成或保持对一个人的工作、学习或生活都起着至关重要的作用。要有正确的人生追求、宽广的胸襟、理性的适应生活、寻找身边的欢乐。学会控制不良情绪。', 'PSYTID0003', '', '', '', '');
INSERT INTO `psyknowledge` VALUES ('PSYKID0004', 'I', 'I1', '人际沟通是人们相互之间交流思想、观点、意见、知识、消息、情感、态度、动作等的过程。是人与人之间相互联系的一个最主要的方面。通常衡量一个人生活质量的高低，很重要的一个指标就是看其人机沟通的广度和深度。是人社会生存、建立和发展人际关系、形成自我意识的必要条件。', 'PSYTID0004', '', '', '', '');
INSERT INTO `psyknowledge` VALUES ('PSYKID0005', 'C', 'C3', '记忆是过去经验在人脑中的反映。人脑感知过的事物，思考过的问题和理论，体验过的情感和情绪，练习过的动作等都可以成为记忆的内容。记忆是一个复杂的心理过程，识记、保持、再认或是回忆是记忆的三个基本环节。记忆把人的过去、现在和未来连在一起。没有记忆参与，知觉过程就不可能实现，没有记忆也就不会有思维活动。人们通过丰富自己的记忆内容，最终形成独具魅力的个性。', 'PSYTID0005', '', '', '', '');
INSERT INTO `psyknowledge` VALUES ('PSYKID0006', 'P', 'P4', '心理学家通常根据测验方式将人格测验分为自陈式测验和投射式测验。自陈式测验是运用自陈量表来进行测验，量表中包含陈述性条目，受测者根据实际情况选择答案，常见自陈式测验：卡特尔16中人格因素测验、埃森克人格问卷、明尼苏达多项人格问卷。投射测验可以避免受测者因主观因素未如实进行测验，常见投射式测验：罗夏墨迹测验、主题统觉测验等。', 'PSYTID0006', '', '', '', '');

-- ----------------------------
-- Table structure for psyopehis
-- ----------------------------
DROP TABLE IF EXISTS `psyopehis`;
CREATE TABLE `psyopehis` (
  `oper_traceno` varchar(16) collate utf8_bin NOT NULL default '' COMMENT '操作流水号',
  `opera_time` char(14) collate utf8_bin NOT NULL default '' COMMENT '操作时间',
  `opera_flag` varchar(2) collate utf8_bin NOT NULL default '' COMMENT '"操作类型1－受灾群众登记2－救援知识查询3-心理知识查询4-心理测评5-心理咨询（受灾群众）6-心理辅导（医师/志愿者）7-登录8-注销"',
  `opera_type` char(1) collate utf8_bin NOT NULL default '' COMMENT '"操作发起方V-受灾群众D-医师G-志愿者"',
  `opera_esult` char(1) collate utf8_bin NOT NULL default '' COMMENT '"操作结果0-交易成功1-交易失败"',
  `opera_remark` varchar(100) collate utf8_bin default NULL COMMENT '操作备注',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备用1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备用2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备用3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备用4',
  PRIMARY KEY  USING BTREE (`oper_traceno`,`opera_time`,`opera_flag`,`opera_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='操作记录表';

-- ----------------------------
-- Records of psyopehis
-- ----------------------------

-- ----------------------------
-- Table structure for psysmspush
-- ----------------------------
DROP TABLE IF EXISTS `psysmspush`;
CREATE TABLE `psysmspush` (
  `push_date` char(8) NOT NULL COMMENT '推送日期',
  `push_time` char(6) NOT NULL COMMENT '推送时间',
  `push_owner` char(1) NOT NULL COMMENT '推送方',
  `push_reason` varchar(20) NOT NULL COMMENT '推送缘由',
  `push_content` varchar(300) NOT NULL COMMENT '推送内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信推送表';

-- ----------------------------
-- Records of psysmspush
-- ----------------------------

-- ----------------------------
-- Table structure for psytest
-- ----------------------------
DROP TABLE IF EXISTS `psytest`;
CREATE TABLE `psytest` (
  `psytests_id` char(16) collate utf8_bin NOT NULL default '' COMMENT '心理测评知识库号',
  `psytests_type` char(2) collate utf8_bin NOT NULL default '' COMMENT '心理测评大类',
  `psytests_subtype` char(2) collate utf8_bin NOT NULL default '' COMMENT '心理测评小类',
  `psytests_detail` varchar(300) collate utf8_bin NOT NULL default '' COMMENT '心理测评详情',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备注1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备注2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备注3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备注4',
  PRIMARY KEY  USING BTREE (`psytests_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='心理测评知识库表';

-- ----------------------------
-- Records of psytest
-- ----------------------------
INSERT INTO `psytest` VALUES ('1', 'SD', 'sd', 'SDS量表测评', null, null, null, null);

-- ----------------------------
-- Table structure for psytestinfo
-- ----------------------------
DROP TABLE IF EXISTS `psytestinfo`;
CREATE TABLE `psytestinfo` (
  `victimtest_id` int(16) NOT NULL auto_increment COMMENT '心理测评号',
  `victim_id` char(20) collate utf8_bin default '' COMMENT '受灾群众ID',
  `psytests_id` char(16) collate utf8_bin default '' COMMENT '心理测评知识库号',
  `tests_level` varchar(2) collate utf8_bin default '' COMMENT '"心理测评等级H-HEALTH健康状态U-UNHEALTH不良状态 B-BOLCK心理障碍I-ILLNESS心理疾病"',
  `psychologist_id` char(20) collate utf8_bin default NULL COMMENT '当前辅导医师ID',
  `volunteer_id` char(20) collate utf8_bin default NULL COMMENT '当前辅导志愿者ID',
  `testioin_date` char(8) collate utf8_bin default '' COMMENT '参与测评日期',
  `testjoin_time` char(6) collate utf8_bin default '' COMMENT '参与测评时间',
  `address_code` char(20) collate utf8_bin default '' COMMENT '参与测评所在地区',
  `counseling` char(1) collate utf8_bin default '' COMMENT '"当前是否参与辅导Y-是N-否"',
  `satisficing` char(1) collate utf8_bin default '' COMMENT '"当前辅导满意度W-WONDERFUL 非常满意G-GOOD      基本满意Y-YAWP      不满意"',
  `added` varchar(100) collate utf8_bin default NULL COMMENT '备注',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备用1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备用2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备用3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备用4',
  PRIMARY KEY  (`victimtest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='心理测评信息表';

-- ----------------------------
-- Records of psytestinfo
-- ----------------------------
INSERT INTO `psytestinfo` VALUES ('1', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('2', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('3', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('4', null, 'test001', 'U', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('5', '9787115435040002', '', '', '3', null, '', '', '', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('6', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('7', null, 'test001', 'U', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('8', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('9', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('10', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('11', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('12', null, 'test001', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('13', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('14', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('15', null, '1', 'I', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('16', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('17', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('18', null, '1', 'U', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('19', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('20', null, '1', 'I', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('21', null, '1', 'H', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('22', null, '1', 'I', null, null, null, null, '湖南长沙', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('23', null, '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('24', null, '1', 'B', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('25', null, '1', 'U', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('26', null, '1', 'U', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('27', null, '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('28', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('29', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('30', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('31', '1', '1', 'U', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('32', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('33', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('34', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('35', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('36', '1', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('37', '1', '1', 'U', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('38', '20190704F0001', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('39', '20190704F0001', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('40', '20190704F0001', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('41', '20190704F0001', '1', 'H', null, null, null, null, '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('42', '20190704F0001', '1', 'H', null, null, '20190704', '172114', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('43', '20190704F0001', '1', 'H', null, null, '20190704', '172334', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('44', '20190704F0001', '1', 'H', null, null, '20190704', '172659', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('45', '20190704F0001', '1', 'U', null, null, '20190704', '172855', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('46', '20190704F0001', '1', 'H', null, null, '20190704', '173103', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('47', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '173228', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('48', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '173326', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('49', '20190704F0001', '1', 'U', 'HOPEDOCID00001', null, '20190704', '173522', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('50', '20190704F0001', '1', 'U', 'HOPEDOCID00001', null, '20190704', '173614', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('51', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '173651', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('52', '20190704F0001', '1', 'U', 'HOPEDOCID00001', null, '20190704', '173712', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('53', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '175031', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('54', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '175222', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('55', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '183531', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('56', '20190704F0001', '1', 'I', 'HOPEDOCID00001', null, '20190704', '184343', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('57', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '184947', '四川德阳', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('58', '20190704F0001', '1', 'I', 'HOPEDOCID00001', null, '20190704', '185049', '四川德阳', '', 'W', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('59', '20190704F0001', '1', 'U', 'HOPEDOCID00001', null, '20190704', '185336', '四川德阳', '', 'G', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('60', '20190704F0001', '1', 'H', 'HOPEDOCID00001', null, '20190704', '191913', '四川德阳', '', 'W', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('61', '20190704F0002', '1', 'H', 'HOPEDOCID00001', null, '20190704', '192456', '四川成都', '', 'W', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('62', '20190704F0002', '1', 'H', 'HOPEDOCID00001', null, '20190704', '192628', '四川成都', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('63', '2', '1', 'H', 'HOPEDOCID00001', null, '20190704', '194216', '', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('64', '2', '1', 'H', 'HOPEDOCID00001', null, '20190704', '194733', '', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('65', '2', '1', 'H', 'HOPEDOCID00001', null, '20190704', '194854', '', '', '', null, null, null, null, null);
INSERT INTO `psytestinfo` VALUES ('66', '2', '1', 'B', 'HOPEDOCID00001', null, '20190704', '203739', '', '', 'W', null, null, null, null, null);

-- ----------------------------
-- Table structure for rescueknowledge
-- ----------------------------
DROP TABLE IF EXISTS `rescueknowledge`;
CREATE TABLE `rescueknowledge` (
  `rescueknowledge_id` char(16) collate utf8_bin NOT NULL default '' COMMENT '救援知识库号',
  `rescue_type` varchar(20) collate utf8_bin default '' COMMENT '救援知识大类',
  `rescue_subtype` varchar(20) collate utf8_bin default '' COMMENT '救援知识小类',
  `rescue_knowledge` varchar(500) collate utf8_bin default '' COMMENT '救援知识详情',
  `psyknowledge_id` varchar(16) collate utf8_bin default '' COMMENT '可关联心理知识库号',
  `psytests_id` varchar(16) collate utf8_bin default '' COMMENT '可关联心理测评知识库号',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备注1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备注2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备注3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备注4',
  PRIMARY KEY  USING BTREE (`rescueknowledge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='救援知识库信息表';

-- ----------------------------
-- Records of rescueknowledge
-- ----------------------------
INSERT INTO `rescueknowledge` VALUES ('rescue.e001', 'O', 'E1', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.e002', 'O', 'E2', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.e003', 'O', 'E3', '如果发生森林大火，要尽快躲避在天然的防火带，比如树林中一条开阔的平地、河流就可以阻挡火势；如果大火随风扑面而来，大火的推进速度极快，应迅速绕道避开大火，寻找宽大开阔的深谷、水道或峡谷躲避。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.g001', 'G', 'G1', '火山爆发的救灾活动应实际情况采取行之有效的施救方法，目的就是将被埋压人员，安全地从废墟中救出来。通过了解、搜寻，确定废墟中有人员埋压后，判断其埋压位置，向废墟中喊话或敲击等方法传递营救信号。营救过程中，要特别注意埋压人员的安全。一是使用的工具（如铁棒、锄头、棍棒等）不要伤及埋压人员；二是不要破坏了埋压人员所处空间周围的支撑条件，引起新的垮塌，使埋压人员再次遇险；三是应尽快与埋压人员的封闭空间沟通，使新鲜空气流人，挖扒中如尘土太大应喷水降尘，以免埋压者窒息；四是埋压时间较长，一时又难以救出，可设法向埋压者输送饮用水、食品和药品，以维持其生命。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.g002', 'G', 'G2', '地震救援最难的就是清理废墟，因为无法预测到二次地震是否会发生，废墟中搜救也容易在救援中造成二次塌陷。如果发现废墟中有人，应该清理废墟减少被压人员身体压迫，及时送水给被救人员饮用，无水先输液，及时清理被压人员呼吸道。保证被救人员基本体征后，不要着急拉出被困人员，应仔细检查有无外伤，身体有无被挤压地方，但肢体被挤压超过24小时后开始出现肌肉坏死。在移开重物前就要为伤者滴注生理盐水，让伤者进行有效代谢，把血液中这些东西排出后再移开重物。被压伤人员无法确定骨折位置，条件准许一定要用硬质木板撤离伤员，出血的先清创消毒止血，配合医务人员。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.g003', 'G', 'G3', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.g004', 'G', 'G4', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.g005', 'G', 'G5', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m001', 'M', 'M1', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m002', 'M', 'M2', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m003', 'M', 'M3', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m004', 'M', 'M4', '发生沙尘暴时，不宜在室外进行体育运动和休闲活动，应立即停止一切露天集体活动，并将人员疏散到安全的地方躲避。行人骑车要谨慎，应减速慢行。若能见度差，视线不好，应靠路边推行。行人过马路要注意安全，不要贸然横穿马路。特别是小孩要远离水渠、水沟、水库等，避免落水发生溺水事故。沙尘暴如果伴有强风，行人要远离高层建筑、工地、广告牌、老树、枯树等，以免被高空坠落物砸伤。不要将机动车辆停靠在高楼、大树下方，以免玻璃、树枝等坠落物损坏车辆，或防止车辆被倒伏的大树砸坏。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m005', 'M', 'M5', '路面结冰、减速慢行。严禁超载、强行超车，安全第一。老人儿童穿防滑鞋、尽量减少外出活动，注意安全。骑行者带安全帽、注意遵守交通规则，防止意外发生。雨雪天气，视线降低，行走时注意观察车辆。打伞者高度重视脚下及避让行驶车辆安全。专家提醒，气温骤降要谨防心脑血管病，特别是高血压病人突发脑卒中，即脑中风。出门时应该注意保暖，保持血压平稳。如果发生一些脑中风的预警信号，如出现一过性头晕、一过性肢体麻木无力、一过性口眼歪斜、流口水、一过性言语不利等症状，应该及时到医院就诊。如果突然出现卒中大发作如偏瘫失语甚至昏迷等症状应该在第一时间呼叫急救系统，送到有溶栓和取栓条件的医院接受静脉溶栓或者动脉取栓。要预防脑卒中高发外，还要预防心脏病的发作。寒冷低温会使冠状动脉收缩，心脏病的高危患者要做好保暖工作，多喝水，但晚间不要吃得太多。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.m006', 'M', 'M6', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.o001', 'O', 'O1', '当感觉强烈地震或长时间振动时、收到海啸警报时、需要立即离开海岸，快速到高地等安全处避难，一旦落入水中，尽可能寻找可用于救生的漂浮物，尽可能的保留身体的能量，沉着冷静，等待救援。', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.o002', 'O', 'O2', '暂无', '', '', null, null, null, null);
INSERT INTO `rescueknowledge` VALUES ('rescue.o003', 'O', 'O3', '暂无', '', '', null, null, null, null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL auto_increment COMMENT '主键',
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `avatar` varchar(255) default NULL,
  `province` varchar(255) default NULL,
  `city` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `sex` varchar(255) default NULL,
  `identity_type` varchar(255) default NULL,
  `identity_code` varchar(255) default NULL,
  `phone` varchar(255) default NULL,
  `record_date` date default NULL,
  `company` varchar(255) default NULL,
  `certificate_code` varchar(255) default NULL,
  `device_code` varchar(20) default NULL,
  `role_type` varchar(255) default NULL,
  `role_id` varchar(20) default NULL,
  PRIMARY KEY  USING BTREE (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'wangqingzhao', '96e79218965eb72c92a549dd5a330112', '/img/1.jpg', '四川', '德阳', '南大街135号', 'F', '01', '510404199602188400', '13908769052', '2019-07-02', '中国银行', '110', '123456789', '0', '20190704F0001');
INSERT INTO `sys_user` VALUES ('2', 'chanbingbing', '96e79218965eb72c92a549dd5a330112', '/img/2.jpg', '四川', '成都', '青羊区镇远路25号', 'F', '01', '510405197410248662', '15999999999', '2019-07-02', '中国华为', '120', '123456788', '0', '20190704F0002');
INSERT INTO `sys_user` VALUES ('3', 'doctor', '96e79218965eb72c92a549dd5a330112', '/img/3.jpg', '福建', '厦门', '鼓浪屿1088号', '1', '01', '510123199808281234', '15999999999', '2019-07-04', '四川大学华西医院', '120', '987654322', '1', 'HOPEDOCID00001');
INSERT INTO `sys_user` VALUES ('4', 'jianla', '96e79218965eb72c92a549dd5a330112', '/img/1.jpg', '四川', '自贡', '荣县昌河大道西路1909号', 'F', '01', '510404200810052125', '15999999999', '2019-07-02', '成都银之杰', '110', '123456787', '0', '20190704F0003');
INSERT INTO `sys_user` VALUES ('5', 'yujina', '96e79218965eb72c92a549dd5a330112', '/img/2.jpg', '四川', '自贡', '自流井区平凡之路32号', 'F', '01', '510404199407051788', '15999999999', '2019-07-02', '自贡食堂', '110', '123456786', '0', '20190704F0004');
INSERT INTO `sys_user` VALUES ('6', 'leienna', '96e79218965eb72c92a549dd5a330112', '/img/3.jpg', '四川', '成都', '金牛区莫干沙街道多兰小区1栋', 'F', '01', '510402198005278502', '15999999999', '2019-07-02', '成都中医大', '110', '123456785', '0', '20190704F0005');
INSERT INTO `sys_user` VALUES ('8', 'lidali', '96e79218965eb72c92a549dd5a330112', '/img/1.jpg', '四川', '成都', '双流区冠道路92号', 'M', '01', '51040620080405473X', '15999999999', '2019-07-02', '双流中学', '110', '123456784', '0', '20190704M0001');
INSERT INTO `sys_user` VALUES ('9', 'dengkendi', '96e79218965eb72c92a549dd5a330112', '/img/1.jpg', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('10', 'caorong', '96e79218965eb72c92a549dd5a330112', '/img/1.jpg', null, null, null, null, null, null, null, null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for victiminfo
-- ----------------------------
DROP TABLE IF EXISTS `victiminfo`;
CREATE TABLE `victiminfo` (
  `victim_id` varchar(20) collate utf8_bin NOT NULL default '' COMMENT '受灾群众ID （物联网设备读取ID、基本信息主动录入时系统自动生成ID）',
  `victim_name` varchar(100) collate utf8_bin default '' COMMENT '姓名',
  `victim_gender` varchar(10) collate utf8_bin default '' COMMENT '性别F-女,M-男',
  `victim_certype` varchar(30) collate utf8_bin default '' COMMENT '证件类型',
  `victim_certno` varchar(30) collate utf8_bin default '' COMMENT '证件号码',
  `victim_phone` varchar(20) collate utf8_bin default '' COMMENT '联系方式',
  `address_code` varchar(20) collate utf8_bin default '' COMMENT '所在地区',
  `address_detail` varchar(100) collate utf8_bin default '' COMMENT '详细地址',
  `register_flag` char(1) collate utf8_bin default NULL COMMENT '是否主动登记 Y-是N-否',
  `serch_rescue` char(1) collate utf8_bin default '' COMMENT '是否寻求救援知识Y-是N-否',
  `rescue_knowledgeid` char(16) collate utf8_bin default NULL COMMENT '救援知识库号',
  `serch_psy` char(1) collate utf8_bin default '' COMMENT '是否寻求心理知识Y-是N-否',
  `psy_knowledgeid` char(16) collate utf8_bin default NULL COMMENT '心理知识库号',
  `join_psyflag` char(1) collate utf8_bin default '' COMMENT '是否参与心理测评Y-是N-否',
  `psytests_id` char(16) collate utf8_bin default NULL COMMENT '心理测评号',
  `tests_level` varchar(2) collate utf8_bin default NULL COMMENT '"当前心理测评等级H-HEALTH健康状态U-UNHEALTH不良状态 B-BOLCK心理障碍I-ILLNESS心理疾病"',
  `help_flag` char(1) collate utf8_bin default '' COMMENT '"当前是否接受辅导Y-是N-否"',
  `psychologist_id` varchar(20) collate utf8_bin default NULL COMMENT '当前辅导医师ID',
  `volunteer_id` varchar(20) collate utf8_bin default NULL COMMENT '当前辅导志愿者ID',
  `register_date` varchar(20) collate utf8_bin default '' COMMENT '登记日期',
  `added` varchar(100) collate utf8_bin default NULL COMMENT '备注',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备用1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备用2',
  PRIMARY KEY  USING BTREE (`victim_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='受灾群众基本信息表';

-- ----------------------------
-- Records of victiminfo
-- ----------------------------
INSERT INTO `victiminfo` VALUES ('20190704F0001', '王清照', 'F', '01', '510404199602188400', '13908769052', '四川省 德阳市', '南大街135号', 'Y', 'N', '', 'Y', 'PSYKID0001', '', null, null, '', null, 'VOLXPA9082', '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704F0002', '产冰冰', 'F', '01', '510405197410248662', '18079821356', '四川省 成都市', '青羊区镇远路25号', 'Y', 'Y', 'rescue.e003', 'N', '', '', '', '', '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704F0003', '吉安拉', 'F', '01', '510404200810052125', '18345781982', '四川省 自贡市', '荣县昌河大道西路1909号', 'Y', 'N', null, 'N', null, '', null, null, '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704F0004', '虞姬娜', 'F', '01', '510404199407051788', '18223551865', '四川省 自贡市', '自流井区平凡之路32号', 'Y', 'Y', 'rescue.m005', 'Y', 'PSYKID0005', '', null, null, '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704F0005', '雷恩娜', 'F', '01', '510402198005278502', '13788923742', '四川省 成都市', '金牛区莫干沙街道多兰小区1栋', 'Y', 'Y', 'rescue.e003', 'Y', 'PSYKID0005', '', null, null, '', null, 'VOLXPA7071', '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704M0001', '李大力', 'M', '01', '51040620080405473X', '13245679812', '四川省 成都市', '双流区冠道路92号', 'Y', 'Y', 'rescue.m005', 'N', null, '', null, null, '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704M0002', '邓肯迪', 'M', '01', '510402200908147776', '18542343009', '四川省 攀枝花市', '东区朗格里路14号', 'Y', 'Y', 'rescue.m004', 'Y', 'PSYKID0004', '', null, null, '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704M0003', '戴勒姆', 'M', '01', '510402197106194751', '15990827071', '四川省 南充市', '广汇南路1232号', 'Y', 'Y', 'rescue.g002', 'Y', 'PSYKID0001', '', null, null, '', null, 'VOLXPA9082', '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704M0004', '姬无命', 'M', '01', '510402197608254399', '17789083568', '四川省 攀枝花市', '东区拉格朗路77号', 'Y', 'Y', 'rescue.e003', 'N', 'PSYKID0005', '', null, null, '', null, null, '', null, null, null);
INSERT INTO `victiminfo` VALUES ('20190704M0005', '范甘迪', 'M', '01', '5104022198501295376', '15289649812', '四川省 成都市', '新都区图拉街道滨水城7栋', 'Y', 'N', null, 'Y', null, '', null, null, '', null, null, '', null, null, null);

-- ----------------------------
-- Table structure for volunteerinfo
-- ----------------------------
DROP TABLE IF EXISTS `volunteerinfo`;
CREATE TABLE `volunteerinfo` (
  `volunteer_id` char(20) collate utf8_bin NOT NULL default '' COMMENT '志愿者ID',
  `volunteer_name` varchar(100) collate utf8_bin NOT NULL default '' COMMENT '志愿者姓名',
  `volun_certtype` char(2) collate utf8_bin NOT NULL default '' COMMENT '志愿者证件类型',
  `volun_certno` varchar(30) collate utf8_bin NOT NULL default '' COMMENT '志愿者证件号码',
  `volun_address` char(20) collate utf8_bin NOT NULL default '' COMMENT '志愿者所在地区',
  `platform` char(20) collate utf8_bin NOT NULL default '' COMMENT '志愿者所在单位',
  `psyledge_type` char(1) collate utf8_bin NOT NULL default '' COMMENT '志愿者掌握心理知识大类',
  `psyledge_subtype` char(2) collate utf8_bin default NULL COMMENT '志愿者掌握心理知识小类',
  `psytests_type` char(1) collate utf8_bin NOT NULL default '' COMMENT '志愿者掌握心理测评知识大类',
  `psytests_subtype` char(2) collate utf8_bin default NULL COMMENT '志愿者掌握心理知识小类',
  `voluntary_type` char(1) collate utf8_bin NOT NULL default '' COMMENT '"志愿辅导类型O-线上D-线下A-线上线下"',
  `counseling` char(1) collate utf8_bin NOT NULL default '' COMMENT '"当前是否参与辅导Y-是N-否"',
  `counselingnum` varchar(5) collate utf8_bin default NULL COMMENT '当前辅导数（线上用）',
  `doctor_id` char(20) collate utf8_bin default NULL COMMENT '标记医师ID',
  `reserved1` varchar(100) collate utf8_bin default NULL COMMENT '备注1',
  `reserved2` varchar(100) collate utf8_bin default NULL COMMENT '备注2',
  `reserved3` varchar(100) collate utf8_bin default NULL COMMENT '备注3',
  `reserved4` varchar(100) collate utf8_bin default NULL COMMENT '备注4',
  PRIMARY KEY  USING BTREE (`volunteer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='志愿者录基本信息表';

-- ----------------------------
-- Records of volunteerinfo
-- ----------------------------
INSERT INTO `volunteerinfo` VALUES ('VOLXPA7071', '姬娜', '01', '542500196704172196', '重庆市', '格桑花', 'C', 'C2', 'S', 'SD', 'A', 'Y', '1', null, null, null, null, null);
INSERT INTO `volunteerinfo` VALUES ('VOLXPA8712', '李天禄', '01', '42070419871003583X', '四川省南充市', '平安救助', 'E', 'E3', 'S', 'SD', 'A', 'N', '0', null, null, null, null, null);
INSERT INTO `volunteerinfo` VALUES ('VOLXPA9082', '范同同', '01', '510703196004081405', '四川省绵阳市', '面朝大海', 'B', 'B1', 'S', 'SD', 'A', 'Y', '2', null, null, null, null, null);
