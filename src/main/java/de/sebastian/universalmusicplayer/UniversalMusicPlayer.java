package de.sebastian.universalmusicplayer;

import de.sebastian.universalmusicplayer.client.ConfigSaver;
import de.sebastian.universalmusicplayer.client.SharedVars;
import de.sebastian.universalmusicplayer.client.SoundTrackInstance;
import de.sebastian.universalmusicplayer.client.UseDestroyedMediaPlayerExpection;
import de.sebastian.universalmusicplayer.client.screen.TestScreen;
import de.sebastian.universalmusicplayer.client.screen.VLCNotInstalled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;

public class UniversalMusicPlayer implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    
    private Boolean shownVLCError = false;
    private static KeyBinding keyBinding;

    @Override
    public void onInitialize() {

        ScreenEvents.AFTER_INIT.register(((minecraftClient, screen, i, i1) -> {
            if(!shownVLCError) {
                if(screen instanceof TitleScreen) {
                    if(!(new NativeDiscovery().discover())) {
                        SharedVars.UNIVERSAL_MUSIC_PLAYER_ENABLED = false;
                        SharedVars.ALL_TOASTS_MUSIC_DUCKING = false;
                        SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = true;
                        shownVLCError = true;
                        MinecraftClient.getInstance().setScreen(new VLCNotInstalled(Text.literal("VLC not found.")));
                    }
                }
            }
        }));

        if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
            keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key.examplemod.spook", // The translation key of the keybinding's name
                    InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                    GLFW.GLFW_KEY_R, // The keycode of the key
                    "category.examplemod.test" // The translation key of the keybinding's category.
            ));

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (keyBinding.wasPressed()) {
                    MinecraftClient.getInstance().setScreen(new TestScreen());
                }
            });
        }

        LOGGER.info("Initializing Universal Music Player...");
        LOGGER.info("Loading Config...");
        ConfigSaver.Config config = ConfigSaver.loadConfig();
        if(config != null) {
            config.setReason("Initializing Mod");

            SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = config.defaultMinecraftMusicEnabled;
            SharedVars.ALL_TOASTS_MUSIC_DUCKING = config.ToastMusicDucking;
            SharedVars.MINECRAFT_MUSIC_FADE_DOWN = config.MinecraftMusicDucking;
            SharedVars.VLC_MUSIC_FADE_DOWN = config.VLCDucking;

            LOGGER.info(config.toString());

            LOGGER.info("Config Loaded!");

        } else {
            LOGGER.warn("Couldn't load config due to the config file not being there or several other reasons. This could be due to starting your minecraft instance with this mod the first time.");
        }
        LOGGER.info("Successfully Initialized Universal Music Player!");

    }
}
