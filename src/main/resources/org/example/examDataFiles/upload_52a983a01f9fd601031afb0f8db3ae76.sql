/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : employeesalary

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/11/2020 17:54:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `ID` int NOT NULL,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, 'Sales');
INSERT INTO `department` VALUES (2, 'IT');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `ID` int NOT NULL,
  `Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Salary` float(10, 2) NOT NULL,
  `DepartmentId` int NOT NULL,
  `ManagerId` int NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, 'Joe', 84000.00, 1, 4);
INSERT INTO `employee` VALUES (2, 'Henry', 80000.00, 2, 9);
INSERT INTO `employee` VALUES (3, 'Sam', 60000.00, 2, 9);
INSERT INTO `employee` VALUES (4, 'Max', 120000.00, 1, NULL);
INSERT INTO `employee` VALUES (5, 'Janet', 69000.00, 1, 4);
INSERT INTO `employee` VALUES (6, 'Randy', 85000.00, 1, 4);
INSERT INTO `employee` VALUES (7, 'Will', 70000.00, 1, 4);
INSERT INTO `employee` VALUES (8, 'Jim', 130000.00, 2, 9);
INSERT INTO `employee` VALUES (9, 'Katie', 120000.00, 2, NULL);
INSERT INTO `employee` VALUES (10, 'Paulin', 125000.00, 2, 9);
INSERT INTO `employee` VALUES (11, 'Jack', 130000.00, 1, 4);

SET FOREIGN_KEY_CHECKS = 1;
