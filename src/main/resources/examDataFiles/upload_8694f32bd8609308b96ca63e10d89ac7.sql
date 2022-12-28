/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : systemstatescenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 25/11/2020 12:07:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events`  (
  `business_id` int NOT NULL,
  `event_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `occurences` int NOT NULL,
  PRIMARY KEY (`business_id`, `event_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of events
-- ----------------------------
INSERT INTO `events` VALUES (1, 'ads', 11);
INSERT INTO `events` VALUES (1, 'page views', 3);
INSERT INTO `events` VALUES (1, 'reviews', 7);
INSERT INTO `events` VALUES (2, 'ads', 7);
INSERT INTO `events` VALUES (2, 'page views', 12);
INSERT INTO `events` VALUES (3, 'ads', 6);
INSERT INTO `events` VALUES (3, 'reviews', 3);

-- ----------------------------
-- Table structure for failed
-- ----------------------------
DROP TABLE IF EXISTS `failed`;
CREATE TABLE `failed`  (
  `fail_date` date NOT NULL,
  `event_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`fail_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of failed
-- ----------------------------
INSERT INTO `failed` VALUES ('2018-12-28', 'reviews');
INSERT INTO `failed` VALUES ('2018-12-29', 'reviews');
INSERT INTO `failed` VALUES ('2019-01-04', 'ads');
INSERT INTO `failed` VALUES ('2019-01-05', 'reviews');

-- ----------------------------
-- Table structure for succeeded
-- ----------------------------
DROP TABLE IF EXISTS `succeeded`;
CREATE TABLE `succeeded`  (
  `success_date` date NOT NULL,
  `event_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`success_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of succeeded
-- ----------------------------
INSERT INTO `succeeded` VALUES ('2018-12-30', 'page views');
INSERT INTO `succeeded` VALUES ('2018-12-31', 'reviews');
INSERT INTO `succeeded` VALUES ('2019-01-01', 'ads');
INSERT INTO `succeeded` VALUES ('2019-01-02', 'ads');
INSERT INTO `succeeded` VALUES ('2019-01-03', 'page views');
INSERT INTO `succeeded` VALUES ('2019-01-06', 'reviews');

SET FOREIGN_KEY_CHECKS = 1;
