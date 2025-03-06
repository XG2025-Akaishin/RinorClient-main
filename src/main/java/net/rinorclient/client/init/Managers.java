package net.rinorclient.client.init;

import net.rinorclient.client.impl.manager.ModuleManager;
import net.rinorclient.client.impl.manager.client.*;

public class Managers {
    public static ModuleManager MODULE;
    public static MacroManager MACRO;
    public static CommandManager COMMAND;
    public static SocialManager SOCIAL;
    private static boolean initialized;

    public static void init() {
        if (!isInitialized()) {
            MODULE = new ModuleManager();
            MACRO = new MacroManager();
            SOCIAL = new SocialManager();
            COMMAND = new CommandManager();
            initialized = true;
        }
    }

    public static void postInit() {
        if (isInitialized()) {
            MODULE.postInit();
            MACRO.postInit();
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
