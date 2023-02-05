/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title2

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 13:10:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for boat
-- ----------------------------
DROP TABLE IF EXISTS `boat`;
CREATE TABLE `boat`  (
  `bid` int NOT NULL,
  `bname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `color` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of boat
-- ----------------------------
INSERT INTO `boat` VALUES (1, 'Aegir', 'red');
INSERT INTO `boat` VALUES (2, 'Balder', 'red');
INSERT INTO `boat` VALUES (3, 'Hell', 'green');
INSERT INTO `boat` VALUES (4, 'Idun', 'red');
INSERT INTO `boat` VALUES (5, 'Magni', 'green');

-- ----------------------------
-- Table structure for reserve
-- ----------------------------
DROP TABLE IF EXISTS `reserve`;
CREATE TABLE `reserve`  (
  `sid` int NOT NULL,
  `bid` int NOT NULL,
  `day` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`sid`, `bid`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  CONSTRAINT `bid` FOREIGN KEY (`bid`) REFERENCES `boat` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sid` FOREIGN KEY (`sid`) REFERENCES `sailor` (`sid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reserve
-- ----------------------------
INSERT INTO `reserve` VALUES (1, 1, '2020-10-27 19:45:00.000000');
INSERT INTO `reserve` VALUES (1, 2, '2020-10-30 19:37:44.000000');
INSERT INTO `reserve` VALUES (2, 3, '2020-11-01 19:38:00.000000');
INSERT INTO `reserve` VALUES (2, 4, '2020-10-23 19:39:05.000000');
INSERT INTO `reserve` VALUES (3, 2, '2020-07-02 19:38:14.000000');
INSERT INTO `reserve` VALUES (3, 5, '2020-08-22 19:38:46.000000');
INSERT INTO `reserve` VALUES (4, 2, '2020-09-27 19:43:01.000000');
INSERT INTO `reserve` VALUES (4, 4, '2020-11-27 19:42:29.000000');
INSERT INTO `reserve` VALUES (4, 5, '2020-10-23 19:43:43.000000');
INSERT INTO `reserve` VALUES (5, 1, '2020-11-01 19:44:13.000000');
INSERT INTO `reserve` VALUES (5, 2, '2020-10-26 19:44:33.000000');
INSERT INTO `reserve` VALUES (5, 4, '2020-10-29 19:44:44.000000');
INSERT INTO `reserve` VALUES (6, 3, '2020-09-28 19:45:25.000000');
INSERT INTO `reserve` VALUES (6, 4, '2020-10-18 19:45:34.000000');
INSERT INTO `reserve` VALUES (7, 1, '2020-11-10 19:39:39.000000');
INSERT INTO `reserve` VALUES (7, 2, '2020-10-28 19:41:44.000000');
INSERT INTO `reserve` VALUES (7, 3, '2020-07-25 19:42:16.000000');
INSERT INTO `reserve` VALUES (7, 4, '2020-10-11 19:41:55.000000');
INSERT INTO `reserve` VALUES (7, 5, '2020-08-27 19:42:04.000000');
INSERT INTO `reserve` VALUES (8, 2, '2020-11-07 19:46:25.000000');
INSERT INTO `reserve` VALUES (9, 2, '2020-06-24 19:46:53.000000');
INSERT INTO `reserve` VALUES (9, 3, '2020-10-31 19:46:41.000000');
INSERT INTO `reserve` VALUES (9, 5, '2020-09-24 19:47:14.000000');
INSERT INTO `reserve` VALUES (10, 1, '2020-10-26 19:47:25.000000');

-- ----------------------------
-- Table structure for sailor
-- ----------------------------
DROP TABLE IF EXISTS `sailor`;
CREATE TABLE `sailor`  (
  `sid` int NOT NULL,
  `sname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rating` int NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sailor
-- ----------------------------
INSERT INTO `sailor` VALUES (1, 'Beth', 5, 29);
INSERT INTO `sailor` VALUES (2, 'Rica', 9, 42);
INSERT INTO `sailor` VALUES (3, 'Jerry', 8, 36);
INSERT INTO `sailor` VALUES (4, 'Smith', 7, 34);
INSERT INTO `sailor` VALUES (5, 'Bob', 3, 26);
INSERT INTO `sailor` VALUES (6, 'Alice', 7, 37);
INSERT INTO `sailor` VALUES (7, 'Kitty', 10, 53);
INSERT INTO `sailor` VALUES (8, 'Neil', 8, 48);
INSERT INTO `sailor` VALUES (9, 'Levis', 7, 45);
INSERT INTO `sailor` VALUES (10, 'Potty', 9, 42);

SET FOREIGN_KEY_CHECKS = 1;
