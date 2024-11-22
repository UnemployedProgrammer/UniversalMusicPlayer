package de.sebastian.universalmusicplayer.client;

import java.util.ArrayList;
import java.util.List;

public class SharedVars {
    public static Boolean DEFAULT_MINECRAFT_MUSIC_ENABLED = true;
    public static Boolean UNIVERSAL_MUSIC_PLAYER_ENABLED = true;
    public static Boolean ALL_TOASTS_MUSIC_DUCKING = true;
    public static Boolean MUSIC_CURRENTLY_DUCKED = true;
    public static Boolean MUSIC_VOLUME = true;
    public static Integer ACTIVE_TOAST_COUNT = 0;
    public static List<SoundTrackInstance> RESPECT_TOAST_DUCKING = new ArrayList<>();
    public static Double OLD_MINECRAFT_MUSIC_VAL = 1.0D;
}
