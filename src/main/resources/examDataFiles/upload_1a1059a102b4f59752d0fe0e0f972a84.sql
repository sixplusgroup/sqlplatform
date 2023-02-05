/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title8

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 16:52:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for logins
-- ----------------------------
DROP TABLE IF EXISTS `logins`;
CREATE TABLE `logins`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL,
  `client_id` int NULL DEFAULT NULL,
  `login_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logins
-- ----------------------------
INSERT INTO `logins` VALUES (1, 1, 3, '2020-07-02');
INSERT INTO `logins` VALUES (2, 1, 4, '2020-07-03');
INSERT INTO `logins` VALUES (3, 1, 3, '2020-07-05');
INSERT INTO `logins` VALUES (4, 2, 1, '2020-07-09');
INSERT INTO `logins` VALUES (5, 3, 5, '2020-07-11');
INSERT INTO `logins` VALUES (6, 1, 3, '2020-07-11');
INSERT INTO `logins` VALUES (7, 3, 2, '2020-07-12');
INSERT INTO `logins` VALUES (8, 5, 3, '2020-07-15');
INSERT INTO `logins` VALUES (9, 6, 4, '2020-07-15');
INSERT INTO `logins` VALUES (10, 5, 1, '2020-07-17');
INSERT INTO `logins` VALUES (11, 4, 2, '2020-07-21');
INSERT INTO `logins` VALUES (12, 1, 3, '2020-07-23');
INSERT INTO `logins` VALUES (13, 4, 3, '2020-07-25');
INSERT INTO `logins` VALUES (14, 3, 6, '2020-08-01');
INSERT INTO `logins` VALUES (15, 7, 3, '2020-08-03');
INSERT INTO `logins` VALUES (16, 7, 3, '2020-08-04');
INSERT INTO `logins` VALUES (17, 6, 4, '2020-08-10');
INSERT INTO `logins` VALUES (18, 5, 6, '2020-08-13');
INSERT INTO `logins` VALUES (19, 8, 4, '2020-08-15');
INSERT INTO `logins` VALUES (20, 9, 2, '2020-08-15');
INSERT INTO `logins` VALUES (21, 10, 5, '2020-08-15');
INSERT INTO `logins` VALUES (22, 8, 3, '2020-08-16');
INSERT INTO `logins` VALUES (23, 1, 6, '2020-08-24');

SET FOREIGN_KEY_CHECKS = 1;
