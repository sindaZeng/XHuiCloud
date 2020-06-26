USE sys_generator;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_datasource_Info
-- ----------------------------
DROP TABLE IF EXISTS `gen_datasource_Info`;
CREATE TABLE `gen_datasource_Info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据源表id',
  `type` varchar(20) NOT NULL COMMENT '数据库类型:mysql,oracle,sqlsever',
  `name` varchar(255) NOT NULL COMMENT '数据库名',
  `host` varchar(255) NOT NULL COMMENT '数据库host',
  `port` varchar(5) NOT NULL COMMENT '数据库端口',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
