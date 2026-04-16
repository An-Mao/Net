package dev.anye.mc.net.core;

import dev.anye.mc.net.Net;
import dev.anye.mc.net.core.example.ExampleHandle;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Function;
import java.util.function.Supplier;

public class NetReg {

    public static final Identifier KEY =  Identifier.fromNamespaceAndPath(Net.MOD_ID, "net");
    public static final ResourceKey<Registry<NetHandle>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<NetHandle> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(false) // ? true
            .maxId(Integer.MAX_VALUE)
            .create();
    @EventBusSubscriber(modid = Net.MOD_ID)
    public static class reg{
        @SubscribeEvent
        public static void registerRegistries(NewRegistryEvent event) {
            event.register(REGISTRY);
        }
    }
    public static final DeferredRegister<NetHandle> HANDLE = DeferredRegister.create(REGISTRY, Net.MOD_ID);



    public static final DeferredHolder<NetHandle,NetHandle> Example = reg("example", ExampleHandle::new);





    public static DeferredHolder<NetHandle, NetHandle> reg(String name , Function<String , NetHandle> function){
        return HANDLE.register(name, () -> function.apply(name));
    }
    public static DeferredHolder<NetHandle, NetHandle> reg(String name, Supplier<? extends NetHandle> sup) {
        return HANDLE.register(name, sup);
    }
    public static void register(IEventBus eventBus){
        HANDLE.register(eventBus);
    }
}
