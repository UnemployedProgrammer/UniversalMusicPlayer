package de.sebastian.universalmusicplayer.client.mixins;

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
        ci.cancel();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;play(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectPlay(MusicSound musicSound, CallbackInfo ci) {
        System.out.println("Canceled play!");
        ci.cancel();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop(Lnet/minecraft/sound/MusicSound;)V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(MusicSound musicSound, CallbackInfo ci) {
        System.out.println("Canceled stop!");
        ci.cancel();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;stop()V", at = @At("HEAD"), cancellable = true)
    private void redirectStop(CallbackInfo ci) {
        System.out.println("Canceled stop!");
        ci.cancel();
    }

    @Inject(method = "Lnet/minecraft/client/sound/MusicTracker;isPlayingType(Lnet/minecraft/sound/MusicSound;)Z", at = @At("HEAD"), cancellable = true)
    private void redirectIsPlayingType(MusicSound musicSound, CallbackInfoReturnable<Boolean> cir) {
        System.out.println("Canceled is_playing_type!");
        cir.cancel();
    }
}
