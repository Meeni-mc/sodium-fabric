package me.jellysquid.mods.sodium.client;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.util.UnsafeUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;

public class SodiumClientMod implements ClientModInitializer {
    private static String VERSION;
    private static SodiumGameOptions CONFIG;
    private static Logger LOGGER;

    @Override
    public void onInitializeClient() {

    }

    public static String version() {
        if (VERSION == null) {
            ModContainer ourContainer = FabricLoader.getInstance().getModContainer("sodium")
                    .orElseThrow(() -> new IllegalStateException("Mod with ID \"sodium\" couldn't be found... but that's us!"));
            VERSION = ourContainer.getMetadata().getVersion().getFriendlyString();
            if ("${version}".equals(VERSION))
                VERSION = Formatting.RED + "(!!) Devel";
        }

        return VERSION;
    }

    public static SodiumGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    public static Logger logger() {
        if (LOGGER == null) {
            LOGGER = LogManager.getLogger("Sodium");
        }

        return LOGGER;
    }

    private static SodiumGameOptions loadConfig() {
        SodiumGameOptions config = SodiumGameOptions.load(Paths.get("config", "sodium-options.json"));
        onConfigChanged(config);

        return config;
    }

    public static void onConfigChanged(SodiumGameOptions options) {
        UnsafeUtil.setEnabled(options.advanced.useMemoryIntrinsics);
    }
}
