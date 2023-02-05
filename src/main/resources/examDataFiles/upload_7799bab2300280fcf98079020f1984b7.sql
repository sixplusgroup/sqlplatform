/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : useractivityscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 24/11/2020 16:24:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities`  (
  `id` int NOT NULL,
  `activity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES (1, 'Eating', '2020-02-12', '2020-02-20');
INSERT INTO `activities` VALUES (2, 'Singing', '2020-02-21', '2020-02-23');
INSERT INTO `activities` VALUES (3, 'Horse Riding', '2020-02-24', '2020-02-28');

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friends
-- ----------------------------
INSERT INTO `friends` VALUES (1, 'Jonathan D', 'Eating');
INSERT INTO `friends` VALUES (2, 'Jade W', 'Singing');
INSERT INTO `friends` VALUES (3, 'Victor J', 'Singing');
INSERT INTO `friends` VALUES (4, 'Elvis Q', 'Eating');
INSERT INTO `friends` VALUES (5, 'Daniel A', 'Eating');
INSERT INTO `friends` VALUES (6, 'Bob B', 'Horse Riding');

SET FOREIGN_KEY_CHECKS = 1;
