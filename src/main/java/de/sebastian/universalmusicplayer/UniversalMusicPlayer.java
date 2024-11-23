package de.sebastian.universalmusicplayer;

import de.sebastian.universalmusicplayer.client.SharedVars;
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
import org.lwjgl.glfw.GLFW;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;

public class UniversalMusicPlayer implements ModInitializer {

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
    }
}
