- 基于 Spring Cloud Hoxton、Spring Boot、OAuth2 的RBAC权限管理系统
- 提供对 Docker、Kubernetes、Rancher2 容器化支持


- [接口文档](http://127.0.0.1:15000/doc.html#/home)
- [部署文档(编写中)]()
- [在线预览](http://admin.xhuicloud.com/) 

#### 目录结构
```lua
XHuiCloud
├── XHuiCloud-auth                         -- 认证服务器[16000]
└── XHuiCloud-commons                      -- 公共模块 
     ├── XHuiCloud-common-core             -- 公共核心模块
     ├── XHuiCloud-common-data             -- 数据缓存模块
     ├── XHuiCloud-common-dds       -- 动态数据源模块
     ├── XHuiCloud-common-elasticJob       -- elasticJob自动配置模块
     ├── XHuiCloud-common-feign            -- 公共feign
     ├── XHuiCloud-common-gateway          -- 网关核心模块
     ├── XHuiCloud-common-gray             -- 灰度发布
     ├── XHuiCloud-common-lock             -- 分布式锁模块
     ├── XHuiCloud-common-log              -- 公共日志核心
     ├── XHuiCloud-common-rabbitMq         -- rabbitMqp配置模块
     ├── XHuiCloud-common-security         -- 安全模块
     ├── XHuiCloud-common-sentinel         -- 高可用的保证,限流降级模块
     ├── XHuiCloud-common-swagger          -- 接口文档模块
     ├── XHuiCloud-common-transaction      -- 分布式事务模块
     ├── XHuiCloud-common-xxl              -- XXL-JOB配置模块
     └── XHuiCloud-common-zero             -- 分布式发号器
├── XHuiCloud-dependencies-bom             -- 统一依赖管理
├── XHuiCloud-business                     -- 系统管理业务模块
     ├── XHuiCloud-gateway                 -- Api网关[15000]
     ├── XHuiCloud-generator               -- 开发平台[21000]
     ├── XHuiCloud-logs                    -- 日志服务器(18000)
     └── XHuiCloud-job                     -- 定时任务
        └── XHuiCloud-job-api              -- 定时任务公共api
        └── XHuiCloud-job-service          -- 定时任务服务(19000)
     └──XHuiCloud-logs                     -- 操作日志模块
        └── XHuiCloud-logs-api             -- 操作日志公共api
        └── XHuiCloud-logs-service         -- 操作日志服务(18000)
     └──XHuiCloud-pay                      -- 支付中心模块
        └── XHuiCloud-pay-api              -- 支付中心公共api
        └── XHuiCloud-pay-service          -- 支付中心服务(23000)
     └──XHuiCloud-push                     -- 统一推送中心模块
        └── XHuiCloud-push-api             -- 统一推送中心公共api
        └── XHuiCloud-push-service         -- 统一推送中心服务(22000)
     ├── XHuiCloud-register                -- 注册中心(13000)
     ├── XHuiCloud-sentinel-dashboard      -- Sentinel监控模块(14000)
     ├── XHuiCloud-seata             -- 事务管理模块(8091)
     └── XHuiCloud-xxl-admin               -- 定时任务管理模块(20000)
└── XHuiCloud-upms                         -- 权限管理模块
     └── XHuiCloud-upms-api                -- 系统权限管理公共api模块
     └── XHuiCloud-upms-service            -- 系统权限管理业务处理模块[17000]
```

环境准备:
yum install -y git java maven

git clone https://github.com/sindaZeng/XHuiCloud.git

# 运行方式一:
Docker

- cd 项目根目录
- mvn clean install -Dmaven.test.skip=true
- docker-compose build
- docker-compose up -d

等待执行完毕,编译打包[XHuiCloud-UI 前端](https://github.com/sindaZeng/xhuicloud-ui)
访问  http://localhost:9527/ 

即可愉快玩耍啦~
