package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.chat.ChatUtil;

public class DisableAllCommand extends Command {
    /**
     *
     */
    public DisableAllCommand() {
        super("DisableAll", "Disables all enabled modules", literal("disableall"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            for (Module module : Managers.MODULE.getModules()) {
                if (module instanceof ToggleModule toggleModule && toggleModule.isEnabled()) {
                    toggleModule.disable();
                }
            }
            ChatUtil.clientSendMessage("All modules are disabled");
            return 1;
        });
    }
}
