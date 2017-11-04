package com.piles.setting.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.setting.entity.RemoteUpdateRequest;
import com.piles.setting.service.IRemoteUpdateService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 远程升级
 */
@Slf4j
@Service("remoteUpdateBusiness")
public class RemoteUpdateBusinessImpl extends BaseBusiness{

    //设置返回报文头命令
    ECommandCode responseCode = ECommandCode.REMOTE_UPDATE_ANSWER_CODE;

    @Resource
    private IRemoteUpdateService remoteUpdateService;



    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        RemoteUpdateRequest remoteUpdateRequest = RemoteUpdateRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = remoteUpdateService.remoteUpdate(remoteUpdateRequest);
        byte[] pileNo = BytesUtil.copyBytes(bodyBytes, 0, 8);
        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
        byte[] responseBody = Bytes.concat(pileNo,result);
        //组装返回报文体
        return responseBody;
    }
}
