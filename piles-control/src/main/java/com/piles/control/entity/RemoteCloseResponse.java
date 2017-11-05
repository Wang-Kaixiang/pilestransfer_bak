package com.piles.control.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 远程结束充电
 */
@Data
public class RemoteCloseResponse implements Serializable
{


    /**
     * 抢号 1 位  BIN 1: A枪 2: B枪
     */
    private int gunNo;
    /**
     * 订单号 8位 BIN long
     */
    private long orderNo;

    /**
     * 封装报文体
     * @param request
     * @return
     */
    public static byte[] packBytes(RemoteCloseResponse request){
        int gunNo = request.getGunNo();
        long orderNo = request.getOrderNo();
        byte[] gunNoBytes = BytesUtil.intToBytes(gunNo,1);
        //TODO 确定orderNo是否是long型
        byte[] orderNoBytes = BytesUtil.long2Byte(orderNo);
        return Bytes.concat(gunNoBytes,orderNoBytes);
    }


    public static void main(String[] args) {
        RemoteCloseResponse remoteCloseResponse = new RemoteCloseResponse();
        remoteCloseResponse.setGunNo(1);
        remoteCloseResponse.setOrderNo(4545454l);
        byte[] bytes = RemoteCloseResponse.packBytes(remoteCloseResponse);
        System.out.println(BytesUtil.byte2Long(BytesUtil.copyBytes(bytes,1,bytes.length-1)));
//        byte[] msg= new byte[]{0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
//        packEntity(msg);

    }


}
