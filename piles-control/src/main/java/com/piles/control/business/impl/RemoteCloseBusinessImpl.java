package com.piles.control.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.util.BytesUtil;
import com.piles.control.entity.LoginRequest;
import com.piles.control.entity.RemoteCloseRequest;
import com.piles.control.service.IRemoteCloseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 远程结束充电
 */
@Slf4j
@Service("remoteCloseBusiness")
public class RemoteCloseBusinessImpl extends BaseBusiness{

    //设置返回报文头命令

    @Resource
    private IRemoteCloseService remoteCloseService;



    @Override
    protected byte[] processBody(byte[] bodyBytes) {
        //依照报文体规则解析报文
        RemoteCloseRequest remoteCloseRequest = RemoteCloseRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = remoteCloseService.remoteClose(remoteCloseRequest);
        byte[] pileNo = BytesUtil.copyBytes(bodyBytes, 0, 8);
        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
        byte[] responseBody = Bytes.concat(pileNo,result);
        //组装返回报文体
        return responseBody;
    }
}
