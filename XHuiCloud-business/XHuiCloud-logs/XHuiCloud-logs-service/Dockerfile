FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-logs-business

WORKDIR /FDP-logs-business

EXPOSE 18000

ADD ./target/FDP-logs-business.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -jar FDP-logs-business.jar
