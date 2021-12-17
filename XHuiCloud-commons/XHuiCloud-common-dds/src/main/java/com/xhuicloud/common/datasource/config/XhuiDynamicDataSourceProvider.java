package com.xhuicloud.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.xhuicloud.common.datasource.entity.SysTenantDs;
import com.xhuicloud.common.datasource.enums.DsJdbcUrlEnum;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XhuiDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

    private final DataSourceProperties properties;

    public XhuiDynamicDataSourceProvider(DataSourceProperties properties) {
        super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), properties.getPassword());
        this.properties = properties;
    }

    /**
     * 执行语句获得数据源参数
     * @param statement 语句
     * @return 数据源参数
     * @throws SQLException sql异常
     */
    @SneakyThrows
    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery(properties.getQueryDsSql());
        Map<String, DataSourceProperty> map = new HashMap<>(8);
        List<SysTenantDs> sysTenantDs = populate(rs, SysTenantDs.class);
        for (SysTenantDs ds : sysTenantDs) {
            String url;
            DsJdbcUrlEnum dsJdbcUrlEnum = DsJdbcUrlEnum.get(ds.getType());
            if (DsJdbcUrlEnum.MSSQL == dsJdbcUrlEnum) {
                url = String.format(dsJdbcUrlEnum.getUrl(), ds.getHost(), ds.getPort(), ds.getDsName());
            }else {
                url = String.format(dsJdbcUrlEnum.getUrl(), ds.getHost(), ds.getPort(), ds.getDsName());
            }

            DataSourceProperty property = new DataSourceProperty();
            property.setUsername(ds.getUsername());
            property.setPassword(ds.getPassword());
            property.setUrl(url);
            map.put(ds.getName(), property);
        }

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setUsername(properties.getUsername());
        property.setPassword(properties.getPassword());
        property.setUrl(properties.getUrl());
        map.put("master", property);
        return map;
    }

    public static <T> List<T> populate(ResultSet rs , Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException{
        //结果集的元素对象
        ResultSetMetaData rsmd = rs.getMetaData();
        //获取结果集的元素个数
        int colCount = rsmd.getColumnCount();

        List<T> list = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        while(rs.next()){//对每一条记录进行操作
            T obj = clazz.newInstance();//构造业务对象实体
            //将每一个字段取出进行赋值
            for(int i = 1;i<=colCount;i++){
                Object value = rs.getObject(i);
                //寻找该列对应的对象属性
                for(int j=0;j<fields.length;j++){
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if(f.getName().equalsIgnoreCase(rsmd.getColumnName(i))){
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }
}