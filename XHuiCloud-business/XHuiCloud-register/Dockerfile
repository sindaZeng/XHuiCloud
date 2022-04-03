FROM openjdk:8u322

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-register

WORKDIR /XHuiCloud-register

EXPOSE 13000

ADD ./target/XHuiCloud-register.jar ./

CMD java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-register.jar


