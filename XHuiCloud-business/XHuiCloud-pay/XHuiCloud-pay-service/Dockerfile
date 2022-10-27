FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-pay-service

WORKDIR /XHuiCloud-pay-service

EXPOSE 23000

ADD ./target/XHuiCloud-pay-service.jar ./

CMD java -Xms512m -Xmx512m -Xss256k -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar XHuiCloud-pay-service.jar


