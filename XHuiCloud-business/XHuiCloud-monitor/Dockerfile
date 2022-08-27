FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-monitor

WORKDIR /XHuiCloud-monitor

EXPOSE 9090

ADD ./target/XHuiCloud-monitor.jar ./

CMD sleep 90;java -Xms256m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-monitor.jar
