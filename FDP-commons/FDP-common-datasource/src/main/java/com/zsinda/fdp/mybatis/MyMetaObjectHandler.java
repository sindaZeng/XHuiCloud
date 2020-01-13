//package com.zsinda.fdp.mybatis;
//
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        Object createTime = getFieldValByName("createTime", metaObject);
//        Object updateTime = getFieldValByName("updateTime", metaObject);
//        if(createTime == null) {
//            setFieldValByName("createTime", new Timestamp(System.currentTimeMillis()), metaObject);
//        }
//        if(updateTime == null) {
//            setFieldValByName("updateTime", new Timestamp(System.currentTimeMillis()), metaObject);
//        }
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        Object updateTime = getFieldValByName("updateTime", metaObject);
//        if(updateTime == null) {
//            setFieldValByName("updateTime", new Timestamp(System.currentTimeMillis()), metaObject);
//        }
//    }
//}
