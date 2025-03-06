package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.RinorMod;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.chat.ChatUtil;

public class ModulesCommand extends Command {
    public ModulesCommand() {
        super("Modules", "Displays all client modules", literal("modules"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            StringBuilder modulesList = new StringBuilder();
            for (Module module : Managers.MODULE.getModules()) {
                String formatting = module instanceof ToggleModule t && t.isEnabled() ? "§s" : "§f";
                modulesList.append(formatting);
                modulesList.append(module.getName());
                modulesList.append(Formatting.RESET);
                // LOL
                if (!module.getName().equalsIgnoreCase(RinorMod.isBaritonePresent() ? "Baritone" : "Speedmine")) {
                    modulesList.append(", ");
                }
            }
            ChatUtil.clientSendMessageRaw(" §7Modules:§f " + modulesList);
            return 1;
        });
    }
}
