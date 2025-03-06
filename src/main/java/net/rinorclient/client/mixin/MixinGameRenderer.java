package net.rinorclient.client.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.impl.event.events.RenderWorldEvent;
import net.rinorclient.client.util.Globals;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer implements Globals {

    /*@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1))
    private void hookRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        RenderWorldEvent.Game renderWorldEvent = new RenderWorldEvent.Game(matrices, tickDelta);
        Shoreline.EVENT_HANDLER.dispatch(renderWorldEvent);
    }*/
}
