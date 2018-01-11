package com.piles.setting.service;

import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.setting.entity.GetPileVersionPushReqeust;

/**
 * 获取充电桩版本 运营中心主动调用服务
 */

public interface IGetPileVersionPushService {
    /**
     * 远程升级 推送消息
     *
     * @param getPileVersionPushReqeust
     * @return
     */
    BasePushCallBackResponse<GetPileVersionPushReqeust> doPush(GetPileVersionPushReqeust getPileVersionPushReqeust);
}
