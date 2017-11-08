package com.piles.setting.service.imp;

import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.setting.entity.VerifyTimePushRequest;
import com.piles.setting.entity.VerifyTimeRequest;
import com.piles.setting.service.IVerifyTimePushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 校时 给充电桩发送消息实现类
 */
@Slf4j
@Service
public class VerifyTimePushServiceImpl implements IVerifyTimePushService {

    @Autowired
    IPushBusiness pushBusiness;

    /**
     * 默认3秒超时
     */
    private long timeout = 3000L;


    @Override
    public BasePushCallBackResponse<VerifyTimeRequest> doPush(VerifyTimePushRequest verifyTimePushRequest) {
        byte[] pushMsg = VerifyTimePushRequest.packBytes( verifyTimePushRequest );
        BasePushCallBackResponse<VerifyTimeRequest> basePushCallBackResponse = new BasePushCallBackResponse();
        basePushCallBackResponse.setSerial( verifyTimePushRequest.getSerial() );
        boolean flag = pushBusiness.push( pushMsg, verifyTimePushRequest.getPileNo(), basePushCallBackResponse  , ECommandCode.VERIFY_TIME_CODE );
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
