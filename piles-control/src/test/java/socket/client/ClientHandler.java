package socket.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<byte[]> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        System.out.println("Server say : " + bytes);
        if (bytes[0]==49){
            channelHandlerContext.writeAndFlush("1".getBytes());
        }
        for (byte b:bytes){
            System.out.print(Integer.toHexString(Byte.toUnsignedInt(b)));
        }
//        byte[] msg= new byte[]{0x68,0x01,0x00,0x00,0x00,0x1D,0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
//                0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01,0x2B,(byte)0xD9};
//        channelHandlerContext.writeAndFlush(msg);
        //TODO 获取到server端返回值

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }


}
