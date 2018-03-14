package com.piles.record.business.impl;


import com.piles.common.business.IBusiness;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.record.entity.XunDaoChargeMonitorRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 上传充电过程监测数据 接口实现
 */
@Slf4j
@Service("xunDaoChargeMonitorBusiness")
public class XunDaoChargeMonitorBusinessImpl implements IBusiness {

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        log.info( "接收到循道充电桩上传充电过程监测数据报文" );
        String order = String.valueOf( BytesUtil.xundaoControlByte2Int( BytesUtil.copyBytes( msg, 2, 4 ) ) );
        byte[] dataBytes = BytesUtil.copyBytes( msg, 13, (msg.length - 13) );
        //依照报文体规则解析报文
        XunDaoChargeMonitorRequest uploadChargeMonitorRequest = XunDaoChargeMonitorRequest.packEntity( dataBytes );
        log.info( "接收到循道充电桩上传充电过程监测数据报文:{}", uploadChargeMonitorRequest.toString() );

        ChannelResponseCallBackMap.callBack( incoming, order, uploadChargeMonitorRequest );
        //组装返回报文体
        return null;
    }

}
