package am.mc.net.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class NetHandle {
    public void server(CustomPayloadEvent.Context context, CompoundTag dat) {
        context.setPacketHandled(true);
    }
    public void client(CustomPayloadEvent.Context context, CompoundTag dat) {
        context.setPacketHandled(true);
    }
}
