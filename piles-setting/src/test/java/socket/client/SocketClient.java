package socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketClient {

    public static String host = "59.110.170.111";
    public static int port = 8080;
    public static LinkedBlockingQueue queue = new LinkedBlockingQueue();
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer());

            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
            byte[] msg= new byte[]{0x68,0x01,0x00,0x00,0x00,0x1D,0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
                    0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01,0x2B,(byte)0xD9};

            ch.writeAndFlush(msg);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            countDownLatch.await();
        } finally {
            group.shutdownGracefully();
        }
    }
}
