package de.sebastian.universalmusicplayer.client.mixins;

import de.sebastian.universalmusicplayer.client.SharedVars;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
public abstract class ToastMusicUnDuckingMixin {
    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdate(CallbackInfo ci) {
        ToastManagerEntryAccessorMixin accessor = (ToastManagerEntryAccessorMixin) this;

        // Check if the toast has finished rendering
        if (accessor.isFinishedRendering()) {
            if(SharedVars.ALL_TOASTS_MUSIC_DUCKING) {
                MinecraftClient client = MinecraftClient.getInstance();
                SharedVars.ACTIVE_TOAST_COUNT = Math.clamp(SharedVars.ACTIVE_TOAST_COUNT - 1, 0, 999999999);
                SharedVars.MUSIC_CURRENTLY_DUCKED = true;
                if(SharedVars.ACTIVE_TOAST_COUNT == 0) {
                    SharedVars.MUSIC_CURRENTLY_DUCKED = false;
                    client.options.getSoundVolumeOption(SoundCategory.MUSIC).setValue(SharedVars.OLD_MINECRAFT_MUSIC_VAL);
                }
            }
        }
    }
}
