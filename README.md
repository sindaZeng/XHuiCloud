- 基于 Spring Cloud、Spring Boot、OAuth2 的RBAC权限管理系统
- 提供对 Docker、Kubernetes、Rancher2 容器化支持


- [接口文档](http://127.0.0.1:15000/doc.html#/home)
- [部署文档(编写中)]()
- [在线预览](http://zsinda.cn:9527/) 

#### 目录结构
```lua
FDPlatform
├── FDP-auth                         -- 认证服务器[16000]
└── FDP-commons                      -- 公共模块 
     ├── FDP-common-core             -- 公共核心模块
     ├── FDP-common-data             -- 数据缓存模块
     ├── FDP-common-datasource       -- 动态数据源模块
     ├── FDP-common-elasticJob       -- elasticJob自动配置模块
     ├── FDP-common-gateway          -- 网关核心模块
     ├── FDP-common-lock             -- 分布式锁模块
     ├── FDP-common-logger           -- 统一日志核心模块
     ├── FDP-common-security         -- 安全模块
     ├── FDP-common-sentinel         -- 高可用的保证,限流降级模块
     ├── FDP-common-swagger          -- 接口文档模块
     ├── FDP-common-transaction      -- 分布式事务模块
     └── FDP-common-zero             -- 分布式发号器
├── FDP-dependencies-bom             -- 统一依赖管理
├── FDP-management-business          -- 系统管理业务模块
     ├── FDP-gateway                 -- Api网关[15000]
     ├── FDP-logs                    -- 日志服务器(18000)
     ├── FDP-register                -- 注册中心(13000)
     ├── FDP-sentinel-dashboard      -- Sentinel监控模块(14000)
     ├── FDP-task                    -- 定时任务模块(8081)
     └── FDP-transaction-coordinator -- 事务管理模块(8091)
└── FDP-upmm                         -- 权限管理模块
     └── FDP-upmm-api                -- 系统权限管理公共api模块
     └── FDP-upmm-business           -- 系统权限管理业务处理模块[17000]
```

环境准备:
yum install -y git java maven

git clone https://github.com/sindaZeng/FDPlatform.git

- [在线预览](http://zsinda.cn:9527/) 

# 运行方式一:
Docker

- cd 项目根目录
- mvn clean install -Dmaven.test.skip=true
- docker-compose build
- docker-compose up -d

等待执行完毕,编译打包[FDP-UI 前端](https://github.com/sindaZeng/Fdp-ui)
访问  http://localhost:9527/ 

即可愉快玩耍啦~
