package com.piles.setting.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.entity.BasePushResponse;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.CRC16Util;
import lombok.Data;

import java.math.BigDecimal;

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
        byte[] data = BytesUtil.str2BcdLittle(getPileVersionReqeusts.getPileNo());
        byte[] head = new byte[]{0x68};
        byte[] length = new byte[]{0x19};
        byte[] contrl = BytesUtil.xundaoControlInt2Byte(Integer.parseInt(getPileVersionReqeusts.getSerial()));
        byte[] type = new byte[]{(byte) 0x133};
        byte[] beiyong = new byte[]{0x00};
        byte[] reason = new byte[]{0x03, 0x00};
        byte[] crc = CRC16Util.getXunDaoCRC(data);
        byte[] addr = new byte[]{0x31};


        byte[] temp = Bytes.concat(head, length, contrl, type, beiyong, reason, crc, addr, data);
        return temp;
    }
}
