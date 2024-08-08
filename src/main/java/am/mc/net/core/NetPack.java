package am.mc.net.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class NetPack {
    public static final String NET_KEY = "net.key";

    public static abstract class PackCore{
        protected final CompoundTag data;
        public PackCore(CompoundTag data){
            this.data = data;
        }
        public PackCore(FriendlyByteBuf buf){
            this.data = buf.readNbt();
        }
        public void toBytes(FriendlyByteBuf buf){
            buf.writeNbt(this.data);
        }
        public abstract void handle(CustomPayloadEvent.Context context);
    }


    public static STC createClientPack(CompoundTag data ,String handle){
        data.putString(NET_KEY, handle);
        return new STC(data);
    }
    public static STC createClientPack(CompoundTag data ,@NotNull NetHandle handle){
        return createClientPack(data,getHandleKey(handle));
    }
    public static STC createClientPack(CompoundTag data , RegistryObject<NetHandle> handle){
        return createClientPack(data,handle.getId().toString());
    }
    public static STS createServerPack(CompoundTag data ,String handle){
        data.putString(NET_KEY, handle);
        return new STS(data);
    }
    public static STS createServerPack(CompoundTag data ,@NotNull NetHandle handle){
        return createServerPack(data,getHandleKey(handle));
    }
    public static STS createServerPack(CompoundTag data ,RegistryObject<NetHandle> handle){
        return createServerPack(data,handle.getId().toString());
    }

    public static String getHandleKey(@NotNull NetHandle handle){
        return NetReg.REGISTRY.get().getKey(handle).toString();
    }
    public static NetHandle getHandle(String key){
        return NetReg.REGISTRY.get().getValue(ResourceLocation.tryParse(key));
    }
    public static class STC extends PackCore{
        public STC(FriendlyByteBuf buf) {
            super(buf);
        }
        public STC(CompoundTag data) {
            super(data);
        }
        @Override
        public void handle(CustomPayloadEvent.Context context) {
            context.enqueueWork(()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () ->
                    () -> {
                        //String key = this.data.getString(NET_KEY);
                        NetHandle easyNet = getHandle(this.data.getString(NET_KEY));
                        if (easyNet != null){
                            easyNet.client(context,this.data);
                        }
                    }));
        }
    }
    public static class STS extends PackCore{
        public STS(CompoundTag data) {
            super(data);
        }
        public STS(FriendlyByteBuf buf) {
            super(buf);
        }
        @Override
        public void handle(CustomPayloadEvent.Context context){
            context.enqueueWork(()->{
                //String key = this.dat.getString(NET_KEY);
                //NetHandle easyNet = EasyNetRegister.REGISTRY.get().getValue(ResourceLocation.tryParse(key));
                NetHandle easyNet = getHandle(this.data.getString(NET_KEY));
                if (easyNet != null){
                    easyNet.server(context,this.data);
                }
            });
        }
    }
}
