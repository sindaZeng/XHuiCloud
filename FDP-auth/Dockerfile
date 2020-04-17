FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-auth

WORKDIR /FDP-auth

EXPOSE 16000

ADD ./target/FDP-auth.jar ./

CMD sleep 120;java -Xms128m -Xmx256m -jar FDP-auth.jar
