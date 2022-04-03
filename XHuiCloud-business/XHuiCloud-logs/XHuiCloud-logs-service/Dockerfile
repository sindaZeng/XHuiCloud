FROM openjdk:8u322

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuicloud-logs-service

WORKDIR /XHuicloud-logs-service

EXPOSE 18000

ADD ./target/XHuiCloud-logs-service.jar ./

CMD sleep 60;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-logs-service.jar
