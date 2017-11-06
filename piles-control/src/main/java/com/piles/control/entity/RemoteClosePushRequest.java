package com.piles.control.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.entity.BasePushRequest;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 远程结束充电
 */
@Data
public class RemoteClosePushRequest extends BasePushRequest implements Serializable
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
    public static byte[] packBytes(RemoteClosePushRequest request){
        int gunNo = request.getGunNo();
        long orderNo = request.getOrderNo();
        byte[] gunNoBytes = BytesUtil.intToBytes(gunNo,1);
        byte[] orderNoBytes = BytesUtil.long2Byte(orderNo);
        return Bytes.concat(gunNoBytes,orderNoBytes);
    }


    public static void main(String[] args) {
        RemoteClosePushRequest remoteCloseResponse = new RemoteClosePushRequest();
        remoteCloseResponse.setGunNo(1);
        remoteCloseResponse.setOrderNo(4545454L);
        byte[] bytes = RemoteClosePushRequest.packBytes(remoteCloseResponse);
        System.out.println(BytesUtil.byte2Long(BytesUtil.copyBytes(bytes,1,bytes.length-1)));

    }


}
