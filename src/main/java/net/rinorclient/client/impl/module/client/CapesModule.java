package net.rinorclient.client.impl.module.client;

import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.BooleanConfig;
import net.rinorclient.client.api.module.ModuleCategory;
import net.rinorclient.client.api.module.ToggleModule;

/**
 * @author xgraza
 * @since 1.0
 */
public final class CapesModule extends ToggleModule
{
    Config<Boolean> optifineConfig = new BooleanConfig("Optifine", "If to show optifine capes", true);

    public CapesModule()
    {
        super("Capes", "Shows player capes", ModuleCategory.CLIENT);
        enable();
    }

    public Config<Boolean> getOptifineConfig()
    {
        return optifineConfig;
    }
}
