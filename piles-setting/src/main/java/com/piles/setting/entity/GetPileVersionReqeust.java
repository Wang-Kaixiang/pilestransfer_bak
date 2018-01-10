package com.piles.setting.entity;

import com.piles.common.entity.BasePushResponse;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.BytesUtil;
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
        //TODO 解析参数
        request.setPileNo("");
        request.setSoftVersion("");
        request.setMerchantNo("");
        request.setTradeType(TradeType.XUN_DAO);
        return request;
    }
    /**
     * 解析报文并封装request体
     *
     * @param getPileVersionReqeusts
     * @return
     */
    public static byte[] packBytesXunDao(GetPileVersionReqeust getPileVersionReqeusts) {
        //TODO 解析参数

        return new byte[]{};
    }
}
