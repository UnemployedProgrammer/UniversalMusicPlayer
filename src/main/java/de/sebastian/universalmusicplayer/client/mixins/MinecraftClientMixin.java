package de.sebastian.universalmusicplayer.client.mixins;

import de.sebastian.universalmusicplayer.UniversalMusicPlayer;
import de.sebastian.universalmusicplayer.client.ConfigSaver;
import de.sebastian.universalmusicplayer.client.SharedVars;
import de.sebastian.universalmusicplayer.client.SoundTrackInstance;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At(value = "HEAD"), method = "close")
    private void onClose(CallbackInfo ci) {
        UniversalMusicPlayer.LOGGER.info("Shutting down Universal Music Player...");
        for (SoundTrackInstance soundInstance : SharedVars.ALL_SOUND_INSTANCES) {
            soundInstance.destroy();
            UniversalMusicPlayer.LOGGER.info("Destroyed '" + soundInstance.getId() + "' sound instance.");
        }
        UniversalMusicPlayer.LOGGER.info("Saving Config...");
        UniversalMusicPlayer.LOGGER.info(ConfigSaver.saveConfig(ConfigSaver.Config.builder(SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED, SharedVars.ALL_TOASTS_MUSIC_DUCKING, SharedVars.MINECRAFT_MUSIC_FADE_DOWN, SharedVars.VLC_MUSIC_FADE_DOWN, "Saving Config")).toString());
        UniversalMusicPlayer.LOGGER.info("Config Saved!");
        UniversalMusicPlayer.LOGGER.info("Successfully shut down Universal Music Player!");
    }
}
