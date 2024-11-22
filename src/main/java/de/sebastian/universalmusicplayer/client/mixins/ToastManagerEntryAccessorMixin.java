package de.sebastian.universalmusicplayer.client.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
public interface ToastManagerEntryAccessorMixin {
    @Accessor("finishedRendering")
    boolean isFinishedRendering();
}
