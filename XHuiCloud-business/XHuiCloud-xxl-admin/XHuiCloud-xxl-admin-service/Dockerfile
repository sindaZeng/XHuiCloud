FROM openjdk:8u322

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-xxl-admin-service

WORKDIR /XHuiCloud-xxl-admin-service

EXPOSE 20000

ADD ./target/XHuiCloud-xxl-admin-service.jar ./

CMD sleep 50;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-xxl-admin-service.jar


