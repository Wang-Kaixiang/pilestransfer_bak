package com.piles.common.business;

import com.piles.common.entity.SocketBaseDTO;
import io.netty.channel.Channel;

import com.alibaba.fastjson.JSONObject;

public interface IBusiness {

	SocketBaseDTO process(byte[] msg);
	
}
