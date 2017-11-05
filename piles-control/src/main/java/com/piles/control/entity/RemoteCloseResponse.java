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
     * 订单号 8位 BIN
     */
    private String orderNo;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static RemoteStartResponse packEntity(byte[] msg){
        RemoteStartResponse request=new RemoteStartResponse();

//        request.setGunNo(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,0,1),10)));
//        request.setChargeModel(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,1,1),10)));
//        request.setChargeData(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,2,4))));
//        request.setChargeStopCode(BytesUtil.bcd2Str(BytesUtil.copyBytes(msg,6,2)));
//        request.setOrderNo(BytesUtil.binary(BytesUtil.copyBytes(msg,17,1),10));

        return request;
    }
    /**
     * 封装报文体
     * @param request
     * @return
     */
    public static byte[] packBytes(RemoteCloseResponse request){
        int gunNo = request.getGunNo();
        String orderNo = request.getOrderNo();
        byte[] gunNoBytes = BytesUtil.intToBytes(gunNo);
        //TODO 解析订单号的格式
        byte[] orderNoBytes = orderNo.getBytes();
        return Bytes.concat(gunNoBytes,orderNoBytes);
    }


    public static void main(String[] args) {
//        byte[] msg= new byte[]{0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
//        packEntity(msg);

    }


}
