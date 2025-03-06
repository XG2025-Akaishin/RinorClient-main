package net.rinorclient.client.impl.event.events;

import net.minecraft.client.gui.DrawContext;
import net.rinorclient.client.api.event.Event;

public class Render2DEvent extends Event {
    private final DrawContext context;
    private float tickDelta;

    public Render2DEvent(DrawContext context, float tickDelta) {
        this.context = context;
    }

    public DrawContext getContext() {
        return context;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
