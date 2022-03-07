FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-upms-service

WORKDIR /XHuiCloud-upms-service

EXPOSE 17000

ADD ./target/XHuiCloud-upms-service.jar ./

CMD sleep 50;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-upms-service.jar


