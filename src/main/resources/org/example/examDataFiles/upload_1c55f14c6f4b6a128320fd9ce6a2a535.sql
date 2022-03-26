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

 Date: 07/11/2020 13:42:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `eno` int NOT NULL,
  `ename` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` decimal(10, 2) NULL DEFAULT NULL,
  `dno` int NULL DEFAULT NULL,
  PRIMARY KEY (`eno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'zhangsan', 283300.00, 1001);
INSERT INTO `employee` VALUES (2, 'lisi', 276800.00, 1003);
INSERT INTO `employee` VALUES (3, 'wangwu', 309760.00, 1003);
INSERT INTO `employee` VALUES (4, 'xieliu', 154600.00, 1004);
INSERT INTO `employee` VALUES (5, 'chenqi', 186980.00, 1002);
INSERT INTO `employee` VALUES (6, 'kaikai', 256640.00, 1001);
INSERT INTO `employee` VALUES (7, 'jieni', 312870.00, 1005);
INSERT INTO `employee` VALUES (8, 'piki', 135320.00, 1003);
INSERT INTO `employee` VALUES (9, 'bide', 176380.00, 1004);
INSERT INTO `employee` VALUES (10, 'trup', 299210.00, 1005);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `pno` int NOT NULL,
  `pname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dno` int NULL DEFAULT NULL,
  PRIMARY KEY (`pno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (2001, 'hadaop', 'beijing', 1001);
INSERT INTO `project` VALUES (2002, 'sparck', 'shanghai', 1005);
INSERT INTO `project` VALUES (2003, 'javea', 'hangzhou', 1003);
INSERT INTO `project` VALUES (2004, 'hexe', 'shenzhou', 1002);
INSERT INTO `project` VALUES (2005, 'pypyon', 'beijing', 1001);
INSERT INTO `project` VALUES (2006, 'dodo', 'shanghai', 1005);
INSERT INTO `project` VALUES (2007, 'cyberp', 'nanjing', 1004);
INSERT INTO `project` VALUES (2008, 'mobac', 'hangzhou', 1003);

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation`  (
  `eno` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`name`, `eno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES (9, 'baima', '女');
INSERT INTO `relation` VALUES (5, 'chendie', '男');
INSERT INTO `relation` VALUES (5, 'chenma', '女');
INSERT INTO `relation` VALUES (7, 'jiedie', '男');
INSERT INTO `relation` VALUES (7, 'jiema', '女');
INSERT INTO `relation` VALUES (6, 'kaidie', '男');
INSERT INTO `relation` VALUES (2, 'lidie', '男');
INSERT INTO `relation` VALUES (3, 'wangma', '女');
INSERT INTO `relation` VALUES (1, 'zhangdie', '男');
INSERT INTO `relation` VALUES (1, 'zhangma', '女');

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`  (
  `eno` int NOT NULL,
  `pno` int NOT NULL,
  `hours` double(5, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`eno`, `pno`) USING BTREE,
  INDEX `pno`(`pno`) USING BTREE,
  CONSTRAINT `pno` FOREIGN KEY (`pno`) REFERENCES `project` (`pno`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `eno` FOREIGN KEY (`eno`) REFERENCES `employee` (`eno`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES (1, 2001, 670);
INSERT INTO `work` VALUES (1, 2005, 240);
INSERT INTO `work` VALUES (2, 2003, 876);
INSERT INTO `work` VALUES (2, 2008, 246);
INSERT INTO `work` VALUES (3, 2008, 890);
INSERT INTO `work` VALUES (4, 2007, 649);
INSERT INTO `work` VALUES (5, 2004, 749);
INSERT INTO `work` VALUES (6, 2005, 1260);
INSERT INTO `work` VALUES (7, 2002, 580);
INSERT INTO `work` VALUES (7, 2006, 540);
INSERT INTO `work` VALUES (8, 2003, 578);
INSERT INTO `work` VALUES (8, 2008, 341);
INSERT INTO `work` VALUES (9, 2007, 930);
INSERT INTO `work` VALUES (10, 2006, 1128);

SET FOREIGN_KEY_CHECKS = 1;
