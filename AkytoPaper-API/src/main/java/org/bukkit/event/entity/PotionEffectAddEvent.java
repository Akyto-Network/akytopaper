package org.bukkit.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

/**
 * Called when a potion effect is applied to an entity, or an existing effect is extended or upgraded
 */
public class PotionEffectAddEvent extends PotionEffectEvent implements Cancellable {

    private boolean cancelled;
    private final EffectAddReason reason;

    public PotionEffectAddEvent(LivingEntity what, PotionEffect effect, EffectAddReason reason) {
        super(what, effect);
        this.reason = reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public EffectAddReason getReason() {
        return reason;
    }

    private static final HandlerList handlers = new HandlerList();
    @Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }

    public enum EffectAddReason {
        /**
         * Added using the /effect command
         */
        COMMAND,
        /**
         * Added by eating a golden apple
         */
        GOLDEN_APPLE,
        /**
         * Added by being near a beacon
         */
        BEACON,
        /**
         * Added by being hit with a wither skull
         */
        WITHER_SKULL,
        /**
         * Added by being hit by a wither skeleton
         */
        WITHER_SKELETON,
        /**
         * Added to villagers when they are cured
         */
        VILLAGER_CURE,
        /**
         * Added to villagers when they unlock a new set of trades
         */
        VILLAGER_LEVELUP,
        /**
         * Added to spiders on higher difficulty
         */
        SPIDER_POWERUP,
        /**
         * Added when a potion is splashed
         */
        POTION_SPLASH,
        /**
         * Added when a potion is consumed
         */
        POTION_DRINK,
        /**
         * Added by a plugin
         */
        CUSTOM,
        /**
         * Added by a part of the vanilla code not covered in the above cases
         */
        UNKNOWN
    }
}
