package net.rinorclient.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.impl.event.events.Render2DEvent;
import net.rinorclient.client.util.Globals;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud implements Globals {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Render2DEvent renderOverlayEvent = new Render2DEvent(context, tickCounter.getTickDelta(true));
        Rinor.EVENT_HANDLER.dispatch(renderOverlayEvent);
    }
}
