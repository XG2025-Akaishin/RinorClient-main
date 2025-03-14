package net.rinorclient.client.impl.gui.click.impl.config;

import net.minecraft.client.gui.DrawContext;
import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.macro.Macro;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ToggleModule;
import net.rinorclient.client.api.render.RenderManager;
import net.rinorclient.client.api.render.anim.Animation;
import net.rinorclient.client.api.render.anim.Easing;
import net.rinorclient.client.impl.gui.click.component.Button;
import net.rinorclient.client.impl.gui.click.impl.config.setting.*;
import net.rinorclient.client.init.Modules;

import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleButton extends Button {
    private final Module module;
    private final List<ConfigButton<?>> configComponents = new CopyOnWriteArrayList<>();
    private float off;
    private boolean open;
    private final Animation settingsAnimation = new Animation(Easing.CUBIC_IN_OUT, 200);

    @SuppressWarnings("unchecked")
    public ModuleButton(Module module, CategoryFrame frame, float x, float y) {
        super(frame, x, y, 103.0f, 13.0f);
        this.module = module;
        for (Config<?> config : module.getConfigs()) {
            if (config.getName().equalsIgnoreCase("Enabled")) {
                continue;
            }
            if (config.getValue() instanceof Boolean) {
                configComponents.add(new CheckboxButton(frame, this,
                        (Config<Boolean>) config, x, y));
            } else if (config.getValue() instanceof Double) {
                configComponents.add(new SliderButton<>(frame, this,
                        (Config<Double>) config, x, y));
            } else if (config.getValue() instanceof Float) {
                configComponents.add(new SliderButton<>(frame, this,
                        (Config<Float>) config, x, y));
            } else if (config.getValue() instanceof Integer) {
                configComponents.add(new SliderButton<>(frame, this,
                        (Config<Integer>) config, x, y));
            } else if (config.getValue() instanceof Enum<?>) {
                configComponents.add(new DropdownButton(frame, this,
                        (Config<Enum<?>>) config, x, y));
            } else if (config.getValue() instanceof String) {
                configComponents.add(new TextButton(frame, this,
                        (Config<String>) config, x, y));
            } else if (config.getValue() instanceof Macro) {
                configComponents.add(new BindButton(frame, this,
                        (Config<Macro>) config, x, y));
            } else if (config.getValue() instanceof Color) {
                configComponents.add(new ColorButton(frame, this,
                        (Config<Color>) config, x, y));
            }
        }
        open = false;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {
        render(context, x, y, mouseX, mouseY, delta);
    }

    public void render(DrawContext context, float ix, float iy, float mouseX,
                       float mouseY, float delta) {
        x = ix;
        y = iy;
        float scaledTime = 1.0f;
        boolean fill = !(module instanceof ToggleModule t) || (scaledTime = t.getAnimation().getScaledTime()) > 0.01f;
        scaledTime *= 1.7f;
        if (module.getName().equalsIgnoreCase("ClickGui")) {
            scaledTime = 1.7f;
        }
        rectGradient(context, fill ? Modules.CLICK_GUI.getColor(scaledTime) : 0x555555, fill ? Modules.CLICK_GUI.getColor1(scaledTime) : 0x555555);
        RenderManager.renderText(context, module.getName(), ix + 2, iy + 3.5f, scaledTime > 0.99f ? -1 : 0xaaaaaa);
        if (settingsAnimation.getScaledTime() > 0.01f) {
            off = y + height + 1.0f;
            float fheight = 0.0f;
            for (ConfigButton<?> configButton : configComponents) {
                if (!configButton.getConfig().isVisible()) {
                    continue;
                }
                fheight += configButton.getHeight();
                if (configButton instanceof ColorButton colorPicker && colorPicker.getScaledTime() > 0.01f) {
                    fheight += colorPicker.getPickerHeight() * colorPicker.getScaledTime() * getScaledTime();
                }
            }
            enableScissor((int) x, (int) (off - 1.0f), (int) (x + width), (int) (off + 2.0f + (fheight * settingsAnimation.getScaledTime())));
            for (ConfigButton<?> configButton : configComponents) {
                if (!configButton.getConfig().isVisible()) {
                    continue;
                }
                // run draw event
                configButton.render(context, ix + 2.0f, off, mouseX, mouseY, delta);
                ((CategoryFrame) frame).offset(configButton.getHeight() * settingsAnimation.getScaledTime());
                off += configButton.getHeight();
            }
            if (fill) {
                fill(context, ix, y + height, 1.0f, off - (y + height) + 1.0f, Modules.CLICK_GUI.getColor1(scaledTime));
                fill(context, ix + width - 1.0f, y + height, 1.0f, off - (y + height) + 1.0f, Modules.CLICK_GUI.getColor(scaledTime));
                fillGradient(context, ix, off + 1.0f, ix + width, off + 2.0f, Modules.CLICK_GUI.getColor(scaledTime),  Modules.CLICK_GUI.getColor1(scaledTime));
            }
            disableScissor();
            ((CategoryFrame) frame).offset(3.0f * settingsAnimation.getScaledTime());
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isWithin(mouseX, mouseY)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && module instanceof ToggleModule t) {
                t.toggle();
                // ToggleGuiEvent toggleGuiEvent = new ToggleGuiEvent(t);
                // Caspian.EVENT_HANDLER.dispatch(toggleGuiEvent);
            } else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                open = !open;
                settingsAnimation.setState(open);
            }
        }
        if (open) {
            for (ConfigButton<?> component : configComponents) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (open) {
            for (ConfigButton<?> component : configComponents) {
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (open) {
            for (ConfigButton<?> component : configComponents) {
                component.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    public void offset(float in) {
        off += in;
    }

    public boolean isOpen() {
        return open;
    }

    public float getScaledTime() {
        return settingsAnimation.getScaledTime();
    }

    public Module getModule() {
        return module;
    }

    public List<ConfigButton<?>> getConfigButtons() {
        return configComponents;
    }
}
