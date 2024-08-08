package am.mc.net.core;

import am.mc.net.Net;
import am.mc.net.core.example.ExampleHandle;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class NetReg {

    public static final ResourceLocation KEY = ResourceLocation.tryBuild(Net.MOD_ID, "net");

    public static final DeferredRegister<NetHandle> NETS = DeferredRegister.create(KEY, Net.MOD_ID);
    public static final Supplier<IForgeRegistry<NetHandle>> REGISTRY = NETS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<NetHandle> Example = NETS.register("example", ExampleHandle::new);



    public static void register(IEventBus eventBus){
        NETS.register(eventBus);
    }
}
