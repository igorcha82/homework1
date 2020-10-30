package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        int line;
        int countWorlds = 0;
        int letters;

        String inputText = text.replaceAll("\\\\n", "\n").toLowerCase();
        Pattern pattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
        Matcher matcher = pattern.matcher(inputText);

        line = inputText.split("\n").length;

        while (matcher.find()) {
            countWorlds++;
        }

        letters = inputText.length();

        return line + ";" + countWorlds + ";" + letters;
    }
}
