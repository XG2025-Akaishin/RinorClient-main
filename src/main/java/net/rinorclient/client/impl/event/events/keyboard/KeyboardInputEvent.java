package net.rinorclient.client.impl.event.events.keyboard;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.rinorclient.client.api.event.Cancelable;
import net.rinorclient.client.api.event.Event;
import net.rinorclient.client.mixin.MixinKeyboard;

/**
 * Dispatched in {@link MixinKeyboard#hookOnKey(long, int, int, int, int, CallbackInfo)}
 *
 * @author linus
 * @see MixinKeyboard
 * @since 1.0
 */
@Cancelable
public class KeyboardInputEvent extends Event {
    // The inputted keycode in GLFW format
    private final int keycode;

    // The current key action
    private final int action;

    /**
     *
     * @param keycode
     * @param action
     */
    public KeyboardInputEvent(int keycode, int action) {
        this.keycode = keycode;
        this.action = action;
    }

    /**
     * Returns the inputted {@link org.lwjgl.glfw.GLFW} keycode
     *
     * @return The input key
     * @see #keycode
     */
    public int getKeycode() {
        return keycode;
    }

    /**
     *  Returns the action performed on the {@link org.lwjgl.glfw.GLFW} key
     * @return The current action
     * @see #action
     */
    public int getAction() {
        return action;
    }
}