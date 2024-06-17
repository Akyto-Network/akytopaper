package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.EventCallback;
import org.bukkit.event.EventPriority;

/**
 * An {@link EventCallback} belonging to a specific {@link Plugin}, that intercepts
 * {@link Event}s at a specific {@link EventPriority} level.
 *
 * Used internally by the event system.
 */
public interface RegisteredEventHandler extends EventCallback {

    Plugin getPlugin();

    EventPriority getPriority();

    boolean isIgnoringCancelled();
}
