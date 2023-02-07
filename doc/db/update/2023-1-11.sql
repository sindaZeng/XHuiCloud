ALTER TABLE `wechat_account`
    ADD COLUMN `url` varchar(255) NULL COMMENT '公众号头像' AFTER `name`;

DROP TABLE IF EXISTS `sys_log`;

DROP TABLE IF EXISTS `sys_log_login`;

CREATE
DATABASE `xhuicloud_audit` DEFAULT CHARACTER SET UTF8MB4 COLLATE UTF8MB4_GENERAL_CI;

CREATE TABLE `audit_login`
(
    `id`         int                                                          NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `user_id`    int NULL DEFAULT NULL COMMENT '用户id',
    `login_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
    `ip`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录ip',
    `useragent`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器信息',
    `status`     tinyint                                                      NOT NULL COMMENT '0:成功 1:失败',
    `remake`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `audit_record`
(
    `id`             bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
    `req_id`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求id',
    `request_ip`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作IP',
    `service_system` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '业务系统',
    `operator`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人(id)',
    `detail`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作描述',
    `class_path`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类路径',
    `request_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方法',
    `request_uri`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求地址',
    `http_method`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'GET' COMMENT '请求类型 {GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;PATCH:PATCH请求;TRACE:TRACE请求;HEAD:HEAD请求;OPTIONS:OPTIONS请求;}',
    `params`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
    `result`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回值',
    `status`         tinyint NULL DEFAULT NULL COMMENT '0: 失败 1: 成功',
    `error_msg`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '异常详情信息',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `xhuicloud_sys`.`sys_menu` (`id`, `name`, `internationalization`, `permission`, `path`, `url`, `icon`, `parent_id`, `sort`, `type`, `redirect`, `create_time`, `update_time`, `is_del`, `tenant_id`, `create_id`, `update_id`) VALUES (99, '审计查看', 'Audit', NULL, '/audit', NULL, 'audit', 0, 3, 0, '', '2023-02-07 14:23:46', NULL, 0, NULL, 1, NULL);

UPDATE `xhuicloud_sys`.`sys_menu` SET `parent_id` = 99 WHERE `id` = 59
UPDATE `xhuicloud_sys`.`sys_menu` SET `path` = '/audit/log/login' WHERE `id` = 59

UPDATE `xhuicloud_sys`.`sys_menu` SET `parent_id` = 99 WHERE `id` = 73
UPDATE `xhuicloud_sys`.`sys_menu` SET `path` = '/audit/log/login' WHERE `id` = 73