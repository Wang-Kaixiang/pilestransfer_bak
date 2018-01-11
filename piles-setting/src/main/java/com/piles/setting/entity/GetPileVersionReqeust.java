package com.piles.setting.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.entity.BasePushRequest;
import com.piles.common.entity.BasePushResponse;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.CRC16Util;
import lombok.Data;

/**
 * Created by lgc on 18/1/8.
 */
@Data
public class GetPileVersionReqeust extends BasePushResponse {
    /**
     * 充电桩编号
     */
    private String pileNo;
    /**
     *厂商类型
     */
    private TradeType tradeType;
    /**
     *厂商编号
     */
    private String merchantNo;
    /**
     *软件版本号
     */
    private String softVersion;

    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static GetPileVersionReqeust packEntityXunDao(byte[] msg) {
        GetPileVersionReqeust request = new GetPileVersionReqeust();
        request.setPileNo(BytesUtil.bcd2StrLittle(BytesUtil.copyBytes(msg, 13, 8)));
        request.setSoftVersion(BytesUtil.ascii2StrLittle(BytesUtil.copyBytes(msg, 21, 20)));
        request.setMerchantNo(BytesUtil.ascii2StrLittle(BytesUtil.copyBytes(msg, 41, 20)));
        request.setTradeType(TradeType.XUN_DAO);
        return request;
    }

}
