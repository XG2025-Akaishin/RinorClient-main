package net.rinorclient.client.impl.event.events;

import net.rinorclient.client.api.event.Cancelable;
import net.rinorclient.client.api.event.Event;

@Cancelable
public class MouseClickEvent extends Event {
    private final int button;
    private final int action;

    public MouseClickEvent(int button, int action) {
        this.button = button;
        this.action = action;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }
}
