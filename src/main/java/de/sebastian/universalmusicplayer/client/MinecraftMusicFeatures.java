package de.sebastian.universalmusicplayer.client;

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
}
