FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-job-service

WORKDIR /XHuiCloud-job-service

EXPOSE 19000

ADD ./target/XHuiCloud-job-service.jar ./

CMD sleep 110;java -Xms256m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-job-service.jar
