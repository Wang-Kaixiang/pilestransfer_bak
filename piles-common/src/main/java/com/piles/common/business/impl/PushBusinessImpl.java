package com.piles.common.business.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.common.util.ChannelMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushBusinessImpl implements IPushBusiness {
    @Override
    public boolean push(byte[] msg, String pileNo, BasePushCallBackResponse basePushRequest) {
        //获取连接channel 获取不到无法推送
        Channel channel=ChannelMap.getChannel(pileNo);
        if (null!=channel){
            ChannelFuture channelFuture=channel.writeAndFlush(msg);
            channelFuture.addListener( new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    basePushRequest.setCode( EPushResponseCode.WRITE_OK );
                    basePushRequest.getCountDownLatch().countDown();
                }
            } );
            return true;
        }else {
            log.error(pileNo+"无法获取到长连接,请检查充电桩连接状态");
            return false;
        }
    }
}
