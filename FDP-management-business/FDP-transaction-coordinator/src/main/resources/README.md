# Script usage demo
![Since 1.1.0](https://img.shields.io/badge/Since%20-1.1.0-orange.svg?style=flat-square)

## Nacos
shell:
```bash
sh ${SEATAPATH}/script/config-center/nacos/nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca
sh nacos-config.sh -h localhost -p 13000 -g SEATA_GROUP -t
```

Parameter Description:

-h: host, the default value is localhost.

-p: port, the default value is 8848.

-g: Configure grouping, the default value is 'SEATA_GROUP'.

-t: Tenant information, corresponding to the namespace ID field of Nacos, the default value is ''.

python:
```bash
python ${SEATAPATH}/script/config-center/nacos/nacos-config.py localhost:8848
```

