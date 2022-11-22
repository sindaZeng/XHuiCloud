/*
 Navicat Premium Data Transfer

 Source Server         : prod
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 14.29.219.82:3306
 Source Schema         : xhuicloud_push

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 22/11/2022 17:20:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for push_group
-- ----------------------------
DROP TABLE IF EXISTS `push_group`;
CREATE TABLE `push_group`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `template_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NOT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '推送组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of push_group
-- ----------------------------
INSERT INTO `push_group` VALUES (1, 'LOGIN_SUCCESS', '登录成功', '2021-12-22 15:39:58', 1, '2021-12-22 15:40:02', NULL, 1, 0);

-- ----------------------------
-- Table structure for push_template
-- ----------------------------
DROP TABLE IF EXISTS `push_template`;
CREATE TABLE `push_template`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int NULL DEFAULT NULL COMMENT '组id',
  `channel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道',
  `channel_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道模板id',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0:正常 1:停用',
  `kv` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key:第三方参数名。value:渠道值对应参数名',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_id` int NULL DEFAULT NULL COMMENT '更新者id',
  `tenant_id` int NULL DEFAULT NULL COMMENT '租户id',
  `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of push_template
-- ----------------------------
INSERT INTO `push_template` VALUES (4, 1, 'WECHAT_MP', 'ZJBTvt518iJwAK3i-8g0kmoGY9WwN0ol-kX5vn2YITw', '{first.DATA}登录账号：{keyword1.DATA}登录APP：{keyword2.DATA}登录时间：{keyword3.DATA}登录地点：{keyword4.DATA}{remark.DATA}', 0, '{\"keyword3\":\"keyword3.DATA\",\"first\":\"first.DATA\",\"keyword1\":\"keyword1.DATA\",\"remark\":\"remark.DATA\",\"keyword4\":\"keyword4.DATA\",\"keyword2\":\"keyword2.DATA\"}', NULL, '2021-12-16 16:18:51', NULL, '2021-12-23 23:16:37', NULL, 1, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
