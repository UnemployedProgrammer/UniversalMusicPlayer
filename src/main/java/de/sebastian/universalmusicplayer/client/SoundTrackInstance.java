package de.sebastian.universalmusicplayer.client;

import uk.co.caprica.vlcj.player.base.MediaPlayer;

import java.io.File;
import java.util.UUID;

/**
 * This Class is self-explanatory!
 * Create a new instance with SoundTrackInstance.create();
 */
public class SoundTrackInstance {
    private File soundTrack;
    private String id;
    private MediaPlayer mediaPlayer;
    private Boolean destroyed = false;
    private final Boolean respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING;

    private SoundTrackInstance(File soundTrack, String id, Boolean respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING) {
        this.soundTrack = soundTrack;
        this.id = id;
        this.respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING = respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING;
        this.mediaPlayer = VLCManager.VLCPlayerManager.createPlayerInstance();
    }

    /**
     * @param volume The base volume. Value can be changed later on via setVolume()
     * @param soundTrack The base soundtrack. Value can be changed later on via setSoundTrack()
     * @param respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING If the audio's volume should be lowered, if a toast notification appears.
     * @param name You can name the audio player here, you can leave this string empty, but it's recommended to choose a name for easier error fixing.
     * @return New SoundTrackInstance.
     * */
    public static SoundTrackInstance create(int volume, File soundTrack, Boolean respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING, String name) {
        SoundTrackInstance instance = new SoundTrackInstance(soundTrack, name + "-" + UUID.randomUUID().toString(), respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING).setInitVol(volume);
        if(respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING) {
            SharedVars.RESPECT_TOAST_DUCKING.add(instance);
        }
        SharedVars.ALL_SOUND_INSTANCES.add(instance);
        instance.getMediaPlayer().controls().setTime(0);
        instance.getMediaPlayer().controls().setPosition(0);
        instance.getMediaPlayer().media().startPaused(soundTrack.getAbsolutePath());
        System.out.println("Preparing: " + soundTrack.getAbsolutePath());
        return instance;
    }

    private SoundTrackInstance setInitVol(int vol) {
        setVolume(vol);
        return this;
    }

    public Boolean getRespectsSharedVars_ALL_TOASTS_MUSIC_DUCKING() {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        return respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING;
    }

    public Boolean isMediaPlayerDestroyed() {
        return destroyed;
    }

    public MediaPlayer getMediaPlayer() {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        return mediaPlayer;
    }

    public void setVolume(int volume) {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        mediaPlayer.audio().setVolume(volume);
    }

    public void pause() {
        mediaPlayer.controls().pause();
    }

    public void play() {
        mediaPlayer.controls().play();
    }

    public void stop() {
        mediaPlayer.controls().stop();
    }

    public Integer getVolume() {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        return mediaPlayer.audio().volume();
    }

    public File getSoundTrack() {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        return soundTrack;
    }

    public void destroy() {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        if(respectsSharedVars_ALL_TOASTS_MUSIC_DUCKING) {
            if(SharedVars.RESPECT_TOAST_DUCKING.contains(this)) {
                SharedVars.RESPECT_TOAST_DUCKING.remove(this);
            }
        }
        destroyed = true;
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void setSoundTrack(File newSoundTrack) {
        if(destroyed) {
            throw new UseDestroyedMediaPlayerExpection(id);
        }
        soundTrack = newSoundTrack;
        mediaPlayer.controls().stop();
        mediaPlayer.media().play(newSoundTrack.getAbsolutePath());
    }

    public String getId() {
        return id;
    }
}
