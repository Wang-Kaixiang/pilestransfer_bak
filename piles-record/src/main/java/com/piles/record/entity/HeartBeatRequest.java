package com.piles.record.entity;

import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 心跳接口请求实体
 */
@Data
public class HeartBeatRequest implements Serializable {


    /**
     * 充电枪数量 1位 BIN
     */
    private int gunCount;
    /**
     * 充电枪状态
     * 0: 空闲
     * 1: 已插枪未充电
     * 2: 充电中
     * 3: 充电结束未拔枪
     * 4: 预约中
     * 5: 故障
     */
    private int[] status;


    /**
     * 解析报文并封装request体
     *
     * @param msg
     * @return
     */
    public static HeartBeatRequest packEntity(byte[] msg) {
        HeartBeatRequest request = new HeartBeatRequest();

        request.setGunCount(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg, 0, 1), 10)));
        int[] tempStatus = new int[request.getGunCount()];
        for (int i = 1; i < (request.getGunCount() + 1); i++) {
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
