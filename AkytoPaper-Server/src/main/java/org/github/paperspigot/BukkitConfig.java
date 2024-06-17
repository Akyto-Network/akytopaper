package org.github.paperspigot;

import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Map;

public class BukkitConfig {

    public static YamlConfiguration config;
    static Map<String, Command> commands;

    public static void init()
    {
        config = SharedConfig.config;
        commands = SharedConfig.commands;
        SharedConfig.readConfig( BukkitConfig.class, null );
    }

    private static boolean getBoolean(String path, boolean def)
    {
        path = "bukkit." + path;
        config.addDefault( path, def );
        return config.getBoolean( path, config.getBoolean( path ) );
    }

    private static double getDouble(String path, double def)
    {
        path = "bukkit." + path;
        config.addDefault( path, def );
        return config.getDouble( path, config.getDouble( path ) );
    }

    private static int getInt(String path, int def)
    {
        path = "bukkit." + path;
        config.addDefault( path, def );
        return config.getInt( path, config.getInt( path ) );
    }

    private static <T> List getList(String path, T def)
    {
        path = "bukkit." + path;
        config.addDefault( path, def );
        return (List<T>) config.getList( path, config.getList( path ) );
    }

    private static String getString(String path, String def)
    {
        path = "bukkit." + path;
        config.addDefault( path, def );
        return config.getString( path, config.getString( path ) );
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection("bukkit." + path);
    }

    public static boolean allowEnd = true;
    public static boolean warnOnOverlaod = true;
    public static String permissionsFile = "permissions.yml";
    public static String updateFolder = "update";
    public static boolean pluginProfiling = false;
    public static int connectionThrottle = 4000;
    public static boolean queryPlugins = true;
    public static String deprecatedVerbose = "default";
    public static String shutdownMessage = "Server closed";
    public static boolean useExactLoginLocation = false;
    public static String worldContainer = ".";
    private static void settings() {
        allowEnd = getBoolean("settings.allow-end", allowEnd);
        warnOnOverlaod = getBoolean("settings.warn-on-overload", warnOnOverlaod);
        permissionsFile = getString("settings.update-folder", updateFolder);
        pluginProfiling = getBoolean("settings.plugin-profilling", pluginProfiling);
        connectionThrottle = getInt("settings.connection-throttle", connectionThrottle);
        queryPlugins = getBoolean("settings.query-plugins", queryPlugins);
        deprecatedVerbose = getString("settings.deprecated-verbose", deprecatedVerbose);
        shutdownMessage = getString("settings.shutdown-message", shutdownMessage);
        useExactLoginLocation = getBoolean("settings.use-exact-login-location", useExactLoginLocation);
        worldContainer = getString("settings.world-container", worldContainer);
    }

    public static int monsterSpawnLimit = 70;
    public static int animalSpawnLimit = 15;
    public static int waterAnimalSpawnLimit = 5;
    public static int ambientSpawnLimit = 15;
    private static void spawnLimits() {
        monsterSpawnLimit = getInt("spawn-limits.monsters", monsterSpawnLimit);
        animalSpawnLimit = getInt("spawn-limits.animals", animalSpawnLimit);
        waterAnimalSpawnLimit = getInt("spawn-limits.water-animals", waterAnimalSpawnLimit);
        ambientSpawnLimit = getInt("spawn-limits.ambient", ambientSpawnLimit);
    }

    public static int chunkGCPeriodInTicks = 600;
    public static int chunkGCLoadThreshold = 0;
    private static void chunkGC() {
        chunkGCPeriodInTicks = getInt("chunk-gc.period-in-ticks", chunkGCPeriodInTicks);
        chunkGCLoadThreshold = getInt("chunk-gc.load-threshold", chunkGCLoadThreshold);
    }

    public static int ticksPerAnimalSpawn = 400;
    public static int ticksPerMonsterSpawn = 1;
    public static int ticksPerAutosave = 6000;
    private static void ticksPer() {
        ticksPerAnimalSpawn = getInt("ticks-per.animal-spawns", ticksPerAnimalSpawn);
        ticksPerMonsterSpawn = getInt("ticks-per.monster-spawns", ticksPerMonsterSpawn);
        ticksPerAutosave = getInt("ticks-per.autosave", ticksPerAutosave);
    }

    public static String databaseUsername = "bukkit";
    public static String databaseIsolation = "SERIALIZABLE";
    public static String databaseDriver = "org.sqlite.JDBC";
    public static String databasePassword = "walrus";
    public static String databaseUrl = "jdbc:sqlite:{DIR}{NAME}.db";
    private static void database() {
        databaseUsername = getString("database.username", databaseUsername);
        databaseIsolation = getString("database.isolation", databaseIsolation);
        databaseDriver = getString("database.driver", databaseDriver);
        databasePassword = getString("database.password", databasePassword);
        databaseUrl = getString("database.url", databaseUrl);
    }

}
