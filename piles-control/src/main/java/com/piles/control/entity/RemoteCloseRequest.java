package com.piles.control.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 远程结束充电
 */
@Data
public class RemoteCloseRequest implements Serializable {

    /**
     * 订单号 8位 BIN
     */
    private String orderNo;
    /**
     * 结果 1位 BIN    0: 结束成功 1: 枪被预约 2: 其他原因失败
     */
    private int result;

    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static RemoteCloseRequest packEntity(byte[] msg) {
        RemoteCloseRequest request = new RemoteCloseRequest();
        request.setOrderNo(BytesUtil.binary(BytesUtil.copyBytes(msg, 0, 8), 10));
        request.setResult(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg, 8, 1), 10)));
        return request;
    }


    public static void main(String[] args) {
//        byte[] msg= new byte[]{0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
//        packEntity(msg);

    }

}
