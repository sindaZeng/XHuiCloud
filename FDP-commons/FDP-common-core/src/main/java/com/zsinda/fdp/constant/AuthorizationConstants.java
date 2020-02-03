package com.zsinda.fdp.constant;

/**
 * @author: Sinda
 * @create: 2020-01-01 22:58
 */
public interface AuthorizationConstants {

    /**
     * 角色前缀
     */
    String ROLE_PREFIX ="ROLE_";

    /**
     * 是来自内部服务调用
     */
    String IS_COMMING_INNER_YES ="Y";

    /**
     * 不是来自内部服务调用
     */
    String IS_COMMING_INNER_NO ="N";

    /**
     * 标志
     */
    String FROM = "from";


    /**
     * 用户锁定
     */
    String USER_IS_LOCK = "1";

    String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
            + " from sys_client_details";

    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

}
