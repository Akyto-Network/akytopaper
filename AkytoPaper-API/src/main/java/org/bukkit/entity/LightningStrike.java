package org.bukkit.entity;

/**
 * Represents an instance of a lightning strike. May or may not do damage.
 */
public interface LightningStrike extends Weather {

    /**
     * Returns whether the strike is an effect that does no damage.
     *
     * @return whether the strike is an effect
     */
    public boolean isEffect();

    void setSilent(boolean bool);


    public class Spigot extends Entity.Spigot
    {

        /*
         * Returns whether the strike is silent.
         *
         * @return whether the strike is silent.
         */
        public boolean isSilent()
        {
            throw new UnsupportedOperationException( "Not supported yet." );
        }

    }

    Spigot spigot();
}
