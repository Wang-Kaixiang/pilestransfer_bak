package com.piles.common.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import com.piles.common.business.IBusinessHandler;
import com.piles.common.util.CRC16Util;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public  class BusinessHander implements IBusinessHandler {
    @Autowired
    IBusinessFactory businessFactory;

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        //报文校验不通过则抛弃
        if (CRC16Util.checkMsg( msg )){
            return processService( msg,incoming );
        }
        return null;
    }



    public byte[] processService(byte[] msg, Channel incoming) {
        IBusiness iBusiness = null;
        iBusiness = businessFactory.getByOrder( msg[1] );
        if (null != iBusiness) {
            return iBusiness.process( msg ,incoming);
        }
        return null;
    }
}
