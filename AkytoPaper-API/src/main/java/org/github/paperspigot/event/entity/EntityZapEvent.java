package org.github.paperspigot.event.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityActionBase;

/**
 *  Fired when lightning strikes an entity
 */
public class EntityZapEvent extends EntityActionBase implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final LightningStrike bolt;
    private final Entity replacementEntity;

    public EntityZapEvent(final Entity entity, final LightningStrike bolt, final Entity replacementEntity) {
        super(entity);
        Validate.notNull(bolt);
        Validate.notNull(replacementEntity);
        this.bolt = bolt;
        this.replacementEntity = replacementEntity;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /**
     * Gets the lightning bolt that is striking the entity.
     * @return The lightning bolt responsible for this event
     */
    public LightningStrike getBolt() {
        return bolt;
    }

    /**
     * Gets the entity that will replace the struck entity.
     * @return The entity that will replace the struck entity
     */
    public Entity getReplacementEntity() {
        return replacementEntity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public LightningStrike getActor() {
        return getBolt();
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
