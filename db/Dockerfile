FROM mysql:8.0.19

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./1schema.sql /docker-entrypoint-initdb.d

COPY ./2sys.sql /docker-entrypoint-initdb.d

COPY ./3sys_register.sql /docker-entrypoint-initdb.d

COPY ./4sys_job.sql /docker-entrypoint-initdb.d

COPY ./5sys_seata.sql /docker-entrypoint-initdb.d
