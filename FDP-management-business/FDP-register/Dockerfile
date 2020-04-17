FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-register

WORKDIR /FDP-register

EXPOSE 13000

ADD ./target/FDP-register.jar ./

CMD sleep 60;java -Xms128m -Xmx256m -jar FDP-register.jar


