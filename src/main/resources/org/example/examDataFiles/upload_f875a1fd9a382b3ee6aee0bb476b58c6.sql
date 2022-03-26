/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title5

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 15:45:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `item_id` int NOT NULL,
  `item_brand` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (1, 'Samsung');
INSERT INTO `items` VALUES (2, 'Apple');
INSERT INTO `items` VALUES (3, 'Huawei');
INSERT INTO `items` VALUES (4, 'Lenovo');
INSERT INTO `items` VALUES (5, 'Nintendo');
INSERT INTO `items` VALUES (6, 'Xiaomi');
INSERT INTO `items` VALUES (7, 'Asus');
INSERT INTO `items` VALUES (8, 'Sony');
INSERT INTO `items` VALUES (9, 'Microsoft');
INSERT INTO `items` VALUES (10, 'Oppo');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL,
  `order_date` date NULL DEFAULT NULL,
  `item_id` int NULL DEFAULT NULL,
  `buyer_id` int NULL DEFAULT NULL,
  `seller_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `buyer_id`(`buyer_id`) USING BTREE,
  INDEX `seller_id`(`seller_id`) USING BTREE,
  INDEX `item_id`(`item_id`) USING BTREE,
  CONSTRAINT `buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `item_id` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `seller_id` FOREIGN KEY (`seller_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '2019-08-09', 3, 4, 1);
INSERT INTO `orders` VALUES (2, '2019-10-25', 4, 1, 3);
INSERT INTO `orders` VALUES (3, '2020-07-21', 2, 5, 2);
INSERT INTO `orders` VALUES (4, '2020-05-28', 5, 7, 2);
INSERT INTO `orders` VALUES (5, '2019-10-20', 5, 3, 6);
INSERT INTO `orders` VALUES (6, '2019-10-25', 2, 13, 11);
INSERT INTO `orders` VALUES (7, '2019-07-17', 1, 10, 4);
INSERT INTO `orders` VALUES (8, '2020-04-15', 5, 3, 5);
INSERT INTO `orders` VALUES (9, '2020-08-13', 8, 2, 7);
INSERT INTO `orders` VALUES (10, '2019-11-12', 7, 6, 7);
INSERT INTO `orders` VALUES (11, '2019-06-21', 9, 12, 5);
INSERT INTO `orders` VALUES (12, '2020-07-09', 10, 6, 8);
INSERT INTO `orders` VALUES (13, '2019-07-31', 2, 1, 12);
INSERT INTO `orders` VALUES (14, '2020-09-21', 9, 8, 7);
INSERT INTO `orders` VALUES (15, '2019-10-19', 8, 3, 10);
INSERT INTO `orders` VALUES (16, '2019-11-17', 2, 14, 9);
INSERT INTO `orders` VALUES (17, '2020-05-26', 8, 2, 3);
INSERT INTO `orders` VALUES (18, '2018-08-23', 3, 7, 4);
INSERT INTO `orders` VALUES (19, '2020-07-28', 10, 5, 2);
INSERT INTO `orders` VALUES (20, '2020-01-30', 7, 4, 11);
INSERT INTO `orders` VALUES (21, '2019-05-21', 3, 12, 9);
INSERT INTO `orders` VALUES (22, '2020-07-12', 1, 5, 8);
INSERT INTO `orders` VALUES (23, '2019-10-25', 4, 5, 6);
INSERT INTO `orders` VALUES (24, '2019-11-11', 2, 13, 14);
INSERT INTO `orders` VALUES (25, '2019-12-08', 9, 6, 12);
INSERT INTO `orders` VALUES (26, '2020-10-23', 7, 14, 8);
INSERT INTO `orders` VALUES (27, '2018-12-01', 2, 7, 13);
INSERT INTO `orders` VALUES (28, '2019-09-04', 6, 4, 11);
INSERT INTO `orders` VALUES (29, '2019-06-18', 4, 11, 7);
INSERT INTO `orders` VALUES (30, '2020-08-12', 1, 10, 2);
INSERT INTO `orders` VALUES (31, '2019-07-23', 10, 9, 6);
INSERT INTO `orders` VALUES (32, '2019-10-20', 8, 12, 10);
INSERT INTO `orders` VALUES (33, '2019-06-08', 2, 10, 4);
INSERT INTO `orders` VALUES (34, '2019-07-18', 1, 11, 7);
INSERT INTO `orders` VALUES (35, '2019-11-23', 3, 6, 3);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL,
  `join_date` date NULL DEFAULT NULL,
  `favorite_brand` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2019-07-24', 'Sony');
INSERT INTO `users` VALUES (2, '2020-03-19', 'Apple');
INSERT INTO `users` VALUES (3, '2019-06-15', 'Huawei');
INSERT INTO `users` VALUES (4, '2018-03-30', 'Sony');
INSERT INTO `users` VALUES (5, '2019-03-06', 'Nintendo');
INSERT INTO `users` VALUES (6, '2019-09-24', 'Lenovo');
INSERT INTO `users` VALUES (7, '2018-07-19', 'Sumsang');
INSERT INTO `users` VALUES (8, '2020-02-20', 'Microsoft');
INSERT INTO `users` VALUES (9, '2018-09-13', 'Apple');
INSERT INTO `users` VALUES (10, '2019-04-12', 'Asus');
INSERT INTO `users` VALUES (11, '2019-05-31', 'Oppo');
INSERT INTO `users` VALUES (12, '2019-01-15', 'Nintendo');
INSERT INTO `users` VALUES (13, '2018-09-05', 'Xiaomi');
INSERT INTO `users` VALUES (14, '2019-05-29', 'Apple');

SET FOREIGN_KEY_CHECKS = 1;
