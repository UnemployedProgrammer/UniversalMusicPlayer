package de.sebastian.universalmusicplayer.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.sebastian.universalmusicplayer.UniversalMusicPlayer;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigSaver {
    // Create a class to hold the configuration
    public static class Config {
        public Boolean defaultMinecraftMusicEnabled;
        public Boolean ToastMusicDucking;
        public Integer MinecraftMusicDucking;
        public Integer VLCDucking;
        public String creationReason;

        public static Config builder(Boolean DefaultMinecraftMusicEnabled, Boolean ToastMusicDucking, Integer MinecraftMusicDucking, Integer VLCDucking, String creationReason) {
            Config config = new Config();
            config.defaultMinecraftMusicEnabled = DefaultMinecraftMusicEnabled;
            config.ToastMusicDucking = ToastMusicDucking;
            config.MinecraftMusicDucking = MinecraftMusicDucking;
            config.VLCDucking = VLCDucking;
            config.creationReason = creationReason;
            return config;
        }

        public Config setReason(String creationReason) {
            this.creationReason = creationReason;
            return this;
        }

        @Override
        public String toString() {
            return """
                    Config with Values stated below: \n
                    ----------------------------------------- \n
                    Default Minecraft Music Enabled: %1 \n
                    Toast Music Ducking Enabled: %2 \n
                    Minecraft Music Ducking: %3 \n
                    VLC Music Ducking: %4 \n
                    ----------------------------------------- \n
                    Config Creation/Load Reason: %r
                    """
                    .replace("%1", defaultMinecraftMusicEnabled.toString())
                    .replace("%2", ToastMusicDucking.toString())
                    .replace("%3", MinecraftMusicDucking.toString())
                    .replace("%4", VLCDucking.toString())
                    .replace("%r", creationReason);
        }


    }

    // File path for saving and loading configuration
    private static final String CONFIG_FILE = new File(MinecraftClient.getInstance().runDirectory, "config/universalmusicplayer.json").toString();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Method to save the config to a file
    public static Config saveConfig(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
            UniversalMusicPlayer.LOGGER.info("Config saved to " + CONFIG_FILE);
        } catch (IOException e) {
            UniversalMusicPlayer.LOGGER.error("Failed to save config: " + e.getMessage());
        }
        return config;
    }

    // Method to load the config from a file
    public static Config loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            UniversalMusicPlayer.LOGGER.error("Failed to load config: " + e.getMessage());
            return null;
        }
    }
}
