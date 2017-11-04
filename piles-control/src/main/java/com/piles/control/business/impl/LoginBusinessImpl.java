package com.piles.control.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelMap;
import com.piles.control.entity.LoginRequest;
import com.piles.control.service.ILoginService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录接口逻辑
 */
@Slf4j
@Component
public class LoginBusinessImpl extends BaseBusiness{

    //设置返回报文头命令
    ECommandCode responseCode = ECommandCode.LOGIN_ANSWER_CODE;

    @Resource
    private ILoginService loginService;

//    @Override
//    public SocketBaseDTO process(byte[] msg) {
//        System.out.println("接收到的原始请求报文：" + new String(msg));
//        return null;
//    }

    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        LoginRequest loginRequest = LoginRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = loginService.login(loginRequest);
        if (flag){
            ChannelMap.addChannel(loginRequest.getPileNo(),incoming);
            ChannelMap.addChannel(incoming,loginRequest.getPileNo());
        }
        byte[] pileNo = BytesUtil.copyBytes(bodyBytes, 0, 8);
        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
        byte[] responseBody = Bytes.concat(pileNo,result);
        //组装返回报文体
        return responseBody;
    }
}
