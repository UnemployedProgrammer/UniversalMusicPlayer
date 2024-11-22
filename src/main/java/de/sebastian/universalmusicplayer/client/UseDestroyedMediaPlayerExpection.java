package de.sebastian.universalmusicplayer.client;

public class UseDestroyedMediaPlayerExpection extends RuntimeException {
    public UseDestroyedMediaPlayerExpection(String name) {
        super("Tried to use destroyed media player, media player's name: " + name);
    }
}
