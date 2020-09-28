FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-gateway

WORKDIR /XHuiCloud-gateway

EXPOSE 15000

ADD ./target/XHuiCloud-gateway.jar ./

CMD sleep 60;java java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-gateway.jar
