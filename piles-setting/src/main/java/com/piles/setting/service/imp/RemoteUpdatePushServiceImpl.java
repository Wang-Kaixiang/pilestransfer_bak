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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 远程升级 给充电桩发送消息实现类
 */
@Slf4j
@Service("remoteUpdatePushServiceImpl_1")
public class RemoteUpdatePushServiceImpl implements IRemoteUpdatePushService, InitializingBean {

    @Resource(name = "pushBusinessImpl")
    IPushBusiness pushBusiness;
    ExecutorService executorService = null;

    /**
     * 默认1分钟超时
     */
    @Value("${timeout:60000}")
    private long timeout;

    //线程池线程数量
    private int threadNum = 10;


    @Override
    public BasePushCallBackResponse<RemoteUpdateRequest> doPush(RemoteUpdatePushRequest remoteUpdatePushRequest) {
        byte[] pushMsg = RemoteUpdatePushRequest.packBytes(remoteUpdatePushRequest);
        BasePushCallBackResponse<RemoteUpdateRequest> basePushCallBackResponse = new BasePushCallBackResponse();
        basePushCallBackResponse.setSerial(remoteUpdatePushRequest.getSerial());
        boolean flag = pushBusiness.push(pushMsg, remoteUpdatePushRequest.getTradeTypeCode(), remoteUpdatePushRequest.getPileNo(), basePushCallBackResponse, ECommandCode.REMOTE_UPDATE_CODE);
        if (!flag) {
            basePushCallBackResponse.setCode(EPushResponseCode.CONNECT_ERROR);
            return basePushCallBackResponse;
        }
        try {
            CountDownLatch countDownLatch = basePushCallBackResponse.getCountDownLatch();
            countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            if(countDownLatch.getCount()>0){
                log.error("远程升级推送失败超时，厂商类型:{},桩号:{}",remoteUpdatePushRequest.getTradeTypeCode(),remoteUpdatePushRequest.getPileNo());
            }
            ChannelResponseCallBackMap.remove(remoteUpdatePushRequest.getTradeTypeCode(), remoteUpdatePushRequest.getPileNo(), remoteUpdatePushRequest.getSerial());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return basePushCallBackResponse;
    }

    /**
     * 远程升级 推送消息
     *
     * @param remoteUpdatePushRequestList
     * @return
     */
    @Override
    public BasePushCallBackResponse<RemoteUpdateRequest> doBatchPush(List<RemoteUpdatePushRequest> remoteUpdatePushRequestList) {
        if (CollectionUtils.isNotEmpty(remoteUpdatePushRequestList)) {
            for (RemoteUpdatePushRequest remoteUpdatePushRequest : remoteUpdatePushRequestList) {
                executorService.submit(() -> {
                            doPush(remoteUpdatePushRequest);
                        }
                );

            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = Executors.newFixedThreadPool(threadNum);

    }
}
