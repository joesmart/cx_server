/*
Navicat MySQL Data Transfer

Source Server         : 10.90.3.122
Source Server Version : 50149
Source Host           : 10.90.3.122:3306
Source Database       : bussiness

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2012-08-01 10:04:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `acct_group`
-- ----------------------------
DROP TABLE IF EXISTS `acct_group`;
CREATE TABLE `acct_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acct_group
-- ----------------------------

-- ----------------------------
-- Table structure for `acct_group_permission`
-- ----------------------------
DROP TABLE IF EXISTS `acct_group_permission`;
CREATE TABLE `acct_group_permission` (
  `group_id` bigint(20) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  KEY `FK19C1023BEFE35BA9` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acct_group_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `acct_user`
-- ----------------------------
DROP TABLE IF EXISTS `acct_user`;
CREATE TABLE `acct_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acct_user
-- ----------------------------

-- ----------------------------
-- Table structure for `acct_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `acct_user_group`;
CREATE TABLE `acct_user_group` (
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `FKD19A04B7274F65AB` (`user_id`),
  KEY `FKD19A04B7EFE35BA9` (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of acct_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `download_num` int(11) DEFAULT NULL,
  `graphic_resource_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for `graphic_infos`
-- ----------------------------
DROP TABLE IF EXISTS `graphic_infos`;
CREATE TABLE `graphic_infos` (
  `id` varchar(32) NOT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `recommend` tinyint(1) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `use_count` int(11) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `holiday_type_id` bigint(20) DEFAULT NULL,
  `status_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA2E5796EE36BFFE3` (`category_id`),
  KEY `FKA2E5796EEBB54FE8` (`status_type_id`),
  KEY `FKA2E5796ECDC4AB82` (`holiday_type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of graphic_infos
-- ----------------------------

-- ----------------------------
-- Table structure for `graphic_resource`
-- ----------------------------
DROP TABLE IF EXISTS `graphic_resource`;
CREATE TABLE `graphic_resource` (
  `id` varchar(32) NOT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `audit_passed` tinyint(1) DEFAULT NULL,
  `graphic_id` varchar(255) DEFAULT NULL,
  `resource_id` varchar(255) DEFAULT NULL,
  `source_path` varchar(255) DEFAULT NULL,
  `thumbnail_path` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `graphicinfo_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4F320785E231CA91` (`graphicinfo_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of graphic_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `holiday_type`
-- ----------------------------
DROP TABLE IF EXISTS `holiday_type`;
CREATE TABLE `holiday_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `download_num` int(11) DEFAULT NULL,
  `graphic_resource_id` varchar(255) DEFAULT NULL,
  `items_num` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of holiday_type
-- ----------------------------

-- ----------------------------
-- Table structure for `mgraphic_storemode`
-- ----------------------------
DROP TABLE IF EXISTS `mgraphic_storemode`;
CREATE TABLE `mgraphic_storemode` (
  `id` varchar(32) NOT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `audit_pass` tinyint(1) NOT NULL,
  `end_hour` int(11) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `graphic_id` varchar(255) DEFAULT NULL,
  `mode_type` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_no` longtext,
  `price` double DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `start_hour` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24444CA06C146C11` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mgraphic_storemode
-- ----------------------------

-- ----------------------------
-- Table structure for `signature`
-- ----------------------------
DROP TABLE IF EXISTS `signature`;
CREATE TABLE `signature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT '-1',
  `type` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of signature
-- ----------------------------

-- ----------------------------
-- Table structure for `sms_message`
-- ----------------------------
DROP TABLE IF EXISTS `sms_message`;
CREATE TABLE `sms_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `from_mobile_no` varchar(255) DEFAULT NULL,
  `is_send` tinyint(1) DEFAULT NULL,
  `sms` varchar(255) DEFAULT NULL,
  `to_mobile_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms_message
-- ----------------------------

-- ----------------------------
-- Table structure for `status_type`
-- ----------------------------
DROP TABLE IF EXISTS `status_type`;
CREATE TABLE `status_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `graphic_resource_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of status_type
-- ----------------------------

-- ----------------------------
-- Table structure for `type`
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of type
-- ----------------------------

-- ----------------------------
-- Table structure for `user_catacts`
-- ----------------------------
DROP TABLE IF EXISTS `user_catacts`;
CREATE TABLE `user_catacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `self_user_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7C0ED6836C146C11` (`user_id`),
  KEY `FK7C0ED6834DEBD61E` (`self_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_catacts
-- ----------------------------

-- ----------------------------
-- Table structure for `user_favorites`
-- ----------------------------
DROP TABLE IF EXISTS `user_favorites`;
CREATE TABLE `user_favorites` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF2FF4EE36C146C11` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_favorites
-- ----------------------------

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `cx_service` int(11) DEFAULT NULL,
  `imsi` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_no` (`phone_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `versioninfo`
-- ----------------------------
DROP TABLE IF EXISTS `versioninfo`;
CREATE TABLE `versioninfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(64) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(64) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of versioninfo
-- ----------------------------
