package com.piles.record.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 上传充电记录接口请求实体
 */
@Data
public class UploadRecordRequest implements Serializable {


    /**
     * 充电枪数量 1位 BIN
     */
    private int chargeGunCount;
    /**
     * 充电枪状态
     */
    private int[] status;


    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static UploadRecordRequest packEntity(byte[] msg) {
        UploadRecordRequest request = new UploadRecordRequest();

        request.setChargeGunCount(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg, 0, 1), 10)));
        int[] tempStatus = new int[request.getChargeGunCount()];
        for (int i = 1; i < (request.getChargeGunCount() + 1); i++) {
            tempStatus[i - 1] = Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg, i, 1), 10));
        }
        request.setStatus(tempStatus);

        return request;
    }


    public static void main(String[] args) {
        byte[] msg = new byte[]{0x03, 0x00, 0x01, 0x02};
        packEntity(msg);

    }


}
