package net.rinorclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class RinorMod implements ClientModInitializer {
    public static final String MOD_NAME = "Rinor";
    public static final String MOD_VER = "1.0";
    public static final String MOD_BUILD_NUMBER = "b5";
    public static final String MOD_MC_VER = "1.21.1";

    @Override
    public void onInitializeClient() {
        Rinor.init();
    }

    public static boolean isBaritonePresent() {
        return FabricLoader.getInstance().getModContainer("baritone").isPresent();
    }
}
