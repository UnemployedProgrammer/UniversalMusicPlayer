package de.sebastian.universalmusicplayer.client.mixins;

import de.sebastian.universalmusicplayer.client.SharedVars;
import de.sebastian.universalmusicplayer.client.SoundTrackInstance;
import de.sebastian.universalmusicplayer.client.VolumeFader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class ToastMusicDuckingMixin {
    @Inject(method = "add", at = @At("HEAD"))
    private void onToastAdded(Toast toast, CallbackInfo ci) {
        // Access MinecraftClient instance
        if(SharedVars.ALL_TOASTS_MUSIC_DUCKING) {
            MinecraftClient client = MinecraftClient.getInstance();
            SharedVars.OLD_MINECRAFT_MUSIC_VAL = client.options.getSoundVolumeOption(SoundCategory.MUSIC).getValue();

            if(SharedVars.OLD_MINECRAFT_MUSIC_VAL != 0.0D) {
                VolumeFader.setMinecraftVolumeCubic(SharedVars.MINECRAFT_MUSIC_FADE_DOWN);
            }
            for (SoundTrackInstance soundInstance : SharedVars.RESPECT_TOAST_DUCKING) {
                if(soundInstance.getVolume() != 0) {
                    SharedVars.RESPECT_TOAST_DUCKING_OLD_VOLUME.put(soundInstance.getId(), soundInstance.getVolume());
                    VolumeFader.setVolumeCubic(soundInstance.getMediaPlayer(), SharedVars.VLC_MUSIC_FADE_DOWN);
                }
            }
            SharedVars.ACTIVE_TOAST_COUNT++;
            SharedVars.MUSIC_CURRENTLY_DUCKED = true;
        }
    }
}
