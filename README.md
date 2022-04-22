<p align="center">
     <img src="https://xhuicloud.oss-cn-shenzhen.aliyuncs.com/logo-transparent.png" width="200px" height="200px" alt="logo"> </br>
     XHuiCloud - 星辉云快速开发 
</p>
<p align="center">
  <a href="https://github.com/sindaZeng/XHuiCloud/blob/develop/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="LICENSE">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/XHuiCloud-2.0.0-green.svg" alt="XHuiCloud">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Spring--Boot-2.5.6-green.svg" alt="SpringBoot">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Spring--Cloud-2020.0.4-green.svg" alt="SpringCloud">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Spring--Cloud--Alibaba-2021.1-green.svg" alt="SpringCloudAlibaba">
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Spring--Security-5.0.3.RELEASE-green.svg" alt="SpringSecurity">
  </a>
  <a href="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQHz8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybV9pT1JlRmxjUEUxMDAwMHcwN0UAAgTet0ZiAwQAAAAA">
    <img src="https://img.shields.io/badge/%E5%85%AC%E4%BC%97%E5%8F%B7-xhuicloud-brightgreen" alt="xhuicloud">
  </a>
</p>


## 温馨提醒

> 1. **快速体验项目**：[在线访问地址](http://xhuicloud.cn/)。
> 2. **接口文档**：[swagger在线](http://api.xhuicloud.cn/doc.html)。
> 3. **后端操作文档**：敬请期待。。。
> 4. **微服务版本**：基于Spring Cloud 2020.0.4 & Alibaba 2021.1。

## 前言

本项目致力于采用现阶段流行技术，打造快速开发平台，做到适配各种场景的脚手架。

#### 项目介绍

`XHuiCloud`项目是基础脚手架系统，
主要技术栈有：
SpringBoot + SpringCloudAlibaba + SpringSecurity + Nacos + MyBatis-Plus 实现，采用Docker容器化部署。拥有基于Seata的分布式事务, 基于redis，zk分布式锁，基于zk的分布式发号器，分布式任务xxl-job，分布式任务elastic-Job，amazonaws oss等等的自动装配包。  

#### 模块介绍(包含规划)

- [x] [认证中心(快速体验授权登录 U:admin P:123456)](http://oauth2.xhuicloud.cn/oauth/authorize?client_id=test&response_type=code&scop=server&redirect_uri=http://xhuicloud.cn)
- [x] 权限管理
- [x] 用户管理
- [x] 租户管理
- [x] 推送中心
- [x] 文件管理
- [x] 日志管理
- [x] 代码生成
- [x] 灰度发布 
- [ ] 大屏系统
- [ ] 报表系统
- [ ] 监控平台
- [ ] 聚合支付
- [ ] 微信公众号运营管理
- [ ] 基础运营系统
- [ ] 内容管理
- [ ] OA办公自动化

#### 目录结构
```lua
XHuiCloud
├── XHuiCloud-auth                         -- 认证服务器[16000]
└── XHuiCloud-commons                      -- 公共模块 
     ├── XHuiCloud-common-core             -- 公共核心模块
     ├── XHuiCloud-common-data             -- 数据缓存模块
     ├── XHuiCloud-common-dds              -- 动态数据源模块
     ├── XHuiCloud-common-elasticJob       -- elasticJob自动配置模块
     ├── XHuiCloud-common-feign            -- 公共feign
     ├── XHuiCloud-common-gateway          -- 网关核心模块
     ├── XHuiCloud-common-gray             -- 灰度发布
     ├── XHuiCloud-common-lock             -- 分布式锁模块
     ├── XHuiCloud-common-log              -- 公共日志核心
     ├── XHuiCloud-common-mybaits          -- 数据源模块
     ├── XHuiCloud-common-oss              -- 公共存储桶配置模块
     ├── XHuiCloud-common-rabbitMq         -- rabbitMqp配置模块
     ├── XHuiCloud-common-security         -- 安全模块
     ├── XHuiCloud-common-sentinel         -- 高可用的保证,限流降级模块
     ├── XHuiCloud-common-swagger          -- 接口文档模块
     ├── XHuiCloud-common-seata      -- 分布式事务模块
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

### 开发环境

| 工具          | 版本号 |
| ------------- | ------ |
| JDK           | 1.8    |
| Mysql         | 8.0.28 |
| Redis         | 6.2.6  |
| RabbitMQ      | 3      |
| Nginx         | 1.10   |
| Minio         |        |

### 搭建步骤

> Docker环境部署

- yum install -y git java maven
- git clone https://github.com/sindaZeng/XHuiCloud.git
- cd 项目根目录
- mvn clean install -Dmaven.test.skip=true
- docker-compose build
- docker-compose up -d
 
## 许可证

[MIT](https://github.com/sindaZeng/XHuiCloud/blob/develop/LICENSE)

Copyright (c) <2021-2022> Sinda(xhuicloud@163.com)
