package net.rinorclient.client.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.Identifiable;
import net.rinorclient.client.api.config.setting.*;
import net.rinorclient.client.api.macro.Macro;
import net.rinorclient.client.util.Globals;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class ConfigContainer implements Identifiable, Serializable<Config<?>>, Globals {
    protected final String name;
    private final Map<String, Config<?>> configurations = Collections.synchronizedMap(new LinkedHashMap<>());

    public ConfigContainer(String name) {
        this.name = name;
    }

    protected void register(Config<?> config) {
        config.setContainer(this);
        configurations.put(config.getId(), config);
    }

    protected void register(Config<?>... configs) {
        for (Config<?> config : configs) {
            register(config);
        }
    }

    protected void unregister(Config<?> config) {
        configurations.remove(config.getId());
    }

    protected void unregister(Config<?>... configs) {
        for (Config<?> config : configs) {
            unregister(config);
        }
    }

    public void reflectConfigs() {
        final ConfigFactory factory = new ConfigFactory(this);
        // populate container using reflection
        for (Field field : getClass().getDeclaredFields()) {
            if (Config.class.isAssignableFrom(field.getType())) {
                Config<?> config = factory.build(field);
                if (config == null) {
                    // failsafe for debugging purposes
                    Rinor.error("Value for field {} is null!", field);
                    continue;
                }
                register(config);
            }
        }
    }

    @Override
    public JsonObject toJson() {
        final JsonObject out = new JsonObject();
        out.addProperty("name", getName());
        out.addProperty("id", getId());
        final JsonArray array = new JsonArray();
        for (Config<?> config : getConfigs()) {
            if (config.getValue() instanceof Macro) {
                continue;
            }
            array.add(config.toJson());
        }
        out.add("configs", array);
        return out;
    }

    @Override
    public Config<?> fromJson(JsonObject jsonObj) {
        if (jsonObj.has("configs")) {
            JsonElement element = jsonObj.get("configs");
            if (!element.isJsonArray()) {
                return null;
            }
            for (JsonElement je : element.getAsJsonArray()) {
                if (!je.isJsonObject()) {
                    continue;
                }
                final JsonObject configObj = je.getAsJsonObject();
                final JsonElement id = configObj.get("id");
                //
                Config<?> config = getConfig(id.getAsString());
                if (config == null) {
                    continue;
                }
                try {
                    if (config instanceof ToggleConfig cfg) {
                        Boolean val = cfg.fromJson(configObj);
                        if (mc.world != null) {
                            if (val) {
                                cfg.enable();
                            } else {
                                cfg.disable();
                            }
                        } else {
                            cfg.setValue(val);
                        }
                    } else if (config instanceof BooleanConfig cfg) {
                        Boolean val = cfg.fromJson(configObj);
                        cfg.setValue(val);
                    } else if (config instanceof ColorConfig cfg) {
                        Color val = cfg.fromJson(configObj);
                        cfg.setValue(val);
                    } else if (config instanceof EnumConfig cfg) {
                        Enum<?> val = cfg.fromJson(configObj);
                        if (val != null) {
                            cfg.setValue(val);
                        }
                    } else if (config instanceof ItemListConfig cfg) {
                        List<?> val = cfg.fromJson(configObj);
                        cfg.setValue(val);
                    } else if (config instanceof NumberConfig cfg) {
                        Number val = cfg.fromJson(configObj);
                        cfg.setValue(val);
                    } else if (config instanceof StringConfig cfg) {
                        String val = cfg.fromJson(configObj);
                        cfg.setValue(val);
                    }
                }
                // couldn't parse Json value
                catch (Exception e) {
                    Rinor.error("Couldn't parse Json for {}!", config.getName());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return String.format("%s-container", name.toLowerCase());
    }

    public Config<?> getConfig(String id) {
        return configurations.get(id);
    }

    public Collection<Config<?>> getConfigs() {
        return configurations.values();
    }
}
