package net.rinorclient.client.api.config;

import java.lang.reflect.Field;

import net.rinorclient.client.Rinor;

public class ConfigFactory {
    // The object to grab from
    protected final Object configObj;

    public ConfigFactory(Object configObj) {
        this.configObj = configObj;
    }

    public Config<?> build(Field f) {
        f.setAccessible(true);
        // attempt to extract object from field
        try {
            return (Config<?>) f.get(configObj);
        }
        // field getter error
        catch (IllegalArgumentException | IllegalAccessException e) {
            Rinor.error("Failed to build config from field {}!", f.getName());
            e.printStackTrace();
        }
        // failed config creation
        throw new RuntimeException("Invalid field!");
    }
}
