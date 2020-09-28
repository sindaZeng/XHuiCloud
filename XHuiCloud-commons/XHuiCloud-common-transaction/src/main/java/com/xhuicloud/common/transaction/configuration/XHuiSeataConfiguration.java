package com.xhuicloud.common.transaction.configuration;

import io.seata.rm.datasource.DataSourceProxy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;

/**
 * @program: XHuiCloud
 * @description: 分布式事务配置
 * @author: Sinda
 * @create: 2020-01-29 00:59
 */
@Slf4j
@AllArgsConstructor
public class XHuiSeataConfiguration implements BeanPostProcessor {

    /**
     *  修改为Seata的代理数据源
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource){
            return new DataSourceProxy((DataSource) bean);
        }
        return bean;
    }
}
