/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : sqlexample

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 12:23:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `eno` int NOT NULL,
  `ename` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` decimal(10, 2) NULL DEFAULT NULL,
  `dno` int NULL DEFAULT NULL,
  PRIMARY KEY (`eno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (1, '无忌', 283300.00, 1001);
INSERT INTO `employees` VALUES (2, '婉清', 276800.00, 1003);
INSERT INTO `employees` VALUES (3, '不悔', 309760.00, 1003);
INSERT INTO `employees` VALUES (4, '婉清', 154600.00, 1004);
INSERT INTO `employees` VALUES (5, '建宁', 186980.00, 1002);
INSERT INTO `employees` VALUES (6, '非烟', 256640.00, 1001);
INSERT INTO `employees` VALUES (7, '黄蓉', 312870.00, 1005);
INSERT INTO `employees` VALUES (8, '清笃', 135320.00, 1003);
INSERT INTO `employees` VALUES (9, '芷若', 176380.00, 1004);
INSERT INTO `employees` VALUES (10, '友谅', 299210.00, 1005);

-- ----------------------------
-- Table structure for projects
-- ----------------------------
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects`  (
  `pno` int NOT NULL,
  `pname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dno` int NULL DEFAULT NULL,
  PRIMARY KEY (`pno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of projects
-- ----------------------------
INSERT INTO `projects` VALUES (2001, 'hadoop', 'beijing', 1001);
INSERT INTO `projects` VALUES (2002, 'spark', 'shanghai', 1005);
INSERT INTO `projects` VALUES (2003, 'zookeeper', 'hangzhou', 1003);
INSERT INTO `projects` VALUES (2004, 'mapreduce', 'shenzhou', 1002);
INSERT INTO `projects` VALUES (2005, 'hdfs', 'beijing', 1001);
INSERT INTO `projects` VALUES (2006, 'yarn', 'shanghai', 1005);
INSERT INTO `projects` VALUES (2007, 'avro', 'nanjing', 1004);
INSERT INTO `projects` VALUES (2008, 'parquet', 'hangzhou', 1003);

-- ----------------------------
-- Table structure for relations
-- ----------------------------
DROP TABLE IF EXISTS `relations`;
CREATE TABLE `relations`  (
  `eno` int NOT NULL,
  `rname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`rname`, `eno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relations
-- ----------------------------
INSERT INTO `relations` VALUES (5, '东珠', '女');
INSERT INTO `relations` VALUES (7, '冯蘅', '女');
INSERT INTO `relations` VALUES (5, '头陀', '男');
INSERT INTO `relations` VALUES (9, '子旺', '男');
INSERT INTO `relations` VALUES (3, '晓芙', '女');
INSERT INTO `relations` VALUES (6, '曲洋', '男');
INSERT INTO `relations` VALUES (2, '正淳', '男');
INSERT INTO `relations` VALUES (1, '素素', '女');
INSERT INTO `relations` VALUES (1, '翠山', '男');
INSERT INTO `relations` VALUES (7, '药师', '男');

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works`  (
  `eno` int NOT NULL,
  `pno` int NOT NULL,
  `hours` double(5, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`eno`, `pno`) USING BTREE,
  INDEX `pno`(`pno`) USING BTREE,
  CONSTRAINT `eno` FOREIGN KEY (`eno`) REFERENCES `employees` (`eno`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pno` FOREIGN KEY (`pno`) REFERENCES `projects` (`pno`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES (1, 2001, 670);
INSERT INTO `works` VALUES (1, 2005, 240);
INSERT INTO `works` VALUES (2, 2003, 876);
INSERT INTO `works` VALUES (2, 2008, 246);
INSERT INTO `works` VALUES (3, 2008, 890);
INSERT INTO `works` VALUES (4, 2007, 649);
INSERT INTO `works` VALUES (5, 2004, 749);
INSERT INTO `works` VALUES (6, 2005, 1260);
INSERT INTO `works` VALUES (7, 2002, 580);
INSERT INTO `works` VALUES (7, 2006, 540);
INSERT INTO `works` VALUES (8, 2003, 578);
INSERT INTO `works` VALUES (8, 2008, 341);
INSERT INTO `works` VALUES (9, 2007, 930);
INSERT INTO `works` VALUES (10, 2006, 1128);

SET FOREIGN_KEY_CHECKS = 1;
