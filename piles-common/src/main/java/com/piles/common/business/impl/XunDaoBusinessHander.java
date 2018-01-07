package com.piles.common.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import com.piles.common.business.IBusinessHandler;
import com.piles.common.util.CRC16Util;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 用于循道报文解析
 */
@Slf4j
@Component("xunDaoBusinessHandler")
public  class XunDaoBusinessHander implements IBusinessHandler {

    @Resource(name="xunDaoBusinessFactory")
    IBusinessFactory xunDaoBusinessFactory;

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        IBusiness business = xunDaoBusinessFactory.getByMsg(msg);
        //报文校验不通过则抛弃
        if (CRC16Util.checkMsg( msg )){
            log.info("CRC校验通过");
            return processService( msg,incoming );
        }
        log.error("CRC验证未通过");
        return null;
    }



    public byte[] processService(byte[] msg, Channel incoming) {
        IBusiness iBusiness = null;
//        iBusiness = xunDaoBusinessFactory.getByOrder( msg[1] );
        if (null != iBusiness) {
            return iBusiness.process( msg ,incoming);
        }
        return null;
    }
}
