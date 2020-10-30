package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;


import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        List<Class<? extends PluginInterface>> pluginList = new ArrayList<>();

        File plugin = new File(pluginDirName);
        File[] files = plugin.listFiles(((dir, name) -> name.endsWith(PLUGIN_EXT)));

        if (files != null && files.length > 0) {
            ArrayList<String> nameClass = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>(files.length);
            for (File file : files) {
                try {
                    try (JarFile jarFile = new JarFile(file)) {
                        jarFile.stream().forEach(jarEntry -> {
                            if (jarEntry.getName().endsWith(".class")) {
                                nameClass.add(jarEntry.getName());
                            }
                        });
                    }

                    URL url = file.toURI().toURL();
                    urls.add(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[0]));
                nameClass.forEach(classes -> {
                    try {
                        Class cls = urlClassLoader.loadClass(classes.replaceAll("/", ".").replace(".class", ""));
                        Class[] interfaces = cls.getInterfaces();
                        for (Class i : interfaces) {
                            if (i.equals(PluginInterface.class)) {
                                pluginList.add(cls);
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }

        return pluginList;
    }
}
