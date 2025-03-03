/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80037
 Source Host           : localhost:3306
 Source Schema         : examdb

 Target Server Type    : MySQL
 Target Server Version : 80037
 File Encoding         : 65001

 Date: 06/12/2024 14:10:38
*/

SET NAMES utf8mb3;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for emp_bonus
-- ----------------------------
DROP TABLE IF EXISTS `emp_bonus`;
CREATE TABLE `emp_bonus`  (
  `emp_no` int(0) NOT NULL,
  `recevied` datetime(0) NOT NULL,
  `btype` smallint(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp_bonus
-- ----------------------------
INSERT INTO `emp_bonus` VALUES (10001, '2010-01-01 00:00:00', 1);
INSERT INTO `emp_bonus` VALUES (10002, '2010-10-01 00:00:00', 2);

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `emp_no` int(0) NOT NULL,
  `birth_date` date NOT NULL,
  `first_name` varchar(14) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `last_name` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `gender` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `hire_date` date NOT NULL,
  PRIMARY KEY (`emp_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (10001, '1953-09-02', 'Georgi', 'Facello', 'M', '1986-06-26');
INSERT INTO `employees` VALUES (10002, '1964-06-02', 'Bezalel', 'Simmel', 'F', '1985-11-21');

-- ----------------------------
-- Table structure for salaries
-- ----------------------------
DROP TABLE IF EXISTS `salaries`;
CREATE TABLE `salaries`  (
  `emp_no` int(0) NOT NULL,
  `salary` int(0) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  PRIMARY KEY (`emp_no`, `from_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of salaries
-- ----------------------------
INSERT INTO `salaries` VALUES (10001, 60117, '1986-06-26', '1987-06-26');
INSERT INTO `salaries` VALUES (10001, 62102, '1987-06-26', '1988-06-25');
INSERT INTO `salaries` VALUES (10001, 66074, '1988-06-25', '1989-06-25');
INSERT INTO `salaries` VALUES (10001, 66596, '1989-06-25', '1990-06-25');
INSERT INTO `salaries` VALUES (10001, 66961, '1990-06-25', '1991-06-25');
INSERT INTO `salaries` VALUES (10001, 71046, '1991-06-25', '1992-06-24');
INSERT INTO `salaries` VALUES (10001, 74333, '1992-06-24', '1993-06-24');
INSERT INTO `salaries` VALUES (10001, 75286, '1993-06-24', '1994-06-24');
INSERT INTO `salaries` VALUES (10001, 75994, '1994-06-24', '1995-06-24');
INSERT INTO `salaries` VALUES (10001, 76884, '1995-06-24', '1996-06-23');
INSERT INTO `salaries` VALUES (10001, 80013, '1996-06-23', '1997-06-23');
INSERT INTO `salaries` VALUES (10001, 81025, '1997-06-23', '1998-06-23');
INSERT INTO `salaries` VALUES (10001, 81097, '1998-06-23', '1999-06-23');
INSERT INTO `salaries` VALUES (10001, 84917, '1999-06-23', '2000-06-22');
INSERT INTO `salaries` VALUES (10001, 85112, '2000-06-22', '2001-06-22');
INSERT INTO `salaries` VALUES (10001, 85097, '2001-06-22', '2002-06-22');
INSERT INTO `salaries` VALUES (10001, 88958, '2002-06-22', '9999-01-01');
INSERT INTO `salaries` VALUES (10002, 72527, '1996-08-03', '1997-08-03');
INSERT INTO `salaries` VALUES (10002, 72527, '1997-08-03', '1998-08-03');
INSERT INTO `salaries` VALUES (10002, 72527, '1998-08-03', '1999-08-03');
INSERT INTO `salaries` VALUES (10002, 72527, '1999-08-03', '2000-08-02');
INSERT INTO `salaries` VALUES (10002, 72527, '2000-08-02', '2001-08-02');
INSERT INTO `salaries` VALUES (10002, 72527, '2001-08-02', '9999-01-01');

SET FOREIGN_KEY_CHECKS = 1;
