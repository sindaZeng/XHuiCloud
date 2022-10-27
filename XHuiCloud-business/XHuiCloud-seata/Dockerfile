FROM openjdk:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-seata

WORKDIR /XHuiCloud-seata

EXPOSE 8091

ADD ./target/XHuiCloud-seata.jar ./

CMD java -Xms256m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar XHuiCloud-seata.jar
