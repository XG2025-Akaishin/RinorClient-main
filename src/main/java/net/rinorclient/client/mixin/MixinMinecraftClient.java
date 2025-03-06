package net.rinorclient.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.event.EventStage;
import net.rinorclient.client.impl.event.*;
import net.rinorclient.client.impl.event.events.TickEvent;
import net.rinorclient.client.impl.imixin.IMinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author linus
 * @since 1.0
 */
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient implements IMinecraftClient {
    @Shadow
    public ClientWorld world;
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void hookTickPre(CallbackInfo ci) {
        if (player != null && world != null) {
            TickEvent tickPreEvent = new TickEvent();
            tickPreEvent.setStage(EventStage.PRE);
            Rinor.EVENT_HANDLER.dispatch(tickPreEvent);
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void hookTickPost(CallbackInfo ci) {
        if (player != null && world != null) {
            TickEvent tickPostEvent = new TickEvent();
            tickPostEvent.setStage(EventStage.POST);
            Rinor.EVENT_HANDLER.dispatch(tickPostEvent);
        }
    }
}
