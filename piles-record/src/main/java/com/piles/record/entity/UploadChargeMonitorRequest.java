package com.piles.record.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 上传充电过程监测数据接口请求实体
 */
@Data
public class UploadChargeMonitorRequest implements Serializable {


    /**
     * 枪号 1位 BIN
     */
    private int chargeGunNo;
    /**
     * 订单号 8位
     */
    private String orderNo;

    /**
     * BMS版本号 3位 原类型传输
     */
    private byte[] bmsVersion;
    /**
     * BMS 类型 1位
     *
     * 0:其他电池
     1:铅酸电池
     2:镍氢电池
     3:磷酸铁锂电池
     4:锰酸锂电池
     5:钴酸锂电池
     6:三原材料电池
     7:聚合物锂电池
     8:钛酸锂电池

     */
    private int bmsType;

    /**
     * 订单号 8位
     */
    private String batteryNominalCapacity;



    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static UploadChargeMonitorRequest packEntity(byte[] msg) {
        UploadChargeMonitorRequest request = new UploadChargeMonitorRequest();
        //TODO 初始化数据

        return request;
    }


    public static void main(String[] args) {
        byte[] msg = new byte[]{0x03, 0x00, 0x01, 0x02};
        packEntity(msg);

    }


}
