/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2020-04-11 22:14:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `url` varchar(255) default NULL,
  `desc` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'update', '/api/user/update', '更新用户');
INSERT INTO `permission` VALUES ('2', 'add', '/api/user/add', '添加用户');
INSERT INTO `permission` VALUES ('3', 'delete', '/api/user/delete', '删除用户');
INSERT INTO `permission` VALUES ('4', 'get', '/api/user/get', '查询用户');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `desc` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'root', '超级管理员');
INSERT INTO `role` VALUES ('2', 'admin', '管理员');
INSERT INTO `role` VALUES ('3', 'guest', '来宾');
INSERT INTO `role` VALUES ('4', 'user', '普通用户');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL auto_increment,
  `rid` int(11) default NULL,
  `pid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');
INSERT INTO `role_permission` VALUES ('3', '1', '3');
INSERT INTO `role_permission` VALUES ('4', '1', '4');
INSERT INTO `role_permission` VALUES ('5', '2', '1');
INSERT INTO `role_permission` VALUES ('6', '2', '2');
INSERT INTO `role_permission` VALUES ('7', '2', '4');
INSERT INTO `role_permission` VALUES ('9', '4', '4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(255) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `ctime` datetime default NULL,
  `salt` varchar(255) default NULL,
  `desc` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', 'root', '2020-04-11 20:07:49', null, '超级管理员用户');
INSERT INTO `user` VALUES ('2', 'admin', 'admin', '2020-04-11 20:07:52', null, '管理员用户');
INSERT INTO `user` VALUES ('3', 'zhaozilong', '123', '2020-04-11 20:07:55', null, '普通用户');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL auto_increment,
  `uid` int(11) default NULL,
  `rid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '3', '4');
