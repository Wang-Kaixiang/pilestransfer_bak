package com.piles.setting.entity;

import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelMap;
import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 请求升级包数据
 */
@Data
public class UpdatePackageRequest implements Serializable
{
    /**
     * 索引 2 位  BIN 升级包的第几段，从1开始，直到等于总索引值为下载完
     */
    private int index;
    /**
     * 充电桩编号
     */
    private String pileNo;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static UpdatePackageRequest packEntity(byte[] msg, Channel income){
        String pileNoTemp=ChannelMap.getChannel(income);
        if (StringUtils.isEmpty(pileNoTemp)){
            return null;
        }
        UpdatePackageRequest request=new UpdatePackageRequest();

        request.setIndex(Integer.parseInt(BytesUtil.binary(BytesUtil.copyBytes(msg,0,2),10)));
        request.setPileNo(pileNoTemp);
        return request;
    }



}
