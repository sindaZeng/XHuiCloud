/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : sys

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 19/03/2020 23:28:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_client_details`;
CREATE TABLE `sys_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_client_details
-- ----------------------------
BEGIN;
INSERT INTO `sys_client_details` VALUES ('U2hhcmVCQlM=', NULL, '$2a$10$xP0Iv4Pd4kn.7msrJOCiC.ttRprLRk2fo3xbCdmPPLYuDo/H4gcdq', 'all', 'password,authorization_code,refresh_token', 'https://www.baidu.com', NULL, 3600, NULL, '{\"home\":\"https://www.baidu.com\",\"name\":\"百度\"}', 'false');
INSERT INTO `sys_client_details` VALUES ('web', '', '$2a$10$xP0Iv4Pd4kn.7msrJOCiC.ttRprLRk2fo3xbCdmPPLYuDo/H4gcdq', 'all', 'password,authorization_code,refresh_token', 'https://www.baidu.com', NULL, 3600, NULL, '{\"home\":\"https://www.baidu.com\",\"name\":\"百度\"}', 'false');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `request_ip` varchar(50) DEFAULT '' COMMENT '操作IP',
  `type` char(1) DEFAULT '0' COMMENT '日志类型\n#LogType{0:操作类型;1:异常类型}',
  `user_name` varchar(50) DEFAULT '' COMMENT '操作人',
  `description` varchar(255) DEFAULT '' COMMENT '操作描述',
  `class_path` varchar(255) DEFAULT '' COMMENT '类路径',
  `request_method` varchar(50) DEFAULT '' COMMENT '请求方法',
  `request_uri` varchar(50) DEFAULT '' COMMENT '请求地址',
  `http_method` varchar(10) DEFAULT 'GET' COMMENT '请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
  `params` longtext COMMENT '请求参数',
  `result` longtext COMMENT '返回值',
  `ex_desc` longtext COMMENT '异常详情信息',
  `ex_detail` longtext COMMENT '异常描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '结束时间',
  `time` mediumtext CHARACTER SET utf8 COMMENT '执行时间',
  `user_agent` varchar(1000) DEFAULT '' COMMENT '浏览器',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_log_create_by` (`user_name`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (176, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 01:41:29', '2020-03-18 01:41:29', '13', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (177, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 01:41:50', '2020-03-18 01:41:50', '5', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (178, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 01:42:35', '2020-03-18 01:42:35', '5', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (179, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:00:33', '2020-03-18 12:00:33', '14', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (180, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:05:09', '2020-03-18 12:05:09', '8', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (181, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:13:35', '2020-03-18 12:13:35', '8', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (182, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:13:34', '2020-03-18 12:13:34', '16', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (183, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:13:34', '2020-03-18 12:13:34', '16', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (184, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:17:45', '2020-03-18 12:17:45', '14', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (185, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:23:51', '2020-03-18 12:23:51', '14', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (186, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:28:06', '2020-03-18 12:28:06', '12', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (187, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:54:38', '2020-03-18 12:54:38', '20', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (188, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:54:38', '2020-03-18 12:54:38', '20', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
INSERT INTO `sys_log` VALUES (189, '127.0.0.1', '0', 'admin', '开启/禁用用户', 'com.zsinda.fdp.controller.SysUserController', 'delete', '/user/2', 'DELETE', '[2]', NULL, NULL, NULL, '2020-03-18 12:54:38', '2020-03-18 12:54:38', '20', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/82.0.4083.0 Safari/537.36', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `parent_id` int(10) NOT NULL DEFAULT '0' COMMENT '父级目录',
  `sort` int(10) DEFAULT NULL COMMENT '排序值',
  `type` int(1) DEFAULT '0' COMMENT '菜单类型:0菜单，1按钮，3其他',
  `redirect` varchar(200) NOT NULL DEFAULT '' COMMENT '重定向地址',
  `del_flag` int(1) DEFAULT '1' COMMENT '0已删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '权限管理', '', '/user', 'permissionmanager', '2020-03-17 09:58:22', '2019-12-14 19:43:23', 0, 1, 0, '', 1);
INSERT INTO `sys_menu` VALUES (2, '角色管理', '', '/admin/roles/index', 'role', '2020-03-16 22:25:06', '2019-12-14 19:44:00', 1, 1, 0, '', 1);
INSERT INTO `sys_menu` VALUES (3, '新增角色', 'sys_add_role', NULL, NULL, '2020-03-17 09:50:16', '2020-01-16 22:09:13', 2, 1, 1, '', 1);
INSERT INTO `sys_menu` VALUES (4, '编辑角色', 'sys_editor_role', NULL, NULL, '2020-03-17 09:50:22', '2020-01-16 22:09:16', 2, 2, 1, '', 1);
INSERT INTO `sys_menu` VALUES (5, '删除角色', 'sys_delete_role', NULL, NULL, '2020-03-17 09:50:23', '2020-01-16 22:09:19', 2, 3, 1, '', 1);
INSERT INTO `sys_menu` VALUES (6, '角色权限', 'sys_permission_role', NULL, NULL, '2020-03-17 09:50:26', NULL, 2, 4, 1, '', 1);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', NULL, '/admin/menus/index', 'menus', '2020-03-17 09:36:11', '2020-01-16 22:09:22', 1, 2, 0, '', 1);
INSERT INTO `sys_menu` VALUES (8, '新增菜单', 'sys_add_menu', NULL, NULL, '2020-03-17 10:10:31', '2020-03-11 00:22:12', 7, 1, 1, '', 1);
INSERT INTO `sys_menu` VALUES (9, '编辑菜单', 'sys_editor_menu', NULL, NULL, '2020-03-17 10:10:31', '2020-03-11 00:22:15', 7, 2, 1, '', 1);
INSERT INTO `sys_menu` VALUES (10, '删除菜单', 'sys_delete_menu', NULL, NULL, '2020-03-17 10:10:33', '2020-03-11 00:22:18', 7, 3, 1, '', 1);
INSERT INTO `sys_menu` VALUES (11, '系统管理', NULL, '/sys', 'sysmanager', '2020-03-17 09:52:09', '2020-01-16 22:09:27', 0, 2, 0, '', 1);
INSERT INTO `sys_menu` VALUES (12, '用户管理', '', '/admin/user/index', 'usermanager', '2020-03-17 11:07:04', NULL, 1, 3, 0, '', 1);
INSERT INTO `sys_menu` VALUES (13, '新增用户', 'sys_add_user', '', '', '2020-03-17 11:07:04', NULL, 12, 1, 1, '', 1);
INSERT INTO `sys_menu` VALUES (14, '编辑用户', 'sys_editor_user', '', '', '2020-03-17 11:07:04', NULL, 12, 2, 1, '', 1);
INSERT INTO `sys_menu` VALUES (15, '删除用户', 'sys_delete_user', '', '', '2020-03-17 11:07:04', NULL, 12, 3, 1, '', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_open_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_open_service`;
CREATE TABLE `sys_open_service` (
  `id` int(11) NOT NULL COMMENT 'id',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `app_id` varchar(100) NOT NULL COMMENT '开放平台id',
  `app_ secret` varchar(255) NOT NULL COMMENT '开放平台密钥',
  `app_desc` varchar(255) DEFAULT NULL COMMENT '开放平台描述',
  `redirect_url` varchar(255) DEFAULT NULL COMMENT '重定向url',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` int(1) DEFAULT '1' COMMENT ' 0:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_open_service
-- ----------------------------
BEGIN;
INSERT INTO `sys_open_service` VALUES (1, 'QQ', '101569319', '1bbc0c0250d2800586cc6e92be6c954e', '分享坛', 'http://www.zsinda.cn/qqLogin/callback.do', '2019-12-15 17:22:54', '2019-12-15 17:22:56', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(50) NOT NULL COMMENT '角色标识',
  `role_name` varchar(255) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` int(1) DEFAULT '1' COMMENT '0表示已删除',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (2, 'ROLE_ADMIN', 'admin', '系统管理员(勿删)', '2020-01-12 22:18:59', '2020-01-13 13:51:57', 1);
INSERT INTO `sys_role` VALUES (16, 'ROLE_ADMIN', 'ordinary7891', '1普通用户21', '2020-01-12 22:18:59', '2020-03-17 10:55:26', 1);
INSERT INTO `sys_role` VALUES (17, 'ROLE_ADMIN', 'ordinary81', '普通用户211', '2020-01-12 22:18:59', '2020-03-17 10:55:33', 1);
INSERT INTO `sys_role` VALUES (18, 'ROLE_ADMIN', 'ordinary9', '普通用户21', '2020-01-12 22:18:59', '2020-03-17 10:55:36', 1);
INSERT INTO `sys_role` VALUES (19, 'ROLE_ADMIN', 'ordinary10', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (20, 'ROLE_ADMIN', 'ordinar11', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (21, 'ROLE_ADMIN', 'ordinary12', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (22, 'ROLE_ADMIN', 'ordinary13', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (23, 'ROLE_ADMIN', 'ordinary14', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (24, 'ROLE_ADMIN', 'ordinary15', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (25, 'ROLE_ADMIN', 'ordinary16', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (26, 'ROLE_ADMIN', 'ordinary17', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (27, 'ROLE_ADMIN', 'ordinary18', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (28, 'ROLE_ADMIN', 'ordinary19', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (29, 'ROLE_ADMIN', 'ordinary20', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (30, 'ROLE_ADMIN', 'ordinary21', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (31, 'ROLE_ADMIN', 'ordinary22', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (32, 'ROLE_ADMIN', 'ordinary23', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (33, 'ROLE_ADMIN', 'ordinary24', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (34, 'ROLE_ADMIN', 'ordinary2', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (35, 'ROLE_ADMIN', 'ordinary25', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (36, 'ROLE_ADMIN', 'ordinary2', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (37, 'ROLE_ADMIN', 'ordinary3', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (38, 'ROLE_ADMIN', 'ordinary4', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (39, 'ROLE_ADMIN', 'ordinary5', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (40, 'ROLE_ADMIN', 'ordinary6', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (41, 'ROLE_ADMIN', 'ordinary7', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (42, 'ROLE_ADMIN', 'ordinary8', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (43, 'ROLE_ADMIN', 'ordinary9', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (44, 'ROLE_ADMIN', 'ordinary10', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (45, 'ROLE_ADMIN', 'ordinar11', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (46, 'ROLE_ADMIN', 'ordinary12', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (47, 'ROLE_ADMIN', 'ordinary13', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (49, 'ROLE_ADMIN', 'ordinary15', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (50, 'ROLE_ADMIN', 'ordinary16', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (51, 'ROLE_ADMIN', 'ordinary17', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (52, 'ROLE_ADMIN', 'ordinary18', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (53, 'ROLE_ADMIN', 'ordinary19', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (54, 'ROLE_ADMIN', 'ordinary20', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (55, 'ROLE_ADMIN', 'ordinary21', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (56, 'ROLE_ADMIN', 'ordinary22', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (57, 'ROLE_ADMIN', 'ordinary23', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (58, 'ROLE_ADMIN', 'ordinary24', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (59, 'ROLE_ADMIN', 'ordinary2', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (60, 'ROLE_ADMIN', 'ordinary25', '普通用户2', '2020-01-12 22:18:59', '2020-01-12 22:18:59', 1);
INSERT INTO `sys_role` VALUES (61, 'ROLE_TEST', 'Test', '测试专用', '2020-01-13 00:40:55', '2020-01-13 00:40:59', 1);
INSERT INTO `sys_role` VALUES (62, 'ROLE_ROOT', 'ROOT', '根用户', '2020-01-13 00:52:13', '2020-01-13 00:52:17', 1);
INSERT INTO `sys_role` VALUES (68, 'ROOT_DEMO', 'Demo', '演示环境角色', '2020-01-13 01:06:56', '2020-01-13 01:06:56', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
COMMIT;

-- ----------------------------
-- Table structure for sys_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_route_conf`;
CREATE TABLE `sys_route_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `route_name` varchar(30) DEFAULT NULL COMMENT '路由名称',
  `route_id` varchar(30) DEFAULT NULL COMMENT '路由id',
  `predicates` json DEFAULT NULL COMMENT '谓词/断言',
  `filters` json DEFAULT NULL COMMENT '过滤器',
  `uri` varchar(100) DEFAULT NULL,
  `sort` int(2) DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='全局路由配置表';

-- ----------------------------
-- Records of sys_route_conf
-- ----------------------------
BEGIN;
INSERT INTO `sys_route_conf` VALUES (1, '认证中心', 'FDP-auth', '[{\"args\": {\"_genkey_0\": \"/auth/**\"}, \"name\": \"Path\"}]', '[]', 'lb://FDP-auth', 0, '2020-03-02 16:21:24', '2020-03-02 16:27:53', '1');
INSERT INTO `sys_route_conf` VALUES (2, '用户中心', 'FDP-upmm-business', '[{\"args\": {\"_genkey_0\": \"/admin/**\"}, \"name\": \"Path\"}]', '[]', 'lb://FDP-upmm-business', 1, '2020-03-03 11:28:42', '2020-03-03 11:31:02', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `lock_flag` int(1) DEFAULT '1' COMMENT '0:账号被锁',
  `del_flag` int(1) DEFAULT '1' COMMENT '0:账号已注销',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$BL2.s4TnW9Es2kVm5hqvlOvd7aVo0CM1xjvGRDwoTbsJm1gvnlScm', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579112510161&di=afd24423c764afb2c7b735789c82422a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171121%2Fbfb3e949b05544c1b1ffb48cb33cdc11.gif', '13333333333', 'sindazeng@gmail.com', '2020-01-13 13:48:46', '2020-01-15 23:34:07', 1, 1);
INSERT INTO `sys_user` VALUES (2, 'root', '$2a$10$mGWkaXpDCGyd9UwNDjWGwOzoO5TBAqJtgjQbFOJwG8UjfMyehE2Ye', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579112510161&di=afd24423c764afb2c7b735789c82422a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171121%2Fbfb3e949b05544c1b1ffb48cb33cdc11.gif', '123123123', '12332@ee.com', '2020-02-19 10:01:19', '2020-03-18 14:59:19', 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_open_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_open_service`;
CREATE TABLE `sys_user_open_service` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `open_service_id` int(11) NOT NULL COMMENT '开放平台表id',
  `user_openid` varchar(200) DEFAULT NULL COMMENT '用户openid',
  PRIMARY KEY (`user_id`,`open_service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_open_service
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_open_service` VALUES (1, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (1, 16);
INSERT INTO `sys_user_role` VALUES (1, 17);
INSERT INTO `sys_user_role` VALUES (2, 18);
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'increment id',
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime NOT NULL COMMENT 'create datetime',
  `log_modified` datetime NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';

SET FOREIGN_KEY_CHECKS = 1;
