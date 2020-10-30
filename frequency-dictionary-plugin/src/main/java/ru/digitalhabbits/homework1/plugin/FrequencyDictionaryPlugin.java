package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        List<String> listText = new ArrayList<>();

        HashSet<String> unicumWorlds = new HashSet<>();
        String inputText = text.replaceAll("\\\\n", "\n").toLowerCase();
        Pattern pattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b");
        Matcher matcherString = pattern.matcher(inputText);

        while (matcherString.find()) {
            listText.add(matcherString.group());
            unicumWorlds.add(matcherString.group());
        }


        SortedMap<String, Integer> result = new TreeMap<>();

        for (String item : unicumWorlds) {

            int count = 0;
            for (int j = 0; j <= listText.size() - 1; j++) {
                if (item.equals(listText.get(j))) {
                    count++;


                }
                if (j == (listText.size() - 1)) {
                    result.put(item, count);
                    System.out.println(item + " " + count);
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder("");

        Iterator iterator = result.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            stringBuilder.append(key).append(" ").append((Integer) result.get(key)).append(" ");
        }

        return stringBuilder.toString();

    }
}

