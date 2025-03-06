package net.rinorclient.client.impl.gui.click.impl.config.setting;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Formatting;
import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.setting.MacroConfig;
import net.rinorclient.client.api.macro.Macro;
import net.rinorclient.client.api.render.RenderManager;
import net.rinorclient.client.impl.gui.click.impl.config.CategoryFrame;
import net.rinorclient.client.impl.gui.click.impl.config.ModuleButton;
import net.rinorclient.client.impl.module.client.ClickGuiModule;

import static org.lwjgl.glfw.GLFW.*;

public class BindButton extends ConfigButton<Macro> {
    // Check for whether we are listening for an input
    private boolean listening;

    public BindButton(CategoryFrame frame, ModuleButton moduleButton, Config<Macro> config, float x, float y) {
        super(frame, moduleButton, config, x, y);
    }

    @Override
    public void render(DrawContext context, float ix, float iy, float mouseX,
                       float mouseY, float delta) {
        // If to allow the GUI to be exited by pressing ESC
        ClickGuiModule.CLICK_GUI_SCREEN.setCloseOnEscape(!listening);

        x = ix;
        y = iy;
        final Macro macro = config.getValue();
        String val = listening ? "..." : macro.getKeyName();
        rect(context, 0x00000000);
        RenderManager.renderText(context, config.getName() + Formatting.GRAY
                + " " + val, ix + 2.0f, iy + 4.0f, -1);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isWithin(mouseX, mouseY)) {
            if (button == GLFW_MOUSE_BUTTON_1) {
                listening = !listening;
            } else if (button == GLFW_MOUSE_BUTTON_2 && !listening) {
                // Reset the bind
                ((MacroConfig) config).setValue(GLFW_KEY_UNKNOWN);
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listening) {
            // unbind
            if (keyCode == GLFW_KEY_ESCAPE || keyCode == GLFW_KEY_BACKSPACE) {
                ((MacroConfig) config).setValue(GLFW_KEY_UNKNOWN);
            } else {
                ((MacroConfig) config).setValue(keyCode);
            }
            listening = false;
        }
    }

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }
}
