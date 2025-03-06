package net.rinorclient.client.api.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import net.rinorclient.client.Rinor;
import net.rinorclient.client.api.macro.MacroFile;
import net.rinorclient.client.api.module.Module;
import net.rinorclient.client.api.module.file.ModuleConfigFile;
import net.rinorclient.client.api.module.file.ModuleFile;
import net.rinorclient.client.api.social.SocialFile;
import net.rinorclient.client.api.social.SocialRelation;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.util.Globals;

/**
 * @author linus
 * @see ConfigFile
 * @since 1.0
 */
public class ClientConfiguration implements Globals {
    // Set of configuration files that must be saved and loaded. This can be
    // modified after init.
    private final Set<ConfigFile> files = new HashSet<>();
    // Main client directory. This folder will contain all locally saved
    // configurations for the client.
    private Path clientDir;

    /**
     *
     */
    public ClientConfiguration() {
        final Path runningDir = mc.runDirectory.toPath();
        try {
            File homeDir = new File(System.getProperty("user.home"));
            clientDir = homeDir.toPath();
        }
        // will resort to running dir if client does not have access to the 
        // user home dir
        catch (Exception e) {
            Rinor.error("Could not access home dir, defaulting to running dir");
            e.printStackTrace();
            clientDir = runningDir;
        } finally {
            // cannot write, minecraft always has access to the running dir
            if (clientDir == null || !Files.exists(clientDir)
                    || !Files.isWritable(clientDir)) {
                clientDir = runningDir;
            }
            clientDir = clientDir.resolve("rinor");
            // create client directory
            if (!Files.exists(clientDir)) {
                try {
                    Files.createDirectory(clientDir);
                }
                // write error
                catch (IOException e) {
                    Rinor.error("Could not create client dir");
                    e.printStackTrace();
                }
            }
            Path configDir = clientDir.resolve("Configs");
            if (!Files.exists(configDir)) {
                try {
                    Files.createDirectory(configDir);
                }
                // write error
                catch (IOException e) {
                    Rinor.error("Could not create config dir");
                    e.printStackTrace();
                }
            }
        }
        files.add(new MacroFile(clientDir));
        for (Module module : Managers.MODULE.getModules()) {
            // files.add(new ModulePreset(clientDir.resolve("Defaults"), module));
            files.add(new ModuleFile(clientDir.resolve("Modules"), module));
        }
        for (SocialRelation relation : SocialRelation.values()) {
            files.add(new SocialFile(clientDir, relation));
        }
    }

    /**
     *
     */
    public void saveClient() {
        for (ConfigFile file : files) {
            file.save();
        }
    }

    /**
     *
     */
    public void loadClient() {
        for (ConfigFile file : files) {
            file.load();
        }
    }

    public void saveModuleConfiguration(String configFile) {
        ModuleConfigFile file = new ModuleConfigFile(clientDir.resolve("Configs"), configFile);
        file.save();
    }

    public void loadModuleConfiguration(String configFile) {
        ModuleConfigFile file = new ModuleConfigFile(clientDir.resolve("Configs"), configFile);
        file.load();
    }

    public Set<ConfigFile> getFiles() {
        return files;
    }

    public void addFile(final ConfigFile configFile) {
        files.add(configFile);
    }

    public void removeFile(final ConfigFile configFile) {
        files.remove(configFile);
    }

    /**
     * @return
     */
    public Path getClientDirectory() {
        return clientDir;
    }
}
