package dev.anye.mc.net.core.example;

import dev.anye.mc.net.core.NetHandle;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.slf4j.Logger;

public class ExampleHandle extends NetHandle {
    public final Logger logger = LogUtils.getLogger();
    @Override
    public void client(final IPayloadContext context, CompoundTag dat) {
        logger.info("client data:{}",dat);
        super.client(context, dat);
    }

    @Override
    public void server(final IPayloadContext  context, CompoundTag dat) {
        logger.info("server data:{}",dat);
        super.server(context, dat);
    }
}
