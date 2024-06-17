package org.bukkit.event;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutionException;

/**
 * Represents an event.
 *
 * All events require a static method named getHandlerList() which returns the same {@link HandlerList} as {@link #getHandlers()}.
 *
 * @see PluginManager#callEvent(Event)
 * @see PluginManager#registerEvents(Listener,Plugin)
 */
public abstract class Event {
    private String name;
    private final boolean async;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public Event() {
        this(false);
    }

    /**
     * This constructor is used to explicitly declare an event as synchronous
     * or asynchronous.
     *
     * @param isAsync true indicates the event will fire asynchronously, false
     *     by default from default constructor
     */
    public Event(boolean isAsync) {
        this.async = isAsync;
    }

    // Paper start
    /**
     * Calls the event and tests if cancelled.
     *
     * @return false if event was cancelled, if cancellable. otherwise true.
     */
    public boolean callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        if (this instanceof Cancellable) {
            return !((Cancellable) this).isCancelled();
        } else {
            return true;
        }
    }
    // Paper end

    /**
     * Convenience method for providing a user-friendly identifier. By
     * default, it is the event's class's {@linkplain Class#getSimpleName()
     * simple name}.
     *
     * @return name of this event
     */
    public String getEventName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    /**
     * Continue dispatch of this event by calling {@link PluginManager#yield(Event)}.
     */
    public void yield() {
        Bukkit.getPluginManager().yield(this);
    }

    /**
     * Return the {@link HandlerList} for the given {@link Event} subtype
     *
     * This will search for an ancestor class containing a static method with this name and signature:
     *
     *     HandlerList getHandlerList();
     *
     * Or a static field with this name and type:
     *
     *     HandlerList handlers;
     *
     * The accessibility of the method/field does not matter.
     *
     * Results are cached per event type, so searches after the first are fairly quick
     * and do not perform any reflective operations.
     *
     * @throws IllegalStateException if no registration class can be found, or the registration class
     *                               throws a checked exception when attempting to get the list.
     */
    public static HandlerList getHandlerList(Class<? extends Event> type) {
        return HANDLER_LISTS.getUnchecked(type);
    }

    private static final LoadingCache<Class<? extends Event>, HandlerList> HANDLER_LISTS = CacheBuilder.newBuilder().build(new CacheLoader<Class<? extends Event>, HandlerList>() {
        @Override
        public HandlerList load(Class<? extends Event> clazz) throws Exception {
            Method method = null;
            try {
                method = clazz.getDeclaredMethod("getHandlerList");
            } catch(NoSuchMethodException ignored) {}
            if(method != null && Modifier.isStatic(method.getModifiers()) && HandlerList.class.isAssignableFrom(method.getReturnType())) {
                method.setAccessible(true);
                return (HandlerList) method.invoke(null);
            }

            Field field = null;
            try {
                field = clazz.getDeclaredField("handlers");
            } catch(NoSuchFieldException ignored) {}
            if(field != null && Modifier.isStatic(field.getModifiers()) && HandlerList.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                return (HandlerList) field.get(null);
            }

            Throwable ex = null;
            final Class<?> up = clazz.getSuperclass();
            if(up != null && !up.equals(Event.class) && Event.class.isAssignableFrom(up)) {
                try {
                    return HANDLER_LISTS.get(up.asSubclass(Event.class));
                } catch(ExecutionException e) {
                    ex = e.getCause();
                } catch(UncheckedExecutionException e) {
                    ex = e.getCause();
                }
            }

            throw new IllegalStateException("Unable to find HandlerList for event type " + clazz.getName(), ex);
        }
    });

    /**
     * Return the {@link HandlerList} for this event.
     *
     * By default, this calls {@link #getHandlerList(Class)} with this object's class.
     * Since this method is called every time the event is dispatched, it may be a
     * worthwhile optimization to override it and return the list directly.
     */
    public HandlerList getHandlers() {
        return getHandlerList(getClass());
    }


    /**
     * Any custom event that should not by synchronized with other events must
     * use the specific constructor. These are the caveats of using an
     * asynchronous event:
     * <ul>
     * <li>The event is never fired from inside code triggered by a
     *     synchronous event. Attempting to do so results in an {@link
     *     java.lang.IllegalStateException}.
     * <li>However, asynchronous event handlers may fire synchronous or
     *     asynchronous events
     * <li>The event may be fired multiple times simultaneously and in any
     *     order.
     * <li>Any newly registered or unregistered handler is ignored after an
     *     event starts execution.
     * <li>The handlers for this event may block for any length of time.
     * <li>Some implementations may selectively declare a specific event use
     *     as asynchronous. This behavior should be clearly defined.
     * <li>Asynchronous calls are not calculated in the plugin timing system.
     * </ul>
     *
     * @return false by default, true if the event fires asynchronously
     */
    public final boolean isAsynchronous() {
        return async;
    }

    public enum Result {

        /**
         * Deny the event. Depending on the event, the action indicated by the
         * event will either not take place or will be reverted. Some actions
         * may not be denied.
         */
        DENY,
        /**
         * Neither deny nor allow the event. The server will proceed with its
         * normal handling.
         */
        DEFAULT,
        /**
         * Allow / Force the event. The action indicated by the event will
         * take place if possible, even if the server would not normally allow
         * the action. Some actions may not be allowed.
         */
        ALLOW;
    }
}
