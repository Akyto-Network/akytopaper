package org.bukkit.plugin;

import java.io.File;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.event.EventCallback;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

/**
 * Handles all plugin management from the Server
 */
public interface PluginManager {

    /**
     * Registers the specified plugin loader
     *
     * @param loader Class name of the PluginLoader to register
     * @throws IllegalArgumentException Thrown when the given Class is not a
     *     valid PluginLoader
     */
    public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException;

    /**
     * Checks if the given plugin is loaded and returns it when applicable
     * <p>
     * Please note that the name of the plugin is case-sensitive
     *
     * @param name Name of the plugin to check
     * @return Plugin if it exists, otherwise null
     */
    public Plugin getPlugin(String name);

    /**
     * Gets a list of all currently loaded plugins
     *
     * @return Array of Plugins
     */
    public Plugin[] getPlugins();

    /**
     * Checks if the given plugin is enabled or not
     * <p>
     * Please note that the name of the plugin is case-sensitive.
     *
     * @param name Name of the plugin to check
     * @return true if the plugin is enabled, otherwise false
     */
    public boolean isPluginEnabled(String name);

    /**
     * Checks if the given plugin is enabled or not
     *
     * @param plugin Plugin to check
     * @return true if the plugin is enabled, otherwise false
     */
    public boolean isPluginEnabled(Plugin plugin);

    /**
     * Loads the plugin in the specified file
     * <p>
     * File must be valid according to the current enabled Plugin interfaces
     *
     * @param file File containing the plugin to load
     * @return The Plugin loaded, or null if it was invalid
     * @throws InvalidPluginException Thrown when the specified file is not a
     *     valid plugin
     * @throws InvalidDescriptionException Thrown when the specified file
     *     contains an invalid description
     * @throws UnknownDependencyException If a required dependency could not
     *     be resolved
     */
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

    /**
     * Loads the plugins contained within the specified directory
     *
     * @param directory Directory to check for plugins
     * @return A list of all plugins loaded
     */
    public Plugin[] loadPlugins(File directory);

    /**
     * Disables all the loaded plugins
     */
    public void disablePlugins();

    /**
     * Disables and removes all plugins
     */
    public void clearPlugins();

    /**
     * Call {@link #callEvent(Event, EventPriority, EventCallback)} with null priority and body.
     */
    void callEvent(Event event) throws IllegalStateException;

    /**
     * Call {@link #callEvent(Event, EventPriority, EventCallback)} with null priority.
     */
    void callEvent(Event event, EventCallback body) throws IllegalStateException;

    /**
     * Call {@link #callEvent(Event, EventPriority, EventCallback)} with a null body.
     */
    void callEvent(Event event, EventPriority priority) throws IllegalStateException;

    /**
     * Dispatch the given event to all applicable registered event handlers,
     * then (optionally) to the given {@link EventCallback}.
     *
     * Event handlers are chained in order of priority, each being called from within
     * the previous handler, either explicitly when that handler calls {@link #yield(Event)},
     * or implicitly when the handler returns without calling it. The {@link EventCallback},
     * if present, effectively becomes the innermost handler for this particular dispatch.
     *
     * @param event         Event object passed to handlers
     * @param priority      If non-null, only call handlers at this priority level
     * @param body          Body of the actual event, or null for a no-op event
     *
     * @throws IllegalStateException Thrown when an asynchronous event is
     *     fired from synchronous code.
     *     <p>
     *     <i>Note: This is best-effort basis, and should not be used to test
     *     synchronized state. This is an indicator for flawed flow logic.</i>
     */
    void callEvent(Event event, EventPriority priority, EventCallback body) throws IllegalStateException;

    /**
     * When called from within a handler for the given {@link Event}, continues with the
     * dispatch of the event, by calling the next registered handler, or the {@link EventCallback}
     * itself, if there are no more handlers. This method does not return until all
     * of those things have completed.
     *
     * Events cannot be cancelled after calling this method, and the effects of doing
     * so are undefined.
     *
     * If an event handler does not call this method itself, it will effectively be
     * called automatically, just after the handler returns.
     *
     * @param event The event being executed
     *
     * @throws IllegalStateException if called from anywhere besides a handler for the given event,
     *                               or if called multiple times from the same handler.
     */
    void yield(Event event) throws IllegalStateException;

    /**
     * Dispatch the given event to a single {@link RegisteredEventHandler}, then (optionally)
     * to the given {@link EventCallback}.
     *
     * This method does not interact with the registration system at all, it simply calls
     * the given handler directly. If the plugin returned by {@link RegisteredEventHandler#getPlugin()}
     * is not currently enabled, then the handler is not called, but the {@link EventCallback} is.
     *
     * This is roughly equivalent to calling {@link #callEvent(Event, EventCallback)},
     * while the given handler is the only one registered, except no synchronization or
     * thread checking is done.
     *
     * The event system uses this method internally, and it is exposed primarily for the
     * purpose of extending the event system.
     *
     * @param event         Event object passed to the handler
     * @param priority      If non-null, only call the handler if it has this exact priority level
     * @param handler       Event handler to call
     * @param body          If non-null, called after the handler, or right away if the handler is not called
     */
    void callEventHandler(Event event, EventPriority priority, RegisteredEventHandler handler, EventCallback body);

    /**
     * Dispatch the given event directly to the given {@link EventCallback}.
     *
     * The event system uses this method internally, and it is exposed primarily for the
     * purpose of extending the event system.
     */
    void callEventBody(Event event, EventCallback body);

    /**
     * Handle the given {@link Throwable} as if it was thrown from an event handler
     * belonging to the given {@link Plugin}.
     *
     * A full trace will be sent to the server logger, and {@link AuthorNagException}s
     * will be handled appropriately.
     *
     * The event system uses this method internally, and it is exposed primarily for the
     * purpose of extending the event system.
     */
    void handleEventException(Event event, Plugin plugin, Throwable ex);

    /**
     * Registers all the events in the given listener class
     *
     * @param listener Listener to register
     * @param plugin Plugin to register
     */
    public void registerEvents(Listener listener, Plugin plugin);

    /**
     * Registers the specified executor to the given event class
     *
     * @param event Event type to register
     * @param listener Listener to register
     * @param priority Priority to register this event at
     * @param executor EventExecutor to register
     * @param plugin Plugin to register
     */
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin);

    /**
     * Registers the specified executor to the given event class
     *
     * @param event Event type to register
     * @param listener Listener to register
     * @param priority Priority to register this event at
     * @param executor EventExecutor to register
     * @param plugin Plugin to register
     * @param ignoreCancelled Whether to pass cancelled events or not
     */
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled);

    /**
     * Enables the specified plugin
     * <p>
     * Attempting to enable a plugin that is already enabled will have no
     * effect
     *
     * @param plugin Plugin to enable
     */
    public void enablePlugin(Plugin plugin);

    /**
     * Disables the specified plugin
     * <p>
     * Attempting to disable a plugin that is not enabled will have no effect
     *
     * @param plugin Plugin to disable
     */
    public void disablePlugin(Plugin plugin);

    /**
     * Gets a {@link Permission} from its fully qualified name
     *
     * @param name Name of the permission
     * @return Permission, or null if none
     */
    public Permission getPermission(String name);

    /**
     * Adds a {@link Permission} to this plugin manager.
     * <p>
     * If a permission is already defined with the given name of the new
     * permission, an exception will be thrown.
     *
     * @param perm Permission to add
     * @throws IllegalArgumentException Thrown when a permission with the same
     *     name already exists
     */
    public void addPermission(Permission perm);

    /**
     * Removes a {@link Permission} registration from this plugin manager.
     * <p>
     * If the specified permission does not exist in this plugin manager,
     * nothing will happen.
     * <p>
     * Removing a permission registration will <b>not</b> remove the
     * permission from any {@link Permissible}s that have it.
     *
     * @param perm Permission to remove
     */
    public void removePermission(Permission perm);

    /**
     * Removes a {@link Permission} registration from this plugin manager.
     * <p>
     * If the specified permission does not exist in this plugin manager,
     * nothing will happen.
     * <p>
     * Removing a permission registration will <b>not</b> remove the
     * permission from any {@link Permissible}s that have it.
     *
     * @param name Permission to remove
     */
    public void removePermission(String name);

    /**
     * Gets the default permissions for the given op status
     *
     * @param op Which set of default permissions to get
     * @return The default permissions
     */
    public Set<Permission> getDefaultPermissions(boolean op);

    /**
     * Recalculates the defaults for the given {@link Permission}.
     * <p>
     * This will have no effect if the specified permission is not registered
     * here.
     *
     * @param perm Permission to recalculate
     */
    public void recalculatePermissionDefaults(Permission perm);

    /**
     * Subscribes the given Permissible for information about the requested
     * Permission, by name.
     * <p>
     * If the specified Permission changes in any form, the Permissible will
     * be asked to recalculate.
     *
     * @param permission Permission to subscribe to
     * @param permissible Permissible subscribing
     */
    public void subscribeToPermission(String permission, Permissible permissible);

    /**
     * Unsubscribes the given Permissible for information about the requested
     * Permission, by name.
     *
     * @param permission Permission to unsubscribe from
     * @param permissible Permissible subscribing
     */
    public void unsubscribeFromPermission(String permission, Permissible permissible);

    /**
     * Gets a set containing all subscribed {@link Permissible}s to the given
     * permission, by name
     *
     * @param permission Permission to query for
     * @return Set containing all subscribed permissions
     */
    public Set<Permissible> getPermissionSubscriptions(String permission);

    /**
     * Subscribes to the given Default permissions by operator status
     * <p>
     * If the specified defaults change in any form, the Permissible will be
     * asked to recalculate.
     *
     * @param op Default list to subscribe to
     * @param permissible Permissible subscribing
     */
    public void subscribeToDefaultPerms(boolean op, Permissible permissible);

    /**
     * Unsubscribes from the given Default permissions by operator status
     *
     * @param op Default list to unsubscribe from
     * @param permissible Permissible subscribing
     */
    public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible);

    /**
     * Gets a set containing all subscribed {@link Permissible}s to the given
     * default list, by op status
     *
     * @param op Default list to query for
     * @return Set containing all subscribed permissions
     */
    public Set<Permissible> getDefaultPermSubscriptions(boolean op);

    /**
     * Gets a set of all registered permissions.
     * <p>
     * This set is a copy and will not be modified live.
     *
     * @return Set containing all current registered permissions
     */
    public Set<Permission> getPermissions();

    /**
     * Returns whether or not timing code should be used for event calls
     *
     * @return True if event timings are to be used
     */
    public boolean useTimings();
}
