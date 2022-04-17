FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-register

WORKDIR /XHuiCloud-register

EXPOSE 13000

ADD ./target/XHuiCloud-register.jar ./

CMD sleep 30;java -Xms256m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-register.jar


