package com.xhuicloud.push.service;

import com.xhuicloud.common.core.utils.Response;
import com.xhuicloud.push.common.BasePush;
import com.xhuicloud.push.common.PushMultiDiff;
import com.xhuicloud.push.common.PushMultiple;
import com.xhuicloud.push.common.PushSingle;

public interface PushCommonService {

    void toQueue(BasePush basePush);

    Response single(PushSingle pushSingle);

    Response multiple(PushMultiple pushMultiple);

    Response multiDiff(PushMultiDiff pushMultiDiff);

}
