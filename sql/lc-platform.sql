/*
 Navicat Premium Data Transfer

 Source Server         : lc-platform
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 120.48.30.72:3306
 Source Schema         : lc-platform

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 30/05/2023 16:19:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for field_meta_info
-- ----------------------------
DROP TABLE IF EXISTS `field_meta_info`;
CREATE TABLE `field_meta_info` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint(4) NOT NULL,
  `physics_field_serial` int(11) NOT NULL,
  `logic_field_name` varchar(255) NOT NULL,
  `business_field_name` varchar(255) NOT NULL,
  `table_meta_info_id` bigint(20) NOT NULL,
  `field_type` varchar(255) NOT NULL,
  `field_length` int(11) NOT NULL,
  `nullable` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for table_meta_info
-- ----------------------------
DROP TABLE IF EXISTS `table_meta_info`;
CREATE TABLE `table_meta_info` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` tinyint(4) NOT NULL,
  `physics_table_serial` int(11) NOT NULL,
  `logic_table_name` varchar(255) NOT NULL,
  `business_table_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
