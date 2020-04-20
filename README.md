环境准备:
yum install -y git java maven

git clone https://github.com/sindaZeng/FDPlatform.git

# 运行方式一:
Docker

- cd 项目根目录
- mvn clean install -Dmaven.test.skip=true
- docker-compose build
- docker-compose up -d

等待执行完毕,编译打包[FDP-UI 前端](https://github.com/sindaZeng/Fdp-ui)
访问  http://localhost:9528/ 

即可愉快玩耍啦~
