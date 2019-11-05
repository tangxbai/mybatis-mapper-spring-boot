/*
Navicat MySQL Data Transfer

Source Server Version : 50620
Source Database       : tester

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2019-10-22 14:58:15
*/
CREATE DATABASE IF NOT EXISTS tester DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE tester;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '账号昵称',
  `login_name` varchar(255) NOT NULL COMMENT '登录账号',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `gender` enum('MALE','FEMALE','UNKNOWN') NOT NULL DEFAULT 'UNKNOWN' COMMENT '性别',
  `age` int(4) NOT NULL DEFAULT '0' COMMENT '年龄',
  `display` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '是否可见',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT '乐观锁版本控制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户信息';
