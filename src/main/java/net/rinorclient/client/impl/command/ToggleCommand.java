package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.api.command.ModuleArgumentType;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("Toggle", "Enables/Disables a module", literal("toggle"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module()).executes(c -> {
            Module module = ModuleArgumentType.getModule(c, "module");
            if (module instanceof ToggleModule t) {
                t.toggle();
                ChatUtil.clientSendMessage("%s is now %s", "§7" + t.getName() + "§f", t.isEnabled() ? "§senabled§f" : "§cdisabled§f");
            }
            return 1;
        })).executes(c -> {
            ChatUtil.error("Must provide module to toggle!");
            return 1;
        });
    }
}
