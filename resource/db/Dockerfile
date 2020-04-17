FROM mysql:5.7

MAINTAINER Sinda(sindazeng@gmail.com)

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./db/sys.sql /docker-entrypoint-initdb.d

COPY ./db/sys_config.sql /docker-entrypoint-initdb.d

COPY ./db/sys_job.sql /docker-entrypoint-initdb.d

COPY ./db/sys_seata.sql /docker-entrypoint-initdb.d
