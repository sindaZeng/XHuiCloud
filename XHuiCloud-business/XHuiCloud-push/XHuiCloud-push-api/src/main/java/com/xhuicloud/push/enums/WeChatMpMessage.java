package com.xhuicloud.push.enums;

public enum WeChatMpMessage implements BasePushMessage{

    /**
     * {first.DATA}登录账号：{keyword1.DATA}登录APP：{keyword2.DATA}登录时间：{keyword3.DATA}登录地点：{keyword4.DATA}{remark.DATA}
     */
    LOGIN_SUCCESS{
        @Override
        public String templateId() {
            return "ZJBTvt518iJwAK3i-8g0kmoGY9WwN0ol-kX5vn2YITw";
        }

        @Override
        public String[] paramNames() {
            return new String[]{"first.DATA","keyword1.DATA","keyword2.DATA","keyword3.DATA","keyword4.DATA","remark.DATA"};
        }


    },

    ;

    @Override
    public String[] paramNames() {
        return new String[0];
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public String source() {
        return null;
    }

    @Override
    public String content() {
        return null;
    }

    @Override
    public String templateId() {
        return null;
    }
}
