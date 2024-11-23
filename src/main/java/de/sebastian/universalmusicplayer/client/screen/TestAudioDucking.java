package de.sebastian.universalmusicplayer.client.screen;

import de.sebastian.universalmusicplayer.client.SharedVars;
import de.sebastian.universalmusicplayer.client.SoundTrackInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestAudioDucking extends Screen {
    private Screen previous;
    private Boolean audioDuckingPreviousValue;
    private SoundTrackInstance audioInstance;
    private Integer ticksLeft = 100;
    private Boolean shouldCountDown = false;

    public TestAudioDucking(Screen previous) {
        super(Text.literal("Test Audio Ducking Screen"));
        audioDuckingPreviousValue = SharedVars.ALL_TOASTS_MUSIC_DUCKING;
        this.previous = previous;
        audioInstance = SoundTrackInstance.create(100, prepareAudioFile(), true, "Test Audio Ducking");
    }

    public File prepareAudioFile() {
        String assetPath = "test_audio_ducking.mp3";
        // Get the Minecraft directory
        File mcDir = MinecraftClient.getInstance().runDirectory;

        // Target file location in .minecraft
        Path targetPath = mcDir.toPath().resolve("test_audio_ducking.mp3");

        try (InputStream inputStream = getClass().getResourceAsStream("/assets/universalmusicplayer/" + assetPath)) {
            if (inputStream == null) {
                System.err.println("Asset file not found: " + assetPath);
                return new File("");
            }

            // Ensure the target directory exists
            Files.createDirectories(targetPath.getParent());

            // Copy the file
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
        }
        return new File(mcDir, "test_audio_ducking.mp3");
    }

    @Override
    protected void init() {
        SharedVars.ALL_TOASTS_MUSIC_DUCKING = true;
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.back"), (btn) -> {
            SharedVars.ALL_TOASTS_MUSIC_DUCKING = audioDuckingPreviousValue;
            audioInstance.destroy();
            this.client.setScreen(previous);
        }).dimensions(20, height - 30, width - 40, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("universalmusicplayer.gui.config.try_ducking"), (btn) -> {
            if(!audioInstance.getMediaPlayer().status().isPlaying()) {
                audioInstance.getMediaPlayer().controls().setPosition(0.0f);
                audioInstance.getMediaPlayer().controls().setTime(0);
                audioInstance.play();
                ticksLeft = 100;
                shouldCountDown = true;
            } else {
                audioInstance.pause();
                shouldCountDown = false;
                ticksLeft = 100;
            }
        }).dimensions(20, height / 2 - 10, width - 40, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void tick() {
        if(shouldCountDown) {
            if(ticksLeft == 0) {
                this.client.getToastManager().add(
                        SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.literal("Test Toast"), Text.literal("Testing Audio Ducking..."))
                );
                shouldCountDown = false;
                ticksLeft = 100;
            }
            ticksLeft--;
        }
        super.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // 256 is the key code for ESC
            SharedVars.ALL_TOASTS_MUSIC_DUCKING = audioDuckingPreviousValue;
            audioInstance.destroy();
            this.client.setScreen(previous);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
