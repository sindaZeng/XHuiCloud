FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-push-service

WORKDIR /XHuiCloud-push-service

EXPOSE 22000

ADD ./target/XHuiCloud-push-service.jar ./

CMD java -Xms512m -Xmx512m -Xss256k -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar XHuiCloud-push-service.jar
