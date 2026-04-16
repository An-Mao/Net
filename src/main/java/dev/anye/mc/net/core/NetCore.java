package dev.anye.mc.net.core;

import dev.anye.mc.net.Net;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

public class NetCore {
    public static final String PROTOCOL_VERSION = "1";
    public static final Identifier ID = Identifier.tryBuild(Net.MOD_ID, "network");
    private static int packetId = 0;

    private static int id(){
        return packetId++;
    }


    public static void sendToServer(NetPack.NetworkPack msg){
        ClientPacketDistributor.sendToServer(msg);
    }
    public static void sendToPlayer(NetPack.NetworkPack msg, ServerPlayer serverPlayer){
        PacketDistributor.sendToPlayer(serverPlayer, msg);
    }
    public static void sendToAllPlayer(NetPack.NetworkPack msg){
        PacketDistributor.sendToAllPlayers(msg);
    }
    public static void sendToEntity(NetPack.NetworkPack msg, Entity entity){
        PacketDistributor.sendToPlayersTrackingEntity( entity,msg);
    }
}
