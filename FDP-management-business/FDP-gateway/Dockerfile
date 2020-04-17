FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-gateway

WORKDIR /FDP-gateway

EXPOSE 15000

ADD ./target/FDP-gateway.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -jar FDP-gateway.jar
