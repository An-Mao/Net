package dev.anye.mc.net;

import dev.anye.mc.net.core.NetReg;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Mod(Net.MOD_ID)
public class Net {
    public static final String MOD_ID = "net";
    public Net(IEventBus modEventBus, ModContainer modContainer) {
        NetReg.register(modEventBus);
    }
}
