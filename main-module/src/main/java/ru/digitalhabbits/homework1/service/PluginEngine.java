package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;

public class PluginEngine {

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {


        String result = "";

        try{
            PluginInterface pluginInterface = cls.getConstructor().newInstance();
            result = pluginInterface.apply(text);
        }catch (Exception ex) {ex.getMessage();}

        return result;
    }
}
