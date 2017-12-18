package com.piles.record.business.impl;


import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelMap;
import com.piles.record.entity.HeartBeatRequest;
import com.piles.record.service.IHeartBeatService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 心跳接口实现
 */
@Slf4j
@Service("heartBeatBusiness")
public class HeartBeatBusinessImpl extends BaseBusiness {


    private static SimpleDateFormat sdf = new SimpleDateFormat( "yyMMddHHmmss" );

    @Resource
    IHeartBeatService heartBeatService;

    @Override
    protected byte[] processBody(byte[] bodyBytes, Channel incoming, int order) {
        log.info( "接收到充电桩心跳报文" );
        //依照报文体规则解析报文
        HeartBeatRequest heartBeatRequest = HeartBeatRequest.packEntity( bodyBytes );
        log.info( "接收到充电桩心跳报文:{}", heartBeatRequest.toString() );
        if (null!=ChannelMap.getChannel( heartBeatRequest.getPileNo() )&&!ChannelMap.getChannel( heartBeatRequest.getPileNo() ).remoteAddress().equals( incoming.remoteAddress() )) {
            log.error( "--------------------充电桩通道变更 原来是" + ChannelMap.getChannel( heartBeatRequest.getPileNo() ).remoteAddress() + "现在的：" + incoming.remoteAddress() );
            ChannelMap.removeChannel( heartBeatRequest.getPileNo() );
        }
        if (null == ChannelMap.getChannel( heartBeatRequest.getPileNo() )) {
            ChannelMap.addChannel( heartBeatRequest.getPileNo(), incoming );
            ChannelMap.addChannel( incoming, heartBeatRequest.getPileNo() );
        }
        // 不需要接调用底层接口
        Date date = heartBeatService.heartBeat( heartBeatRequest );
        byte[] responseBody = BytesUtil.str2Bcd( sdf.format( date ) );
        //组装返回报文体
        return responseBody;
    }

    @Override
    public ECommandCode getReponseCode() {
        return ECommandCode.HEART_BEAT_ANSWER_CODE;
    }

}
