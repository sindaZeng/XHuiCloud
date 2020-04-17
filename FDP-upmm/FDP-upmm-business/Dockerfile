FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-upmm-business

WORKDIR /FDP-upmm-business

EXPOSE 17000

ADD ./target/FDP-upmm-business.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -jar FDP-upmm-business.jar


