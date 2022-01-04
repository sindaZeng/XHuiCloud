package com.xhuicloud.common.core.thread;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadBroker {

    @FunctionalInterface
    public interface Execute<T> {

        void execute(T t) throws Exception;

    }

    @FunctionalInterface
    public interface Submit<T, R> {

        R submit(T t) throws Exception;

    }

}


