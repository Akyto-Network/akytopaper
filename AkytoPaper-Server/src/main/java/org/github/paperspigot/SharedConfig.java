package org.github.paperspigot;

import akyto.spigot.aSpigotConfig;
import com.google.common.base.Throwables;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.spigotmc.SpigotConfig;
import org.spigotmc.SpigotWorldConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SharedConfig {

    private static File CONFIG_FILE;
    private static final String HEADER = "This is the main configuration file for Spigot.\n"
            + "Command aliases also go in this file, just put what you would normally put in commands.yml under a commands: tag";

    /*========================================================================*/
    public static YamlConfiguration config;
    public static Map<String, Command> commands;
    /*========================================================================*/

    public static void init(File configFile) {
        CONFIG_FILE = configFile;
        config = new YamlConfiguration();
        try {
            config.load (CONFIG_FILE);
        } catch (IOException ex) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load sportpaper.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }
        config.options().header(HEADER);
        config.options().copyDefaults(true);

        commands = new HashMap<String, Command>();

        BukkitConfig.init();
        SpigotConfig.init();
        PaperSpigotConfig.init();
        SpigotWorldConfig.init();
        PaperSpigotWorldConfig.init();
        new aSpigotConfig();
    }

    public static void registerCommands() {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            MinecraftServer.
                    getServer().
                    server.
                    getCommandMap().
                    register(entry.getKey(), "Paper", entry.getValue());
        }
    }

    public static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        throw Throwables.propagate(ex.getCause());
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }

        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }

}
