package am.mc.net.core.example;

import am.mc.net.core.NetHandle;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.slf4j.Logger;

public class ExampleHandle extends NetHandle {
    public final Logger logger = LogUtils.getLogger();
    @Override
    public void client(CustomPayloadEvent.Context context, CompoundTag dat) {
        logger.info("client data:{}",dat);
        super.client(context, dat);
    }

    @Override
    public void server(CustomPayloadEvent.Context context, CompoundTag dat) {
        logger.info("server data:{}",dat);
        super.server(context, dat);
    }
}
