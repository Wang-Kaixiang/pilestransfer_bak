package com.piles.common.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class ChannelMap {

    private ChannelMap() {
    }

    /**
     * key:桩编号,value:channel
     */
    private static Map<String, Channel> channelMap2 = Maps.newConcurrentMap();

    /**
     * key:channel,value:桩编号
     */
    private static Map<Channel, String> channelMap3 =  Maps.newConcurrentMap();

    /**
     *
     * add channelInfo
     *
     * @param pileNo
     * @param channel
     * @return void
     * @exception
     */
    public static void addChannel(String pileNo, Channel channel) {
        channelMap2.put(pileNo, channel);
    }

    public static void addChannel(Channel channel, String pileNo) {
        channelMap3.put(channel, pileNo);
    }


    /**
     * 根据桩编号移出channel
     * @param pileNo
     */
    public static void removeChannel(String pileNo) {
        Channel channel = channelMap2.get(pileNo);
        if (channel != null) {
            channelMap3.remove(channel);
        }
        channelMap2.remove(pileNo);
    }

    public static Channel getChannel(String pileNo) {
        return channelMap2.get(pileNo);
    }
    public static String getChannel(Channel channel) {
        return channelMap3.get(channel);
    }


    public static void removeChannel(Channel channel) {
        String pileNo = channelMap3.get(channel);
        if (pileNo != null) {
            channelMap2.remove(pileNo);
        }

        channelMap3.remove(channel);
    }
}
