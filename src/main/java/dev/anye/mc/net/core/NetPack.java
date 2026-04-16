package dev.anye.mc.net.core;

import dev.anye.mc.net.Net;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

public class NetPack {
    public static final String NET_KEY = "net.key";
    public static NetworkPack createClientPack(CompoundTag data ,String handle){
        data.putString(NET_KEY, handle);
        return new NetworkPack(data);
    }
    public static NetworkPack createClientPack(CompoundTag data ,@NotNull NetHandle handle){
        return createClientPack(data,getHandleKey(handle));
    }
    public static NetworkPack createClientPack(CompoundTag data , DeferredHolder<NetHandle,NetHandle> handle){
        return createClientPack(data,handle.getId().toString());
    }
    public static NetworkPack createServerPack(CompoundTag data ,String handle){
        data.putString(NET_KEY, handle);
        return new NetworkPack(data);
    }
    public static NetworkPack createServerPack(CompoundTag data ,@NotNull NetHandle handle){
        return createServerPack(data,getHandleKey(handle));
    }
    public static NetworkPack createServerPack(CompoundTag data ,DeferredHolder<NetHandle,NetHandle> handle){
        return createServerPack(data,handle.getId().toString());
    }

    public static String getHandleKey(@NotNull NetHandle handle){
        return NetReg.REGISTRY.getKey(handle).toString();
    }

	public static NetHandle getHandle(Optional<String> key) {
		return getHandle(key.orElseThrow());
	}

    public static NetHandle getHandle(String key){
        return NetReg.REGISTRY.get(Identifier.tryParse(key)).get().value();
    }

    /**
     * Client
     */
	public static class STC{
		




        public static void handleData(final NetworkPack data, final IPayloadContext context) {
            /*
            // Do something with the data, on the network thread
            blah(data.data());

            // Do something with the data, on the main thread
            context.enqueueWork(() -> {
                        blah(data.age());
                    })
                    .exceptionally(e -> {
                        // Handle exception
                        context.disconnect(Component.translatable("my_mod.networking.failed", e.getMessage()));
                        return null;
                    });

             */
            context.enqueueWork(()-> {
                NetHandle easyNet = getHandle(data.data().getString(NET_KEY));
                if (easyNet != null) {
                    easyNet.client(context, data.data());
                }
            });
        }
    }
    public static class STS{
        public static void handleData(final NetworkPack data, final IPayloadContext context){
            context.enqueueWork(()->{
                NetHandle easyNet = getHandle(data.data().getString(NET_KEY));
                if (easyNet != null){
                    easyNet.server(context,data.data());
                }
            });
        }
    }

    public record NetworkPack(CompoundTag data) implements CustomPacketPayload {
        public static final Type<NetworkPack> TYPE = new Type<>(
				Identifier.fromNamespaceAndPath(Net.MOD_ID, "net_pack"));
        public static final StreamCodec<ByteBuf, NetworkPack> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.COMPOUND_TAG,
                NetworkPack::data,
                NetworkPack::new
        );
        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
