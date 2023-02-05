/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title6

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 16:28:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acceptedrequests
-- ----------------------------
DROP TABLE IF EXISTS `acceptedrequests`;
CREATE TABLE `acceptedrequests`  (
  `requester_id` int NOT NULL,
  `accepter_id` int NOT NULL,
  `accept_date` date NOT NULL,
  PRIMARY KEY (`requester_id`, `accepter_id`, `accept_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acceptedrequests
-- ----------------------------
INSERT INTO `acceptedrequests` VALUES (1, 4, '2020-03-08');
INSERT INTO `acceptedrequests` VALUES (1, 7, '2020-04-25');
INSERT INTO `acceptedrequests` VALUES (2, 1, '2020-10-20');
INSERT INTO `acceptedrequests` VALUES (2, 8, '2020-08-29');
INSERT INTO `acceptedrequests` VALUES (4, 3, '2020-08-21');
INSERT INTO `acceptedrequests` VALUES (4, 6, '2020-09-20');
INSERT INTO `acceptedrequests` VALUES (4, 9, '2020-06-30');
INSERT INTO `acceptedrequests` VALUES (6, 8, '2019-03-20');
INSERT INTO `acceptedrequests` VALUES (7, 2, '2020-06-12');
INSERT INTO `acceptedrequests` VALUES (7, 4, '2019-11-21');
INSERT INTO `acceptedrequests` VALUES (8, 1, '2020-03-11');
INSERT INTO `acceptedrequests` VALUES (8, 4, '2019-11-25');
INSERT INTO `acceptedrequests` VALUES (8, 5, '2019-12-31');
INSERT INTO `acceptedrequests` VALUES (9, 2, '2020-01-05');

-- ----------------------------
-- Table structure for friendrequests
-- ----------------------------
DROP TABLE IF EXISTS `friendrequests`;
CREATE TABLE `friendrequests`  (
  `sender_id` int NOT NULL,
  `send_to_id` int NOT NULL,
  `request_date` date NOT NULL,
  PRIMARY KEY (`sender_id`, `send_to_id`, `request_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friendrequests
-- ----------------------------
INSERT INTO `friendrequests` VALUES (1, 4, '2020-03-04');
INSERT INTO `friendrequests` VALUES (1, 7, '2020-04-23');
INSERT INTO `friendrequests` VALUES (2, 1, '2020-10-16');
INSERT INTO `friendrequests` VALUES (2, 5, '2020-05-13');
INSERT INTO `friendrequests` VALUES (2, 5, '2020-05-14');
INSERT INTO `friendrequests` VALUES (2, 8, '2020-08-28');
INSERT INTO `friendrequests` VALUES (3, 1, '2020-02-23');
INSERT INTO `friendrequests` VALUES (3, 5, '2020-06-14');
INSERT INTO `friendrequests` VALUES (3, 6, '2020-02-29');
INSERT INTO `friendrequests` VALUES (4, 3, '2020-08-08');
INSERT INTO `friendrequests` VALUES (4, 5, '2020-10-20');
INSERT INTO `friendrequests` VALUES (4, 6, '2020-09-19');
INSERT INTO `friendrequests` VALUES (4, 9, '2020-06-17');
INSERT INTO `friendrequests` VALUES (5, 9, '2020-05-19');
INSERT INTO `friendrequests` VALUES (6, 8, '2019-03-19');
INSERT INTO `friendrequests` VALUES (7, 2, '2020-06-11');
INSERT INTO `friendrequests` VALUES (7, 4, '2019-11-20');
INSERT INTO `friendrequests` VALUES (8, 1, '2020-03-02');
INSERT INTO `friendrequests` VALUES (8, 4, '2019-11-23');
INSERT INTO `friendrequests` VALUES (8, 5, '2019-11-30');
INSERT INTO `friendrequests` VALUES (9, 2, '2020-01-03');
INSERT INTO `friendrequests` VALUES (9, 6, '2020-03-27');

SET FOREIGN_KEY_CHECKS = 1;
