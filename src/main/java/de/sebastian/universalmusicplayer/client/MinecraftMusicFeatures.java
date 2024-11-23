package de.sebastian.universalmusicplayer.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;

public class MinecraftMusicFeatures {
    //Minecraft Music

    /**
     * Disables the default Minecraft music played in-game and in the menu.
     */
    public static void disableStandardMinecraftMusic() {
        SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = false;
    }

    /**
     * Enables the default Minecraft music played in-game and in the menu.
     */
    public static void enableStandardMinecraftMusic() {
        SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = true;
    }

    /**
     * Enables/Disables the default Minecraft music played in-game and in the menu.
     * @param enabled Specify if the music should be enabled (= true)/disabled (= false).
     */
    public static void setStandardMinecraftMusicEnabled(Boolean enabled) {
        SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = enabled;
    }

    /**
     * If disabled, doesn't reduce the music volume when a toast notification appears.
     */
    public static void disableMinecraftToastMusicDucking() {
        SharedVars.ALL_TOASTS_MUSIC_DUCKING = false;
    }

    /**
     * If enabled, reduces the music volume when a toast notification appears.
     */
    public static void enableMinecraftToastMusicDucking() {
        SharedVars.ALL_TOASTS_MUSIC_DUCKING = true;
    }

    /**
     * If enabled, reduces the music volume when a toast notification appears.
     * If disabled, doesn't reduce the music volume when a toast notification appears.
     * @param enabled Specify if the music ducking should be enabled (= true)/disabled (= false).
     */
    public static void setMinecraftToastMusicDucking(Boolean enabled) {
        SharedVars.ALL_TOASTS_MUSIC_DUCKING = enabled;
    }

    /**
     * @return Returns, if Universal Music Player is enabled (Working).
     * This var gets disabled, for ex. when a user doesn't have VLC installed.
     */
    public static Boolean getUniversalMusicPlayerEnabled() {
        return SharedVars.UNIVERSAL_MUSIC_PLAYER_ENABLED;
    }

    /**
     * This stops all current minecraft sounds, including music.
     */
    public static void stopCurrentMinecraftSounds() {
        MinecraftClient.getInstance().getSoundManager().stopAll();
    }


    /**
     * This locks 'StandardMinecraftMusicEnabled' value in config, so users can't enable/disable it.
     * @param lockedByModName The name of the mod, which locked the config, helpful for users! Will display like: 'Entry locked by Example Mod!'
     */
    public static void setStandardMinecraftMusicLocked(String lockedByModName) {
        SharedVars.MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG = true;
        SharedVars.MINECRAFT_MUSIC_ENABLED_LOCKED_BY = lockedByModName;
    }

    /**
     * This unlocks 'StandardMinecraftMusicEnabled' value in config, so users can enable/disable it manually.
     */
    public static void setStandardMinecraftMusicUnlocked() {
        MinecraftClient.getInstance().getSoundManager().stopAll();
    }

    /**
     * This locks 'ToastMusicDucking' value in config, so users can't enable/disable it.
     * @param lockedByModName The name of the mod, which locked the config, helpful for users! Will display like: 'Entry locked by Example Mod!'
     */
    public static void setToastMusicDuckingLocked(String lockedByModName) {
        SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG = true;
        SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCKED_BY = lockedByModName;
    }

    /**
     * This unlocks 'ToastMusicDucking' value in config, so users can enable/disable it manually.
     */
    public static void setToastMusicDuckingUnlocked() {
        MinecraftClient.getInstance().getSoundManager().stopAll();
    }
}
