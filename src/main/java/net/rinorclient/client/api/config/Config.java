package net.rinorclient.client.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.Identifiable;
import net.rinorclient.client.api.config.setting.*;
import net.rinorclient.client.api.event.EventStage;
import net.rinorclient.client.api.render.anim.Animation;
import net.rinorclient.client.api.render.anim.Easing;
import net.rinorclient.client.impl.event.ConfigUpdateEvent;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Supplier;

public abstract class Config<T> implements Identifiable, Serializable<T> {
    private final String name;
    private final String desc;
    protected T value;
    private final T defaultValue;
    private ConfigContainer container;
    private Supplier<Boolean> visible;
    protected final Animation configAnimation = new Animation(Easing.CUBIC_IN_OUT, 200);

    public Config(String name, String desc, T value) {
        if (value == null) {
            throw new NullPointerException("Null values not supported");
        }
        this.name = name;
        this.desc = desc;
        this.value = value;
        this.defaultValue = value;
    }

    public Config(String name, String desc, T value, Supplier<Boolean> visible) {
        this(name, desc, value);
        this.visible = visible;
    }

    @Internal
    public Config(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.defaultValue = null;
    }

    @Override
    public JsonObject toJson() {
        final JsonObject obj = new JsonObject();
        obj.addProperty("name", getName());
        obj.addProperty("id", getId());
        return obj;
    }

    @Override
    public T fromJson(JsonObject obj) {
        if (obj.has("value")) {
            JsonElement element = obj.get("value");
            return (T) (Byte) element.getAsByte();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return String.format("%s-%s-config", container.getName().toLowerCase(), name.toLowerCase());
    }

    public String getDescription() {
        return desc;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T val) {
        if (val == null) {
            throw new NullPointerException("Null values not supported!");
        }
        final ConfigUpdateEvent event = new ConfigUpdateEvent(this);
        // PRE
        event.setStage(EventStage.PRE);
        Rinor.EVENT_HANDLER.dispatch(event);
        value = val;
        // POST
        event.setStage(EventStage.POST);
        Rinor.EVENT_HANDLER.dispatch(event);
    }

    public ConfigContainer getContainer() {
        return container;
    }

    public void setContainer(final ConfigContainer cont) {
        container = cont;
    }

    public Animation getAnimation() {
        return configAnimation;
    }

    public boolean isVisible() {
        if (visible != null) {
            return visible.get();
        }
        return true;
    }

    public void resetValue() {
        setValue(defaultValue);
    }
}


