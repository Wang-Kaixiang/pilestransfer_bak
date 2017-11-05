package com.piles.common.business.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.common.util.ChannelMap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PushBusinessImpl implements IPushBusiness {
    @Override
    public boolean push(byte[] msg,String pileNo) {
        //获取连接channel 获取不到无法推送
        Channel channel=ChannelMap.getChannel(pileNo);
        if (null!=channel){
            channel.writeAndFlush(msg);
            return true;
        }else {
            log.error(pileNo+"无法获取到长连接,请检查充电桩连接状态");
            return false;
        }
    }
}
