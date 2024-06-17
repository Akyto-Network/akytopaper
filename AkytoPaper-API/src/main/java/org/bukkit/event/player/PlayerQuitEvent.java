package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when a player leaves a server
 */
public class PlayerQuitEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private String quitMessage;
    private final String disconnectReason; // SportPaper - expose reason

    public PlayerQuitEvent(final Player who, final String quitMessage) {
        this(who, quitMessage, null);
    }

    // SportPaper start - Expose disconnect reason
		public PlayerQuitEvent(Player who, String quitMessage, String disconnectReason) {
				super(who);
				this.quitMessage = quitMessage;
				this.disconnectReason = disconnectReason;
		}

		/**
		 * Gets the exact reason the player disconnected from the server
		 *
		 * @return string disconnect reason
		 */
		public String getDisconnectReason() {
				return disconnectReason;
		}

		// SportPaper end

		/**
     * Gets the quit message to send to all online players
     *
     * @return string quit message
     */
    public String getQuitMessage() {
        return quitMessage;
    }

    /**
     * Sets the quit message to send to all online players
     *
     * @param quitMessage quit message
     */
    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
