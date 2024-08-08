package am.mc.net.netty;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NettyHelper {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static int getServerPort() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("server.properties")) {
            properties.load(input);
            return Integer.parseInt(properties.getProperty("server-port"));
        } catch (IOException ex) {
            LOGGER.error("Failed to load server properties", ex);
            return 25565;
        }
    }
}
