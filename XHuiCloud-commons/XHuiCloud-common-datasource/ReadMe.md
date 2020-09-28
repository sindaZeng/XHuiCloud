

 **未开启多数据源的数据库配置格式**
```
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。
      logic-delete-value: 0 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    platform: mysql
    url: jdbc:mysql://localhost:3306/sinda_user?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true
    username: root
    password: root
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    filters: stat,wall,log4j
  session:
    timeout: 2592000
```
**开启动态数据源的数据库配置格式**

```
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: delFlag  #全局逻辑删除字段值 3.3.0开始支持，详情看下面。
      logic-delete-value: 0 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)
spring:
  datasources:
    #       数据库 1
    first:
      url: jdbc:mysql://localhost:3306/sinda_user?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver
      initialSize: 5
      minIdle: 5
      maxActive: 20
    second:
      url: jdbc:mysql://localhost:3306/sys?characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver
      initialSize: 5
      minIdle: 5
      maxActive: 20
  session:
    timeout: 2592000
```
