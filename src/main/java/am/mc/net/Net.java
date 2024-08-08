package am.mc.net;

import am.mc.net.core.NetReg;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Net.MOD_ID)
public class Net {
    public static final String MOD_ID = "net";
    public Net() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        NetReg.register(modEventBus);
    }
}
