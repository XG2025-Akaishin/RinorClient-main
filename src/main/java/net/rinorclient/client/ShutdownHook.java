package net.rinorclient.client;

public class ShutdownHook extends Thread {
    public ShutdownHook() {
        setName("Rinor-ShutdownHook");
    }

    @Override
    public void run() {
        Rinor.info("Saving configurations and shutting down!");
        Rinor.CONFIG.saveClient();
    }
}
