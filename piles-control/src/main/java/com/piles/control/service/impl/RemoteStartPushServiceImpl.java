package com.piles.control.service.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.entity.RemoteCloseRequest;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.entity.RemoteStartRequest;
import com.piles.control.service.IRemoteClosePushService;
import com.piles.control.service.IRemoteStartPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 远程开始充电给充电桩发送消息实现类
 */
@Slf4j
@Service
public class RemoteStartPushServiceImpl implements IRemoteStartPushService {
    @Autowired
    IPushBusiness pushBusiness;


    @Override
    public BasePushCallBackResponse<RemoteStartRequest> doPush(RemoteStartPushRequest remoteStartPushRequest) {
        byte[] pushMsg=RemoteStartPushRequest.packBytes(remoteStartPushRequest);
        BasePushCallBackResponse<RemoteStartRequest> basePushCallBackResponse=new BasePushCallBackResponse();
        basePushCallBackResponse.setSerial( remoteStartPushRequest.getSerial() );
        boolean flag= pushBusiness.push(pushMsg,remoteStartPushRequest.getPileNo(),basePushCallBackResponse, ECommandCode.REMOTE_CHARGE_CODE);
        if (!flag){
            basePushCallBackResponse.setCode( EPushResponseCode.CONNECT_ERROR );
            return basePushCallBackResponse;
        }
        try {
            basePushCallBackResponse.getCountDownLatch().await(timeout, TimeUnit.MILLISECONDS);
            ChannelResponseCallBackMap.remove( remoteStartPushRequest.getPileNo(),remoteStartPushRequest.getSerial() );
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error( e.getMessage(),e );
        }
        return basePushCallBackResponse;
    }

    /**
     * 默认3秒超时
     */
    private long timeout=60000L;
}
