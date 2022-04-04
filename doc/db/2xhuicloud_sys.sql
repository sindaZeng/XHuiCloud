/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

USE xhuicloud_sys;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_client_details`;
CREATE TABLE `sys_client_details`  (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                       `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `access_token_validity` int(11) NULL DEFAULT NULL,
                                       `refresh_token_validity` int(11) NULL DEFAULT NULL,
                                       `additional_information` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                       `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                                       `tenant_id` int(11) NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '终端信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_client_details
-- ----------------------------
INSERT INTO `sys_client_details` VALUES (1, 'test', NULL, 'test', 'server', 'password,refresh_token,social', NULL, NULL, NULL, NULL, '{ \"enc_flag\":\"1\",\"captcha_flag\":\"0\"}', 'true', 0, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
                             `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区域、地址、工位',
                             `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级ID',
                             `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             `create_id` int(11) NOT NULL COMMENT '创建者id',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '董事会', NULL, 0, 1, '2020-03-21 15:17:09', '2022-03-07 13:59:27', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (2, '总经理', NULL, 1, 1, '2020-03-21 15:18:17', '2022-03-07 13:59:35', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (3, '营销策划中心', NULL, 2, 1, '2020-03-21 15:18:49', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (4, '工程管理中心', NULL, 2, 2, '2020-03-21 15:19:48', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (5, '运营中心', NULL, 2, 3, '2020-03-21 15:20:32', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (6, '财务中心', NULL, 2, 4, '2020-03-21 15:20:58', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (7, '行政人力资源中心', NULL, 2, 5, '2020-03-21 15:21:33', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (8, '策划部', NULL, 3, 1, '2020-03-21 15:22:13', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (9, '销售部', NULL, 3, 2, '2020-03-21 15:22:30', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (10, '项目开发部', NULL, 4, 1, '2020-03-21 15:22:52', '2022-03-07 13:59:39', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (11, '设计部', NULL, 4, 2, '2020-03-21 15:23:30', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (12, '工程部', NULL, 4, 3, '2020-03-21 15:24:00', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (13, '总工室', NULL, 4, 4, '2020-03-21 15:24:15', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (14, '项目一部', NULL, 4, 5, '2020-03-21 15:24:52', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (15, '战略投资部', NULL, 5, 1, '2020-03-21 15:25:38', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (16, '法律事务部', NULL, 5, 2, '2020-03-21 15:25:48', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (17, '财务部', NULL, 6, 1, '2020-03-21 15:26:17', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (18, '融资部', NULL, 6, 2, '2020-03-21 15:26:38', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (19, '人力资源部', NULL, 7, 1, '2020-03-21 15:27:47', '2022-03-07 13:59:40', 0, 1, 0, NULL);
INSERT INTO `sys_dept` VALUES (20, '行政部', NULL, 7, 2, '2020-03-21 15:27:59', '2022-03-07 13:59:40', 0, 1, 0, NULL);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典项主键',
                             `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项类型',
                             `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项描述',
                             `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项备注',
                             `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `create_id` int(11) NOT NULL COMMENT '创建者id',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `name`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'sys_parameters', '系统参数', '系统全局参数', NULL, '2020-03-23 09:47:52', 1, '2022-02-11 15:36:08', NULL, 0, 1);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典数据id',
                                  `dict_id` int(11) NOT NULL COMMENT '字典项id',
                                  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项名称',
                                  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典标签',
                                  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典键值',
                                  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典描述',
                                  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                                  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                                  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `create_id` int(11) NOT NULL COMMENT '创建者id',
                                  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                                  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                                  PRIMARY KEY (`id`, `dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, 'sys_parameters', '默认密码', NULL, '系统账户默认密码', '用户管理-账号初始密码', 1, '2020-03-23 10:16:57', 1, '2021-11-26 15:39:18', NULL, 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
                             `url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片Url',
                             `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桶内文件名称',
                             `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原本文件名称',
                             `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
                             `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
                             `bucket_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '桶名称',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             `create_id` int(11) NOT NULL COMMENT '创建者id',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
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
                            `time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '执行时间',
                            `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器',
                            `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                            `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `sys_log_create_by`(`user_name`) USING BTREE,
                            INDEX `sys_log_request_uri`(`request_uri`) USING BTREE,
                            INDEX `sys_log_type`(`type`) USING BTREE,
                            INDEX `sys_log_create_date`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1058 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                                  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
                                  `login_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
                                  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录ip',
                                  `useragent` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器信息',
                                  `status` tinyint(4) NOT NULL COMMENT '0:成功 1:失败',
                                  `remake` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
                             `internationalization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国际化key',
                             `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限',
                             `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
                             `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
                             `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父级目录',
                             `sort` int(11) NULL DEFAULT NULL COMMENT '排序值',
                             `type` int(11) NULL DEFAULT 0 COMMENT '菜单类型:0菜单，1按钮，3其他',
                             `redirect` varchar(200) CHARACTER SET utf16 COLLATE utf16_croatian_ci NOT NULL DEFAULT '' COMMENT '重定向地址',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             `create_id` int(11) NOT NULL COMMENT '创建者id',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '权限管理', 'Permissions', '', '/admin', 'file', 0, 1, 0, '', '2021-11-25 16:59:24', '2022-02-15 16:34:12', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (2, '角色管理', 'Roles', '', '/admin/roles/index', 'role', 1, 1, 0, '', '2021-11-25 16:59:24', '2022-02-16 14:38:18', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (3, '新增角色', 'RolesAdd', 'sys_add_role', NULL, NULL, 2, 4, 1, '', '2021-11-25 16:59:24', '2022-03-15 19:10:38', 0, NULL, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '编辑角色', 'RolesUpdate', 'sys_editor_role', NULL, NULL, 2, 2, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:33', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (5, '删除角色', 'RolesDelete', 'sys_delete_role', NULL, NULL, 2, 3, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:37', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (6, '角色权限', 'RolesPermission', 'sys_permission_role', NULL, NULL, 2, 4, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:46:50', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (7, '菜单管理', 'Menus', NULL, '/admin/menus/index', 'menus', 1, 2, 0, '', '2021-11-25 16:59:24', '2022-02-16 14:38:21', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (8, '新增菜单', 'MenusAdd', 'sys_add_menu', NULL, NULL, 7, 1, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:42', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (9, '编辑菜单', 'MenusUpdate', 'sys_editor_menu', NULL, NULL, 7, 2, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:47:54', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (10, '删除菜单', 'MenusDelete', 'sys_delete_menu', NULL, NULL, 7, 3, 1, '', '2021-11-25 16:59:24', '2022-02-15 16:48:01', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (11, '系统管理', 'System', NULL, '/system', 'system', 0, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:15', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (12, '用户管理', 'Users', '', '/admin/user/index', 'user', 1, 3, 0, '', '2021-11-25 16:59:24', '2022-03-08 17:29:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (13, '新增用户', 'UsersAdd', 'sys_add_user', '', '', 12, 1, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (14, '编辑用户', 'UsersUpdate', 'sys_editor_user', '', '', 12, 2, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (15, '删除用户', 'UsersDelete', 'sys_delete_user', '', '', 12, 3, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (16, '禁用用户', 'UsersBan', 'sys_ban_user', '', '', 12, 4, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (17, '导出用户', 'UsersExport', 'sys_export_user', '', '', 12, 5, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (18, '导入用户', 'UsersImport', 'sys_import_user', '', '', 12, 6, 1, '', '2021-11-25 16:59:24', '2022-03-13 22:23:22', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (19, '公共参数', 'Params', '', '/system/param/index', 'data', 11, 1, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (20, '新增参数', 'ParamsAdd', 'sys_add_param', '', '', 19, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (21, '编辑参数', 'ParamsUpdate', 'sys_editor_param', '', '', 19, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (22, '删除参数', 'ParamsDelete', 'sys_delete_param', '', '', 19, 3, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (23, '文件管理', 'Files', '', '/system/file/index', 'file', 11, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (24, '上传文件', 'FilesUpload', 'sys_upload_file', '', NULL, 23, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:40', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (25, '删除文件', 'FilesDelete', 'sys_delete_file', '', '', 23, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:40', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (26, '租户管理', 'Tenants', '', '/admin/tenant/index', 'tenant', 1, 4, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (27, '新增租户', 'TenantsAdd', 'sys_add_tenant', '', '', 26, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (28, '编辑租户', 'TenantsUpdate', 'sys_editor_tenant', '', '', 26, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (29, '删除租户', 'TenantsDelete', 'sys_delete_tenant', '', '', 26, 3, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:29:23', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (31, '支付管理', 'Pays', '', '/pay', 'pay', 0, 3, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (32, '聚合支付', 'PaysQrCode', '', '/pay/qrcode/index', 'QrCode', 31, 1, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:16', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (33, '开发管理', 'Devs', '', '/development', 'dev', 0, 4, 0, '', '2021-11-25 16:59:24', '2022-03-14 16:26:49', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (34, '代码生成', 'Generators', '', '/development/generator/index', 'datasource', 33, 1, 0, '', '2021-11-25 16:59:24', '2022-03-14 15:17:17', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (35, '新增数据源', 'DataSourceAdd', 'sys_add_dataSource', '', '', 34, 1, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:17', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (36, '批量生成', 'GeneratorsBatchAdd', 'sys_download_code', '', '', 34, 2, 1, '', '2021-11-25 16:59:24', '2022-03-14 15:17:17', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (37, '接口文档', 'InterfaceDocs', '', 'http://127.0.0.1:15000/doc.html#/home', 'doc', 33, 2, 0, '', '2021-11-25 16:59:24', '2022-03-14 18:02:58', 1, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (38, '系统监控', 'SystemMonitor', '', 'http://127.0.0.1:14000/#/login', 'sentinel', 33, 3, 0, '', '2021-11-25 16:59:24', '2022-03-14 18:02:59', 1, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (58, '图标', 'Icons', NULL, '/icons/index', 'icons', 11, 3, 0, '', '2022-03-16 19:47:34', '2022-03-19 23:51:48', 0, NULL, 1, NULL);
INSERT INTO `sys_menu` VALUES (59, '登录记录', 'LoginLog', NULL, '/system/login-log/index', 'logs', 11, 1, 0, '', '2022-03-19 23:40:20', '2022-03-23 21:30:14', 0, NULL, 1, 1);

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param`  (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
                              `param_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数名称',
                              `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数键名',
                              `param_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '参数键值',
                              `param_type` int(11) NOT NULL DEFAULT 0 COMMENT '0:系统参数  1:业务参数',
                              `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                              `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                              `create_id` int(11) NOT NULL COMMENT '创建者id',
                              `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                              `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0:否 1:是',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `KEY`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES (1, '系统账号默认密码', 'SYS_USER_DEFAULT_PASSWORD', '$2a$10$BL2.s4TnW9Es2kVm5hqvlOvd7aVo0CM1xjvGRDwoTbsJm1gvnlScm', 0, '系统用户默认密码', 1, 1, '2020-03-23 12:00:50', 1, '2021-12-03 10:28:23', 0);
INSERT INTO `sys_param` VALUES (2, '系统账号默认角色', 'SYS_USER_DEFAULT_ROLE', '4', 0, '系统用户默认角色', 2, 1, '2020-03-23 15:06:52', 1, '2021-12-03 10:28:34', 0);
INSERT INTO `sys_param` VALUES (3, '系统账号默认部门', 'SYS_USER_DEFAULT_DEPT', '1', 0, '系统用户默认部门', 3, 1, '2020-03-23 15:07:34', 1, '2021-12-03 10:28:41', 0);
INSERT INTO `sys_param` VALUES (4, '系统默认cdn域名', 'SYS_CDN_DEFAULT_DOMAIN', 'http://cdn.xhuicloud.cn/', 0, '系统默认cdn域名', 4, 1, '2020-05-09 16:46:33', 1, '2022-04-01 20:36:13', 0);
INSERT INTO `sys_param` VALUES (5, '系统默认域名', 'SYS_DEFAULT_DOMAIN', 'http://admin.xhuicloud.com/', 0, '系统默认域名', 5, 1, '2020-06-09 15:51:13', 1, '2022-04-01 20:36:13', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
                             `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色标识',
                             `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
                             `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `create_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 916 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ORDINARY', 'ordinary', '普通用户', '2021-11-25 17:11:56', '2021-11-25 21:21:46', 0, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (2, 'ADMIN', 'ROOT', '系统管理员(勿删)', '2021-11-25 17:11:56', '2021-11-25 21:21:49', 0, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (3, 'TEST', 'Test', '测试专用123', '2021-11-25 17:11:56', '2022-03-16 09:57:02', 0, 0, NULL, 1);
INSERT INTO `sys_role` VALUES (4, 'DEMO', 'Demo', '演示环境角色', '2021-11-25 17:11:56', '2022-03-03 11:16:45', 0, 0, NULL, 1);


-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                  `role_id` int(11) NOT NULL COMMENT '角色id',
                                  `menu_id` int(11) NOT NULL COMMENT '菜单id',
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
INSERT INTO `sys_role_menu` VALUES (1, 37);
INSERT INTO `sys_role_menu` VALUES (1, 38);
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
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 25);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 27);
INSERT INTO `sys_role_menu` VALUES (2, 28);
INSERT INTO `sys_role_menu` VALUES (2, 29);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 35);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 58);
INSERT INTO `sys_role_menu` VALUES (2, 59);
INSERT INTO `sys_role_menu` VALUES (4, 1);
INSERT INTO `sys_role_menu` VALUES (4, 2);
INSERT INTO `sys_role_menu` VALUES (4, 3);
INSERT INTO `sys_role_menu` VALUES (4, 4);
INSERT INTO `sys_role_menu` VALUES (4, 5);
INSERT INTO `sys_role_menu` VALUES (4, 6);
INSERT INTO `sys_role_menu` VALUES (4, 7);
INSERT INTO `sys_role_menu` VALUES (4, 8);
INSERT INTO `sys_role_menu` VALUES (4, 9);
INSERT INTO `sys_role_menu` VALUES (4, 10);
INSERT INTO `sys_role_menu` VALUES (4, 11);
INSERT INTO `sys_role_menu` VALUES (4, 12);
INSERT INTO `sys_role_menu` VALUES (4, 13);
INSERT INTO `sys_role_menu` VALUES (4, 14);
INSERT INTO `sys_role_menu` VALUES (4, 15);
INSERT INTO `sys_role_menu` VALUES (4, 16);
INSERT INTO `sys_role_menu` VALUES (4, 17);
INSERT INTO `sys_role_menu` VALUES (4, 18);
INSERT INTO `sys_role_menu` VALUES (4, 19);
INSERT INTO `sys_role_menu` VALUES (4, 20);
INSERT INTO `sys_role_menu` VALUES (4, 21);
INSERT INTO `sys_role_menu` VALUES (4, 22);
INSERT INTO `sys_role_menu` VALUES (4, 23);
INSERT INTO `sys_role_menu` VALUES (4, 24);
INSERT INTO `sys_role_menu` VALUES (4, 25);
INSERT INTO `sys_role_menu` VALUES (4, 26);
INSERT INTO `sys_role_menu` VALUES (4, 27);
INSERT INTO `sys_role_menu` VALUES (4, 28);
INSERT INTO `sys_role_menu` VALUES (4, 29);
INSERT INTO `sys_role_menu` VALUES (4, 31);
INSERT INTO `sys_role_menu` VALUES (4, 32);
INSERT INTO `sys_role_menu` VALUES (4, 33);
INSERT INTO `sys_role_menu` VALUES (4, 34);
INSERT INTO `sys_role_menu` VALUES (4, 35);
INSERT INTO `sys_role_menu` VALUES (4, 36);
INSERT INTO `sys_role_menu` VALUES (4, 58);
INSERT INTO `sys_role_menu` VALUES (4, 59);

-- ----------------------------
-- Table structure for sys_route_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_route_conf`;
CREATE TABLE `sys_route_conf`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `route_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由名称',
                                   `route_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由id',
                                   `predicates` json NULL COMMENT '谓词/断言',
                                   `filters` json NULL COMMENT '过滤器',
                                   `uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                   `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
                                   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                                   `create_id` int(11) NOT NULL COMMENT '创建者id',
                                   `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全局路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_route_conf
-- ----------------------------
INSERT INTO `sys_route_conf` VALUES (1, '认证中心', 'XHuiCloud-auth', '[{\"args\": {\"_genkey_0\": \"/auth/**\"}, \"name\": \"Path\"}]', '[{\"args\": {}, \"name\": \"CodeGatewayFilter\"}]', 'lb://XHuiCloud-auth', 1, '2021-11-25 21:05:43', NULL, 0, 0, NULL);
INSERT INTO `sys_route_conf` VALUES (2, '用户中心', 'XHuiCloud-upms-service', '[{\"args\": {\"_genkey_0\": \"/admin/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-upms-service', 2, '2021-11-25 21:05:43', NULL, 0, 0, NULL);
INSERT INTO `sys_route_conf` VALUES (3, '支付中心', 'XHuiCloud-pay-business', '[{\"args\": {\"_genkey_0\": \"/pay/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-pay-business', 3, '2021-11-25 21:05:43', NULL, 0, 0, NULL);
INSERT INTO `sys_route_conf` VALUES (4, '开发管理', 'XHuiCloud-generator', '[{\"args\": {\"_genkey_0\": \"/dev/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-generator', 4, '2021-11-25 21:05:43', NULL, 0, 0, NULL);
INSERT INTO `sys_route_conf` VALUES (5, '推送中心', 'XHuiCloud-push-service', '[{\"args\": {\"_genkey_0\": \"/push/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-push-service', 5, '2021-12-07 16:45:44', '2021-12-07 16:51:11', 0, 0, NULL);
INSERT INTO `sys_route_conf` VALUES (6, '日志中心', 'XHuiCloud-logs-service', '[{\"args\": {\"_genkey_0\": \"/logs/**\"}, \"name\": \"Path\"}]', '[]', 'lb://XHuiCloud-logs-service', 6, '2021-12-07 16:45:44', '2021-12-07 16:51:11', 0, 0, NULL);

-- ----------------------------
-- Table structure for sys_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_social`;
CREATE TABLE `sys_social`  (
                               `id` int(11) NOT NULL COMMENT 'id',
                               `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
                               `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台id',
                               `app_ secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台密钥',
                               `app_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开放平台描述',
                               `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向url',
                               `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                               `create_id` int(11) NOT NULL COMMENT '创建者id',
                               `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统开放平台' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '租户id',
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户名称',
                               `state` int(11) NULL DEFAULT 2 COMMENT '状态\n0:禁用; 1:正常;  2:待审核;  3:拒绝',
                               `expiration_time` datetime NULL DEFAULT NULL COMMENT '有效期',
                               `logo` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'logo地址',
                               `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '租户描述',
                               `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                               `create_id` int(11) NOT NULL COMMENT '创建者id',
                               `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '星辉云', 1, '2027-03-29 18:12:28', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.bq233.com%2Fkanqq%2Fpic%2Fupload%2F2018%2F0807%2F1533622762937587.jpg&refer=http%3A%2F%2Fimg.bq233.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1643242439&t=f2eba74c2b362625321525d5b426685d', NULL, '2020-05-11 18:13:31', '2021-12-28 08:14:09', 0, 0, NULL);
INSERT INTO `sys_tenant` VALUES (2, '测试', 1, '2023-05-25 18:13:03', '876780536b8d4513850d5e1ebc052fc2.jpg', NULL, '2020-05-13 16:00:59', '2021-12-23 16:08:06', 0, 0, NULL);
INSERT INTO `sys_tenant` VALUES (3, '分公司', 1, '2029-09-25 18:13:08', '932235ea685946fb963e4046243526fd.jpg', NULL, '2020-05-13 16:02:26', '2022-04-01 14:00:32', 0, 0, NULL);
INSERT INTO `sys_tenant` VALUES (4, '广东公司', 1, '2021-05-21 00:00:00', '7d5cb2b6c60b42a3a78592d434bd5dce.jpg', NULL, '2020-05-25 18:18:38', '2022-04-01 14:00:32', 0, 0, NULL);
INSERT INTO `sys_tenant` VALUES (5, '测试租户1', 1, '2029-12-06 00:00:00', '8b9b2db0a67445c5b55428bf122ccaef.jpg', '测试租户', '2020-05-29 16:38:52', '2021-12-23 16:08:06', 0, 0, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                             `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
                             `avatar` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
                             `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
                             `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `sex` int(11) NULL DEFAULT 1 COMMENT '性别:0 女、1  男、2  其他',
                             `lock_flag` tinyint(1) NULL DEFAULT 1 COMMENT '0: 否 1：是',
                             `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                             `create_id` int(11) NULL DEFAULT NULL COMMENT '创建者id',
                             `update_id` int(11) NULL DEFAULT NULL COMMENT '更新者id',
                             PRIMARY KEY (`user_id`) USING BTREE,
                             UNIQUE INDEX `username`(`username`) USING BTREE,
                             UNIQUE INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '{bcrypt}$2a$10$n2IGDxenvMUj0IJQo/104eHtkL3kLAVjRs/0ktT.JWsG4rRQYqIKC', 'https://img0.baidu.com/it/u=1056811702,4111096278&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '13333323333', 'sindazeng@gmail.com', 1, 0, 1, '2021-11-25 21:06:29', '2022-03-25 23:09:49', 0, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
                                  `user_id` int(11) NOT NULL,
                                  `dept_id` int(11) NOT NULL,
                                  PRIMARY KEY (`user_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` int(11) NOT NULL COMMENT '用户id',
                                  `role_id` int(11) NOT NULL COMMENT '角色id',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 2);

-- ----------------------------
-- Table structure for sys_user_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_social`;
CREATE TABLE `sys_user_social`  (
                                    `user_id` int(11) NOT NULL COMMENT '用户id',
                                    `social_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '开放平台类型',
                                    `user_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户openid',
                                    PRIMARY KEY (`user_id`, `user_openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户社交' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `branch_id` bigint(20) NOT NULL,
                             `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                             `rollback_info` longblob NOT NULL,
                             `log_status` int(11) NOT NULL,
                             `log_created` datetime NOT NULL,
                             `log_modified` datetime NOT NULL,
                             `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
