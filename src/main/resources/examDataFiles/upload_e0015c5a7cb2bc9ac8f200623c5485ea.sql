/*
 Navicat Premium Data Transfer

 Source Server         : j2eedb
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : title10

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 19:10:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `department_id` int NOT NULL,
  `department_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES (1, 'Administrative');
INSERT INTO `departments` VALUES (2, 'Market');
INSERT INTO `departments` VALUES (3, 'Technology');
INSERT INTO `departments` VALUES (4, 'Finance');
INSERT INTO `departments` VALUES (5, 'Personnel');
INSERT INTO `departments` VALUES (6, 'Sales');

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `id` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salary` int NULL DEFAULT NULL,
  `department_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `department_id`(`department_id`) USING BTREE,
  CONSTRAINT `department_id` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (1, '阿青', 14500, 1);
INSERT INTO `employees` VALUES (2, '狄云', 24300, 3);
INSERT INTO `employees` VALUES (3, '万圭', 16490, 2);
INSERT INTO `employees` VALUES (4, '霜华', 14270, 4);
INSERT INTO `employees` VALUES (5, '破天', 16890, 6);
INSERT INTO `employees` VALUES (6, '灵素', 15390, 5);
INSERT INTO `employees` VALUES (7, '紫衣', 22830, 2);
INSERT INTO `employees` VALUES (8, '薛鹊', 15600, 1);
INSERT INTO `employees` VALUES (9, '宗雄', 18200, 1);
INSERT INTO `employees` VALUES (10, '家洛', 17580, 6);
INSERT INTO `employees` VALUES (11, '青桐', 12380, 4);
INSERT INTO `employees` VALUES (12, '骆冰', 25300, 3);
INSERT INTO `employees` VALUES (13, '仲英', 21840, 6);
INSERT INTO `employees` VALUES (14, '金标', 16290, 2);
INSERT INTO `employees` VALUES (15, '段誉', 18450, 3);
INSERT INTO `employees` VALUES (16, '虚竹', 15490, 4);
INSERT INTO `employees` VALUES (17, '坦之', 17430, 1);
INSERT INTO `employees` VALUES (18, '婉清', 13840, 5);
INSERT INTO `employees` VALUES (19, '语嫣', 12540, 4);
INSERT INTO `employees` VALUES (20, '星竹', 12480, 2);
INSERT INTO `employees` VALUES (21, '百泉', 24380, 6);
INSERT INTO `employees` VALUES (22, '空玄', 21740, 1);
INSERT INTO `employees` VALUES (23, '洪烈', 23910, 2);
INSERT INTO `employees` VALUES (24, '惜弱', 17340, 5);
INSERT INTO `employees` VALUES (25, '念慈', 13840, 1);
INSERT INTO `employees` VALUES (26, '铁心', 17930, 3);
INSERT INTO `employees` VALUES (27, '冠英', 16490, 6);
INSERT INTO `employees` VALUES (28, '冷谦', 13280, 4);
INSERT INTO `employees` VALUES (29, '松溪', 19880, 2);
INSERT INTO `employees` VALUES (30, '翠山', 16430, 4);
INSERT INTO `employees` VALUES (31, '远桥', 21840, 3);

SET FOREIGN_KEY_CHECKS = 1;
