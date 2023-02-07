USE xhuicloud_register;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11231231231559 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'application-common-dev.yml', 'DEFAULT_GROUP', 'spring:\n  boot:  # spring-boot admin 配置\n    admin:\n      client:\n        url: http://${XHUI_MONITOR:xhuicloud-monitor}:${XHUI_MONITOR_PORT:9090}\n  security:\n    oauth2:\n      resourceserver:\n        jwt:\n          issuer-uri: ${xhuicloud.security.resource-server.issuer-uri}\n          jwk-set-uri: ${xhuicloud.security.resource-server.jwk-set-uri}\n  mvc:\n    pathmatch:\n      matching-strategy: ant_path_matcher\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://${MYSQL_HOST:xhuicloud-mysql}:${MYSQL_PORT:3306}/${mysql.scheme:\'\'}?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      username: ${MYSQL_USER:root}\n      password: ${MYSQL_PASSWORD:root}\n      stat-view-servlet:\n        enabled: true\n        allow: \"\"\n        url-pattern: /druid/*\n      filter:\n        stat:\n          enabled: true\n          log-slow-sql: true\n          slow-sql-millis: 10000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n\n  redis:\n    host: ${REDIS_HOST:xhuicloud-redis}\n    password: ${REDIS_PASSWORD:root}\n    port: ${REDIS_PORT:6379}\n   # username: ${REDIS_USER:root}\n    timeout: 10000\n  rabbitmq:\n    host : ${MQ_HOST:xhuicloud-mq}\n    port: ${MQ_PORT:5672}\n    username: ${MQ_USER:root} \n    password: ${MQ_PASSWORD:root}\n    virtual-host: ${MQ_VHOST:xhuicloud}\n  cloud:\n    alibaba:\n      seata:\n        tx-service-group: xhui_tx_group\n    circuitbreaker:\n      sentinel:\n        enabled: true\n    sentinel:\n      filter:\n        urlPatterns:\n          - /**\n      transport:\n        # sentinel 地址\n        dashboard: http://${XHUI_SENTINEL:xhuicloud-sentinel}:${XHUI_SENTINEL_PORT:10101}\n        #监控此服务端口\n        port: ${server.port}\ngray:\n  enabled: true\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'          \n  endpoint:\n    health:\n      show-details: ALWAYS\n\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 20000\n        readTimeout: 20000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n\nmybatis-plus:\n  tenant-enable: ture\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  configuration:\n    jdbc-type-for-null: null\n  type-handlers-package:  com.xhuicloud.common.mybatis.handler\n  global-config:\n    banner: false\n    db-config:\n      logic-delete-field: isDel\n      logic-delete-value: 1\n      logic-not-delete-value: 0\n      db-type: oracle\n      id-type: auto\n      select-strategy: not_empty\n      insert-strategy: not_empty\n      update-strategy: not_empty\n\n  zipkin:\n    #设置zipkin的地址\n    base-url: http://${XHUI_ZIPKIN:xhuicloud-zipkin}:${XHUI_ZIPKIN_PORT:9411}\n    #注意 如果不设置 nacos客户端会把这个当成服务名疯狂的从服务端获取此服务\n    discoveryClientEnabled: false\n\n  sleuth:\n    sampler:\n      #抽样率 默认是(10%) 生产环境请不要设置1.0\n      probability: 1.0\n\nxhuicloud:\n  security:\n    #匿名令牌\n    # access-token-format: reference\n    # JWT\n    access-token-format: self-contained\n    # 认证授权配置\n    authorization:\n      # token 有效期\n      access-token-validity-seconds: 200000\n      issuer: http://${XHUI_AUTH:xhuicloud-auth}:${XHUI_AUTH_PORT:16000}\n      # 白名单\n      ignore-urls:\n         - /error\n         - /actuator/**\n         - /v2/api-docs\n    # 资源服务器配置\n    resource-server:\n      # 匿名令牌模式下需要配置\n      clientId: test\n      clientSecret: test\n      introspectionUri: ${xhuicloud.security.authorization.issuer}/oauth2/introspect\n      # JWT模式下需要配置      \n      issuer-uri: ${xhuicloud.security.authorization.issuer}\n      jwk-set-uri: ${xhuicloud.security.authorization.issuer}/oauth2/jwks\n      # 白名单\n      ignore-urls:\n        - /error\n        - /druid/**\n        - /actuator/**\n        - /v2/api-docs\n\n#  zero:\n#    snowflake:\n#      zk-address: 127.0.0.1\n#      port: 19999\n\nlogging:\n  level:\n    com.xhuicloud.*.mapper: debug\n  # path: /Users/cengxinda/workspace/sourceCode/XHuiCloud/logs\n  # name: ${logging.file.path}/${spring.application.name}/root.log\n  config: classpath:com/xhuicloud/common/log/logback/defaults-log.xml\n\n# swagger 全局配置\nswagger:\n  enabled: true\n  title: 星辉云\n  license: 星辉云\n  licenseUrl: http://www.xhuicloud.cn/\n  terms-of-service-url: http://www.xhuicloud.cn/\n  contact:\n    email: sindazeng@gmail.com\n    url: http://www.xhuicloud.cn/\n  authorization:\n    name: oauth2\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://${XHUI_GATEWAY:xhuicloud-gateway}:${XHUI_GATEWAY_PORT:15000}/auth/oauth/token\n\nseata:\n  enabled: true\n  tx-service-group: xhui_tx_group # 事务群组\n  client:\n    rm:\n      async-commit-buffer-limit: 1000  # 异步提交缓存队列长度（默认10000）\n      report-retry-count: 5 # 一阶段结果上报TC重试次数（默认5）\n      table-meta-check-enable: false # 自动刷新缓存中的表结构（默认false）\n      report-success-enable: false\n      saga-branch-register-enable: false\n      lock:\n        retry-interval: 10 # 校验或占用全局锁重试间隔（默认10ms）\n        retry-times: 30 # 校验或占用全局锁重试次数（默认30）\n        retry-policy-branch-rollback-on-conflict: true # 分支事务与其它全局回滚事务冲突时锁策略（优先释放本地锁让回滚成功）\n    tm:\n      degrade-check: false\n      degrade-check-period: 2000\n      degrade-check-allow-times: 10\n      commit-retry-count: 5 # 一阶段全局提交结果上报TC重试次数（默认1次，建议大于1）\n      rollback-retry-count: 5 # 一阶段全局回滚结果上报TC重试次数（默认1次，建议大于1）\n    undo:\n      data-validation: true # 二阶段回滚镜像校验（默认true开启）\n      log-serialization: kryo # undo序列化方式（默认jackson 不支持 LocalDateTime）\n      log-table: undo_log # 自定义undo表名（默认undo_log）\n      only-care-update-columns: true\n    log:\n      exceptionRate: 100 # 日志异常输出概率（默认100）\n  service:\n    vgroup-mapping:\n      xhui_tx_group: default # TC 集群（必须与seata-server保持一致）\n    grouplist:\n      default: ${SEATA_HOST:xhuicloud-seata}:${SEATA_PORT:8091}\n    enable-degrade: false # 降级开关\n    disable-global-transaction: false # 禁用全局事务（默认false）\n  transport:\n    shutdown:\n      wait: 3\n    thread-factory:\n      boss-thread-prefix: NettyBoss\n      worker-thread-prefix: NettyServerNIOWorker\n      server-executor-thread-prefix: NettyServerBizHandler\n      share-boss-worker: false\n      client-selector-thread-prefix: NettyClientSelector\n      client-selector-thread-size: 1\n      client-worker-thread-prefix: NettyClientWorkerThread\n      worker-thread-size: default\n      boss-thread-size: 1\n    type: TCP\n    server: NIO\n    heartbeat: true\n    serialization: seata\n    compressor: none\n    enable-client-batch-send-request: true # 客户端事务消息请求是否批量合并发送（默认true）', '37963c6d8025211f73391d8c2fd2a3d0', '2020-01-28 20:04:52', '2022-10-20 17:12:14', 'nacos', '192.168.1.36', '快速开发平台-星辉云', '', '公共通用配置文件\n', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (2, 'XHuiCloud-upms-service-dev.yml', 'DEFAULT_GROUP', 'mysql:\r\n  scheme: xhuicloud_sys\r\n\r\noss:\r\n  service-endpoint: http://127.0.0.1:9000/\r\n  access-key: minioadmin\r\n  secret-key: minioadmin', 'fd822c0d0f2eac5a0a1057c49eab47b8', '2021-10-17 19:54:05', '2021-11-12 21:03:37', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3, 'XHuiCloud-auth-dev.yml', 'DEFAULT_GROUP', 'spring:\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/', '5343596a85cb14ed9b1a48fbe39d6d76', '2020-02-01 11:42:05', '2022-10-18 08:16:58', 'nacos', '192.168.1.36', '快速开发平台-星辉云', '', '认证中心配置', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (4, 'XHuiCloud-logs-service-dev.yml', 'DEFAULT_GROUP', 'server:\n  port: 18000\n', '6b3cc3571ec3601abf33967d1b323ceb', '2020-03-18 00:58:37', '2021-12-15 15:22:33', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '日志管理中心', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (5, 'XHuiCloud-pay-service-dev.yml', 'DEFAULT_GROUP', 'server:\n  port: 23000\nmysql:\n  scheme: xhuicloud_pay\n', '50e9eb5072ba9ca44a648e83bc370269', '2020-06-03 11:17:13', '2021-12-15 16:15:33', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '收银台', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (6, 'XHuiCloud-generator-dev.yml', 'DEFAULT_GROUP', 'mysql:\n  scheme: xhuicloud_gen\nserver:\n  port: 21000', '7cf0204fdd3d81cc354b1101fdff2048', '2020-06-22 14:50:41', '2022-02-11 11:41:20', 'nacos', '0:0:0:0:0:0:0:1', '快速开发平台-星辉云', '', '代码生成模块', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'XHuiCloud-xxl-admin-service-dev.yml', 'DEFAULT_GROUP', 'management:\n  health:\n    mail:\n      enabled: false\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\nmybatis:\n  mapper-locations: classpath:/mybatis-mapper/*Mapper.xml\nserver:\n  servlet:\n    context-path: /xxl-job-admin\nspring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    hikari:\n      auto-commit: true\n      connection-test-query: SELECT 1\n      connection-timeout: 10000\n      idle-timeout: 30000\n      max-lifetime: 900000\n      maximum-pool-size: 30\n      minimum-idle: 10\n      pool-name: HikariCP\n      validation-timeout: 1000\n    type: com.zaxxer.hikari.HikariDataSource\n    url: jdbc:mysql://${MYSQL_HOST:xhuicloud-mysql}:${MYSQL_PORT:3306}/${mysql.scheme:xhuicloud_job}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai\n    username: ${MYSQL_USER:root}\n    password: ${MYSQL_PASSWORD:root}\n  freemarker:\n    request-context-attribute: request\n    settings:\n      number_format: 0.##########\n    suffix: .ftl\n  mail:\n    from: xxx@qq.com\n    host: smtp.qq.com\n    password: xxx\n    port: 25\n    properties:\n      mail:\n        smtp:\n          auth: true\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n          starttls:\n            enable: true\n            required: true\n    username: xxx@qq.com\n  mvc:\n    servlet:\n      load-on-startup: 0\n    static-path-pattern: /static/**\n  resources:\n    static-locations: classpath:/static/\n\nxxl:\n  job:\n    i18n: zh_CN\n    logretentiondays: 30\n    triggerpool:\n      fast.max: 200\n      slow.max: 200\n', 'e58a18d688882a95f6285bef14e0de28', '2021-11-29 22:17:25', '2022-11-03 08:13:05', 'nacos', '192.168.1.36', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'XHuiCloud-job-service-dev.yml', 'DEFAULT_GROUP', 'job:\n addresses: http://${XXL_HOST:xhuicloud-xxl-admin}:${XXL_PORT:20000}/xxl-job-admin\n executor:\n  accessToken: XXXXXXXX', '4071fc847a3ec19891951da120efbc72', '2021-11-26 16:25:55', '2022-04-06 09:15:11', 'nacos', '192.168.163.1', '快速开发平台-星辉云', '', '', '', '', 'yaml', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
  INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 345 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

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
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
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
  UNIQUE INDEX `uk_username_role`(`username` ASC, `role` ASC) USING BTREE
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
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

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
