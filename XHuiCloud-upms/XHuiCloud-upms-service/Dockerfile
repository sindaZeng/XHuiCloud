FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-upms-service

WORKDIR /XHuiCloud-upms-service

EXPOSE 17000

ADD ./target/XHuiCloud-upms-service.jar ./

CMD sleep 70;java -Xms512m -Xmx512m -Xss256k -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-upms-service.jar


