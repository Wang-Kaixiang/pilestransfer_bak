package com.piles.record.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 循道上传充电过程监测数据接口请求实体
 */
@Data
public class XunDaoUploadChargeMonitorRequest implements Serializable {


    //充电输出电压(直 流最大输出电压)	BIN	2	精确到小数点后一位
    private BigDecimal highestAllowVoltage;
    //充电输出电流(直 流最大输出电流)	BIN	2	单位：A，精确到小数点后二位
    private BigDecimal highestAllowElectricity;
    //输出继电器状态  BIN 码 1Byte 布尔型, 变化上传;0 断开，1:闭合
    private int outputRelayStatus;
    //连接确认开关状态 BIN 码 1Byte 变化上传;0:断开， 1:连接，2:可充电， 3:故障状态
    private int switchStatus;
    //有功总电度 BIN 码 4Byte  精确到小数点后两位
    private BigDecimal activElectricalDegree;
    //桩编号 8位 BCD
    private String pileNo;
    //是否连接电池 BIN 码 1Byte
    private int connectBattery;
    // 工作状态 BCD 码 1Byte 0x00 离线，0x01 故障 0x02 待机，0x03 充电 04 停止充电 0x10 暂停，0x11 维护 0x12 测试
    private String workStatus;
    /**
     * 故障状态 Bin 码 1Byte 共 8bit
     *    第 0bit:读卡器状态 0:正常，1 故障
     *   第 1bit:电表状态 0: 正常，1 故障
     *   第 2bit:急停状态 0: 正常，1 故障
     *   第 3bit:过压状态 0: 正常，1 故障
     *   第 4bit:欠压状态 0: 正常，1 故障
     *   第 5it:过流状态 0: 正常，1 故障
     *   第 6bit:充电机状态 0:正常，1 故障
     *   第 7bit:其它状态 0: 正常，1 故障
     */
    private int troubleStatus;
    //充电时长 BIN 2 字节 分
    private int chargeDuration;
    //本次充电电量 BIN 4 字节
    private int currentChargeQuantity;

    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static XunDaoUploadChargeMonitorRequest packEntity(byte[] msg) {
        XunDaoUploadChargeMonitorRequest request = new XunDaoUploadChargeMonitorRequest();
        int cursor = 0;
        request.setHighestAllowVoltage( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 2 ) ) ).divide( new BigDecimal( 1000 ), 1, BigDecimal.ROUND_HALF_UP ) );
        cursor += 2;
        request.setHighestAllowElectricity( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 2 ) ) ).divide( new BigDecimal( 1000 ), 2, BigDecimal.ROUND_HALF_UP ) );
        cursor += 2;
        request.setOutputRelayStatus( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ));
        cursor += 1;
        request.setSwitchStatus( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ));
        cursor += 1;
        request.setActivElectricalDegree( BigDecimal.valueOf( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 ) ) ).divide( new BigDecimal( 1000 ), 2, BigDecimal.ROUND_HALF_UP ) );
        cursor += 4;
        request.setPileNo( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 8 ) ) );
        cursor += 8;
        request.setConnectBattery( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ));
        cursor += 1;
        request.setWorkStatus( BytesUtil.bcd2StrLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ) );
        cursor += 1;
        request.setTroubleStatus( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 1 ) ));
        cursor += 1;
        request.setChargeDuration( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 2 ) ));
        cursor += 2;
        request.setCurrentChargeQuantity( BytesUtil.bytesToIntLittle( BytesUtil.copyBytes( msg, cursor, 4 ) ));

        return request;
    }

    public static byte[] packBytes(XunDaoUploadChargeMonitorRequest request) {
        byte[] responseBytes = new byte[]{};
        return responseBytes;
    }


    public static void main(String[] args) {
        XunDaoUploadChargeMonitorRequest bean = new XunDaoUploadChargeMonitorRequest();
        Class clazz = XunDaoUploadChargeMonitorRequest.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            if (methodName.indexOf( "set" ) == 0) {
                System.out.println( "request." + methods[i].getName() + "(new BigDecimal(" + i + "));" );
            }
        }

    }

    @Override
    public String toString() {
        return "XunDaoUploadChargeMonitorRequest{" +
                "highestAllowVoltage=" + highestAllowVoltage +
                ", highestAllowElectricity=" + highestAllowElectricity +
                ", outputRelayStatus=" + outputRelayStatus +
                ", switchStatus=" + switchStatus +
                ", activElectricalDegree=" + activElectricalDegree +
                ", pileNo='" + pileNo + '\'' +
                ", connectBattery=" + connectBattery +
                ", workStatus='" + workStatus + '\'' +
                ", troubleStatus=" + troubleStatus +
                ", chargeDuration=" + chargeDuration +
                ", currentChargeQuantity=" + currentChargeQuantity +
                '}';
    }
}
