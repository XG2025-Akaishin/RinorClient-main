package net.rinorclient.client.impl.event.events.keyboard;

import net.minecraft.client.input.Input;
import net.rinorclient.client.api.event.Cancelable;
import net.rinorclient.client.api.event.Event;
import net.rinorclient.client.api.event.StageEvent;

@Cancelable
public class KeyboardTickEvent extends StageEvent {

    private final Input input;

    public KeyboardTickEvent(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }
}
