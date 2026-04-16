package dev.anye.mc.net.core;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class NetHandle {
    public void server(final IPayloadContext context, CompoundTag dat) {
    }
    public void client(final IPayloadContext context, CompoundTag dat) {
    }
}
