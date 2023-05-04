/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title7

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 16:42:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for views
-- ----------------------------
DROP TABLE IF EXISTS `views`;
CREATE TABLE `views`  (
  `article_id` int NOT NULL,
  `author_id` int NOT NULL,
  `viewer_id` int NOT NULL,
  `view_date` date NOT NULL,
  PRIMARY KEY (`article_id`, `author_id`, `viewer_id`, `view_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of views
-- ----------------------------
INSERT INTO `views` VALUES (1, 3, 3, '2020-08-12');
INSERT INTO `views` VALUES (1, 3, 4, '2019-02-02');
INSERT INTO `views` VALUES (1, 3, 5, '2019-03-12');
INSERT INTO `views` VALUES (1, 3, 6, '2019-10-03');
INSERT INTO `views` VALUES (2, 4, 1, '2020-02-28');
INSERT INTO `views` VALUES (2, 4, 5, '2020-02-12');
INSERT INTO `views` VALUES (2, 4, 6, '2019-10-03');
INSERT INTO `views` VALUES (2, 4, 7, '2020-08-23');
INSERT INTO `views` VALUES (3, 1, 1, '2020-02-28');
INSERT INTO `views` VALUES (3, 1, 5, '2019-08-04');
INSERT INTO `views` VALUES (3, 1, 6, '2019-12-25');
INSERT INTO `views` VALUES (3, 1, 8, '2019-08-31');
INSERT INTO `views` VALUES (4, 7, 2, '2020-06-29');
INSERT INTO `views` VALUES (4, 7, 6, '2020-03-19');
INSERT INTO `views` VALUES (5, 2, 1, '2019-10-01');
INSERT INTO `views` VALUES (5, 2, 4, '2020-01-26');
INSERT INTO `views` VALUES (5, 2, 6, '2019-12-25');
INSERT INTO `views` VALUES (5, 2, 7, '2019-12-21');
INSERT INTO `views` VALUES (6, 3, 2, '2020-01-02');
INSERT INTO `views` VALUES (6, 3, 6, '2020-03-14');
INSERT INTO `views` VALUES (6, 3, 7, '2019-12-26');
INSERT INTO `views` VALUES (7, 7, 3, '2018-08-25');
INSERT INTO `views` VALUES (7, 7, 4, '2019-02-02');
INSERT INTO `views` VALUES (7, 7, 8, '2019-09-12');

SET FOREIGN_KEY_CHECKS = 1;
