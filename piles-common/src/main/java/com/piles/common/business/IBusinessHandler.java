package com.piles.common.business;

import com.piles.common.entity.SocketBaseDTO;
import io.netty.channel.Channel;

public interface IBusinessHandler {

	SocketBaseDTO process(Byte[] msg, Channel incoming);

}
