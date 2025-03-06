package net.rinorclient.client.init;

import java.util.HashSet;
import java.util.Set;

import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.impl.module.client.*;

public class Modules {
    public static ClickGuiModule CLICK_GUI;
    public static ColorsModule COLORS;
    public static HUDModule HUD;

    private static boolean initialized;
    private static Set<Module> CACHE;

    private static Module getRegisteredModule(final String id) {
        Module registered = Managers.MODULE.getModule(id);
        if (CACHE.add(registered)) {
            return registered;
        }
        // already cached!!
        else {
            throw new IllegalStateException("Invalid module requested: " + id);
        }
    }

    public static void init() {
        if (Managers.isInitialized()) {
            CACHE = new HashSet<>();
            CLICK_GUI = (ClickGuiModule) getRegisteredModule("clickgui-module");
            COLORS = (ColorsModule) getRegisteredModule("colors-module");
            HUD = (HUDModule) getRegisteredModule("hud-module");

            initialized = true;
            // reflect configuration properties for each cached module
            for (Module module : CACHE) {
                if (module == null) {
                    continue;
                }
                module.reflectConfigs();
                if (module instanceof ToggleModule t) {
                    Managers.MACRO.register(t.getKeybinding());
                }
            }
            CACHE.clear();
        } else {
            throw new RuntimeException("Accessed modules before managers " +
                    "finished initializing!");
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
