package de.sebastian.universalmusicplayer.client;

import de.sebastian.universalmusicplayer.UniversalMusicPlayer;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class VLCManager {
    public class VLCPlayerManager {

        private static MediaPlayerFactory factory;

        // Initialize VLC player instance
        public static MediaPlayer createPlayerInstance() {
            if (factory == null) {
                factory = new MediaPlayerFactory(); // Creates the media player factory
            }
            return factory.mediaPlayers().newMediaPlayer(); // Creates a media player instance
        }

        // Release the media player instance (deletes player)
        public static void deletePlayerInstance(MediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
                UniversalMusicPlayer.LOGGER.info("Media player instance deleted");
            }
        }
    }
}
