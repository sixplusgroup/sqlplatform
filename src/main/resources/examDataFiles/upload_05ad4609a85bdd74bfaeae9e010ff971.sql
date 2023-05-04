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

 Date: 25/11/2020 20:12:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for boats
-- ----------------------------
DROP TABLE IF EXISTS `boats`;
CREATE TABLE `boats`  (
  `bid` int NOT NULL,
  `bname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `color` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of boats
-- ----------------------------
INSERT INTO `boats` VALUES (1, 'Aegir', 'GREEN');
INSERT INTO `boats` VALUES (2, 'Balder', 'RED');
INSERT INTO `boats` VALUES (3, 'Hell', 'GREEN');
INSERT INTO `boats` VALUES (4, 'Idun', 'RED');
INSERT INTO `boats` VALUES (5, 'Magni', 'YELLOW');
INSERT INTO `boats` VALUES (6, 'Agni', 'GREEN');
INSERT INTO `boats` VALUES (7, 'Luna', 'YELLOW');
INSERT INTO `boats` VALUES (8, 'Sun', 'RED');

-- ----------------------------
-- Table structure for reserves
-- ----------------------------
DROP TABLE IF EXISTS `reserves`;
CREATE TABLE `reserves`  (
  `sid` int NOT NULL,
  `bid` int NOT NULL,
  `reserve_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`sid`, `bid`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  CONSTRAINT `bid` FOREIGN KEY (`bid`) REFERENCES `boats` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sid` FOREIGN KEY (`sid`) REFERENCES `sailors` (`sid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reserves
-- ----------------------------
INSERT INTO `reserves` VALUES (1, 1, '2020-08-30');
INSERT INTO `reserves` VALUES (1, 2, '2020-10-30');
INSERT INTO `reserves` VALUES (1, 4, '2020-11-05');
INSERT INTO `reserves` VALUES (1, 6, '2020-09-26');
INSERT INTO `reserves` VALUES (1, 8, '2020-08-22');
INSERT INTO `reserves` VALUES (2, 1, '2020-08-06');
INSERT INTO `reserves` VALUES (2, 2, '2020-05-26');
INSERT INTO `reserves` VALUES (2, 3, '2020-11-01');
INSERT INTO `reserves` VALUES (2, 4, '2020-08-23');
INSERT INTO `reserves` VALUES (2, 5, '2020-09-15');
INSERT INTO `reserves` VALUES (2, 6, '2020-08-14');
INSERT INTO `reserves` VALUES (2, 7, '2020-08-19');
INSERT INTO `reserves` VALUES (2, 8, '2020-04-22');
INSERT INTO `reserves` VALUES (3, 2, '2020-07-02');
INSERT INTO `reserves` VALUES (3, 5, '2020-08-22');
INSERT INTO `reserves` VALUES (3, 6, '2020-07-15');
INSERT INTO `reserves` VALUES (3, 7, '2020-08-07');
INSERT INTO `reserves` VALUES (3, 8, '2020-08-25');
INSERT INTO `reserves` VALUES (4, 1, '2020-05-30');
INSERT INTO `reserves` VALUES (4, 2, '2020-09-27');
INSERT INTO `reserves` VALUES (4, 4, '2020-11-27');
INSERT INTO `reserves` VALUES (4, 5, '2020-10-23');
INSERT INTO `reserves` VALUES (4, 6, '2020-05-09');
INSERT INTO `reserves` VALUES (5, 1, '2020-11-01');
INSERT INTO `reserves` VALUES (5, 2, '2020-10-26');
INSERT INTO `reserves` VALUES (5, 4, '2020-10-29');
INSERT INTO `reserves` VALUES (5, 6, '2020-05-13');
INSERT INTO `reserves` VALUES (6, 1, '2020-05-18');
INSERT INTO `reserves` VALUES (6, 2, '2020-08-06');
INSERT INTO `reserves` VALUES (6, 3, '2020-09-28');
INSERT INTO `reserves` VALUES (6, 4, '2020-09-18');
INSERT INTO `reserves` VALUES (6, 5, '2020-09-17');
INSERT INTO `reserves` VALUES (6, 6, '2020-07-21');
INSERT INTO `reserves` VALUES (6, 7, '2020-01-29');
INSERT INTO `reserves` VALUES (6, 8, '2020-10-15');
INSERT INTO `reserves` VALUES (7, 1, '2020-09-10');
INSERT INTO `reserves` VALUES (7, 2, '2020-08-28');
INSERT INTO `reserves` VALUES (7, 3, '2020-08-25');
INSERT INTO `reserves` VALUES (7, 4, '2020-10-11');
INSERT INTO `reserves` VALUES (7, 5, '2020-08-27');
INSERT INTO `reserves` VALUES (7, 6, '2020-07-14');
INSERT INTO `reserves` VALUES (7, 7, '2020-05-18');
INSERT INTO `reserves` VALUES (7, 8, '2020-08-19');
INSERT INTO `reserves` VALUES (8, 1, '2020-05-25');
INSERT INTO `reserves` VALUES (8, 2, '2020-11-07');
INSERT INTO `reserves` VALUES (8, 3, '2020-03-18');
INSERT INTO `reserves` VALUES (8, 4, '2020-08-14');
INSERT INTO `reserves` VALUES (9, 2, '2020-09-24');
INSERT INTO `reserves` VALUES (9, 3, '2020-10-31');
INSERT INTO `reserves` VALUES (9, 5, '2020-09-24');
INSERT INTO `reserves` VALUES (9, 6, '2020-09-25');
INSERT INTO `reserves` VALUES (10, 1, '2020-10-26');
INSERT INTO `reserves` VALUES (10, 2, '2020-08-13');
INSERT INTO `reserves` VALUES (10, 4, '2020-11-03');
INSERT INTO `reserves` VALUES (10, 7, '2020-08-18');

-- ----------------------------
-- Table structure for sailors
-- ----------------------------
DROP TABLE IF EXISTS `sailors`;
CREATE TABLE `sailors`  (
  `sid` int NOT NULL,
  `sname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rating` int NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sailors
-- ----------------------------
INSERT INTO `sailors` VALUES (1, 'Beth', 5, 29);
INSERT INTO `sailors` VALUES (2, 'Rica', 9, 42);
INSERT INTO `sailors` VALUES (3, 'Jerry', 8, 36);
INSERT INTO `sailors` VALUES (4, 'Smith', 7, 34);
INSERT INTO `sailors` VALUES (5, 'Bob', 3, 26);
INSERT INTO `sailors` VALUES (6, 'Alice', 7, 37);
INSERT INTO `sailors` VALUES (7, 'Kitty', 10, 53);
INSERT INTO `sailors` VALUES (8, 'Neil', 8, 48);
INSERT INTO `sailors` VALUES (9, 'Levis', 7, 45);
INSERT INTO `sailors` VALUES (10, 'Potty', 9, 42);

SET FOREIGN_KEY_CHECKS = 1;
