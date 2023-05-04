/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title4

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 15:21:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for matches
-- ----------------------------
DROP TABLE IF EXISTS `matches`;
CREATE TABLE `matches`  (
  `match_id` int NOT NULL,
  `first_player` int NULL DEFAULT NULL,
  `second_player` int NULL DEFAULT NULL,
  `first_score` int NULL DEFAULT NULL,
  `second_score` int NULL DEFAULT NULL,
  `group_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`match_id`) USING BTREE,
  INDEX `first_player`(`first_player`) USING BTREE,
  INDEX `second_player`(`second_player`) USING BTREE,
  CONSTRAINT `first_player` FOREIGN KEY (`first_player`) REFERENCES `players` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `second_player` FOREIGN KEY (`second_player`) REFERENCES `players` (`player_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matches
-- ----------------------------
INSERT INTO `matches` VALUES (1, 5, 40, 2, 1, 1);
INSERT INTO `matches` VALUES (2, 10, 55, 3, 4, 1);
INSERT INTO `matches` VALUES (3, 35, 5, 0, 2, 1);
INSERT INTO `matches` VALUES (6, 15, 30, 2, 1, 2);
INSERT INTO `matches` VALUES (7, 15, 60, 3, 2, 2);
INSERT INTO `matches` VALUES (8, 20, 60, 1, 4, 2);
INSERT INTO `matches` VALUES (9, 30, 60, 3, 0, 2);
INSERT INTO `matches` VALUES (10, 25, 50, 6, 4, 3);
INSERT INTO `matches` VALUES (11, 50, 70, 2, 1, 3);
INSERT INTO `matches` VALUES (12, 25, 70, 3, 3, 3);
INSERT INTO `matches` VALUES (13, 45, 65, 1, 2, 4);
INSERT INTO `matches` VALUES (14, 5, 10, 1, 1, 1);
INSERT INTO `matches` VALUES (15, 10, 35, 3, 2, 1);
INSERT INTO `matches` VALUES (16, 35, 40, 1, 2, 1);
INSERT INTO `matches` VALUES (17, 55, 35, 3, 2, 1);
INSERT INTO `matches` VALUES (18, 55, 40, 2, 2, 1);
INSERT INTO `matches` VALUES (19, 15, 20, 3, 1, 2);
INSERT INTO `matches` VALUES (20, 20, 30, 4, 1, 2);

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players`  (
  `player_id` int NOT NULL,
  `group_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of players
-- ----------------------------
INSERT INTO `players` VALUES (5, 1);
INSERT INTO `players` VALUES (10, 1);
INSERT INTO `players` VALUES (15, 2);
INSERT INTO `players` VALUES (20, 2);
INSERT INTO `players` VALUES (25, 3);
INSERT INTO `players` VALUES (30, 2);
INSERT INTO `players` VALUES (35, 1);
INSERT INTO `players` VALUES (40, 1);
INSERT INTO `players` VALUES (45, 4);
INSERT INTO `players` VALUES (50, 3);
INSERT INTO `players` VALUES (55, 1);
INSERT INTO `players` VALUES (60, 2);
INSERT INTO `players` VALUES (65, 4);
INSERT INTO `players` VALUES (70, 3);

SET FOREIGN_KEY_CHECKS = 1;
