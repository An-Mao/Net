package dev.anye.mc.net.event;

import dev.anye.mc.net.Net;
import dev.anye.mc.net.core.NetCore;
import dev.anye.mc.net.core.NetPack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Events {
    @EventBusSubscriber(modid = Net.MOD_ID)
    public static class ModEvents{
        @SubscribeEvent
        public static void register(final RegisterPayloadHandlersEvent event) {
            // Sets the current network version
            final PayloadRegistrar registrar = event.registrar(NetCore.PROTOCOL_VERSION);
            registrar.playBidirectional(
                    NetPack.NetworkPack.TYPE,
                    NetPack.NetworkPack.STREAM_CODEC,
                    NetPack.STS::handleData,
                    NetPack.STC::handleData
                    
            );
        }
        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event)
        {
        }
        @SubscribeEvent
        public static void onServer(EntityJoinLevelEvent event){
            /*
            if (event.getEntity() instanceof Player player){
                CompoundTag dat = new CompoundTag();
                if (player.level().isClientSide){
                    dat.putInt("client", 1);
                    NetCore.sendToServer(NetPack.createServerPack(dat, NetReg.Example));
                }else {
                    dat.putInt("server", 2);
                    NetCore.sendToPlayer(NetPack.createClientPack(dat, NetReg.Example), (ServerPlayer) player);
                }
            }

             */
        }


    }

}
