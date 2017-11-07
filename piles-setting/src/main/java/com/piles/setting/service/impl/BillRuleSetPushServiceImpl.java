package com.piles.setting.service.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.setting.entity.BillRuleSetPushRequest;
import com.piles.setting.entity.BillRuleSetRequest;
import com.piles.setting.service.IBillRuleSetPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 计费规则设置 给充电桩发送消息实现类
 */
@Slf4j
@Service
public class BillRuleSetPushServiceImpl implements IBillRuleSetPushService {

    @Autowired
    IPushBusiness pushBusiness;

    /**
     * 默认3秒超时
     */
    private long timeout = 3000L;

    @Override
    public BasePushCallBackResponse<BillRuleSetRequest> doPush(BillRuleSetPushRequest remoteClosePushRequest) {
        byte[] pushMsg = BillRuleSetPushRequest.packBytes( remoteClosePushRequest );
        BasePushCallBackResponse<BillRuleSetRequest> basePushCallBackResponse = new BasePushCallBackResponse();
        boolean flag = pushBusiness.push( pushMsg, remoteClosePushRequest.getPileNo(), basePushCallBackResponse );
        if (!flag) {
            basePushCallBackResponse.setCode( EPushResponseCode.CONNECT_ERROR );
            return basePushCallBackResponse;
        }
        try {
            basePushCallBackResponse.getCountDownLatch().await( timeout, TimeUnit.MILLISECONDS );
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error( e.getMessage(), e );
        }
        return basePushCallBackResponse;
    }
}
