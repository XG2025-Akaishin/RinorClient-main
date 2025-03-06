package net.rinorclient.client.util;

import net.minecraft.client.MinecraftClient;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public interface Globals {
    MinecraftClient mc = MinecraftClient.getInstance();
    //
    Random RANDOM = ThreadLocalRandom.current();
}
