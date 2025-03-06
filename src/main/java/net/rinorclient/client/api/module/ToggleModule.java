package net.rinorclient.client.api.module;

import org.lwjgl.glfw.GLFW;

import net.rinorclient.client.api.Hideable;
import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.BooleanConfig;
import net.rinorclient.client.api.config.setting.MacroConfig;
import net.rinorclient.client.api.config.setting.ToggleConfig;
import net.rinorclient.client.api.macro.Macro;
import net.rinorclient.client.api.render.anim.Animation;
import net.rinorclient.client.api.render.anim.Easing;


public class ToggleModule extends Module implements Hideable {
    private final Animation animation = new Animation(Easing.CUBIC_IN_OUT);
    Config<Boolean> enabledConfig = new ToggleConfig("Enabled", "The module" + " enabled state. This state is true when the module is running.", false);

    Config<Macro> keybindingConfig = new MacroConfig("Keybind", "The module " +"keybinding. Pressing this key will toggle the module enabled " +"state. Press [BACKSPACE] to delete the keybind.",new Macro(getId(), GLFW.GLFW_KEY_UNKNOWN, () -> toggle()));

    Config<Boolean> hiddenConfig = new BooleanConfig("Hidden", "The hidden " +"state of the module in the Arraylist", false);


    public ToggleModule(String name, String desc, ModuleCategory category) {
        super(name, desc, category);
        // Toggle settings
        register(keybindingConfig, enabledConfig, hiddenConfig);
    }

    public ToggleModule(String name, String desc, ModuleCategory category,
                        Integer keycode) {
        this(name, desc, category);
        keybind(keycode);
    }


    @Override
    public boolean isHidden() {
        return hiddenConfig.getValue();
    }

    @Override
    public void setHidden(boolean hidden) {
        hiddenConfig.setValue(hidden);
    }

    public void toggle() {
        if (isEnabled()) {
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        enabledConfig.setValue(true);
        onEnable();
    }

    public void disable() {
        enabledConfig.setValue(false);
        onDisable();
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public void keybind(int keycode) {
        keybindingConfig.setContainer(this);
        ((MacroConfig) keybindingConfig).setValue(keycode);
    }

    public boolean isEnabled() {
        return enabledConfig.getValue();
    }

    public Macro getKeybinding() {
        return keybindingConfig.getValue();
    }

    public Animation getAnimation() {
        return animation;
    }
}
