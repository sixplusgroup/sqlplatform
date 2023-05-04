/*
 Navicat Premium Data Transfer

 Source Server         : Milk
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : movierelatedscenario

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 24/11/2020 23:38:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for movie_rating
-- ----------------------------
DROP TABLE IF EXISTS `movie_rating`;
CREATE TABLE `movie_rating`  (
  `movie_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`movie_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie_rating
-- ----------------------------
INSERT INTO `movie_rating` VALUES (1, 1, 3, '2020-01-12');
INSERT INTO `movie_rating` VALUES (1, 2, 4, '2020-02-11');
INSERT INTO `movie_rating` VALUES (1, 3, 2, '2020-02-12');
INSERT INTO `movie_rating` VALUES (1, 4, 1, '2020-01-01');
INSERT INTO `movie_rating` VALUES (2, 1, 5, '2020-02-17');
INSERT INTO `movie_rating` VALUES (2, 2, 2, '2020-02-01');
INSERT INTO `movie_rating` VALUES (2, 3, 2, '2020-03-01');
INSERT INTO `movie_rating` VALUES (3, 1, 3, '2020-02-22');
INSERT INTO `movie_rating` VALUES (3, 2, 4, '2020-02-25');

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies`  (
  `movie_id` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`movie_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movies
-- ----------------------------
INSERT INTO `movies` VALUES (1, 'Avengers');
INSERT INTO `movies` VALUES (2, 'Frozen 2');
INSERT INTO `movies` VALUES (3, 'Joker');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'Daniel');
INSERT INTO `users` VALUES (2, 'Monica');
INSERT INTO `users` VALUES (3, 'Maria');
INSERT INTO `users` VALUES (4, 'James');

SET FOREIGN_KEY_CHECKS = 1;
