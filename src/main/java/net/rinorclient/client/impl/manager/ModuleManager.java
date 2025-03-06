package net.rinorclient.client.impl.manager;

import java.util.*;

import net.rinorclient.client.Rinor;
import net.rinorclient.client.RinorMod;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.impl.module.client.*;

public class ModuleManager {

    private final Map<String, Module> modules = Collections.synchronizedMap(new LinkedHashMap<>());

    public ModuleManager() {
        register(
                new ClickGuiModule(),
                new ColorsModule(),
                new HUDModule()
        );
        Rinor.info("Registered {} modules!", modules.size());
    }

    public void postInit() {
    }

    private void register(Module... modules) {
        for (Module module : modules) {
            register(module);
        }
    }

    private void register(Module module) {
        modules.put(module.getId(), module);
    }

    public Module getModule(String id) {
        return modules.get(id);
    }

    public List<Module> getModules() {
        return new ArrayList<>(modules.values());
    }
}
