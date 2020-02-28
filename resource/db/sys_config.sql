/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : sys_config

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 28/02/2020 17:13:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (3, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2020-02-12 21:05:30', '2020-02-12 21:05:48', NULL, '192.168.0.103', '', '', 'null', 'null', 'null', 'null', 'null');
INSERT INTO `config_info` VALUES (4, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (5, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (6, 'transport.enable-client-batch-send-request', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (7, 'transport.thread-factory.boss-thread-prefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (8, 'transport.thread-factory.worker-thread-prefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (9, 'transport.thread-factory.server-executor-thread-prefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (10, 'transport.thread-factory.share-boss-worker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (11, 'transport.thread-factory.client-selector-thread-prefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (12, 'transport.thread-factory.client-selector-thread-size', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (13, 'transport.thread-factory.client-worker-thread-prefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (14, 'transport.thread-factory.boss-thread-size', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (15, 'transport.thread-factory.worker-thread-size', 'SEATA_GROUP', '8', 'c9f0f895fb98ab9159f51fd0297e236d', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (16, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (17, 'service.vgroup_mapping.fdp_tx_group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (18, 'service.default.grouplist', 'SEATA_GROUP', '127.0.0.1:8091', 'c32ce0d3e264525dcdada751f98143a3', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (19, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (20, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (21, 'client.rm.async.commit.buffer.limit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (22, 'client.rm.lock.retry.internal', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (23, 'client.rm.lock.retry.times', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (24, 'client.rm.report.retry.count', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (25, 'client.rm.lock.retry.policy.branch-rollback-on-conflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (26, 'client.rm.table.meta.check.enable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (27, 'client.rm.report.success.enable', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (28, 'client.tm.commit.retry.count', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (29, 'client.tm.rollback.retry.count', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (30, 'store.mode', 'SEATA_GROUP', 'db', 'd77d5e503ad1439f585ac494268b351b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (31, 'store.file.dir', 'SEATA_GROUP', 'file_store/data', '6a8dec07c44c33a8a9247cba5710bbb2', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (32, 'store.file.max-branch-session-size', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (33, 'store.file.max-global-session-size', 'SEATA_GROUP', '512', '10a7cdd970fe135cf4f7bb55c0e3b59f', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (34, 'store.file.file-write-buffer-cache-size', 'SEATA_GROUP', '16384', 'c76fe1d8e08462434d800487585be217', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (35, 'store.file.flush-disk-mode', 'SEATA_GROUP', 'async', '0df93e34273b367bb63bad28c94c78d5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (36, 'store.file.session.reload.read_size', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (37, 'store.db.datasource', 'SEATA_GROUP', 'dbcp', '3a9f384fb40c59fbdc67024ee97d94b1', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (38, 'store.db.db-type', 'SEATA_GROUP', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (39, 'store.db.driver-class-name', 'SEATA_GROUP', 'com.mysql.jdbc.Driver', '683cf0c3a5a56cec94dfac94ca16d760', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (40, 'store.db.url', 'SEATA_GROUP', 'jdbc:mysql://127.0.0.1:3306/sys_seata?useUnicode=true', '922a27e1cf456b99d1a16193e80ae2c5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (41, 'store.db.user', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (42, 'store.db.password', 'SEATA_GROUP', 'root', '63a9f0ea7bb98050796b649e85481845', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (43, 'store.db.min-conn', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (44, 'store.db.max-conn', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (45, 'store.db.global.table', 'SEATA_GROUP', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (46, 'store.db.branch.table', 'SEATA_GROUP', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (47, 'store.db.query-limit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (48, 'store.db.lock-table', 'SEATA_GROUP', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (49, 'server.recovery.committing-retry-period', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (50, 'server.recovery.asyn-committing-retry-period', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (51, 'server.recovery.rollbacking-retry-period', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (52, 'server.recovery.timeout-retry-period', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (53, 'server.max.commit.retry.timeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (54, 'server.max.rollback.retry.timeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (55, 'client.undo.data.validation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (56, 'client.undo.log.serialization', 'SEATA_GROUP', 'jackson', 'b41779690b83f182acc67d6388c7bac9', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (57, 'server.undo.log.save.days', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (58, 'server.undo.log.delete.period', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (59, 'client.undo.log.table', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (60, 'client.log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2020-02-12 21:05:30', '2020-02-12 21:05:30', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (61, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (62, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (63, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (64, 'metrics.registry-type', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (65, 'metrics.exporter-list', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (66, 'metrics.exporter-prometheus-port', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (67, 'client.support.spring.datasource.autoproxy', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2020-02-12 21:05:31', '2020-02-12 21:05:31', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `config_info` VALUES (200, 'application-common.yml', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n  main:\n    allow-bean-definition-overriding: true\n\nseata:\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  config:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  tx-service-group: fdp_tx_group\n  service:\n    disable-global-transaction: false\n    \nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://localhost:9411/\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\nlogging:\n  file:\n    path: /Users/cengxinda/workspace/sourceCode/FDPlatform/logs\n    name: ${logging.file.path}/${spring.application.name}/root.log\n\nsecurity:\n  oauth2:\n    client:\n      access-token-uri: http://localhost:15000/FDP-auth/oauth/token  #请求令牌的地址\n      user-authorization-uri: http://localhost:15000/FDP-auth/oauth/authorize #对用户授权的地址\n      ignore-urls:\n        - /druid/**\n    resource:\n      loadBalanced: true\n      token-info-uri: http://localhost:15000/FDP-auth/oauth/check_token #检查token是否有效的地址\nswagger:\n  title: FDP\n  description: 快速开发平台\n  license: Powered By Sinda\n  license-url: http://www.zsinda.cn/\n  terms-of-service-url: http://www.zsinda.cn/\n  contact:\n    name: Sinda\n    email: sindazeng@gmail.com\n    url: https://github.com/sindaZeng\n  authorization:\n    name: default OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - ${security.oauth2.client.access-token-uri}', 'a26dda3b7561613a5a87b721077c1b2d', '2020-01-28 20:04:52', '2020-02-21 14:55:39', NULL, '192.168.50.2', '', '', '公共通用配置文件\n', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (201, 'FDP-upmm-business.yml', 'DEFAULT_GROUP', 'spring:\n  # datasources:\n  #   #       数据库 1\n  #   first:\n  #     url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n  #     username: root\n  #     password: root\n  #     driver-class-name: com.mysql.cj.jdbc.Driver\n  #     initialSize: 5\n  #     minIdle: 5\n  #     maxActive: 20\n datasource:\n   type: com.alibaba.druid.pool.DruidDataSource\n   druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n      username: root\n      password: root\nserver:\n  port: 17000\n', 'ecc706db614f5dff745c3b44f02c7546', '2020-01-29 00:12:43', '2020-02-13 22:39:16', NULL, '192.168.0.103', '', '', '用户管理', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (202, 'FDP-auth.yml', 'DEFAULT_GROUP', 'server:\n  port: 16000\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      username: root\n      password: root\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', 'c50e9513132b1c9ed97c84ed454d44f3', '2020-02-01 11:42:05', '2020-02-13 22:29:51', NULL, '192.168.0.103', '', '', '认证中心配置', 'null', 'null', 'yaml', 'null');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
INSERT INTO `his_config_info` VALUES (0, 1, '21312', 'DEFAULT_GROUP', '', '333', '310dcbbf4cce62f762a2aaa148d556bd', '2010-05-05 00:00:00', '2020-02-12 21:04:57', NULL, '192.168.0.103', 'I', '');
INSERT INTO `his_config_info` VALUES (1, 2, '21312', 'DEFAULT_GROUP', '', '333', '310dcbbf4cce62f762a2aaa148d556bd', '2010-05-05 00:00:00', '2020-02-12 21:05:14', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (0, 3, 'transport.type', 'SEATA_GROUP', '', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 4, 'transport.server', 'SEATA_GROUP', '', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 5, 'transport.heartbeat', 'SEATA_GROUP', '', 'true', 'b326b5062b2f0e69046810717534cb09', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 6, 'transport.enable-client-batch-send-request', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 7, 'transport.thread-factory.boss-thread-prefix', 'SEATA_GROUP', '', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 8, 'transport.thread-factory.worker-thread-prefix', 'SEATA_GROUP', '', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 9, 'transport.thread-factory.server-executor-thread-prefix', 'SEATA_GROUP', '', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 10, 'transport.thread-factory.share-boss-worker', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 11, 'transport.thread-factory.client-selector-thread-prefix', 'SEATA_GROUP', '', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 12, 'transport.thread-factory.client-selector-thread-size', 'SEATA_GROUP', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 13, 'transport.thread-factory.client-worker-thread-prefix', 'SEATA_GROUP', '', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 14, 'transport.thread-factory.boss-thread-size', 'SEATA_GROUP', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 15, 'transport.thread-factory.worker-thread-size', 'SEATA_GROUP', '', '8', 'c9f0f895fb98ab9159f51fd0297e236d', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 16, 'transport.shutdown.wait', 'SEATA_GROUP', '', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 17, 'service.vgroup_mapping.fdp_tx_group', 'SEATA_GROUP', '', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 18, 'service.default.grouplist', 'SEATA_GROUP', '', '127.0.0.1:8091', 'c32ce0d3e264525dcdada751f98143a3', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 19, 'service.enableDegrade', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 20, 'service.disableGlobalTransaction', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 21, 'client.rm.async.commit.buffer.limit', 'SEATA_GROUP', '', '10000', 'b7a782741f667201b54880c925faec4b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 22, 'client.rm.lock.retry.internal', 'SEATA_GROUP', '', '10', 'd3d9446802a44259755d38e6d163e820', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 23, 'client.rm.lock.retry.times', 'SEATA_GROUP', '', '30', '34173cb38f07f89ddbebc2ac9128303f', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 24, 'client.rm.report.retry.count', 'SEATA_GROUP', '', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 25, 'client.rm.lock.retry.policy.branch-rollback-on-conflict', 'SEATA_GROUP', '', 'true', 'b326b5062b2f0e69046810717534cb09', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 26, 'client.rm.table.meta.check.enable', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 27, 'client.rm.report.success.enable', 'SEATA_GROUP', '', 'true', 'b326b5062b2f0e69046810717534cb09', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 28, 'client.tm.commit.retry.count', 'SEATA_GROUP', '', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 29, 'client.tm.rollback.retry.count', 'SEATA_GROUP', '', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 30, 'store.mode', 'SEATA_GROUP', '', 'db', 'd77d5e503ad1439f585ac494268b351b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 31, 'store.file.dir', 'SEATA_GROUP', '', 'file_store/data', '6a8dec07c44c33a8a9247cba5710bbb2', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 32, 'store.file.max-branch-session-size', 'SEATA_GROUP', '', '16384', 'c76fe1d8e08462434d800487585be217', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 33, 'store.file.max-global-session-size', 'SEATA_GROUP', '', '512', '10a7cdd970fe135cf4f7bb55c0e3b59f', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 34, 'store.file.file-write-buffer-cache-size', 'SEATA_GROUP', '', '16384', 'c76fe1d8e08462434d800487585be217', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 35, 'store.file.flush-disk-mode', 'SEATA_GROUP', '', 'async', '0df93e34273b367bb63bad28c94c78d5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 36, 'store.file.session.reload.read_size', 'SEATA_GROUP', '', '100', 'f899139df5e1059396431415e770c6dd', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 37, 'store.db.datasource', 'SEATA_GROUP', '', 'dbcp', '3a9f384fb40c59fbdc67024ee97d94b1', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 38, 'store.db.db-type', 'SEATA_GROUP', '', 'mysql', '81c3b080dad537de7e10e0987a4bf52e', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 39, 'store.db.driver-class-name', 'SEATA_GROUP', '', 'com.mysql.jdbc.Driver', '683cf0c3a5a56cec94dfac94ca16d760', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 40, 'store.db.url', 'SEATA_GROUP', '', 'jdbc:mysql://127.0.0.1:3306/sys_seata?useUnicode=true', '922a27e1cf456b99d1a16193e80ae2c5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 41, 'store.db.user', 'SEATA_GROUP', '', 'root', '63a9f0ea7bb98050796b649e85481845', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 42, 'store.db.password', 'SEATA_GROUP', '', 'root', '63a9f0ea7bb98050796b649e85481845', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 43, 'store.db.min-conn', 'SEATA_GROUP', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 44, 'store.db.max-conn', 'SEATA_GROUP', '', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 45, 'store.db.global.table', 'SEATA_GROUP', '', 'global_table', '8b28fb6bb4c4f984df2709381f8eba2b', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 46, 'store.db.branch.table', 'SEATA_GROUP', '', 'branch_table', '54bcdac38cf62e103fe115bcf46a660c', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 47, 'store.db.query-limit', 'SEATA_GROUP', '', '100', 'f899139df5e1059396431415e770c6dd', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 48, 'store.db.lock-table', 'SEATA_GROUP', '', 'lock_table', '55e0cae3b6dc6696b768db90098b8f2f', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 49, 'server.recovery.committing-retry-period', 'SEATA_GROUP', '', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 50, 'server.recovery.asyn-committing-retry-period', 'SEATA_GROUP', '', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 51, 'server.recovery.rollbacking-retry-period', 'SEATA_GROUP', '', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 52, 'server.recovery.timeout-retry-period', 'SEATA_GROUP', '', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 53, 'server.max.commit.retry.timeout', 'SEATA_GROUP', '', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 54, 'server.max.rollback.retry.timeout', 'SEATA_GROUP', '', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 55, 'client.undo.data.validation', 'SEATA_GROUP', '', 'true', 'b326b5062b2f0e69046810717534cb09', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 56, 'client.undo.log.serialization', 'SEATA_GROUP', '', 'jackson', 'b41779690b83f182acc67d6388c7bac9', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 57, 'server.undo.log.save.days', 'SEATA_GROUP', '', '7', '8f14e45fceea167a5a36dedd4bea2543', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 58, 'server.undo.log.delete.period', 'SEATA_GROUP', '', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 59, 'client.undo.log.table', 'SEATA_GROUP', '', 'undo_log', '2842d229c24afe9e61437135e8306614', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 60, 'client.log.exceptionRate', 'SEATA_GROUP', '', '100', 'f899139df5e1059396431415e770c6dd', '2010-05-05 00:00:00', '2020-02-12 21:05:30', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 61, 'transport.serialization', 'SEATA_GROUP', '', 'seata', 'b943081c423b9a5416a706524ee05d40', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 62, 'transport.compressor', 'SEATA_GROUP', '', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 63, 'metrics.enabled', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 64, 'metrics.registry-type', 'SEATA_GROUP', '', 'compact', '7cf74ca49c304df8150205fc915cd465', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 65, 'metrics.exporter-list', 'SEATA_GROUP', '', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 66, 'metrics.exporter-prometheus-port', 'SEATA_GROUP', '', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (0, 67, 'client.support.spring.datasource.autoproxy', 'SEATA_GROUP', '', 'false', '68934a3e9455fa72420237eb05902327', '2010-05-05 00:00:00', '2020-02-12 21:05:31', NULL, '127.0.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (1, 68, '21312', 'DEFAULT_GROUP', '', '333222', 'ed67c4f2317154944d7244a5c5e384f9', '2010-05-05 00:00:00', '2020-02-12 21:05:38', NULL, '192.168.0.103', 'D', '');
INSERT INTO `his_config_info` VALUES (3, 69, 'transport.type', 'SEATA_GROUP', '', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2010-05-05 00:00:00', '2020-02-12 21:05:48', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (200, 70, 'application-common.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    sentinel:\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n  main:\n    allow-bean-definition-overriding: true\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)', '67709b5fabc6a968a2a4a424d57c5f12', '2010-05-05 00:00:00', '2020-02-12 21:07:32', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (200, 71, 'application-common.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    sentinel:\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n  main:\n    allow-bean-definition-overriding: true\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0) ', '3809e2cafcd75cfe99ca329c77209e31', '2010-05-05 00:00:00', '2020-02-13 21:29:27', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 72, 'FDP-auth-dev.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\n', 'ef40021c1776ed90de14d34193a2d2aa', '2010-05-05 00:00:00', '2020-02-13 21:32:11', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 73, 'FDP-auth.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    platform: mysql\n    url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    validationQuery: SELECT 1\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    filters: stat,wall,log4j\n    session:\n      timeout: 2592000\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', '965c829e1c59cd8a0fc5c60c8e5818b8', '2010-05-05 00:00:00', '2020-02-13 21:56:04', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 74, 'FDP-auth.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\nspring:\n  datasources:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    platform: mysql\n    url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    validationQuery: SELECT 1\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    filters: stat,wall,log4j\n    session:\n      timeout: 2592000\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', '6a485bbc2864e36abc1a3b4b30284de1', '2010-05-05 00:00:00', '2020-02-13 21:56:44', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 75, 'FDP-auth.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    platform: mysql\n    url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: root\n    initialSize: 5\n    minIdle: 5\n    maxActive: 20\n    maxWait: 60000\n    timeBetweenEvictionRunsMillis: 60000\n    minEvictableIdleTimeMillis: 300000\n    validationQuery: SELECT 1\n    testWhileIdle: true\n    testOnBorrow: false\n    testOnReturn: false\n    filters: stat,wall,log4j\n    session:\n      timeout: 2592000\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', '965c829e1c59cd8a0fc5c60c8e5818b8', '2010-05-05 00:00:00', '2020-02-13 22:21:03', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 76, 'FDP-auth.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\nspring:\n  datasource:\n    druid:\n      type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      platform: mysql\n      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      username: root\n      password: root\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n      maxWait: 60000\n      timeBetweenEvictionRunsMillis: 60000\n      minEvictableIdleTimeMillis: 300000\n      validationQuery: SELECT 1\n      testWhileIdle: true\n      testOnBorrow: false\n      testOnReturn: false\n      filters: stat,wall,log4j\n    session:\n      timeout: 2592000\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', 'ea6150a67e778dba8aa1d1554dbf7917', '2010-05-05 00:00:00', '2020-02-13 22:26:21', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (202, 77, 'FDP-auth.yml', 'DEFAULT_GROUP', '', 'server:\n  port: 16000\nspring:\n  datasource:\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      platform: mysql\n      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      username: root\n      password: root\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n      maxWait: 60000\n      timeBetweenEvictionRunsMillis: 60000\n      minEvictableIdleTimeMillis: 300000\n      validationQuery: SELECT 1\n      testWhileIdle: true\n      testOnBorrow: false\n      testOnReturn: false\n    session:\n      timeout: 2592000\n#  datasources:\n#    first:\n#      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#      username: root\n#      password: root\n#      driver-class-name: com.mysql.cj.jdbc.Driver\n#      initialSize: 5\n#      minIdle: 5\n#      maxActive: 20\n', '8d019b8ea0156ebf43ebd1c6cbe52d0d', '2010-05-05 00:00:00', '2020-02-13 22:29:51', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (201, 78, 'FDP-upmm-business.yml', 'DEFAULT_GROUP', '', 'spring:\n  datasources:\n    #       数据库 1\n    first:\n      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n      username: root\n      password: root\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      initialSize: 5\n      minIdle: 5\n      maxActive: 20\n#  datasource:\n#    type: com.alibaba.druid.pool.DruidDataSource\n#    driver-class-name: com.mysql.cj.jdbc.Driver\n#    platform: mysql\n#    url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n#    username: root\n#    password: root\n#    initialSize: 5\n#    minIdle: 5\n#    maxActive: 20\n#    maxWait: 60000\n#    timeBetweenEvictionRunsMillis: 60000\n#    minEvictableIdleTimeMillis: 300000\n#    validationQuery: SELECT 1\n#    testWhileIdle: true\n#    testOnBorrow: false\n#    testOnReturn: false\n#    filters: stat,wall,log4j\n  session:\n    timeout: 2592000\nserver:\n  port: 17000\n', '7799400cc286d11af94a0923e9a1ac18', '2010-05-05 00:00:00', '2020-02-13 22:35:28', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (201, 79, 'FDP-upmm-business.yml', 'DEFAULT_GROUP', '', 'spring:\n  # datasources:\n  #   #       数据库 1\n  #   first:\n  #     url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n  #     username: root\n  #     password: root\n  #     driver-class-name: com.mysql.cj.jdbc.Driver\n  #     initialSize: 5\n  #     minIdle: 5\n  #     maxActive: 20\n datasource:\n   type: com.alibaba.druid.pool.DruidDataSource\n   druid:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n   url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true\n   username: root\n   password: root\nserver:\n  port: 17000\n', 'c4d9f2f23165e488d5f7d8cabe7790c7', '2010-05-05 00:00:00', '2020-02-13 22:39:16', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (200, 80, 'application-common.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n    alibaba:\n      seata:\n        tx-service-group: fdp_tx_group\n  main:\n    allow-bean-definition-overriding: true\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://localhost:9411/\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\nlogging:\n  file:\n    path: /Users/cengxinda/workspace/sourceCode/FDPlatform/logs\n    name: ${logging.file.path}/${spring.application.name}/root.log', 'e6d007f5310bbdd7422eff6c805ff778', '2010-05-05 00:00:00', '2020-02-13 23:17:28', NULL, '192.168.0.103', 'U', '');
INSERT INTO `his_config_info` VALUES (200, 81, 'application-common.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n  main:\n    allow-bean-definition-overriding: true\n\nseata:\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  config:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  tx-service-group: fdp_tx_group\n  service:\n    disable-global-transaction: false\n    \nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://localhost:9411/\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\nlogging:\n  file:\n    path: /Users/cengxinda/workspace/sourceCode/FDPlatform/logs\n    name: ${logging.file.path}/${spring.application.name}/root.log', '70429da8aa41f0ed166c515ffe4969ca', '2010-05-05 00:00:00', '2020-02-21 14:55:13', NULL, '192.168.50.2', 'U', '');
INSERT INTO `his_config_info` VALUES (200, 82, 'application-common.yml', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\n    nacos:\n      discovery:\n        #nacos 地址\n        server-addr: ${nacos.address}\n  main:\n    allow-bean-definition-overriding: true\n\nseata:\n  registry:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  config:\n    type: nacos\n    nacos:\n      server-addr: ${nacos.address}\n  tx-service-group: fdp_tx_group\n  service:\n    disable-global-transaction: false\n    \nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\nfeign:\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  sentinel:\n    #feign开启sentinle监控\n    enabled: true\n    \nmybatis-plus:\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。\n      logic-delete-value: 0 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://localhost:9411/\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\nlogging:\n  file:\n    path: /Users/cengxinda/workspace/sourceCode/FDPlatform/logs\n    name: ${logging.file.path}/${spring.application.name}/root.log\n\nsecurity:\n  oauth2:\n    client:\n      access-token-uri: http://localhost:15000/FDP-auth/oauth/token  #请求令牌的地址\n      user-authorization-uri: http://localhost:15000/FDP-auth/oauth/authorize #对用户授权的地址\n      ignore-urls:\n        - /druid/**\n    resource:\n      loadBalanced: true\n      token-info-uri: http://localhost:15000/FDP-auth/oauth/check_token #检查token是否有效的地址', '84643edfcebd5b5a8cd854375a36737b', '2010-05-05 00:00:00', '2020-02-21 14:55:39', NULL, '192.168.50.2', 'U', '');
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
