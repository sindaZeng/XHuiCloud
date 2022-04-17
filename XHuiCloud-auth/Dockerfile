FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-auth

WORKDIR /XHuiCloud-auth

EXPOSE 16000

COPY ./target/XHuiCloud-auth.jar XHuiCloud-auth.jar

CMD sleep 90;java -Xms256m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-auth.jar
