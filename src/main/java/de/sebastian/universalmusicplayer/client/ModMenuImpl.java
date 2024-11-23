package de.sebastian.universalmusicplayer.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.sebastian.universalmusicplayer.client.screen.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        return new ConfigScreen(parent);
    }
}
