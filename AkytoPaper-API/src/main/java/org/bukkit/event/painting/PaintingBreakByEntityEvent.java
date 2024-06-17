package org.bukkit.event.painting;

import org.bukkit.Warning;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;
import org.bukkit.event.EntityAction;

/**
 * Triggered when a painting is removed by an entity
 *
 * @deprecated Use {@link org.bukkit.event.hanging.HangingBreakByEntityEvent}
 *     instead.
 */
@Deprecated
@Warning(reason="This event has been replaced by HangingBreakByEntityEvent")
public class PaintingBreakByEntityEvent extends PaintingBreakEvent implements EntityAction {
    private final Entity remover;

    public PaintingBreakByEntityEvent(final Painting painting, final Entity remover) {
        super(painting, RemoveCause.ENTITY);
        this.remover = remover;
    }

    /**
     * Gets the entity that removed the painting
     *
     * @return the entity that removed the painting.
     */
    public Entity getRemover() {
        return remover;
    }

    @Override
    public Entity getActor() {
        return getRemover();
    }
}
