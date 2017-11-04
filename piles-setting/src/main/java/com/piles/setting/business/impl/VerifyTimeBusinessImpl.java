package com.piles.setting.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.setting.entity.VerifyTimeRequest;
import com.piles.setting.service.IVerifyTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 校时
 */
@Slf4j
@Service("verifyTimeBusiness")
public class VerifyTimeBusinessImpl extends BaseBusiness{

    //设置返回报文头命令
    ECommandCode responseCode = ECommandCode.VERIFY_TIME_ANSWER_CODE;

    @Resource
    private IVerifyTimeService verifyTimeService;


    @Override
    protected byte[] processBody(byte[] bodyBytes) {
        //依照报文体规则解析报文
        VerifyTimeRequest verifyTimeRequest = VerifyTimeRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = verifyTimeService.verify(verifyTimeRequest);
        byte[] pileNo = BytesUtil.copyBytes(bodyBytes, 0, 8);
        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
        byte[] responseBody = Bytes.concat(pileNo,result);
        //组装返回报文体
        return responseBody;
    }
}
