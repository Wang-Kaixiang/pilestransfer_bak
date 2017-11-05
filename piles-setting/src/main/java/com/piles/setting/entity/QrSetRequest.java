package com.piles.setting.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 二维码设置
 */
@Data
public class QrSetRequest implements Serializable
{
    /**
     * 抢号 1 位  BIN 1: A枪 2: B枪
     */
    private int gunNo;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static QrSetRequest packEntity(byte[] msg){
        QrSetRequest request=new QrSetRequest();

        request.setGunNo(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,0,1),10)));
        return request;
    }


    public static void main(String[] args) {
        byte[] msg= new byte[]{0x01};
        QrSetRequest request = packEntity(msg);
        System.out.println(request.getGunNo());

    }



}
