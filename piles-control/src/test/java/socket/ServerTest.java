package socket;

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

            Object lock = new Object();
            synchronized (lock) {
                try {
                    while (true)
                        lock.wait();
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

        } catch (RuntimeException e) {
            LOGGER.error("Spring 启动错误", e);
        }

    }

}
