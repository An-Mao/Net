package am.mc.net.netty;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import org.slf4j.Logger;

public class ClientEvent {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static NettyClient nettyClient;
    private static Thread clientThread;
    public static void onClientLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        nettyClient = new NettyClient();
        clientThread = new Thread(nettyClient);
        clientThread.start();
    }
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {

    }
}
