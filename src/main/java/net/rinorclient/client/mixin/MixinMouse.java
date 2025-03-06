package net.rinorclient.client.mixin;

import net.minecraft.client.Mouse;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.impl.event.events.MouseClickEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods,
                               CallbackInfo ci) {
        MouseClickEvent mouseClickEvent = new MouseClickEvent(button, action);
        Rinor.EVENT_HANDLER.dispatch(mouseClickEvent);
        if (mouseClickEvent.isCanceled()) {
            ci.cancel();
        }
    }
}
