/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title9

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 18:50:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for grades
-- ----------------------------
DROP TABLE IF EXISTS `grades`;
CREATE TABLE `grades`  (
  `user_id` int NOT NULL,
  `job` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grades
-- ----------------------------
INSERT INTO `grades` VALUES (1, 'C++', 172);
INSERT INTO `grades` VALUES (2, 'C++', 169);
INSERT INTO `grades` VALUES (3, 'Java', 185);
INSERT INTO `grades` VALUES (4, 'Java', 192);
INSERT INTO `grades` VALUES (5, 'Python', 146);
INSERT INTO `grades` VALUES (6, 'Python', 193);
INSERT INTO `grades` VALUES (7, 'Java', 149);
INSERT INTO `grades` VALUES (8, 'Java', 138);
INSERT INTO `grades` VALUES (9, 'AI', 179);
INSERT INTO `grades` VALUES (10, 'AI', 182);
INSERT INTO `grades` VALUES (11, 'AI', 183);
INSERT INTO `grades` VALUES (12, 'C++', 168);
INSERT INTO `grades` VALUES (13, 'AI', 190);
INSERT INTO `grades` VALUES (14, 'BigData', 153);
INSERT INTO `grades` VALUES (15, 'BigData', 148);
INSERT INTO `grades` VALUES (16, 'Java', 165);
INSERT INTO `grades` VALUES (17, 'Java', 179);
INSERT INTO `grades` VALUES (18, 'BigData', 185);
INSERT INTO `grades` VALUES (19, '前端', 172);
INSERT INTO `grades` VALUES (20, '前端', 170);
INSERT INTO `grades` VALUES (21, '前端', 158);
INSERT INTO `grades` VALUES (22, 'Java', 162);
INSERT INTO `grades` VALUES (23, '前端', 148);
INSERT INTO `grades` VALUES (24, 'Java', 196);
INSERT INTO `grades` VALUES (25, 'Java', 184);
INSERT INTO `grades` VALUES (26, 'C++', 174);
INSERT INTO `grades` VALUES (27, 'Python', 168);
INSERT INTO `grades` VALUES (28, 'Python', 182);
INSERT INTO `grades` VALUES (29, 'AI', 146);
INSERT INTO `grades` VALUES (30, 'AI', 162);
INSERT INTO `grades` VALUES (31, 'BigData', 175);

SET FOREIGN_KEY_CHECKS = 1;
