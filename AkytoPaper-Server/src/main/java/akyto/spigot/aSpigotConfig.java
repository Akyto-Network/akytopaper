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

    // START KNOCKBACK //

    private boolean onePointOneKnockback;

    private double horizontal;
    private double vertical;

    private boolean enableFrictionHorizontal;
    private boolean enableFrictionVertical;
    private double verticalFriction;
    private double horizontalFriction;

    private double sprintHorizontal;
    private double sprintVertical;

    private double groundHorizontal;
    private double groundVertical;

    private double slowdown;

    private boolean stopSprint;

    private boolean enableVerticalLimit;
    private double verticalLimit;

    // END KNOCKBACK //

    private boolean hitDetect;
    private boolean hidePlayersFromTab;
    private boolean antiglitchPearl;

    private float gravity;
    private float velocity;
    private float inaccuracy;
    private boolean smoothPotting;

    private boolean releaseItemFix;
    private double maxBlockingSpeed;

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
        this.onePointOneKnockback = this.getBoolean("onePointOneKnockback", false);

        this.horizontal = this.getDouble("horizontal", 0.33d);
        this.vertical = this.getDouble("vertical", 0.36d);

        this.enableFrictionHorizontal = this.getBoolean("enableFrictionHorizontal", true);
        this.enableFrictionVertical = this.getBoolean("enableFrictionVertical", false);
        this.horizontalFriction = this.getDouble("horizontalFriction", 1.0d);
        this.verticalFriction = this.getDouble("verticalFriction", 1.0d);

        this.sprintHorizontal = this.getDouble("sprintHorizontal", 1.2d);
        this.sprintVertical = this.getDouble("sprintVertical", 1.0d);

        this.groundHorizontal = this.getDouble("groundHorizontal", 1.1d);
        this.groundVertical = this.getDouble("groundVertical", 1.0d);

        this.slowdown = this.getDouble("slowdown", 0.6d);

        this.stopSprint = this.getBoolean("stopSprint", true);

        this.enableVerticalLimit = this.getBoolean("enableVerticalLimit", true);
        this.verticalLimit = this.getDouble("verticalLimit", 1.2d);


        this.smoothPotting = this.getBoolean("smooth-potting", true);
        this.hitDetect = this.getBoolean("hitDetect", true);
        this.hidePlayersFromTab = this.getBoolean("hidePlayersFromTab", false);
        this.antiglitchPearl = this.getBoolean("antiglitchPearl", true);
        this.gravity = this.getFloat("gravity", 0.05f);
        this.velocity = this.getFloat("velocity", 0.5f);
        this.inaccuracy = this.getFloat("inaccuracy", -10.0f);
        this.releaseItemFix = this.getBoolean("releaseItemFix", true);
        this.maxBlockingSpeed = this.getDouble("maxBlockingSpeed", 0.2d);
        SpigotBridge.disableOpPermissions = this.getBoolean("disable-op", false);

        save();
    }

    public void save() {
        try {
            config.set("onePointOneKnockback", onePointOneKnockback);
            config.set("horizontal", horizontal);
            config.set("vertical", vertical);
            config.set("sprintHorizontal", sprintHorizontal);
            config.set("sprintVertical", sprintVertical);
            config.set("enableVerticalLimit", enableVerticalLimit);
            config.set("verticalLimit", verticalLimit);
            config.set("enableFrictionHorizontal", enableFrictionHorizontal);
            config.set("horizontalFriction", horizontalFriction);
            config.set("enableFrictionVertical", enableFrictionVertical);
            config.set("verticalFriction", verticalFriction);
            config.set("groundHorizontal", groundHorizontal);
            config.set("groundVertical", groundVertical);
            config.set("slowdown", slowdown);
            config.set("stopSprint", stopSprint);
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
