/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : studentsandcoursesscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 25/11/2020 15:32:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `c_id` int NOT NULL,
  `c_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `t_id` int NOT NULL,
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES (1, '语文', 2);
INSERT INTO `courses` VALUES (2, '数学', 1);
INSERT INTO `courses` VALUES (3, '英语', 3);

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `s_id` int NOT NULL,
  `c_id` int NOT NULL,
  `s_score` float NOT NULL,
  PRIMARY KEY (`s_id`, `c_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES (1, 1, 80);
INSERT INTO `scores` VALUES (1, 2, 90);
INSERT INTO `scores` VALUES (1, 3, 99);
INSERT INTO `scores` VALUES (2, 1, 70);
INSERT INTO `scores` VALUES (2, 2, 60);
INSERT INTO `scores` VALUES (2, 3, 80);
INSERT INTO `scores` VALUES (3, 1, 80);
INSERT INTO `scores` VALUES (3, 2, 80);
INSERT INTO `scores` VALUES (3, 3, 80);
INSERT INTO `scores` VALUES (4, 1, 50);
INSERT INTO `scores` VALUES (4, 2, 30);
INSERT INTO `scores` VALUES (4, 3, 20);
INSERT INTO `scores` VALUES (5, 1, 76);
INSERT INTO `scores` VALUES (5, 2, 87);
INSERT INTO `scores` VALUES (6, 1, 31);
INSERT INTO `scores` VALUES (6, 3, 34);
INSERT INTO `scores` VALUES (7, 2, 89);
INSERT INTO `scores` VALUES (7, 3, 98);

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `s_id` int NOT NULL,
  `s_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_birthday` date NOT NULL,
  `s_sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (1, '赵雷', '1990-01-01', '男');
INSERT INTO `students` VALUES (2, '钱电', '1990-12-21', '男');
INSERT INTO `students` VALUES (3, '孙风', '1990-05-20', '男');
INSERT INTO `students` VALUES (4, '李云', '1990-08-06', '男');
INSERT INTO `students` VALUES (5, '周梅', '1991-12-01', '女');
INSERT INTO `students` VALUES (6, '吴兰', '1992-03-01', '女');
INSERT INTO `students` VALUES (7, '郑竹', '1989-07-01', '女');
INSERT INTO `students` VALUES (8, '王菊', '1990-01-02', '女');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `t_id` int NOT NULL,
  `t_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES (1, '张三');
INSERT INTO `teachers` VALUES (2, '李四');
INSERT INTO `teachers` VALUES (3, '王五');

SET FOREIGN_KEY_CHECKS = 1;
