package net.rinorclient.client.api.module;

import net.rinorclient.client.api.config.Config;
import net.rinorclient.client.api.config.ConfigContainer;
import net.rinorclient.client.api.config.Serializable;
import net.rinorclient.client.util.Globals;
import net.rinorclient.client.util.chat.ChatUtil;


public class Module extends ConfigContainer implements Globals {
    public static final String MODULE_ID_FORMAT = "%s-module";
    private final String desc;
    private final ModuleCategory category;

    public Module(String name, String desc, ModuleCategory category) {
        super(name);
        this.desc = desc;
        this.category = category;
    }

    protected void sendModuleMessage(String message) {
        ChatUtil.clientSendMessageRaw("§s[%s]§f %s", name, message);
    }

    protected void sendModuleMessage(String message, Object... params) {
        sendModuleMessage(String.format(message, params));
    }

    @Override
    public String getId() {
        return String.format(MODULE_ID_FORMAT, name.toLowerCase());
    }

    public String getDescription() {
        return desc;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public String getModuleData() {
        return "ARRAYLIST_INFO";
    }
}
