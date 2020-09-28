FROM java:8-jre

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /FDP-generator

WORKDIR /FDP-generator

EXPOSE 21000

ADD ./target/FDP-generator.jar ./

CMD sleep 130;java -Xms128m -Xmx256m -jar FDP-generator.jar
