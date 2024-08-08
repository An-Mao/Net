package am.mc.net.core;

import am.mc.net.Net;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class NetCore {
    private static final int PROTOCOL_VERSION = 1;
    public static final ResourceLocation ID = ResourceLocation.tryBuild(Net.MOD_ID, "network");
    private static int packetId = 0;
    private static final SimpleChannel SIMPLE = ChannelBuilder.named(ID)
            .optionalServer()
            .clientAcceptedVersions((status, version) -> version >= PROTOCOL_VERSION)
            .serverAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

    public static SimpleChannel getSimple(){
        return SIMPLE;
    }


    private static int id(){
        return packetId++;
    }
    public static void register(){
        register(SIMPLE);
    }
    public static void register(SimpleChannel channel){
        channel.messageBuilder(NetPack.STC.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NetPack.STC::new)
                .encoder(NetPack.STC::toBytes)
                .consumerMainThread(NetPack.STC::handle).add();
        channel.messageBuilder(NetPack.STS.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NetPack.STS::new)
                .encoder(NetPack.STS::toBytes)
                .consumerMainThread(NetPack.STS::handle).add();
    }
    public static <MSG> void sendToServer(MSG msg){
        SIMPLE.send(msg, PacketDistributor.SERVER.noArg());
    }
    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer serverPlayer){
        SIMPLE.send(msg,PacketDistributor.PLAYER.with(serverPlayer));
    }
    public static <MSG> void sendToAllPlayer(MSG msg){
        SIMPLE.send(msg,PacketDistributor.PLAYER.noArg());
    }
    public static <MSG> void sendToEntity(MSG msg, Entity entity){
        SIMPLE.send(msg,PacketDistributor.TRACKING_ENTITY.with(entity));
    }
}
