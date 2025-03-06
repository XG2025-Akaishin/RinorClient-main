package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.util.chat.ChatUtil;

public class SoundReloadCommand extends Command {
    public SoundReloadCommand() {
        super("SoundReload", "Reloads the Minecraft sound system", literal("soundreload"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            mc.getSoundManager().reloadSounds();
            ChatUtil.clientSendMessage("Reloaded the SoundSystem");
            return 1;
        });
    }
}
