package net.rinorclient.client.impl.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class ConfigCommand extends Command {
    /**
     *
     */
    public ConfigCommand() {
        super("Config", "Creates a new configuration preset", literal("config"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("save/load", StringArgumentType.string()).suggests(suggest("save", "load"))
                .then(argument("config_name", StringArgumentType.string()).executes(c -> {
                    String action = StringArgumentType.getString(c, "save/load");
                    String name = StringArgumentType.getString(c, "config_name");
                    if (action.equalsIgnoreCase("save")) {
                        Rinor.CONFIG.saveModuleConfiguration(name);
                        ChatUtil.clientSendMessage("Saved config: §s" + name);
                    } else if (action.equalsIgnoreCase("load")) {
                        Rinor.CONFIG.loadModuleConfiguration(name);
                        ChatUtil.clientSendMessage("Loaded config: §s" + name);
                    }
                    return 1;
                })).executes(c -> {
                    ChatUtil.error("Must provide a config to load!");
                    return 1;
                })).executes(c -> {
                    ChatUtil.error("Invalid usage! Usage: " + getUsage());
                    return 1;
                });
    }
}
