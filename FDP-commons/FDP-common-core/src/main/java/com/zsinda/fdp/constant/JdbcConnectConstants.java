package com.zsinda.fdp.constant;

/**
 * @program: FDPlatform
 * @description: JdbcConnectConstants
 * @author: Sinda
 * @create: 2020-06-22 16:55
 */
public interface JdbcConnectConstants {

    String MYSQL_DRIVER="com.mysql.jdbc.Driver";

    String MYSQL_URL_PREFIX = "jdbc:mysql://";

    String MYSQL_URL_SUFFIX = "characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true";

    String MYSQL_URL = MYSQL_URL_PREFIX + "%s:%s/%s?" + MYSQL_URL_SUFFIX;
}
