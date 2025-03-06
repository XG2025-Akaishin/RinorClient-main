package net.rinorclient.client.impl.module.client;

import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.BooleanConfig;
import net.rinorclient.client.api.config.setting.NumberConfig;
import net.rinorclient.client.api.module.ConcurrentModule;
import net.rinorclient.client.api.module.ModuleCategory;
import net.rinorclient.client.api.module.ToggleModule;

/**
 * @author linus
 * @since 1.0
 */
public class RotationsModule extends ToggleModule {
    //
    Config<Float> preserveTicksConfig = new NumberConfig<>("PreserveTicks", "Time to preserve rotations after reaching the target rotations", 0.0f, 10.0f, 20.0f);
    Config<Boolean> movementFixConfig = new BooleanConfig("MovementFix", "Fixes movement on Grim when rotating", false);
    //
    private float prevYaw;

    /**
     *
     */
    public RotationsModule() {
        super("Rotations", "Manages client rotations",
                ModuleCategory.CLIENT);
    }

    public boolean getMovementFix() {
        return movementFixConfig.getValue();
    }

    /**
     * @return
     */
    public float getPreserveTicks() {
        return preserveTicksConfig.getValue();
    }
}
