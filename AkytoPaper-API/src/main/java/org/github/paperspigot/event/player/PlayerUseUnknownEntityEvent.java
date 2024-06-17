package org.github.paperspigot.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUseUnknownEntityEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final int entityId;
    private final boolean attack;

    public PlayerUseUnknownEntityEvent(Player who, int entityId, boolean attack) {
        super(who);
        this.entityId = entityId;
        this.attack = attack;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public boolean isAttack() {
        return this.attack;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
