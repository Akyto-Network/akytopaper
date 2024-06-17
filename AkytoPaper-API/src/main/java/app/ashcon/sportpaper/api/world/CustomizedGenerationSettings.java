package app.ashcon.sportpaper.api.world;

import java.util.Random;

/**
 * This class is used to customize aspects of world generation outside of what vanilla Minecraft
 * normally provides.
 *
 * @author Rafi Baum
 */
public class CustomizedGenerationSettings {

  private int caveFrequency = 7;
  private int caveLengthMin = 4;

  /**
   * Gets how frequently caves are generated, as the inverse of the percent chance that a cave
   * (system) will start in any given chunk. For example, 7 would represent a 1/7 (14.8%) chance
   * that a cave spawns in a chunk.
   *
   * @return the inverse of the chance a cave will spawn in a chunk
   */
  public int getCaveFrequency() {
    return caveFrequency;
  }

  /**
   * Sets how frequently caves are generated, as the inverse of the percent chance that a cave
   * (system) will start in any given chunk. For example, 7 would represent a 1/7 (14.8%) chance
   * that a cave spawns in a chunk.
   *
   * @return the inverse of the chance a cave will spawn in a chunk
   */
  public void setCaveFrequency(int caveFrequency) {
    this.caveFrequency = caveFrequency;
  }

  /**
   * Gets the minimum length factor of a cave, a positive integer greater than zero. The larger the
   * factor, the longer caves will be on average.
   *
   * @return minimum cave length factor
   */
  public int getCaveLengthMin() {
    return caveLengthMin;
  }

  /**
   * Sets minimum cave length factor. The larger the factor, the longer caves will be on average.
   * @param caveLengthMin minimum cave length factor
   */
  public void setCaveLengthMin(int caveLengthMin) {
    this.caveLengthMin = caveLengthMin;
  }

  /**
   * Override this method to change how large caves should be.
   * @param random
   * @return size of caves, typically between 0 and 3
   */
  public float getCaveSize(Random random) {
    return random.nextFloat() * 2.0F + random.nextFloat();
  }

  /**
   * Override this method to change how large caves should be when a larger cave should be spawned.
   * @param random
   * @return size of larger caves, typically between 1 and 4
   */
  public float getLargeCaveSize(Random random) {
    return random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
  }

}
