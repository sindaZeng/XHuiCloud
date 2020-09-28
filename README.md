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
     ├── XHuiCloud-common-datasource       -- 动态数据源模块
     ├── XHuiCloud-common-elasticJob       -- elasticJob自动配置模块
     ├── XHuiCloud-common-gateway          -- 网关核心模块
     ├── XHuiCloud-common-lock             -- 分布式锁模块
     ├── XHuiCloud-common-logger           -- 统一日志核心模块
     ├── XHuiCloud-common-security         -- 安全模块
     ├── XHuiCloud-common-sentinel         -- 高可用的保证,限流降级模块
     ├── XHuiCloud-common-swagger          -- 接口文档模块
     ├── XHuiCloud-common-transaction      -- 分布式事务模块
     └── XHuiCloud-common-zero             -- 分布式发号器
├── XHuiCloud-dependencies-bom             -- 统一依赖管理
├── XHuiCloud-management-business          -- 系统管理业务模块
     ├── XHuiCloud-gateway                 -- Api网关[15000]
     ├── XHuiCloud-generator               -- 开发平台[21000]
     ├── XHuiCloud-logs                    -- 日志服务器(18000)
     ├── XHuiCloud-pay                     -- 支付中心(20000)
     ├── XHuiCloud-register                -- 注册中心(13000)
     ├── XHuiCloud-sentinel-dashboard      -- Sentinel监控模块(14000)
     ├── XHuiCloud-task                    -- 定时任务模块(8081)
     └── XHuiCloud-transaction-coordinator -- 事务管理模块(8091)
└── XHuiCloud-upmm                         -- 权限管理模块
     └── XHuiCloud-upmm-api                -- 系统权限管理公共api模块
     └── XHuiCloud-upmm-business           -- 系统权限管理业务处理模块[17000]
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

等待执行完毕,编译打包[XHuiCloud-UI 前端](https://github.com/sindaZeng/Fdp-ui)
访问  http://localhost:9527/ 

即可愉快玩耍啦~
