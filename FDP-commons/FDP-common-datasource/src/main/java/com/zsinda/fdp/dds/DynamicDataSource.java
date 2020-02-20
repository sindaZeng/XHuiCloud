package com.zsinda.fdp.dds;

import com.zsinda.fdp.context.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * @author X
 * @date 2019/09/03
 * 动态数据源实现类
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 数据源路由，选取的数据源逻辑名称
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }

}
