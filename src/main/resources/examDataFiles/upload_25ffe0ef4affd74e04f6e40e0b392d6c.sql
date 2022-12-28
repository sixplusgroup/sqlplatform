/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title3

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 14:56:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customer_id` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, 'Aegil');
INSERT INTO `customer` VALUES (2, 'Balder');
INSERT INTO `customer` VALUES (3, 'Borr');
INSERT INTO `customer` VALUES (4, 'Honir');
INSERT INTO `customer` VALUES (5, 'Thor');
INSERT INTO `customer` VALUES (6, 'Njord');
INSERT INTO `customer` VALUES (7, 'Ran');
INSERT INTO `customer` VALUES (8, 'Loki');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL,
  `order_date` date NULL DEFAULT NULL,
  `customer_id` int NULL DEFAULT NULL,
  `product_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2019-12-19', 1, 3);
INSERT INTO `orders` VALUES (2, '2020-03-19', 1, 7);
INSERT INTO `orders` VALUES (3, '2020-02-27', 3, 9);
INSERT INTO `orders` VALUES (4, '2020-06-10', 4, 3);
INSERT INTO `orders` VALUES (5, '2020-08-05', 6, 4);
INSERT INTO `orders` VALUES (6, '2020-07-16', 4, 8);
INSERT INTO `orders` VALUES (7, '2019-08-23', 2, 8);
INSERT INTO `orders` VALUES (8, '2020-08-10', 4, 2);
INSERT INTO `orders` VALUES (9, '2020-09-25', 5, 5);
INSERT INTO `orders` VALUES (10, '2020-11-11', 5, 2);
INSERT INTO `orders` VALUES (11, '2020-11-11', 5, 3);
INSERT INTO `orders` VALUES (12, '2020-11-11', 4, 1);
INSERT INTO `orders` VALUES (13, '2020-11-11', 2, 4);
INSERT INTO `orders` VALUES (14, '2020-11-11', 3, 1);
INSERT INTO `orders` VALUES (15, '2020-11-11', 8, 3);
INSERT INTO `orders` VALUES (16, '2020-09-11', 6, 7);
INSERT INTO `orders` VALUES (17, '2020-10-16', 7, 9);
INSERT INTO `orders` VALUES (18, '2020-09-20', 3, 4);
INSERT INTO `orders` VALUES (19, '2020-10-03', 3, 2);
INSERT INTO `orders` VALUES (20, '2020-09-13', 2, 3);
INSERT INTO `orders` VALUES (21, '2020-09-29', 5, 1);
INSERT INTO `orders` VALUES (22, '2020-08-21', 6, 9);
INSERT INTO `orders` VALUES (23, '2020-08-19', 6, 3);
INSERT INTO `orders` VALUES (24, '2020-07-27', 5, 4);
INSERT INTO `orders` VALUES (25, '2020-08-17', 5, 8);
INSERT INTO `orders` VALUES (26, '2020-10-31', 8, 4);
INSERT INTO `orders` VALUES (27, '2020-11-06', 7, 3);
INSERT INTO `orders` VALUES (28, '2020-08-18', 5, 9);
INSERT INTO `orders` VALUES (29, '2020-08-15', 3, 5);
INSERT INTO `orders` VALUES (30, '2020-09-19', 1, 5);
INSERT INTO `orders` VALUES (31, '2020-09-19', 5, 6);
INSERT INTO `orders` VALUES (32, '2020-09-06', 2, 5);
INSERT INTO `orders` VALUES (33, '2020-11-14', 5, 7);
INSERT INTO `orders` VALUES (34, '2020-06-19', 4, 6);
INSERT INTO `orders` VALUES (35, '2020-08-24', 2, 6);
INSERT INTO `orders` VALUES (36, '2020-08-04', 4, 9);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int NOT NULL,
  `product_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` int NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 'cpu', 2500);
INSERT INTO `product` VALUES (2, 'memory', 800);
INSERT INTO `product` VALUES (3, 'harddisk', 1000);
INSERT INTO `product` VALUES (4, 'mouse', 250);
INSERT INTO `product` VALUES (5, 'keyboard', 900);
INSERT INTO `product` VALUES (6, 'screen', 1500);
INSERT INTO `product` VALUES (7, 'computercase', 640);
INSERT INTO `product` VALUES (8, 'power supply', 950);
INSERT INTO `product` VALUES (9, 'video card', 3500);

SET FOREIGN_KEY_CHECKS = 1;
