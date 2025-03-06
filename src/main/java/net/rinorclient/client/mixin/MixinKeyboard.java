package net.rinorclient.client.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.impl.event.events.keyboard.KeyboardInputEvent;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void injectOnKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (client.getWindow().getHandle() == window) {
            KeyboardInputEvent keyboardInputEvent = new KeyboardInputEvent(key, action);
            Rinor.EVENT_HANDLER.dispatch(keyboardInputEvent);
            if (keyboardInputEvent.isCanceled()) {
                ci.cancel();
            }
        }
    }
}