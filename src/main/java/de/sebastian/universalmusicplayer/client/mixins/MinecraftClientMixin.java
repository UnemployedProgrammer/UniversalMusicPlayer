package de.sebastian.universalmusicplayer.client.mixins;

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
        System.out.println("Shutting down Universal Music Player...");
        for (SoundTrackInstance soundInstance : SharedVars.ALL_SOUND_INSTANCES) {
            soundInstance.destroy();
            System.out.println("Destroyed '" + soundInstance.getId() + "' sound instance.");
        }
        System.out.println("Successfully shut down Universal Music Player!");
    }
}
