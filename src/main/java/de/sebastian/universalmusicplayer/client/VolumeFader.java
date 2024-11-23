package de.sebastian.universalmusicplayer.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

public class VolumeFader {
    private static MinecraftClient client = MinecraftClient.getInstance();

    /**
     * Smoothly adjusts the volume to a target level using a cubic curve over 0.5 seconds.
     *
     * @param mediaPlayer   The VLCJ MediaPlayer instance.
     * @param targetVolume  The target volume (0 to 100).
     */
    public static void setVolumeCubic(MediaPlayer mediaPlayer, int targetVolume) {
        if (mediaPlayer == null || targetVolume < 0 || targetVolume > 100) {
            System.err.println("Invalid media player or target volume.");
            return;
        }

        // Create a separate thread for volume adjustment
        new Thread(() -> {
            int initialVolume = mediaPlayer.audio().volume();
            int volumeDifference = targetVolume - initialVolume;
            int steps = 50; // Divide the 0.5s duration into 50 steps (10 ms per step)
            double duration = 0.5; // Total duration in seconds

            for (int step = 0; step <= steps; step++) {
                // Calculate the time ratio (progress) from 0.0 to 1.0
                double progress = (double) step / steps;
                // Apply a cubic easing function
                double easedProgress = Math.pow(progress, 3);

                // Compute the current volume using the eased progress
                int currentVolume = initialVolume + (int) (easedProgress * volumeDifference);

                // Set the current volume
                mediaPlayer.audio().setVolume(currentVolume);

                // Sleep for the step duration (10 ms)
                try {
                    Thread.sleep((long) (duration * 1000 / steps));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Exit on interruption
                }
            }
        }).start();
    }

    /**
     * Smoothly adjusts the volume to a target level using a cubic curve over 0.5 seconds.
     *
     * @param targetVolume  The target volume (0 to 100).
     */
    public static void setMinecraftVolumeCubic(int targetVolume) {
        if (targetVolume < 0 || targetVolume > 100) {
            throw new IllegalArgumentException("Invalid media player or target volume.");
        }

        // Create a separate thread for volume adjustment
        new Thread(() -> {
            int initialVolume = (int) (client.options.getSoundVolumeOption(SoundCategory.MUSIC).getValue() * 100);
            int volumeDifference = targetVolume - initialVolume;
            int steps = 50; // Divide the 0.5s duration into 50 steps (10 ms per step)
            double duration = 0.5; // Total duration in seconds

            for (int step = 0; step <= steps; step++) {
                // Calculate the time ratio (progress) from 0.0 to 1.0
                double progress = (double) step / steps;
                // Apply a cubic easing function
                double easedProgress = Math.pow(progress, 3);

                // Compute the current volume using the eased progress
                int currentVolume = initialVolume + (int) (easedProgress * volumeDifference);

                // Set the current volume
                client.options.getSoundVolumeOption(SoundCategory.MUSIC).setValue(((double) currentVolume) / 100);

                // Sleep for the step duration (10 ms)
                try {
                    Thread.sleep((long) (duration * 1000 / steps));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Exit on interruption
                }
            }
        }).start();
    }
}
