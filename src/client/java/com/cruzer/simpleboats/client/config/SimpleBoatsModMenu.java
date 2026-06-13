package com.cruzer.simpleboats.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class SimpleBoatsModMenu implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SimpleBoatsConfigScreen::create;
    }
}
