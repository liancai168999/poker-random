/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : poker_random

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-01-05 17:05:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `poker_result`
-- ----------------------------
DROP TABLE IF EXISTS `poker_result`;
CREATE TABLE `poker_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poker_result` blob COMMENT '发牌结果集',
  `poker_num` int(11) DEFAULT NULL COMMENT '出牌时的牌幅数量',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `poker_public_key` varchar(3000) DEFAULT NULL COMMENT '公布明文结果解密时所需密钥(公钥)',
  `poker_private_key` varchar(3000) DEFAULT NULL COMMENT '私钥',
  `poker_private_result` blob COMMENT '加密后的数据',
  `tb_status` char(100) DEFAULT NULL COMMENT '状态：正常：正常；删除：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1129 DEFAULT CHARSET=utf8 COMMENT='出牌结果表';

-- ----------------------------
-- Records of poker_result
-- ----------------------------

-- ----------------------------
-- Table structure for `poker_type`
-- ----------------------------
DROP TABLE IF EXISTS `poker_type`;
CREATE TABLE `poker_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` char(200) DEFAULT NULL COMMENT '牌面对应数字',
  `type` char(200) DEFAULT NULL COMMENT '牌面花色',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `tb_status` char(100) DEFAULT NULL COMMENT '状态：正常：正常；删除：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='设置初始化牌的类型-数值';

-- ----------------------------
-- Records of poker_type
-- ----------------------------
INSERT INTO `poker_type` VALUES ('1', '01,02,03,04,05,06,07,08,09,10,11,12,13', '1,2,3,4', '2018-12-03 17:51:04', null, '正常');
INSERT INTO `poker_type` VALUES ('2', '2', null, '2018-12-03 18:01:52', '2018-12-03 18:01:52', '正常');
