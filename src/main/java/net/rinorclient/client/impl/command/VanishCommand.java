package net.rinorclient.client.impl.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.util.chat.ChatUtil;

public class VanishCommand extends Command {
    private Entity mount;

    public VanishCommand() {
        super("Vanish", "Desyncs the riding entity", literal("vanish"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
    }
}
