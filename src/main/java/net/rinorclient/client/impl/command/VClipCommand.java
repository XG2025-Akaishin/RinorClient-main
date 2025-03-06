package net.rinorclient.client.impl.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.chat.ChatUtil;


public class VClipCommand extends Command {

    public VClipCommand() {
        super("VClip", "Vertically clips the player", literal("vclip"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
    }
}
