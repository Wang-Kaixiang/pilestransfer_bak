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
public class GetPileVersionPushReqeust extends BasePushRequest {

    /**
     * 厂商编号
     */
    private String merchantNo;
    /**
     * 软件版本号
     */
    private String softVersion;


    /**
     * 解析报文并封装request体
     *
     * @param getPileVersionPushReqeusts
     * @return
     */
    public static byte[] packBytesXunDao(GetPileVersionPushReqeust getPileVersionPushReqeusts) {
        byte[] data = BytesUtil.str2BcdLittle(getPileVersionPushReqeusts.getPileNo());
        byte[] head = new byte[]{0x68};
        byte[] length = new byte[]{0x19};
        byte[] contrl = BytesUtil.xundaoControlInt2Byte(Integer.parseInt(getPileVersionPushReqeusts.getSerial()));
        byte[] type = new byte[]{(byte) 0x133};
        byte[] beiyong = new byte[]{0x00};
        byte[] reason = new byte[]{0x03, 0x00};
        byte[] crc = CRC16Util.getXunDaoCRC(data);
        byte[] addr = new byte[]{0x31};


        byte[] temp = Bytes.concat(head, length, contrl, type, beiyong, reason, crc, addr, data);
        return temp;
    }
}
