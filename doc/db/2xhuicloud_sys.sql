/*
 Navicat Premium Data Transfer

 Source Server         : prod
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 14.29.219.82:3306
 Source Schema         : xhuicloud_sys

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 22/11/2022 17:23:02
*/
USE xhuicloud_sys;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_client_details`;
CREATE TABLE `sys_client_details`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端名称',
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端资源id',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端权限',
  `access_token_validity` int NULL DEFAULT NULL COMMENT 'access_token的有效时间值(单位:秒)',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT 'refresh_token的有效时间值(单位:秒)',
  `captcha_enable` tinyint(1) NULL DEFAULT NULL COMMENT '登录验证码0: 否 1：是',
  `multi_login` tinyint(1) NULL DEFAULT NULL COMMENT '重复登录0: 否 1：是',
  `auto_approve` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自动授权',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  `token_format` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'reference/self-contained',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '终端信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_client_details
-- ----------------------------
INSERT INTO `sys_client_details` VALUES (1, '测试机', 'test', NULL, '{noop}test', 'server', 'password,refresh_token,authorization_code,client_credentials,mobile,social', 'http://xhuicloud.cn', NULL, 300000, 300000, 1, 0, 'false', 0, 'self-contained', 1);
INSERT INTO `sys_client_details` VALUES (2, '星辉01', 'xhuicloud', NULL, '{noop}xhuicloud', 'server', 'password,refresh_token,authorization_code,client_credentials,social', 'http://127.0.0.1:9092/authorization', NULL, 300000, 300000, 0, 1, 'true', 0, NULL, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区域、地址、工位',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '上级ID',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '董事会', NULL, 0, 1, '2020-03-21 15:17:09', 0, '2022-03-07 13:59:27', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (2, '总经理', NULL, 1, 1, '2020-03-21 15:18:17', 0, '2022-03-07 13:59:35', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (3, '营销策划中心', NULL, 2, 1, '2020-03-21 15:18:49', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (4, '工程管理中心', NULL, 2, 2, '2020-03-21 15:19:48', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (5, '运营中心', NULL, 2, 3, '2020-03-21 15:20:32', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (6, '财务中心', NULL, 2, 4, '2020-03-21 15:20:58', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (7, '行政人力资源中心', NULL, 2, 5, '2020-03-21 15:21:33', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (8, '策划部', NULL, 3, 1, '2020-03-21 15:22:13', 0, '2022-04-22 00:53:30', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (9, '销售部', NULL, 3, 2, '2020-03-21 15:22:30', 0, '2022-03-07 13:59:39', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (10, '项目开发部', '工程楼5楼', 4, 1, '2020-03-21 15:22:52', 0, '2022-09-15 10:39:22', 1, 1, 0);
INSERT INTO `sys_dept` VALUES (11, '设计部', NULL, 4, 2, '2020-03-21 15:23:30', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (12, '工程部', NULL, 4, 3, '2020-03-21 15:24:00', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (13, '总工室', NULL, 4, 4, '2020-03-21 15:24:15', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (14, '项目一部', NULL, 4, 5, '2020-03-21 15:24:52', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (15, '战略投资部', NULL, 5, 1, '2020-03-21 15:25:38', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (16, '法律事务部', NULL, 5, 2, '2020-03-21 15:25:48', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (17, '财务部', NULL, 6, 1, '2020-03-21 15:26:17', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (18, '融资部', NULL, 6, 2, '2020-03-21 15:26:38', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (19, '人力资源部', NULL, 7, 1, '2020-03-21 15:27:47', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (20, '行政部', NULL, 7, 2, '2020-03-21 15:27:59', 0, '2022-03-07 13:59:40', NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (21, '研发部', NULL, 1, 0, '2022-04-22 00:53:52', 1, '2022-05-07 17:16:57', 1, NULL, 1);
INSERT INTO `sys_dept` VALUES (22, '12312', '123123', 1, 0, '2022-05-07 17:24:37', 1, NULL, NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (23, '股东会', NULL, 0, 0, '2022-09-15 10:39:55', 1, NULL, NULL, 1, 0);
INSERT INTO `sys_dept` VALUES (24, '医疗部', NULL, 0, 0, '2022-09-15 10:40:42', 1, '2022-09-15 10:42:03', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (25, '测试', NULL, 0, 0, '2022-09-15 10:41:55', 1, '2022-09-15 10:42:01', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '字典项主键',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项类型',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项描述',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'sys_parameters', '系统参数', '系统全局参数', NULL, '2020-03-23 09:47:52', 1, '2022-02-11 15:36:08', 1, 0);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '字典数据id',
  `dict_id` int NOT NULL COMMENT '字典项id',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项名称',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典键值',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典描述',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`, `dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, 'sys_parameters', '默认密码', NULL, '系统账户默认密码', '用户管理-账号初始密码', 1, '2020-03-23 10:16:57', 1, '2021-11-26 15:39:18', NULL, 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片Url',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桶内文件名称',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原本文件名称',
  `file_size` bigint NULL DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `bucket_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '桶名称',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作IP',
  `type` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '日志类型\n#LogType{0:操作类型;1:异常类型}',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作描述',
  `class_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类路径',
  `request_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方法',
  `request_uri` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求地址',
  `http_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'GET' COMMENT '请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回值',
  `ex_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '异常详情信息',
  `ex_detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '异常描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `time` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_create_by`(`user_name` ASC) USING BTREE,
  INDEX `sys_log_request_uri`(`request_uri` ASC) USING BTREE,
  INDEX `sys_log_type`(`type` ASC) USING BTREE,
  INDEX `sys_log_create_date`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2867 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `login_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录ip',
  `useragent` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器信息',
  `status` tinyint NOT NULL COMMENT '0:成功 1:失败',
  `remake` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1051 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `internationalization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国际化key',
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限值',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前端路径',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源url',
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '父级id',
  `sort` int NULL DEFAULT NULL COMMENT '排序值',
  `type` int NULL DEFAULT 0 COMMENT '菜单类型:0菜单，1按钮，3其他',
  `redirect` varchar(200) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL DEFAULT '' COMMENT '重定向地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '权限管理', 'Permissions', '', '/admin', NULL, 'file', 0, 1, 0, '', '2021-11-25 16:59:24', '2022-02-15 16:34:12', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2, '角色管理', 'Roles', '', '/admin/roles/index', NULL, 'role', 1, 2, 0, '', '2021-11-25 16:59:24', '2022-04-23 22:09:24', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (3, '新增角色', 'RolesAdd', 'sys_add_role', NULL, NULL, NULL, 2, 4, 1, '', '2021-11-25 16:59:24', '2022-03-15 19:10:38', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '编辑角色', 'RolesUpdate', 'sys_editor_role', NULL, NULL, NULL, 2, 2, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:33', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (5, '删除角色', 'RolesDelete', 'sys_delete_role', NULL, NULL, NULL, 2, 3, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:37', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (6, '角色权限', 'RolesPermission', 'sys_permission_role', NULL, NULL, NULL, 2, 4, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:46:50', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', 'Menus', NULL, '/admin/menus/index', NULL, 'menus', 1, 3, 0, '', '2021-11-25 16:59:24', '2022-04-23 22:09:28', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (8, '新增菜单', 'MenusAdd', 'sys_add_menu', NULL, NULL, NULL, 7, 1, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:42', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (9, '编辑菜单', 'MenusUpdate', 'sys_editor_menu', NULL, NULL, NULL, 7, 2, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:54', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (10, '删除菜单', 'MenusDelete', 'sys_delete_menu', NULL, NULL, NULL, 7, 3, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:48:01', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (11, '系统管理', 'System', NULL, '/system', NULL, 'system', 0, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:15', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (12, '用户管理', 'Users', '', '/admin/user/index', NULL, 'user', 1, 1, 0, '', '2021-11-25 16:59:24', '2022-04-23 22:09:19', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (13, '新增用户', 'UsersAdd', 'sys_add_user', '', NULL, '', 12, 1, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (14, '编辑用户', 'UsersUpdate', 'sys_editor_user', '', NULL, '', 12, 2, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (15, '删除用户', 'UsersDelete', 'sys_delete_user', '', NULL, '', 12, 3, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (16, '禁用用户', 'UsersBan', 'sys_ban_user', '', NULL, '', 12, 4, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (17, '导出用户', 'UsersExport', 'sys_export_user', '', NULL, '', 12, 5, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (18, '导入用户', 'UsersImport', 'sys_import_user', '', NULL, '', 12, 6, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (19, '公共参数', 'Params', '', '/system/param/index', NULL, 'data', 11, 1, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (20, '新增参数', 'ParamsAdd', 'sys_add_param', '', NULL, '', 19, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (21, '编辑参数', 'ParamsUpdate', 'sys_editor_param', '', NULL, '', 19, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (22, '删除参数', 'ParamsDelete', 'sys_delete_param', '', NULL, '', 19, 3, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (23, '文件管理', 'Files', '', '/system/file/index', NULL, 'file', 11, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (24, '上传文件', 'FilesUpload', 'sys_upload_icon', '', NULL, NULL, 23, 1, 1, '', '2021-11-25 16:59:24', '2022-04-22 22:54:03', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (25, '删除文件', 'FilesDelete', 'sys_delete_icon', '', NULL, '', 23, 2, 1, '', '2021-11-25 16:59:24', '2022-04-22 22:53:56', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (26, '租户管理', 'Tenants', '', '/admin/tenant/index', NULL, 'tenant', 1, 4, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (27, '新增租户', 'TenantsAdd', 'sys_add_tenant', '', NULL, '', 26, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (28, '编辑租户', 'TenantsUpdate', 'sys_editor_tenant', '', NULL, '', 26, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (29, '删除租户', 'TenantsDelete', 'sys_delete_tenant', '', NULL, '', 26, 3, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (31, '支付管理', 'Pays', '', '/pay', NULL, 'pay', 0, 3, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (32, '聚合支付', 'PaysQrCode', '', '/pay/qrcode/index', NULL, 'QrCode', 31, 1, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (33, '开发管理', 'Devs', '', '/dev', NULL, 'dev', 0, 4, 0, '', '2021-11-25 16:59:24', '2022-04-18 17:20:43', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (34, '代码生成', 'Generators', '', '/development/gen/index', NULL, 'datasource', 33, 1, 0, '', '2021-11-25 16:59:24', '2022-11-01 07:11:11', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (35, '新增数据源', 'DataSourceAdd', 'sys_add_db', '', NULL, '', 34, 1, 1, '', '2021-11-25 16:59:24', '2022-04-18 17:11:30', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (36, '批量生成', 'GeneratorsBatchAdd', 'sys_download_code', '', NULL, '', 34, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:17', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (37, '接口文档', 'InterfaceDocs', '', 'http://127.0.0.1:15000/doc.html#/home', NULL, 'doc', 33, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 18:02:58', 1, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (38, '系统监控', 'SystemMonitor', '', 'http://127.0.0.1:14000/#/login', NULL, 'sentinel', 33, 3, 0, '', '2021-11-25 16:59:24', '2022-03-14 18:02:59', 1, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (58, '图库管理', 'Icons', NULL, '/system/icons/index', NULL, 'icons', 11, 3, 0, '', '2022-03-16 19:47:34', '2022-09-20 16:18:57', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (59, '登录记录', 'LoginLog', NULL, '/system/log/login', NULL, 'loginLogs', 11, 5, 0, '', '2022-03-19 23:40:20', '2022-09-20 16:18:49', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (61, '部门管理', 'Depts', NULL, '/admin/dept/index', NULL, 'dept', 1, 5, 0, '', '2022-04-21 22:55:32', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (62, '新增部门', 'DeptsAdd', 'sys_add_dept', NULL, NULL, NULL, 61, 1, 1, '', '2022-04-21 23:58:24', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (63, '编辑部门', 'DeptsUpdate', 'sys_editor_dept', NULL, NULL, NULL, 61, 2, 1, '', '2022-04-21 23:59:05', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (64, '删除部门', 'DeptsDelete', 'sys_delete_dept', NULL, NULL, NULL, 61, 3, 1, '', '2022-04-21 23:59:49', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (65, '删除图标', 'IconsDelete', 'sys_delete_file', NULL, NULL, NULL, 58, 1, 1, '', '2022-04-22 22:49:08', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (66, '数据字典', 'Dicts', NULL, '/system/dict/index', NULL, 'dict', 11, 4, 0, '', '2022-04-22 23:14:21', '2022-09-20 16:19:00', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (67, '新增数据字典', 'DictsAdd', 'sys_add_dict', NULL, NULL, NULL, 66, 1, 1, '', '2022-04-22 23:24:24', '2022-04-22 23:30:15', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (68, '编辑数据字典', 'DictsUpdate', 'sys_editor_dict', NULL, NULL, NULL, 66, 2, 1, '', '2022-04-22 23:24:55', '2022-04-22 23:30:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (69, '删除数据字典', 'DictsDelete', 'sys_delete_dict', NULL, NULL, NULL, 66, 3, 1, '', '2022-04-22 23:25:40', '2022-04-22 23:30:19', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (70, '新增字典项', 'DictsDataAdd', 'sys_add_dictData', NULL, NULL, NULL, 66, 4, 1, '', '2022-04-22 23:33:19', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (71, '编辑字典项', 'DictsDataUpdate', 'sys_editor_dictData', NULL, NULL, NULL, 66, 5, 1, '', '2022-04-22 23:33:57', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (72, '删除字典项', 'DictsDataDelete', 'sys_delete_dictData', NULL, NULL, NULL, 66, 6, 1, '', '2022-04-22 23:34:27', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (73, '操作日志', 'Logs', NULL, '/system/log/index', NULL, 'logs', 11, 6, 0, '', '2022-04-23 22:15:39', '2022-09-20 16:18:51', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (74, '授权管理', 'AuthClient', NULL, '/system/client/index', NULL, 'authClient', 11, 7, 0, '', '2022-05-02 23:25:04', '2022-09-20 16:18:51', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (76, '编辑', 'ClientUpdate', 'sys_editor_client', NULL, NULL, NULL, 74, 1, 1, '', '2022-10-22 08:08:52', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (77, '新增', 'ClientAdd', 'sys_add_client', NULL, NULL, NULL, 74, 2, 1, '', '2022-10-22 08:10:38', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (78, '删除', 'ClientDel', 'sys_delete_client', NULL, NULL, NULL, 74, 3, 1, '', '2022-10-22 08:11:05', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (79, '应用路由', 'ApplicationRouter', NULL, '/system/application/index', NULL, 'route', 11, 8, 0, '', '2022-10-28 03:33:33', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (80, '编辑', 'ApplicationRouterUpdate', 'sys_editor_route', NULL, NULL, NULL, 79, 1, 1, '', '2022-10-31 13:42:47', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (81, '新增', 'ApplicationRouterAdd', 'sys_add_route', NULL, NULL, NULL, 79, 2, 1, '', '2022-10-31 13:44:06', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (82, '删除', 'ApplicationRouterDelete', 'sys_delete_route', NULL, NULL, NULL, 79, 3, 1, '', '2022-11-02 15:51:38', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (83, '编辑', 'DataSourceUpdate', 'sys_editor_db', NULL, NULL, NULL, 34, 3, 1, '', '2022-11-02 16:23:29', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (84, '删除', 'DataSourceDelete', 'sys_delete_db', NULL, NULL, NULL, 34, 4, 1, '', '2022-11-02 16:24:01', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (85, '微信运营', 'WechatOperating', NULL, '/wechat', NULL, 'wechatOperating', 0, 5, 0, '', '2022-11-04 02:56:33', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (86, '底部菜单', 'WechatMenus', NULL, '/wechat/menu/index', NULL, 'menus', 85, 1, 0, '', '2022-11-04 02:57:38', '2022-11-04 11:24:15', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (87, '账户管理', 'WechatAccount', NULL, '/wechat/account/index', NULL, 'userInfo', 85, 2, 0, '', '2022-11-04 11:27:46', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (88, '添加', 'WechatAccountAdd', 'sys_add_account', NULL, NULL, NULL, 87, 1, 1, '', '2022-11-07 06:54:53', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (89, '编辑', 'WechatAccountUpdate', 'sys_editor_account', NULL, NULL, NULL, 87, 2, 1, '', '2022-11-07 06:55:26', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (90, '删除', 'WechatAccountDelete', 'sys_delete_account', NULL, NULL, NULL, 87, 3, 1, '', '2022-11-07 06:55:53', '2022-11-07 06:56:11', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (91, '素材管理', 'MaterialManagement', NULL, '/wechat/material/index', NULL, 'material', 85, 3, 0, '', '2022-11-14 12:24:40', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (92, '添加', 'MaterialAdd', 'sys_add_material', NULL, NULL, NULL, 91, 1, 1, '', '2022-11-15 07:16:46', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (93, '删除', 'Material', 'sys_delete_material', NULL, NULL, NULL, 91, 2, 1, '', '2022-11-15 07:17:17', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (94, '草稿箱', 'Drafts', NULL, '/wechat/drafts/index', NULL, 'drafts', 85, 1, 0, '', '2022-11-25 10:35:52', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (95, '新增', 'DraftsAdd', 'sys_add_drafts', NULL, NULL, NULL, 94, 0, 1, '', '2022-12-01 13:43:44', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (96, '编辑', 'DraftsUpdate', 'sys_editor_drafts', NULL, NULL, NULL, 94, 1, 1, '', '2022-12-01 13:45:34', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (97, '删除', 'DraftsDelete', 'sys_delete_drafts', NULL, NULL, NULL, 94, 2, 1, '', '2022-12-01 13:46:40', NULL, 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (98, '发布', 'DraftsPublish', 'sys_publish_drafts', NULL, NULL, NULL, 94, 4, 1, '', '2022-12-01 13:53:52', NULL, 0, NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `param_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数键名',
  `param_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数键值',
  `param_type` int NOT NULL DEFAULT 0 COMMENT '0:系统参数  1:业务参数',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_id` int NOT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0:否 1:是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `KEY`(`param_key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES (1, '系统账号默认密码', 'SYS_USER_DEFAULT_PASSWORD', '{bcrypt}$2a$10$Fti91Ze0Rx7yoXGz8kp43ejvZDXkQ1B085DE9jwNCcjE4jB8w1oJC', 0, '系统用户默认密码', 1, 1, '2020-03-23 12:00:50', 1, '2022-04-29 10:47:10', 0);
INSERT INTO `sys_param` VALUES (2, '系统账号默认角色', 'SYS_USER_DEFAULT_ROLE', '2', 0, '系统用户默认角色', 2, 1, '2020-03-23 15:06:52', 1, '2022-04-29 09:56:34', 0);
INSERT INTO `sys_param` VALUES (3, '系统账号默认部门', 'SYS_USER_DEFAULT_DEPT', '5', 0, '系统用户默认部门', 3, 1, '2020-03-23 15:07:34', 1, '2022-04-29 09:56:52', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色标识',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 919 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', 'ROOT', '系统管理员(勿删)', '2021-11-25 17:11:56', 0, '2022-07-08 11:16:34', 1, 1, 0);
INSERT INTO `sys_role` VALUES (2, 'ORDINARY', 'ordinary', '普通用户', '2021-11-25 17:11:56', 0, '2022-07-08 11:16:34', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 29);
INSERT INTO `sys_role_menu` VALUES (1, 31);
INSERT INTO `sys_role_menu` VALUES (1, 32);
INSERT INTO `sys_role_menu` VALUES (1, 33);
INSERT INTO `sys_role_menu` VALUES (1, 34);
INSERT INTO `sys_role_menu` VALUES (1, 35);
INSERT INTO `sys_role_menu` VALUES (1, 36);
INSERT INTO `sys_role_menu` VALUES (1, 58);
INSERT INTO `sys_role_menu` VALUES (1, 59);
INSERT INTO `sys_role_menu` VALUES (1, 61);
INSERT INTO `sys_role_menu` VALUES (1, 62);
INSERT INTO `sys_role_menu` VALUES (1, 63);
INSERT INTO `sys_role_menu` VALUES (1, 64);
INSERT INTO `sys_role_menu` VALUES (1, 65);
INSERT INTO `sys_role_menu` VALUES (1, 66);
INSERT INTO `sys_role_menu` VALUES (1, 67);
INSERT INTO `sys_role_menu` VALUES (1, 68);
INSERT INTO `sys_role_menu` VALUES (1, 69);
INSERT INTO `sys_role_menu` VALUES (1, 70);
INSERT INTO `sys_role_menu` VALUES (1, 71);
INSERT INTO `sys_role_menu` VALUES (1, 72);
INSERT INTO `sys_role_menu` VALUES (1, 73);
INSERT INTO `sys_role_menu` VALUES (1, 74);
INSERT INTO `sys_role_menu` VALUES (1, 76);
INSERT INTO `sys_role_menu` VALUES (1, 77);
INSERT INTO `sys_role_menu` VALUES (1, 78);
INSERT INTO `sys_role_menu` VALUES (1, 79);
INSERT INTO `sys_role_menu` VALUES (1, 80);
INSERT INTO `sys_role_menu` VALUES (1, 81);
INSERT INTO `sys_role_menu` VALUES (1, 82);
INSERT INTO `sys_role_menu` VALUES (1, 83);
INSERT INTO `sys_role_menu` VALUES (1, 84);
INSERT INTO `sys_role_menu` VALUES (1, 85);
INSERT INTO `sys_role_menu` VALUES (1, 86);
INSERT INTO `sys_role_menu` VALUES (1, 87);
INSERT INTO `sys_role_menu` VALUES (1, 88);
INSERT INTO `sys_role_menu` VALUES (1, 89);
INSERT INTO `sys_role_menu` VALUES (1, 90);
INSERT INTO `sys_role_menu` VALUES (1, 91);
INSERT INTO `sys_role_menu` VALUES (1, 92);
INSERT INTO `sys_role_menu` VALUES (1, 93);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 36);

-- ----------------------------
-- Table structure for sys_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_route_conf`;
CREATE TABLE `sys_route_conf`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `route_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
  `route_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由id',
  `predicates` json NULL COMMENT '谓词/断言',
  `filters` json NULL COMMENT '过滤器',
  `uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全局路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_route_conf
-- ----------------------------
INSERT INTO `sys_route_conf` VALUES (1, '认证中心', 'XHuiCloud-auth', '[{\"args\": {\"_genkey_0\": \"/auth/**\"}, \"name\": \"Path\"}]', '[{\"args\": {}, \"name\": \"Code\"}]', 'lb://XHuiCloud-auth', 1, '2021-11-25 21:05:43', 0, '2022-04-05 22:59:51', NULL, 0);
INSERT INTO `sys_route_conf` VALUES (2, '用户中心', 'XHuiCloud-upms-service', '[{\"args\": {\"_genkey_0\": \"/admin/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-upms-service', 2, '2021-11-25 21:05:43', 0, '2022-04-29 09:28:58', NULL, 0);
INSERT INTO `sys_route_conf` VALUES (3, '支付中心', 'XHuiCloud-pay-business', '[{\"args\": {\"_genkey_0\": \"/pay/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-pay-business', 3, '2021-11-25 21:05:43', 0, '2022-04-29 09:29:01', NULL, 0);
INSERT INTO `sys_route_conf` VALUES (4, '开发管理', 'XHuiCloud-generator', '[{\"args\": {\"_genkey_0\": \"/gen/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-generator', 4, '2021-11-25 21:05:43', 0, '2022-04-29 09:29:02', NULL, 0);
INSERT INTO `sys_route_conf` VALUES (5, '推送中心', 'XHuiCloud-push-service', '[{\"args\": {\"_genkey_0\": \"/push/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-push-service', 5, '2021-12-07 16:45:44', 0, '2022-04-29 09:29:03', NULL, 0);
INSERT INTO `sys_route_conf` VALUES (6, '日志中心', 'XHuiCloud-logs-service', '[{\"args\": {\"_genkey_0\": \"/logs/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-logs-service', 6, '2021-12-07 16:45:44', 0, '2022-10-31 11:56:17', 1, 0);
INSERT INTO `sys_route_conf` VALUES (9, '微信运营', 'XHuiCloud-wechat-service', '[{\"args\": {\"_genkey_0\": \"/wechat/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-wechat-service', 0, '2022-11-04 09:07:21', 1, '2022-11-04 09:20:08', 1, 0);

-- ----------------------------
-- Table structure for sys_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_social`;
CREATE TABLE `sys_social`  (
  `id` int NOT NULL COMMENT 'id',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台id',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台密钥',
  `app_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开放平台描述',
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向url',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_tenant_id`(`type` ASC, `tenant_id` ASC) USING BTREE COMMENT '租户社交唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统开放平台' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '租户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户名称',
  `state` int NULL DEFAULT 2 COMMENT '状态\n0:禁用; 1:正常;  2:待审核;  3:拒绝',
  `expiration_time` datetime NULL DEFAULT NULL COMMENT '有效期',
  `logo` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'logo地址',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '租户描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NOT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '星辉云', 1, '2027-03-29 18:12:28', 'https://xhuicloud.oss-cn-shenzhen.aliyuncs.com/xhuicloud-logo.png', NULL, '2020-05-11 18:13:31', 0, '2022-04-07 17:38:56', NULL, 0);
INSERT INTO `sys_tenant` VALUES (2, '测试', 1, '2023-05-25 18:13:03', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F0915%252F1cea7c3bj00qzgw4s0015c000hs00hsc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651925977&t=1c2ce613aa75287b0101df72959f3d27', NULL, '2020-05-13 16:00:59', 0, '2022-04-07 20:19:52', NULL, 0);
INSERT INTO `sys_tenant` VALUES (3, '分公司', 1, '2029-09-25 18:13:08', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic2.zhimg.com%2F80%2Fv2-8a73940f010e56b63465be0b35ef5701_720w.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic2.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651925851&t=da4d7b3e67d830baa6a438c5a7828f8c', NULL, '2020-05-13 16:02:26', 0, '2022-04-07 20:17:35', NULL, 0);
INSERT INTO `sys_tenant` VALUES (4, '广东公司', 1, '2021-05-21 00:00:00', 'https://img0.baidu.com/it/u=2840206629,2327254830&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', NULL, '2020-05-25 18:18:38', 0, '2022-04-07 20:17:42', NULL, 0);
INSERT INTO `sys_tenant` VALUES (5, '测试租户1', 1, '2029-12-06 00:00:00', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp4.itc.cn%2Fq_70%2Fimages03%2F20210124%2F1cb2ea3b778a4759b02e93e68133d1dd.jpeg&refer=http%3A%2F%2Fp4.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651925868&t=b3f8255134bd8493b98eaa2ee0389684', '测试租户', '2020-05-29 16:38:52', 0, '2022-04-07 20:17:52', NULL, 0);
INSERT INTO `sys_tenant` VALUES (6, '广州', 1, '2022-04-29 00:01:00', 'http://127.0.0.1:9000/xhuicloud/2b10b75d4e1f4d58b679fc961b6af4fe.jpg', NULL, '2022-04-10 15:00:50', 1, '2022-04-10 15:51:16', 1, 0);
INSERT INTO `sys_tenant` VALUES (7, '测试', 0, NULL, 'http://127.0.0.1:9000/xhuicloud/3acd15c00d71442da037d85f3fa4b5f4.jpg', NULL, '2022-04-10 15:01:26', 1, '2022-04-15 21:10:35', 1, 1);
INSERT INTO `sys_tenant` VALUES (8, '123213', 1, '2022-04-29 00:00:00', 'http://127.0.0.1:9000/xhuicloud/54301af74ac04ae78517bc2df025f7f8.jpg', NULL, '2022-04-15 17:16:09', 1, '2022-04-15 21:10:30', 1, 1);
INSERT INTO `sys_tenant` VALUES (9, '测试11111', 1, '2022-04-15 21:10:49', 'http://127.0.0.1:9000/xhuicloud/8833031b4f0a412ebea1e438e6e2885f.png', NULL, '2022-04-15 21:10:52', 1, '2022-09-13 14:44:24', 1, 0);
INSERT INTO `sys_tenant` VALUES (10, '123123', 0, '2022-09-21 12:00:00', 'http://127.0.0.1:9000/xhuicloud/e7d2916cc9404a3d8b2c93036e713aff.png', NULL, '2022-09-13 14:48:14', 1, '2022-09-13 14:48:37', 1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` int NULL DEFAULT 1 COMMENT '性别:0 女、1  男、2  其他',
  `lock_flag` tinyint(1) NULL DEFAULT 1 COMMENT '0: 否 1：是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  `motto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$2vOpPUA2pL5f5RY2f7A8W.aSL8i0AoVogItFaV6EcHjmLnCzP3BCu', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.zhimg.com%2F80%2Fv2-cb8594f76789bea7cd324f0509b13565_720w.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic3.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666340692&t=899bc5f3513d766b4b5617f620c94de2', '15555555555', 'sindazeng@gmail.com', 1, 0, '2021-11-25 21:06:29', 0, '2022-09-27 00:49:18', 1, 1, 0, '落叶的一生，只是为了归根吗');
INSERT INTO `sys_user` VALUES (74, '13392659042', '{bcrypt}$2a$10$Fti91Ze0Rx7yoXGz8kp43ejvZDXkQ1B085DE9jwNCcjE4jB8w1oJC', NULL, '13392659042', NULL, 0, 0, '2022-08-23 14:02:17', NULL, '2022-09-09 14:01:09', 1, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (75, '测试工程师', '{bcrypt}$2a$10$Fti91Ze0Rx7yoXGz8kp43ejvZDXkQ1B085DE9jwNCcjE4jB8w1oJC', 'http://127.0.0.1:9000/xhuicloud/6f91cf3e49384263b8fa4f545dbfdad5.png', '13222222222', '5555@qq.com', 0, 0, '2022-09-08 14:36:22', 1, '2022-09-08 14:52:48', 1, 1, 0, NULL);
INSERT INTO `sys_user` VALUES (76, '运维工程师', '{bcrypt}$2a$10$Fti91Ze0Rx7yoXGz8kp43ejvZDXkQ1B085DE9jwNCcjE4jB8w1oJC', 'http://127.0.0.1:9000/xhuicloud/32390faef24f47098bd1a812c5e22f7f.png', '13323333333', 'yunwei@qq.com', 1, 0, '2022-09-09 13:58:03', 1, '2022-09-09 14:01:48', 1, 1, 1, NULL);
INSERT INTO `sys_user` VALUES (78, '^_^', '{bcrypt}$2a$10$Fti91Ze0Rx7yoXGz8kp43ejvZDXkQ1B085DE9jwNCcjE4jB8w1oJC', 'http://thirdqq.qlogo.cn/g?b=oidb&k=9qiboyggqmVRrlnu3wG4MgA&s=640&t=1649494474', NULL, NULL, 1, 0, '2022-09-16 13:59:24', NULL, NULL, NULL, 1, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `user_id` int NOT NULL,
  `dept_id` int NOT NULL,
  PRIMARY KEY (`user_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES (1, 1);
INSERT INTO `sys_user_dept` VALUES (1, 9);
INSERT INTO `sys_user_dept` VALUES (74, 5);
INSERT INTO `sys_user_dept` VALUES (75, 9);
INSERT INTO `sys_user_dept` VALUES (76, 2);
INSERT INTO `sys_user_dept` VALUES (77, 5);
INSERT INTO `sys_user_dept` VALUES (78, 5);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (74, 2);
INSERT INTO `sys_user_role` VALUES (75, 2);
INSERT INTO `sys_user_role` VALUES (76, 1);
INSERT INTO `sys_user_role` VALUES (77, 2);
INSERT INTO `sys_user_role` VALUES (78, 2);

-- ----------------------------
-- Table structure for sys_user_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_social`;
CREATE TABLE `sys_user_social`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `social_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台类型',
  `user_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户openid',
  PRIMARY KEY (`user_id`, `user_openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户社交' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
