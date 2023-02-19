USE xhuicloud_wechat;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wechat_account
-- ----------------------------
DROP TABLE IF EXISTS `wechat_account`;
CREATE TABLE `wechat_account`  (
                                   `id` bigint(20) NOT NULL COMMENT 'id',
                                   `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公众号名称',
                                   `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号头像',
                                   `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公众号AppId',
                                   `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公众号secret',
                                   `app_auth_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '平台认证token',
                                   `app_decrypt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '平台解密密钥',
                                   `type` tinyint(1) NULL DEFAULT 0 COMMENT '0: 订阅号 1: 服务号',
                                   `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向url',
                                   `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
                                   `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
                                   `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
                                   `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公众号账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for wechat_menus
-- ----------------------------
DROP TABLE IF EXISTS `wechat_menus`;
CREATE TABLE `wechat_menus`  (
                                 `id` bigint(20) NOT NULL COMMENT 'id',
                                 `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公众号AppId',
                                 `account_id` bigint(20) NOT NULL COMMENT '账号id',
                                 `menu` json NULL COMMENT '菜单',
                                 `is_release` tinyint(1) NULL DEFAULT 0 COMMENT '是否发布 0: 否 1：是',
                                 `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
                                 `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
                                 `tenant_id` bigint(20) NULL DEFAULT NULL COMMENT '租户id',
                                 `is_del` tinyint(1) NULL DEFAULT 0 COMMENT '0: 否 1：是',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公众号菜单' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;