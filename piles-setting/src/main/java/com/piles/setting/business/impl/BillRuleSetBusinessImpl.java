package com.piles.setting.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.setting.entity.BillRuleSetRequest;
import com.piles.setting.service.IBillRuleSetService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 计费规则设置
 */
@Slf4j
@Service("billRuleSetBusiness")
public class BillRuleSetBusinessImpl extends BaseBusiness {


    @Resource
    private IBillRuleSetService billRuleSetService;


    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        BillRuleSetRequest billRuleSetRequest = BillRuleSetRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = billRuleSetService.billRuleSet(billRuleSetRequest);
        byte[] pileNo = BytesUtil.copyBytes(bodyBytes, 0, 8);
        byte[] result = flag == true ? new byte[]{0x00} : new byte[]{0x01};
        byte[] responseBody = Bytes.concat(pileNo, result);
        //组装返回报文体
        return responseBody;
    }

    @Override
    public ECommandCode getReponseCode() {
        return ECommandCode.BILL_RULE_SET_ANSWER_CODE;
    }
}
