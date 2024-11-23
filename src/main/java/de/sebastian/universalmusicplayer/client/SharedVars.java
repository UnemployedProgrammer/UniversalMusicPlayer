package de.sebastian.universalmusicplayer.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedVars {
    public static Boolean DEFAULT_MINECRAFT_MUSIC_ENABLED = true;
    public static Boolean UNIVERSAL_MUSIC_PLAYER_ENABLED = true;
    public static Boolean ALL_TOASTS_MUSIC_DUCKING = true;
    public static Boolean MUSIC_CURRENTLY_DUCKED = true;
    public static Boolean MUSIC_VOLUME = true;
    public static Integer ACTIVE_TOAST_COUNT = 0;
    public static List<SoundTrackInstance> RESPECT_TOAST_DUCKING = new ArrayList<>();
    public static Map<String, Integer> RESPECT_TOAST_DUCKING_OLD_VOLUME = new HashMap<>();
    public static List<SoundTrackInstance> ALL_SOUND_INSTANCES = new ArrayList<>();
    public static Double OLD_MINECRAFT_MUSIC_VAL = 1.0D;

    public static Boolean MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG = true;
    public static String MINECRAFT_MUSIC_ENABLED_LOCKED_BY = "Example Mod";
    public static Boolean MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG = true;
    public static String MINECRAFT_TOAST_MUSIC_DUCKING_LOCKED_BY = "Example Mod";

    public static Integer MINECRAFT_MUSIC_FADE_DOWN = 20;
    public static Integer VLC_MUSIC_FADE_DOWN = 60;
}
