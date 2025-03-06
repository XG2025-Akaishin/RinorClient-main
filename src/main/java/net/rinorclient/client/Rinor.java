package net.rinorclient.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.rinorclient.client.api.Identifiable;
import net.rinorclient.client.api.event.handler.EventBus;
import net.rinorclient.client.api.event.handler.EventHandler;
import net.rinorclient.client.api.file.ClientConfiguration;
import net.rinorclient.client.init.Managers;
import net.rinorclient.client.init.Modules;
import net.rinorclient.client.util.Hwid;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Rinor {
    public static Logger LOGGER;
    public static EventHandler EVENT_HANDLER;
    public static ClientConfiguration CONFIG;
    public static ShutdownHook SHUTDOWN;
    public static Executor EXECUTOR;

    public static void init() {
        LOGGER = LogManager.getLogger("Rinor");
        //new Hwid();
        info("Starting preInit ...");
        EXECUTOR = Executors.newFixedThreadPool(1);
        EVENT_HANDLER = new EventBus();
        info("Starting init ...");
        Managers.init();
        Modules.init();
        info("Starting postInit ...");
        CONFIG = new ClientConfiguration();
        Managers.postInit();
        SHUTDOWN = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(SHUTDOWN);
        CONFIG.loadClient();
        //if (!new Hwid().bo()) {
        //    MinecraftClient.getInstance().scheduleStop();
        //}
    }

    public static void info(String message) {
        LOGGER.info(String.format("[Rinor] %s", message));
    }

    public static void info(String message, Object... params) {
        LOGGER.info(String.format("[Rinor] %s", message), params);
    }

    public static void info(Identifiable feature, String message) {
        LOGGER.info(String.format("[%s] %s", feature.getId(), message));
    }

    public static void info(Identifiable feature, String message,
                            Object... params) {
        LOGGER.info(String.format("[%s] %s", feature.getId(), message), params);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    public static void error(Identifiable feature, String message) {
        LOGGER.error(String.format("[%s] %s", feature.getId(), message));
    }

    public static void error(Identifiable feature, String message,
                             Object... params) {
        LOGGER.error(String.format("[%s] %s", feature.getId(), message), params);
    }
}
