package akyto.spigot;

import com.google.common.base.Throwables;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

@Getter
@Setter
public class aSpigotConfig {

    private static final String HEADER = "This is the main configuration file for AkytoSpigot.\n"
                                         + "Modify with caution, and make sure you know what you are doing.\n";

    private File configFile;
    private YamlConfiguration config;

    private double horizontal;
    private double vertical;
    private double friction;
    private double extraHorizontal;
    private double extraVertical;
    private double verticalLimit;
    private double latency;
    private boolean hitDetect;
    private boolean smoothPotting;
    private boolean hidePlayersFromTab;
    private boolean antiglitchPearl;

    public aSpigotConfig() {
        this.configFile = new File("settings.yml");
        this.config = new YamlConfiguration();

        try {
            if (!configFile.exists())
                configFile.createNewFile();
            config.load(this.configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load settings.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }

        this.config.options().header(aSpigotConfig.HEADER);
        this.config.options().copyDefaults(true);
        this.loadConfig();
        aSpigot.INSTANCE.setConfig(this);
    }

    private void loadConfig() {
        this.horizontal = this.getDouble("horizontal", 0.35d);
        this.vertical = this.getDouble("vertical", 0.35d);
        this.friction = this.getDouble("friction", 2.0d);
        this.extraHorizontal = this.getDouble("extraHorizontal", 0.425d);
        this.extraVertical = this.getDouble("extraVertical", 0.085d);
        this.verticalLimit = this.getDouble("verticalLimit", 0.4d);
        this.latency = this.getDouble("latency", 15.0d);
        this.smoothPotting = this.getBoolean("smooth-potting", false);
        this.hitDetect = this.getBoolean("hitDetect", true);
        this.hidePlayersFromTab = this.getBoolean("hidePlayersFromTab", false);
        this.antiglitchPearl = this.getBoolean("antiglitchPearl", true);
        SpigotBridge.disableOpPermissions = this.getBoolean("disable-op", false);

        save();
    }

    public void save() {
        try {
            config.set("horizontal", horizontal);
            config.set("vertical", vertical);
            config.set("extraHorizontal", extraHorizontal);
            config.set("extraVertical", extraVertical);
            config.set("verticalLimit", verticalLimit);
            config.set("latency", latency);
            config.save(configFile);
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + configFile, ex);
        }
    }

    public void set(String path, Object val) {
        this.config.set(path, val);

        save();
    }

    public Set<String> getKeys(String path) {
        if (!this.config.isConfigurationSection(path)) {
            this.config.createSection(path);
            return new HashSet<>();
        }

        return this.config.getConfigurationSection(path).getKeys(false);
    }

    public boolean getBoolean(String path, boolean def) {
        this.config.addDefault(path, def);
        return this.config.getBoolean(path, this.config.getBoolean(path));
    }

    public double getDouble(String path, double def) {
        this.config.addDefault(path, def);
        return this.config.getDouble(path, this.config.getDouble(path));
    }

    public float getFloat(String path, float def) {
        return (float) this.getDouble(path, (double) def);
    }

    public int getInt(String path, int def) {
        this.config.addDefault(path, def);
        return config.getInt(path, this.config.getInt(path));
    }

    public <T> List<?> getList(String path, T def) {
        this.config.addDefault(path, def);
        return this.config.getList(path, this.config.getList(path));
    }

    public String getString(String path, String def) {
        this.config.addDefault(path, def);
        return this.config.getString(path, this.config.getString(path));
    }

}
