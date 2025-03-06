package net.rinorclient.client.impl.module.client;

import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.BooleanConfig;
import net.rinorclient.client.api.module.ConcurrentModule;
import net.rinorclient.client.api.module.ModuleCategory;
import net.rinorclient.client.api.module.ToggleModule;

/**
 * @author linus
 * @since 1.0
 */
public class ChatModule extends ToggleModule {
    //
    Config<Boolean> debugConfig = new BooleanConfig("ChatDebug", "Allows client debug messages to be printed in the chat", false);

    /**
     *
     */
    public ChatModule() {
        super("Chat", "Manages the client chat", ModuleCategory.CLIENT);
    }
}
