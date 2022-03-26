/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : invoicerelatedscenarios

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 24/11/2020 21:47:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for contacts
-- ----------------------------
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts`  (
  `user_id` int NOT NULL,
  `contact_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contact_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`, `contact_email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contacts
-- ----------------------------
INSERT INTO `contacts` VALUES (1, 'bob@smail.nju.edu.cn', 'Bob');
INSERT INTO `contacts` VALUES (1, 'Jal@smail.nju.edu.cn', 'Jal');
INSERT INTO `contacts` VALUES (1, 'john@smail.nju.edu.cn', 'John');
INSERT INTO `contacts` VALUES (2, 'Meir@smail.nju.edu.cn', 'Meir');
INSERT INTO `contacts` VALUES (2, 'Omar@smail.nju.edu.cn', 'Omar');
INSERT INTO `contacts` VALUES (6, 'Alice@smail.nju.edu.cn', 'Alice');

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers`  (
  `customer_id` int NOT NULL,
  `customer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES (1, 'Alice', 'alice@smail.nju.cn');
INSERT INTO `customers` VALUES (2, 'Bob', 'bob@smail.nju.cn');
INSERT INTO `customers` VALUES (6, 'Alex', 'alex@smail.nju.cn');
INSERT INTO `customers` VALUES (13, 'John', 'john@smail.nju.cn');

-- ----------------------------
-- Table structure for invoices
-- ----------------------------
DROP TABLE IF EXISTS `invoices`;
CREATE TABLE `invoices`  (
  `invoice_id` int NOT NULL,
  `price` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`invoice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoices
-- ----------------------------
INSERT INTO `invoices` VALUES (44, 60, 6);
INSERT INTO `invoices` VALUES (55, 500, 13);
INSERT INTO `invoices` VALUES (66, 400, 2);
INSERT INTO `invoices` VALUES (77, 100, 1);
INSERT INTO `invoices` VALUES (88, 200, 1);
INSERT INTO `invoices` VALUES (99, 300, 2);

SET FOREIGN_KEY_CHECKS = 1;
