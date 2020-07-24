/*
Navicat MySQL Data Transfer

Source Server         : mysql-zhanglitao
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-07-24 16:53:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', 'user:*:*', null);
INSERT INTO `t_permission` VALUES ('2', 'product:*:01', null);
INSERT INTO `t_permission` VALUES ('3', 'order:*:*', null);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin');
INSERT INTO `t_role` VALUES ('2', 'user');
INSERT INTO `t_role` VALUES ('3', 'product');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `roleId` int(6) DEFAULT NULL,
  `permissionId` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1');
INSERT INTO `t_role_permission` VALUES ('2', '1', '2');
INSERT INTO `t_role_permission` VALUES ('3', '2', '1');
INSERT INTO `t_role_permission` VALUES ('4', '3', '2');
INSERT INTO `t_role_permission` VALUES ('5', '1', '3');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'taoshen', '055aefb2e81d9c43596d065ed0e7a6a7', 'XO$J2#W3');
INSERT INTO `t_user` VALUES ('2', 'zhuzhu', '8d8397f099aabe858b08bfe3472a9721', 'RQB@#9&U');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `userId` int(6) DEFAULT NULL,
  `roleId` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('2', '2', '2');
INSERT INTO `t_user_role` VALUES ('3', '2', '3');
