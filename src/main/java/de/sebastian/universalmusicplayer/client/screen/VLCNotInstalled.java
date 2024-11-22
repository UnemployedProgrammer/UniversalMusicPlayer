package de.sebastian.universalmusicplayer.client.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

public class VLCNotInstalled extends Screen {
    public VLCNotInstalled(Text title) {
        super(title);
    }

    public class OSUtils {

        public enum OS {
            WINDOWS,
            MACOS,
            LINUX,
            UNKNOWN
        }

        public static OS getOperatingSystem() {
            String osName = System.getProperty("os.name").toLowerCase();

            if (osName.contains("win")) {
                return OS.WINDOWS;
            } else if (osName.contains("mac")) {
                return OS.MACOS;
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
                return OS.LINUX;
            } else {
                return OS.UNKNOWN;
            }
        }

        public static String getLink() {
            OS os = getOperatingSystem();
            return switch (os) {
                case WINDOWS -> "https://www.videolan.org/vlc/download-windows.html";
                case MACOS -> "https://www.videolan.org/vlc/download-macosx.html";
                case LINUX -> "https://www.videolan.org/vlc/#download";
                default -> "https://www.videolan.org/vlc/#download";
            };
        }
    }

    @Override
    protected void init() {
        ButtonWidget open_page = addDrawableChild(ButtonWidget.builder(Text.literal(Text.translatable("universalmusicplayer.gui.vlcinstallbtn").getString().replace("%s", System.getProperty("os.name"))), (btn) -> {
            Util.getOperatingSystem().open(OSUtils.getLink());
            MinecraftClient.getInstance().setScreen(null);
        }).dimensions(width / 2 - 100, height / 2 + 15, 200, 20).build());

        ButtonWidget dismiss = addDrawableChild(ButtonWidget.builder(Text.translatable("universalmusicplayer.gui.dismiss"), (btn) -> {
            MinecraftClient.getInstance().setScreen(null);
        }).dimensions(width / 2 - 100, height / 2 + 45, 200, 20).tooltip(Tooltip.of(Text.translatable("universalmusicplayer.gui.vlcinstallcancel.tooltip"))).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, "VLC is not installed on the System!", width / 2, height / 2, 0xFFFFFF);
    }
}
