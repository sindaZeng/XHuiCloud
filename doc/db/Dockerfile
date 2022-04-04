FROM mysql:8.0.28

MAINTAINER Sinda(sindazeng@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY 1schema.sql /docker-entrypoint-initdb.d

COPY 2xhuicloud_sys.sql /docker-entrypoint-initdb.d

COPY 3xhuicloud_register.sql /docker-entrypoint-initdb.d

COPY 4xhuicloud_job.sql /docker-entrypoint-initdb.d

COPY 5xhuicloud_seata.sql /docker-entrypoint-initdb.d

COPY 6xhuicloud_gen.sql /docker-entrypoint-initdb.d

COPY 7xhuicloud_push.sql /docker-entrypoint-initdb.d
