package am.mc.net.netty;

import com.mojang.logging.LogUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.slf4j.Logger;

public class NettyClient implements Runnable {
    private final Logger LOGGER = LogUtils.getLogger();
    private final String host;
    private final int port;

    public NettyClient() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isLocalServer()) {
            ServerData serverData = Minecraft.getInstance().getCurrentServer();
            if (serverData == null) {
                throw new RuntimeException("No server selected");
            }
            LOGGER.info("amlib client local server + {}", serverData.ip);
            this.host = minecraft.getSingleplayerServer().getLocalIp();
            this.port = minecraft.getSingleplayerServer().getPort();
        } else {
            ServerData serverData = Minecraft.getInstance().getCurrentServer();
            if (serverData == null) {
                throw new RuntimeException("No server selected");
            }
            String ip = serverData.ip;
            if (ip.contains(":")) {
                String[] split = ip.split(":");
                this.host = split[0];
                this.port = Integer.parseInt(split[1]);
            } else {
                this.host = ip;
                this.port = 25565;
            }
        }
        LOGGER.info("amlib client host {} port {}", host, port);
    }

    public void start(String msg) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();
            sendMessage(f, msg);
            /*
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while ((msg = in.readLine()) != null) {
                f.channel().writeAndFlush(msg + "\r\n");
            }
             */

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
    public void sendMessage(ChannelFuture f, String message) {
        f.channel().writeAndFlush(message);
    }

    @Override
    public void run() {
        try {
            start("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
