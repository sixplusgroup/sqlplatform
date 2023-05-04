/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : championshipscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 30/12/2020 23:56:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for matches
-- ----------------------------
DROP TABLE IF EXISTS `matches`;
CREATE TABLE `matches`  (
  `match_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `first_player` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `second_player` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_score` int NOT NULL,
  `second_score` int NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of matches
-- ----------------------------
INSERT INTO `matches` VALUES ('1', '15', '45', 3, 0);
INSERT INTO `matches` VALUES ('2', '30', '25', 1, 2);
INSERT INTO `matches` VALUES ('3', '30', '15', 2, 0);
INSERT INTO `matches` VALUES ('4', '40', '20', 5, 2);
INSERT INTO `matches` VALUES ('5', '35', '50', 1, 1);

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players`  (
  `player_id` int NOT NULL,
  `player_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of players
-- ----------------------------
INSERT INTO `players` VALUES (10, 'Slaman', 2);
INSERT INTO `players` VALUES (15, 'Aron', 1);
INSERT INTO `players` VALUES (20, 'Priya', 3);
INSERT INTO `players` VALUES (25, 'Alice', 1);
INSERT INTO `players` VALUES (30, 'Bajrang', 1);
INSERT INTO `players` VALUES (35, 'Joe', 2);
INSERT INTO `players` VALUES (40, 'Priyanka', 3);
INSERT INTO `players` VALUES (45, 'Khali', 1);
INSERT INTO `players` VALUES (50, 'Jose', 2);

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `player_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `day` date NOT NULL,
  `score_points` int NOT NULL,
  PRIMARY KEY (`gender`, `day`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES ('Priyanka', 'F', '2019-12-30', 17);
INSERT INTO `scores` VALUES ('Priya', 'F', '2019-12-31', 23);
INSERT INTO `scores` VALUES ('Aron', 'F', '2020-01-01', 17);
INSERT INTO `scores` VALUES ('Alice', 'F', '2020-01-07', 23);
INSERT INTO `scores` VALUES ('Khali', 'M', '2019-12-25', 11);
INSERT INTO `scores` VALUES ('Jose', 'M', '2019-12-28', 2);
INSERT INTO `scores` VALUES ('Slaman', 'M', '2019-12-30', 13);
INSERT INTO `scores` VALUES ('Joe', 'M', '2019-12-31', 3);
INSERT INTO `scores` VALUES ('Bajrang', 'M', '2020-01-07', 7);

SET FOREIGN_KEY_CHECKS = 1;
