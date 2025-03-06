package net.rinorclient.client.impl.event.events;

import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.event.StageEvent;

/**
 * @author linus
 * @since 1.0
 */
public class ConfigUpdateEvent extends StageEvent {
    //
    private final Config<?> config;

    /**
     * @param config
     */
    public ConfigUpdateEvent(Config<?> config) {
        this.config = config;
    }

    /**
     * @return
     */
    public Config<?> getConfig() {
        return config;
    }
}