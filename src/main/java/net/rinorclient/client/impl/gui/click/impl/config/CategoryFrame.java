package net.rinorclient.client.impl.gui.click.impl.config;

import net.minecraft.client.gui.DrawContext;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.ModuleCategory;
import net.rinorclient.client.api.render.RenderManager;
import net.rinorclient.client.api.render.anim.Animation;
import net.rinorclient.client.api.render.anim.Easing;
import net.rinorclient.client.impl.gui.click.ClickGuiScreen;
import net.rinorclient.client.impl.gui.click.component.Frame;
import net.rinorclient.client.impl.gui.click.impl.config.setting.ColorButton;
import net.rinorclient.client.impl.gui.click.impl.config.setting.ConfigButton;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.init.Modules;
import net.rinorclient.client.util.string.EnumFormatter;

import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CategoryFrame extends Frame {
    private final String name;
    private final ModuleCategory category;
    private final List<ModuleButton> moduleButtons = new CopyOnWriteArrayList<>();
    private float off, inner;
    private boolean open;
    private boolean drag;

    private final Animation categoryAnimation = new Animation(Easing.CUBIC_IN_OUT, 200);


    public CategoryFrame(ModuleCategory category, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.category = category;
        this.name = EnumFormatter.formatEnum(category);
        for (Module module : Managers.MODULE.getModules()) {
            if (module.getCategory() == category) {
                moduleButtons.add(new ModuleButton(module, this, x, y));
            }
        }
        categoryAnimation.setStateHard(true);
        open = true;
    }

    public CategoryFrame(ModuleCategory category, float x, float y) {
        this(category, x, y, 105.0f, 15.0f);
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {
        if (drag) {
            x += ClickGuiScreen.MOUSE_X - px;
            y += ClickGuiScreen.MOUSE_Y - py;
        }
        fheight = 2.0f;
        for (ModuleButton moduleButton : moduleButtons) {
            // account for button height
            fheight += moduleButton.getHeight() + 1.0f;
            if (moduleButton.getScaledTime() < 0.01f) {
                continue;
            }
            fheight += 3.0f * moduleButton.getScaledTime();
            for (ConfigButton<?> configButton : moduleButton.getConfigButtons()) {
                if (!configButton.getConfig().isVisible()) {
                    continue;
                }
                // config button height may vary
                fheight += configButton.getHeight() * moduleButton.getScaledTime();
                if (configButton instanceof ColorButton colorPicker && colorPicker.getScaledTime() > 0.01f) {
                    fheight += colorPicker.getPickerHeight() * colorPicker.getScaledTime() * moduleButton.getScaledTime();
                }
            }
        }
        if (y < -(fheight - 10)) {
            y = -(fheight - 10);
        }
        if (y > mc.getWindow().getHeight() - 10) {
            y = mc.getWindow().getHeight() - 10;
        }
        rect(context, Modules.CLICK_GUI.getColor(1.7f));
        RenderManager.renderText(context, name, x + 3.0f, y + 4.0f, -1);
        if (categoryAnimation.getScaledTime() > 0.01f) {
            enableScissor((int) x, (int) (y + height), (int) (x + width), (int) (y + height + fheight * categoryAnimation.getScaledTime()));
            fill(context, x, y + height, width, fheight, 0x77000000);
            off = y + height + 1.0f;
            inner = off;
            for (ModuleButton moduleButton : moduleButtons) {
                moduleButton.render(context, x + 1.0f, inner + 1.0f, mouseX, mouseY, delta);
                off += (moduleButton.getHeight() + 1.0f) * categoryAnimation.getScaledTime();
                inner += moduleButton.getHeight() + 1.0f;
            }
            disableScissor();
        }
        // update previous position
        px = ClickGuiScreen.MOUSE_X;
        py = ClickGuiScreen.MOUSE_Y;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == GLFW.GLFW_MOUSE_BUTTON_RIGHT && isWithin(mouseX, mouseY)) {
            open = !open;
            categoryAnimation.setState(open);
        }
        if (open) {
            for (ModuleButton button : moduleButtons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        drag = false;
        if (open) {
            for (ModuleButton button : moduleButtons) {
                button.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        super.keyPressed(keyCode, scanCode, modifiers);
        if (open) {
            for (ModuleButton button : moduleButtons) {
                button.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }

    public boolean isWithinTotal(float mx, float my) {
        return isMouseOver(mx, my, x, y, width, getTotalHeight());
    }

    public void offset(float in) {
        off += in;
        inner += in;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public float getTotalHeight() {
        return height + fheight;
    }

    public List<ModuleButton> getModuleButtons() {
        return moduleButtons;
    }

    public void setDragging(boolean drag) {
        this.drag = drag;
    }

    public boolean isDragging() {
        return drag;
    }
}
