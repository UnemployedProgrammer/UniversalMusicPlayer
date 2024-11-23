package de.sebastian.universalmusicplayer.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.sebastian.universalmusicplayer.client.screen.ConfigScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if(SharedVars.UNIVERSAL_MUSIC_PLAYER_ENABLED) {
            return this::createConfigScreen;
        }
        return this::createAlertScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        return new ConfigScreen(parent);
    }

    private Screen createAlertScreen(Screen parent) {
        return new MessageScreen(Text.translatable("universalmusicplayer.gui.config.vlcnotinstalled"));
    }
}
