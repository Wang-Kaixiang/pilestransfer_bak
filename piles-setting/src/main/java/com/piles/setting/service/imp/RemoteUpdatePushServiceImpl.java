package com.piles.setting.service.imp;

import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.setting.entity.RemoteUpdatePushRequest;
import com.piles.setting.entity.RemoteUpdateRequest;
import com.piles.setting.service.IRemoteUpdatePushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 远程升级 给充电桩发送消息实现类
 */
@Slf4j
@Service
public class RemoteUpdatePushServiceImpl implements IRemoteUpdatePushService {

    @Autowired
    IPushBusiness pushBusiness;

    /**
     * 默认1分钟超时
     */
    @Value("${timeout:60000}")
    private long timeout;


    @Override
    public BasePushCallBackResponse<RemoteUpdateRequest> doPush(RemoteUpdatePushRequest remoteUpdatePushRequest) {
        byte[] pushMsg = RemoteUpdatePushRequest.packBytes( remoteUpdatePushRequest );
        BasePushCallBackResponse<RemoteUpdateRequest> basePushCallBackResponse = new BasePushCallBackResponse();
        basePushCallBackResponse.setSerial( remoteUpdatePushRequest.getSerial() );
        boolean flag = pushBusiness.push( pushMsg, remoteUpdatePushRequest.getPileNo(), basePushCallBackResponse  , ECommandCode.REMOTE_UPDATE_CODE );
        if (!flag) {
            basePushCallBackResponse.setCode( EPushResponseCode.CONNECT_ERROR );
            return basePushCallBackResponse;
        }
        try {
            basePushCallBackResponse.getCountDownLatch().await( timeout, TimeUnit.MILLISECONDS );
            ChannelResponseCallBackMap.remove( remoteUpdatePushRequest.getPileNo(),remoteUpdatePushRequest.getSerial() );
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error( e.getMessage(), e );
        }
        return basePushCallBackResponse;
    }
}
