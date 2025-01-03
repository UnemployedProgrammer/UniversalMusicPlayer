package de.sebastian.universalmusicplayer.client.screen;

import com.terraformersmc.modmenu.gui.ModsScreen;
import de.sebastian.universalmusicplayer.client.MinecraftMusicFeatures;
import de.sebastian.universalmusicplayer.client.SharedVars;
import de.sebastian.universalmusicplayer.client.screen.widget.NumberOnlyTextFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ConfigScreen extends Screen {
    Screen parent;

    private Boolean madeChanges = false;
    private Boolean newToastMusicDucking = SharedVars.ALL_TOASTS_MUSIC_DUCKING;
    private Boolean newDefaultMusic = SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED;
    private Integer newDuckingMinecraftMusic = SharedVars.MINECRAFT_MUSIC_FADE_DOWN;
    private Integer newDuckingVLCMusic = SharedVars.VLC_MUSIC_FADE_DOWN;

    private ButtonWidget toastMusicDucking;
    private ButtonWidget testAudioDucking;
    private ButtonWidget defaultMusic;
    private NumberOnlyTextFieldWidget duckingMinecraft;
    private NumberOnlyTextFieldWidget duckingVLC;

    private ButtonWidget saveQuitWidget;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Mod Config Screen"));
        this.parent = parent;
    }

    private Text getTranslatedBoolean(Boolean bool) {
        return bool ? Text.translatable("universalmusicplayer.gui.config.enabled").formatted(Formatting.GREEN) : Text.translatable("universalmusicplayer.gui.config.disabled").formatted(Formatting.RED);
    }

    public static boolean isMouseBetween(int mX, int mY, int x1, int y1, int x2, int y2) {
        // Ensure x1 < x2 and y1 < y2 regardless of the input order
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        // Check if the mouse coordinates are within the bounds
        return mX >= minX && mX <= maxX && mY >= minY && mY <= maxY;
    }

    private void updateMadeChangesVar() {
        madeChanges = false;
        if(!(newDefaultMusic == SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED)) {
            madeChanges = true;
        }

        if(!(newToastMusicDucking == SharedVars.ALL_TOASTS_MUSIC_DUCKING)) {
            madeChanges = true;
        }

        if(!Objects.equals(newDuckingMinecraftMusic, SharedVars.MINECRAFT_MUSIC_FADE_DOWN)) {
            madeChanges = true;
        }

        if(!Objects.equals(newDuckingVLCMusic, SharedVars.VLC_MUSIC_FADE_DOWN)) {
            madeChanges = true;
        }
    }

    private void applyChanges() {
        if(!(newDefaultMusic == SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED)) {
            SharedVars.DEFAULT_MINECRAFT_MUSIC_ENABLED = newDefaultMusic;
        }

        if(!(newToastMusicDucking == SharedVars.ALL_TOASTS_MUSIC_DUCKING)) {
            SharedVars.ALL_TOASTS_MUSIC_DUCKING = newToastMusicDucking;
        }

        if(!Objects.equals(newDuckingMinecraftMusic, SharedVars.MINECRAFT_MUSIC_FADE_DOWN)) {
            SharedVars.MINECRAFT_MUSIC_FADE_DOWN = newDuckingMinecraftMusic;
        }

        if(!Objects.equals(newDuckingVLCMusic, SharedVars.VLC_MUSIC_FADE_DOWN)) {
            SharedVars.VLC_MUSIC_FADE_DOWN = newDuckingVLCMusic;
        }
    }

    @Override
    protected void init() {

        saveQuitWidget = addDrawableChild(ButtonWidget.builder(Text.translatable("universalmusicplayer.gui.config.save_quit"), (btn) -> {
            if(madeChanges) {
                this.client.getToastManager().add(
                        SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.translatable("universalmusicplayer.gui.config.esc.saved.title"), Text.translatable("universalmusicplayer.gui.config.esc.saved"))
                );
                applyChanges();
            }
            this.client.setScreen(parent);
        }).dimensions(20, height - 30, width - 40, 20).build());
        saveQuitWidget.setTooltip(Tooltip.of(Text.translatable("universalmusicplayer.gui.config.esc.tooltip")));


        toastMusicDucking = addDrawableChild(ButtonWidget.builder(getTranslatedBoolean(newToastMusicDucking), (btn) -> {
            if(!SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG) {
                newToastMusicDucking = !newToastMusicDucking;
            }
            btn.setMessage(getTranslatedBoolean(newToastMusicDucking));
            updateMadeChangesVar();
        }).dimensions(width - 120, height / 2 - 60, 100, 20).build());
        if(SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG) {
            toastMusicDucking.active = false;
        }

        testAudioDucking = addDrawableChild(ButtonWidget.builder(Text.literal(""), (btn) -> {
            if (!SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG) {
                MinecraftClient.getInstance().setScreen(new TestAudioDucking(this));
            }
        }).dimensions(width - 150, height / 2 - 60, 20, 20).build());
        if(SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG) {
            testAudioDucking.active = false;
        }

        defaultMusic = addDrawableChild(ButtonWidget.builder(getTranslatedBoolean(newDefaultMusic), (btn) -> {
            if(!SharedVars.MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG) {
                newDefaultMusic = !newDefaultMusic;
                if(!newDefaultMusic) {
                    MinecraftMusicFeatures.stopCurrentMinecraftSounds();
                }
            }
            btn.setMessage(getTranslatedBoolean(newDefaultMusic));
            updateMadeChangesVar();
        }).dimensions(width - 120, height / 2 - 30, 100, 20).build());
        if(SharedVars.MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG) {
            defaultMusic.active = false;
        }

        duckingVLC = addDrawableChild(new NumberOnlyTextFieldWidget(width - 120, height / 2, 100, 20, Text.literal("60"), 0, 100));
        duckingVLC.setChangedListener(value -> {
            newDuckingVLCMusic = Math.clamp(duckingVLC.getIntValue(), 0, 100);
            updateMadeChangesVar();
        });
        duckingVLC.setText(String.valueOf(newDuckingVLCMusic));

        duckingMinecraft = addDrawableChild(new NumberOnlyTextFieldWidget(width - 120, height / 2 + 30, 100, 20, Text.literal("20"), 0, 100));
        duckingMinecraft.setChangedListener(value -> {
            newDuckingMinecraftMusic = Math.clamp(duckingMinecraft.getIntValue(), 0, 100);
            updateMadeChangesVar();
        });
        duckingMinecraft.setText(String.valueOf(newDuckingMinecraftMusic));

        super.init();
    }



    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.gui.config.title"), width / 2, 10, 0xFFFFFF);

        context.drawTexture(RenderLayer::getGuiTextured, Identifier.of("universalmusicplayer","audio_ducking.png"), width - 148, height / 2 - 58, 0, 0, 16, 16, 16, 16);

        //Titles

        blurDisabledValues(context);

        context.drawText(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.toast_music_ducking.title"), 40, height / 2 - 50, 0xFFFFFF, false);
        context.drawText(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.default_mc_music.title"), 40, height / 2 - 20, 0xFFFFFF, false);
        context.drawText(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.ducking_vlc.title"), 40, height / 2 + 10, 0xFFFFFF, false);
        context.drawText(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.ducking_mc.title"), 40, height / 2 + 40, 0xFFFFFF, false);

        toolTips(context, mouseX, mouseY);
    }

    public void toolTips(DrawContext ctx, int mX, int mY) {
        if(isMouseBetween(mX, mY, 0, height / 2 - 60, width, height / 2 - 40)) {
            ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG ? Text.literal(Text.translatable("universalmusicplayer.gui.config.entry_disabled").getString().replace("%modname", SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCKED_BY)) : Text.translatable("universalmusicplayer.config.entry.toast_music_ducking"), mX, mY);
        }

        if(isMouseBetween(mX, mY, 0, height / 2 - 30, width, height / 2 - 10)) {
            ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, SharedVars.MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG ? Text.literal(Text.translatable("universalmusicplayer.gui.config.entry_disabled").getString().replace("%modname", SharedVars.MINECRAFT_MUSIC_ENABLED_LOCKED_BY)) : Text.translatable("universalmusicplayer.config.entry.default_mc_music"), mX, mY);
        }

        if(isMouseBetween(mX, mY, 0, height / 2, width, height / 2 + 20)) {
            ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.ducking_vlc"), mX, mY);
        }

        if(isMouseBetween(mX, mY, 0, height / 2 + 30, width, height / 2 + 50)) {
            ctx.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable("universalmusicplayer.config.entry.ducking_mc"), mX, mY);
        }
    }

    public void blurDisabledValues(DrawContext ctx) {
        if(SharedVars.MINECRAFT_TOAST_MUSIC_DUCKING_LOCK_IN_CONFIG) {
            ctx.enableScissor(0, height / 2 - 60, width, height / 2 - 40);
            applyBlur();
            ctx.disableScissor();
        }

        if(SharedVars.MINECRAFT_MUSIC_ENABLED_LOCK_IN_CONFIG) {
            ctx.enableScissor(0, height / 2 - 30, width, height / 2 - 10);
            applyBlur();
            ctx.disableScissor();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // 256 is the key code for ESC
            this.client.setScreen(parent);
            if(madeChanges) {
                this.client.getToastManager().add(
                        SystemToast.create(this.client, SystemToast.Type.NARRATOR_TOGGLE, Text.translatable("universalmusicplayer.gui.config.esc.not_saved.title"), Text.translatable("universalmusicplayer.gui.config.esc.not_saved"))
                );
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
