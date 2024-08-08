package am.mc.net.event;

import am.mc.net.Net;
import am.mc.net.core.NetCore;
import am.mc.net.core.NetPack;
import am.mc.net.core.NetReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Events {

    @Mod.EventBusSubscriber(modid = Net.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents{
        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event)
        {
            NetCore.register();
        }
    }
    @Mod.EventBusSubscriber(modid = Net.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents{
        /*
        @SubscribeEvent
        public static void onServer(EntityJoinLevelEvent event){
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
        }

         */
    }

}
