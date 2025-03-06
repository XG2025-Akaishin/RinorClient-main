package net.rinorclient.client.impl.module.client;

import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.BooleanConfig;
import net.rinorclient.client.api.event.listener.EventListener;
import net.rinorclient.client.api.module.ConcurrentModule;
import net.rinorclient.client.api.module.ModuleCategory;
import net.rinorclient.client.api.module.ToggleModule;

import static net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN;

/**
 * @author xgraza
 * @since 1.0
 */
public final class ServerModule extends ToggleModule
{
    Config<Boolean> packetKickConfig = new BooleanConfig("NoPacketKick", "If to prevent thrown exceptions from kicking you", true);
    Config<Boolean> demoConfig = new BooleanConfig("NoDemo", "If to prevent servers from forcing you to a demo screen", true);

    public ServerModule()
    {
        super("Server", "Prevents servers from doing shady shit", ModuleCategory.CLIENT);
    }

    public boolean isPacketKick()
    {
        return packetKickConfig.getValue();
    }
}
