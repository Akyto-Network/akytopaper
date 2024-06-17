package org.bukkit.event.painting;

import org.bukkit.Physical;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.entity.Painting;
import org.bukkit.event.Event;

/**
 * Represents a painting-related event.
 *
 * @deprecated Use {@link org.bukkit.event.hanging.HangingEvent} instead.
 */
@Deprecated
@Warning(reason="This event has been replaced by HangingEvent")
public abstract class PaintingEvent extends Event implements Physical {
    protected Painting painting;

    protected PaintingEvent(final Painting painting) {
        this.painting = painting;
    }

    /**
     * Gets the painting involved in this event.
     *
     * @return the painting
     */
    public Painting getPainting() {
        return painting;
    }

    @Override
    public World getWorld() {
        return getPainting().getWorld();
    }
}
