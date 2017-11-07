package com.piles.setting.service;

import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.setting.entity.BillRuleSetPushRequest;
import com.piles.setting.entity.BillRuleSetRequest;

/**
 * 远程关闭充电 运营中心主动调用服务
 */

public interface IBillRuleSetPushService {
    /**
     * 远程关闭充电 推送消息
     *
     * @param remoteClosePushRequest
     * @return
     */
    BasePushCallBackResponse<BillRuleSetRequest> doPush(BillRuleSetPushRequest remoteClosePushRequest);
}
