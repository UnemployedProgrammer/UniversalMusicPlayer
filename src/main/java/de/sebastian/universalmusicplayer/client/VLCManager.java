package de.sebastian.universalmusicplayer.client;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class VLCManager {
    public class VLCPlayerManager {

        private MediaPlayerFactory factory;

        // Initialize VLC player instance
        public MediaPlayer createPlayerInstance() {
            if (factory == null) {
                factory = new MediaPlayerFactory(); // Creates the media player factory
            }
            return factory.mediaPlayers().newMediaPlayer(); // Creates a media player instance
        }

        // Release the media player instance (deletes player)
        public void deletePlayerInstance(MediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
                System.out.println("Media player instance deleted");
            }
        }
    }
}
