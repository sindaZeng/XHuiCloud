FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /XHuiCloud-upmm-business

WORKDIR /XHuiCloud-upmm-business

EXPOSE 17000

ADD ./target/XHuiCloud-upmm-business.jar ./

CMD sleep 50;java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar XHuiCloud-upmm-business.jar


