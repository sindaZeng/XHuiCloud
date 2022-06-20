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

USE xhuicloud_register;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11231231231502 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'application-common-dev.yml', 'DEFAULT_GROUP', 'spring:\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://${MYSQL_HOST:xhuicloud-mysql}:${MYSQL_PORT:3306}/${mysql.scheme:\'\'}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      username: ${MYSQL_USER:root}\n      password: ${MYSQL_PASSWORD:root}\n      stat-view-servlet:\n        enabled: true\n        allow: \"\"\n        url-pattern: /druid/*\n      filter:\n        stat:\n          enabled: true\n          log-slow-sql: true\n          slow-sql-millis: 10000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n\n  redis:\n    host: ${REDIS_HOST:xhuicloud-redis}\n    password: ${REDIS_PASSWORD:root}\n   # username: ${REDIS_USER:root}\n    timeout: 10000\n  rabbitmq:\n    host : ${MQ_HOST:xhuicloud-mq}\n    port: ${MQ_PORT:5672}\n    username: ${MQ_USER:root} \n    password: ${MQ_PASSWORD:root}\n    virtual-host: ${MQ_VHOST:xhuicloud}\n  cloud:\n    alibaba:\n      seata:\n        tx-service-group: xhui_tx_group\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: localhost:14000\n        #监控此服务端口\n        port: ${server.port}\ngray:\n  enabled: true\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'          \n  endpoint:\n    health:\n      show-details: ALWAYS\n\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 20000\n        readTimeout: 20000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n\nmybatis-plus:\n  tenant-enable: ture\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  configuration:\n    jdbc-type-for-null: null\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: isDel\n      logic-delete-value: 1\n      logic-not-delete-value: 0\n      db-type: oracle\n      id-type: auto\n      select-strategy: not_empty\n      insert-strategy: not_empty\n      update-strategy: not_empty\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://localhost:9411/\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\n#xhuicloud:\n#  zero:\n#    snowflake:\n#      zk-address: 127.0.0.1\n#      port: 19999\n\nlogging:\n  level:\n    com.xhuicloud.*.mapper: debug\n  # path: /Users/cengxinda/workspace/sourceCode/XHuiCloud/logs\n  # name: ${logging.file.path}/${spring.application.name}/root.log\n  config: classpath:com/xhuicloud/common/log/logback/defaults-log.xml\nsecurity:\n  oauth2:\n    client:\n      ignore-urls:\n        - /error\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://XHuiCloud-gateway:15000/auth/oauth/check_token #检查token是否有效的地址\n# swagger 全局配置\nswagger:\n  enabled: true\n  title: 星辉云\n  license: 星辉云\n  licenseUrl: http://www.xhuicloud.cn/\n  terms-of-service-url: http://www.xhuicloud.cn/\n  contact:\n    email: sindazeng@gmail.com\n    url: http://www.xhuicloud.cn/\n  authorization:\n    name: oauth2\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://XHuiCloud-gateway:15000/auth/oauth/token\nseata:\n  client:\n    rm:\n      async-commit-buffer-limit: 1000\n      report-retry-count: 5\n      table-meta-check-enable: false\n      report-success-enable: false\n      saga-branch-register-enable: false\n      lock:\n        retry-interval: 10\n        retry-times: 30\n        retry-policy-branch-rollback-on-conflict: true\n    tm:\n      degrade-check: false\n      degrade-check-period: 2000\n      degrade-check-allow-times: 10\n      commit-retry-count: 5\n      rollback-retry-count: 5\n    undo:\n      data-validation: true\n      log-serialization: kryo\n      log-table: undo_log\n      only-care-update-columns: true\n    log:\n      exceptionRate: 100\n  service:\n    vgroup-mapping:\n      xhui_tx_group: default\n    grouplist:\n      default: ${SEATA-HOST:xhuicloud-seata}:${SEATA-PORT:8091}\n    enable-degrade: false\n    disable-global-transaction: false\n  transport:\n    shutdown:\n      wait: 3\n    thread-factory:\n      boss-thread-prefix: NettyBoss\n      worker-thread-prefix: NettyServerNIOWorker\n      server-executor-thread-prefix: NettyServerBizHandler\n      share-boss-worker: false\n      client-selector-thread-prefix: NettyClientSelector\n      client-selector-thread-size: 1\n      client-worker-thread-prefix: NettyClientWorkerThread\n      worker-thread-size: default\n      boss-thread-size: 1\n    type: TCP\n    server: NIO\n    heartbeat: true\n    serialization: seata\n    compressor: none\n    enable-client-batch-send-request: true', '9643cac0192f76ee682cc13fb5833113', '2020-01-28 20:04:52', '2022-04-18 16:11:13', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '公共通用配置文件\n', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (2, 'XHuiCloud-upms-service.yml', 'DEFAULT_GROUP', 'mysql:\r\n  scheme: xhuicloud_sys\r\n\r\noss:\r\n  service-endpoint: http://127.0.0.1:9000/\r\n  access-key: minioadmin\r\n  secret-key: minioadmin', 'fd822c0d0f2eac5a0a1057c49eab47b8', '2021-10-17 19:54:05', '2021-11-12 21:03:37', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3, 'XHuiCloud-auth.yml', 'DEFAULT_GROUP', 'mysql:\n  scheme: sys\nspring:\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/\n\n', '8375babf80454931060904a7f0f6dfac', '2020-02-01 11:42:05', '2021-11-12 21:09:32', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '认证中心配置', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (4, 'XHuiCloud-logs-service.yml', 'DEFAULT_GROUP', 'server:\n  port: 18000\n', '6b3cc3571ec3601abf33967d1b323ceb', '2020-03-18 00:58:37', '2021-12-15 15:22:33', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '日志管理中心', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (5, 'XHuiCloud-pay-service.yml', 'DEFAULT_GROUP', 'server:\n  port: 23000\nmysql:\n  scheme: xhuicloud_pay\n', '50e9eb5072ba9ca44a648e83bc370269', '2020-06-03 11:17:13', '2021-12-15 16:15:33', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '收银台', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (6, 'XHuiCloud-generator.yml', 'DEFAULT_GROUP', 'mysql:\n  scheme: xhuicloud_gen\nserver:\n  port: 21000', '7cf0204fdd3d81cc354b1101fdff2048', '2020-06-22 14:50:41', '2022-02-11 11:41:20', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '代码生成模块', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'XHuiCloud-xxl-admin-service.yml', 'DEFAULT_GROUP', 'management:\r\n  health:\r\n    mail:\r\n      enabled: false\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    health:\r\n      show-details: ALWAYS\r\nmybatis:\r\n  mapper-locations: classpath:/mybatis-mapper/*Mapper.xml\r\nserver:\r\n  servlet:\r\n    context-path: /xxl-job-admin\r\nspring:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    hikari:\r\n      auto-commit: true\r\n      connection-test-query: SELECT 1\r\n      connection-timeout: 10000\r\n      idle-timeout: 30000\r\n      max-lifetime: 900000\r\n      maximum-pool-size: 30\r\n      minimum-idle: 10\r\n      pool-name: HikariCP\r\n      validation-timeout: 1000\r\n    type: com.zaxxer.hikari.HikariDataSource\r\n    url: jdbc:mysql://${MYSQL_HOST:xhuicloud-mysql}:${MYSQL_PORT:3306}/${mysql.scheme:xhuicloud_job}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai\r\n    username: ${MYSQL_USER:root}\r\n    password: ${MYSQL_PASSWORD:root}\r\n  freemarker:\r\n    request-context-attribute: request\r\n    settings:\r\n      number_format: 0.##########\r\n    suffix: .ftl\r\n  mail:\r\n    from: xxx@qq.com\r\n    host: smtp.qq.com\r\n    password: xxx\r\n    port: 25\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          socketFactory:\r\n            class: javax.net.ssl.SSLSocketFactory\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n    username: xxx@qq.com\r\n  mvc:\r\n    servlet:\r\n      load-on-startup: 0\r\n    static-path-pattern: /static/**\r\n  resources:\r\n    static-locations: classpath:/static/\r\n\r\nxxl:\r\n  job:\r\n    accessToken: XXXXXXXX\r\n    i18n: zh_CN\r\n    logretentiondays: 30\r\n    triggerpool:\r\n      fast.max: 200\r\n      slow.max: 200\r\n', '2ff2fcbdab90551b68c021869d68a587', '2021-11-29 22:17:25', '2021-12-21 09:58:52', 'nacos', '116.21.176.236', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'XHuiCloud-job-service.yml', 'DEFAULT_GROUP', 'job:\n addresses: http://${XXL_HOST:xhuicloud-xxl-admin}:${XXL_PORT:20000}/xxl-job-admin\n executor:\n  accessToken: XXXXXXXX', '4071fc847a3ec19891951da120efbc72', '2021-11-26 16:25:55', '2022-04-06 09:15:11', 'nacos', '192.168.163.1', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (11231231231435, 'transport.type', 'SEATA_GROUP', 'TCP', 'b136ef5f6a01d816991fe3cf7a6ac763', '2021-12-21 17:29:46', '2021-12-21 17:29:46', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231436, 'transport.server', 'SEATA_GROUP', 'NIO', 'b6d9dfc0fb54277321cebc0fff55df2f', '2021-12-21 17:29:47', '2021-12-21 17:29:47', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231437, 'transport.heartbeat', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-12-21 17:29:48', '2021-12-21 17:29:48', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231438, 'transport.enableClientBatchSendRequest', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:29:48', '2021-12-21 17:29:48', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231439, 'transport.threadFactory.bossThreadPrefix', 'SEATA_GROUP', 'NettyBoss', '0f8db59a3b7f2823f38a70c308361836', '2021-12-21 17:29:49', '2021-12-21 17:29:49', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231440, 'transport.threadFactory.workerThreadPrefix', 'SEATA_GROUP', 'NettyServerNIOWorker', 'a78ec7ef5d1631754c4e72ae8a3e9205', '2021-12-21 17:29:49', '2021-12-21 17:29:49', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231441, 'transport.threadFactory.serverExecutorThreadPrefix', 'SEATA_GROUP', 'NettyServerBizHandler', '11a36309f3d9df84fa8b59cf071fa2da', '2021-12-21 17:29:50', '2021-12-21 17:29:50', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231442, 'transport.threadFactory.shareBossWorker', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:29:51', '2021-12-21 17:29:51', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231443, 'transport.threadFactory.clientSelectorThreadPrefix', 'SEATA_GROUP', 'NettyClientSelector', 'cd7ec5a06541e75f5a7913752322c3af', '2021-12-21 17:29:52', '2021-12-21 17:29:52', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231444, 'transport.threadFactory.clientSelectorThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-12-21 17:29:52', '2021-12-21 17:29:52', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231445, 'transport.threadFactory.clientWorkerThreadPrefix', 'SEATA_GROUP', 'NettyClientWorkerThread', '61cf4e69a56354cf72f46dc86414a57e', '2021-12-21 17:29:53', '2021-12-21 17:29:53', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231446, 'transport.threadFactory.bossThreadSize', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-12-21 17:29:54', '2021-12-21 17:29:54', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231447, 'transport.threadFactory.workerThreadSize', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-12-21 17:29:54', '2021-12-21 17:29:54', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231448, 'transport.shutdown.wait', 'SEATA_GROUP', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '2021-12-21 17:29:55', '2021-12-21 17:29:55', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231449, 'service.vgroupMapping.xhui_tx_group', 'SEATA_GROUP', 'default', 'c21f969b5f03d33d43e04f8f136e7682', '2021-12-21 17:29:56', '2021-12-21 17:29:56', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231450, 'service.default.grouplist', 'SEATA_GROUP', 'xhuicloud-seata:8091', 'd5ecf38d7afa5dd93071dec9a49df87b', '2021-12-21 17:29:57', '2021-12-21 17:29:57', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231451, 'service.enableDegrade', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:29:58', '2021-12-21 17:29:58', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231452, 'service.disableGlobalTransaction', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:29:58', '2021-12-21 17:29:58', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231453, 'client.rm.asyncCommitBufferLimit', 'SEATA_GROUP', '10000', 'b7a782741f667201b54880c925faec4b', '2021-12-21 17:29:59', '2021-12-21 17:29:59', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231454, 'client.rm.lock.retryInterval', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-12-21 17:30:00', '2021-12-21 17:30:00', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231455, 'client.rm.lock.retryTimes', 'SEATA_GROUP', '30', '34173cb38f07f89ddbebc2ac9128303f', '2021-12-21 17:30:01', '2021-12-21 17:30:01', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231456, 'client.rm.lock.retryPolicyBranchRollbackOnConflict', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-12-21 17:30:02', '2021-12-21 17:30:02', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231457, 'client.rm.reportRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-12-21 17:30:04', '2021-12-21 17:30:04', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231458, 'client.rm.tableMetaCheckEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:05', '2021-12-21 17:30:05', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231459, 'client.rm.sqlParserType', 'SEATA_GROUP', 'druid', '3d650fb8a5df01600281d48c47c9fa60', '2021-12-21 17:30:08', '2021-12-21 17:30:08', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231460, 'client.rm.reportSuccessEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:09', '2021-12-21 17:30:09', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231461, 'client.rm.sagaBranchRegisterEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:10', '2021-12-21 17:30:10', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231462, 'client.tm.commitRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-12-21 17:30:11', '2021-12-21 17:30:11', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231463, 'client.tm.rollbackRetryCount', 'SEATA_GROUP', '5', 'e4da3b7fbbce2345d7772b0674a318d5', '2021-12-21 17:30:12', '2021-12-21 17:30:12', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231464, 'client.tm.degradeCheck', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:12', '2021-12-21 17:30:12', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231465, 'client.tm.degradeCheckAllowTimes', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-12-21 17:30:14', '2021-12-21 17:30:14', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231466, 'client.tm.degradeCheckPeriod', 'SEATA_GROUP', '2000', '08f90c1a417155361a5c4b8d297e0d78', '2021-12-21 17:30:15', '2021-12-21 17:30:15', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231467, 'store.mode', 'SEATA_GROUP', 'redis', '86a1b907d54bf7010394bf316e183e67', '2021-12-21 17:30:16', '2021-12-21 17:30:16', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231468, 'store.redis.host', 'SEATA_GROUP', 'xhuicloud.redis.rds.aliyuncs.com', '06c94ca6127bbebc5f44411c2f431084', '2021-12-21 17:30:16', '2021-12-21 17:30:16', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231469, 'store.redis.port', 'SEATA_GROUP', '6379', '92c3b916311a5517d9290576e3ea37ad', '2021-12-21 17:30:17', '2021-12-21 17:30:17', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231470, 'store.redis.maxConn', 'SEATA_GROUP', '10', 'd3d9446802a44259755d38e6d163e820', '2021-12-21 17:30:18', '2021-12-21 17:30:18', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231471, 'store.redis.minConn', 'SEATA_GROUP', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2021-12-21 17:30:19', '2021-12-21 17:30:19', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231472, 'store.redis.database', 'SEATA_GROUP', '0', 'cfcd208495d565ef66e7dff9f98764da', '2021-12-21 17:30:19', '2021-12-21 17:30:19', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231473, 'store.redis.password', 'SEATA_GROUP', 'Xhuicloud@123', '589e6cff0fa4e1a38b82107bdd564b62', '2021-12-21 17:30:20', '2021-12-21 17:30:20', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231474, 'store.redis.queryLimit', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-12-21 17:30:21', '2021-12-21 17:30:21', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231475, 'server.recovery.committingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-12-21 17:30:22', '2021-12-21 17:30:22', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231476, 'server.recovery.asynCommittingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-12-21 17:30:22', '2021-12-21 17:30:22', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231477, 'server.recovery.rollbackingRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-12-21 17:30:23', '2021-12-21 17:30:23', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231478, 'server.recovery.timeoutRetryPeriod', 'SEATA_GROUP', '1000', 'a9b7ba70783b617e9998dc4dd82eb3c5', '2021-12-21 17:30:24', '2021-12-21 17:30:24', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231479, 'server.maxCommitRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2021-12-21 17:30:24', '2021-12-21 17:30:24', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231480, 'server.maxRollbackRetryTimeout', 'SEATA_GROUP', '-1', '6bb61e3b7bce0931da574d19d1d82c88', '2021-12-21 17:30:25', '2021-12-21 17:30:25', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231481, 'server.rollbackRetryTimeoutUnlockEnable', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:26', '2021-12-21 17:30:26', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231482, 'client.undo.dataValidation', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-12-21 17:30:26', '2021-12-21 17:30:26', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231483, 'client.undo.logSerialization', 'SEATA_GROUP', 'kryo', 'd78f017576c8b3ad5beec73e6c39a59e', '2021-12-21 17:30:27', '2021-12-21 17:30:27', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231484, 'client.undo.onlyCareUpdateColumns', 'SEATA_GROUP', 'true', 'b326b5062b2f0e69046810717534cb09', '2021-12-21 17:30:28', '2021-12-21 17:30:28', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231485, 'server.undo.logSaveDays', 'SEATA_GROUP', '7', '8f14e45fceea167a5a36dedd4bea2543', '2021-12-21 17:30:28', '2021-12-21 17:30:28', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231486, 'server.undo.logDeletePeriod', 'SEATA_GROUP', '86400000', 'f4c122804fe9076cb2710f55c3c6e346', '2021-12-21 17:30:29', '2021-12-21 17:30:29', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231487, 'client.undo.logTable', 'SEATA_GROUP', 'undo_log', '2842d229c24afe9e61437135e8306614', '2021-12-21 17:30:29', '2021-12-21 17:30:29', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231488, 'client.log.exceptionRate', 'SEATA_GROUP', '100', 'f899139df5e1059396431415e770c6dd', '2021-12-21 17:30:30', '2021-12-21 17:30:30', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231489, 'transport.serialization', 'SEATA_GROUP', 'seata', 'b943081c423b9a5416a706524ee05d40', '2021-12-21 17:30:30', '2021-12-21 17:30:30', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231490, 'transport.compressor', 'SEATA_GROUP', 'none', '334c4a4c42fdb79d7ebc3e73b517e6f8', '2021-12-21 17:30:31', '2021-12-21 17:30:31', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231491, 'metrics.enabled', 'SEATA_GROUP', 'false', '68934a3e9455fa72420237eb05902327', '2021-12-21 17:30:31', '2021-12-21 17:30:31', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231492, 'metrics.registryType', 'SEATA_GROUP', 'compact', '7cf74ca49c304df8150205fc915cd465', '2021-12-21 17:30:32', '2021-12-21 17:30:32', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231493, 'metrics.exporterList', 'SEATA_GROUP', 'prometheus', 'e4f00638b8a10e6994e67af2f832d51c', '2021-12-21 17:30:32', '2021-12-21 17:30:32', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);
INSERT INTO `config_info` VALUES (11231231231494, 'metrics.exporterPrometheusPort', 'SEATA_GROUP', '9898', '7b9dc501afe4ee11c56a4831e20cee71', '2021-12-21 17:30:33', '2021-12-21 17:30:33', '', '127.0.0.1', '', '', NULL, NULL, NULL, 'text', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
                                     `gmt_modified` datetime NOT NULL COMMENT '修改时间',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                     `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                     `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                     `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                     `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
                                     `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                     `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                     `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                     `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                     `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                     `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                    `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
                                    `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
                                    `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
                                         `id` bigint(20) NOT NULL COMMENT 'id',
                                         `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
                                         `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
                                         `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
                                         `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
                                         `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                         `nid` bigint(20) NOT NULL AUTO_INCREMENT,
                                         PRIMARY KEY (`nid`) USING BTREE,
                                         UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
                                         INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
                                   `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
                                   `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                   `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                   `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                   `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
                                   `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                   `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                   `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                   `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
                                    `id` bigint(20) UNSIGNED NOT NULL,
                                    `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
                                    `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
                                    `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
                                    `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
                                    `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
                                    `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
                                    `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
                                    PRIMARY KEY (`nid`) USING BTREE,
                                    INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
                                    INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
                                    INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 287 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
                                `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `resource` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          UNIQUE INDEX `uk_username_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
                                    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
                                    `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
                                    `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
                                    `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
                                    `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
                                    `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
                                    `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
                                    `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
                                    `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
                                `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
                                `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
                                `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
                                `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
                                `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
                                `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
                                INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
                          `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `enabled` tinyint(1) NOT NULL,
                          PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
