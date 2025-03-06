package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.chat.ChatUtil;

public class HideAllCommand extends Command {

    public HideAllCommand() {
        super("HideAll", "Hides all modules from the arraylist", literal("hideall"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            for (Module module : Managers.MODULE.getModules()) {
                if (module instanceof ToggleModule toggleModule && !toggleModule.isHidden()) {
                    toggleModule.setHidden(true);
                }
            }
            ChatUtil.clientSendMessage("All modules are hidden");
            return 1;
        });
    }
}
