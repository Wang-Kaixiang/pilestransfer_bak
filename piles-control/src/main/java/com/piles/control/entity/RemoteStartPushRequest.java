package com.piles.control.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.entity.BasePushRequest;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 远程开始充电
 */
@Data
public class RemoteStartPushRequest extends BasePushRequest implements Serializable
{
    /**
     * 抢号 1 位  BIN 1: A枪 2: B枪
     */
    private int gunNo;
    /**
     * 充电模式 1位 BIN
     * 1: 自动充满 2: 按金额充  3: 按时间充 4: 按电量充
     */
    private int chargeModel;
    /**
     * 充电数据 4位 BIN
     * 对应每种充电模式的数据。
     * 1：直到充满，填0
     * 2：按金额充，填金额大小，单位：元，精确到0.001
     * 3：按时间充，填时间长度，单位：秒
     * 4：按电量充，填电量大小，单位：度, 精确到0.001
     */
    private BigDecimal chargeData;
    /**
     * 充电停止码 2位  BCD
     * 用户在屏幕输入充电停止码，可结束充电
     */
    private String chargeStopCode;
    /**
     * 订单号 8位 BIN
     */
    private long orderNo;

    /**
     * 封装报文体
     * @param request
     * @return
     */
    public static byte[] packBytes(RemoteStartPushRequest request){
        int gunNo = request.getGunNo();
        int chargeModel = request.getChargeModel();
        BigDecimal chargeData = request.getChargeData();
        String chargeStopCode = request.getChargeStopCode();
        long orderNo = request.getOrderNo();
        byte[] gunNoBytes = BytesUtil.intToBytes(gunNo,1);
        byte[] chargeModelBytes = BytesUtil.intToBytes(chargeModel);
        int chargeDataInt = 0;
        if(chargeModel==2 || chargeModel==4){
            BigDecimal chargeDataVal = chargeData.multiply(BigDecimal.valueOf(1000));
            chargeDataInt = chargeDataVal.intValue();
        }else if(chargeModel==3){
            chargeDataInt = chargeData.intValue();
        }
        byte[] chargeDataBytes = BytesUtil.intToBytes(chargeDataInt,4);
        byte[] chargeStopCodeBytes = BytesUtil.str2Bcd(chargeStopCode);
        byte[] orderNoBytes = BytesUtil.long2Byte(orderNo);
        return Bytes.concat(gunNoBytes,chargeModelBytes,chargeDataBytes,chargeStopCodeBytes,orderNoBytes);
    }


    public static void main(String[] args) {
        RemoteStartPushRequest request = new RemoteStartPushRequest();
        request.setGunNo(1);
        request.setChargeModel(3);
        request.setChargeData(new BigDecimal(34324234L));
        request.setChargeStopCode("1245");
        request.setOrderNo(1223123L);
        RemoteStartPushRequest.packBytes(request);
    }



}
