package com.piles.record.entity;

import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 循道上传充电记录接口请求实体
 */
@Data
public class XunDaoUploadRecordRequest implements Serializable {

    /**
     * 桩编号 8位 BCD
     */
    private String pileNo;
    //交易流水号	BCD 码16Byte
    private String orderNo;
    //物理卡号 BIN 码 8Byte
    private long physicCardNo;
    //用户卡号 BCD 码 8Byte
    private String userCardNo;
    //分时计费标志 BCD 码 1Byte  0:分时
    private String subTimeBillFlag;
    //开始时间 BIN 码 7Byte 小端 CP56Time2a 格式
    private long startTime;
    //结束时间 BIN 码 7Byte 小端 CP56Time2a 格式
    private long endTime;
    //每半小时电量 BIN 码 2Byte 精确到小数点后两位 0:00-0:30 电量----23:30-24:00 电量
    private List<BigDecimal> everyHalfHourDegress;
    //总电量 BIN 码 4Byte 小端 精确到小数点后两位
    private BigDecimal totalAmmeterDegree;
    //计量示数类型 BCD 码 2Byte  小端  0002-充电量
    private String measureNumberType;
    //业务类型 BCD 码 2Byte 0x0001-充电，0x0002-换电， 0x0011-二维码充电， 0x0021-密码充电 比如 0x0021，上送 0x21,0x00
    private String bizType;
    //电子钱包余额 BIN 码 4Byte 本次消费完之后电子钱包余额
    private int eWalletBalance;
    //备用 BIN 码 4Byte 全部置 0
    private int reserved;
    // 本次消费金额 BIN 码 4Byte 精确到小数点后两位
    private BigDecimal currentExpenseAmount;
    //交易标识 BIN 1Byte 0x01 - 钱包完整的交易; 0x02 - -钱包未完成的交易; 0x03 - 钱包后台完整后变为 3
    //0x04 - 在线刷卡完整的交易; 0x05 - 在线刷卡未完成的交易;
    //0x06 - 在线刷卡后台完整后 变为 6; 0x11 - 二维码充电交易记录 0x21 - 密码充电交易记录
    private int transactionFlag;
    //开始 SOC BIN 码 1Byte 精确到 1%，1 表示 SOC=1% 类 推
    private int beginSoc;
    //结束 SOC BIN 码 1Byte 精确到 1%，1 表示 SOC=1% 类 推
    private int endSoc;
    // 停止充电原因 BIN 码 1Byte 0x01-余额不足 0x02-定费用到 0x03-定电量到 0x04-定时间到 0x05-汽车主动停止
    // 0x06-人工正常停止 0x07-输出失败 0x08-系统故障 0x09-未结账 0x0A-CP 异常 0x0B-意外断电
    private int stopChargeReason;

    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static XunDaoUploadRecordRequest packEntity(byte[] msg) {
        XunDaoUploadRecordRequest request = new XunDaoUploadRecordRequest();
        int cursor = 0;
        request.setPileNo( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 8 ) ) );
        cursor += 8;
        request.setOrderNo( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 16 ) ) );
        cursor += 16;
        request.setPhysicCardNo( BytesUtil.byte2LongLittle( BytesUtil.copyBytes( msg, cursor, 8 ) ) );
        cursor += 8;
        request.setUserCardNo( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 8 ) ) );
        cursor += 8;
        request.setSubTimeBillFlag( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        cursor += 1;
        request.setStartTime( BytesUtil.byte2LongLittle( BytesUtil.copyBytes( msg, cursor, 7 ) ) );
        cursor += 7;
        request.setEndTime( BytesUtil.byte2LongLittle( BytesUtil.copyBytes( msg, cursor, 7 ) ) );
        cursor += 7;
        //处理每半个小时 共12小时 24个
        List<BigDecimal> list = Lists.newArrayList();
        for(int i =0;i<24;i++){
            list.add( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 2 )) ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) );
            cursor += 2;
        }
        request.setEveryHalfHourDegress(list);
        request.setTotalAmmeterDegree( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 )) ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) );
        cursor += 4;
        request.setMeasureNumberType( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 2 ) ) );
        cursor += 2;
        request.setBizType( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 2 ) ) );
        cursor += 2;
        request.setEWalletBalance( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 ) ) );
        cursor += 4;
        request.setReserved( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 ) ) );
        cursor += 4;
        request.setCurrentExpenseAmount( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 )) ).divide( new BigDecimal( 100 ), 2, BigDecimal.ROUND_HALF_UP ) );
        cursor += 4;
        request.setTransactionFlag( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        cursor += 1;
        request.setBeginSoc( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        cursor += 1;
        request.setEndSoc( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        cursor += 1;
        request.setStopChargeReason( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        return request;
    }


//    public static byte[] packBytes(XunDaoUploadRecordRequest request) {
//        byte[] responseBytes = new byte[]{};
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getGunNo(), 1 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.long2Byte( request.getOrderNo() ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getChargeModel(), 1 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.str2Bcd( request.getCardNo() ) );
//        responseBytes = Bytes.concat( responseBytes, request.getVin().getBytes() );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getSoc(), 1 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getEndReason(), 1 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.str2Bcd( request.getStartTime() ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.str2Bcd( request.getEndTime() ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getStartAmmeterDegree().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getEndAmmeterDegree().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getTotalAmmeterDegree().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getPointElectricQuantity().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getPeakElectricQuantity().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getOrdinaryElectricQuantity().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getDipElectricQuantity().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getTotalElectricAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getPointElectricAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getPeakElectricAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getOrdinaryElectricAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getDipElectricAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getSubscriptionAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getServiceAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//        responseBytes = Bytes.concat( responseBytes, BytesUtil.intToBytes( request.getParkingAmount().multiply( BigDecimal.valueOf( 1000 ) ).intValue(), 4 ) );
//
//        return responseBytes;
//    }

    @Override
    public String toString() {
        return "XunDaoUploadRecordRequest{" +
                "pileNo='" + pileNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", physicCardNo=" + physicCardNo +
                ", userCardNo='" + userCardNo + '\'' +
                ", subTimeBillFlag='" + subTimeBillFlag + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", everyHalfHourDegress=" + everyHalfHourDegress +
                ", totalAmmeterDegree=" + totalAmmeterDegree +
                ", measureNumberType='" + measureNumberType + '\'' +
                ", bizType='" + bizType + '\'' +
                ", eWalletBalance=" + eWalletBalance +
                ", reserved=" + reserved +
                ", currentExpenseAmount=" + currentExpenseAmount +
                ", transactionFlag=" + transactionFlag +
                ", beginSoc=" + beginSoc +
                ", endSoc=" + endSoc +
                ", stopChargeReason=" + stopChargeReason +
                '}';
    }

    public static void main(String[] args) {
        XunDaoUploadRecordRequest bean = new XunDaoUploadRecordRequest();
        Class clazz = XunDaoUploadRecordRequest.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            if (methodName.indexOf( "set" ) == 0) {
                System.out.println( "request." + methods[i].getName() + "(new BigDecimal(" + i + "));" );
            }
        }

    }

}
