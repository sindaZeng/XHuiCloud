FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-pay-service

WORKDIR /XHuiCloud-pay-service

EXPOSE 23000

ADD ./target/XHuiCloud-pay-service.jar ./

CMD sleep 60;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-pay-service.jar


