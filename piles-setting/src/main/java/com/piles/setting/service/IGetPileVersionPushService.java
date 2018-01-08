package com.piles.setting.service;

import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.setting.entity.GetPileVersionReqeust;
import com.piles.setting.entity.RemoteUpdatePushRequest;
import com.piles.setting.entity.RemoteUpdateRequest;

/**
 * 获取充电桩版本 运营中心主动调用服务
 */

public interface IGetPileVersionPushService {
    /**
     * 远程升级 推送消息
     *
     * @param getPileVersionReqeust
     * @return
     */
    BasePushCallBackResponse<GetPileVersionReqeust> doPush(GetPileVersionReqeust getPileVersionReqeust);
}
