FROM openjdk:8u322

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-seata

WORKDIR /XHuiCloud-seata

EXPOSE 8091

ADD ./target/XHuiCloud-seata.jar ./

CMD sleep 50;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-seata.jar
