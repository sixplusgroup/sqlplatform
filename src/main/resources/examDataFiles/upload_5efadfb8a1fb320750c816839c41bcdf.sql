/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : bookborrowingscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 25/11/2020 16:20:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `book_id` int NOT NULL,
  `sort` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `writer` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `output` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` float(10, 3) NOT NULL,
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (112266, 'TP3/12', 'FoxBASE', '张三', '电子工业出版社', 23.600);
INSERT INTO `books` VALUES (113388, 'TR7/90', '大学英语', '胡玲', '清华大学出版社', 12.500);
INSERT INTO `books` VALUES (114455, 'TR9/12', '线性代数', '孙业', '北京大学出版社', 20.800);
INSERT INTO `books` VALUES (118801, 'TP4/15', '计算机网络', '黄力钧', '高等教育出版社', 21.800);
INSERT INTO `books` VALUES (118802, 'TP4/15', '计算机网络', '黄力钧', '高等教育出版社', 21.800);
INSERT INTO `books` VALUES (332211, 'TP5/10', '计算机基础', '李伟', '高等教育出版社', 18.000);
INSERT INTO `books` VALUES (445501, 'TP3/12', '数据库导论', '王强', '科学出版社', 17.900);
INSERT INTO `books` VALUES (445502, 'TP3/12', '数据库导论', '王强', '科学出版社', 17.900);
INSERT INTO `books` VALUES (445503, 'TP3/12', '数据库导论', '王强', '科学出版社', 17.900);
INSERT INTO `books` VALUES (446601, 'TP4/13', '数据库基础', '马凌云', '人民邮电出版社', 22.500);
INSERT INTO `books` VALUES (446602, 'TP4/13', '数据库基础', '马凌云', '人民邮电出版社', 22.500);
INSERT INTO `books` VALUES (446603, 'TP4/13', '数据库基础', '马凌云', '人民邮电出版社', 22.500);
INSERT INTO `books` VALUES (449901, 'TP4/14', 'FoxPro大全', '周虹', '科学出版社', 32.700);
INSERT INTO `books` VALUES (449902, 'TP4/14', 'FoxPro大全', '周虹', '科学出版社', 32.700);
INSERT INTO `books` VALUES (665544, 'TS7/21', '高等数学', '刘明', '高等教育出版社', 20.000);

-- ----------------------------
-- Table structure for borrows
-- ----------------------------
DROP TABLE IF EXISTS `borrows`;
CREATE TABLE `borrows`  (
  `reader_id` int NOT NULL,
  `book_id` int NOT NULL,
  `borrow_date` date NOT NULL,
  PRIMARY KEY (`reader_id`, `book_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrows
-- ----------------------------
INSERT INTO `borrows` VALUES (111, 445503, '2006-08-21');
INSERT INTO `borrows` VALUES (112, 112266, '2006-03-14');
INSERT INTO `borrows` VALUES (112, 445501, '2006-03-19');
INSERT INTO `borrows` VALUES (112, 449901, '2006-10-23');
INSERT INTO `borrows` VALUES (114, 665544, '2006-10-21');
INSERT INTO `borrows` VALUES (115, 449902, '2006-08-21');
INSERT INTO `borrows` VALUES (118, 118801, '2006-09-10');
INSERT INTO `borrows` VALUES (119, 446603, '2006-11-12');
INSERT INTO `borrows` VALUES (120, 114455, '2006-11-02');
INSERT INTO `borrows` VALUES (120, 118801, '2006-10-18');
INSERT INTO `borrows` VALUES (125, 332211, '2006-02-12');

-- ----------------------------
-- Table structure for readers
-- ----------------------------
DROP TABLE IF EXISTS `readers`;
CREATE TABLE `readers`  (
  `reader_id` int NULL DEFAULT NULL,
  `company` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grade` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of readers
-- ----------------------------
INSERT INTO `readers` VALUES (111, '信息系', '王维利', '女', '教授', '1号楼424');
INSERT INTO `readers` VALUES (112, '财会系', '李  立', '男', '副教授', '2号楼316');
INSERT INTO `readers` VALUES (113, '经济系', '张  三', '男', '讲师', '3号楼105');
INSERT INTO `readers` VALUES (114, '信息系', '周华发', '男', '讲师', '1号楼316');
INSERT INTO `readers` VALUES (115, '信息系', '赵正义', '男', '工程师', '1号楼224');
INSERT INTO `readers` VALUES (116, '信息系', '李  明', '男', '副教授', '1号楼318');
INSERT INTO `readers` VALUES (117, '计算机系', '李小峰', '男', '助教', '1号楼214');
INSERT INTO `readers` VALUES (118, '计算机系', '许鹏飞', '男', '助工', '1号楼216');
INSERT INTO `readers` VALUES (119, '计算机系', '刘大龙', '男', '教授', '1号楼318');
INSERT INTO `readers` VALUES (120, '国际贸易', '李  雪', '男', '副教授', '4号楼506');
INSERT INTO `readers` VALUES (121, '国际贸易', '李  爽', '女', '讲师', '4号楼510');
INSERT INTO `readers` VALUES (122, '国际贸易', '王  纯', '女', '讲师', '4号楼512');
INSERT INTO `readers` VALUES (123, '财会系', '沈小霞', '女', '助教', '2号楼202');
INSERT INTO `readers` VALUES (124, '财会系', '朱  海', '男', '讲师', '2号楼210');
INSERT INTO `readers` VALUES (125, '财会系', '马英明', '男', '副教授', '2号楼212');

SET FOREIGN_KEY_CHECKS = 1;
