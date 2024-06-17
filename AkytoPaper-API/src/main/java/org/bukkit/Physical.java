package org.bukkit;

/**
 * Common interface for any type of object that can be associated with a specific world.
 * This interface makes no guarantees about the mutability or nullability of the world.
 */
public interface Physical {

    /**
     * Return the world this object is associated with. May return null if the world
     * is not available (i.e. not loaded), or this object does not have a world.
     */
    World getWorld();
}
