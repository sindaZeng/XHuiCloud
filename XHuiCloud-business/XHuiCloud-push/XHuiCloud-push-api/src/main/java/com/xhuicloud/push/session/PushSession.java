package com.xhuicloud.push.session;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.BasePush;

public interface PushSession {

    Response push(BasePush basePush);

}
