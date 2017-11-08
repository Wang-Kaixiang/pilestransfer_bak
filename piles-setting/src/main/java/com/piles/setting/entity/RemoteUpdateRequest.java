package com.piles.setting.entity;


import com.piles.common.entity.BasePushResponse;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteUpdateRequest extends BasePushResponse implements Serializable {
    /**
     * 结果 1位 BIN 0: 成功 1: 失败
     */
    private int result;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static RemoteUpdateRequest packEntity(byte[] msg){
        RemoteUpdateRequest request=new RemoteUpdateRequest();
        request.setResult(Integer.parseInt( BytesUtil.binary(BytesUtil.copyBytes(msg, 0, 1), 10)));
        return request;
    }


}
