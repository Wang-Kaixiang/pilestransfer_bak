package socket;

import com.piles.common.util.ChannelMap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {
    private static final String SRPING_ROOT_XML = "classpath:application.xml";


    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(SRPING_ROOT_XML)) {
            context.registerShutdownHook();
            context.start();


            LOGGER.info("启动成功");

            while (true){
                Channel channel=ChannelMap.getChannel("1000025484561835");
                if (null!=channel){
                    System.out.println(channel.isWritable());
                    channel.writeAndFlush("1000025484561835".getBytes());
                    System.out.println("flush");

                }
                Thread.sleep(10000L);
            }

        } catch (Exception e) {
            LOGGER.error("Spring 启动错误", e);
        }

    }

}
