package de.sebastian.universalmusicplayer.client.mixins;

import de.sebastian.universalmusicplayer.client.SharedVars;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicTracker.class)
public class DisableMusicMixin {
    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;tick()V", at = @At("HEAD"), cancellable = true)
    private void redirectTick(CallbackInfo ci) {
        if(!SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED) {
            ci.cancel();
        }
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectPlay(MusicSound musicSound, CallbackInfo ci) {
        if(!SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED) {
            ci.cancel();
            System.out.println("Canceled play!");
        }
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(MusicSound musicSound, CallbackInfo ci) {
        if(!SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED) {
            ci.cancel();
            System.out.println("Canceled stop!");
        }
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop()V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(CallbackInfo ci) {
        if(!SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED) {
            ci.cancel();
            System.out.println("Canceled stop!");
        }
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;isPlayingType(Lnet/minecraft/sound/MusicSound;)Z", at = @At("HEAD"), cancellable = true)
    private void redirectIsPlayingType(MusicSound musicSound, CallbackInfoReturnable<Boolean> cir) {
        if(!SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED) {
            cir.cancel();
            System.out.println("Canceled is_playing_type!");
        }
    }
}
