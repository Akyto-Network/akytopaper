package org.bukkit.event;

/**
 * Something that is called with an {@link Event}, has several uses.
 */
@FunctionalInterface
public interface EventCallback {
    void callEvent(Event event) throws Exception;
}
