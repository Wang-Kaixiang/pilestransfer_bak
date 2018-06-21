package com.piles.record.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 循道上传充电过程监测数据接口请求实体
 */
@Data
public class XunDaoDCUploadChargeMonitorRequest implements Serializable {
    private int pileType;
    //桩编号 8位 BCD
    private String pileNo;

    private int soc;//bin 1%;
    //连接确认开关状态 BIN 码 1Byte 变化上传;0:断开， 1:连接，2:可充电， 3:故障状态
    private int switchStatus;
    //有功总电度 BIN 码 4Byte  精确到小数点后两位
    private BigDecimal activElectricalDegree;

    //充电输出电压(直 流最大输出电压)	BIN	2	精确到小数点后一位
    private BigDecimal dcAllowVoltage;
    //充电输出电流(直 流最大输出电流)	BIN	2	单位：A，精确到小数点后二位
    private BigDecimal dcAllowElectricity;
    //充电输出电压(直 流最大输出电压)	BIN	2	精确到小数点后一位
    private BigDecimal bmsAllowVoltage;
    //充电输出电流(直 流最大输出电流)	BIN	2	单位：A，精确到小数点后二位
    private BigDecimal bmsAllowElectricity;
    private int chargeType;//0恒压 1恒流 bin 2
    private int batteryType;//电池类型  bin 1
    private byte[] noyong; //bin 22
    // 工作状态 BCD 码 1Byte 0x00 离线，0x01 故障 0x02 待机，0x03 充电 04 停止充电 0x10 暂停，0x11 维护 0x12 测试
    private String workStatus;
    /**
     * 故障状态 Bin 码 1Byte 共 8bit
     * 第 0bit:读卡器状态 0:正常，1 故障
     * 第 1bit:电表状态 0: 正常，1 故障
     * 第 2bit:急停状态 0: 正常，1 故障
     * 第 3bit:过压状态 0: 正常，1 故障
     * 第 4bit:欠压状态 0: 正常，1 故障
     * 第 5it:过流状态 0: 正常，1 故障
     * 第 6bit:充电机状态 0:正常，1 故障
     * 第 7bit:其它状态 0: 正常，1 故障
     */
    private int troubleStatus;

    //剩余充电时间 BIN 2 字节 分
    private int unDochargeDuration;
    //充电时长 BIN 2 字节 分
    private int chargeDuration;
    //本次充电电量 BIN 4 字节
    private BigDecimal currentChargeQuantity;
    //交易流水号	BCD 码16Byte
    private String serial;
    //订单号 ascii 32位小端
    private String orderNo;

    //充电桩最高允许充电电源	BIN	4
    private BigDecimal highestAllowVoltage;
    //充电桩最高功率	BIN	4	单位：A，
    private BigDecimal highestAllowW;

    private byte[] temp;//预留bin 4

    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static XunDaoDCUploadChargeMonitorRequest packEntity(byte[] msg) {
        XunDaoDCUploadChargeMonitorRequest request = new XunDaoDCUploadChargeMonitorRequest();
        int cursor = 0;
        request.setPileNo(BytesUtil.bcd2StrLittle(BytesUtil.copyBytes(msg, cursor, 8)));
        cursor += 8;
        request.setSoc(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 1)));
        cursor += 1;
        request.setSwitchStatus(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 1)));
        cursor += 1;
        request.setActivElectricalDegree(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 4))).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        cursor += 4;
        request.setDcAllowVoltage(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2))).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
        cursor += 2;
        request.setDcAllowElectricity(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2))).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
        cursor += 2;
        request.setBmsAllowVoltage(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2))).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
        cursor += 2;
        request.setBmsAllowElectricity(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2))).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
        cursor += 2;
        request.setChargeType(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2)));
        cursor += 2;
        request.setBatteryType(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 1)));
        cursor += 1;
        request.setNoyong(BytesUtil.copyBytes(msg, cursor, 22));
        cursor += 22;
        request.setWorkStatus(BytesUtil.bcd2StrLittle(BytesUtil.copyBytes(msg, cursor, 1)));
        cursor += 1;
        request.setTroubleStatus(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 1)));
        cursor += 1;
        request.setUnDochargeDuration(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2)));
        cursor += 2;
        request.setChargeDuration(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 2)));
        cursor += 2;
        request.setCurrentChargeQuantity(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 4))).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        cursor += 4;
        byte[] serials = BytesUtil.copyBytes(msg, cursor, 16);
        int i = 0;
        while (serials[i] != 0x00) {
            i++;
        }
        request.setSerial(new String(BytesUtil.copyBytes(serials, 0, i)));
        cursor += 16;
        byte[] orderNos = BytesUtil.copyBytes(msg, cursor, 32);
        i = 0;
        while (orderNos[i] != 0x00) {
            i++;
        }
        String orderNo = new String(BytesUtil.copyBytes(orderNos, 0, i));
        if (orderNo.length() > 0 && '\u0006' == orderNo.charAt(0)) {
            orderNo = orderNo.substring(1);
        }
        request.setOrderNo(orderNo);
        cursor += 32;

        request.setHighestAllowVoltage(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 4))).divide(new BigDecimal(10), 1, BigDecimal.ROUND_HALF_UP));
        cursor += 4;
        request.setHighestAllowW(BigDecimal.valueOf(BytesUtil.bytesToIntLittle(BytesUtil.copyBytes(msg, cursor, 4))).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        cursor += 4;

        request.setTemp(BytesUtil.copyBytes(msg, cursor, 4));

        return request;
    }

    public static byte[] packBytes(XunDaoDCUploadChargeMonitorRequest request) {
        byte[] responseBytes = new byte[]{};
        return responseBytes;
    }

    @Override
    public String toString() {
        return "XunDaoDCUploadChargeMonitorRequest{" +
                "pileType=" + pileType +
                ", pileNo='" + pileNo + '\'' +
                ", soc=" + soc +
                ", switchStatus=" + switchStatus +
                ", activElectricalDegree=" + activElectricalDegree +
                ", dcAllowVoltage=" + dcAllowVoltage +
                ", dcAllowElectricity=" + dcAllowElectricity +
                ", bmsAllowVoltage=" + bmsAllowVoltage +
                ", bmsAllowElectricity=" + bmsAllowElectricity +
                ", chargeType=" + chargeType +
                ", batteryType=" + batteryType +
                ", noyong=" + Arrays.toString(noyong) +
                ", workStatus='" + workStatus + '\'' +
                ", troubleStatus=" + troubleStatus +
                ", unDochargeDuration=" + unDochargeDuration +
                ", chargeDuration=" + chargeDuration +
                ", currentChargeQuantity=" + currentChargeQuantity +
                ", serial='" + serial + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", highestAllowVoltage=" + highestAllowVoltage +
                ", highestAllowW=" + highestAllowW +
                ", temp=" + Arrays.toString(temp) +
                '}';
    }

    public static void main(String[] args) {
        byte[] b = new byte[]{0x01};
        System.out.println(BytesUtil.bcd2StrLittle(b));
        BytesUtil.bcd2StrLittle(b);
        byte[] bytes = new byte[]{(byte) 0x68, 0x26, (byte) 0x9a, (byte) 0xd0, 0x0, 0x16, (byte) 0x86, 0x0, 0x3, 0x0, 0x57, 0x0, 0x1, 0x9, 0x9, 0x0, 0x0, 0x0, 0x1, (byte) 0xfe, 0x18, 0x0, 0x0, 0x55, 0x0, 0x0, (byte) 0x80, 0x0, 0x0, 0x0, 0x0, 0x0, 0x3, 0x0, 0xa, 0xa, 0x3d, 0x5, 0x0, 0x0};
        byte[] dataBytes = BytesUtil.copyBytes(bytes, 13, (bytes.length - 13));

        //依照报文体规则解析报文
        XunDaoDCUploadChargeMonitorRequest uploadChargeMonitorRequest = XunDaoDCUploadChargeMonitorRequest.packEntity(dataBytes);
    }
}
