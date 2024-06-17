package app.ashcon.sportpaper.server;

import net.minecraft.server.NBTTagCompound;
import app.ashcon.sportpaper.api.world.CustomizedGenerationSettings;

/**
 * Acts as a bridge between {@link
 * CustomizedGenerationSettings} and NMS classes.
 *
 * @author Rafi Baum
 */
public class WorldGenSettingsManager {

  private final CustomizedGenerationSettings settings;

  /**
   * Constructs the object with default settings.
   */
  public WorldGenSettingsManager() {
    this(new CustomizedGenerationSettings());
  }

  /**
   * Constructs the object with specified generation settings.
   * @param settings to use in world gen.
   */
  public WorldGenSettingsManager(CustomizedGenerationSettings settings) {
    this.settings = settings;
  }

  /**
   * Constructs the object using data from an NBTTag.
   * @param nbtTagCompound to read settings from
   */
  public WorldGenSettingsManager(NBTTagCompound nbtTagCompound) {
    this.settings = new CustomizedGenerationSettings();

    if (nbtTagCompound.hasKey("CaveFrequency")) {
      settings.setCaveFrequency(nbtTagCompound.getInt("CaveFrequency"));
    }

    if (nbtTagCompound.hasKey("CaveMinLengthFactor")) {
      settings.setCaveLengthMin(nbtTagCompound.getInt("CaveMinLengthFactor"));
    }

  }

  /**
   * @return settings to be used with world generation
   */
  public CustomizedGenerationSettings getSettings() {
    return settings;
  }

  /**
   * Saves custom settings to the map's NBTTag
   * @param nbtTagCompound to save data to
   */
  public void save(NBTTagCompound nbtTagCompound) {
    nbtTagCompound.setInt("CaveFrequency", settings.getCaveFrequency());
    nbtTagCompound.setInt("CaveMinLengthFactor", settings.getCaveLengthMin());
  }

}
