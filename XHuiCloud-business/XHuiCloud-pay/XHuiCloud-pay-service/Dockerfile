FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-pay-business

WORKDIR /FDP-pay-business

EXPOSE 20000

ADD ./target/FDP-pay-business.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -jar FDP-pay-business.jar


