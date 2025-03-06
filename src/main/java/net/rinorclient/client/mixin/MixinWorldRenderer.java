package net.rinorclient.client.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.impl.event.events.RenderWorldEvent;
import net.rinorclient.client.util.Globals;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer implements Globals {

    @Unique
    MatrixStack current = null;

    @ModifyExpressionValue(method = "render", at = @At(value = "NEW", target = "net/minecraft/client/util/math/MatrixStack"))
    private MatrixStack setMatrixStack(MatrixStack matrixStack) {
        current = matrixStack;
        return matrixStack;
    }

    @Inject(method = "render", at = @At(value = "CONSTANT", args = "stringValue=blockentities", ordinal = 0), cancellable = true)
    private void afterEntities(CallbackInfo ci) {
        RenderWorldEvent event = new RenderWorldEvent(current, mc.getRenderTickCounter().getTickDelta(true));
        Rinor.EVENT_HANDLER.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
