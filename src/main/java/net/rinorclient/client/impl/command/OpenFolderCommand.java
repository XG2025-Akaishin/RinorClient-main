package net.rinorclient.client.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.command.Command;
import net.rinorclient.client.util.chat.ChatUtil;

import java.awt.*;
import java.io.IOException;

/**
 * @author linus
 * @since 1.0
 */
public class OpenFolderCommand extends Command {

    /**
     *
     */
    public OpenFolderCommand() {
        super("OpenFolder", "Opens the client configurations folder", literal("openfolder"));
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(c -> {
            try {
                Desktop.getDesktop().open(Rinor.CONFIG.getClientDirectory().toFile());
            } catch (IOException e) {
                e.printStackTrace();
                ChatUtil.error("Failed to open client folder!");
            }
            return 1;
        });
    }
}
