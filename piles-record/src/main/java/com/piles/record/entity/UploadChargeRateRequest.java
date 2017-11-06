package com.piles.record.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 上传充电进度接口请求实体
 */
@Data
public class UploadChargeRateRequest implements Serializable {

    //枪号	BIN	1	1: A枪 2: B枪
    private int gunNo;
    //订单号	BIN	8
    private long orderNo;
    //充电方式	BIN	1	1: 刷卡充电 2: APP充电
    private int chargeModel;
    //卡号	BCD	8	非卡充电全部置0
    private long cardNo;
    //车辆识别码(VIN)	ASCII	17
    private String vin;
    //SOC	BIN	1	数据范围0~100
    private int soc;
    //开始时间	BCD	6	格式: YYMMDDHHMMSS
    private String startTime;
    //结束时间	BCD	6	格式: YYMMDDHHMMSS
    private String endTime;
    //当前充电总电量	BIN	4	单位: 度，精确度为0.001
    private BigDecimal totalAmmeterDegree;
    //尖时电量	BIN	4	单位: 度，精确度为0.001
    private BigDecimal pointElectricQuantity;
    //峰时电量	BIN	4	单位: 度，精确度为0.001
    private BigDecimal peakElectricQuantity;
    //平时电量	BIN	4	单位: 度，精确度为0.001
    private BigDecimal ordinaryElectricQuantity;
    //谷时电量	BIN	4	单位: 度，精确度为0.001
    private BigDecimal dipElectricQuantity;
    //当前充电费总金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal totalElectricAmount;
    //尖时电费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal pointElectricAmount;
    //峰时电费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal peakElectricAmount;
    //平时电费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal ordinaryElectricAmount;
    //谷时电费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal dipElectricAmount;
    //预约费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal subscriptionAmount;
    //服务费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal serviceAmount;
    //停车费金额	BIN	4	单位: 元，精确度为0.001
    private BigDecimal parkingAmount;
    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static UploadChargeRateRequest packEntity(byte[] msg) {
        UploadChargeRateRequest request = new UploadChargeRateRequest();
        int cursor = 0;
        request.setGunNo(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,cursor,1),10)));
        cursor+=1;
        request.setOrderNo(BytesUtil.byte2Long(BytesUtil.copyBytes(msg,cursor,8)));
        cursor+=8;
        request.setChargeModel(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,cursor,1),10)));
        cursor+=1;
        request.setCardNo(BytesUtil.byte2Long(BytesUtil.copyBytes(msg,cursor,8)));
        cursor+=8;
        //TODO String 类型直接用new String
        request.setVin(new String(BytesUtil.copyBytes(msg,cursor,17)));
        cursor+=17;
        request.setSoc(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,cursor,1),10)));
        cursor+=1;
        request.setStartTime(BytesUtil.bcd2Str(BytesUtil.copyBytes(msg,cursor,6)));
        cursor+=6;
        request.setEndTime(BytesUtil.bcd2Str(BytesUtil.copyBytes(msg,cursor,6)));
        cursor+=6;
        //TODO 是否需要除以1000
        request.setTotalAmmeterDegree(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setPointElectricQuantity(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setPeakElectricQuantity(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setOrdinaryElectricQuantity(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setDipElectricQuantity(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setTotalElectricAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setPointElectricAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setPeakElectricAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setOrdinaryElectricAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setDipElectricAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setSubscriptionAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setServiceAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        cursor+=4;
        request.setParkingAmount(BigDecimal.valueOf(BytesUtil.bytesToInt(BytesUtil.copyBytes(msg, cursor, 4), 0)).divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP));
        return request;
    }


    public static void main(String[] args) {
        byte[] msg = new byte[]{0x03, 0x00, 0x01, 0x02};
        packEntity(msg);

    }


}
