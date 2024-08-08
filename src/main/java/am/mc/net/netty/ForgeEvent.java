package am.mc.net.netty;

import com.mojang.logging.LogUtils;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import org.slf4j.Logger;


public class ForgeEvent {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final NettyServer nettyServer = new NettyServer();
    private static final Thread serverThread = new Thread(nettyServer);
    public static void onServerStarting(ServerStartingEvent event) {
        serverThread.start();
    }
    public static void onServerStopping(ServerStoppingEvent event) {
        nettyServer.stop();
        System.out.println("AMLib Netty Server Closed");
    }
}
