/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : commodityorderscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/11/2020 21:38:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers`  (
  `customer_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES (1, 'Winston');
INSERT INTO `customers` VALUES (2, 'Jonathan');
INSERT INTO `customers` VALUES (3, 'Annabelle');
INSERT INTO `customers` VALUES (4, 'Marwan');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `order_date` datetime(0) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 1, '2020-07-31 19:55:54', 1);
INSERT INTO `orders` VALUES (2, 2, '2020-07-30 19:59:38', 2);
INSERT INTO `orders` VALUES (3, 3, '2020-08-29 20:00:00', 3);
INSERT INTO `orders` VALUES (4, 4, '2020-07-29 20:02:09', 1);
INSERT INTO `orders` VALUES (5, 1, '2020-06-10 20:02:40', 2);
INSERT INTO `orders` VALUES (6, 2, '2020-08-01 20:03:06', 1);
INSERT INTO `orders` VALUES (7, 3, '2020-08-01 20:03:34', 1);
INSERT INTO `orders` VALUES (8, 1, '2020-08-03 20:04:03', 2);
INSERT INTO `orders` VALUES (9, 2, '2020-08-07 20:04:32', 3);
INSERT INTO `orders` VALUES (10, 1, '2020-07-15 20:04:56', 2);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `product_id` int NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` float(10, 2) NOT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'keyboard', 120.00);
INSERT INTO `products` VALUES (2, 'mouse', 80.00);
INSERT INTO `products` VALUES (3, 'screen ', 600.00);
INSERT INTO `products` VALUES (4, 'harddisk', 450.00);

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales`  (
  `product_id` int NOT NULL,
  `period_start` date NOT NULL,
  `period_end` date NOT NULL,
  `average_daily_sales` int NOT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sales
-- ----------------------------
INSERT INTO `sales` VALUES (1, '2019-01-25', '2019-02-28', 10);
INSERT INTO `sales` VALUES (2, '2018-12-01', '2020-01-01', 100);
INSERT INTO `sales` VALUES (3, '2019-12-01', '2020-01-31', 1);

SET FOREIGN_KEY_CHECKS = 1;
