FROM mysql:8.0.19

MAINTAINER Sinda(sindazeng@gmail.com)

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./sys.sql /docker-entrypoint-initdb.d

COPY ./sys_config.sql /docker-entrypoint-initdb.d

COPY ./sys_job.sql /docker-entrypoint-initdb.d

COPY ./sys_seata.sql /docker-entrypoint-initdb.d
