FROM mysql:8.0.19

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./sys.sql /docker-entrypoint-initdb.d

COPY ./sys_register.sql /docker-entrypoint-initdb.d

COPY ./sys_job.sql /docker-entrypoint-initdb.d

COPY ./sys_seata.sql /docker-entrypoint-initdb.d
