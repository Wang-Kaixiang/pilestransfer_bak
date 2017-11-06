package com.piles.record.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 上传充电进度接口返回实体
 */
@Data
public class UploadChargeRateResponse implements Serializable
{
    /**
     * 订单号 8位 BIN
     */
    private long orderNo;
    /**
     * 结果 1位 BIN    0: 接收成功 1: 接收失败
     */
    private int result;

    /**
     * 封装报文体
     * @param request
     * @return
     */
    public static byte[] packBytes(UploadChargeRateResponse reponse){
        long orderNo = reponse.getOrderNo();
        int result = reponse.getResult();

        byte[] orderNoBytes = BytesUtil.long2Byte(orderNo);
        byte[] resultBytes = BytesUtil.intToBytes(result);
        return Bytes.concat(orderNoBytes,resultBytes);
    }


    public static void main(String[] args) {
//        byte[] msg= new byte[]{0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
//        packEntity(msg);

    }



}
