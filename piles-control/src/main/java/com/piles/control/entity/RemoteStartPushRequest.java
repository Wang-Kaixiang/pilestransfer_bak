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
//    private BigDecimal chargeAmount;
//    private long chargeTime;
//    private BigDecimal chargeQuantity;
    /**
     * 充电停止码 2位  BCD
     * 用户在屏幕输入充电停止码，可结束充电
     */
    private String chargeStopCode;
    /**
     * 订单号 8位 BIN
     */
    private String orderNo;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static RemoteStartPushRequest packEntity(byte[] msg){
        RemoteStartPushRequest request=new RemoteStartPushRequest();

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
    public static byte[] packBytes(RemoteStartPushRequest request){
        int gunNo = request.getGunNo();
        int chargeModel = request.getChargeModel();
        BigDecimal chargeData = request.getChargeData();
        String chargeStopCode = request.getChargeStopCode();
        String orderNo = request.getOrderNo();
        byte[] gunNoBytes = BytesUtil.intToBytes(gunNo);
        byte[] chargeModelBytes = BytesUtil.intToBytes(chargeModel);
        byte[] chargeDataBytes = new byte[]{0x00};//默认使用充满
        //TODO 解析这个的格式
        if(chargeModel!=2){

        }else if(chargeModel==3){

        }else if(chargeModel==4){

        }
        byte[] chargeStopCodeBytes = BytesUtil.str2Bcd(chargeStopCode);
        //TODO 解析订单号的格式
        byte[] orderNoBytes = orderNo.getBytes();
        return Bytes.concat(gunNoBytes,chargeModelBytes,chargeDataBytes,chargeStopCodeBytes,orderNoBytes);
    }


    public static void main(String[] args) {
//        byte[] msg= new byte[]{0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
//        packEntity(msg);

    }



}
