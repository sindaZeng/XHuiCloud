FROM openjdk:8u322

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-pay-service

WORKDIR /XHuiCloud-pay-service

EXPOSE 23000

ADD ./target/XHuiCloud-pay-service.jar ./

CMD sleep 120;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-pay-service.jar


